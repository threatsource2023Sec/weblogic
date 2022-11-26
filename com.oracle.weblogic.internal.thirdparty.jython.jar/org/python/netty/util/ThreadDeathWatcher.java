package org.python.netty.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.python.netty.util.concurrent.DefaultThreadFactory;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class ThreadDeathWatcher {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadDeathWatcher.class);
   static final ThreadFactory threadFactory;
   private static final Queue pendingEntries = new ConcurrentLinkedQueue();
   private static final Watcher watcher = new Watcher();
   private static final AtomicBoolean started = new AtomicBoolean();
   private static volatile Thread watcherThread;

   public static void watch(Thread thread, Runnable task) {
      if (thread == null) {
         throw new NullPointerException("thread");
      } else if (task == null) {
         throw new NullPointerException("task");
      } else if (!thread.isAlive()) {
         throw new IllegalArgumentException("thread must be alive.");
      } else {
         schedule(thread, task, true);
      }
   }

   public static void unwatch(Thread thread, Runnable task) {
      if (thread == null) {
         throw new NullPointerException("thread");
      } else if (task == null) {
         throw new NullPointerException("task");
      } else {
         schedule(thread, task, false);
      }
   }

   private static void schedule(Thread thread, Runnable task, boolean isWatch) {
      pendingEntries.add(new Entry(thread, task, isWatch));
      if (started.compareAndSet(false, true)) {
         Thread watcherThread = threadFactory.newThread(watcher);
         watcherThread.start();
         ThreadDeathWatcher.watcherThread = watcherThread;
      }

   }

   public static boolean awaitInactivity(long timeout, TimeUnit unit) throws InterruptedException {
      if (unit == null) {
         throw new NullPointerException("unit");
      } else {
         Thread watcherThread = ThreadDeathWatcher.watcherThread;
         if (watcherThread != null) {
            watcherThread.join(unit.toMillis(timeout));
            return !watcherThread.isAlive();
         } else {
            return true;
         }
      }
   }

   private ThreadDeathWatcher() {
   }

   static {
      String poolName = "threadDeathWatcher";
      String serviceThreadPrefix = SystemPropertyUtil.get("org.python.netty.serviceThreadPrefix");
      if (!StringUtil.isNullOrEmpty(serviceThreadPrefix)) {
         poolName = serviceThreadPrefix + poolName;
      }

      threadFactory = new DefaultThreadFactory(poolName, true, 1, (ThreadGroup)null);
   }

   private static final class Entry {
      final Thread thread;
      final Runnable task;
      final boolean isWatch;

      Entry(Thread thread, Runnable task, boolean isWatch) {
         this.thread = thread;
         this.task = task;
         this.isWatch = isWatch;
      }

      public int hashCode() {
         return this.thread.hashCode() ^ this.task.hashCode();
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (!(obj instanceof Entry)) {
            return false;
         } else {
            Entry that = (Entry)obj;
            return this.thread == that.thread && this.task == that.task;
         }
      }
   }

   private static final class Watcher implements Runnable {
      private final List watchees;

      private Watcher() {
         this.watchees = new ArrayList();
      }

      public void run() {
         while(true) {
            this.fetchWatchees();
            this.notifyWatchees();
            this.fetchWatchees();
            this.notifyWatchees();

            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var2) {
            }

            if (this.watchees.isEmpty() && ThreadDeathWatcher.pendingEntries.isEmpty()) {
               boolean stopped = ThreadDeathWatcher.started.compareAndSet(true, false);

               assert stopped;

               if (ThreadDeathWatcher.pendingEntries.isEmpty() || !ThreadDeathWatcher.started.compareAndSet(false, true)) {
                  return;
               }
            }
         }
      }

      private void fetchWatchees() {
         while(true) {
            Entry e = (Entry)ThreadDeathWatcher.pendingEntries.poll();
            if (e == null) {
               return;
            }

            if (e.isWatch) {
               this.watchees.add(e);
            } else {
               this.watchees.remove(e);
            }
         }
      }

      private void notifyWatchees() {
         List watchees = this.watchees;
         int i = 0;

         while(i < watchees.size()) {
            Entry e = (Entry)watchees.get(i);
            if (!e.thread.isAlive()) {
               watchees.remove(i);

               try {
                  e.task.run();
               } catch (Throwable var5) {
                  ThreadDeathWatcher.logger.warn("Thread death watcher task raised an exception:", var5);
               }
            } else {
               ++i;
            }
         }

      }

      // $FF: synthetic method
      Watcher(Object x0) {
         this();
      }
   }
}
