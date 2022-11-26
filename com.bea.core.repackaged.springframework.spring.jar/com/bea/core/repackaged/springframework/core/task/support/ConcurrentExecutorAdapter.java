package com.bea.core.repackaged.springframework.core.task.support;

import com.bea.core.repackaged.springframework.core.task.TaskExecutor;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.concurrent.Executor;

public class ConcurrentExecutorAdapter implements Executor {
   private final TaskExecutor taskExecutor;

   public ConcurrentExecutorAdapter(TaskExecutor taskExecutor) {
      Assert.notNull(taskExecutor, (String)"TaskExecutor must not be null");
      this.taskExecutor = taskExecutor;
   }

   public void execute(Runnable command) {
      this.taskExecutor.execute(command);
   }
}
