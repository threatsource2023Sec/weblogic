package com.bea.core.repackaged.springframework.core.task.support;

import com.bea.core.repackaged.springframework.core.task.TaskExecutor;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceAdapter extends AbstractExecutorService {
   private final TaskExecutor taskExecutor;

   public ExecutorServiceAdapter(TaskExecutor taskExecutor) {
      Assert.notNull(taskExecutor, (String)"TaskExecutor must not be null");
      this.taskExecutor = taskExecutor;
   }

   public void execute(Runnable task) {
      this.taskExecutor.execute(task);
   }

   public void shutdown() {
      throw new IllegalStateException("Manual shutdown not supported - ExecutorServiceAdapter is dependent on an external lifecycle");
   }

   public List shutdownNow() {
      throw new IllegalStateException("Manual shutdown not supported - ExecutorServiceAdapter is dependent on an external lifecycle");
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      throw new IllegalStateException("Manual shutdown not supported - ExecutorServiceAdapter is dependent on an external lifecycle");
   }

   public boolean isShutdown() {
      return false;
   }

   public boolean isTerminated() {
      return false;
   }
}
