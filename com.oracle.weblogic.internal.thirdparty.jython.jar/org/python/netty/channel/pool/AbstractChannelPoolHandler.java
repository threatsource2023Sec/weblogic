package org.python.netty.channel.pool;

import org.python.netty.channel.Channel;

public abstract class AbstractChannelPoolHandler implements ChannelPoolHandler {
   public void channelAcquired(Channel ch) throws Exception {
   }

   public void channelReleased(Channel ch) throws Exception {
   }
}
