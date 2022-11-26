package org.python.netty.handler.ipfilter;

import java.net.SocketAddress;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelInboundHandlerAdapter;

public abstract class AbstractRemoteAddressFilter extends ChannelInboundHandlerAdapter {
   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      this.handleNewChannel(ctx);
      ctx.fireChannelRegistered();
   }

   public void channelActive(ChannelHandlerContext ctx) throws Exception {
      if (!this.handleNewChannel(ctx)) {
         throw new IllegalStateException("cannot determine to accept or reject a channel: " + ctx.channel());
      } else {
         ctx.fireChannelActive();
      }
   }

   private boolean handleNewChannel(ChannelHandlerContext ctx) throws Exception {
      SocketAddress remoteAddress = ctx.channel().remoteAddress();
      if (remoteAddress == null) {
         return false;
      } else {
         ctx.pipeline().remove((ChannelHandler)this);
         if (this.accept(ctx, remoteAddress)) {
            this.channelAccepted(ctx, remoteAddress);
         } else {
            ChannelFuture rejectedFuture = this.channelRejected(ctx, remoteAddress);
            if (rejectedFuture != null) {
               rejectedFuture.addListener(ChannelFutureListener.CLOSE);
            } else {
               ctx.close();
            }
         }

         return true;
      }
   }

   protected abstract boolean accept(ChannelHandlerContext var1, SocketAddress var2) throws Exception;

   protected void channelAccepted(ChannelHandlerContext ctx, SocketAddress remoteAddress) {
   }

   protected ChannelFuture channelRejected(ChannelHandlerContext ctx, SocketAddress remoteAddress) {
      return null;
   }
}
