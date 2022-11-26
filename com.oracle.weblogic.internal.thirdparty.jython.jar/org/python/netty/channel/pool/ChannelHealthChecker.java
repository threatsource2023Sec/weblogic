package org.python.netty.channel.pool;

import org.python.netty.channel.Channel;
import org.python.netty.channel.EventLoop;
import org.python.netty.util.concurrent.Future;

public interface ChannelHealthChecker {
   ChannelHealthChecker ACTIVE = new ChannelHealthChecker() {
      public Future isHealthy(Channel channel) {
         EventLoop loop = channel.eventLoop();
         return channel.isActive() ? loop.newSucceededFuture(Boolean.TRUE) : loop.newSucceededFuture(Boolean.FALSE);
      }
   };

   Future isHealthy(Channel var1);
}
