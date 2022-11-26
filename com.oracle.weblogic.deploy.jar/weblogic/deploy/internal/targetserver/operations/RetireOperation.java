package weblogic.deploy.internal.targetserver.operations;

import java.security.AccessController;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.logging.NonCatalogLogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DeploymentConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeployerRuntimeTextTextFormatter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

public final class RetireOperation extends AbstractOperation {
   private static final NonCatalogLogger longOperationThreadDumpLogger = new NonCatalogLogger("RetireLongOperationThreadDumpLogger");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final ServerRuntimeMBean serverRuntime;
   private final DomainMBean domain;
   private Timer longOperationMonitorTimer;
   private long longOperationThreadDumpStartTime;
   private long longOperationThreadDumpInterval;
   private int longOperationThreadDumpCount;
   private int threadDumpCounter;
   private long longOperationMonitorTimerStartTime;

   public RetireOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean requiresRestart) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, requiresRestart);
      this.serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      this.domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      this.longOperationMonitorTimer = null;
      this.longOperationThreadDumpStartTime = -1L;
      this.longOperationThreadDumpInterval = 0L;
      this.longOperationThreadDumpCount = 0;
      this.threadDumpCounter = 0;
      this.operation = 13;
      this.controlOperation = true;
   }

   protected void doCommit() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("Retiring application " + this.getApplication().getName());
      }

      this.appcontainer = this.getApplication().findDeployment();
      if (this.appcontainer != null && this.getApplication().needRetirement()) {
         try {
            this.startLongOperationMonitor();
            if (this.getState(this.appcontainer) == 3) {
               this.forceProductionToAdmin(this.appcontainer);
            }

            if (this.getState(this.appcontainer) == 2) {
               this.appcontainer.deactivate(this.deploymentContext);
            }

            if (this.getState(this.appcontainer) == 1) {
               this.silentUnprepare(this.appcontainer);
            }
         } finally {
            if (this.getState() != null) {
               this.getState().setCurrentState("STATE_RETIRED", true);
               this.silentRemove(this.appcontainer);
            }

            this.cancelLongOperationMonitor();
         }
      } else {
         SlaveDeployerLogger.logNoDeployment(DeployerRuntimeTextTextFormatter.getInstance().messageRetire(), this.mbean.getName());
      }

      this.complete(3, (Exception)null);
   }

   private void startLongOperationMonitor() {
      if (this.domain != null) {
         DeploymentConfigurationMBean conf = this.domain.getDeploymentConfiguration();
         if (conf != null) {
            this.longOperationThreadDumpStartTime = conf.getLongRunningRetireThreadDumpStartTime();
            this.longOperationThreadDumpInterval = conf.getLongRunningRetireThreadDumpInterval();
            this.longOperationThreadDumpCount = conf.getLongRunningRetireThreadDumpCount();
         }
      }

      if (this.longOperationThreadDumpCount > 0) {
         this.longOperationMonitorTimerStartTime = System.currentTimeMillis();
         this.longOperationMonitorTimer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.deploy.RetireLongOperationMonitor", WorkManagerFactory.getInstance().getSystem()).schedule(new LongOperationMonitor(), this.longOperationThreadDumpStartTime, this.longOperationThreadDumpInterval);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Retire long operation monitor for " + this.getApplication().getName() + " will start thread dump in " + this.longOperationThreadDumpStartTime / 1000L + " seconds with an interval of " + this.longOperationThreadDumpInterval / 1000L + " seconds and " + this.longOperationThreadDumpCount + " tries.");
         }
      }

   }

   private final void cancelLongOperationMonitor() {
      try {
         if (this.longOperationMonitorTimer != null) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Cancelling Retire long operation monitor for " + this.getApplication().getName());
            }

            this.longOperationMonitorTimer.cancel();
            this.longOperationMonitorTimer = null;
         }
      } catch (Throwable var2) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.serviceDebug("Cancelling Retire long operation monitor for " + this.getApplication().getName() + "failed due to " + var2);
         }
      }

   }

   private final class LongOperationMonitor implements TimerListener {
      private LongOperationMonitor() {
      }

      public final void timerExpired(Timer timer) {
         if (RetireOperation.this.threadDumpCounter >= RetireOperation.this.longOperationThreadDumpCount) {
            RetireOperation.this.cancelLongOperationMonitor();
         } else {
            RetireOperation.this.threadDumpCounter++;
            StringBuffer sBuffer = new StringBuffer();

            try {
               String dump = RetireOperation.this.serverRuntime.getJVMRuntime().getThreadStackDump();
               sBuffer.append("Retire commit for " + RetireOperation.this.getApplication().getName() + " is unexpectedly long as " + (System.currentTimeMillis() - RetireOperation.this.longOperationMonitorTimerStartTime) / 1000L + " seconds has elapsed without completing. Generating thread dump (" + RetireOperation.this.threadDumpCounter + " of " + RetireOperation.this.longOperationThreadDumpCount + "):").append("\n");
               sBuffer.append(dump);
               sBuffer.append("\n\n");
            } catch (Throwable var4) {
               sBuffer.append(" Exception while getting thread dump for retire commit of " + RetireOperation.this.getApplication().getName() + ": ");
               sBuffer.append(StackTraceUtils.throwable2StackTrace(var4));
               sBuffer.append("\n\n");
            }

            RetireOperation.longOperationThreadDumpLogger.warning(sBuffer.toString());
         }
      }

      // $FF: synthetic method
      LongOperationMonitor(Object x1) {
         this();
      }
   }
}
