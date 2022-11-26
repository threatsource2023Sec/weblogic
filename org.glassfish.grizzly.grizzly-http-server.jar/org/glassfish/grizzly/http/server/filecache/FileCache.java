package org.glassfish.grizzly.http.server.filecache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.util.SimpleDateFormats;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.FastHttpDateFormat;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.utils.DelayedExecutor;

public class FileCache implements MonitoringAware {
   private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));
   static final String[] COMPRESSION_ALIASES = new String[]{"gzip"};
   private static final Logger LOGGER = Grizzly.logger(FileCache.class);
   private final AtomicInteger cacheSize = new AtomicInteger();
   private final ConcurrentMap fileCacheMap = new ConcurrentHashMap();
   private final FileCacheEntry NULL_CACHE_ENTRY = new FileCacheEntry(this);
   private int secondsMaxAge = -1;
   private volatile int maxCacheEntries = 1024;
   private long minEntrySize = Long.MIN_VALUE;
   private long maxEntrySize = Long.MAX_VALUE;
   private volatile long maxLargeFileCacheSize = Long.MAX_VALUE;
   private volatile long maxSmallFileCacheSize = 1048576L;
   private final AtomicLong mappedMemorySize = new AtomicLong();
   private final AtomicLong heapSize = new AtomicLong();
   private boolean enabled = true;
   private DelayedExecutor.DelayQueue delayQueue;
   private volatile File compressedFilesFolder;
   private final CompressionConfig compressionConfig;
   private boolean fileSendEnabled;
   protected final DefaultMonitoringConfig monitoringConfig;

   public FileCache() {
      this.compressedFilesFolder = TMP_DIR;
      this.compressionConfig = new CompressionConfig();
      this.monitoringConfig = new DefaultMonitoringConfig(FileCacheProbe.class) {
         public Object createManagementObject() {
            return FileCache.this.createJmxManagementObject();
         }
      };
   }

   public void initialize(DelayedExecutor delayedExecutor) {
      this.delayQueue = delayedExecutor.createDelayQueue(new EntryWorker(), new EntryResolver());
   }

   public CacheResult add(HttpRequestPacket request, long lastModified) {
      return this.add(request, (File)null, lastModified);
   }

   public CacheResult add(HttpRequestPacket request, File cacheFile) {
      return this.add(request, cacheFile, cacheFile.lastModified());
   }

   protected CacheResult add(HttpRequestPacket request, File cacheFile, long lastModified) {
      String requestURI = request.getRequestURI();
      if (requestURI == null) {
         return FileCache.CacheResult.FAILED;
      } else {
         String host = request.getHeader(Header.Host);
         FileCacheKey key = new FileCacheKey(host, requestURI);
         if (this.fileCacheMap.putIfAbsent(key, this.NULL_CACHE_ENTRY) != null) {
            key.recycle();
            return FileCache.CacheResult.FAILED_ENTRY_EXISTS;
         } else {
            int size = this.cacheSize.incrementAndGet();
            if (size > this.getMaxCacheEntries()) {
               this.cacheSize.decrementAndGet();
               this.fileCacheMap.remove(key);
               key.recycle();
               return FileCache.CacheResult.FAILED_CACHE_FULL;
            } else {
               HttpResponsePacket response = request.getResponse();
               MimeHeaders headers = response.getHeaders();
               String contentType = response.getContentType();
               FileCacheEntry entry;
               if (cacheFile != null) {
                  entry = this.createEntry(cacheFile);
                  entry.setCanBeCompressed(this.canBeCompressed(cacheFile, contentType));
               } else {
                  entry = new FileCacheEntry(this);
                  entry.type = FileCache.CacheType.TIMESTAMP;
               }

               entry.key = key;
               entry.requestURI = requestURI;
               entry.lastModified = lastModified;
               entry.contentType = ContentType.newContentType(contentType);
               entry.xPoweredBy = headers.getHeader(Header.XPoweredBy);
               entry.date = headers.getHeader(Header.Date);
               entry.lastModifiedHeader = headers.getHeader(Header.LastModified);
               entry.host = host;
               entry.Etag = headers.getHeader(Header.ETag);
               entry.server = headers.getHeader(Header.Server);
               this.fileCacheMap.put(key, entry);
               notifyProbesEntryAdded(this, entry);
               int secondsMaxAgeLocal = this.getSecondsMaxAge();
               if (secondsMaxAgeLocal > 0) {
                  this.delayQueue.add(entry, (long)secondsMaxAgeLocal, TimeUnit.SECONDS);
               }

               return entry.type == FileCache.CacheType.TIMESTAMP ? FileCache.CacheResult.OK_CACHED_TIMESTAMP : FileCache.CacheResult.OK_CACHED;
            }
         }
      }
   }

   public FileCacheEntry get(HttpRequestPacket request) {
      if (this.cacheSize.get() == 0) {
         return null;
      } else {
         LazyFileCacheKey key = LazyFileCacheKey.create(request);
         FileCacheEntry entry = (FileCacheEntry)this.fileCacheMap.get(key);
         key.recycle();

         try {
            if (entry != null && entry != this.NULL_CACHE_ENTRY) {
               HttpStatus httpStatus = this.checkIfHeaders(entry, request);
               boolean flushBody = httpStatus == null;
               if (flushBody && entry.type == FileCache.CacheType.TIMESTAMP) {
                  return null;
               }

               request.getResponse().setStatus(httpStatus != null ? httpStatus : HttpStatus.OK_200);
               notifyProbesEntryHit(this, entry);
               return entry;
            }

            notifyProbesEntryMissed(this, request);
         } catch (Exception var6) {
            notifyProbesError(this, var6);
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_FILECACHE_GENERAL_ERROR(), var6);
         }

         return null;
      }
   }

   protected void remove(FileCacheEntry entry) {
      if (this.fileCacheMap.remove(entry.key) != null) {
         this.cacheSize.decrementAndGet();
      }

      if (entry.type == FileCache.CacheType.MAPPED) {
         this.subMappedMemorySize((long)entry.bb.remaining());
      } else if (entry.type == FileCache.CacheType.HEAP) {
         this.subHeapSize((long)entry.bb.remaining());
      }

      notifyProbesEntryRemoved(this, entry);
   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.server.filecache.jmx.FileCache", this, FileCache.class);
   }

   private FileCacheEntry createEntry(File file) {
      FileCacheEntry entry = this.tryMapFileToBuffer(file);
      if (entry == null) {
         entry = new FileCacheEntry(this);
         entry.type = FileCache.CacheType.FILE;
      }

      entry.plainFile = file;
      entry.plainFileSize = file.length();
      return entry;
   }

   private FileCacheEntry tryMapFileToBuffer(File file) {
      long size = file.length();
      if (size > this.getMaxEntrySize()) {
         return null;
      } else {
         FileChannel fileChannel = null;
         FileInputStream stream = null;

         CacheType type;
         MappedByteBuffer bb;
         FileCacheEntry entry;
         try {
            if (size > this.getMinEntrySize()) {
               if (this.addMappedMemorySize(size) > this.getMaxLargeFileCacheSize()) {
                  this.subMappedMemorySize(size);
                  entry = null;
                  return entry;
               }

               type = FileCache.CacheType.MAPPED;
            } else {
               if (this.addHeapSize(size) > this.getMaxSmallFileCacheSize()) {
                  this.subHeapSize(size);
                  entry = null;
                  return entry;
               }

               type = FileCache.CacheType.HEAP;
            }

            stream = new FileInputStream(file);
            fileChannel = stream.getChannel();
            bb = fileChannel.map(MapMode.READ_ONLY, 0L, size);
            if (type == FileCache.CacheType.HEAP) {
               ((MappedByteBuffer)bb).load();
            }
         } catch (Exception var27) {
            notifyProbesError(this, var27);
            Object var9 = null;
            return (FileCacheEntry)var9;
         } finally {
            if (stream != null) {
               try {
                  stream.close();
               } catch (IOException var26) {
                  notifyProbesError(this, var26);
               }
            }

            if (fileChannel != null) {
               try {
                  fileChannel.close();
               } catch (IOException var25) {
                  notifyProbesError(this, var25);
               }
            }

         }

         entry = new FileCacheEntry(this);
         entry.type = type;
         entry.plainFileSize = size;
         entry.bb = bb;
         return entry;
      }
   }

   private boolean canBeCompressed(File cacheFile, String contentType) {
      switch (this.compressionConfig.getCompressionMode()) {
         case FORCE:
            return true;
         case OFF:
            return false;
         case ON:
            if (cacheFile.length() < (long)this.compressionConfig.getCompressionMinSize()) {
               return false;
            }

            return this.compressionConfig.checkMimeType(contentType);
         default:
            throw new IllegalStateException("Unknown mode");
      }
   }

   public int getSecondsMaxAge() {
      return this.secondsMaxAge;
   }

   public void setSecondsMaxAge(int secondsMaxAge) {
      this.secondsMaxAge = secondsMaxAge;
   }

   public int getMaxCacheEntries() {
      return this.maxCacheEntries;
   }

   public void setMaxCacheEntries(int maxCacheEntries) {
      this.maxCacheEntries = maxCacheEntries;
   }

   public long getMinEntrySize() {
      return this.minEntrySize;
   }

   public void setMinEntrySize(long minEntrySize) {
      this.minEntrySize = minEntrySize;
   }

   public long getMaxEntrySize() {
      return this.maxEntrySize;
   }

   public void setMaxEntrySize(long maxEntrySize) {
      this.maxEntrySize = maxEntrySize;
   }

   public long getMaxLargeFileCacheSize() {
      return this.maxLargeFileCacheSize;
   }

   public void setMaxLargeFileCacheSize(long maxLargeFileCacheSize) {
      this.maxLargeFileCacheSize = maxLargeFileCacheSize;
   }

   public long getMaxSmallFileCacheSize() {
      return this.maxSmallFileCacheSize;
   }

   public void setMaxSmallFileCacheSize(long maxSmallFileCacheSize) {
      this.maxSmallFileCacheSize = maxSmallFileCacheSize;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public CompressionConfig getCompressionConfig() {
      return this.compressionConfig;
   }

   public File getCompressedFilesFolder() {
      return this.compressedFilesFolder;
   }

   public void setCompressedFilesFolder(File compressedFilesFolder) {
      this.compressedFilesFolder = compressedFilesFolder != null ? compressedFilesFolder : TMP_DIR;
   }

   public boolean isFileSendEnabled() {
      return this.fileSendEnabled;
   }

   public void setFileSendEnabled(boolean fileSendEnabled) {
      this.fileSendEnabled = fileSendEnabled;
   }

   protected void compressFile(FileCacheEntry entry) {
      try {
         File tmpCompressedFile = File.createTempFile(String.valueOf(entry.plainFile.hashCode()), ".tmpzip", this.compressedFilesFolder);
         tmpCompressedFile.deleteOnExit();
         InputStream in = null;
         OutputStream out = null;

         try {
            in = new FileInputStream(entry.plainFile);
            out = new GZIPOutputStream(new FileOutputStream(tmpCompressedFile));
            byte[] tmp = new byte[1024];

            while(true) {
               int readNow = in.read(tmp);
               if (readNow == -1) {
                  break;
               }

               out.write(tmp, 0, readNow);
            }
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (IOException var26) {
               }
            }

            if (out != null) {
               try {
                  out.close();
               } catch (IOException var25) {
               }
            }

         }

         long size = tmpCompressedFile.length();
         switch (entry.type) {
            case HEAP:
            case MAPPED:
               FileInputStream cFis = new FileInputStream(tmpCompressedFile);

               try {
                  FileChannel cFileChannel = cFis.getChannel();
                  MappedByteBuffer compressedBb = cFileChannel.map(MapMode.READ_ONLY, 0L, size);
                  if (entry.type == FileCache.CacheType.HEAP) {
                     compressedBb.load();
                  }

                  entry.compressedBb = compressedBb;
               } finally {
                  cFis.close();
               }
            case FILE:
               entry.compressedFileSize = size;
               entry.compressedFile = tmpCompressedFile;
               break;
            default:
               throw new IllegalStateException("The type is not supported: " + entry.type);
         }
      } catch (IOException var29) {
         LOGGER.log(Level.FINE, "Can not compress file: " + entry.plainFile, var29);
      }

   }

   protected final long addHeapSize(long size) {
      return this.heapSize.addAndGet(size);
   }

   protected final long subHeapSize(long size) {
      return this.heapSize.addAndGet(-size);
   }

   public long getHeapCacheSize() {
      return this.heapSize.get();
   }

   protected final long addMappedMemorySize(long size) {
      return this.mappedMemorySize.addAndGet(size);
   }

   protected final long subMappedMemorySize(long size) {
      return this.mappedMemorySize.addAndGet(-size);
   }

   public long getMappedCacheSize() {
      return this.mappedMemorySize.get();
   }

   private HttpStatus checkIfHeaders(FileCacheEntry entry, HttpRequestPacket request) throws IOException {
      HttpStatus httpStatus = this.checkIfMatch(entry, request);
      if (httpStatus == null) {
         httpStatus = this.checkIfModifiedSince(entry, request);
         if (httpStatus == null) {
            httpStatus = this.checkIfNoneMatch(entry, request);
            if (httpStatus == null) {
               httpStatus = this.checkIfUnmodifiedSince(entry, request);
            }
         }
      }

      return httpStatus;
   }

   private HttpStatus checkIfModifiedSince(FileCacheEntry entry, HttpRequestPacket request) throws IOException {
      try {
         String reqModified = request.getHeader(Header.IfModifiedSince);
         if (reqModified != null) {
            if (reqModified.equals(entry.lastModifiedHeader)) {
               return HttpStatus.NOT_MODIFIED_304;
            }

            long headerValue = convertToLong(reqModified);
            if (headerValue != -1L) {
               long lastModified = entry.lastModified;
               if (request.getHeader(Header.IfNoneMatch) == null && lastModified - headerValue <= 1000L) {
                  return HttpStatus.NOT_MODIFIED_304;
               }
            }
         }
      } catch (IllegalArgumentException var8) {
         notifyProbesError(this, var8);
      }

      return null;
   }

   private HttpStatus checkIfNoneMatch(FileCacheEntry entry, HttpRequestPacket request) throws IOException {
      String headerValue = request.getHeader(Header.IfNoneMatch);
      if (headerValue != null) {
         String eTag = entry.Etag;
         boolean conditionSatisfied = false;
         if (!headerValue.equals("*")) {
            StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

            while(!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
               String currentToken = commaTokenizer.nextToken();
               if (currentToken.trim().equals(eTag)) {
                  conditionSatisfied = true;
               }
            }
         } else {
            conditionSatisfied = true;
         }

         if (conditionSatisfied) {
            Method method = request.getMethod();
            if (!Method.GET.equals(method) && !Method.HEAD.equals(method)) {
               return HttpStatus.PRECONDITION_FAILED_412;
            }

            return HttpStatus.NOT_MODIFIED_304;
         }
      }

      return null;
   }

   private HttpStatus checkIfUnmodifiedSince(FileCacheEntry entry, HttpRequestPacket request) throws IOException {
      try {
         long lastModified = entry.lastModified;
         String h = request.getHeader(Header.IfUnmodifiedSince);
         if (h != null) {
            if (h.equals(entry.lastModifiedHeader)) {
               return HttpStatus.PRECONDITION_FAILED_412;
            }

            long headerValue = convertToLong(h);
            if (headerValue != -1L && headerValue - lastModified <= 1000L) {
               return HttpStatus.PRECONDITION_FAILED_412;
            }
         }
      } catch (IllegalArgumentException var8) {
         notifyProbesError(this, var8);
      }

      return null;
   }

   private HttpStatus checkIfMatch(FileCacheEntry entry, HttpRequestPacket request) throws IOException {
      String headerValue = request.getHeader(Header.IfMatch);
      if (headerValue != null && headerValue.indexOf(42) == -1) {
         String eTag = entry.Etag;
         StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
         boolean conditionSatisfied = false;

         while(!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
            String currentToken = commaTokenizer.nextToken();
            if (currentToken.trim().equals(eTag)) {
               conditionSatisfied = true;
            }
         }

         if (!conditionSatisfied) {
            return HttpStatus.PRECONDITION_FAILED_412;
         }
      }

      return null;
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   protected static void notifyProbesEntryAdded(FileCache fileCache, FileCacheEntry entry) {
      FileCacheProbe[] probes = (FileCacheProbe[])fileCache.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         FileCacheProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FileCacheProbe probe = var3[var5];
            probe.onEntryAddedEvent(fileCache, entry);
         }
      }

   }

   protected static void notifyProbesEntryRemoved(FileCache fileCache, FileCacheEntry entry) {
      FileCacheProbe[] probes = (FileCacheProbe[])fileCache.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         FileCacheProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FileCacheProbe probe = var3[var5];
            probe.onEntryRemovedEvent(fileCache, entry);
         }
      }

   }

   protected static void notifyProbesEntryHit(FileCache fileCache, FileCacheEntry entry) {
      FileCacheProbe[] probes = (FileCacheProbe[])fileCache.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         FileCacheProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FileCacheProbe probe = var3[var5];
            probe.onEntryHitEvent(fileCache, entry);
         }
      }

   }

   protected static void notifyProbesEntryMissed(FileCache fileCache, HttpRequestPacket request) {
      FileCacheProbe[] probes = (FileCacheProbe[])fileCache.monitoringConfig.getProbesUnsafe();
      if (probes != null && probes.length > 0) {
         FileCacheProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FileCacheProbe probe = var3[var5];
            probe.onEntryMissedEvent(fileCache, request.getHeader(Header.Host), request.getRequestURI());
         }
      }

   }

   protected static void notifyProbesError(FileCache fileCache, Throwable error) {
      FileCacheProbe[] probes = (FileCacheProbe[])fileCache.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         FileCacheProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FileCacheProbe probe = var3[var5];
            probe.onErrorEvent(fileCache, error);
         }
      }

   }

   protected static long convertToLong(String dateHeader) {
      if (dateHeader == null) {
         return -1L;
      } else {
         SimpleDateFormats formats = SimpleDateFormats.create();

         long var4;
         try {
            long result = FastHttpDateFormat.parseDate(dateHeader, formats.getFormats());
            if (result == -1L) {
               throw new IllegalArgumentException(dateHeader);
            }

            var4 = result;
         } finally {
            formats.recycle();
         }

         return var4;
      }
   }

   private static class EntryResolver implements DelayedExecutor.Resolver {
      private EntryResolver() {
      }

      public boolean removeTimeout(FileCacheEntry element) {
         if (element.timeoutMillis != -1L) {
            element.timeoutMillis = -1L;
            return true;
         } else {
            return false;
         }
      }

      public long getTimeoutMillis(FileCacheEntry element) {
         return element.timeoutMillis;
      }

      public void setTimeoutMillis(FileCacheEntry element, long timeoutMillis) {
         element.timeoutMillis = timeoutMillis;
      }

      // $FF: synthetic method
      EntryResolver(Object x0) {
         this();
      }
   }

   private static class EntryWorker implements DelayedExecutor.Worker {
      private EntryWorker() {
      }

      public boolean doWork(FileCacheEntry element) {
         element.run();
         return true;
      }

      // $FF: synthetic method
      EntryWorker(Object x0) {
         this();
      }
   }

   public static enum CacheResult {
      OK_CACHED,
      OK_CACHED_TIMESTAMP,
      FAILED_CACHE_FULL,
      FAILED_ENTRY_EXISTS,
      FAILED;
   }

   public static enum CacheType {
      HEAP,
      MAPPED,
      FILE,
      TIMESTAMP;
   }
}
