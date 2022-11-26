package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.core.task.AsyncListenableTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.TaskDecorator;
import com.bea.core.repackaged.springframework.core.task.TaskRejectedException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.SchedulingTaskExecutor;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFutureTask;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTaskExecutor extends ExecutorConfigurationSupport implements AsyncListenableTaskExecutor, SchedulingTaskExecutor {
   private final Object poolSizeMonitor = new Object();
   private int corePoolSize = 1;
   private int maxPoolSize = Integer.MAX_VALUE;
   private int keepAliveSeconds = 60;
   private int queueCapacity = Integer.MAX_VALUE;
   private boolean allowCoreThreadTimeOut = false;
   @Nullable
   private TaskDecorator taskDecorator;
   @Nullable
   private ThreadPoolExecutor threadPoolExecutor;
   private final Map decoratedTaskMap;

   public ThreadPoolTaskExecutor() {
      this.decoratedTaskMap = new ConcurrentReferenceHashMap(16, ConcurrentReferenceHashMap.ReferenceType.WEAK);
   }

   public void setCorePoolSize(int corePoolSize) {
      synchronized(this.poolSizeMonitor) {
         this.corePoolSize = corePoolSize;
         if (this.threadPoolExecutor != null) {
            this.threadPoolExecutor.setCorePoolSize(corePoolSize);
         }

      }
   }

   public int getCorePoolSize() {
      synchronized(this.poolSizeMonitor) {
         return this.corePoolSize;
      }
   }

   public void setMaxPoolSize(int maxPoolSize) {
      synchronized(this.poolSizeMonitor) {
         this.maxPoolSize = maxPoolSize;
         if (this.threadPoolExecutor != null) {
            this.threadPoolExecutor.setMaximumPoolSize(maxPoolSize);
         }

      }
   }

   public int getMaxPoolSize() {
      synchronized(this.poolSizeMonitor) {
         return this.maxPoolSize;
      }
   }

   public void setKeepAliveSeconds(int keepAliveSeconds) {
      synchronized(this.poolSizeMonitor) {
         this.keepAliveSeconds = keepAliveSeconds;
         if (this.threadPoolExecutor != null) {
            this.threadPoolExecutor.setKeepAliveTime((long)keepAliveSeconds, TimeUnit.SECONDS);
         }

      }
   }

   public int getKeepAliveSeconds() {
      synchronized(this.poolSizeMonitor) {
         return this.keepAliveSeconds;
      }
   }

   public void setQueueCapacity(int queueCapacity) {
      this.queueCapacity = queueCapacity;
   }

   public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
      this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
   }

   public void setTaskDecorator(TaskDecorator taskDecorator) {
      this.taskDecorator = taskDecorator;
   }

   protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
      BlockingQueue queue = this.createQueue(this.queueCapacity);
      ThreadPoolExecutor executor;
      if (this.taskDecorator != null) {
         executor = new ThreadPoolExecutor(this.corePoolSize, this.maxPoolSize, (long)this.keepAliveSeconds, TimeUnit.SECONDS, queue, threadFactory, rejectedExecutionHandler) {
            public void execute(Runnable command) {
               Runnable decorated = ThreadPoolTaskExecutor.this.taskDecorator.decorate(command);
               if (decorated != command) {
                  ThreadPoolTaskExecutor.this.decoratedTaskMap.put(decorated, command);
               }

               super.execute(decorated);
            }
         };
      } else {
         executor = new ThreadPoolExecutor(this.corePoolSize, this.maxPoolSize, (long)this.keepAliveSeconds, TimeUnit.SECONDS, queue, threadFactory, rejectedExecutionHandler);
      }

      if (this.allowCoreThreadTimeOut) {
         executor.allowCoreThreadTimeOut(true);
      }

      this.threadPoolExecutor = executor;
      return executor;
   }

   protected BlockingQueue createQueue(int queueCapacity) {
      return (BlockingQueue)(queueCapacity > 0 ? new LinkedBlockingQueue(queueCapacity) : new SynchronousQueue());
   }

   public ThreadPoolExecutor getThreadPoolExecutor() throws IllegalStateException {
      Assert.state(this.threadPoolExecutor != null, "ThreadPoolTaskExecutor not initialized");
      return this.threadPoolExecutor;
   }

   public int getPoolSize() {
      return this.threadPoolExecutor == null ? this.corePoolSize : this.threadPoolExecutor.getPoolSize();
   }

   public int getActiveCount() {
      return this.threadPoolExecutor == null ? 0 : this.threadPoolExecutor.getActiveCount();
   }

   public void execute(Runnable task) {
      Executor executor = this.getThreadPoolExecutor();

      try {
         executor.execute(task);
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   public void execute(Runnable task, long startTimeout) {
      this.execute(task);
   }

   public Future submit(Runnable task) {
      ExecutorService executor = this.getThreadPoolExecutor();

      try {
         return executor.submit(task);
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   public Future submit(Callable task) {
      ExecutorService executor = this.getThreadPoolExecutor();

      try {
         return executor.submit(task);
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   public ListenableFuture submitListenable(Runnable task) {
      ExecutorService executor = this.getThreadPoolExecutor();

      try {
         ListenableFutureTask future = new ListenableFutureTask(task, (Object)null);
         executor.execute(future);
         return future;
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   public ListenableFuture submitListenable(Callable task) {
      ExecutorService executor = this.getThreadPoolExecutor();

      try {
         ListenableFutureTask future = new ListenableFutureTask(task);
         executor.execute(future);
         return future;
      } catch (RejectedExecutionException var4) {
         throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
      }
   }

   protected void cancelRemainingTask(Runnable task) {
      super.cancelRemainingTask(task);
      Object original = this.decoratedTaskMap.get(task);
      if (original instanceof Future) {
         ((Future)original).cancel(true);
      }

   }
}
