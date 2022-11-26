package weblogic.store.io.file.direct;

import java.io.IOException;
import java.nio.ByteBuffer;
import weblogic.store.PersistentStoreException;
import weblogic.store.common.StoreDebug;

public final class ReplicatedIONativeImpl implements IONative {
   private static final String REP_STORE_LIB_NAME = "wlrepstore1";
   private static ReplicatedIONativeImpl singleton = new ReplicatedIONativeImpl();
   private static final DirectIOManager managerSingleton;
   private static final Object bigLock;
   private static final int HANDLE_NATIVE_SIZE = 10000;
   private static final Object handleLock;
   private static int handleCircularIndex;
   private static final long[] handleAvailableTime;
   private static final String[] handleName;
   private static final long HANDLE_DRAIN_TIME = 90000L;

   private ReplicatedIONativeImpl() {
   }

   public static int allocateNativeHandle(String name) throws IOException {
      if (name == null) {
         throw new IOException("attempt to reserve handle without a name");
      } else {
         long now = System.currentTimeMillis();
         synchronized(handleLock) {
            for(int lcv = 0; lcv < 10000; ++lcv) {
               handleCircularIndex = (handleCircularIndex + 1) % 10000;
               int handle = handleCircularIndex;
               if (handleName[handle] == null && (handleAvailableTime[handle] == 0L || now >= handleAvailableTime[handle])) {
                  handleName[handle] = name;
                  return handle;
               }
            }

            throw new IOException("could not allocate for " + name);
         }
      }
   }

   public static void freeNativeHandle(long longHandle) throws IOException {
      int handle = (int)longHandle;
      synchronized(handleLock) {
         if (handleName[handle] == null) {
            throw new IOException("free of free handle=" + handle);
         } else {
            handleAvailableTime[handle] = System.currentTimeMillis() + 90000L;
            handleName[handle] = null;
         }
      }
   }

   private static Object getLockInstance() {
      return bigLock;
   }

   private static Object getLockInstance(long handle) {
      return bigLock;
   }

   public static DirectIOManager getDirectIOManagerSingletonPersistentStoreException() throws PersistentStoreException {
      return managerSingleton.checkNativePersistentStoreException();
   }

   public static DirectIOManager getDirectIOManagerSingletonIOException() throws IOException {
      return managerSingleton.checkNativeIOException();
   }

   public static void loadReplicatedLib() {
   }

   public int checkAlignment(String testFileName) throws IOException {
      synchronized(getLockInstance()) {
         return DirectIONativeImpl.getSingleton().checkAlignment(testFileName);
      }
   }

   public ByteBuffer allocate(int size) {
      synchronized(getLockInstance()) {
         return DirectIONativeImpl.getSingleton().allocate(size);
      }
   }

   public void free(ByteBuffer buf) {
      synchronized(getLockInstance()) {
         ReplicatedIONative.free(buf);
      }
   }

   public long openConsiderLock(String fileName, String mode, boolean exclusive) throws IOException {
      IOException ioe = new IOException("not avail for replicated store");
      throw ioe;
   }

   public long openConsiderLock(String fileName, String mode, boolean exclusive, String[] configurationKeys, String[] configurationValues) throws IOException {
      if (configurationKeys != null && configurationValues != null) {
         synchronized(getLockInstance()) {
            return ReplicatedIONative.openConsiderLock(fileName, mode, exclusive, configurationKeys, configurationValues);
         }
      } else {
         IOException ioe = new IOException("not avail for replicated store");
         throw ioe;
      }
   }

   public long openBasic(String fileName, String mode) throws IOException {
      IOException ioe = new IOException("not avail for replicated store");
      throw ioe;
   }

   public long openEnhanced(String fileName, String mode, String[] configurationKeys, String[] configurationValues) throws IOException {
      if (configurationKeys != null && configurationValues != null) {
         synchronized(getLockInstance()) {
            return ReplicatedIONative.open(fileName, mode, configurationKeys, configurationValues);
         }
      } else {
         IOException ioe = new IOException("not avail for replicated store");
         throw ioe;
      }
   }

   private static void checkHandle(long handle) throws IOException {
      if (handle >= 10000L || handle < 0L) {
         IOException ioe = new IOException("illegal handle=" + handle);
         throw ioe;
      }
   }

   public void close(long handle) throws IOException {
      try {
         checkHandle(handle);
         synchronized(getLockInstance(handle)) {
            ReplicatedIONative.close(handle);
         }
      } catch (IOException var10) {
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StoreDebug.storeIOPhysicalVerbose.debug("ReplicatedIONativeImpl close unthrown ", var10);
         }

         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("ReplicatedIONativeImpl close unthrown ", var10);
         }
      } finally {
         if (handle >= 0L && handle < 10000L) {
            freeNativeHandle(handle);
         }

      }

   }

   public long getSize(long handle) throws IOException {
      checkHandle(handle);
      synchronized(getLockInstance(handle)) {
         return ReplicatedIONative.getSize(handle);
      }
   }

   public long getDeviceLimit(long handle) throws IOException {
      checkHandle(handle);
      synchronized(getLockInstance(handle)) {
         return ReplicatedIONative.getDeviceLimit(handle);
      }
   }

   public long getDeviceUsed(long handle) throws IOException {
      checkHandle(handle);
      synchronized(getLockInstance(handle)) {
         return ReplicatedIONative.getDeviceUsed(handle);
      }
   }

   public void truncate(long handle, long newSize) throws IOException {
      checkHandle(handle);
      ReplicatedIONative.truncate(handle, newSize);
   }

   public int read(long handle, long filePosition, ByteBuffer buf, int bufPosition, int bufLength) throws IOException {
      checkHandle(handle);
      synchronized(getLockInstance(handle)) {
         return ReplicatedIONative.read(handle, filePosition, buf, bufPosition, bufLength);
      }
   }

   public int write(long handle, long filePosition, ByteBuffer buf, int bufPosition, int bufLength) throws IOException {
      checkHandle(handle);
      synchronized(getLockInstance(handle)) {
         return ReplicatedIONative.write(handle, filePosition, buf, bufPosition, bufLength);
      }
   }

   public void force(long handle, boolean metaData) throws IOException {
   }

   public long createMapping(long fileHandle, long size) throws IOException {
      throw new IOException("ReplicatedIONativeImpl createMapping handle=" + fileHandle);
   }

   public ByteBuffer mapFile(long handle, long offset, int size, boolean prefetched) throws IOException {
      throw new IOException("ReplicatedIONativeImpl mapFile handle=" + handle + ", offset=" + offset + ", size=" + size + ", prefetched=" + prefetched);
   }

   public void unmapFile(ByteBuffer buf) throws IOException {
      throw new IOException("ReplicatedIONativeImpl unmapFile ");
   }

   public long getMemoryMapGranularity() {
      synchronized(getLockInstance()) {
         return ReplicatedIONative.getMemoryMapGranularity();
      }
   }

   public void fillBuffer(ByteBuffer buf, int position, int size, byte value) {
      DirectIONativeImpl.getSingleton().fillBuffer(buf, position, size, value);
   }

   static {
      managerSingleton = new DirectIOManager(true, "wlrepstore1", singleton);
      bigLock = new Object();
      handleLock = new Object();
      handleCircularIndex = 9998;
      handleAvailableTime = new long[10000];
      handleName = new String[10000];
   }
}
