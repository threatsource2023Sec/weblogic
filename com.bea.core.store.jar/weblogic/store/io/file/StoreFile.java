package weblogic.store.io.file;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Callable;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreLogger;
import weblogic.store.StoreWritePolicy;
import weblogic.store.common.StoreDebug;
import weblogic.store.common.StoreRCMUtils;
import weblogic.store.internal.StoreStatisticsImpl;
import weblogic.store.io.file.direct.DirectFileChannel;
import weblogic.store.io.file.direct.DirectIOManager;
import weblogic.store.io.file.direct.FileMapping;

public final class StoreFile {
   private static final boolean veryVerboseDebug = System.getProperty("weblogic.store.DebugVerboseFileIO") != null;
   private final StoreDir dir;
   private final Heap heap;
   private final File file;
   private final DirectBufferPool bufferPool;
   private final short fileNum;
   private StoreWritePolicy writePolicy;
   private FileChannel readChannel;
   private FileChannel writeChannel;
   private FileMapping cache;
   private long fileSize;
   private boolean open;
   private String ioMode;
   int ioSize;
   int minMapSize;
   int maxMapSize;
   boolean locking;
   boolean enforceExplicitIO;
   StoreStatisticsImpl stats;
   private static final boolean DEBUG_SPACE_UPDATES = Boolean.getBoolean("weblogic.store.DebugSpaceUpdates");

