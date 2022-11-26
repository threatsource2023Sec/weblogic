package com.bea.core.repackaged.springframework.core.io.buffer;

import com.bea.core.repackaged.springframework.util.Assert;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

public class DefaultDataBufferFactory implements DataBufferFactory {
   public static final int DEFAULT_INITIAL_CAPACITY = 256;
   private final boolean preferDirect;
   private final int defaultInitialCapacity;

   public DefaultDataBufferFactory() {
      this(false);
   }

   public DefaultDataBufferFactory(boolean preferDirect) {
      this(preferDirect, 256);
   }

   public DefaultDataBufferFactory(boolean preferDirect, int defaultInitialCapacity) {
      Assert.isTrue(defaultInitialCapacity > 0, "'defaultInitialCapacity' should be larger than 0");
      this.preferDirect = preferDirect;
      this.defaultInitialCapacity = defaultInitialCapacity;
   }

   public DefaultDataBuffer allocateBuffer() {
      return this.allocateBuffer(this.defaultInitialCapacity);
   }

   public DefaultDataBuffer allocateBuffer(int initialCapacity) {
      ByteBuffer byteBuffer = this.preferDirect ? ByteBuffer.allocateDirect(initialCapacity) : ByteBuffer.allocate(initialCapacity);
      return DefaultDataBuffer.fromEmptyByteBuffer(this, byteBuffer);
   }

   public DefaultDataBuffer wrap(ByteBuffer byteBuffer) {
      return DefaultDataBuffer.fromFilledByteBuffer(this, byteBuffer.slice());
   }

   public DefaultDataBuffer wrap(byte[] bytes) {
      return DefaultDataBuffer.fromFilledByteBuffer(this, ByteBuffer.wrap(bytes));
   }

   public DefaultDataBuffer join(List dataBuffers) {
      Assert.notEmpty((Collection)dataBuffers, (String)"DataBuffer List must not be empty");
      int capacity = dataBuffers.stream().mapToInt(DataBuffer::readableByteCount).sum();
      DefaultDataBuffer result = this.allocateBuffer(capacity);
      dataBuffers.forEach((xva$0) -> {
         result.write(xva$0);
      });
      dataBuffers.forEach(DataBufferUtils::release);
      return result;
   }

   public String toString() {
      return "DefaultDataBufferFactory (preferDirect=" + this.preferDirect + ")";
   }
}
