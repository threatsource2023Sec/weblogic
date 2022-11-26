package org.glassfish.grizzly.utils;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayedExecutor {
   public static final long UNSET_TIMEOUT = -1L;
   private final ExecutorService threadPool;
   private final DelayedRunnable runnable;
   private final Queue queues;
   private final Object sync;
   private volatile boolean isStarted;
   private final long checkIntervalMillis;

   public DelayedExecutor(ExecutorService threadPool) {
      this(threadPool, 1000L, TimeUnit.MILLISECONDS);
   }

   public DelayedExecutor(ExecutorService threadPool, long checkInterval, TimeUnit timeunit) {
      this.runnable = new DelayedRunnable();
      this.queues = new ConcurrentLinkedQueue();
      this.sync = new Object();
      if (checkInterval < 0L) {
         throw new IllegalArgumentException("check interval can't be negative");
      } else {
         this.threadPool = threadPool;
         this.checkIntervalMillis = TimeUnit.MILLISECONDS.convert(checkInterval, timeunit);
      }
   }

   public void start() {
      synchronized(this.sync) {
         if (!this.isStarted) {
            this.isStarted = true;
            this.threadPool.execute(this.runnable);
         }

      }
   }

   public void stop() {
      synchronized(this.sync) {
         if (this.isStarted) {
            this.isStarted = false;
            this.sync.notify();
         }

      }
   }

   public void destroy() {
      this.stop();
      synchronized(this.sync) {
         this.queues.clear();
      }
   }

   public ExecutorService getThreadPool() {
      return this.threadPool;
   }

   public DelayQueue createDelayQueue(Worker worker, Resolver resolver) {
      DelayQueue queue = new DelayQueue(worker, resolver);
      this.queues.add(queue);
      return queue;
   }

   private static boolean wasModified(long l1, long l2) {
      return l1 != l2;
   }

   public interface Resolver {
      boolean removeTimeout(Object var1);

      long getTimeoutMillis(Object var1);

      void setTimeoutMillis(Object var1, long var2);
   }

   public interface Worker {
      boolean doWork(Object var1);
   }

   public class DelayQueue {
      final ConcurrentMap queue = new ConcurrentHashMap();
      final Worker worker;
      final Resolver resolver;

      public DelayQueue(Worker worker, Resolver resolver) {
         this.worker = worker;
         this.resolver = resolver;
      }

      public void add(Object elem, long delay, TimeUnit timeUnit) {
         if (delay >= 0L) {
            long delayWithSysTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(delay, timeUnit);
            this.resolver.setTimeoutMillis(elem, delayWithSysTime < 0L ? Long.MAX_VALUE : delayWithSysTime);
            this.queue.put(elem, this);
         }

      }

      public void remove(Object elem) {
         this.resolver.removeTimeout(elem);
      }

      public void destroy() {
         DelayedExecutor.this.queues.remove(this);
      }
   }

   private class DelayedRunnable implements Runnable {
      private DelayedRunnable() {
      }

      public void run() {
         label68:
         while(DelayedExecutor.this.isStarted) {
            long currentTimeMillis = System.currentTimeMillis();
            Iterator var3 = DelayedExecutor.this.queues.iterator();

            while(true) {
               DelayQueue delayQueue;
               do {
                  if (!var3.hasNext()) {
                     synchronized(DelayedExecutor.this.sync) {
                        if (!DelayedExecutor.this.isStarted) {
                           return;
                        }

                        try {
                           DelayedExecutor.this.sync.wait(DelayedExecutor.this.checkIntervalMillis);
                        } catch (InterruptedException var12) {
                        }
                        continue label68;
                     }
                  }

                  delayQueue = (DelayQueue)var3.next();
               } while(delayQueue.queue.isEmpty());

               Resolver resolver = delayQueue.resolver;
               Iterator it = delayQueue.queue.keySet().iterator();

               while(it.hasNext()) {
                  Object element = it.next();
                  long timeoutMillis = resolver.getTimeoutMillis(element);
                  if (timeoutMillis == -1L) {
                     it.remove();
                     if (DelayedExecutor.wasModified(timeoutMillis, resolver.getTimeoutMillis(element))) {
                        delayQueue.queue.put(element, delayQueue);
                     }
                  } else if (currentTimeMillis - timeoutMillis >= 0L) {
                     it.remove();
                     if (DelayedExecutor.wasModified(timeoutMillis, resolver.getTimeoutMillis(element))) {
                        delayQueue.queue.put(element, delayQueue);
                     } else {
                        try {
                           if (!delayQueue.worker.doWork(element)) {
                              delayQueue.queue.put(element, delayQueue);
                           }
                        } catch (Exception var14) {
                        }
                     }
                  }
               }
            }
         }

      }

      // $FF: synthetic method
      DelayedRunnable(Object x1) {
         this();
      }
   }
}
