package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.core.task.AsyncListenableTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.TaskDecorator;
import com.bea.core.repackaged.springframework.core.task.support.TaskExecutorAdapter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.SchedulingAwareRunnable;
import com.bea.core.repackaged.springframework.scheduling.SchedulingTaskExecutor;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutors;
import javax.enterprise.concurrent.ManagedTaskListener;

public class ConcurrentTaskExecutor implements AsyncListenableTaskExecutor, SchedulingTaskExecutor {
   @Nullable
   private static Class managedExecutorServiceClass;
   private Executor concurrentExecutor;
   private TaskExecutorAdapter adaptedExecutor;

   public ConcurrentTaskExecutor() {
      this.concurrentExecutor = Executors.newSingleThreadExecutor();
      this.adaptedExecutor = new TaskExecutorAdapter(this.concurrentExecutor);
   }

   public ConcurrentTaskExecutor(@Nullable Executor executor) {
      this.concurrentExecutor = (Executor)(executor != null ? executor : Executors.newSingleThreadExecutor());
      this.adaptedExecutor = getAdaptedExecutor(this.concurrentExecutor);
   }

   public final void setConcurrentExecutor(@Nullable Executor executor) {
      this.concurrentExecutor = (Executor)(executor != null ? executor : Executors.newSingleThreadExecutor());
      this.adaptedExecutor = getAdaptedExecutor(this.concurrentExecutor);
   }

   public final Executor getConcurrentExecutor() {
      return this.concurrentExecutor;
   }

   public final void setTaskDecorator(TaskDecorator taskDecorator) {
      this.adaptedExecutor.setTaskDecorator(taskDecorator);
   }

   public void execute(Runnable task) {
      this.adaptedExecutor.execute(task);
   }

   public void execute(Runnable task, long startTimeout) {
      this.adaptedExecutor.execute(task, startTimeout);
   }

   public Future submit(Runnable task) {
      return this.adaptedExecutor.submit(task);
   }

   public Future submit(Callable task) {
      return this.adaptedExecutor.submit(task);
   }

   public ListenableFuture submitListenable(Runnable task) {
      return this.adaptedExecutor.submitListenable(task);
   }

   public ListenableFuture submitListenable(Callable task) {
      return this.adaptedExecutor.submitListenable(task);
   }

   private static TaskExecutorAdapter getAdaptedExecutor(Executor concurrentExecutor) {
      return (TaskExecutorAdapter)(managedExecutorServiceClass != null && managedExecutorServiceClass.isInstance(concurrentExecutor) ? new ManagedTaskExecutorAdapter(concurrentExecutor) : new TaskExecutorAdapter(concurrentExecutor));
   }

   static {
      try {
         managedExecutorServiceClass = ClassUtils.forName("javax.enterprise.concurrent.ManagedExecutorService", ConcurrentTaskScheduler.class.getClassLoader());
      } catch (ClassNotFoundException var1) {
         managedExecutorServiceClass = null;
      }

   }

   protected static class ManagedTaskBuilder {
      public static Runnable buildManagedTask(Runnable task, String identityName) {
         HashMap properties;
         if (task instanceof SchedulingAwareRunnable) {
            properties = new HashMap(4);
            properties.put("javax.enterprise.concurrent.LONGRUNNING_HINT", Boolean.toString(((SchedulingAwareRunnable)task).isLongLived()));
         } else {
            properties = new HashMap(2);
         }

         properties.put("javax.enterprise.concurrent.IDENTITY_NAME", identityName);
         return ManagedExecutors.managedTask(task, properties, (ManagedTaskListener)null);
      }

      public static Callable buildManagedTask(Callable task, String identityName) {
         Map properties = new HashMap(2);
         properties.put("javax.enterprise.concurrent.IDENTITY_NAME", identityName);
         return ManagedExecutors.managedTask(task, properties, (ManagedTaskListener)null);
      }
   }

   private static class ManagedTaskExecutorAdapter extends TaskExecutorAdapter {
      public ManagedTaskExecutorAdapter(Executor concurrentExecutor) {
         super(concurrentExecutor);
      }

      public void execute(Runnable task) {
         super.execute(ConcurrentTaskExecutor.ManagedTaskBuilder.buildManagedTask(task, task.toString()));
      }

      public Future submit(Runnable task) {
         return super.submit(ConcurrentTaskExecutor.ManagedTaskBuilder.buildManagedTask(task, task.toString()));
      }

      public Future submit(Callable task) {
         return super.submit(ConcurrentTaskExecutor.ManagedTaskBuilder.buildManagedTask(task, task.toString()));
      }

      public ListenableFuture submitListenable(Runnable task) {
         return super.submitListenable(ConcurrentTaskExecutor.ManagedTaskBuilder.buildManagedTask(task, task.toString()));
      }

      public ListenableFuture submitListenable(Callable task) {
         return super.submitListenable(ConcurrentTaskExecutor.ManagedTaskBuilder.buildManagedTask(task, task.toString()));
      }
   }
}
