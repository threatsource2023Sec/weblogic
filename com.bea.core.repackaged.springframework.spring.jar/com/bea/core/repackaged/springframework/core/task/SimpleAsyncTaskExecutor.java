package com.bea.core.repackaged.springframework.core.task;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ConcurrencyThrottleSupport;
import com.bea.core.repackaged.springframework.util.CustomizableThreadCreator;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFutureTask;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

public class SimpleAsyncTaskExecutor extends CustomizableThreadCreator implements AsyncListenableTaskExecutor, Serializable {
   public static final int UNBOUNDED_CONCURRENCY = -1;
   public static final int NO_CONCURRENCY = 0;
   private final ConcurrencyThrottleAdapter concurrencyThrottle = new ConcurrencyThrottleAdapter();
   @Nullable
   private ThreadFactory threadFactory;
   @Nullable
   private TaskDecorator taskDecorator;

   public SimpleAsyncTaskExecutor() {
   }

   public SimpleAsyncTaskExecutor(String threadNamePrefix) {
      super(threadNamePrefix);
   }

   public SimpleAsyncTaskExecutor(ThreadFactory threadFactory) {
      this.threadFactory = threadFactory;
   }

   public void setThreadFactory(@Nullable ThreadFactory threadFactory) {
      this.threadFactory = threadFactory;
   }

   @Nullable
   public final ThreadFactory getThreadFactory() {
      return this.threadFactory;
   }

   public final void setTaskDecorator(TaskDecorator taskDecorator) {
      this.taskDecorator = taskDecorator;
   }

   public void setConcurrencyLimit(int concurrencyLimit) {
      this.concurrencyThrottle.setConcurrencyLimit(concurrencyLimit);
   }

   public final int getConcurrencyLimit() {
      return this.concurrencyThrottle.getConcurrencyLimit();
   }

   public final boolean isThrottleActive() {
      return this.concurrencyThrottle.isThrottleActive();
   }

   public void execute(Runnable task) {
      this.execute(task, Long.MAX_VALUE);
   }

   public void execute(Runnable task, long startTimeout) {
      Assert.notNull(task, (String)"Runnable must not be null");
      Runnable taskToUse = this.taskDecorator != null ? this.taskDecorator.decorate(task) : task;
      if (this.isThrottleActive() && startTimeout > 0L) {
         this.concurrencyThrottle.beforeAccess();
         this.doExecute(new ConcurrencyThrottlingRunnable(taskToUse));
      } else {
         this.doExecute(taskToUse);
      }

   }

   public Future submit(Runnable task) {
      FutureTask future = new FutureTask(task, (Object)null);
      this.execute(future, Long.MAX_VALUE);
      return future;
   }

   public Future submit(Callable task) {
      FutureTask future = new FutureTask(task);
      this.execute(future, Long.MAX_VALUE);
      return future;
   }

   public ListenableFuture submitListenable(Runnable task) {
      ListenableFutureTask future = new ListenableFutureTask(task, (Object)null);
      this.execute(future, Long.MAX_VALUE);
      return future;
   }

   public ListenableFuture submitListenable(Callable task) {
      ListenableFutureTask future = new ListenableFutureTask(task);
      this.execute(future, Long.MAX_VALUE);
      return future;
   }

   protected void doExecute(Runnable task) {
      Thread thread = this.threadFactory != null ? this.threadFactory.newThread(task) : this.createThread(task);
      thread.start();
   }

   private class ConcurrencyThrottlingRunnable implements Runnable {
      private final Runnable target;

      public ConcurrencyThrottlingRunnable(Runnable target) {
         this.target = target;
      }

      public void run() {
         try {
            this.target.run();
         } finally {
            SimpleAsyncTaskExecutor.this.concurrencyThrottle.afterAccess();
         }

      }
   }

   private static class ConcurrencyThrottleAdapter extends ConcurrencyThrottleSupport {
      private ConcurrencyThrottleAdapter() {
      }

      protected void beforeAccess() {
         super.beforeAccess();
      }

      protected void afterAccess() {
         super.afterAccess();
      }

      // $FF: synthetic method
      ConcurrencyThrottleAdapter(Object x0) {
         this();
      }
   }
}
