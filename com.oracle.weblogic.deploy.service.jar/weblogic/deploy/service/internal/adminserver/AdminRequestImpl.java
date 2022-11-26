package weblogic.deploy.service.internal.adminserver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentServiceCallbackHandler;
import weblogic.deploy.service.Deployment.DeploymentType;
import weblogic.deploy.service.internal.DeploymentRequestTaskRuntimeMBeanImpl;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.RequestImpl;
import weblogic.deploy.service.internal.statemachines.adminserver.AdminServerState;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.management.utils.TargetingUtils;

public final class AdminRequestImpl extends RequestImpl {
   private static final AdminRequestManager requestManager = AdminRequestManager.getInstance();
   private static final AdminDeploymentsManager deploymentsManager = AdminDeploymentsManager.getInstance();
   private final Set targets = new HashSet();
   private DomainVersion proposedDomainVersion;
   private transient AdminRequestStatus status;
   private transient DeploymentRequestTaskRuntimeMBeanImpl taskRuntime;
   private final Object requestLock = new Object();
   private Runnable sendCancelAction;

   AdminRequestImpl() {
   }

   public void setTaskRuntime(DeploymentRequestTaskRuntimeMBean task) {
      synchronized(this.requestLock) {
         if (this.taskRuntime == null) {
            this.taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)task;
         } else if (isDebugEnabled()) {
            debug("attempting to set a duplicate task runtime for '" + this.identifier + "' - ignoring this");
         }

      }
   }

   public DeploymentRequestTaskRuntimeMBean getTaskRuntime() {
      synchronized(this.requestLock) {
         return this.taskRuntime;
      }
   }

   public final AdminRequestStatus getStatus() {
      synchronized(this.requestLock) {
         return this.status;
      }
   }

   public final DomainVersion getProposedDomainVersion() {
      synchronized(this.requestLock) {
         return this.proposedDomainVersion;
      }
   }

   public final synchronized boolean toBeCancelled() {
      synchronized(this.requestLock) {
         if (this.status == null) {
            return false;
         } else {
            return this.status.isCancelledByUser() || this.status.isCancelledByClusterConstraints() || this.status.timedOut() || this.status.failed();
         }
      }
   }

   public final void run() {
      if (isDebugEnabled()) {
         debug("DeploymentService call: Starting deploy for " + this.toString());
      }

      this.startTimeoutMonitor("admin request for id '" + this.getId() + "'");
      this.status = AdminRequestStatus.createAdminRequestStatus(this);
      if (!this.toBeCancelled() && !requestManager.isCancelPending(this.getId())) {
         this.processRequest();
      } else {
         if (isDebugEnabled()) {
            debug("request '" + this.getId() + "' has been cancelled - will not proceed with the deploy");
         }

         requestManager.removePendingCancel(this.getId());
         synchronized(this.requestLock) {
            if (this.status != null) {
               this.status.signalCancelSucceeded();
            }

         }
      }
   }

   private void processRequest() {
      requestManager.addToRequestTable(this);
      this.proposedDomainVersion = deploymentsManager.getCurrentDomainVersion().getCopy();
      synchronized(this.requestLock) {
         this.processDeployments();

         try {
            AdminServerState state = this.getCurrentState();
            if (state != null) {
               state.start();
            }
         } catch (InvalidStateException var4) {
            if (isDebugEnabled()) {
               debug("could not start deployment of id '" + this.identifier + "' due to '" + var4.toString());
            }
         }

      }
   }

   private void processDeployments() {
      for(int i = 0; i < this.deployments.size(); ++i) {
         Deployment deployment = (Deployment)this.deployments.get(i);
         if (deployment != null) {
            String deploymentIdentity = deployment.getCallbackHandlerId();
            this.proposedDomainVersion.addOrUpdateDeploymentVersion(deploymentIdentity, deployment.getProposedVersion());
            DeploymentServiceCallbackHandler callbackHandler = deploymentsManager.getCallbackHandler(deploymentIdentity);
            if (callbackHandler == null && isDebugEnabled()) {
               debug("no DeploymentServiceCallbackHandler associated with '" + deploymentIdentity + "'");
            }

            this.updateTargetList(deployment);
         }
      }

   }

   private void updateTargetList(Deployment deployment) {
      boolean isConfiguration = DeploymentType.CONFIGURATION.equals(deployment.getDeploymentType());
      String[] deploymentTargets = deployment.getTargets();

      for(int j = 0; j < deploymentTargets.length; ++j) {
         this.addToTargetList(deploymentTargets[j], isConfiguration);
      }

   }

   private void addToTargetList(String target, boolean isConfiguration) {
      this.targets.add(target);
      ClusterMBean targetCluster = TargetingUtils.getTargetCluster(target);
      if (targetCluster == null) {
         this.status.addTargetedServer(target, false);
         if (isDebugEnabled()) {
            debug("Added '" + target + "' to target server list");
         }
      } else {
         ServerMBean[] serverList = targetCluster.getServers();

         for(int i = 0; i < serverList.length; ++i) {
            String serverName = serverList[i].getName();
            this.status.addTargetedServer(serverName, !isConfiguration);
            if (isDebugEnabled()) {
               debug("Added clustered server '" + serverName + "' to target server list");
            }
         }
      }

   }

   public final Iterator getTargets() {
      synchronized(this.requestLock) {
         return this.status == null ? null : this.targets.iterator();
      }
   }

   public final Iterator getTargetServers() {
      synchronized(this.requestLock) {
         return this.status == null ? null : this.status.getTargetedServers();
      }
   }

   public final void cancel() throws InvalidStateException {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.status.setCancelledByUser();
            AdminServerState state = this.getCurrentState();
            if (state != null) {
               state.cancel();
            }

         }
      }
   }

   public final void cancelDueToClusterConstraints() throws InvalidStateException {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.status.setCancelledByClusterConstraints();
            AdminServerState state = this.getCurrentState();
            if (state != null) {
               state.cancel();
            }

         }
      }
   }

   public final void requestTimedout() {
      synchronized(this.requestLock) {
         if (this.status != null) {
            if (isDebugEnabled()) {
               debug(this.identifier + " timed out on admin server");
            }

            this.status.setTimedOut();
            AdminServerState state = this.getCurrentState();
            if (state != null) {
               state.requestTimedOut();
            }

         }
      }
   }

   public final AdminServerState getCurrentState() {
      AdminServerState result = null;
      synchronized(this.requestLock) {
         if (this.status != null) {
            result = this.status.getCurrentState();
         }

         return result;
      }
   }

   public final boolean equals(Object inObj) {
      if (this == inObj) {
         return true;
      } else {
         return inObj instanceof AdminRequestImpl ? super.equals(inObj) : false;
      }
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public final String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AdminDeploymentRequest id '" + this.getId() + "'");
      return sb.toString();
   }

   public void receivedPrepareSucceeded(long requestId, String target, boolean requiresRestart) {
      synchronized(this.requestLock) {
         AdminServerState state = this.getCurrentState();
         if (state != null) {
            state.receivedPrepareSucceeded(target, requiresRestart);
         }

      }
   }

   public void receivedPrepareFailed(String target, Throwable reason) {
      this.receivedPrepareFailed(target, reason, false);
   }

   public void receivedPrepareFailed(String target, Throwable reason, boolean failedWhileSending) {
      synchronized(this.requestLock) {
         AdminServerState state = this.getCurrentState();
         if (state != null) {
            state.receivedPrepareFailed(target, reason, failedWhileSending);
         }

      }
   }

   public void receivedConfigPrepareSucceeded(long requestId, String target, boolean requiresRestart) {
      synchronized(this.requestLock) {
         AdminServerState state = this.getCurrentState();
         if (state != null) {
            state.receivedConfigPrepareSucceeded(target, requiresRestart);
         }

      }
   }

   public void receivedConfigPrepareFailed(String target, Throwable reason) {
      synchronized(this.requestLock) {
         AdminServerState state = this.getCurrentState();
         if (state != null) {
            state.receivedConfigPrepareFailed(target, reason);
         }

      }
   }

   public void receivedCommitSucceeded(String target) {
      synchronized(this.requestLock) {
         AdminServerState state = this.getCurrentState();
         if (state != null) {
            state.receivedCommitSucceeded(target);
         }

      }
   }

   public void receivedCommitFailed(String target, Throwable reason) {
      synchronized(this.requestLock) {
         AdminServerState state = this.getCurrentState();
         if (state != null) {
            state.receivedCommitFailed(target, reason);
         }

      }
   }

   public void receivedCancelSucceeded(String target) {
      synchronized(this.requestLock) {
         AdminServerState state = this.getCurrentState();
         if (state != null) {
            state.receivedCancelSucceeded(target);
         }

      }
   }

   public void receivedCancelFailed(String target, Throwable reason) {
      synchronized(this.requestLock) {
         AdminServerState state = this.getCurrentState();
         if (state != null) {
            state.receivedCancelFailed(target, reason);
         }

      }
   }

   public void prepareDeliveredTo(String target) {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.status.prepareDeliveredTo(target);
         }
      }
   }

   public void prepareDeliveryFailureWhenContacting(String target, Exception e) {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.status.prepareDeliveryFailureWhenContacting(target, e);
         }
      }
   }

   public void commitDeliveredTo(String target) {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.status.commitDeliveredTo(target);
         }
      }
   }

   public void commitDeliveryFailureWhenContacting(String target, Exception e) {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.status.commitDeliveryFailureWhenContacting(target, e);
         }
      }
   }

   public void cancelDeliveredTo(String target) {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.status.cancelDeliveredTo(target);
         }
      }
   }

   public void cancelDeliveryFailureWhenContacting(String target, Exception e) {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.status.cancelDeliveryFailureWhenContacting(target, e);
         }
      }
   }

   public final void reset() {
      synchronized(this.requestLock) {
         if (this.status != null) {
            this.targets.clear();
            this.proposedDomainVersion = null;
            this.status.reset();
            this.status = null;
         }

         if (this.taskRuntime != null) {
            this.taskRuntime.unregisterIfNoSubTasks();
            this.taskRuntime = null;
         }

      }
   }

   public void setSendCancelAction(Runnable a) {
      this.sendCancelAction = a;
   }

   public Runnable getSendCancelAction() {
      return this.sendCancelAction;
   }
}
