package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;

public interface ByteBufferAware {
   ByteBuffer allocateByteBuffer(int var1);

   ByteBuffer allocateByteBufferAtLeast(int var1);

   ByteBuffer reallocateByteBuffer(ByteBuffer var1, int var2);

   void releaseByteBuffer(ByteBuffer var1);
}
