package weblogic.deploy.service.internal.statemachines.adminserver;

import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.adminserver.AdminRequestManager;

public final class AwaitingCommitResponses extends AdminServerState {
   public final AdminServerState cancel() throws InvalidStateException {
      throw new InvalidStateException(DeploymentServiceLogger.logTooLateToCancelLoggable(this.requestId, this.toString()).getMessage());
   }

   public final AdminServerState requestTimedOut() {
      this.fireStateTransitionEvent(this, "requestTimedout", this.requestId);
      synchronized(AdminRequestManager.getInstance()) {
         this.request.getStatus().signalCommitFailed();
         return null;
      }
   }

   public final String toString() {
      return "AwaitingCommitResponses";
   }
}
