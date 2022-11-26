package org.python.netty.handler.ipfilter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.util.internal.ConcurrentSet;

@ChannelHandler.Sharable
public class UniqueIpFilter extends AbstractRemoteAddressFilter {
   private final Set connected = new ConcurrentSet();

   protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
      final InetAddress remoteIp = remoteAddress.getAddress();
      if (this.connected.contains(remoteIp)) {
         return false;
      } else {
         this.connected.add(remoteIp);
         ctx.channel().closeFuture().addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
               UniqueIpFilter.this.connected.remove(remoteIp);
            }
         });
         return true;
      }
   }
}
