package weblogic.store.io.file.direct;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import weblogic.store.common.StoreDebug;
import weblogic.store.common.StoreRCMUtils;
import weblogic.store.internal.StoreStatisticsImpl;
import weblogic.store.io.file.StoreDir;

public final class FileMapping {
   private static final String DIGEST_ALGORITHM = "SHA-512";
   private static final IONative ioNative = DirectIOManager.getFileMappingNative();
   private static final long GRANULARITY;
   private final int minMapSize;
   private final int maxMapSize;
   private final DirectFileChannel primaryChannel;
   private final File primaryFile;
   private final int ioSize;
   private int maxViewSize;
   private File cacheFile;
   private long mapLimit;
   private long mapStart;
   private long mapEnd;
   private ByteBuffer directView;
   private Long mappingHandle;
   private Long fileHandle;
   private boolean open;
   private long primaryDataLimit;
   private boolean prefetched = true;
   private boolean reinitialize;
   private Throwable openEx;
   private StoreStatisticsImpl stats;

   public FileMapping(FileChannel channel, int ioSize, int minMapSize, int maxMapSize, StoreStatisticsImpl stats, File primaryFile) throws IOException {
      this.ioSize = ioSize;
      this.minMapSize = minMapSize;
      this.maxMapSize = maxMapSize;
      this.maxViewSize = maxMapSize;
      this.stats = stats;
      this.primaryChannel = (DirectFileChannel)channel;
      this.primaryFile = primaryFile;
   }

   public void open(long size, long cacheableSize, final String dir, boolean locked) throws IOException {
      try {
         if (dir != null) {
            boolean wasCreated = false;

            try {
               class OpenCallable implements Callable {
                  public Boolean call() throws IOException {
                     File cacheDir = new File(dir);
                     cacheDir.mkdirs();
                     StoreDir.checkDirectory(cacheDir);
                     String baseName = FileMapping.this.primaryFile.getName();
                     FileMapping.this.cacheFile = new File(cacheDir, baseName + ".cache");
                     return FileMapping.this.cacheFile.createNewFile();
                  }
               }

               wasCreated = (Boolean)StoreRCMUtils.accountAsGlobal((Callable)(new OpenCallable()));
            } catch (Exception var9) {
               StoreRCMUtils.throwIOorRuntimeException(var9);
            }

            StoreDir.checkFile(this.cacheFile);
         }

         this.openInternal(size, cacheableSize, locked);
      } catch (IOException var10) {
         this.openEx = var10;
         throw var10;
      }
   }

   private void openInternal(long size, long cacheableSize, boolean locked) throws IOException {
      this.primaryDataLimit = size;
      this.mapLimit = this.primaryDataLimit % GRANULARITY == 0L ? this.primaryDataLimit : (1L + this.primaryDataLimit / GRANULARITY) * GRANULARITY;
      if (this.cacheFile == null) {
         this.fileHandle = this.primaryChannel.getHandle();
      } else if (this.fileHandle == null) {
         if (StoreDebug.cacheDebug.isDebugEnabled()) {
            StoreDebug.cacheDebug.debug(this + " opening file: " + this.cacheFile);
         }

         this.fileHandle = ioNative.openConsiderLock(this.cacheFile.getAbsolutePath(), "rw", locked);
      }

      if (StoreDebug.cacheDebug.isDebugEnabled()) {
         StoreDebug.cacheDebug.debug(this + " creating mapping: " + this.fileHandle);
      }

      this.mappingHandle = ioNative.createMapping(this.fileHandle, this.mapLimit);
      if (size <= cacheableSize) {
         this.remapViewInit(0L);
      } else {
         for(long mapPos = cacheableSize; this.mapEnd < this.mapLimit; mapPos = this.mapEnd) {
            this.mapView(mapPos);
            int offset = (int)(mapPos - this.mapStart);
            ioNative.fillBuffer(this.directView, offset, this.directView.capacity() - offset, (byte)0);
            if (this.cacheFile != null) {
               long filePos = mapPos;

               while(filePos < this.mapEnd) {
                  int chunk1 = (int)Math.min((long)this.ioSize, this.mapEnd - filePos);
                  int chunk = (int)Math.min((long)chunk1, this.primaryDataLimit - filePos);
                  this.directView.limit(offset + chunk);
                  this.directView.position(offset);
                  int bytes = 0;

                  try {
                     bytes = this.primaryChannel.write(this.directView, filePos);
                     long filePosAfter = filePos + (long)bytes;
                     if (filePosAfter >= this.primaryDataLimit) {
                        break;
                     }

                     if (bytes < chunk) {
                        throw new EOFException("premature EOF: expected=" + (filePos + (long)chunk) + ", actual=" + filePosAfter);
                     }

                     filePos = filePosAfter;
                  } catch (IOException var16) {
                     ioNative.fillBuffer(this.directView, offset + bytes, chunk - bytes, (byte)0);
                     throw var16;
                  }
               }
            }
         }
      }

      this.open = true;
   }

