package com.bea.core.repackaged.springframework.core.io.buffer;

import java.nio.ByteBuffer;
import java.util.List;

public interface DataBufferFactory {
   DataBuffer allocateBuffer();

   DataBuffer allocateBuffer(int var1);

   DataBuffer wrap(ByteBuffer var1);

   DataBuffer wrap(byte[] var1);

   DataBuffer join(List var1);
}
