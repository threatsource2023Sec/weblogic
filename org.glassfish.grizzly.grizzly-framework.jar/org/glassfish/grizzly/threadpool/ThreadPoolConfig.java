package org.glassfish.grizzly.threadpool;

import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.utils.DelayedExecutor;

public final class ThreadPoolConfig {
   private static final ThreadPoolConfig DEFAULT;
   protected String poolName;
   protected int corePoolSize;
   protected int maxPoolSize;
   protected Queue queue;
   protected int queueLimit = -1;
   protected long keepAliveTimeMillis;
   protected ThreadFactory threadFactory;
   protected int priority = 10;
   protected boolean isDaemon;
   protected MemoryManager mm;
   protected DelayedExecutor transactionMonitor;
   protected long transactionTimeoutMillis;
   protected ClassLoader initialClassLoader;
   protected final DefaultMonitoringConfig threadPoolMonitoringConfig;

   public static ThreadPoolConfig defaultConfig() {
      return DEFAULT.copy();
   }

   private ThreadPoolConfig(String poolName, int corePoolSize, int maxPoolSize, Queue queue, int queueLimit, long keepAliveTime, TimeUnit timeUnit, ThreadFactory threadFactory, int priority, boolean isDaemon, MemoryManager mm, DelayedExecutor transactionMonitor, long transactionTimeoutMillis, ClassLoader initialClassLoader) {
      this.poolName = poolName;
      this.corePoolSize = corePoolSize;
      this.maxPoolSize = maxPoolSize;
      this.queue = queue;
      this.queueLimit = queueLimit;
      if (keepAliveTime > 0L) {
         this.keepAliveTimeMillis = TimeUnit.MILLISECONDS.convert(keepAliveTime, timeUnit);
      } else {
         this.keepAliveTimeMillis = keepAliveTime;
      }

      this.threadFactory = threadFactory;
      this.priority = priority;
      this.isDaemon = isDaemon;
      this.mm = mm;
      this.transactionMonitor = transactionMonitor;
      this.transactionTimeoutMillis = transactionTimeoutMillis;
      this.initialClassLoader = initialClassLoader;
      this.threadPoolMonitoringConfig = new DefaultMonitoringConfig(ThreadPoolProbe.class);
   }

   private ThreadPoolConfig(ThreadPoolConfig cfg) {
      this.queue = cfg.queue;
      this.threadFactory = cfg.threadFactory;
      this.poolName = cfg.poolName;
      this.priority = cfg.priority;
      this.isDaemon = cfg.isDaemon;
      this.maxPoolSize = cfg.maxPoolSize;
      this.queueLimit = cfg.queueLimit;
      this.corePoolSize = cfg.corePoolSize;
      this.keepAliveTimeMillis = cfg.keepAliveTimeMillis;
      this.mm = cfg.mm;
      this.initialClassLoader = cfg.initialClassLoader;
      this.threadPoolMonitoringConfig = new DefaultMonitoringConfig(ThreadPoolProbe.class);
      ThreadPoolProbe[] srcProbes = (ThreadPoolProbe[])cfg.threadPoolMonitoringConfig.getProbesUnsafe();
      if (srcProbes != null) {
         this.threadPoolMonitoringConfig.addProbes(srcProbes);
      }

      this.transactionMonitor = cfg.transactionMonitor;
      this.transactionTimeoutMillis = cfg.transactionTimeoutMillis;
   }

   public ThreadPoolConfig copy() {
      return new ThreadPoolConfig(this);
   }

   public Queue getQueue() {
      return this.queue;
   }

   public ThreadPoolConfig setQueue(Queue queue) {
      this.queue = queue;
      return this;
   }

   public ThreadFactory getThreadFactory() {
      return this.threadFactory;
   }

   public ThreadPoolConfig setThreadFactory(ThreadFactory threadFactory) {
      this.threadFactory = threadFactory;
      return this;
   }

   public String getPoolName() {
      return this.poolName;
   }

   public ThreadPoolConfig setPoolName(String poolname) {
      this.poolName = poolname;
      return this;
   }

   public int getPriority() {
      return this.priority;
   }

   public ThreadPoolConfig setPriority(int priority) {
      this.priority = priority;
      return this;
   }

   public boolean isDaemon() {
      return this.isDaemon;
   }