   StoreFile(Heap heap, StoreDir dir, File file, short fileNum, DirectBufferPool bufferPool) {
      this.heap = heap;
      this.file = file;
      this.fileNum = fileNum;
      this.dir = dir;
      this.bufferPool = bufferPool;
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("StoreFile construct {dir.getDirName " + dir.getDirName() + " dir.absolute " + dir.getDirAbsolutePath() + "} { file.getAbsolutePath() " + file.getAbsolutePath() + " }");
      }

   }

   private void reinitFields() {
      this.writePolicy = null;
      this.readChannel = null;
      this.writeChannel = null;
      this.cache = null;
      this.fileSize = 0L;
      this.open = false;
      this.ioMode = null;
      this.ioSize = 0;
      this.minMapSize = 0;
      this.maxMapSize = 0;
      this.locking = false;
      this.stats = null;
      this.enforceExplicitIO = false;
   }

   StoreWritePolicy getWritePolicy() {
      return this.writePolicy;
   }

   private static void throwIOException(Throwable linked) throws IOException {
      IOException ioe = new IOException(linked.toString());
      ioe.initCause(linked);
      throw ioe;
   }

   private void openInternal(boolean directIO, boolean diabloDualHandleDirectMode) throws IOException {
      try {
         StoreDir.checkFile(this.file);
         if (this.writePolicy.synchronous()) {
            if (directIO) {
               this.writeChannel = this.fileChannelFactory(this.file, "rwD", this.locking);
               this.readChannel = this.writeChannel;
               this.ioMode = "single-handle-unbuffered";
               if (diabloDualHandleDirectMode) {
                  try {
                     this.readChannel = this.fileChannelFactory(this.file, "r", false);
                     this.ioMode = "read-buffered";
                  } catch (IOException var4) {
                     StoreLogger.logDualHandleOpenFailed(this.file.getPath(), var4);
                  }
               }
            } else {
               this.writeChannel = this.fileChannelFactory(this.file, "rwd", this.locking);
               this.readChannel = this.writeChannel;
               this.ioMode = "single-handle-buffered";
            }
         } else {
            this.writeChannel = this.fileChannelFactory(this.file, "rw", this.locking);
            this.readChannel = this.writeChannel;
            this.ioMode = "single-handle-non-direct";
         }
      } catch (SecurityException var5) {
         throwIOException(var5);
      }

      this.fileSize = this.readChannel.size();
      if (this.mapped()) {
         this.cache = new FileMapping(this.writeChannel, this.ioSize, this.minMapSize, this.maxMapSize, this.stats, this.file);
      }

      this.open = true;
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("Opened file name=" + this.file.getName() + " size=" + this.fileSize + " directIO=" + directIO + " singleHandle=" + (this.writeChannel == this.readChannel));
      }

   }

   FileChannel fileChannelFactory(File file, String mode, boolean exclusive) throws IOException {
      return this.heap == null ? FileStoreIO.staticOpen(this.heap, file, mode, exclusive) : this.heap.fileChannelFactory(file, mode, exclusive);
   }

   void open(StoreWritePolicy synchronousWritePolicy) throws IOException {
      this.writePolicy = synchronousWritePolicy;
      this.openInternal(false, false);
   }

   void openDirect(StoreWritePolicy synchronousWritePolicy) throws IOException {
      this.writePolicy = synchronousWritePolicy;
      this.openInternal(true, true);
   }

   void openSingleHandleDirect(StoreWritePolicy synchronousWritePolicy) throws IOException {
      this.writePolicy = synchronousWritePolicy;
      this.openInternal(true, false);
   }

   void adjustFileSize(int blockSize) throws IOException {
      if (blockSize != 0 && this.fileSize % (long)blockSize != 0L) {
         long newFileSize = this.fileSize / (long)blockSize * (long)blockSize;
         if (this.mapped()) {
            this.cache.reopen(newFileSize, this.fileSize);
         }

         this.fileSize = newFileSize;
         this.writeChannel.truncate(this.fileSize);
      }

      if (this.dir != null) {
         this.dir.incBytesUsed(this.fileSize);
      }

   }

   private long writeBuffer(ByteBuffer directBuffer, ByteBuffer data, long filePos) throws IOException {
      long curPos = filePos;
      int dataLimit = data.limit();

      while(data.position() < dataLimit) {
         int chunk = Math.min(dataLimit - data.position(), directBuffer.remaining());
         data.limit(data.position() + chunk);
         directBuffer.put(data);
         if (!directBuffer.hasRemaining()) {
            directBuffer.position(0);
            this.writeChannel.write(directBuffer, curPos);
            directBuffer.position(0);
            curPos += (long)directBuffer.capacity();
         }
      }

      return curPos;
   }

   void write(long filePos, ByteBuffer data) throws IOException {
      if (veryVerboseDebug && StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("Writing to file filePos=" + filePos + " len=" + data.remaining());
      }

      if (this.hasCacheFile()) {
         int dataPosition = data.position();
         int dataRemaining = data.remaining();
         int bytes = 0;

         try {
            bytes = this.writeChannel.write(data, filePos);
            if (bytes < dataRemaining) {
               throw new EOFException("Write incomplete: expected=" + dataRemaining + ", actual=" + bytes);
            }
         } catch (IOException var12) {
            data.position(dataPosition + bytes);
            DirectIOManager.getFileMemoryManager().zeroBuffer(data, dataRemaining - bytes);
            throw var12;
         }
      } else if (data.isDirect()) {
         this.writeChannel.write(data, filePos);
      } else {
         ByteBuffer directBuffer = this.bufferPool.get();

         try {
            long curPos = this.writeBuffer(directBuffer, data, filePos);
            if (directBuffer.position() > 0) {
               directBuffer.flip();
               this.writeChannel.write(directBuffer, curPos);
            }
         } finally {
            this.bufferPool.put(directBuffer);
         }
      }

   }

   void read(long filePos, ByteBuffer buf) throws IOException {
      if (veryVerboseDebug && StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("Reading from file filePos=" + filePos + " len=" + buf.remaining());
      }

      int origPos = buf.position();
      if (buf.isDirect()) {
         int lengthToRead = buf.remaining();
         int count = this.readChannel.read(buf, filePos);
         if (count < lengthToRead) {
            throw new EOFException("premature EOF: expected=" + (filePos + (long)lengthToRead) + ", actual=" + (filePos + (long)count));
         }
      } else {
         ByteBuffer directBuffer = this.bufferPool.get();

         int chunk;
         try {
            for(long curPos = filePos; buf.hasRemaining(); curPos += (long)chunk) {
               directBuffer.clear();
               chunk = Math.min(buf.remaining(), directBuffer.remaining());
               directBuffer.limit(chunk);
               int count = this.readChannel.read(directBuffer, curPos);
               if (count < chunk) {
                  throw new EOFException("premature EOF: expected=" + (curPos + (long)chunk) + ", actual=" + (curPos + (long)count));
               }

               directBuffer.flip();
               buf.put(directBuffer);
            }
         } finally {
            this.bufferPool.put(directBuffer);
         }
      }

      buf.position(origPos);
   }

   int readBulk(long filePos, ByteBuffer buf, int minBytes) throws IOException {
      ByteBuffer directBuf = buf.isDirect() ? buf : this.bufferPool.get();
      int expected = Math.min(minBytes, directBuf.remaining());

      int var8;
      try {
         int count = this.readChannel.read(directBuf, filePos);
         if (count < expected) {
            throw new EOFException("premature EOF: expected=" + (filePos + (long)expected) + ", actual=" + (filePos + (long)count));
         }

         if (count > 0) {
            directBuf.limit(directBuf.position());
            directBuf.position(directBuf.position() - count);
            if (!buf.isDirect()) {
               buf.put(directBuf);
               buf.limit(buf.position());
               buf.position(buf.position() - count);
            }
         }

         var8 = count;
      } finally {
         if (!buf.isDirect()) {
            this.bufferPool.put(directBuf);
         }

      }

      return var8;
   }

   synchronized void expand(long amt) throws IOException {
      try {
         long pos;
         if (this.mapped()) {
            pos = this.fileSize + amt;
            this.cache.reopen(pos, this.fileSize);
         } else {
            pos = this.fileSize;
            long expandAmt = amt;
            ByteBuffer tmpBuf = this.bufferPool.get();

            try {
               DirectIOManager.getFileMemoryManager().zeroBuffer(tmpBuf);

               do {
                  tmpBuf.clear();
                  int chunk = (int)Math.min((long)tmpBuf.capacity(), expandAmt);
                  tmpBuf.limit(chunk);
                  this.write(pos, tmpBuf);
                  pos += (long)chunk;
                  expandAmt -= (long)chunk;
               } while(expandAmt > 0L);
            } finally {
               this.bufferPool.put(tmpBuf);
            }
         }

         if (!this.writePolicy.unforced()) {
            this.writeChannel.force(true);
         }

         if (this.readChannel != this.writeChannel) {
            ((DirectFileChannel)this.readChannel).resetSizeAfterExpansion();
         }

         this.fileSize += amt;
         if (this.dir != null) {
            this.dir.incBytesUsed(amt);
         }

         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StoreDebug.storeIOPhysicalVerbose.debug("expand() amt=" + amt + " new fileSize=" + this.fileSize);
         }

      } catch (IOException var27) {
         try {
            this.writeChannel.truncate(this.fileSize);
            if (this.mapped()) {
               this.cache.reopen(this.fileSize, this.fileSize);
            }

            if (!this.writePolicy.unforced()) {
               this.writeChannel.force(true);
            }
         } finally {
            ;
         }

         throw var27;
      } catch (OutOfMemoryError var28) {
         try {
            this.writeChannel.truncate(this.fileSize);
            if (this.writePolicy != StoreWritePolicy.DISABLED && this.writePolicy != StoreWritePolicy.NON_DURABLE) {
               this.writeChannel.force(true);
            }
         } catch (IOException var23) {
         } catch (OutOfMemoryError var24) {
         }

         throw new IOException(var28.toString());
      }
   }

   synchronized long size() {
      return this.fileSize;
   }

   short getFileNum() {
      return this.fileNum;
   }

   public String getName() {
      return this.file.getName();
   }

   boolean checkFileBytesQuota(long amt, long bytesQuota) {
      return this.fileSize + amt <= bytesQuota;
   }

   synchronized void flush() throws IOException {
      if (this.writePolicy == StoreWritePolicy.CACHE_FLUSH) {
         this.writeChannel.force(false);
      }

   }

   synchronized void close() throws IOException {
      IOException ioEx = null;

      try {
         if (this.mapped()) {
            try {
               if (StoreDebug.cacheDebug.isDebugEnabled()) {
                  StoreDebug.cacheDebug.debug("closing cache: " + this.cache);
               }

               if (this.cache != null) {
                  this.cache.close();
               }
            } catch (IOException var8) {
               if (StoreDebug.cacheDebug.isDebugEnabled()) {
                  StoreDebug.cacheDebug.debug("closing cache failed: " + this.cache, var8);
               }

               if (ioEx == null) {
                  ioEx = var8;
               }
            }
         }

         try {
            IOException tIoe = null;

            class CloseChannels implements Callable {
               public IOException call() {
                  IOException ioe = null;

                  try {
                     if (StoreFile.this.writeChannel != null) {
                        StoreFile.this.writeChannel.close();
                     }
                  } catch (IOException var4) {
                     if (ioe == null) {
                        ioe = var4;
                     }
                  }

                  try {
                     if (StoreFile.this.readChannel != null && StoreFile.this.writeChannel != StoreFile.this.readChannel) {
                        StoreFile.this.readChannel.close();
                     }
                  } catch (IOException var3) {
                     if (ioe == null) {
                        ioe = var3;
                     }
                  }

                  return ioe;
               }
            }

            tIoe = (IOException)StoreRCMUtils.accountAsGlobal((Callable)(new CloseChannels()));
            if (tIoe != null && ioEx == null) {
               ioEx = tIoe;
            }
         } catch (Exception var7) {
            StoreRCMUtils.throwIOorRuntimeException(var7);
         }

         if (ioEx != null) {
            throw ioEx;
         }
      } finally {
         this.reinitFields();
      }

   }

   void rename(File newName) {
      this.file.renameTo(newName);
   }

   public boolean equals(Object obj) {
      return obj instanceof StoreFile && ((StoreFile)obj).fileNum == this.fileNum;
   }

   public int hashCode() {
      return this.fileNum;
   }

   public String toString() {
      return this.file.getAbsolutePath();
   }

   String getIOMode() {
      return this.ioMode;
   }

   String getDriver() {
      return this.heap.getDriver();
   }

   ByteBuffer mappedRead(long position, int size) throws PersistentStoreException {
      try {
         return this.cache.getMappedBuffer(position, position + (long)size, false);
      } catch (IOException var5) {
         throw new PersistentStoreException(var5);
      }
   }

   ByteBuffer mappedRecoveryRead(long position, int size) throws PersistentStoreException {
      ByteBuffer var4;
      try {
         this.cache.setPrefetched(true);
         var4 = this.cache.getMappedBuffer(position, position + (long)size, false);
      } catch (IOException var8) {
         throw new PersistentStoreException(var8);
      } finally {
         this.cache.setPrefetched(false);
      }

      return var4;
   }

   Heap.HeapFileHeader mappedRecoveryInit(String storeName) throws IOException {
      long minSize = Math.max(this.fileSize, 8192L);
      this.cache.open(minSize, minSize, (String)null, false);
      ByteBuffer cacheHeaderBuf = this.cache.getMappedBuffer(0L, 512L, true);
      if (this.fileNum == 0) {
         StoreLogger.logCacheInfo(storeName, this.cache.toString());
      }

      return new Heap.HeapFileHeader(cacheHeaderBuf);
   }

   void commitScan(short version, int blockSize, long uuidLo, long uuidHi, long heapRecordMagic) throws PersistentStoreException {
      ByteBuffer headerBuf = null;

      try {
         if (this.mapped()) {
            headerBuf = this.getDirectMappedBuffer(0L, (long)blockSize);
            if (this.hasCacheFile()) {
               this.cache.setInitialized();
            }
         } else {
            headerBuf = this.bufferPool.get();
            headerBuf.limit(blockSize);
            this.read(0L, headerBuf);
            headerBuf.position(0);
         }

         Heap.HeapFileHeader currentHeader = new Heap.HeapFileHeader(headerBuf);
         headerBuf.position(0);
         if (StoreDebug.cacheDebug.isDebugEnabled()) {
            StoreDebug.cacheDebug.debug("Checking current header: " + currentHeader);
         }

         if (!currentHeader.magicVerified || currentHeader.signatureZero() && currentHeader.uuidLo == uuidLo) {
            return;
         }

         Heap.HeapFileHeader header = new Heap.HeapFileHeader(version, blockSize, uuidLo, uuidHi, heapRecordMagic);
         headerBuf.position(0);
         header.serialize(headerBuf);
         if (StoreDebug.cacheDebug.isDebugEnabled()) {
            StoreDebug.cacheDebug.debug("Replacing with new header: " + header + " serialized into " + headerBuf);
         }

         if (!this.hasCacheFile() && this.mapped()) {
            this.cache.reopenFlush(this.fileSize, this.fileSize);
         } else {
            this.writeChannel.write(headerBuf, 0L);
            this.flush();
         }
      } catch (IOException var15) {
         throw new PersistentStoreException(var15);
      } finally {
         if (!this.mapped() && headerBuf != null) {
            this.bufferPool.put(headerBuf);
         }

      }

   }

   void commitClose(int blockSize) throws IOException {
      ByteBuffer buf = this.bufferPool.get();

      try {
         buf.limit(blockSize);
         DirectIOManager.getFileMemoryManager().zeroBuffer(buf);
         buf.position(0);
         this.cache.commitClose(buf);
      } finally {
         this.bufferPool.put(buf);
      }

   }

   boolean mapped() {
      return !this.enforceExplicitIO && (DirectIOManager.getMemMapManager().nativeFileCodeAvailable() && this.writePolicy.genuineMemoryMap() || this.hasCacheFile());
   }

   boolean hasCacheFile() {
      return this.writePolicy.usesCacheFile() && this.ioMode == "single-handle-unbuffered";
   }

   ByteBuffer getDirectMappedBuffer(long start, long end) throws IOException {
      return this.cache.getMappedBuffer(start, end, true);
   }

   void openCacheFile(String dir, String storeName) throws IOException {
      long minSize = Math.max(this.fileSize, 8192L);
      this.cache.open(minSize, minSize, dir, this.locking);
      if (this.fileNum == 0) {
         StoreLogger.logCacheInfo(storeName, this.cache.toString());
      }

   }

   void verifyCacheFile(Heap.HeapFileHeader primaryHeader, String storeName) throws IOException {
      ByteBuffer cacheHeaderBuf = this.cache.getMappedBuffer(0L, 512L, true);
      Heap.HeapFileHeader cacheHeader = new Heap.HeapFileHeader(cacheHeaderBuf);
      if (primaryHeader.signatureZero() || !cacheHeader.equalsTo(primaryHeader)) {
         if (primaryHeader.uuidLo != 0L) {
            StoreLogger.logCacheSignatureVerificationFailed(storeName, this.file.toString(), this.cache.getCacheFile().toString());
         }

         this.cache.ensureReinit();
         this.cache.initFromPrimary();
      }

   }

   File getCacheFile() {
      return this.cache.getCacheFile();
   }

   void flush0() throws IOException {
      if (this.mapped()) {
         if (!this.hasCacheFile()) {
            this.cache.reopenFlush(this.fileSize, this.fileSize);
         }
      } else if (this.writePolicy.unforced() || this.writePolicy == StoreWritePolicy.CACHE_FLUSH) {
         this.writeChannel.force(false);
      }

   }

   void updateStats(StoreStatisticsImpl stats, long maxFileSize) throws IOException {
      if (this.writeChannel instanceof DirectFileChannel) {
         DirectFileChannel dfc = (DirectFileChannel)this.writeChannel;
         long limit = dfc.getDeviceLimit();
         long used = dfc.getDeviceUsed();
         if (limit <= 0L || used < 0L) {
            if (DEBUG_SPACE_UPDATES) {
               System.out.println("*** STORE DEBUG SPACE device limit/used/percent " + limit + "/" + used + "/" + 0);
            }

            stats.setDeviceUsedPercent(0);
            return;
         }

         long percent = used * 100L / limit;
         if (percent > 100L) {
            percent = 100L;
         }

         if (DEBUG_SPACE_UPDATES) {
            System.out.println("*** STORE DEBUG SPACE heap= " + this.heap.getName() + " device limit/used/percent" + limit + "/" + used + "/" + percent);
         }

         stats.setDeviceUsedPercent((int)percent);
         long oneRegionPercent = 100L * maxFileSize / limit;
         stats.setOneRegionPercent((int)oneRegionPercent);
      } else {
         stats.setDeviceUsedPercent(0);
         stats.setOneRegionPercent(0);
      }

   }
}
