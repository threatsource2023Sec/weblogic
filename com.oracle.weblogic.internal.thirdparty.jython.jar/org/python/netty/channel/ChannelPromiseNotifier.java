package org.python.netty.channel;

import org.python.netty.util.concurrent.PromiseNotifier;

public final class ChannelPromiseNotifier extends PromiseNotifier implements ChannelFutureListener {
   public ChannelPromiseNotifier(ChannelPromise... promises) {
      super(promises);
   }

   public ChannelPromiseNotifier(boolean logNotifyFailure, ChannelPromise... promises) {
      super(logNotifyFailure, promises);
   }
}
