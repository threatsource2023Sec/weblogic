package org.python.netty.channel;

import java.util.List;
import java.util.Map;
import org.python.netty.util.concurrent.EventExecutorGroup;

public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker, Iterable {
   ChannelPipeline addFirst(String var1, ChannelHandler var2);

   ChannelPipeline addFirst(EventExecutorGroup var1, String var2, ChannelHandler var3);

   ChannelPipeline addLast(String var1, ChannelHandler var2);

   ChannelPipeline addLast(EventExecutorGroup var1, String var2, ChannelHandler var3);

   ChannelPipeline addBefore(String var1, String var2, ChannelHandler var3);

   ChannelPipeline addBefore(EventExecutorGroup var1, String var2, String var3, ChannelHandler var4);

   ChannelPipeline addAfter(String var1, String var2, ChannelHandler var3);

   ChannelPipeline addAfter(EventExecutorGroup var1, String var2, String var3, ChannelHandler var4);

   ChannelPipeline addFirst(ChannelHandler... var1);

   ChannelPipeline addFirst(EventExecutorGroup var1, ChannelHandler... var2);

   ChannelPipeline addLast(ChannelHandler... var1);

   ChannelPipeline addLast(EventExecutorGroup var1, ChannelHandler... var2);

   ChannelPipeline remove(ChannelHandler var1);

   ChannelHandler remove(String var1);

   ChannelHandler remove(Class var1);

   ChannelHandler removeFirst();

   ChannelHandler removeLast();

   ChannelPipeline replace(ChannelHandler var1, String var2, ChannelHandler var3);

   ChannelHandler replace(String var1, String var2, ChannelHandler var3);

   ChannelHandler replace(Class var1, String var2, ChannelHandler var3);

   ChannelHandler first();

   ChannelHandlerContext firstContext();

   ChannelHandler last();

   ChannelHandlerContext lastContext();

   ChannelHandler get(String var1);

   ChannelHandler get(Class var1);

   ChannelHandlerContext context(ChannelHandler var1);

   ChannelHandlerContext context(String var1);

   ChannelHandlerContext context(Class var1);

   Channel channel();

   List names();

   Map toMap();

   ChannelPipeline fireChannelRegistered();

   ChannelPipeline fireChannelUnregistered();

   ChannelPipeline fireChannelActive();

   ChannelPipeline fireChannelInactive();

   ChannelPipeline fireExceptionCaught(Throwable var1);

   ChannelPipeline fireUserEventTriggered(Object var1);

   ChannelPipeline fireChannelRead(Object var1);

   ChannelPipeline fireChannelReadComplete();

   ChannelPipeline fireChannelWritabilityChanged();

   ChannelPipeline flush();
}
