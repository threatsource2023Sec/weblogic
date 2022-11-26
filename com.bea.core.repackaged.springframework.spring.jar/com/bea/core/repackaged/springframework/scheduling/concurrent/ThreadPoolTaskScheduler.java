package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.core.task.AsyncListenableTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.TaskRejectedException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.SchedulingTaskExecutor;
import com.bea.core.repackaged.springframework.scheduling.TaskScheduler;
import com.bea.core.repackaged.springframework.scheduling.Trigger;
import com.bea.core.repackaged.springframework.scheduling.support.TaskUtils;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.ErrorHandler;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFutureTask;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTaskScheduler extends ExecutorConfigurationSupport implements AsyncListenableTaskExecutor, SchedulingTaskExecutor, TaskScheduler {
   private volatile int poolSize = 1;
   private volatile boolean removeOnCancelPolicy = false;
   @Nullable
   private volatile ErrorHandler errorHandler;
   @Nullable
   private ScheduledExecutorService scheduledExecutor;
   private final Map listenableFutureMap;

   public ThreadPoolTaskScheduler() {
      this.listenableFutureMap = new ConcurrentReferenceHashMap(16, ConcurrentReferenceHashMap.ReferenceType.WEAK);
   }

   public void setPoolSize(int poolSize) {
      Assert.isTrue(poolSize > 0, "'poolSize' must be 1 or higher");
      this.poolSize = poolSize;
      if (this.scheduledExecutor instanceof ScheduledThreadPoolExecutor) {
         ((ScheduledThreadPoolExecutor)this.scheduledExecutor).setCorePoolSize(poolSize);
      }

   }

   public void setRemoveOnCancelPolicy(boolean removeOnCancelPolicy) {
      this.removeOnCancelPolicy = removeOnCancelPolicy;
      if (this.scheduledExecutor instanceof ScheduledThreadPoolExecutor) {
         ((ScheduledThreadPoolExecutor)this.scheduledExecutor).setRemoveOnCancelPolicy(removeOnCancelPolicy);
      } else if (removeOnCancelPolicy && this.scheduledExecutor != null) {
         this.logger.debug("Could not apply remove-on-cancel policy - not a ScheduledThreadPoolExecutor");
      }

   }

   public void setErrorHandler(ErrorHandler errorHandler) {
      this.errorHandler = errorHandler;
   }

   protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
      this.scheduledExecutor = this.createExecutor(this.poolSize, threadFactory, rejectedExecutionHandler);
      if (this.removeOnCancelPolicy) {
         if (this.scheduledExecutor instanceof ScheduledThreadPoolExecutor) {
            ((ScheduledThreadPoolExecutor)this.scheduledExecutor).setRemoveOnCancelPolicy(true);
         } else {
            this.logger.debug("Could not apply remove-on-cancel policy - not a ScheduledThreadPoolExecutor");
         }
      }

      return this.scheduledExecutor;
   }

   protected ScheduledExecutorService createExecutor(int poolSize, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
      return new ScheduledThreadPoolExecutor(poolSize, threadFactory, rejectedExecutionHandler);
   }

   public ScheduledExecutorService getScheduledExecutor() throws IllegalStateException {
      Assert.state(this.scheduledExecutor != null, "ThreadPoolTaskScheduler not initialized");
      return this.scheduledExecutor;
   }

   public ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() throws IllegalStateException {
      Assert.state(this.scheduledExecutor instanceof ScheduledThreadPoolExecutor, "No ScheduledThreadPoolExecutor available");
      return (ScheduledThreadPoolExecutor)this.scheduledExecutor;
   }

   public int getPoolSize() {
      return this.scheduledExecutor == null ? this.poolSize : this.getScheduledThreadPoolExecutor().getPoolSize();
   }

   public boolean isRemoveOnCancelPolicy() {
      return this.scheduledExecutor == null ? this.removeOnCancelPolicy : this.getScheduledThreadPoolExecutor().getRemoveOnCancelPolicy();
   }

   public int getActiveCount() {
      return this.scheduledExecutor == null ? 0 : this.getScheduledThreadPoolExecutor().getActiveCount();
   }

   public void execute(Runnable task) {
      Executor executor = this.getScheduledExecutor();

      try {
         executor.execute(this.errorHandlingTask(task, false));
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   public void execute(Runnable task, long startTimeout) {
      this.execute(task);
   }

   public Future submit(Runnable task) {
      ExecutorService executor = this.getScheduledExecutor();

      try {
         return executor.submit(this.errorHandlingTask(task, false));
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   public Future submit(Callable task) {
      ExecutorService executor = this.getScheduledExecutor();

      try {
         Callable taskToUse = task;
         ErrorHandler errorHandler = this.errorHandler;
         if (errorHandler != null) {
            taskToUse = new DelegatingErrorHandlingCallable(task, errorHandler);
         }

         return executor.submit((Callable)taskToUse);
      } catch (RejectedExecutionException var5) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var5);
      }
   }

   public ListenableFuture submitListenable(Runnable task) {
      ExecutorService executor = this.getScheduledExecutor();

      try {
         ListenableFutureTask listenableFuture = new ListenableFutureTask(task, (Object)null);
         this.executeAndTrack(executor, listenableFuture);
         return listenableFuture;
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   public ListenableFuture submitListenable(Callable task) {
      ExecutorService executor = this.getScheduledExecutor();

      try {
         ListenableFutureTask listenableFuture = new ListenableFutureTask(task);
         this.executeAndTrack(executor, listenableFuture);
         return listenableFuture;
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   private void executeAndTrack(ExecutorService executor, ListenableFutureTask listenableFuture) {
      Future scheduledFuture = executor.submit(this.errorHandlingTask(listenableFuture, false));
      this.listenableFutureMap.put(scheduledFuture, listenableFuture);
      listenableFuture.addCallback((result) -> {
         ListenableFuture var10000 = (ListenableFuture)this.listenableFutureMap.remove(scheduledFuture);
      }, (ex) -> {
         ListenableFuture var10000 = (ListenableFuture)this.listenableFutureMap.remove(scheduledFuture);
      });
   }

   protected void cancelRemainingTask(Runnable task) {
      super.cancelRemainingTask(task);
      ListenableFuture listenableFuture = (ListenableFuture)this.listenableFutureMap.get(task);
      if (listenableFuture != null) {
         listenableFuture.cancel(true);
      }

   }

   @Nullable
   public ScheduledFuture schedule(Runnable task, Trigger trigger) {
      ScheduledExecutorService executor = this.getScheduledExecutor();

      try {
         ErrorHandler errorHandler = this.errorHandler;
         if (errorHandler == null) {
            errorHandler = TaskUtils.getDefaultErrorHandler(true);
         }

         return (new ReschedulingRunnable(task, trigger, executor, errorHandler)).schedule();
      } catch (RejectedExecutionException var5) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var5);
      }
   }

   public ScheduledFuture schedule(Runnable task, Date startTime) {
      ScheduledExecutorService executor = this.getScheduledExecutor();
      long initialDelay = startTime.getTime() - System.currentTimeMillis();

      try {
         return executor.schedule(this.errorHandlingTask(task, false), initialDelay, TimeUnit.MILLISECONDS);
      } catch (RejectedExecutionException var7) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var7);
      }
   }

   public ScheduledFuture scheduleAtFixedRate(Runnable task, Date startTime, long period) {
      ScheduledExecutorService executor = this.getScheduledExecutor();
      long initialDelay = startTime.getTime() - System.currentTimeMillis();

      try {
         return executor.scheduleAtFixedRate(this.errorHandlingTask(task, true), initialDelay, period, TimeUnit.MILLISECONDS);
      } catch (RejectedExecutionException var9) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var9);
      }
   }

   public ScheduledFuture scheduleAtFixedRate(Runnable task, long period) {
      ScheduledExecutorService executor = this.getScheduledExecutor();

      try {
         return executor.scheduleAtFixedRate(this.errorHandlingTask(task, true), 0L, period, TimeUnit.MILLISECONDS);
      } catch (RejectedExecutionException var6) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var6);
      }
   }

   public ScheduledFuture scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
      ScheduledExecutorService executor = this.getScheduledExecutor();
      long initialDelay = startTime.getTime() - System.currentTimeMillis();

      try {
         return executor.scheduleWithFixedDelay(this.errorHandlingTask(task, true), initialDelay, delay, TimeUnit.MILLISECONDS);
      } catch (RejectedExecutionException var9) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var9);
      }
   }

   public ScheduledFuture scheduleWithFixedDelay(Runnable task, long delay) {
      ScheduledExecutorService executor = this.getScheduledExecutor();

      try {
         return executor.scheduleWithFixedDelay(this.errorHandlingTask(task, true), 0L, delay, TimeUnit.MILLISECONDS);
      } catch (RejectedExecutionException var6) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var6);
      }
   }

   private Runnable errorHandlingTask(Runnable task, boolean isRepeatingTask) {
      return TaskUtils.decorateTaskWithErrorHandler(task, this.errorHandler, isRepeatingTask);
   }

   private static class DelegatingErrorHandlingCallable implements Callable {
      private final Callable delegate;
      private final ErrorHandler errorHandler;

      public DelegatingErrorHandlingCallable(Callable delegate, ErrorHandler errorHandler) {
         this.delegate = delegate;
         this.errorHandler = errorHandler;
      }

      @Nullable
      public Object call() throws Exception {
         try {
            return this.delegate.call();
         } catch (Throwable var2) {
            this.errorHandler.handleError(var2);
            return null;
         }
      }
   }
}
