package org.glassfish.tyrus.core;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.WebSocketContainer;

public abstract class BaseContainer extends ExecutorServiceProvider implements WebSocketContainer {
   private static final Logger LOGGER = Logger.getLogger(BaseContainer.class.getName());
   private final ExecutorService managedExecutorService = this.lookupManagedExecutorService();
   private final ScheduledExecutorService managedScheduledExecutorService = this.lookupManagedScheduledExecutorService();
   private final ThreadFactory threadFactory;
   private final Object EXECUTORS_CLEAN_UP_LOCK = new Object();
   private volatile ExecutorService executorService = null;
   private volatile ScheduledExecutorService scheduledExecutorService = null;

   public BaseContainer() {
      if (this.managedExecutorService != null && this.managedScheduledExecutorService != null) {
         this.threadFactory = null;
      } else {
         this.threadFactory = new DaemonThreadFactory();
      }

   }

   public ExecutorService getExecutorService() {
      if (this.managedExecutorService != null) {
         return this.managedExecutorService;
      } else {
         if (this.executorService == null) {
            synchronized(this.EXECUTORS_CLEAN_UP_LOCK) {
               if (this.executorService == null) {
                  this.executorService = Executors.newCachedThreadPool(this.threadFactory);
               }
            }
         }

         return this.executorService;
      }
   }

   public ScheduledExecutorService getScheduledExecutorService() {
      if (this.managedScheduledExecutorService != null) {
         return this.managedScheduledExecutorService;
      } else {
         if (this.scheduledExecutorService == null) {
            synchronized(this.EXECUTORS_CLEAN_UP_LOCK) {
               if (this.scheduledExecutorService == null) {
                  this.scheduledExecutorService = Executors.newScheduledThreadPool(10, this.threadFactory);
               }
            }
         }

         return this.scheduledExecutorService;
      }
   }

   public void shutdown() {
      if (this.executorService != null) {
         this.executorService.shutdown();
         this.executorService = null;
      }

      if (this.scheduledExecutorService != null) {
         this.scheduledExecutorService.shutdownNow();
         this.scheduledExecutorService = null;
      }

   }

   protected void shutdown(ShutDownCondition shutDownCondition) {
      synchronized(this.EXECUTORS_CLEAN_UP_LOCK) {
         if (shutDownCondition.evaluate()) {
            this.shutdown();
         }

      }
   }

   private ExecutorService lookupManagedExecutorService() {
      try {
         Class aClass = Class.forName("javax.naming.InitialContext");
         Object o = aClass.newInstance();
         Method lookupMethod = aClass.getMethod("lookup", String.class);
         return (ExecutorService)lookupMethod.invoke(o, "java:comp/DefaultManagedExecutorService");
      } catch (Exception var4) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, var4.getMessage(), var4);
         }
      } catch (LinkageError var5) {
      }

      return null;
   }

   private ScheduledExecutorService lookupManagedScheduledExecutorService() {
      try {
         Class aClass = Class.forName("javax.naming.InitialContext");
         Object o = aClass.newInstance();
         Method lookupMethod = aClass.getMethod("lookup", String.class);
         return (ScheduledExecutorService)lookupMethod.invoke(o, "java:comp/DefaultManagedScheduledExecutorService");
      } catch (Exception var4) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, var4.getMessage(), var4);
         }
      } catch (LinkageError var5) {
      }

      return null;
   }

   protected interface ShutDownCondition {
      boolean evaluate();
   }

   private static class DaemonThreadFactory implements ThreadFactory {
      static final AtomicInteger poolNumber = new AtomicInteger(1);
      final AtomicInteger threadNumber = new AtomicInteger(1);
      final String namePrefix;

      DaemonThreadFactory() {
         this.namePrefix = "tyrus-" + poolNumber.getAndIncrement() + "-thread-";
      }

      public Thread newThread(Runnable r) {
         Thread t = new Thread((ThreadGroup)null, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
         if (!t.isDaemon()) {
            t.setDaemon(true);
         }

         if (t.getPriority() != 5) {
            t.setPriority(5);
         }

         return t;
      }
   }
}
