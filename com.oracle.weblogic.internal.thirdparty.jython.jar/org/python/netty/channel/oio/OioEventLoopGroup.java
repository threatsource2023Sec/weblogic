package org.python.netty.channel.oio;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.python.netty.channel.ThreadPerChannelEventLoopGroup;

public class OioEventLoopGroup extends ThreadPerChannelEventLoopGroup {
   public OioEventLoopGroup() {
      this(0);
   }

   public OioEventLoopGroup(int maxChannels) {
      this(maxChannels, Executors.defaultThreadFactory());
   }

   public OioEventLoopGroup(int maxChannels, Executor executor) {
      super(maxChannels, executor);
   }

   public OioEventLoopGroup(int maxChannels, ThreadFactory threadFactory) {
      super(maxChannels, threadFactory);
   }
}
