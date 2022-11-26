package org.jboss.weld.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jboss.weld.logging.BootstrapLogger;

public class FixedThreadPoolExecutorServices extends AbstractExecutorServices {
   private final int threadPoolSize;
   private final ExecutorService executor;

   public FixedThreadPoolExecutorServices(int threadPoolSize) {
      this.threadPoolSize = threadPoolSize;
      this.executor = Executors.newFixedThreadPool(threadPoolSize, new DaemonThreadFactory(new ThreadGroup("weld-workers"), "weld-worker-"));
      BootstrapLogger.LOG.threadsInUse(threadPoolSize);
   }

   public ExecutorService getTaskExecutor() {
      return this.executor;
   }

   public int getThreadPoolSize() {
      return this.threadPoolSize;
   }

   public String toString() {
      return "FixedThreadPoolExecutorServices [threadPoolSize=" + this.threadPoolSize + "]";
   }
}
