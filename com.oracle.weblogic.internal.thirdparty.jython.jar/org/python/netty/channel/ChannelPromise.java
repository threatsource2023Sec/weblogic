package org.python.netty.channel;

import org.python.netty.util.concurrent.GenericFutureListener;
import org.python.netty.util.concurrent.Promise;

public interface ChannelPromise extends ChannelFuture, Promise {
   Channel channel();

   ChannelPromise setSuccess(Void var1);

   ChannelPromise setSuccess();

   boolean trySuccess();

   ChannelPromise setFailure(Throwable var1);

   ChannelPromise addListener(GenericFutureListener var1);

   ChannelPromise addListeners(GenericFutureListener... var1);

   ChannelPromise removeListener(GenericFutureListener var1);

   ChannelPromise removeListeners(GenericFutureListener... var1);

   ChannelPromise sync() throws InterruptedException;

   ChannelPromise syncUninterruptibly();

   ChannelPromise await() throws InterruptedException;

   ChannelPromise awaitUninterruptibly();

   ChannelPromise unvoid();
}
