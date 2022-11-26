package com.bea.core.repackaged.springframework.core.task.support;

import com.bea.core.repackaged.springframework.core.task.AsyncListenableTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.TaskDecorator;
import com.bea.core.repackaged.springframework.core.task.TaskRejectedException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;

public class TaskExecutorAdapter implements AsyncListenableTaskExecutor {
   private final Executor concurrentExecutor;
   @Nullable
   private TaskDecorator taskDecorator;

   public TaskExecutorAdapter(Executor concurrentExecutor) {
      Assert.notNull(concurrentExecutor, (String)"Executor must not be null");
      this.concurrentExecutor = concurrentExecutor;
   }

   public final void setTaskDecorator(TaskDecorator taskDecorator) {
      this.taskDecorator = taskDecorator;
   }

   public void execute(Runnable task) {
      try {
         this.doExecute(this.concurrentExecutor, this.taskDecorator, task);
      } catch (RejectedExecutionException var3) {
         throw new TaskRejectedException("Executor [" + this.concurrentExecutor + "] did not accept task: " + task, var3);
      }
   }

   public void execute(Runnable task, long startTimeout) {
      this.execute(task);
   }

   public Future submit(Runnable task) {
      try {
         if (this.taskDecorator == null && this.concurrentExecutor instanceof ExecutorService) {
            return ((ExecutorService)this.concurrentExecutor).submit(task);
         } else {
            FutureTask future = new FutureTask(task, (Object)null);
            this.doExecute(this.concurrentExecutor, this.taskDecorator, future);
            return future;
         }
      } catch (RejectedExecutionException var3) {
         throw new TaskRejectedException("Executor [" + this.concurrentExecutor + "] did not accept task: " + task, var3);
      }
   }

   public Future submit(Callable task) {
      try {
         if (this.taskDecorator == null && this.concurrentExecutor instanceof ExecutorService) {
            return ((ExecutorService)this.concurrentExecutor).submit(task);
         } else {
            FutureTask future = new FutureTask(task);
            this.doExecute(this.concurrentExecutor, this.taskDecorator, future);
            return future;
         }
      } catch (RejectedExecutionException var3) {
         throw new TaskRejectedException("Executor [" + this.concurrentExecutor + "] did not accept task: " + task, var3);
      }
   }

   public ListenableFuture submitListenable(Runnable task) {
      try {
         ListenableFutureTask future = new ListenableFutureTask(task, (Object)null);
         this.doExecute(this.concurrentExecutor, this.taskDecorator, future);
         return future;
      } catch (RejectedExecutionException var3) {
         throw new TaskRejectedException("Executor [" + this.concurrentExecutor + "] did not accept task: " + task, var3);
      }
   }

   public ListenableFuture submitListenable(Callable task) {
      try {
         ListenableFutureTask future = new ListenableFutureTask(task);
         this.doExecute(this.concurrentExecutor, this.taskDecorator, future);
         return future;
      } catch (RejectedExecutionException var3) {
         throw new TaskRejectedException("Executor [" + this.concurrentExecutor + "] did not accept task: " + task, var3);
      }
   }

   protected void doExecute(Executor concurrentExecutor, @Nullable TaskDecorator taskDecorator, Runnable runnable) throws RejectedExecutionException {
      concurrentExecutor.execute(taskDecorator != null ? taskDecorator.decorate(runnable) : runnable);
   }
}
