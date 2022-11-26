package org.jboss.weld.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DaemonThreadFactory implements ThreadFactory {
   public static final String WELD_WORKERS = "weld-workers";
   private final AtomicInteger threadNumber = new AtomicInteger(1);
   private final String threadNamePrefix;
   private final ThreadGroup threadGroup;

   public DaemonThreadFactory(ThreadGroup threadGroup, String threadNamePrefix) {
      this.threadGroup = threadGroup;
      this.threadNamePrefix = threadNamePrefix;
   }

   public Thread newThread(Runnable r) {
      Thread thread = new Thread(this.threadGroup, r, this.threadNamePrefix + this.threadNumber.getAndIncrement());
      thread.setDaemon(true);
      return thread;
   }
}
