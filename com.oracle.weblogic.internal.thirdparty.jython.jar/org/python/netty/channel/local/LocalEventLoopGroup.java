package org.python.netty.channel.local;

import java.util.concurrent.ThreadFactory;
import org.python.netty.channel.DefaultEventLoopGroup;

/** @deprecated */
@Deprecated
public class LocalEventLoopGroup extends DefaultEventLoopGroup {
   public LocalEventLoopGroup() {
   }

   public LocalEventLoopGroup(int nThreads) {
      super(nThreads);
   }

   public LocalEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
      super(nThreads, threadFactory);
   }
}
