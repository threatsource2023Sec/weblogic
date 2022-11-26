package weblogic.work;

import java.security.AccessController;
import weblogic.logging.Loggable;
import weblogic.management.configuration.WorkManagerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.internal.ServerTransactionManagerImpl;
import weblogic.transaction.internal.TransactionManagerImpl;
import weblogic.utils.StackTraceUtils;

public final class WorkManagerServiceImpl extends WorkManagerLifecycleImpl implements WorkManagerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int PENDING_TX_TIMER_INTERVAL = 2000;
   private WorkManagerService.WorkListener rmiManager;
   private boolean allowRMIWork = false;

   public static WorkManagerService createService(String name, String appName, String moduleName) {
      return createService(name, appName, moduleName, (StuckThreadManager)null);
   }

   static WorkManagerService createService(ServerWorkManagerImpl swm) {
      WorkManagerServiceImpl service = new WorkManagerServiceImpl(swm);
      swm.setWorkManagerService(service);
      return service;
   }

   public static WorkManagerService createService(String name, String appName, String moduleName, StuckThreadManager stm) {
      if (ManagementService.getRuntimeAccess(kernelId).getServer().getUse81StyleExecuteQueues()) {
         WorkManagerFactory factory = WorkManagerFactory.getInstance();
         WorkManager wm = factory.findOrCreate(name, -1, -1);
         return new WorkManagerServiceImpl(wm);
      } else {
         ServerWorkManagerImpl swm = ServerWorkManagerFactory.create(name, appName, moduleName, stm);
         WorkManagerServiceImpl service = new WorkManagerServiceImpl(swm);
         swm.setWorkManagerService(service);
         return service;
      }
   }

   public static WorkManagerService createService(String partitionName, String appName, String moduleName, WorkManagerMBean mbean, StuckThreadManager stm) {
      WorkManagerFactory factory = WorkManagerFactory.getInstance();
      if (ManagementService.getRuntimeAccess(kernelId).getServer().getUse81StyleExecuteQueues()) {
         int maxCount = mbean.getMaxThreadsConstraint() != null ? mbean.getMaxThreadsConstraint().getCount() : -1;
         int minCount = mbean.getMinThreadsConstraint() != null ? mbean.getMinThreadsConstraint().getCount() : -1;
         WorkManager wm = factory.findOrCreate(mbean.getName(), maxCount, minCount);
         return new WorkManagerServiceImpl(wm);
      } else {
         ServerWorkManagerImpl swm = ServerWorkManagerFactory.create(partitionName, appName, moduleName, mbean, stm);
         WorkManagerServiceImpl service = new WorkManagerServiceImpl(swm);
         swm.setWorkManagerService(service);
         return service;
      }
   }

   public static WorkManagerService createService(String name, String appName, String moduleName, RequestClass requestClass, MaxThreadsConstraint max, MinThreadsConstraint min, OverloadManager overload, StuckThreadManager stm) {
      if (ManagementService.getRuntimeAccess(kernelId).getServer().getUse81StyleExecuteQueues()) {
         WorkManagerFactory factory = WorkManagerFactory.getInstance();
         WorkManager wm = factory.findOrCreate(name, max != null ? max.getCount() : -1, min != null ? min.getCount() : -1);
         return new WorkManagerServiceImpl(wm);
      } else {
         ServerWorkManagerImpl swm = ServerWorkManagerFactory.create(name, appName, moduleName, requestClass, max, min, overload, stm);
         WorkManagerServiceImpl service = new WorkManagerServiceImpl(swm);
         swm.setWorkManagerService(service);
         return service;
      }
   }

   private WorkManagerServiceImpl(WorkManager workManager) {
      super(workManager);
      if (debugEnabled()) {
         debug("-- wmservice created - " + this);
      }

   }

   public synchronized void shutdown(ShutdownCallback callback) {
      if (!this.internal && this.state != 3 && this.state != 2) {
         if (debugEnabled()) {
            debug("-- wmservice - " + this + " shutdown with callback " + callback + "\nstack trace:\n" + StackTraceUtils.throwable2StackTrace((Throwable)null));
         }

         this.callback = callback;
         if (this.waitForPendingTransactions()) {
            if (debugEnabled()) {
               debug("-- wmservice - " + this + " is waiting for pending txn to complete before shutdown");
            }

            this.state = 2;
         } else if (!this.allowRMIWork) {
            if (debugEnabled()) {
               debug("-- wmservice - " + this + " has no pending txn. commencing shutdown ...");
            }

            this.state = 3;
            this.releaseExecutingRequests();
            if (!this.workPending()) {
               if (debugEnabled()) {
                  debug("-- wmservice - " + this + " has no pending work and no pending txn. Invoking callback");
               }

               if (callback != null) {
                  callback.completed();
               }

               this.callback = null;
            }
         }
      } else {
         if (callback != null) {
            callback.completed();
         }

      }
   }

   ServerTransactionManagerImpl getServerTransactionManager() {
      return (ServerTransactionManagerImpl)TransactionManagerImpl.getTransactionManager();
   }

   boolean isTxMapEmpty() {
      return this.getServerTransactionManager().getNumTransactions() == 0;
   }

   int getTxTimeoutMillis() {
      return this.getServerTransactionManager().getTransactionTimeout() * 1000;
   }

   private boolean waitForPendingTransactions() {
      int state = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getStateVal();
      if (state == 2 && !this.isTxMapEmpty()) {
         if (!this.workPending()) {
            return false;
         } else {
            if (debugEnabled()) {
               debug("-- wmservice - " + this + " has pending txn and will timeout in " + this.getTxTimeoutMillis() + "ms");
            }

            TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new TxEmptyChecker(this.getTxTimeoutMillis(), 2000), 0L, 2000L);
            return true;
         }
      } else {
         return false;
      }
   }

   public WorkManager getDelegate() {
      return this.delegate;
   }

   public void cleanup() {
      if (this.delegate instanceof ServerWorkManagerImpl) {
         ((ServerWorkManagerImpl)this.delegate).cleanup();
      }

   }

   String getCancelMessage() {
      Loggable loggable = WorkManagerLogger.logCancelBeforeEnqueueLoggable(this.getName(), this.getApplicationName());
      loggable.log();
      return loggable.getMessage();
   }

   public int getQueueDepth() {
      return this.workCount;
   }

   protected boolean permitSchedule(Runnable runnable) {
      if (this.internal) {
         return true;
      } else {
         boolean allowWork = false;
         synchronized(this) {
            if (this.state == 1 || !(runnable instanceof Work) || this.allowTransactionalWork(runnable)) {
               ++this.workCount;
               allowWork = true;
            }
         }

         if (this.allowRMIWork) {
            this.rmiManager.preScheduleWork();
            return true;
         } else if (allowWork) {
            return true;
         } else {
            if (debugEnabled()) {
               debug("-- wmservice - " + this + " is shutdown");
            }

            if (!this.isAdminUser(runnable) && !this.isAdminChannelRequest(runnable)) {
               return false;
            } else {
               synchronized(this) {
                  ++this.workCount;
                  return true;
               }
            }
         }
      }
   }

   private boolean allowTransactionalWork(Runnable runnable) {
      if (this.state == 2 && runnable instanceof WorkAdapter) {
         boolean txn = ((WorkAdapter)runnable).isTransactional();
         if (debugEnabled()) {
            debug("-- wmservice - " + this + " is waiting for pending txn and current work has txn:" + txn);
         }

         return txn;
      } else {
         return false;
      }
   }

   private boolean isAdminChannelRequest(Runnable runnable) {
      if (!(runnable instanceof WorkAdapter)) {
         return false;
      } else if (((WorkAdapter)runnable).isAdminChannelRequest()) {
         if (debugEnabled()) {
            debug("-- wmservice - " + this + " is shutdown but accepted work from admin channel");
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isAdminUser(Runnable runnable) {
      if (!(runnable instanceof ServerWorkAdapter)) {
         return false;
      } else {
         AuthenticatedSubject subject = ((ServerWorkAdapter)runnable).getAuthenticatedSubject();
         if (subject != null && SubjectUtils.doesUserHaveAnyAdminRoles(subject)) {
            if (debugEnabled()) {
               debug("-- wmservice - " + this + " is shutdown but accepted work from " + subject);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public void startRMIGracePeriod(WorkManagerService.WorkListener rmiManager) {
      this.rmiManager = rmiManager;
      if (rmiManager != null) {
         this.allowRMIWork = true;
      }

   }

   public void endRMIGracePeriod() {
      boolean workPending = false;
      synchronized(this) {
         this.allowRMIWork = false;
         if (this.state != 2) {
            this.state = 3;
         }

         workPending = this.workPending();
      }

      if (!workPending) {
         this.invokeCallback();
      }

      this.rmiManager = null;
   }

   private void notifyTransactionCompletion() {
      if (this.callback != null) {
         boolean workPending = false;
         synchronized(this) {
            if (!this.allowRMIWork) {
               this.state = 3;
            }

            workPending = this.workPending();
         }

         if (!workPending) {
            this.invokeCallback();
         }

      }
   }

   private final class TxEmptyChecker implements TimerListener {
      private final int totalExecutionCount;
      private int executionCount;

      TxEmptyChecker(int txTimeout, int timerInterval) {
         this.totalExecutionCount = txTimeout / timerInterval;
      }

      public void timerExpired(Timer timer) {
         if (!WorkManagerServiceImpl.this.isTxMapEmpty() && WorkManagerServiceImpl.this.workPending() && this.executionCount < this.totalExecutionCount) {
            if (WorkManagerLifecycleImpl.debugEnabled()) {
               WorkManagerLifecycleImpl.debug("-- wmservice - " + this + " timer expired: will wait for pending transactions");
            }

            ++this.executionCount;
         } else {
            if (WorkManagerLifecycleImpl.debugEnabled()) {
               WorkManagerLifecycleImpl.debug("-- wmservice - " + this + " timer expired: will no longer wait for pending transactions");
            }

            WorkManagerServiceImpl.this.notifyTransactionCompletion();
            timer.cancel();
         }

      }
   }
}
