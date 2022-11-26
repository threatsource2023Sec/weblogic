package weblogic.deploy.service.internal.statemachines.targetserver;

import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.DomainVersion;

public class AwaitingCancelCompletion extends TargetServerState {
   public final TargetServerState cancelSucceeded() {
      this.fireStateTransitionEvent(this, "cancelSucceeded", this.getId());
      if (this.deploymentStatus != null) {
         if (this.deploymentStatus.isCanceled()) {
            this.sendCancelSucceeded();
         } else {
            if (!this.deploymentStatus.isTimedOut() && !this.deploymentStatus.isAborted()) {
               DomainVersion currentVersion = this.deploymentsManager.getCurrentDomainVersion();
               this.syncWithAdminServer(currentVersion);
               return this.getCurrentState();
            }

            this.deploymentStatus.reset();
         }
      }

      return null;
   }

   public final TargetServerState cancelFailed() {
      this.fireStateTransitionEvent(this, "cancelFailed", this.getId());
      if (this.deploymentStatus != null) {
         if (this.deploymentStatus.isCanceled()) {
            this.sendCancelFailed(this.deploymentStatus.getCancelFailureError());
         } else if (this.deploymentStatus.isTimedOut()) {
            this.deploymentStatus.reset();
         } else {
            String msg = DeploymentServiceLogger.optimisticConcurrencyErr(this.getId());
            Exception e = new Exception(msg, this.deploymentStatus.getCancelFailureError());
            this.sendCancelFailed(e);
         }
      }

      return null;
   }

   public final String toString() {
      return "AwaitingCancelCompletion";
   }
}
