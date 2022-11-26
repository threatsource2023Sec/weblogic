package org.python.netty.channel.pool;

public interface ChannelPoolMap {
   ChannelPool get(Object var1);

   boolean contains(Object var1);
}
