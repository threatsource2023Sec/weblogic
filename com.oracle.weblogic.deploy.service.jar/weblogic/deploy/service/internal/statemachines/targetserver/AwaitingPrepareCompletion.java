package weblogic.deploy.service.internal.statemachines.targetserver;

import java.rmi.RemoteException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;

public final class AwaitingPrepareCompletion extends TargetServerState {
   public final synchronized TargetServerState prepareSucceeded() {
      this.fireStateTransitionEvent(this, "prepareSucceeded", this.getId());
      if (this.cancelIfNecessary()) {
         return null;
      } else {
         this.setExpectedNextState(3);
         TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
         RemoteException e;
         if (deploymentRequest == null) {
            if (this.isDebugEnabled()) {
               this.debug("'prepare succeeded' received for id: " + this.getId() + " that has no request - it may completed or been cancelled ");
            }

            if (this.getId() != -1L) {
               try {
                  this.sender.sendPrepareAckMsg(this.getId(), false);
               } catch (RemoteException var5) {
                  e = var5;

                  try {
                     this.sender.sendPrepareNakMsg(this.getId(), e);
                  } catch (Exception var4) {
                  }
               }
            }

            return null;
         } else {
            if (deploymentRequest.getSyncToAdminDeployments() != null) {
               this.deploymentStatus.getCurrentState().receivedCommit();
            } else {
               try {
                  this.sender.sendPrepareAckMsg(this.getId(), this.deploymentStatus.isRestartPending());
               } catch (RemoteException var7) {
                  e = var7;

                  try {
                     this.sender.sendPrepareNakMsg(this.getId(), e);
                  } catch (Exception var6) {
                  }

                  this.deploymentStatus.setAborted();
                  this.cancelIfNecessary();
               }
            }

            return this.getCurrentState();
         }
      }
   }

   public final synchronized TargetServerState prepareFailed(Throwable reason) {
      this.fireStateTransitionEvent(this, "prepareFailed", this.getId());
      if (this.cancelIfNecessary()) {
         return null;
      } else {
         TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
         if (deploymentRequest.getSyncToAdminVersion() != null) {
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug("'prepare' failed in sync to admin version " + deploymentRequest.getSyncToAdminVersion() + " - transitioning server to admin state");
            }

            this.transitionServerToAdminState("prepare", reason);
            return null;
         } else {
            this.setExpectedNextState(6);
            this.sendPrepareNak(this.deploymentStatus.getSavedError());
            return this.getCurrentState();
         }
      }
   }

   public final synchronized TargetServerState configDeploymentPrepareSucceeded() {
      if (!this.concurrentAppPrepareEnabled(this.getDeploymentRequest())) {
         return this.getCurrentState();
      } else {
         this.fireStateTransitionEvent(this, "configDeploymentPrepareSucceeded", this.getId());
         if (this.cancelIfNecessary()) {
            return null;
         } else {
            TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
            if (deploymentRequest == null) {
               if (this.isDebugEnabled()) {
                  this.debug("'config prepare succeeded' received for id: " + this.getId() + " that has no request - it may completed or been cancelled ");
               }

               return null;
            } else if (deploymentRequest.getSyncToAdminDeployments() != null) {
               return null;
            } else {
               try {
                  this.sender.sendConfigPrepareAckMsg(this.getId(), this.deploymentStatus.isRestartPending());
               } catch (RemoteException var3) {
               }

               return this.getCurrentState();
            }
         }
      }
   }

   public synchronized TargetServerState configDeploymentPrepareFailed(Throwable reason) {
      if (!this.concurrentAppPrepareEnabled(this.getDeploymentRequest())) {
         return this.getCurrentState();
      } else {
         this.fireStateTransitionEvent(this, "configDeploymentPrepareFailed", this.getId());
         if (this.cancelIfNecessary()) {
            return null;
         } else {
            TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
            if (deploymentRequest.getSyncToAdminVersion() != null) {
               if (Debug.serviceLogger.isDebugEnabled()) {
                  Debug.serviceLogger.debug("'config prepare' failed in sync to admin version " + deploymentRequest.getSyncToAdminVersion() + " - transitioning server to admin state");
               }

               this.transitionServerToAdminState("prepare", reason);
               return null;
            } else {
               this.setExpectedNextState(6);
               this.sendConfigPrepareNak(this.deploymentStatus.getSavedError());
               return this.getCurrentState();
            }
         }
      }
   }

   public final String toString() {
      return "AwaitingPrepareCompletion";
   }
}
