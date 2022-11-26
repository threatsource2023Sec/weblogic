package org.jboss.weld.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorServices extends AbstractExecutorServices {
   private final transient ExecutorService taskExecutor = Executors.newSingleThreadExecutor();

   public ExecutorService getTaskExecutor() {
      return this.taskExecutor;
   }

   protected int getThreadPoolSize() {
      return 1;
   }
}
