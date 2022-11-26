package org.glassfish.grizzly.http.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.http.io.OutputBuffer;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeType;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public abstract class StaticHttpHandlerBase extends HttpHandler {
   private static final Logger LOGGER = Grizzly.logger(StaticHttpHandlerBase.class);
   private volatile int fileCacheFilterIdx = -1;
   private volatile boolean isFileCacheEnabled = true;

   public boolean isFileCacheEnabled() {
      return this.isFileCacheEnabled;
   }

   public void setFileCacheEnabled(boolean isFileCacheEnabled) {
      this.isFileCacheEnabled = isFileCacheEnabled;
   }

   public static void sendFile(Response response, File file) throws IOException {
      response.setStatus(HttpStatus.OK_200);
      pickupContentType(response, file.getPath());
      long length = file.length();
      response.setContentLengthLong(length);
      response.addDateHeader(Header.Date, System.currentTimeMillis());
      if (response.isSendFileEnabled() && !response.getRequest().isSecure()) {
         sendZeroCopy(response, file);
      } else {
         sendUsingBuffers(response, file);
      }

   }

   private static void sendUsingBuffers(Response response, File file) throws FileNotFoundException, IOException {
      int chunkSize = true;
      response.suspend();
      NIOOutputStream outputStream = response.getNIOOutputStream();
      outputStream.notifyCanWrite(new NonBlockingDownloadHandler(response, outputStream, file, 8192));
   }

   private static void sendZeroCopy(Response response, File file) throws IOException {
      OutputBuffer outputBuffer = response.getOutputBuffer();
      outputBuffer.sendfile(file, (CompletionHandler)null);
   }

   public final boolean addToFileCache(Request req, Response res, File resource) {
      if (this.isFileCacheEnabled) {
         FilterChainContext fcContext = req.getContext();
         FileCacheFilter fileCacheFilter = this.lookupFileCache(fcContext);
         if (fileCacheFilter != null) {
            FileCache fileCache = fileCacheFilter.getFileCache();
            if (fileCache.isEnabled()) {
               if (res != null) {
                  addCachingHeaders(res, resource);
               }

               fileCache.add(req.getRequest(), resource);
               return true;
            }
         }
      }

      return false;
   }

   public void service(Request request, Response response) throws Exception {
      String uri = this.getRelativeURI(request);
      if (uri == null || !this.handle(uri, request, response)) {
         this.onMissingResource(request, response);
      }

   }

   protected String getRelativeURI(Request request) throws Exception {
      String uri = request.getDecodedRequestURI();
      if (uri.contains("..")) {
         return null;
      } else {
         String resourcesContextPath = request.getContextPath();
         if (resourcesContextPath != null && !resourcesContextPath.isEmpty()) {
            if (!uri.startsWith(resourcesContextPath)) {
               return null;
            }

            uri = uri.substring(resourcesContextPath.length());
         }

         return uri;
      }
   }

   protected void onMissingResource(Request request, Response response) throws Exception {
      response.sendError(404);
   }

   protected abstract boolean handle(String var1, Request var2, Response var3) throws Exception;

   protected FileCacheFilter lookupFileCache(FilterChainContext fcContext) {
      FilterChain fc = fcContext.getFilterChain();
      int lastFileCacheIdx = this.fileCacheFilterIdx;
      if (lastFileCacheIdx != -1 && lastFileCacheIdx < fc.size()) {
         Filter filter = (Filter)fc.get(lastFileCacheIdx);
         if (filter instanceof FileCacheFilter) {
            return (FileCacheFilter)filter;
         }
      }

      int size = fc.size();

      for(int i = 0; i < size; ++i) {
         Filter filter = (Filter)fc.get(i);
         if (filter instanceof FileCacheFilter) {
            this.fileCacheFilterIdx = i;
            return (FileCacheFilter)filter;
         }
      }

      this.fileCacheFilterIdx = -1;
      return null;
   }

   protected static void pickupContentType(Response response, String path) {
      if (!response.getResponse().isContentTypeSet()) {
         int dot = path.lastIndexOf(46);
         if (dot > 0) {
            String ext = path.substring(dot + 1);
            String ct = MimeType.get(ext);
            if (ct != null) {
               response.setContentType(ct);
            }
         } else {
            response.setContentType(MimeType.get("html"));
         }
      }

   }

   protected static void addCachingHeaders(Response response, File file) {
      StringBuilder sb = new StringBuilder();
      long fileLength = file.length();
      long lastModified = file.lastModified();
      if (fileLength >= 0L || lastModified >= 0L) {
         sb.append('"').append(fileLength).append('-').append(lastModified).append('"');
         response.setHeader(Header.ETag, sb.toString());
      }

      response.addDateHeader(Header.LastModified, lastModified);
   }

   private static class NonBlockingDownloadHandler implements WriteHandler {
      private volatile long size;
      private final Response response;
      private final NIOOutputStream outputStream;
      private final FileChannel fileChannel;
      private final MemoryManager mm;
      private final int chunkSize;

      NonBlockingDownloadHandler(Response response, NIOOutputStream outputStream, File file, int chunkSize) {
         try {
            this.fileChannel = (new FileInputStream(file)).getChannel();
         } catch (FileNotFoundException var6) {
            throw new IllegalStateException("File should have existed", var6);
         }

         this.size = file.length();
         this.response = response;
         this.outputStream = outputStream;
         this.mm = response.getRequest().getContext().getMemoryManager();
         this.chunkSize = chunkSize;
      }

      public void onWritePossible() throws Exception {
         StaticHttpHandlerBase.LOGGER.log(Level.FINE, "[onWritePossible]");
         boolean isWriteMore = this.sendChunk();
         if (isWriteMore) {
            this.outputStream.notifyCanWrite(this);
         }

      }

      public void onError(Throwable t) {
         StaticHttpHandlerBase.LOGGER.log(Level.FINE, "[onError] ", t);
         this.response.setStatus(500, t.getMessage());
         this.complete(true);
      }

      private boolean sendChunk() throws IOException {
         Buffer buffer = this.mm.allocate(this.chunkSize);
         buffer.allowBufferDispose(true);
         int justReadBytes = (int)Buffers.readFromFileChannel(this.fileChannel, buffer);
         if (justReadBytes <= 0) {
            this.complete(false);
            return false;
         } else {
            buffer.trim();
            this.outputStream.write(buffer);
            this.size -= (long)justReadBytes;
            if (this.size <= 0L) {
               this.complete(false);
               return false;
            } else {
               return true;
            }
         }
      }

      private void complete(boolean isError) {
         try {
            this.fileChannel.close();
         } catch (IOException var4) {
            if (!isError) {
               this.response.setStatus(500, var4.getMessage());
            }
         }

         try {
            this.outputStream.close();
         } catch (IOException var3) {
            if (!isError) {
               this.response.setStatus(500, var3.getMessage());
            }
         }

         if (this.response.isSuspended()) {
            this.response.resume();
         } else {
            this.response.finish();
         }

      }
   }
}
