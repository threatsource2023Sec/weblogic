package com.bea.core.repackaged.springframework.core.task;

import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class SyncTaskExecutor implements TaskExecutor, Serializable {
   public void execute(Runnable task) {
      Assert.notNull(task, (String)"Runnable must not be null");
      task.run();
   }
}
