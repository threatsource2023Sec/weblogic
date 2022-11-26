package weblogic.deploy.service.internal.statemachines.targetserver;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.statemachines.State;
import weblogic.deploy.service.internal.targetserver.DeploymentContextImpl;
import weblogic.deploy.service.internal.targetserver.TargetDeploymentsManager;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;
import weblogic.deploy.service.internal.targetserver.TargetRequestManager;
import weblogic.deploy.service.internal.targetserver.TargetRequestStatus;
import weblogic.deploy.service.internal.targetserver.TimeAuditorManager;
import weblogic.deploy.service.internal.transport.DeploymentServiceMessage;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;

public class TargetServerState extends State {
   protected TargetRequestStatus deploymentStatus = null;
   protected TargetRequestManager requestsManager = null;
   protected TargetDeploymentsManager deploymentsManager = null;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public TargetServerState() {
      this.deploymentsManager = TargetDeploymentsManager.getInstance();
      this.requestsManager = TargetRequestManager.getInstance();
   }

   public synchronized TargetServerState contextUpdated() {
      this.fireStateTransitionEvent(this, "contextUpdated", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState contextUpdateFailed(Throwable reason) {
      this.fireStateTransitionEvent(this, "contextUpdateFailed", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState prepareSucceeded() {
      this.fireStateTransitionEvent(this, "prepareSucceeded", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState prepareFailed(Throwable reason) {
      this.fireStateTransitionEvent(this, "prepareFailed", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState configDeploymentPrepareSucceeded() {
      this.fireStateTransitionEvent(this, "configDeploymentPrepareSucceeded", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState configDeploymentPrepareFailed(Throwable reason) {
      this.fireStateTransitionEvent(this, "configDeploymentPrepareFailed", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState commitSucceeded() {
      this.fireStateTransitionEvent(this, "commitSucceeded", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState commitFailed(Throwable reason) {
      this.fireStateTransitionEvent(this, "commitFailed", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState cancelSucceeded() {
      this.fireStateTransitionEvent(this, "cancelSucceeded", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState cancelFailed() {
      this.fireStateTransitionEvent(this, "cancelFailed", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState receivedPrepare() {
      this.fireStateTransitionEvent(this, "receivedPrepare", 0L);
      return this.getCurrentState();
   }

   public synchronized TargetServerState receivedCommit() {
      this.fireStateTransitionEvent(this, "receivedCommit", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState receivedCancel() {
      this.fireStateTransitionEvent(this, "receivedCancel", this.getId());
      return this.getCurrentState();
   }

   public synchronized TargetServerState receivedGetDeploymentsResponse(DeploymentServiceMessage message) {
      this.fireStateTransitionEvent(this, "receivedGetDeltasResponse", 0L);
      return this.getCurrentState();
   }

   public synchronized TargetServerState abort() {
      this.fireStateTransitionEvent(this, "abort", this.getId());
      if (this.deploymentStatus != null) {
         this.deploymentStatus.setAborted();
      }

      if (this.deploymentStatus != null && !this.deploymentStatus.isCanceled()) {
         if (this.getId() != -1L) {
            this.requestsManager.addToPendingCancels(this.getId());
            if (this.isDebugEnabled()) {
               this.debug("'abort' with id '" + this.getId() + "' added as pending cancel for future cancel request");
            }
         }

         this.cancelIfNecessary();
      }

      return this.getCurrentState();
   }

   public final void setDeploymentStatus(TargetRequestStatus status) {
      this.deploymentStatus = status;
   }

   protected final void transitionServerToAdminState(String msg, Throwable reason) {
      ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      boolean var11 = false;

      TargetRequestImpl request;
      label124: {
         try {
            try {
               var11 = true;
               DeploymentServiceLogger.logTransitioningServerToAdminState(msg, reason);
               serverRuntime.setRestartRequired(true);
               serverRuntime.suspend();
               var11 = false;
               break label124;
            } catch (ServerLifecycleException var13) {
               if (this.isDebugEnabled()) {
                  this.debug("Attempt to 'suspend' server failed due to '" + var13 + "' will attempt to 'force suspend' the server");
               }
            }

            try {
               serverRuntime.forceSuspend();
               var11 = false;
            } catch (Exception var12) {
               if (this.isDebugEnabled()) {
                  this.debug("Attempt to 'force suspend' server failed due to '" + var12 + "' ignoring the event");
                  var11 = false;
               } else {
                  var11 = false;
               }
            }
         } finally {
            if (var11) {
               if (this.deploymentStatus != null) {
                  TargetRequestImpl request = this.deploymentStatus.getDeploymentRequest();
                  if (!this.deploymentStatus.isSyncToAdminFailed()) {
                     request.resetSyncToAdminDeployments();
                  }

                  this.deploymentStatus.reset();
               }

            }
         }

         if (this.deploymentStatus != null) {
            request = this.deploymentStatus.getDeploymentRequest();
            if (!this.deploymentStatus.isSyncToAdminFailed()) {
               request.resetSyncToAdminDeployments();
            }

            this.deploymentStatus.reset();
         }

         return;
      }

      if (this.deploymentStatus != null) {
         request = this.deploymentStatus.getDeploymentRequest();
         if (!this.deploymentStatus.isSyncToAdminFailed()) {
            request.resetSyncToAdminDeployments();
         }

         this.deploymentStatus.reset();
      }

   }

   protected final TargetServerState getCurrentState() {
      TargetServerState result = null;
      if (this.deploymentStatus != null) {
         result = this.deploymentStatus.getCurrentState();
      }

      return result;
   }

   protected final TargetRequestImpl getDeploymentRequest() {
      return this.requestsManager.getRequest(this.getId());
   }

   protected final DeploymentContextImpl getDeploymentContext() {
      TargetRequestImpl request = this.getDeploymentRequest();
      return request == null ? null : request.getDeploymentContext();
   }

   protected final long getId() {
      long result = -1L;
      if (this.deploymentStatus != null) {
         result = this.deploymentStatus.getId();
      }

      return result;
   }

   protected final String getTargetStateString(int stateId) {
      String targetStateString = null;
      switch (stateId) {
         case 0:
            targetStateString = "ReceivedPrepare";
            break;
         case 1:
            targetStateString = "AwaitingContextUpdateCompletion";
            break;
         case 2:
            targetStateString = "AwaitingPrepareCompletion";
            break;
         case 3:
            targetStateString = "AwaitingCommit";
            break;
         case 4:
            targetStateString = "AwaitingCommitCompletion";
            break;
         case 5:
            targetStateString = "AwaitingGetDeploymentsResponse";
            break;
         case 6:
            targetStateString = "AwaitingCancel";
            break;
         case 7:
            targetStateString = "AwaitingCancelCompletion";
      }

      return targetStateString;
   }

   protected final void setExpectedNextState(int stateId) {
      TargetServerState targetState = this.deploymentStatus.getTargetServerState(stateId);
      if (this.isDebugEnabled()) {
         this.debug("Setting target state of deployment '" + this.getId() + "' to : " + this.getTargetStateString(stateId));
      }

      this.deploymentStatus.setCurrentState(targetState);
   }

   private final void dispatchCancelToDeploymentReceivers() {
      TargetRequestImpl depRequest = this.getDeploymentRequest();
      TimeAuditorManager.getInstance().startTransition(depRequest.getId(), 3);
      if (depRequest != null && this.deploymentStatus != null && !this.deploymentStatus.isCancelDispatched()) {
         Iterator iterator = this.deploymentStatus.getDeploymentsToBeCancelled();
         this.deploymentStatus.setCancelDispatched();
         if (iterator == null) {
            if (this.deploymentStatus.isCanceled()) {
               if (this.isDebugEnabled()) {
                  this.debug("No deployments to cancel for request '" + depRequest.getId() + "'");
               }

               this.sendCancelSucceeded();
            }
         } else {
            while(iterator.hasNext()) {
               String callbackHandlerId = (String)iterator.next();
               DeploymentReceiver deploymentReceiver = this.deploymentsManager.getDeploymentReceiver(callbackHandlerId);
               DeploymentContextImpl context = depRequest.getDeploymentContext();
               TimeAuditorManager.getInstance().startDeploymentTransition(depRequest.getId(), callbackHandlerId, 3);
               deploymentReceiver.cancel(context);
            }
         }

      }
   }

   protected final void sendPrepareAck() {
      TargetRequestImpl depRequest = this.getDeploymentRequest();
      if (this.isDebugEnabled()) {
         this.debug("sendPrepareAck(): request id = " + depRequest.getId());
      }

      if (depRequest != null) {
         try {
            if (this.isDebugEnabled()) {
               this.debug("sendPrepareAck(): call sender.sendPrepareAckMsg()");
            }

            this.sender.sendPrepareAckMsg(depRequest.getId(), false);
         } catch (RemoteException var3) {
            if (this.isDebugEnabled()) {
               this.debug("sendPrepareAck(): unable to reach admin server, do cleanup");
            }

            this.deploymentStatus.setAborted();
            this.cancelIfNecessary();
         }

      }
   }

   protected final void sendPrepareNak(Throwable e) {
      TargetRequestImpl depRequest = this.getDeploymentRequest();
      if (depRequest != null) {
         try {
            this.sender.sendPrepareNakMsg(depRequest.getId(), e);
         } catch (RemoteException var4) {
            this.deploymentStatus.setAborted();
            this.cancelIfNecessary();
         }

      }
   }

   protected final void sendConfigPrepareNak(Throwable e) {
      TargetRequestImpl depRequest = this.getDeploymentRequest();
      if (depRequest != null) {
         try {
            this.sender.sendConfigPrepareNakMsg(depRequest.getId(), e);
         } catch (RemoteException var4) {
            this.deploymentStatus.setAborted();
            this.cancelIfNecessary();
         }

      }
   }

   protected final void sendCommitSucceeded() {
      long requestId = this.getId();
      TargetRequestImpl depRequest = this.requestsManager.getRequest(requestId);
      if (depRequest == null) {
         if (this.isDebugEnabled()) {
            this.debug("no request corresponding to id '" + requestId + "' - aborting 'commit success' send attempt");
         }

      } else {
         this.sender.sendCommitSucceededMsg(depRequest.getId(), depRequest.getTimeoutInterval());
         this.deploymentStatus.reset();
      }
   }

   protected final void sendCommitFailed(Throwable t) throws RemoteException {
      long requestId = this.getId();
      TargetRequestImpl depRequest = this.requestsManager.getRequest(requestId);
      if (depRequest == null) {
         if (this.isDebugEnabled()) {
            this.debug("no request corresponding to id '" + requestId + "' - aborting 'commit failure' send attempt");
         }

      } else {
         try {
            this.sender.sendCommitFailedMsg(depRequest.getId(), t);
         } finally {
            this.deploymentStatus.reset();
         }

      }
   }

   protected final void sendCancelSucceeded() {
      TargetRequestImpl depRequest = this.getDeploymentRequest();
      if (depRequest != null) {
         this.sender.sendCancelSucceededMsg(depRequest.getId());
         this.deploymentStatus.reset();
      }
   }

   protected final void sendCancelFailed(Throwable e) {
      TargetRequestImpl depRequest = this.getDeploymentRequest();
      if (depRequest != null) {
         this.sender.sendCancelFailedMsg(depRequest.getId(), e);
         this.deploymentStatus.reset();
      }
   }

   protected void setupDeploymentRequest(DomainVersion currentVersion, DomainVersion proposedVersion, TargetRequestImpl deploymentRequest, Iterator iterator) throws Exception {
      Map deploymentsMap = new LinkedHashMap();
      this.getDeploymentReceiversInfo(iterator, currentVersion, proposedVersion, deploymentsMap);
      deploymentRequest.setProposedDomainVersion(proposedVersion);
      deploymentRequest.setPreparingFromVersion(currentVersion);
      deploymentRequest.setDeploymentsMap(deploymentsMap);
   }

   protected void callDeploymentReceivers() throws Exception {
   }

   protected final void getDeploymentReceiversInfo(Iterator iterator, DomainVersion ourVersion, DomainVersion proposedDomainVersion, Map deploymentsMap) throws Exception {
      while(iterator.hasNext()) {
         Deployment deployment = (Deployment)iterator.next();
         if (deployment != null) {
            String receiverId = deployment.getCallbackHandlerId();
            if (this.deploymentsManager.getDeploymentReceiver(receiverId) == null) {
               String msg = DeployerRuntimeLogger.receiverNotFound(receiverId);
               throw new Exception(msg);
            }

            this.deploymentStatus.addToDeploymentsAckList(receiverId);
            List deploymentsList = (List)deploymentsMap.get(receiverId);
            if (deploymentsList == null) {
               deploymentsList = new ArrayList();
               deploymentsMap.put(receiverId, deploymentsList);
            }

            ((List)deploymentsList).add(deployment);
            proposedDomainVersion.addOrUpdateDeploymentVersion(receiverId, deployment.getProposedVersion());
         }
      }

   }

   protected final void setupDeploymentContext() throws Exception {
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
      if (deploymentRequest == null) {
         String msg = DeployerRuntimeLogger.noDeploymentRequest();
         throw new Exception(msg);
      } else {
         DeploymentContextImpl context = deploymentRequest.getDeploymentContext();
         if (context == null) {
            context = new DeploymentContextImpl(deploymentRequest);
            deploymentRequest.setDeploymentContext(context);
         }

      }
   }

   protected final void syncWithAdminServer(DomainVersion fromVersion) {
      if (this.isDebugEnabled()) {
         this.debug("Deployment '" + this.getId() + "' in '" + this.getCurrentState() + "' state needs to sync with admin to catch up from '" + fromVersion.toString());
      }

      TargetServerState targetState = this.deploymentStatus.getTargetServerState(5);
      this.deploymentStatus.setCurrentState(targetState);
      this.sender.sendGetDeploymentsMsg(fromVersion, this.getId());
   }

   protected boolean cancelIfNecessary() {
      boolean result = false;
      if (this.deploymentStatus != null && this.deploymentStatus.isCanceledOrAborted()) {
         result = true;
         this.doCancel();
      }

      return result;
   }

   private void doCancel() {
      this.setExpectedNextState(7);
      this.dispatchCancelToDeploymentReceivers();
   }

   protected final void fireStateTransitionEvent(State state, String method, long id) {
      if (this.isDebugEnabled()) {
         this.debug("Target DeploymentService event : '" + state.toString() + "." + method + "()' for deployment id '" + id + "'");
      }

   }
}
