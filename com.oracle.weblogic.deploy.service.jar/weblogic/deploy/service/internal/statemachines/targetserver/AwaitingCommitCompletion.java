package weblogic.deploy.service.internal.statemachines.targetserver;

import java.rmi.RemoteException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;

public final class AwaitingCommitCompletion extends TargetServerState {
   public final TargetServerState receivedCancel() {
      this.fireStateTransitionEvent(this, "receivedCancel", this.getId());
      String msg = DeploymentServiceLogger.cancelRejected(this.getId());
      this.sendCancelFailed(new Exception(msg));
      return this.getCurrentState();
   }

   public TargetServerState abort() {
      this.fireStateTransitionEvent(this, "abort", this.getId());
      if (this.isDebugEnabled()) {
         this.debug("already waiting for commit completion for id: " + this.getId() + " nothing to be done");
      }

      return this.getCurrentState();
   }

   private final TargetRequestImpl getDeploymentRequest(long id) {
      return this.requestsManager.getRequest(id);
   }

   public final TargetServerState commitSucceeded() {
      long id = this.getId();
      this.fireStateTransitionEvent(this, "commitSucceeded", id);
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest(id);
      if (deploymentRequest == null) {
         if (this.isDebugEnabled()) {
            this.debug("'commit succeeded' received on target for id: " + id + " that has no request - it may be completed or cancelled");
         }

         return null;
      } else {
         DomainVersion syncToAdminVersion = deploymentRequest.getSyncToAdminVersion();
         if (syncToAdminVersion != null) {
            if (this.isDebugEnabled()) {
               this.debug("'commit success' of sync to admin version " + syncToAdminVersion);
            }

            this.deploymentsManager.setCurrentDomainVersion(syncToAdminVersion);
            this.deploymentStatus.reset();
         } else {
            if (this.isDebugEnabled()) {
               this.debug("'commit success' on target of id " + id);
            }

            boolean needsVersionUpdate = !deploymentRequest.isControlRequest();
            if (deploymentRequest.getDeploymentContext().getContextComponent("calloutAbortedProposedConfig") != null) {
               needsVersionUpdate = false;
            }

            if (this.isDebugEnabled()) {
               this.debug("Needs VersionUpdate : " + needsVersionUpdate);
            }

            if (needsVersionUpdate) {
               this.deploymentsManager.setCurrentDomainVersion(deploymentRequest.getProposedDomainVersion());
            }

            boolean cancelled = this.deploymentStatus.isCanceled();
            this.sendCommitSucceeded();
            if (cancelled) {
               String msg = DeploymentServiceLogger.cancelRejected(this.getId());
               this.sender.sendCancelFailedMsg(id, new Exception(msg));
            }
         }

         return null;
      }
   }

   public final TargetServerState commitFailed(Throwable reason) {
      long id = this.getId();
      this.fireStateTransitionEvent(this, "commitFailed", id);
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
      if (deploymentRequest == null) {
         String message = DeploymentServiceLogger.commitFailed(id);
         if (this.isDebugEnabled()) {
            this.debug(message);
         }

         try {
            this.sender.sendCommitFailedMsg(this.getId(), new Exception(message));
         } catch (RemoteException var10) {
         }

         return null;
      } else {
         DomainVersion syncToAdminVersion = deploymentRequest.getSyncToAdminVersion();
         if (syncToAdminVersion != null) {
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug("'commit failed' in sync to admin version " + syncToAdminVersion + " - transition server to admin state");
            }

            this.transitionServerToAdminState("commit", reason);
         } else {
            boolean cancelled = this.deploymentStatus.isCanceled();

            try {
               this.sendCommitFailed(this.deploymentStatus.getCommitFailureError());
            } catch (RemoteException var12) {
               String message = DeploymentServiceLogger.sendCommitFailMsgFailed(id);

               try {
                  this.sender.sendCommitFailedMsg(this.getId(), new Exception(message));
               } catch (RemoteException var11) {
               }
            }

            if (cancelled) {
               this.sender.sendCancelFailedMsg(id, new Exception("'commit' already called on deployment request with id '" + id + "'"));
            }
         }

         return null;
      }
   }

   public final String toString() {
      return "AwaitingCommitCompletion";
   }
}