   private void remapViewInit(long pos) throws IOException {
      this.mapView(pos);
      if (this.reinitialize) {
         this.initFromPrimary();
      }

   }

   public void initFromPrimary() throws IOException {
      long filePosAfter;
      for(long filePos = this.mapStart; filePos < this.mapEnd; filePos = filePosAfter) {
         int chunk = (int)Math.min((long)this.ioSize, this.mapEnd - filePos);
         this.directView.limit(this.directView.position() + chunk);
         int bytes = this.primaryChannel.read(this.directView, filePos);
         filePosAfter = filePos + (long)bytes;
         if (filePosAfter >= this.primaryDataLimit) {
            break;
         }

         if (bytes < chunk) {
            throw new EOFException("premature EOF: expected=" + (filePos + (long)chunk) + ", actual=" + filePosAfter);
         }
      }

   }

   private void mapView(long pos) throws IOException {
      this.unmapView();
      int viewSize = false;

      while(true) {
         try {
            int viewSize = (int)Math.min(this.mapLimit, (long)this.maxViewSize);
            this.mapStart = Math.min(this.mapLimit - (long)viewSize, pos / GRANULARITY * GRANULARITY);
            this.mapEnd = this.mapStart + (long)viewSize;
            this.directView = ioNative.mapFile(this.mappingHandle, this.mapStart, viewSize, this.prefetched);
            if (this.stats != null) {
               this.stats.addMappedBytes(viewSize);
            }

            return;
         } catch (OutOfMemoryError var6) {
            if (this.maxViewSize <= this.minMapSize) {
               IOException ioe = new IOException("mmap failed due to lack of resources");
               ioe.initCause(var6);
               throw ioe;
            }

            if (StoreDebug.cacheDebug.isDebugEnabled()) {
               StoreDebug.cacheDebug.debug("Configured cache view size " + this.maxViewSize + " is too large, trying 1/2, but too small views cause significant performance drop");
            }

            this.maxViewSize >>>= 1;
         }
      }
   }

   private void unmapView() throws IOException {
      if (this.directView != null) {
         try {
            if (this.stats != null) {
               this.stats.addMappedBytes(-this.directView.capacity());
            }

            ioNative.unmapFile(this.directView);
         } finally {
            this.directView = null;
         }
      }

   }

   private void checkOpen() throws IOException {
      if (!this.open) {
         throw new IOException(this + " is not open.");
      }
   }

   public ByteBuffer getMappedBuffer(long start, long end, boolean zeroCopyEnforced) throws IOException {
      this.checkOpen();
      if (start >= this.mapStart && end <= this.mapEnd) {
         this.directView.limit((int)(end - this.mapStart));
         this.directView.position((int)(start - this.mapStart));
         return this.directView.slice();
      } else {
         this.remapViewInit(start);
         if (this.mapEnd >= end) {
            this.directView.limit((int)(end - this.mapStart));
            this.directView.position((int)(start - this.mapStart));
            return this.directView.slice();
         } else if (zeroCopyEnforced) {
            this.directView.limit((int)(this.mapEnd - this.mapStart));
            this.directView.position((int)(start - this.mapStart));
            return this.directView.slice();
         } else {
            ByteBuffer copyBuffer = ByteBuffer.allocate((int)(end - start));

            ByteBuffer source;
            for(long pos = start; copyBuffer.hasRemaining(); pos += (long)source.capacity()) {
               source = this.getMappedBuffer(pos, pos + (long)copyBuffer.remaining(), true);
               copyBuffer.put(source);
            }

            copyBuffer.clear();
            return copyBuffer;
         }
      }
   }

   public void setInitialized() throws IOException {
      this.reinitialize = false;
   }

