package org.python.netty.channel;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import org.python.netty.util.concurrent.DefaultThreadFactory;

public class DefaultEventLoop extends SingleThreadEventLoop {
   public DefaultEventLoop() {
      this((EventLoopGroup)null);
   }

   public DefaultEventLoop(ThreadFactory threadFactory) {
      this((EventLoopGroup)null, (ThreadFactory)threadFactory);
   }

   public DefaultEventLoop(Executor executor) {
      this((EventLoopGroup)null, (Executor)executor);
   }

   public DefaultEventLoop(EventLoopGroup parent) {
      this(parent, (ThreadFactory)(new DefaultThreadFactory(DefaultEventLoop.class)));
   }

   public DefaultEventLoop(EventLoopGroup parent, ThreadFactory threadFactory) {
      super(parent, threadFactory, true);
   }

   public DefaultEventLoop(EventLoopGroup parent, Executor executor) {
      super(parent, executor, true);
   }

   protected void run() {
      do {
         Runnable task = this.takeTask();
         if (task != null) {
            task.run();
            this.updateLastExecutionTime();
         }
      } while(!this.confirmShutdown());

   }
}
