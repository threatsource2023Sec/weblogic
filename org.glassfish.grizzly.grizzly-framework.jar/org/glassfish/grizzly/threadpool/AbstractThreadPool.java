package org.glassfish.grizzly.threadpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.memory.ThreadLocalPoolProvider;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.utils.DelayedExecutor;

public abstract class AbstractThreadPool extends AbstractExecutorService implements Thread.UncaughtExceptionHandler, MonitoringAware {
   private static final Logger logger = Grizzly.logger(AbstractThreadPool.class);
   public static final int DEFAULT_MIN_THREAD_COUNT;
   public static final int DEFAULT_MAX_THREAD_COUNT;
   private static final Long NEVER_TIMEOUT = Long.MAX_VALUE;
   public static final int DEFAULT_MAX_TASKS_QUEUED = -1;
   public static final int DEFAULT_IDLE_THREAD_KEEPALIVE_TIMEOUT = 30000;
   protected static final Runnable poison;
   protected final Object stateLock = new Object();
   protected final Map workers = new HashMap();
   protected volatile boolean running = true;
   protected final ThreadPoolConfig config;
   protected final long transactionTimeoutMillis;
   protected final DelayedExecutor.DelayQueue delayedQueue;
   private static final DelayedExecutor.Resolver transactionResolver;
   protected final DefaultMonitoringConfig monitoringConfig = new DefaultMonitoringConfig(ThreadPoolProbe.class) {
      public Object createManagementObject() {
         return AbstractThreadPool.this.createJmxManagementObject();
      }
   };

   public AbstractThreadPool(ThreadPoolConfig config) {
      if (config.getMaxPoolSize() < 1) {
         throw new IllegalArgumentException("poolsize < 1");
      } else {
         this.config = config;
         if (config.getInitialMonitoringConfig().hasProbes()) {
            this.monitoringConfig.addProbes(config.getInitialMonitoringConfig().getProbes());
         }

         if (config.getThreadFactory() == null) {
            config.setThreadFactory(this.getDefaultThreadFactory());
         }

         this.transactionTimeoutMillis = config.getTransactionTimeout(TimeUnit.MILLISECONDS);
         DelayedExecutor transactionMonitor = this.transactionTimeoutMillis > 0L ? config.getTransactionMonitor() : null;
         if (transactionMonitor != null) {
            DelayedExecutor.Worker transactionWorker = new DelayedExecutor.Worker() {
               public boolean doWork(Worker worker) {
                  worker.t.interrupt();
                  AbstractThreadPool.this.delayedQueue.add(worker, AbstractThreadPool.NEVER_TIMEOUT, TimeUnit.MILLISECONDS);
                  return true;
               }
            };
            this.delayedQueue = transactionMonitor.createDelayQueue(transactionWorker, transactionResolver);
         } else {
            this.delayedQueue = null;
         }

      }
   }

   protected void startWorker(Worker worker) {
      Thread thread = this.config.getThreadFactory().newThread(worker);
      worker.t = thread;
      this.workers.put(worker, System.currentTimeMillis());
      worker.t.start();
   }

   public ThreadPoolConfig getConfig() {
      return this.config;
   }

   public Queue getQueue() {
      return this.config.getQueue();
   }

   public final int getSize() {
      synchronized(this.stateLock) {
         return this.workers.size();
      }
   }

   public List shutdownNow() {
      synchronized(this.stateLock) {
         List drained = new ArrayList();
         if (this.running) {
            this.running = false;
            drain(this.getQueue(), drained);
            Iterator var3 = drained.iterator();

            while(var3.hasNext()) {
               Runnable task = (Runnable)var3.next();
               this.onTaskDequeued(task);
               this.onTaskCancelled(task);
            }

            this.poisonAll();
            var3 = this.workers.keySet().iterator();

            while(var3.hasNext()) {
               Worker w = (Worker)var3.next();
               w.t.interrupt();
            }

            ProbeNotifier.notifyThreadPoolStopped(this);
         }

         return drained;
      }
   }

