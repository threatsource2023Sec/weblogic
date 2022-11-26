package weblogic.store.io.file.direct;

import java.nio.ByteBuffer;

interface DirectBufferIONative {
   ByteBuffer allocate(int var1);

   void free(ByteBuffer var1);

   void fillBuffer(ByteBuffer var1, int var2, int var3, byte var4);
}
