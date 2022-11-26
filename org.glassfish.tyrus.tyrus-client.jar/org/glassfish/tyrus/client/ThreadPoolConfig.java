package org.glassfish.tyrus.client;

import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class ThreadPoolConfig {
   private static final int DEFAULT_CORE_POOL_SIZE = 1;
   private static final int DEFAULT_MAX_POOL_SIZE = Math.max(Runtime.getRuntime().availableProcessors(), 20);
   private static final int DEFAULT_MAX_QUEUE_SIZE = -1;
   private static final int DEFAULT_IDLE_THREAD_KEEP_ALIVE_TIMEOUT = 10;
   private static final ThreadPoolConfig DEFAULT;
   private String poolName;
   private int corePoolSize;
   private int maxPoolSize;
   private Queue queue;
   private int queueLimit = -1;
   private long keepAliveTimeMillis;
   private ThreadFactory threadFactory;
   private int priority = 10;
   private boolean isDaemon;
   private ClassLoader initialClassLoader;

   public static ThreadPoolConfig defaultConfig() {
      return DEFAULT.copy();
   }

   private ThreadPoolConfig(String poolName, int corePoolSize, int maxPoolSize, Queue queue, int queueLimit, long keepAliveTime, TimeUnit timeUnit, ThreadFactory threadFactory, int priority, boolean isDaemon, ClassLoader initialClassLoader) {
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
      this.initialClassLoader = initialClassLoader;
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
      this.initialClassLoader = cfg.initialClassLoader;
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

   public ThreadPoolConfig setPoolName(String poolName) {
      this.poolName = poolName;
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
      if (maxPoolSize < 3) {
         throw new IllegalArgumentException("Max thread pool size cannot be smaller than 3");
      } else {
         this.maxPoolSize = maxPoolSize;
         return this;
      }
   }

   public int getCorePoolSize() {
      return this.corePoolSize;
   }

   public ThreadPoolConfig setCorePoolSize(int corePoolSize) {
      if (corePoolSize < 0) {
         throw new IllegalArgumentException("Core thread pool size cannot be smaller than 0");
      } else {
         this.corePoolSize = corePoolSize;
         return this;
      }
   }

   public int getQueueLimit() {
      return this.queueLimit;
   }

   public ThreadPoolConfig setQueueLimit(int queueLimit) {
      if (queueLimit < 0) {
         this.queueLimit = -1;
      } else {
         this.queueLimit = queueLimit;
      }

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

   public ClassLoader getInitialClassLoader() {
      return this.initialClassLoader;
   }

   public ThreadPoolConfig setInitialClassLoader(ClassLoader initialClassLoader) {
      this.initialClassLoader = initialClassLoader;
      return this;
   }

   public String toString() {
      return ThreadPoolConfig.class.getSimpleName() + " :\r\n  poolName: " + this.poolName + "\r\n  corePoolSize: " + this.corePoolSize + "\r\n  maxPoolSize: " + this.maxPoolSize + "\r\n  queue: " + (this.queue != null ? this.queue.getClass() : "undefined") + "\r\n  queueLimit: " + this.queueLimit + "\r\n  keepAliveTime (millis): " + this.keepAliveTimeMillis + "\r\n  threadFactory: " + this.threadFactory + "\r\n  priority: " + this.priority + "\r\n  isDaemon: " + this.isDaemon + "\r\n  initialClassLoader: " + this.initialClassLoader;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ThreadPoolConfig that = (ThreadPoolConfig)o;
         if (this.corePoolSize != that.corePoolSize) {
            return false;
         } else if (this.isDaemon != that.isDaemon) {
            return false;
         } else if (this.keepAliveTimeMillis != that.keepAliveTimeMillis) {
            return false;
         } else if (this.maxPoolSize != that.maxPoolSize) {
            return false;
         } else if (this.priority != that.priority) {
            return false;
         } else if (this.queueLimit != that.queueLimit) {
            return false;
         } else {
            label72: {
               if (this.initialClassLoader != null) {
                  if (this.initialClassLoader.equals(that.initialClassLoader)) {
                     break label72;
                  }
               } else if (that.initialClassLoader == null) {
                  break label72;
               }

               return false;
            }

            label65: {
               if (this.poolName != null) {
                  if (this.poolName.equals(that.poolName)) {
                     break label65;
                  }
               } else if (that.poolName == null) {
                  break label65;
               }

               return false;
            }

            if (this.queue != null) {
               if (!this.queue.equals(that.queue)) {
                  return false;
               }
            } else if (that.queue != null) {
               return false;
            }

            if (this.threadFactory != null) {
               if (!this.threadFactory.equals(that.threadFactory)) {
                  return false;
               }
            } else if (that.threadFactory != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.poolName != null ? this.poolName.hashCode() : 0;
      result = 31 * result + this.corePoolSize;
      result = 31 * result + this.maxPoolSize;
      result = 31 * result + (this.queue != null ? this.queue.hashCode() : 0);
      result = 31 * result + this.queueLimit;
      result = 31 * result + (int)(this.keepAliveTimeMillis ^ this.keepAliveTimeMillis >>> 32);
      result = 31 * result + (this.threadFactory != null ? this.threadFactory.hashCode() : 0);
      result = 31 * result + this.priority;
      result = 31 * result + (this.isDaemon ? 1 : 0);
      result = 31 * result + (this.initialClassLoader != null ? this.initialClassLoader.hashCode() : 0);
      return result;
   }

   static {
      DEFAULT = new ThreadPoolConfig("Tyrus-client", 1, DEFAULT_MAX_POOL_SIZE, (Queue)null, -1, 10L, TimeUnit.SECONDS, (ThreadFactory)null, 5, true, (ClassLoader)null);
   }
}