   public void shutdown() {
      synchronized(this.stateLock) {
         if (this.running) {
            this.running = false;
            this.poisonAll();
            this.stateLock.notifyAll();
            ProbeNotifier.notifyThreadPoolStopped(this);
         }

      }
   }

   public boolean isShutdown() {
      return !this.running;
   }

   public boolean isTerminated() {
      synchronized(this.stateLock) {
         return !this.running && this.workers.isEmpty();
      }
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      long millis = unit.toMillis(timeout);
      long timeEnd = System.currentTimeMillis() + millis;
      synchronized(this.stateLock) {
         if (this.isTerminated()) {
            return true;
         } else {
            while(millis >= 20L) {
               this.stateLock.wait(millis);
               if (this.isTerminated()) {
                  return true;
               }

               millis = timeEnd - System.currentTimeMillis();
            }

            return false;
         }
      }
   }

   protected void poisonAll() {
      int size = Math.max(this.config.getMaxPoolSize(), this.workers.size()) * 4 / 3;
      Queue q = this.getQueue();

      while(size-- > 0) {
         q.offer(poison);
      }

   }

   protected static void drain(Queue from, Collection to) {
      boolean cont = true;

      while(cont) {
         Runnable r = (Runnable)from.poll();
         if ((cont = r != null) && r != poison) {
            to.add(r);
         }
      }

   }

   protected void validateNewPoolSize(int corePoolsize, int maxPoolSize) {
      if (maxPoolSize < 1) {
         throw new IllegalArgumentException("maxPoolsize < 1 :" + maxPoolSize);
      } else if (corePoolsize < 1) {
         throw new IllegalArgumentException("corePoolsize < 1 :" + corePoolsize);
      } else if (corePoolsize > maxPoolSize) {
         throw new IllegalArgumentException("corePoolsize > maxPoolSize: " + corePoolsize + " > " + maxPoolSize);
      }
   }

   protected void beforeExecute(Worker worker, Thread t, Runnable r) {
      if (this.delayedQueue != null) {
         worker.transactionExpirationTime = System.currentTimeMillis() + this.transactionTimeoutMillis;
      }

      ClassLoader initial = this.config.getInitialClassLoader();
      if (initial != null) {
         t.setContextClassLoader(initial);
      }

   }

   protected void afterExecute(Worker worker, Thread thread, Runnable r, Throwable t) {
      if (this.delayedQueue != null) {
         worker.transactionExpirationTime = NEVER_TIMEOUT;
      }

   }

   protected void onTaskCompletedEvent(Runnable task) {
      ProbeNotifier.notifyTaskCompleted(this, task);
   }

   protected void onWorkerStarted(Worker worker) {
      if (this.delayedQueue != null) {
         this.delayedQueue.add(worker, NEVER_TIMEOUT, TimeUnit.MILLISECONDS);
      }

      ProbeNotifier.notifyThreadAllocated(this, worker.t);
   }

   protected void onWorkerExit(Worker worker) {
      synchronized(this.stateLock) {
         this.workers.remove(worker);
         if (this.delayedQueue != null) {
            this.delayedQueue.remove(worker);
         }

         if (this.workers.isEmpty()) {
            this.stateLock.notifyAll();
         }
      }

      ProbeNotifier.notifyThreadReleased(this, worker.t);
   }

   protected void onMaxNumberOfThreadsReached() {
      ProbeNotifier.notifyMaxNumberOfThreads(this, this.config.getMaxPoolSize());
   }

   protected void onTaskQueued(Runnable task) {
      ProbeNotifier.notifyTaskQueued(this, task);
   }

   protected void onTaskDequeued(Runnable task) {
      ProbeNotifier.notifyTaskDequeued(this, task);
   }

   protected void onTaskCancelled(Runnable task) {
      ProbeNotifier.notifyTaskCancelled(this, task);
   }

