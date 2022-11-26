package weblogic.store.io.file.direct;

import java.io.IOException;
import java.nio.ByteBuffer;

class ReplicatedIONative {
   static native int checkAlignment(String var0) throws IOException;

   static native ByteBuffer allocate(int var0);

   static native void free(ByteBuffer var0);

   static native long openConsiderLock(String var0, String var1, boolean var2, String[] var3, String[] var4) throws IOException;

   static native long open(String var0, String var1, String[] var2, String[] var3) throws IOException;

   static native void close(long var0) throws IOException;

   static native long getSize(long var0) throws IOException;

   static native long getDeviceLimit(long var0) throws IOException;

   static native long getDeviceUsed(long var0) throws IOException;

   static native void truncate(long var0, long var2) throws IOException;

   static native int read(long var0, long var2, ByteBuffer var4, int var5, int var6) throws IOException;

   static native int write(long var0, long var2, ByteBuffer var4, int var5, int var6) throws IOException;

   static native void force(long var0, boolean var2) throws IOException;

   static native long createMapping(long var0, long var2) throws IOException;

   static native ByteBuffer mapFile(long var0, long var2, int var4, boolean var5) throws IOException;

   static native void unmapFile(ByteBuffer var0) throws IOException;

   static native long getMemoryMapGranularity();

   static native void fillBuffer(ByteBuffer var0, int var1, int var2, byte var3);
}
