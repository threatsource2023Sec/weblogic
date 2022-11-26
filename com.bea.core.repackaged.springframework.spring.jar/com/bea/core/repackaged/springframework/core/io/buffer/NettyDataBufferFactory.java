package com.bea.core.repackaged.springframework.core.io.buffer;

import com.bea.core.repackaged.springframework.util.Assert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NettyDataBufferFactory implements DataBufferFactory {
   private final ByteBufAllocator byteBufAllocator;

   public NettyDataBufferFactory(ByteBufAllocator byteBufAllocator) {
      Assert.notNull(byteBufAllocator, (String)"ByteBufAllocator must not be null");
      this.byteBufAllocator = byteBufAllocator;
   }

   public ByteBufAllocator getByteBufAllocator() {
      return this.byteBufAllocator;
   }

   public NettyDataBuffer allocateBuffer() {
      ByteBuf byteBuf = this.byteBufAllocator.buffer();
      return new NettyDataBuffer(byteBuf, this);
   }

   public NettyDataBuffer allocateBuffer(int initialCapacity) {
      ByteBuf byteBuf = this.byteBufAllocator.buffer(initialCapacity);
      return new NettyDataBuffer(byteBuf, this);
   }

   public NettyDataBuffer wrap(ByteBuffer byteBuffer) {
      ByteBuf byteBuf = Unpooled.wrappedBuffer(byteBuffer);
      return new NettyDataBuffer(byteBuf, this);
   }

   public DataBuffer wrap(byte[] bytes) {
      ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
      return new NettyDataBuffer(byteBuf, this);
   }

   public NettyDataBuffer wrap(ByteBuf byteBuf) {
      byteBuf.touch();
      return new NettyDataBuffer(byteBuf, this);
   }

   public DataBuffer join(List dataBuffers) {
      Assert.notEmpty((Collection)dataBuffers, (String)"DataBuffer List must not be empty");
      int bufferCount = dataBuffers.size();
      if (bufferCount == 1) {
         return (DataBuffer)dataBuffers.get(0);
      } else {
         CompositeByteBuf composite = this.byteBufAllocator.compositeBuffer(bufferCount);
         Iterator var4 = dataBuffers.iterator();

         while(var4.hasNext()) {
            DataBuffer dataBuffer = (DataBuffer)var4.next();
            Assert.isInstanceOf(NettyDataBuffer.class, dataBuffer);
            composite.addComponent(true, ((NettyDataBuffer)dataBuffer).getNativeBuffer());
         }

         return new NettyDataBuffer(composite, this);
      }
   }

   public static ByteBuf toByteBuf(DataBuffer buffer) {
      return buffer instanceof NettyDataBuffer ? ((NettyDataBuffer)buffer).getNativeBuffer() : Unpooled.wrappedBuffer(buffer.asByteBuffer());
   }

   public String toString() {
      return "NettyDataBufferFactory (" + this.byteBufAllocator + ")";
   }
}
