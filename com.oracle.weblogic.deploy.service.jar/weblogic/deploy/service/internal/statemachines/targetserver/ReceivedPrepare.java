package weblogic.deploy.service.internal.statemachines.targetserver;

import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.targetserver.DeploymentContextImpl;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;
import weblogic.deploy.service.internal.targetserver.TimeAuditorManager;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;

public final class ReceivedPrepare extends TargetServerState {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public final TargetServerState receivedPrepare() {
      this.fireStateTransitionEvent(this, "receivedPrepare", this.getId());
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest();

      try {
         this.handlePrepare(deploymentRequest);
      } catch (Throwable var3) {
         if (Debug.serviceLogger.isDebugEnabled()) {
            Debug.serviceLogger.debug(var3.getMessage());
         }

         if (this.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var3));
         }

         this.setExpectedNextState(6);
         this.sendPrepareNak(new Exception(var3.getMessage()));
      }

      return this.getCurrentState();
   }

   protected final void handlePrepare(TargetRequestImpl deploymentRequest) throws Exception {
      TimeAuditorManager.getInstance().startAuditor(deploymentRequest.getId());
      TimeAuditorManager.getInstance().startTransition(deploymentRequest.getId(), 0);
      if (!this.handlePendingCancel(deploymentRequest)) {
         DomainVersion currentVersion = this.deploymentsManager.getCurrentDomainVersion();
         if (AwaitingCommit.requestShouldBeAborted(this.getDeploymentRequest())) {
            if (this.isDebugEnabled()) {
               this.debug("handlePrepare(): Abort due to callout, request id =" + deploymentRequest.getId() + ", set state to AWAITING_COMMIT, call sendPrepareAck ");
            }

            this.setExpectedNextState(3);
            this.sendPrepareAck();
         } else if (!this.syncWithAdminIfNecessary(currentVersion, deploymentRequest)) {
            this.setExpectedNextState(1);
            DomainVersion proposedVersion = currentVersion.getCopy();
            this.setupDeploymentRequest(currentVersion, proposedVersion, deploymentRequest, deploymentRequest.getDeployments());
            this.setupDeploymentContext();
            if (this.isDebugEnabled()) {
               this.debug("'Receivedprepare' for id '" + deploymentRequest + "' from version '" + deploymentRequest.getPreparingFromVersion() + "' to version '" + deploymentRequest.getProposedDomainVersion());
            }

            this.callDeploymentReceivers();
         }
      }
   }

   private final boolean handlePendingCancel(TargetRequestImpl deploymentRequest) {
      long requestId = deploymentRequest.getId();
      if (this.requestsManager.hasAPendingCancelFor(requestId)) {
         if (this.isDebugEnabled()) {
            this.debug("ReceivedPrepare: handling pending cancel for request with id '" + requestId + "'");
         }

         this.requestsManager.removePendingCancelFor(requestId);
         this.sendCancelSucceeded();
         return true;
      } else {
         return false;
      }
   }

   private final boolean syncWithAdminIfNecessary(DomainVersion currentVersion, TargetRequestImpl deploymentRequest) {
      if (ManagementService.getPropertyService(kernelId).isAdminServer()) {
         return false;
      } else {
         boolean result = false;
         if (deploymentRequest.isSyncAlreadyPerformed()) {
            return false;
         } else {
            if (!currentVersion.equals(deploymentRequest.getDomainVersion())) {
               result = true;
               this.syncWithAdminServer(currentVersion);
            }

            return result;
         }
      }
   }

   protected final void doPrePrepareValidation(TargetRequestImpl deploymentRequest) throws Exception {
      if (deploymentRequest == null) {
         String message = DeployerRuntimeLogger.invalidPrepare(this.getId());
         if (this.isDebugEnabled()) {
            this.debug(message);
         }

         throw new Exception(message);
      }
   }

   protected final void callDeploymentReceivers() throws Exception {
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
      Map deploymentsMap = deploymentRequest.getDeploymentsMap();
      DeploymentContextImpl context = deploymentRequest.getDeploymentContext();
      Iterator iterator = deploymentsMap.keySet().iterator();

      while(iterator.hasNext()) {
         String callbackHandlerId = (String)iterator.next();
         DeploymentReceiver deploymentReceiver = this.deploymentsManager.getDeploymentReceiver(callbackHandlerId);
         TimeAuditorManager.getInstance().startDeploymentTransition(deploymentRequest.getId(), callbackHandlerId, 0);
         deploymentReceiver.updateDeploymentContext(context);
      }

   }

   public final String toString() {
      return "ReceivedPrepare";
   }
}
