package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.support.DelegatingErrorHandlingRunnable;
import com.bea.core.repackaged.springframework.scheduling.support.TaskUtils;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class ScheduledExecutorFactoryBean extends ExecutorConfigurationSupport implements FactoryBean {
   private int poolSize = 1;
   @Nullable
   private ScheduledExecutorTask[] scheduledExecutorTasks;
   private boolean removeOnCancelPolicy = false;
   private boolean continueScheduledExecutionAfterException = false;
   private boolean exposeUnconfigurableExecutor = false;
   @Nullable
   private ScheduledExecutorService exposedExecutor;

   public void setPoolSize(int poolSize) {
      Assert.isTrue(poolSize > 0, "'poolSize' must be 1 or higher");
      this.poolSize = poolSize;
   }

   public void setScheduledExecutorTasks(ScheduledExecutorTask... scheduledExecutorTasks) {
      this.scheduledExecutorTasks = scheduledExecutorTasks;
   }

   public void setRemoveOnCancelPolicy(boolean removeOnCancelPolicy) {
      this.removeOnCancelPolicy = removeOnCancelPolicy;
   }

   public void setContinueScheduledExecutionAfterException(boolean continueScheduledExecutionAfterException) {
      this.continueScheduledExecutionAfterException = continueScheduledExecutionAfterException;
   }

   public void setExposeUnconfigurableExecutor(boolean exposeUnconfigurableExecutor) {
      this.exposeUnconfigurableExecutor = exposeUnconfigurableExecutor;
   }

   protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
      ScheduledExecutorService executor = this.createExecutor(this.poolSize, threadFactory, rejectedExecutionHandler);
      if (this.removeOnCancelPolicy) {
         if (executor instanceof ScheduledThreadPoolExecutor) {
            ((ScheduledThreadPoolExecutor)executor).setRemoveOnCancelPolicy(true);
         } else {
            this.logger.debug("Could not apply remove-on-cancel policy - not a ScheduledThreadPoolExecutor");
         }
      }

      if (!ObjectUtils.isEmpty((Object[])this.scheduledExecutorTasks)) {
         this.registerTasks(this.scheduledExecutorTasks, executor);
      }

      this.exposedExecutor = this.exposeUnconfigurableExecutor ? Executors.unconfigurableScheduledExecutorService(executor) : executor;
      return executor;
   }

   protected ScheduledExecutorService createExecutor(int poolSize, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
      return new ScheduledThreadPoolExecutor(poolSize, threadFactory, rejectedExecutionHandler);
   }

   protected void registerTasks(ScheduledExecutorTask[] tasks, ScheduledExecutorService executor) {
      ScheduledExecutorTask[] var3 = tasks;
      int var4 = tasks.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ScheduledExecutorTask task = var3[var5];
         Runnable runnable = this.getRunnableToSchedule(task);
         if (task.isOneTimeTask()) {
            executor.schedule(runnable, task.getDelay(), task.getTimeUnit());
         } else if (task.isFixedRate()) {
            executor.scheduleAtFixedRate(runnable, task.getDelay(), task.getPeriod(), task.getTimeUnit());
         } else {
            executor.scheduleWithFixedDelay(runnable, task.getDelay(), task.getPeriod(), task.getTimeUnit());
         }
      }

   }

   protected Runnable getRunnableToSchedule(ScheduledExecutorTask task) {
      return this.continueScheduledExecutionAfterException ? new DelegatingErrorHandlingRunnable(task.getRunnable(), TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER) : new DelegatingErrorHandlingRunnable(task.getRunnable(), TaskUtils.LOG_AND_PROPAGATE_ERROR_HANDLER);
   }

   @Nullable
   public ScheduledExecutorService getObject() {
      return this.exposedExecutor;
   }

   public Class getObjectType() {
      return this.exposedExecutor != null ? this.exposedExecutor.getClass() : ScheduledExecutorService.class;
   }

   public boolean isSingleton() {
      return true;
   }
}