   public void commitClose(ByteBuffer buf) throws IOException {
      this.checkOpen();

      MessageDigest digest;
      try {
         digest = MessageDigest.getInstance("SHA-512");
      } catch (NoSuchAlgorithmException var5) {
         IOException ioEx = new IOException(var5.getMessage());
         ioEx.initCause(var5);
         throw ioEx;
      }

      ByteBuffer now = ByteBuffer.allocate(8);
      now.putLong(System.currentTimeMillis());
      now.clear();
      digest.update(now);
      digest.update(this.primaryFile.getAbsolutePath().getBytes());
      digest.update(InetAddress.getLocalHost().getAddress());
      byte[] sha512 = digest.digest(String.valueOf(this.primaryChannel.size()).getBytes());
      if (this.mapStart != 0L) {
         this.mapView(0L);
      }

      this.directView.limit(512);
      this.directView.position(30);
      this.directView.put(sha512);
      this.directView.position(0);
      buf.put(this.directView);
      this.unmapView();
      if (this.dualHandle()) {
         ioNative.close(this.mappingHandle);
         this.mappingHandle = null;
      }

      ioNative.force(this.fileHandle, true);
      buf.position(0);
      this.primaryChannel.write(buf, 0L);
   }

   public void close() throws IOException {
      this.closeInternal(false);
   }

   private void closeInternal(boolean reopen) throws IOException {
      IOException closeEx = null;

      try {
         if (StoreDebug.cacheDebug.isDebugEnabled() && this.directView != null) {
            StoreDebug.cacheDebug.debug(this + " unmapping view: " + this.directView);
         }

         try {
            this.unmapView();
         } catch (IOException var9) {
            closeEx = var9;
         }

         if (this.dualHandle()) {
            try {
               if (StoreDebug.cacheDebug.isDebugEnabled()) {
                  StoreDebug.cacheDebug.debug(this + " closing mapping: " + this.mappingHandle);
               }

               ioNative.close(this.mappingHandle);
            } catch (IOException var10) {
               if (closeEx == null) {
                  closeEx = var10;
               }
            }
         }

         if (!reopen && this.cacheFile != null && this.fileHandle != null) {
            if (StoreDebug.cacheDebug.isDebugEnabled()) {
               StoreDebug.cacheDebug.debug(this + " closing file: " + this.fileHandle);
            }

            try {
               ioNative.close(this.fileHandle);
            } catch (IOException var11) {
               if (closeEx == null) {
                  closeEx = var11;
               }
            }
         }

         if (closeEx != null) {
            if (this.openEx != null) {
               closeEx.initCause(this.openEx);
            }

            throw closeEx;
         }
      } finally {
         this.reinitFields(reopen);
      }

   }

   private boolean dualHandle() {
      return this.mappingHandle != null && this.mappingHandle != this.fileHandle;
   }

   private void reinitFields(boolean reopen) {
      if (!reopen) {
         this.maxViewSize = this.maxMapSize;
         this.cacheFile = null;
      }

      this.mapLimit = 0L;
      this.mapStart = 0L;
      this.mapEnd = 0L;
      this.directView = null;
      this.mappingHandle = null;
      if (!reopen) {
         this.fileHandle = null;
      }

      this.open = false;
      this.primaryDataLimit = 0L;
      this.prefetched = false;
      this.reinitialize = false;
      this.openEx = null;
      if (!reopen) {
         this.stats = null;
      }

   }

   public void setPrefetched(boolean b) {
      this.prefetched = b;
   }

   public void ensureReinit() {
      this.reinitialize = true;
   }

   public File getCacheFile() {
      return this.cacheFile;
   }

   public String toString() {
      return this.getClass().getName() + "[granularity=" + GRANULARITY + " io=" + (this.cacheFile == null ? "mapped" : "mappedAndDirect") + (this.cacheFile == null ? "" : " directory=" + this.cacheFile.getParent()) + "]";
   }

   public void reopen(long size, long cacheableSize) throws IOException {
      this.closeInternal(true);
      this.openInternal(size, cacheableSize, false);
   }

   public void reopenFlush(long size, long cacheableSize) throws IOException {
      this.closeInternal(true);
      this.primaryChannel.force(false);
      this.openInternal(size, cacheableSize, false);
   }

   static {
      GRANULARITY = ioNative.getMemoryMapGranularity();
   }
}
