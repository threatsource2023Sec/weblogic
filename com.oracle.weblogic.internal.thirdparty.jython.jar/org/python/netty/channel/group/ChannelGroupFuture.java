package org.python.netty.channel.group;

import java.util.Iterator;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.GenericFutureListener;

public interface ChannelGroupFuture extends Future, Iterable {
   ChannelGroup group();

   ChannelFuture find(Channel var1);

   boolean isSuccess();

   ChannelGroupException cause();

   boolean isPartialSuccess();

   boolean isPartialFailure();

   ChannelGroupFuture addListener(GenericFutureListener var1);

   ChannelGroupFuture addListeners(GenericFutureListener... var1);

   ChannelGroupFuture removeListener(GenericFutureListener var1);

   ChannelGroupFuture removeListeners(GenericFutureListener... var1);

   ChannelGroupFuture await() throws InterruptedException;

   ChannelGroupFuture awaitUninterruptibly();

   ChannelGroupFuture syncUninterruptibly();

   ChannelGroupFuture sync() throws InterruptedException;

   Iterator iterator();
}
