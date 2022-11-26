package weblogic.deploy.service.internal.statemachines.targetserver;

import java.util.Iterator;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;
import weblogic.deploy.service.internal.targetserver.TimeAuditorManager;
import weblogic.utils.StackTraceUtils;

public class AwaitingContextUpdateCompletion extends TargetServerState {
   public final synchronized TargetServerState contextUpdated() {
      this.fireStateTransitionEvent(this, "contextUpdated", this.getId());
      this.handleContextUpdateSuccess();
      return this.getCurrentState();
   }

   public final synchronized TargetServerState contextUpdateFailed(Throwable reason) {
      this.fireStateTransitionEvent(this, "contextUpdated", this.getId());
      this.handleContextUpdateFailure(reason);
      return this.getCurrentState();
   }

   private void handleContextUpdateSuccess() {
      if (!this.cancelIfNecessary()) {
         TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
         if (deploymentRequest.getDeploymentContext().isRestartRequired()) {
            this.deploymentStatus.setRestartPending();
         }

         this.setExpectedNextState(2);

         try {
            this.callDeploymentReceivers(deploymentRequest);
         } catch (Exception var3) {
            if (this.isDebugEnabled()) {
               this.debug("AwaitingContextUpdateCompletion:handleContextUpdateSuccess: encountered an error " + StackTraceUtils.throwable2StackTrace(var3));
            }

            if (deploymentRequest.getSyncToAdminDeployments() != null) {
               if (Debug.serviceLogger.isDebugEnabled()) {
                  Debug.serviceLogger.debug("'context update' sync to admin version " + deploymentRequest.getSyncToAdminVersion() + " - handling failed - transitioning server to admin state");
               }

               this.transitionServerToAdminState("context update", var3);
               return;
            }

            if (this.cancelIfNecessary()) {
               return;
            }

            this.setExpectedNextState(6);
            this.sendPrepareNak(new Exception(var3.getMessage()));
         }

      }
   }

   private void handleContextUpdateFailure(Throwable reason) {
      if (!this.cancelIfNecessary()) {
         TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
         if (deploymentRequest.getSyncToAdminVersion() != null) {
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug("'context update' failed in sync to admin version " + deploymentRequest.getSyncToAdminVersion() + " - transitioning server to admin state");
            }

            this.deploymentStatus.setSyncToAdminFailed();
            this.setExpectedNextState(6);
            this.sendPrepareNak(reason);
            this.transitionServerToAdminState("context update", reason);
         } else {
            this.setExpectedNextState(6);
            this.sendPrepareNak(reason);
         }
      }
   }

   protected final void callDeploymentReceivers(TargetRequestImpl deploymentRequest) throws Exception {
      TimeAuditorManager.getInstance().startTransition(deploymentRequest.getId(), 1);
      DeploymentReceiver configCallbackHandler = null;
      Map deploymentsMap = deploymentRequest.getDeploymentsMap();
      Iterator iterator = deploymentsMap.keySet().iterator();

      while(true) {
         while(iterator.hasNext()) {
            String callbackHandlerId = (String)iterator.next();
            DeploymentReceiver callbackHandler = this.deploymentsManager.getDeploymentReceiver(callbackHandlerId);
            if (deploymentRequest.isConfigurationProviderCalledLast() && configCallbackHandler == null && callbackHandlerId.equals("Configuration")) {
               configCallbackHandler = callbackHandler;
               if (this.isDebugEnabled()) {
                  this.debug("'call config last' set for id: " + deploymentRequest.getId());
               }
            } else {
               TimeAuditorManager.getInstance().startDeploymentTransition(deploymentRequest.getId(), callbackHandlerId, 1);
               callbackHandler.prepare(deploymentRequest.getDeploymentContext());
            }
         }

         if (configCallbackHandler != null) {
            TimeAuditorManager.getInstance().startDeploymentTransition(deploymentRequest.getId(), configCallbackHandler.getHandlerIdentity(), 1);
            configCallbackHandler.prepare(deploymentRequest.getDeploymentContext());
         }

         return;
      }
   }

   public final String toString() {
      return "AwaitingContextUpdateCompletion";
   }
}
