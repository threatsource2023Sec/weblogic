package weblogic.store.io.file.direct;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import weblogic.store.common.StoreDebug;

public final class DirectFileChannel extends FileChannel {
   private final long handle;
   private final long onlyForLockHandle;
   private long size = -1L;
   private final IONative ioNative;

   protected DirectFileChannel(IONative ioNative, File file, String mode, boolean exclusive) throws IOException {
      this.onlyForLockHandle = -1L;
      this.ioNative = ioNative;
      this.handle = ioNative.openConsiderLock(file.getCanonicalPath(), mode, exclusive);
      if (this.handle < 0L) {
         throw new IOException("illegal handle = " + this.handle);
      }
   }

   protected DirectFileChannel(IONative ioNative, File file, String mode, boolean exclusive, String[] configurationKeys, String[] configurationValues) throws IOException {
      this.ioNative = ioNative;
      String canonicalPath = file.getCanonicalPath();
      DirectIONativeImpl fileSingleton = DirectIONativeImpl.singleton;
      if (ioNative == fileSingleton) {
         this.onlyForLockHandle = -1L;
      } else {
         this.onlyForLockHandle = fileSingleton.openConsiderLock(canonicalPath, mode, true);
         if (this.onlyForLockHandle < 0L) {
            throw new IOException("could not acquire lock file for " + canonicalPath);
         }
      }

      boolean wasError = true;

      try {
         String name = ioNative instanceof ReplicatedIONativeImpl ? file.getName().substring(0, file.getName().lastIndexOf(".")) : canonicalPath;
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StoreDebug.storeIOPhysicalVerbose.debug("About to call " + ioNative + ".openConsiderLock with file name:" + name);
         }

         long tmp = ioNative.openConsiderLock(name, mode, exclusive, configurationKeys, configurationValues);
         if (tmp < 0L) {
            throw new IOException("illegal handle = " + tmp);
         }

         this.handle = tmp;
         wasError = false;
      } finally {
         if (wasError) {
            this.closeLockHandle();
         }

      }

   }

   protected void implCloseChannel() throws IOException {
      IOException ioException = null;

      try {
         this.ioNative.close(this.handle);
      } catch (IOException var6) {
         ioException = var6;
      } finally {
         this.closeLockHandle();
         if (ioException != null) {
            throw ioException;
         }

      }

   }

   public long getHandle() {
      return this.handle;
   }

   private void closeLockHandle() throws IOException {
      if (this.onlyForLockHandle >= 0L) {
         DirectIONativeImpl.getSingleton().close(this.onlyForLockHandle);
      }
   }

   public long position() throws IOException {
      throw new UnsupportedOperationException();
   }

   public FileChannel position(long newPosition) throws IOException {
      throw new UnsupportedOperationException();
   }

   public synchronized long resetSizeAfterExpansion() throws IOException {
      return this.size = this.ioNative.getSize(this.handle);
   }

   public long getDeviceLimit() throws IOException {
      this.checkClosed();
      return this.ioNative.getDeviceLimit(this.handle);
   }

   public long getDeviceUsed() throws IOException {
      this.checkClosed();
      return this.ioNative.getDeviceUsed(this.handle);
   }

   public synchronized long size() throws IOException {
      this.checkClosed();
      if (this.size < 0L) {
         this.size = this.ioNative.getSize(this.handle);
      }

      return this.size;
   }

   public synchronized FileChannel truncate(long newSize) throws IOException {
      this.checkClosed();
      this.ioNative.truncate(this.handle, newSize);
      this.size = newSize;
      return this;
   }

   public void force(boolean metaData) throws IOException {
      this.ioNative.force(this.handle, metaData);
   }

   public int read(ByteBuffer buf) throws IOException {
      throw new UnsupportedOperationException();
   }

   public synchronized int read(ByteBuffer buf, long readPosition) throws IOException {
      this.checkClosed();
      if (readPosition >= this.size) {
         return -1;
      } else {
         int chunk = (int)Math.min((long)buf.remaining(), this.size - readPosition);
         if (chunk == 0) {
            return 0;
         } else {
            this.checkDirectBuffer(buf);
            boolean success = false;
            this.begin();

            int var7;
            try {
               int bytesRead = this.ioNative.read(this.handle, readPosition, buf, buf.position(), chunk);
               buf.position(buf.position() + bytesRead);
               success = true;
               var7 = bytesRead;
            } finally {
               this.end(success);
            }

            return var7;
         }
      }
   }

   public int write(ByteBuffer buf) throws IOException {
      throw new UnsupportedOperationException();
   }

   public synchronized int write(ByteBuffer buf, long writePosition) throws IOException {
      this.checkClosed();
      this.checkDirectBuffer(buf);
      boolean success = false;
      this.begin();

      int var8;
      try {
         int bytesWritten = this.ioNative.write(this.handle, writePosition, buf, buf.position(), buf.remaining());
         buf.position(buf.position() + bytesWritten);
         long endOfWrite = writePosition + (long)bytesWritten;
         if (endOfWrite > this.size) {
            this.size = endOfWrite;
         }

         success = true;
         var8 = bytesWritten;
      } finally {
         this.end(success);
      }

      return var8;
   }

   public long read(ByteBuffer[] bufs, int offset, int length) {
      throw new UnsupportedOperationException();
   }

   public long write(ByteBuffer[] srcs, int offset, int length) {
      throw new UnsupportedOperationException();
   }

   public MappedByteBuffer map(FileChannel.MapMode mode, long position, long size) {
      throw new UnsupportedOperationException();
   }

   public long transferTo(long position, long count, WritableByteChannel target) {
      throw new UnsupportedOperationException();
   }

   public long transferFrom(ReadableByteChannel src, long position, long count) {
      throw new UnsupportedOperationException();
   }

   public FileLock lock(long position, long size, boolean shared) {
      throw new UnsupportedOperationException();
   }

   public FileLock tryLock(long position, long size, boolean shared) {
      throw new UnsupportedOperationException();
   }

   private void checkClosed() throws ClosedChannelException {
      if (!this.isOpen()) {
         throw new ClosedChannelException();
      }
   }

   private void checkDirectBuffer(ByteBuffer buf) {
      if (!buf.isDirect()) {
         throw new IllegalArgumentException();
      }
   }
}
