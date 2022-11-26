package org.python.netty.channel;

import java.util.concurrent.ConcurrentMap;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

@ChannelHandler.Sharable
public abstract class ChannelInitializer extends ChannelInboundHandlerAdapter {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
   private final ConcurrentMap initMap = PlatformDependent.newConcurrentHashMap();

   protected abstract void initChannel(Channel var1) throws Exception;

   public final void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      if (this.initChannel(ctx)) {
         ctx.pipeline().fireChannelRegistered();
      } else {
         ctx.fireChannelRegistered();
      }

   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      logger.warn("Failed to initialize a channel. Closing: " + ctx.channel(), cause);
      ctx.close();
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      if (ctx.channel().isRegistered()) {
         this.initChannel(ctx);
      }

   }

   private boolean initChannel(ChannelHandlerContext ctx) throws Exception {
      if (this.initMap.putIfAbsent(ctx, Boolean.TRUE) == null) {
         try {
            this.initChannel(ctx.channel());
         } catch (Throwable var6) {
            this.exceptionCaught(ctx, var6);
         } finally {
            this.remove(ctx);
         }

         return true;
      } else {
         return false;
      }
   }

   private void remove(ChannelHandlerContext ctx) {
      try {
         ChannelPipeline pipeline = ctx.pipeline();
         if (pipeline.context((ChannelHandler)this) != null) {
            pipeline.remove((ChannelHandler)this);
         }
      } finally {
         this.initMap.remove(ctx);
      }

   }
}