   public ThreadPoolConfig setDaemon(boolean isDaemon) {
      this.isDaemon = isDaemon;
      return this;
   }

   public int getMaxPoolSize() {
      return this.maxPoolSize;
   }

   public ThreadPoolConfig setMaxPoolSize(int maxPoolSize) {
      this.maxPoolSize = maxPoolSize;
      return this;
   }

   public int getCorePoolSize() {
      return this.corePoolSize;
   }

   public ThreadPoolConfig setCorePoolSize(int corePoolSize) {
      this.corePoolSize = corePoolSize;
      return this;
   }

   public int getQueueLimit() {
      return this.queueLimit;
   }

   public ThreadPoolConfig setQueueLimit(int queueLimit) {
      this.queueLimit = queueLimit;
      return this;
   }

   public ThreadPoolConfig setKeepAliveTime(long time, TimeUnit unit) {
      if (time < 0L) {
         this.keepAliveTimeMillis = -1L;
      } else {
         this.keepAliveTimeMillis = TimeUnit.MILLISECONDS.convert(time, unit);
      }

      return this;
   }

   public long getKeepAliveTime(TimeUnit timeUnit) {
      return this.keepAliveTimeMillis == -1L ? -1L : timeUnit.convert(this.keepAliveTimeMillis, TimeUnit.MILLISECONDS);
   }

   public MemoryManager getMemoryManager() {
      return this.mm;
   }

   public ThreadPoolConfig setMemoryManager(MemoryManager mm) {
      this.mm = mm;
      return this;
   }

   public DefaultMonitoringConfig getInitialMonitoringConfig() {
      return this.threadPoolMonitoringConfig;
   }

   public DelayedExecutor getTransactionMonitor() {
      return this.transactionMonitor;
   }

   public ThreadPoolConfig setTransactionMonitor(DelayedExecutor transactionMonitor) {
      this.transactionMonitor = transactionMonitor;
      return this;
   }

   public long getTransactionTimeout(TimeUnit timeUnit) {
      return this.transactionTimeoutMillis > 0L ? timeUnit.convert(this.transactionTimeoutMillis, TimeUnit.MILLISECONDS) : 0L;
   }

   public ThreadPoolConfig setTransactionTimeout(long transactionTimeout, TimeUnit timeunit) {
      if (transactionTimeout > 0L) {
         this.transactionTimeoutMillis = TimeUnit.MILLISECONDS.convert(transactionTimeout, timeunit);
      } else {
         this.transactionTimeoutMillis = 0L;
      }

      return this;
   }

   public ThreadPoolConfig setTransactionTimeout(DelayedExecutor transactionMonitor, long transactionTimeout, TimeUnit timeunit) {
      this.transactionMonitor = transactionMonitor;
      return this.setTransactionTimeout(transactionTimeout, timeunit);
   }

   public ClassLoader getInitialClassLoader() {
      return this.initialClassLoader;
   }

   public ThreadPoolConfig setInitialClassLoader(ClassLoader initialClassLoader) {
      this.initialClassLoader = initialClassLoader;
      return this;
   }

   public String toString() {
      return ThreadPoolConfig.class.getSimpleName() + " :\r\n  poolName: " + this.poolName + "\r\n  corePoolSize: " + this.corePoolSize + "\r\n  maxPoolSize: " + this.maxPoolSize + "\r\n  queue: " + (this.queue != null ? this.queue.getClass() : "undefined") + "\r\n  queueLimit: " + this.queueLimit + "\r\n  keepAliveTime (millis): " + this.keepAliveTimeMillis + "\r\n  threadFactory: " + this.threadFactory + "\r\n  transactionMonitor: " + this.transactionMonitor + "\r\n  transactionTimeoutMillis: " + this.transactionTimeoutMillis + "\r\n  priority: " + this.priority + "\r\n  isDaemon: " + this.isDaemon + "\r\n  initialClassLoader: " + this.initialClassLoader;
   }

   static {
      DEFAULT = new ThreadPoolConfig("Grizzly", AbstractThreadPool.DEFAULT_MIN_THREAD_COUNT, AbstractThreadPool.DEFAULT_MAX_THREAD_COUNT, (Queue)null, -1, 30000L, TimeUnit.MILLISECONDS, (ThreadFactory)null, 5, true, (MemoryManager)null, (DelayedExecutor)null, -1L, (ClassLoader)null);
   }
}
