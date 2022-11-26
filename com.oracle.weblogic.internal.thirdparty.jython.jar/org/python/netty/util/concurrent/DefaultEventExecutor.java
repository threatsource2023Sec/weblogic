package org.python.netty.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public final class DefaultEventExecutor extends SingleThreadEventExecutor {
   public DefaultEventExecutor() {
      this((EventExecutorGroup)null);
   }

   public DefaultEventExecutor(ThreadFactory threadFactory) {
      this((EventExecutorGroup)null, (ThreadFactory)threadFactory);
   }

   public DefaultEventExecutor(Executor executor) {
      this((EventExecutorGroup)null, (Executor)executor);
   }

   public DefaultEventExecutor(EventExecutorGroup parent) {
      this(parent, (ThreadFactory)(new DefaultThreadFactory(DefaultEventExecutor.class)));
   }

   public DefaultEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory) {
      super(parent, threadFactory, true);
   }

   public DefaultEventExecutor(EventExecutorGroup parent, Executor executor) {
      super(parent, executor, true);
   }

   public DefaultEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
      super(parent, threadFactory, true, maxPendingTasks, rejectedExecutionHandler);
   }

   public DefaultEventExecutor(EventExecutorGroup parent, Executor executor, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
      super(parent, executor, true, maxPendingTasks, rejectedExecutionHandler);
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
