package weblogic.store.io.file.direct;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import weblogic.store.common.StoreRCMUtils;

final class DirectIONativeImpl implements IONative {
   static DirectIONativeImpl singleton = new DirectIONativeImpl();

   private DirectIONativeImpl() {
   }

   static IONative getSingleton() {
      return singleton;
   }

   public int checkAlignment(String testFileName) throws IOException {
      return DirectIONative.checkAlignment(testFileName);
   }

   public ByteBuffer allocate(int size) {
      return DirectIONative.allocate(size);
   }

   public void free(ByteBuffer buf) {
      DirectIONative.free(buf);
   }

   public long openConsiderLock(final String fileName, final String mode, final boolean exclusive) throws IOException {
      long ret = -1L;

      try {
         class OpenConsiderLockCallable implements Callable {
            public Long call() throws IOException {
               return DirectIONative.openConsiderLock(fileName, mode, exclusive);
            }
         }

         ret = (Long)StoreRCMUtils.accountAsGlobal((Callable)(new OpenConsiderLockCallable()));
      } catch (Exception var7) {
         StoreRCMUtils.throwIOorRuntimeException(var7);
      }

      return ret;
   }

   public long openConsiderLock(String fileName, String mode, boolean exclusive, String[] configurationKeys, String[] configurationValues) throws IOException {
      IOException ioe = new IOException("not avail for file store");
      throw ioe;
   }

   public long openBasic(final String fileName, final String mode) throws IOException {
      long ret = -1L;

      try {
         class OpenBasicCallable implements Callable {
            public Long call() throws IOException {
               return DirectIONative.open(fileName, mode);
            }
         }

         ret = (Long)StoreRCMUtils.accountAsGlobal((Callable)(new OpenBasicCallable()));
      } catch (Exception var6) {
         StoreRCMUtils.throwIOorRuntimeException(var6);
      }

      return ret;
   }

   public long openEnhanced(String fileName, String mode, String[] configurationKeys, String[] configurationValues) throws IOException {
      IOException ioe = new IOException("not avail for file store");
      throw ioe;
   }

   public void close(final long handle) throws IOException {
      try {
         class CloseRunnable implements Runnable {
            public void run() {
               try {
                  DirectIONative.close(handle);
               } catch (IOException var2) {
                  throw new RuntimeException(var2);
               }
            }
         }

         StoreRCMUtils.accountAsGlobal((Runnable)(new CloseRunnable()));
      } catch (Exception var4) {
         StoreRCMUtils.throwIOorRuntimeException(var4);
      }

   }

   public long getSize(long handle) throws IOException {
      return DirectIONative.getSize(handle);
   }

   public long getDeviceLimit(long handle) throws IOException {
      return Long.MAX_VALUE;
   }

   public long getDeviceUsed(long handle) throws IOException {
      return 0L;
   }

   public void truncate(long handle, long newSize) throws IOException {
      DirectIONative.truncate(handle, newSize);
   }

   public int read(long handle, long filePosition, ByteBuffer buf, int bufPosition, int bufLength) throws IOException {
      return DirectIONative.read(handle, filePosition, buf, bufPosition, bufLength);
   }

   public int write(long handle, long filePosition, ByteBuffer buf, int bufPosition, int bufLength) throws IOException {
      return DirectIONative.write(handle, filePosition, buf, bufPosition, bufLength);
   }

   public void force(long handle, boolean metaData) throws IOException {
      DirectIONative.force(handle, metaData);
   }

   public long createMapping(long fileHandle, long size) throws IOException {
      return DirectIONative.createMapping(fileHandle, size);
   }

   public ByteBuffer mapFile(long handle, long offset, int size, boolean prefetched) throws IOException {
      return DirectIONative.mapFile(handle, offset, size, prefetched);
   }

   public void unmapFile(ByteBuffer buf) throws IOException {
      DirectIONative.unmapFile(buf);
   }

   public long getMemoryMapGranularity() {
      return DirectIONative.getMemoryMapGranularity();
   }

   public void fillBuffer(ByteBuffer buf, int position, int size, byte value) {
      DirectIONative.fillBuffer(buf, position, size, value);
   }
}
