package weblogic.work;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class SelfTuningWorkManagerImpl extends WorkManagerImpl implements ComponentInvocationContextHolder {
   private static final DebugCategory DEBUG = Debug.getCategory("weblogic.workmanager");
   private static Logger debugLogger = new Logger() {
      public boolean debugEnabled() {
         return SelfTuningWorkManagerImpl.DEBUG.isEnabled();
      }

      public void log(String msg) {
         System.err.println("DEBUG: " + msg);
      }
   };
   private WorkManagerLifecycleImpl workManagerLifecycle;
   static OverloadManager SHARED_OVERLOAD_MANAGER;
   private final ServiceClassSupport requestClass;
   protected final MaxThreadsConstraint max;
   protected final MinThreadsConstraint min;
   private OverloadManager overload;
   private OverloadManager partitionOverload;
   private final StuckThreadManager stuckThreadManager;
   private AtomicLong acceptedCount;
   private AtomicLong completedCount;
   private final ComponentInvocationContext componentInvocationContext;
   private final String partitionName;
   private final boolean isGlobalDomain;

   static void initialize(int capacity) {
      SHARED_OVERLOAD_MANAGER = new OverloadManager("global overload manager");
      SHARED_OVERLOAD_MANAGER.setCapacity(capacity);
   }

   SelfTuningWorkManagerImpl(String name, String appName, String moduleName, RequestClass p, MaxThreadsConstraint max, MinThreadsConstraint min, OverloadManager overload, StuckThreadManager stm) {
      this(name, appName, moduleName, p, max, min, overload, (OverloadManager)null, (PartitionFairShare)null, stm, (ComponentInvocationContext)null);
   }

   SelfTuningWorkManagerImpl(String name, String appName, String moduleName, RequestClass p, MaxThreadsConstraint max, MinThreadsConstraint min, OverloadManager overload, OverloadManager partitionOverload, PartitionFairShare partitionFairShare, StuckThreadManager stm, ComponentInvocationContext componentInvocationContext) {
      this.acceptedCount = new AtomicLong();
      this.completedCount = new AtomicLong();
      this.wmName = name != null ? name.intern() : null;
      this.applicationName = appName;
      this.moduleName = moduleName;
      if (this.wmName != "weblogic.kernel.Default" && this.applicationName == null) {
         this.setInternal();
      }

      if (p == null) {
         this.requestClass = new FairShareRequestClass(name, appName, moduleName, partitionFairShare);
      } else {
         this.requestClass = (ServiceClassSupport)p;
      }

      if (this.isInternal()) {
         this.requestClass.setInternal(true);
      }

      this.max = max;
      this.min = min;
      if (min != null) {
         RequestManager.getInstance().register(min);
      }

      this.overload = overload;
      this.partitionOverload = partitionOverload;
      this.stuckThreadManager = stm;
      this.componentInvocationContext = componentInvocationContext;
      this.partitionName = componentInvocationContext == null ? null : componentInvocationContext.getPartitionName();
      this.isGlobalDomain = componentInvocationContext == null ? true : componentInvocationContext.isGlobalRuntime();
   }

   public int getType() {
      return 1;
   }

   public int getConfiguredThreadCount() {
      return -1;
   }

   public void schedule(Runnable runnable) {
      this.scheduleInternal(runnable, false);
   }

   private boolean scheduleInternal(Runnable runnable, boolean fromScheduleIfBusy) {
      try {
         if ("direct" == this.wmName) {
            runnable.run();
            return true;
         } else if (this.accept(runnable)) {
            WorkAdapter adapter = this.getWorkAdapter(runnable);
            if (fromScheduleIfBusy) {
               RequestManager.getInstance().executeItWithRethrow(adapter);
            } else {
               RequestManager.getInstance().executeIt(adapter);
            }

            return true;
         } else {
            if (this.workManagerLifecycle != null) {
               this.workManagerLifecycle.workCompleted();
            }

            return false;
         }
      } catch (ConstraintFullQueueException var4) {
         return false;
      } catch (RuntimeException var5) {
         WorkManagerLogger.logScheduleFailed(this.wmName, var5);
         throw var5;
      } catch (OutOfMemoryError var6) {
         WorkManagerLogger.logScheduleFailed(this.wmName, var6);
         this.notifyOOME(var6);
         throw var6;
      } catch (Error var7) {
         WorkManagerLogger.logScheduleFailed(this.wmName, var7);
         throw var7;
      }
   }

   public boolean executeIfIdle(Runnable runnable) {
      if ("direct" == this.wmName) {
         runnable.run();
         return true;
      } else {
         return this.accept(runnable) ? RequestManager.getInstance().executeIfIdle(this.getWorkAdapter(runnable)) : false;
      }
   }

   public boolean scheduleIfBusy(Runnable runnable) {
      ExecuteThread et = ThreadUtility.getCurrentThreadAsExecuteThread();
      if (et != null && this.min == null) {
         et.setLongRunningTask();
      }

      if (RequestManager.getInstance().isBusyForScheduleIfBusy() && this.scheduleInternal(runnable, true)) {
         return true;
      } else {
         if (et != null) {
            et.setHog(false);
            RequestManager.updateRequestClass((ServiceClassStatsSupport)et.getWorkManager().getRequestClass(), et);
         }

         return false;
      }
   }

   private WorkAdapter getWorkAdapter(Runnable work) {
      Object workAdapter;
      if (work instanceof WorkAdapter) {
         workAdapter = (WorkAdapter)work;
         if (!((WorkAdapter)workAdapter).setScheduled()) {
            workAdapter = new WorkAdapterImpl((Runnable)workAdapter);
         }
      } else {
         workAdapter = new WorkAdapterImpl(work);
      }

      ((WorkAdapter)workAdapter).setWorkManager(this);
      return (WorkAdapter)workAdapter;
   }

   public int getQueueDepth() {
      return this.getPendingRequests();
   }

   public boolean isThreadOwner(Thread th) {
      if (!(th instanceof ExecuteThread)) {
         return false;
      } else {
         ExecuteThread et = (ExecuteThread)th;
         return this == et.getWorkManager();
      }
   }

   protected void notifyOOME(OutOfMemoryError oome) {
      try {
         Class cls = Class.forName("weblogic.health.HealthMonitorService");
         Object hms = cls.newInstance();
         Class[] partypes = new Class[]{Class.forName("java.lang.Throwable")};
         Method meth = cls.getMethod("panic", partypes);
         Object[] args = new Object[]{oome};
         meth.invoke(hms, args);
      } catch (ClassNotFoundException var7) {
      } catch (Exception var8) {
         throw new RuntimeException(var8);
      }

      throw oome;
   }

   protected boolean accept(Runnable runnable) {
      if (this.isInternal()) {
         return true;
      } else if (!(runnable instanceof Work)) {
         return true;
      } else {
         OverloadManager rejectingOverloadManager = this.getRejectingOverloadManager();
         if (rejectingOverloadManager != null) {
            String rejectionMessage = getOverloadMessage(rejectingOverloadManager);
            if (rejectionMessage != null) {
               return this.overload(rejectionMessage, runnable);
            }
         }

         return true;
      }
   }

   protected boolean overload(String rejectionMessage, Runnable runnable) {
      Runnable overloadAction = ((Work)runnable).overloadAction(rejectionMessage);
      if (overloadAction == null) {
         return true;
      } else {
         WorkManagerFactory.getInstance().getRejector().schedule(overloadAction);
         return false;
      }
   }

   protected OverloadManager getRejectingOverloadManager() {
      if (this.overload != null && !this.overload.canAcceptMore()) {
         return this.overload;
      } else if (this.partitionOverload == null || this.partitionOverload.canAcceptMore() || this.requestClass.getPendingRequestsCount() < this.partitionOverload.getCapacity() && this.partitionOverload.acceptRequestClass(this.requestClass)) {
         if (SHARED_OVERLOAD_MANAGER.canAcceptMore()) {
            return null;
         } else if (this.min != null && !this.min.isConstraintSatisfied()) {
            return null;
         } else if (this.requestClass.getPendingRequestsCount() < SHARED_OVERLOAD_MANAGER.getCapacity() && SHARED_OVERLOAD_MANAGER.acceptRequestClass(this.requestClass)) {
            return null;
         } else {
            SHARED_OVERLOAD_MANAGER.incrementRejectedRequestsCounter();
            return SHARED_OVERLOAD_MANAGER;
         }
      } else {
         this.partitionOverload.incrementRejectedRequestsCounter();
         return this.partitionOverload;
      }
   }

   static String getOverloadMessage(OverloadManager om) {
      Loggable loggable = WorkManagerLogger.logOverloadActionLoggable(om.getName(), om.getInProgress(), om.getCapacity());
      loggable.log();
      return loggable.getMessage();
   }

   String getCancelMessage() {
      Loggable loggable = WorkManagerLogger.logCancelAfterEnqueueLoggable(this.wmName, this.applicationName);
      loggable.log();
      return loggable.getMessage();
   }

   boolean isShutdown() {
      return this.workManagerLifecycle != null ? this.workManagerLifecycle.isShutdown() : false;
   }

   void accepted() {
      this.acceptedCount.getAndIncrement();
      if (!this.isInternal()) {
         SHARED_OVERLOAD_MANAGER.acceptWork();
         SHARED_OVERLOAD_MANAGER.incrementQueueDepth();
      }

      if (this.overload != null) {
         this.overload.acceptWork();
         this.overload.incrementQueueDepth();
      }

      if (this.partitionOverload != null) {
         this.partitionOverload.acceptWork();
         this.partitionOverload.incrementQueueDepth();
      }

      this.requestClass.incrementPendingRequestCount();
      if (this.workManagerLifecycle != null) {
         this.workManagerLifecycle.workAccepted();
      }

   }

   void started() {
      if (!this.isInternal()) {
         SHARED_OVERLOAD_MANAGER.decrementQueueDepth();
      }

      if (this.overload != null) {
         this.overload.decrementQueueDepth();
      }

      if (this.partitionOverload != null) {
         this.partitionOverload.decrementQueueDepth();
      }

      if (this.workManagerLifecycle != null) {
         this.workManagerLifecycle.workStarted();
      }

   }

   void increaseMaxThreadConstraintInProgress() {
      if (this.max != null) {
         this.max.acquire();
      }

   }

   void increaseMinThreadConstraintInProgress(boolean isStandbyThread) {
      if (this.min != null) {
         this.min.acquire(isStandbyThread);
      }

   }

   void stuck() {
      if (this.workManagerLifecycle != null) {
         this.workManagerLifecycle.workStuck();
      }

   }

   void unstuck() {
      if (this.workManagerLifecycle != null) {
         this.workManagerLifecycle.workUnstuck();
      }

   }

   void completed() {
      this.completedCount.getAndIncrement();
      if (!this.isInternal()) {
         SHARED_OVERLOAD_MANAGER.finishWork();
      }

      if (this.overload != null) {
         this.overload.finishWork();
      }

      if (this.partitionOverload != null) {
         this.partitionOverload.finishWork();
      }

      this.requestClass.decrementPendingRequestCount();
      if (this.min != null) {
         this.min.completed();
      }

      if (this.workManagerLifecycle != null) {
         this.workManagerLifecycle.workCompleted();
      }

   }

   void acquireMinMaxConstraint(boolean isStandbyThread) {
      this.increaseMinThreadConstraintInProgress(isStandbyThread);
      this.increaseMaxThreadConstraintInProgress();
   }

   void releaseMinMaxConstraint(boolean standbyThread) {
      if (this.min != null) {
         this.min.release(standbyThread);
      }

      if (this.max != null) {
         this.max.release();
      }

   }

   protected void setWorkManagerService(WorkManagerLifecycleImpl lifecycle) {
      this.workManagerLifecycle = lifecycle;
   }

   public WorkManagerLifecycleImpl getWorkManagerService() {
      return this.workManagerLifecycle;
   }

   final long getAcceptedCount() {
      return this.acceptedCount.get();
   }

   final long getCompletedCount() {
      return this.completedCount.get();
   }

   final OverloadManager getOverloadManager() {
      return this.overload;
   }

   public final void setCapacity(int capacity) {
      if (this.overload == null) {
         this.overload = new OverloadManager(this.wmName, capacity);
      } else {
         this.overload.setCapacity(capacity);
      }

   }

   public final RequestClass getRequestClass() {
      return this.requestClass;
   }

   public final MaxThreadsConstraint getMaxThreadsConstraint() {
      return this.max;
   }

   public final MinThreadsConstraint getMinThreadsConstraint() {
      return this.min;
   }

   protected final StuckThreadManager getStuckThreadManager() {
      return this.stuckThreadManager;
   }

   public int getPendingRequests() {
      int diff = (int)(this.getAcceptedCount() - this.getCompletedCount());
      return diff > 0 ? diff : 0;
   }

   public boolean isGlobalRuntime() {
      return this.componentInvocationContext == null ? true : this.componentInvocationContext.isGlobalRuntime();
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.componentInvocationContext;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   void runWorkUnderContext(WorkAdapter workAdapter) throws Throwable {
      this.runWorkUnderContext((ExecuteThread)null, workAdapter);
   }

   void runWorkUnderContext(ExecuteThread invokingThread, WorkAdapter workAdapter) throws Throwable {
      ComponentInvocationContext context = this.componentInvocationContext;
      if (workAdapter instanceof ComponentRequest) {
         ComponentInvocationContext workAdapterContext = ((ComponentRequest)workAdapter).getComponentInvocationContext();
         if (workAdapterContext != null) {
            context = workAdapterContext;
         }
      }

      if (invokingThread != null) {
         invokingThread.setPreviousCIC(context);
      }

      try {
         PartitionUtility.runWorkUnderContext(workAdapter.getWork(), context);
      } catch (ExecutionException var5) {
         throw var5.getCause();
      }
   }

   public void cleanup() {
      if (!this.requestClass.isShared()) {
         this.requestClass.cleanup();
      }

      if (this.min != null && !this.min.isShared()) {
         this.min.cleanup();
      }

      if (this.max != null && !this.max.isShared()) {
         this.max.cleanup();
      }

   }

   static void setDebugLogger(Logger logger) {
      debugLogger = logger;
   }

   public static boolean debugEnabled() {
      return debugLogger.debugEnabled();
   }

   public static void debug(String msg) {
      debugLogger.log(msg);
   }

   public void dumpInformation(PrintWriter img) {
      if (img != null) {
         img.println("--- WorkManager " + this.wmName + " for app " + this.applicationName + ", module " + this.moduleName + " ---");
         img.println("Requests accepted       : " + this.acceptedCount.get());
         img.println("Requests Completed      : " + this.completedCount.get());
      }
   }

   public String toString() {
      return this.isGlobalDomain ? this.applicationName + "@" + this.moduleName + "@" + this.wmName : this.partitionName + "@" + this.applicationName + "@" + this.moduleName + "@" + this.wmName;
   }

   public interface Logger {
      boolean debugEnabled();

      void log(String var1);
   }

   private static final class WorkAdapterImpl extends ComponentWorkAdapter {
      private final Runnable runnable;

      private WorkAdapterImpl(Runnable runnable) {
         this.runnable = runnable;
         if (runnable instanceof ComponentRequest) {
            this.componentInvocationContext = ((ComponentRequest)runnable).getComponentInvocationContext();
         }

      }

      private WorkAdapterImpl(Runnable runnable, ComponentInvocationContext cic) {
         this.runnable = runnable;
         this.componentInvocationContext = cic;
      }

      public void run() {
         this.runnable.run();
      }

      public Runnable overloadAction(String reason) {
         if (this.runnable instanceof Work) {
            Runnable overloadAction = ((Work)this.runnable).overloadAction(reason);
            if (overloadAction != null && this.componentInvocationContext != null) {
               overloadAction = new WorkAdapterImpl((Runnable)overloadAction, this.componentInvocationContext);
            }

            return (Runnable)overloadAction;
         } else {
            return null;
         }
      }

      public Runnable cancel(String reason) {
         if (this.runnable instanceof Work) {
            Runnable cancelAction = ((Work)this.runnable).cancel(reason);
            if (cancelAction != null && this.componentInvocationContext != null) {
               cancelAction = new WorkAdapterImpl((Runnable)cancelAction, this.componentInvocationContext);
            }

            return (Runnable)cancelAction;
         } else {
            return null;
         }
      }

      public void release() {
         if (this.runnable instanceof commonj.work.Work) {
            ((commonj.work.Work)this.runnable).release();
         } else {
            Runnable cancelTask = this.cancel("Work manager is being shut down");
            if (cancelTask != null) {
               cancelTask.run();
            }
         }

      }

      public String getDescription() {
         return this.runnable instanceof Describable ? ((Describable)this.runnable).getDescription() : null;
      }

      // $FF: synthetic method
      WorkAdapterImpl(Runnable x0, Object x1) {
         this(x0);
      }
   }
}
