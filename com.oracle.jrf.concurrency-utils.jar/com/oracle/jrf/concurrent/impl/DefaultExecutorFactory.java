package com.oracle.jrf.concurrent.impl;

import com.oracle.jrf.concurrent.ExecutorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.naming.NameNotFoundException;

public final class DefaultExecutorFactory extends ExecutorFactory {
   private final Map executors = new ConcurrentHashMap();
   private static final RuntimePermission shutdownPerm = new RuntimePermission("modifyThread");

   public DefaultExecutorFactory() {
      Runtime.getRuntime().addShutdownHook(new Thread() {
         public void run() {
            DefaultExecutorFactory.this.shutdownAll();
         }
      });
   }

   public ExecutorService getExecutor(String name) {
      Util.checkNull(name);
      ExecutorService e = (ExecutorService)this.executors.get(name);
      if (e == null) {
         this.executors.put(name, this.newExecutor(name));
      }

      return (ExecutorService)this.executors.get(name);
   }

   public ScheduledExecutorService getScheduler(String name) throws NameNotFoundException {
      Util.checkNull(name);
      ExecutorService e = (ExecutorService)this.executors.get(name);
      if (e == null) {
         this.executors.put(name, this.newScheduler(name));
         return (ScheduledExecutorService)this.executors.get(name);
      } else {
         try {
            return (ScheduledExecutorService)ScheduledExecutorService.class.cast(e);
         } catch (ClassCastException var4) {
            throw new NameNotFoundException("ExecutorService called " + name + " is not a ScheduledExecutorService.");
         }
      }
   }

   public Callable wrapAsLongRunningTask(Callable task) {
      Util.checkNull(task);
      return task;
   }

   public Runnable wrapAsLongRunningTask(Runnable task) {
      Util.checkNull(task);
      return task;
   }

   public Callable wrapAsTenantTask(Callable task) {
      Util.checkNull(task);
      return task;
   }

   public Runnable wrapAsTenantTask(Runnable task) {
      Util.checkNull(task);
      return task;
   }

   public Executor wrapAsSerialExecutor(Executor e) {
      Util.checkNull(e);
      return new SerialExecutor(e);
   }

   public synchronized List shutdownAll() {
      this.checkShutdownAllPermission();
      List uncommencedTasks = new ArrayList();
      Iterator var2 = this.executors.values().iterator();

      while(var2.hasNext()) {
         DefaultExecutorService e = (DefaultExecutorService)var2.next();
         uncommencedTasks.addAll(e.getDelegate().shutdownNow());
      }

      this.executors.clear();
      return uncommencedTasks;
   }

   private void checkShutdownAllPermission() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(shutdownPerm);
      }

   }

   private DefaultExecutorService newExecutor(String name) {
      ThreadFactory tf = new DefaultThreadFactory(name);
      ExecutorService delegate = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new SynchronousQueue(), tf);
      return new DefaultExecutorService(delegate);
   }

   private DefaultExecutorService newScheduler(String name) {
      ThreadFactory tf = new DefaultThreadFactory(name);
      ScheduledExecutorService s = Executors.newScheduledThreadPool(0, tf);
      return new DefaultScheduledExecutorService(s);
   }

   private static class DefaultThreadFactory implements ThreadFactory {
      private final String name;
      private final AtomicLong threadNo = new AtomicLong(1L);

      private boolean isDaemonThread(Runnable r) {
         return !Boolean.getBoolean("com.oracle.jrf.concurrent.CreateNonDaemonThreadOnJavaSE");
      }

      public DefaultThreadFactory(String name) {
         this.name = name;
      }

      public Thread newThread(Runnable r) {
         Thread t = Executors.defaultThreadFactory().newThread(r);
         t.setName(this.name + "-thread-" + this.threadNo.getAndIncrement());
         t.setDaemon(this.isDaemonThread(r));
         return t;
      }
   }
}
