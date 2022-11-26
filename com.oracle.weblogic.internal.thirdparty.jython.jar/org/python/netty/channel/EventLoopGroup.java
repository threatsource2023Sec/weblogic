package org.python.netty.channel;

import org.python.netty.util.concurrent.EventExecutorGroup;

public interface EventLoopGroup extends EventExecutorGroup {
   EventLoop next();

   ChannelFuture register(Channel var1);

   ChannelFuture register(ChannelPromise var1);

   /** @deprecated */
   @Deprecated
   ChannelFuture register(Channel var1, ChannelPromise var2);
}
