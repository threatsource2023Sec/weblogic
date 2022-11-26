package weblogic.store.io.file.direct;

import java.io.IOException;
import java.nio.ByteBuffer;

interface DirectDataIONative {
   int checkAlignment(String var1) throws IOException;

   long openConsiderLock(String var1, String var2, boolean var3) throws IOException;

   long openConsiderLock(String var1, String var2, boolean var3, String[] var4, String[] var5) throws IOException;

   long openBasic(String var1, String var2) throws IOException;

   long openEnhanced(String var1, String var2, String[] var3, String[] var4) throws IOException;

   void close(long var1) throws IOException;

   long getSize(long var1) throws IOException;

   long getDeviceLimit(long var1) throws IOException;

   long getDeviceUsed(long var1) throws IOException;

   void truncate(long var1, long var3) throws IOException;

   int read(long var1, long var3, ByteBuffer var5, int var6, int var7) throws IOException;

   int write(long var1, long var3, ByteBuffer var5, int var6, int var7) throws IOException;

   void force(long var1, boolean var3) throws IOException;
}
