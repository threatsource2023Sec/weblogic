package weblogic.deploy.service.internal.statemachines.adminserver;

import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.adminserver.AdminRequestManager;

public final class SendingCommit extends AdminServerState {
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

   public final AdminServerState allCommitsDelivered() {
      this.fireStateTransitionEvent(this, "commitsDelivered", this.requestId);
      this.setExpectedNextState(3);
      AdminServerState currState = this.getCurrentState();
      if (currState != null) {
         currState.doCommitCompletionCheck();
      }

      return this.getCurrentState();
   }

   public final String toString() {
      return "SendingCommit";
   }
}