   protected void onTaskQueueOverflow() {
      ProbeNotifier.notifyTaskQueueOverflow(this);
      throw new RejectedExecutionException("The thread pool's task queue is full, limit: " + this.config.getQueueLimit());
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   public void uncaughtException(Thread thread, Throwable throwable) {
      logger.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_THREADPOOL_UNCAUGHT_EXCEPTION(thread), throwable);
   }

   Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.threadpool.jmx.ThreadPool", this, AbstractThreadPool.class);
   }

   protected final ThreadFactory getDefaultThreadFactory() {
      final AtomicInteger counter = new AtomicInteger();
      return new ThreadFactory() {
         public Thread newThread(Runnable r) {
            MemoryManager mm = AbstractThreadPool.this.config.getMemoryManager();
            ThreadLocalPoolProvider threadLocalPoolProvider;
            if (mm instanceof ThreadLocalPoolProvider) {
               threadLocalPoolProvider = (ThreadLocalPoolProvider)mm;
            } else {
               threadLocalPoolProvider = null;
            }

            DefaultWorkerThread thread = new DefaultWorkerThread(Grizzly.DEFAULT_ATTRIBUTE_BUILDER, AbstractThreadPool.this.config.getPoolName() + '(' + counter.incrementAndGet() + ')', threadLocalPoolProvider != null ? threadLocalPoolProvider.createThreadLocalPool() : null, r);
            thread.setUncaughtExceptionHandler(AbstractThreadPool.this);
            thread.setPriority(AbstractThreadPool.this.config.getPriority());
            thread.setDaemon(AbstractThreadPool.this.config.isDaemon());
            ClassLoader initial = AbstractThreadPool.this.config.getInitialClassLoader();
            if (initial != null) {
               thread.setContextClassLoader(initial);
            }

            return thread;
         }
      };
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(256);
      sb.append(this.getClass().getSimpleName());
      sb.append(" config: [").append(this.config.toString()).append("]\r\n");
      sb.append(", is-shutdown=").append(this.isShutdown());
      return sb.toString();
   }

   static {
      int processorsBasedThreadCount = Runtime.getRuntime().availableProcessors();
      DEFAULT_MIN_THREAD_COUNT = processorsBasedThreadCount > 5 ? processorsBasedThreadCount : 5;
      DEFAULT_MAX_THREAD_COUNT = Integer.MAX_VALUE;
      poison = new Runnable() {
         public void run() {
         }
      };
      transactionResolver = new DelayedExecutor.Resolver() {
         public boolean removeTimeout(Worker element) {
            element.transactionExpirationTime = -1L;
            return true;
         }

         public long getTimeoutMillis(Worker element) {
            return element.transactionExpirationTime;
         }

         public void setTimeoutMillis(Worker element, long timeoutMillis) {
            element.transactionExpirationTime = timeoutMillis;
         }
      };
   }

   public abstract class Worker implements Runnable {
      protected Thread t;
      protected volatile long transactionExpirationTime;

      public void run() {
         try {
            AbstractThreadPool.this.onWorkerStarted(this);
            this.doWork();
         } finally {
            AbstractThreadPool.this.onWorkerExit(this);
         }

      }

      protected void doWork() {
         Thread thread = this.t;

         while(true) {
            try {
               Thread.interrupted();
               Runnable r = this.getTask();
               if (r == AbstractThreadPool.poison || r == null) {
                  return;
               }

               AbstractThreadPool.this.onTaskDequeued(r);
               Throwable error = null;

               try {
                  AbstractThreadPool.this.beforeExecute(this, thread, r);
                  r.run();
                  AbstractThreadPool.this.onTaskCompletedEvent(r);
               } catch (Exception var9) {
                  error = var9;
               } finally {
                  AbstractThreadPool.this.afterExecute(this, thread, r, error);
               }
            } catch (Exception var11) {
            }
         }
      }

      protected abstract Runnable getTask() throws InterruptedException;
   }
}
