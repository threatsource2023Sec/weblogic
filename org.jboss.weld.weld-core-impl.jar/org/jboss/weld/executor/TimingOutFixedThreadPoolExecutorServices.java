package org.jboss.weld.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jboss.weld.logging.BootstrapLogger;

public class TimingOutFixedThreadPoolExecutorServices extends AbstractExecutorServices {
   private final int threadPoolSize;
   private long keepAliveTime;
   private final ThreadPoolExecutor executor;

   public TimingOutFixedThreadPoolExecutorServices(int threadPoolSize, long keepAliveTime) {
      this.threadPoolSize = threadPoolSize;
      this.keepAliveTime = keepAliveTime;
      this.executor = new ThreadPoolExecutor(threadPoolSize, threadPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue(), new DaemonThreadFactory(new ThreadGroup("weld-workers"), "weld-worker-"));
      this.executor.allowCoreThreadTimeOut(true);
      BootstrapLogger.LOG.threadsInUse(threadPoolSize);
   }

   public int getPoolSize() {
      return this.executor.getPoolSize();
   }

   public ExecutorService getTaskExecutor() {
      return this.executor;
   }

   protected int getThreadPoolSize() {
      return this.threadPoolSize;
   }

   public String toString() {
      return String.format("TimingOutFixedThreadPoolExecutorServices [threadPoolSize=%s, keepAliveTime=%s]", this.threadPoolSize, this.keepAliveTime);
   }
}
