package weblogic.store.io.file.direct;

import java.io.IOException;
import java.nio.ByteBuffer;

interface DirectMappedIONative {
   long createMapping(long var1, long var3) throws IOException;

   ByteBuffer mapFile(long var1, long var3, int var5, boolean var6) throws IOException;

   void unmapFile(ByteBuffer var1) throws IOException;

   long getMemoryMapGranularity();
}
