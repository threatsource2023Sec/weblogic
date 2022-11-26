package weblogic.deploy.service.internal.statemachines.targetserver;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.targetserver.DeploymentContextImpl;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;
import weblogic.deploy.service.internal.transport.DeploymentServiceMessage;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.utils.StackTraceUtils;

public final class AwaitingGetDeploymentsResponse extends TargetServerState {
   public synchronized TargetServerState receivedCancel() {
      this.fireStateTransitionEvent(this, "receivedCancel", this.getId());
      return this.getCurrentState();
   }

   public final synchronized TargetServerState receivedGetDeploymentsResponse(DeploymentServiceMessage message) {
      this.fireStateTransitionEvent(this, "receivedGetDeltasResponse", 0L);
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest();

      try {
         this.handleGetDeploymentsResponse(deploymentRequest, message);
      } catch (Throwable var4) {
         if (Debug.serviceLogger.isDebugEnabled()) {
            Debug.serviceLogger.debug("'" + StackTraceUtils.throwable2StackTrace(var4) + "' failure when handling a 'get deployments response' - this is an unexpected failure & probably requires a server reboot to recover");
         }

         this.transitionServerToAdminState("getting deployment response", var4);
         return null;
      }

      return this.getCurrentState();
   }

   private final void handleGetDeploymentsResponse(TargetRequestImpl deploymentRequest, DeploymentServiceMessage message) throws Exception {
      this.doPreHandleResponseValidation(deploymentRequest, message);
      DomainVersion currentVersion = this.deploymentsManager.getCurrentDomainVersion();
      deploymentRequest.setSyncToAdminMessage(message);
      DomainVersion proposedVersion = deploymentRequest.getSyncToAdminVersion();
      this.setExpectedNextState(1);
      this.setupDeploymentRequest(currentVersion, proposedVersion, deploymentRequest, deploymentRequest.getSyncToAdminDeployments().iterator());
      this.setupDeploymentContext();
      Map deploymentsMap = deploymentRequest.getSyncToAdminDeploymentsMap();
      if (deploymentsMap != null && deploymentsMap.size() != 0) {
         this.callDeploymentReceivers();
      } else {
         this.doCommit(deploymentRequest);
      }
   }

   private final void doPreHandleResponseValidation(TargetRequestImpl deploymentRequest, DeploymentServiceMessage message) throws Exception {
      String msg;
      if (deploymentRequest == null) {
         msg = DeployerRuntimeLogger.invalidHandleResponse(this.getId());
         if (Debug.serviceLogger.isDebugEnabled()) {
            Debug.serviceLogger.debug(msg);
         }

         throw new Exception(msg);
      } else if (deploymentRequest.getSyncToAdminDeployments() != null) {
         msg = DeployerRuntimeLogger.duplicateHandleResponse(message.toString());
         if (Debug.serviceLogger.isDebugEnabled()) {
            Debug.serviceLogger.debug(msg);
         }

         throw new Exception(msg);
      }
   }

   protected final void setupDeploymentRequest(DomainVersion ourVersion, DomainVersion proposedDomainVersion, TargetRequestImpl deploymentRequest, Iterator iterator) throws Exception {
      Map deploymentsMap = new LinkedHashMap();
      this.getDeploymentReceiversInfo(iterator, ourVersion, proposedDomainVersion, deploymentsMap);
      deploymentRequest.setSyncToAdminDeploymentsMap(deploymentsMap);
   }

   protected final void callDeploymentReceivers() throws Exception {
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
      Map deploymentsMap = deploymentRequest.getDeploymentsMap();
      DeploymentContextImpl context = deploymentRequest.getDeploymentContext();
      boolean noReceivers = true;
      Iterator iterator = deploymentsMap.keySet().iterator();

      while(iterator.hasNext()) {
         String callbackHandlerId = (String)iterator.next();
         DeploymentReceiver deploymentReceiver = this.deploymentsManager.getDeploymentReceiver(callbackHandlerId);
         if (deploymentReceiver != null) {
            noReceivers = false;
            deploymentReceiver.updateDeploymentContext(context);
         }

         if (noReceivers) {
            this.deploymentStatus.reset();
         }
      }

   }

   private final void doCommit(TargetRequestImpl deploymentRequest) {
      DomainVersion syncToAdminVersion = deploymentRequest.getSyncToAdminVersion();
      if (syncToAdminVersion != null) {
         this.deploymentsManager.setCurrentDomainVersion(syncToAdminVersion);
      }

      this.setExpectedNextState(4);
      this.deploymentStatus.getCurrentState().commitSucceeded();
   }

   public final String toString() {
      return "AwaitingGetDeploymentsResponse";
   }
}
