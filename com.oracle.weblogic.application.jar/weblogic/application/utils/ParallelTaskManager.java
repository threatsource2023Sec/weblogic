package weblogic.application.utils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.application.internal.Controls;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.ErrorCollectionException;
import weblogic.work.Work;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class ParallelTaskManager {
   private final String name;
   private static WorkManager workManager = null;
   private boolean workScheduled;
   private final Phaser barrier;
   private final DebugLogger debugger;
   private ErrorCollectionException ece;
   private final boolean isEnabled;
   private static List disabledManagers = null;

   static synchronized WorkManager getWorkManager() {
      if (workManager == null) {
         workManager = createWorkManager(WorkManagerFactory.getInstance());
      }

      return workManager;
   }

   static WorkManager createWorkManager(WorkManagerFactory wmFactory) {
      return wmFactory.findOrCreate("AppContainer:" + UUID.randomUUID().toString(), 10, -1);
   }

   public static ParallelTaskManager create(Class codeLoc, String simpleName, DebugLogger debugger) {
      return new ParallelTaskManager(createName(codeLoc, simpleName), debugger);
   }

   public static ParallelTaskManager create(Class codeLoc, String simpleName, DebugLogger debugger, boolean isEnabled) {
      return new ParallelTaskManager(createName(codeLoc, simpleName), debugger, isEnabled);
   }

   static ParallelTaskManager create(Class codeLoc, String simpleName, DebugLogger debugger, boolean isEnabled, WorkManagerFactory wmFactory) {
      final WorkManager wm = createWorkManager(wmFactory);
      return new ParallelTaskManager(createName(codeLoc, simpleName), debugger, isEnabled) {
         protected WorkManager workManager() {
            return wm;
         }
      };
   }

   private ParallelTaskManager(String name, DebugLogger debugger) {
      this(name, debugger, isEnabled(name));
   }

   private ParallelTaskManager(String name, DebugLogger debugger, boolean isEnabled) {
      this.workScheduled = false;
      this.ece = null;
      this.name = name;
      this.barrier = new Phaser();
      this.debugger = debugger;
      this.isEnabled = isEnabled;
      if (debugger.isDebugEnabled()) {
         if (isEnabled) {
            debugger.debug("PTM " + name + " instantiated");
         } else {
            debugger.debug("PTM " + name + " instantiated but disabled");
         }
      }

   }

   private static String createName(Class codeLoc, String simpleName) {
      return codeLoc.getName() + '.' + simpleName;
   }

   private static boolean isEnabled(String name) {
      if (!Controls.paralleltaskmanager.disabled && ManagementUtils.isRuntimeAccessAvailable()) {
         if (disabledManagers != null) {
            return !disabledManagers.contains(name);
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public void schedule(Callable c) {
      if (this.debugger.isDebugEnabled()) {
         this.debugger.debug("Scheduling task with PTM " + this.name);
      }

      ParallelTask task = new ParallelTask(c);
      if (this.isEnabled) {
         if (!this.workScheduled) {
            this.workScheduled = true;
            if (this.debugger.isDebugEnabled()) {
               this.debugger.debug("Scheduling task with PTM " + this.name + ": Barrier registration for manager");
            }

            this.barrier.register();
         }

         if (this.debugger.isDebugEnabled()) {
            this.debugger.debug("Scheduling task with PTM " + this.name + ": Barrier registration for task");
         }

         this.barrier.register();
         this.workManager().schedule(task);
      } else {
         if (this.debugger.isDebugEnabled()) {
            this.debugger.debug("Scheduling task with PTM " + this.name + ": Parallelization disabled, directly invoking task");
         }

         task.run();
      }

   }

   protected WorkManager workManager() {
      return getWorkManager();
   }

   private synchronized void addThrowable(Throwable th) {
      if (this.ece == null) {
         this.ece = new ErrorCollectionException();
      }

      if (this.debugger.isDebugEnabled()) {
         this.debugger.debug("Adding throwable to PTM " + this.name + ": " + th.getMessage());
      }

      this.ece.add(th);
   }

   public ErrorCollectionException finishAndAwaitResults() {
      if (this.debugger.isDebugEnabled()) {
         this.debugger.debug("finishAndAwaitResults for PTM " + this.name + " enabled = " + this.isEnabled + ", workScheduled = " + this.workScheduled);
      }

      if (this.isEnabled && this.workScheduled) {
         this.barrier.arriveAndAwaitAdvance();
      }

      return this.ece;
   }

   // $FF: synthetic method
   ParallelTaskManager(String x0, DebugLogger x1, boolean x2, Object x3) {
      this(x0, x1, x2);
   }

   static {
      String[] array = Controls.paralleltaskmanagerparam.strValue.split(",");
      disabledManagers = Arrays.asList(array);
   }

   private class ParallelTask implements Work {
      private final Callable task;
      private final AtomicBoolean firstCall = new AtomicBoolean(false);

      public ParallelTask(Callable task) {
         if (task == null) {
            throw new IllegalArgumentException();
         } else {
            this.task = task;
         }
      }

      private void onFinish() {
         if (ParallelTaskManager.this.isEnabled && !this.firstCall.getAndSet(true)) {
            ParallelTaskManager.this.barrier.arrive();
         }

      }

      public final void run() {
         try {
            this.task.call();
         } catch (Throwable var5) {
            ParallelTaskManager.this.addThrowable(var5);
         } finally {
            this.onFinish();
         }

      }

      public Runnable overloadAction(String reason) {
         return new Runnable() {
            public void run() {
               ParallelTask.this.onFinish();
            }
         };
      }

      public Runnable cancel(String reason) {
         return new Runnable() {
            public void run() {
               ParallelTask.this.onFinish();
            }
         };
      }
   }
}
