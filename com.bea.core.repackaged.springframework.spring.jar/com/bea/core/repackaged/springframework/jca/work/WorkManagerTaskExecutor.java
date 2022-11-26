package com.bea.core.repackaged.springframework.jca.work;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.core.task.AsyncListenableTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.TaskDecorator;
import com.bea.core.repackaged.springframework.core.task.TaskRejectedException;
import com.bea.core.repackaged.springframework.core.task.TaskTimeoutException;
import com.bea.core.repackaged.springframework.jca.context.BootstrapContextAware;
import com.bea.core.repackaged.springframework.jndi.JndiLocatorSupport;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.SchedulingException;
import com.bea.core.repackaged.springframework.scheduling.SchedulingTaskExecutor;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import javax.naming.NamingException;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkManager;
import javax.resource.spi.work.WorkRejectedException;

public class WorkManagerTaskExecutor extends JndiLocatorSupport implements AsyncListenableTaskExecutor, SchedulingTaskExecutor, WorkManager, BootstrapContextAware, InitializingBean {
   @Nullable
   private WorkManager workManager;
   @Nullable
   private String workManagerName;
   private boolean blockUntilStarted = false;
   private boolean blockUntilCompleted = false;
   @Nullable
   private WorkListener workListener;
   @Nullable
   private TaskDecorator taskDecorator;

   public WorkManagerTaskExecutor() {
   }

   public WorkManagerTaskExecutor(WorkManager workManager) {
      this.setWorkManager(workManager);
   }

   public void setWorkManager(WorkManager workManager) {
      Assert.notNull(workManager, (String)"WorkManager must not be null");
      this.workManager = workManager;
   }

   public void setWorkManagerName(String workManagerName) {
      this.workManagerName = workManagerName;
   }

   public void setBootstrapContext(BootstrapContext bootstrapContext) {
      Assert.notNull(bootstrapContext, (String)"BootstrapContext must not be null");
      this.workManager = bootstrapContext.getWorkManager();
   }

   public void setBlockUntilStarted(boolean blockUntilStarted) {
      this.blockUntilStarted = blockUntilStarted;
   }

   public void setBlockUntilCompleted(boolean blockUntilCompleted) {
      this.blockUntilCompleted = blockUntilCompleted;
   }

   public void setWorkListener(@Nullable WorkListener workListener) {
      this.workListener = workListener;
   }

   public void setTaskDecorator(TaskDecorator taskDecorator) {
      this.taskDecorator = taskDecorator;
   }

   public void afterPropertiesSet() throws NamingException {
      if (this.workManager == null) {
         if (this.workManagerName != null) {
            this.workManager = (WorkManager)this.lookup(this.workManagerName, WorkManager.class);
         } else {
            this.workManager = this.getDefaultWorkManager();
         }
      }

   }

   protected WorkManager getDefaultWorkManager() {
      return new SimpleTaskWorkManager();
   }

   private WorkManager obtainWorkManager() {
      Assert.state(this.workManager != null, "No WorkManager specified");
      return this.workManager;
   }

   public void execute(Runnable task) {
      this.execute(task, Long.MAX_VALUE);
   }

   public void execute(Runnable task, long startTimeout) {
      Work work = new DelegatingWork(this.taskDecorator != null ? this.taskDecorator.decorate(task) : task);

      try {
         if (this.blockUntilCompleted) {
            if (startTimeout == Long.MAX_VALUE && this.workListener == null) {
               this.obtainWorkManager().doWork(work);
            } else {
               this.obtainWorkManager().doWork(work, startTimeout, (ExecutionContext)null, this.workListener);
            }
         } else if (this.blockUntilStarted) {
            if (startTimeout == Long.MAX_VALUE && this.workListener == null) {
               this.obtainWorkManager().startWork(work);
            } else {
               this.obtainWorkManager().startWork(work, startTimeout, (ExecutionContext)null, this.workListener);
            }
         } else if (startTimeout == Long.MAX_VALUE && this.workListener == null) {
            this.obtainWorkManager().scheduleWork(work);
         } else {
            this.obtainWorkManager().scheduleWork(work, startTimeout, (ExecutionContext)null, this.workListener);
         }

      } catch (WorkRejectedException var6) {
         if ("1".equals(var6.getErrorCode())) {
            throw new TaskTimeoutException("JCA WorkManager rejected task because of timeout: " + task, var6);
         } else {
            throw new TaskRejectedException("JCA WorkManager rejected task: " + task, var6);
         }
      } catch (WorkException var7) {
         throw new SchedulingException("Could not schedule task on JCA WorkManager", var7);
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

   public void doWork(Work work) throws WorkException {
      this.obtainWorkManager().doWork(work);
   }

   public void doWork(Work work, long delay, ExecutionContext executionContext, WorkListener workListener) throws WorkException {
      this.obtainWorkManager().doWork(work, delay, executionContext, workListener);
   }

   public long startWork(Work work) throws WorkException {
      return this.obtainWorkManager().startWork(work);
   }

   public long startWork(Work work, long delay, ExecutionContext executionContext, WorkListener workListener) throws WorkException {
      return this.obtainWorkManager().startWork(work, delay, executionContext, workListener);
   }

   public void scheduleWork(Work work) throws WorkException {
      this.obtainWorkManager().scheduleWork(work);
   }

   public void scheduleWork(Work work, long delay, ExecutionContext executionContext, WorkListener workListener) throws WorkException {
      this.obtainWorkManager().scheduleWork(work, delay, executionContext, workListener);
   }
}
