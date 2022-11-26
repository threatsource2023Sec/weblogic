package org.python.netty.channel;

import org.python.netty.util.concurrent.GenericFutureListener;
import org.python.netty.util.concurrent.ProgressivePromise;

public interface ChannelProgressivePromise extends ProgressivePromise, ChannelProgressiveFuture, ChannelPromise {
   ChannelProgressivePromise addListener(GenericFutureListener var1);

   ChannelProgressivePromise addListeners(GenericFutureListener... var1);

   ChannelProgressivePromise removeListener(GenericFutureListener var1);

   ChannelProgressivePromise removeListeners(GenericFutureListener... var1);

   ChannelProgressivePromise sync() throws InterruptedException;

   ChannelProgressivePromise syncUninterruptibly();

   ChannelProgressivePromise await() throws InterruptedException;

   ChannelProgressivePromise awaitUninterruptibly();

   ChannelProgressivePromise setSuccess(Void var1);

   ChannelProgressivePromise setSuccess();

   ChannelProgressivePromise setFailure(Throwable var1);

   ChannelProgressivePromise setProgress(long var1, long var3);

   ChannelProgressivePromise unvoid();
}
