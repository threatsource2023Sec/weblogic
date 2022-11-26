package weblogic.work;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.server.GlobalServiceLocator;

public final class ServerWorkManagerImpl extends SelfTuningWorkManagerImpl implements DaemonTaskStatisticsCollector {
   static LowMemoryListener LOW_MEMORY_LISTENER;
   private static volatile RuntimeAccess runtimeAccess;
   private AtomicLong daemonTasksStartedCount = new AtomicLong();
   private AtomicLong daemonTasksCompletedCount = new AtomicLong();

   private static RuntimeAccess getRuntimeAccess() {
      if (runtimeAccess != null) {
         return runtimeAccess;
      } else {
         Class var0 = ServerWorkManagerImpl.class;
         synchronized(ServerWorkManagerImpl.class) {
            if (runtimeAccess != null) {
               return runtimeAccess;
            } else {
               runtimeAccess = (RuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
                  public RuntimeAccess run() {
                     return (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
                  }
               });
               return runtimeAccess;
            }
         }
      }
   }

   static void initialize() {
      SHARED_OVERLOAD_MANAGER = new OverloadManager("global overload manager");
      SHARED_OVERLOAD_MANAGER.setCapacity(getRuntimeAccess().getServer().getOverloadProtection().getSharedCapacityForWorkManagers());
      SHARED_OVERLOAD_MANAGER.enableRejectedRequestsCounter();
      LOW_MEMORY_LISTENER = new LowMemoryListener();
      setDebugLogger(new SelfTuningWorkManagerImpl.Logger() {
         public boolean debugEnabled() {
            return SelfTuningDebugLogger.isDebugEnabled();
         }

         public void log(String str) {
            SelfTuningDebugLogger.debug("<ThreadPriorityManager>" + str);
         }
      });
   }

   ServerWorkManagerImpl(String name, String appName, String moduleName, RequestClass p, MaxThreadsConstraint max, MinThreadsConstraint min, OverloadManager overload, OverloadManager partitionOverload, PartitionFairShare partitionFairShare, StuckThreadManager stm, ComponentInvocationContext componentInvocationContext) {
      super(name, appName, moduleName, p, max, min, overload, partitionOverload, partitionFairShare, stm, componentInvocationContext);
      WorkManagerImageSource.getInstance().register(this);
   }

   protected void notifyOOME(OutOfMemoryError oome) {
      HealthMonitorService.panic(oome);
   }

   protected boolean accept(Runnable runnable) {
      if (this.isInternal()) {
         return true;
      } else if (!(runnable instanceof Work)) {
         return true;
      } else {
         String rejectionMessage = null;
         if (LOW_MEMORY_LISTENER.lowMemory()) {
            rejectionMessage = this.getLowMemoryMessage();
         } else {
            OverloadManager rejectingOverloadManager = this.getRejectingOverloadManager();
            if (rejectingOverloadManager != null) {
               rejectionMessage = getOverloadMessage(rejectingOverloadManager);
            }
         }

         if (rejectionMessage == null) {
            return true;
         } else {
            Runnable overloadAction = ((Work)runnable).overloadAction(rejectionMessage);
            if (overloadAction != null && !isAdminWork(runnable)) {
               WorkManagerFactory.getInstance().getRejector().schedule(overloadAction);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   private static final boolean isAdminWork(Runnable runnable) {
      if (!(runnable instanceof ServerWorkAdapter)) {
         return false;
      } else {
         AuthenticatedSubject as = ((ServerWorkAdapter)runnable).getAuthenticatedSubject();
         return as == null ? false : SubjectUtils.doesUserHaveAnyAdminRoles(as);
      }
   }

   final String getLowMemoryMessage() {
      return WorkManagerLogger.logLowMemoryLoggable(this.wmName).getMessage();
   }

   public void daemonTaskStarted() {
      this.daemonTasksStartedCount.getAndIncrement();
   }

   public void daemonTaskCompleted() {
      this.daemonTasksCompletedCount.getAndIncrement();
   }

   public long getDaemonTasksStartedCount() {
      return this.daemonTasksStartedCount.get();
   }

   public long getDaemonTasksCompletedCount() {
      return this.daemonTasksCompletedCount.get();
   }

   public void cleanup() {
      super.cleanup();
      WorkManagerImageSource.getInstance().deregister(this);
   }

   static final class LowMemoryListener implements PropertyChangeListener {
      private static boolean lowMemory;

      private LowMemoryListener() {
         ServerRuntimeMBean bean = ServerWorkManagerImpl.getRuntimeAccess().getServerRuntime();
         bean.addPropertyChangeListener(this);
      }

      boolean lowMemory() {
         return lowMemory;
      }

      public void propertyChange(PropertyChangeEvent event) {
         if (event != null) {
            if ("HealthState".equals(event.getPropertyName())) {
               int state = ((HealthState)event.getNewValue()).getState();
               if (state == 4) {
                  lowMemory = true;
               } else {
                  if (state == 0) {
                     lowMemory = false;
                  }

               }
            }
         }
      }

      // $FF: synthetic method
      LowMemoryListener(Object x0) {
         this();
      }
   }
}
