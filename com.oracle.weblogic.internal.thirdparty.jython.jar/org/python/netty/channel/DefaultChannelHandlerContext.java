package org.python.netty.channel;

import org.python.netty.util.concurrent.EventExecutor;

final class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {
   private final ChannelHandler handler;

   DefaultChannelHandlerContext(DefaultChannelPipeline pipeline, EventExecutor executor, String name, ChannelHandler handler) {
      super(pipeline, executor, name, isInbound(handler), isOutbound(handler));
      if (handler == null) {
         throw new NullPointerException("handler");
      } else {
         this.handler = handler;
      }
   }

   public ChannelHandler handler() {
      return this.handler;
   }

   private static boolean isInbound(ChannelHandler handler) {
      return handler instanceof ChannelInboundHandler;
   }

   private static boolean isOutbound(ChannelHandler handler) {
      return handler instanceof ChannelOutboundHandler;
   }
}
