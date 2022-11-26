package org.python.netty.channel;

import org.python.netty.util.concurrent.PromiseAggregator;

/** @deprecated */
@Deprecated
public final class ChannelPromiseAggregator extends PromiseAggregator implements ChannelFutureListener {
   public ChannelPromiseAggregator(ChannelPromise aggregatePromise) {
      super(aggregatePromise);
   }
}
