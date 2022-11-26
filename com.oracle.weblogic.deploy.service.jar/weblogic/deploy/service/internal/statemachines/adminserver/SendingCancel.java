package weblogic.deploy.service.internal.statemachines.adminserver;

import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.adminserver.AdminRequestManager;

public final class SendingCancel extends AdminServerState {
   public final AdminServerState cancel() throws InvalidStateException {
      throw new InvalidStateException(DeploymentServiceLogger.logAlreadyCancelledLoggable(this.requestId, this.toString()).getMessage());
   }

   public final AdminServerState requestTimedOut() {
      this.fireStateTransitionEvent(this, "requestTimedout", this.requestId);
      synchronized(AdminRequestManager.getInstance()) {
         this.request.getStatus().signalCancelFailed(true);
         return null;
      }
   }

   public final AdminServerState receivedPrepareSucceeded(String serverName, boolean requiresRestart) {
      this.fireStateTransitionEvent(this, "receivedPrepareSucceeded", this.requestId);
      return this.getCurrentState();
   }

   public AdminServerState receivedPrepareFailed(String serverName, Throwable e, boolean failedWhileSending) {
      this.fireStateTransitionEvent(this, "receivedPrepareFailed", this.requestId);
      return this.getCurrentState();
   }

   public final AdminServerState receivedCancelSucceeded(String serverName) {
      this.fireStateTransitionEvent(this, "receivedCancelSucceeded", this.requestId);
      this.request.getStatus().receivedCancelSucceededFrom(serverName);
      return this.getCurrentState();
   }

   public final AdminServerState receivedCancelFailed(String serverName, Throwable e) {
      this.fireStateTransitionEvent(this, "receivedCancelFailed", this.requestId);
      this.request.getStatus().receivedCancelFailedFrom(serverName, e);
      return this.getCurrentState();
   }

   public final AdminServerState allCancelsDelivered() {
      this.fireStateTransitionEvent(this, "cancelsDelivered", this.requestId);
      this.setExpectedNextState(5);
      AdminServerState currState = this.getCurrentState();
      if (currState != null) {
         currState.doCancelCompletionCheck();
      }

      return this.getCurrentState();
   }

   public final String toString() {
      return "SendingCancel";
   }
}
