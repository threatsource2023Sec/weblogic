package org.glassfish.grizzly.threadpool;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;

public class GrizzlyExecutorService extends AbstractExecutorService implements MonitoringAware {
   private final Object statelock = new Object();
   private volatile AbstractThreadPool pool;
   protected volatile ThreadPoolConfig config;

   public static GrizzlyExecutorService createInstance() {
      return createInstance(ThreadPoolConfig.defaultConfig());
   }

   public static GrizzlyExecutorService createInstance(ThreadPoolConfig cfg) {
      return new GrizzlyExecutorService(cfg);
   }

   protected GrizzlyExecutorService(ThreadPoolConfig config) {
      this.setImpl(config);
   }

   protected final void setImpl(ThreadPoolConfig cfg) {
      if (cfg == null) {
         throw new IllegalArgumentException("config is null");
      } else {
         cfg = cfg.copy();
         if (cfg.getMemoryManager() == null) {
            cfg.setMemoryManager(MemoryManager.DEFAULT_MEMORY_MANAGER);
         }

         Queue queue = cfg.getQueue();
         if (queue != null && !(queue instanceof BlockingQueue) || cfg.getCorePoolSize() >= 0 && cfg.getCorePoolSize() != cfg.getMaxPoolSize()) {
            this.pool = new SyncThreadPool(cfg);
         } else {
            this.pool = (AbstractThreadPool)(cfg.getQueueLimit() < 0 ? new FixedThreadPool(cfg) : new QueueLimitedThreadPool(cfg));
         }

         this.config = cfg;
      }
   }

   public GrizzlyExecutorService reconfigure(ThreadPoolConfig config) {
      synchronized(this.statelock) {
         AbstractThreadPool oldpool = this.pool;
         if (config.getQueue() == oldpool.getQueue()) {
            config.setQueue((Queue)null);
         }

         this.setImpl(config);
         AbstractThreadPool.drain(oldpool.getQueue(), this.pool.getQueue());
         oldpool.shutdown();
         return this;
      }
   }

   public ThreadPoolConfig getConfiguration() {
      return this.config.copy();
   }

   public void shutdown() {
      this.pool.shutdown();
   }

   public List shutdownNow() {
      return this.pool.shutdownNow();
   }

   public boolean isShutdown() {
      return this.pool.isShutdown();
   }

   public boolean isTerminated() {
      return this.pool.isTerminated();
   }

   public void execute(Runnable r) {
      this.pool.execute(r);
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      return this.pool.awaitTermination(timeout, unit);
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.pool.getMonitoringConfig();
   }
}
