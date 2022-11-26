package org.python.netty.channel;

import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.Attribute;
import org.python.netty.util.AttributeKey;
import org.python.netty.util.AttributeMap;
import org.python.netty.util.concurrent.EventExecutor;

public interface ChannelHandlerContext extends AttributeMap, ChannelInboundInvoker, ChannelOutboundInvoker {
   Channel channel();

   EventExecutor executor();

   String name();

   ChannelHandler handler();

   boolean isRemoved();

   ChannelHandlerContext fireChannelRegistered();

   ChannelHandlerContext fireChannelUnregistered();

   ChannelHandlerContext fireChannelActive();

   ChannelHandlerContext fireChannelInactive();

   ChannelHandlerContext fireExceptionCaught(Throwable var1);

   ChannelHandlerContext fireUserEventTriggered(Object var1);

   ChannelHandlerContext fireChannelRead(Object var1);

   ChannelHandlerContext fireChannelReadComplete();

   ChannelHandlerContext fireChannelWritabilityChanged();

   ChannelHandlerContext read();

   ChannelHandlerContext flush();

   ChannelPipeline pipeline();

   ByteBufAllocator alloc();

   /** @deprecated */
   @Deprecated
   Attribute attr(AttributeKey var1);

   /** @deprecated */
   @Deprecated
   boolean hasAttr(AttributeKey var1);
}
