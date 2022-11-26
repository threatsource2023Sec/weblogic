package weblogic.deploy.service.internal.statemachines.adminserver;

import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.adminserver.TimeAuditorManager;

public final class SendingPrepare extends AdminServerState {
   public final AdminServerState start() throws InvalidStateException {
      this.fireStateTransitionEvent(this, "start", this.requestId);
      TimeAuditorManager.getInstance().startAuditor(this.requestId);
      TimeAuditorManager.getInstance().startTransition(this.requestId, 1);
      return this.sendDeploymentRequest();
   }

   public AdminServerState requestTimedOut() {
      this.fireStateTransitionEvent(this, "requestTimedout", this.requestId);
      return this.getCurrentState();
   }

   public final AdminServerState receivedPrepareSucceeded(String serverName, boolean requiresRestart) {
      this.fireStateTransitionEvent(this, "receivedPrepareSucceeded", this.requestId);
      this.request.getStatus().receivedPrepareSucceededFrom(serverName, requiresRestart);
      return this.getCurrentState();
   }

   public AdminServerState receivedPrepareFailed(String serverName, Throwable e, boolean failedWhileSending) {
      this.fireStateTransitionEvent(this, "receivedPrepareFailed", this.requestId);
      this.request.getStatus().receivedPrepareFailedFrom(serverName, e, failedWhileSending);
      return this.getCurrentState();
   }

   public synchronized AdminServerState receivedConfigPrepareSucceeded(String serverName, boolean requiresRestart) {
      this.fireStateTransitionEvent(this, "receivedConfigPrepareSucceeded", this.requestId);
      this.request.getStatus().receivedConfigPrepareSucceededFrom(serverName, requiresRestart);
      return this.getCurrentState();
   }

   public final synchronized AdminServerState receivedConfigPrepareFailed(String serverName, Throwable e) {
      this.fireStateTransitionEvent(this, "receivedConfigPrepareFailed", this.requestId);
      this.request.getStatus().receivedConfigPrepareFailedFrom(serverName, e);
      return this.getCurrentState();
   }

   public final AdminServerState allPreparesDelivered() {
      this.fireStateTransitionEvent(this, "preparesDelivered", this.requestId);
      this.setExpectedNextState(1);
      this.getCurrentState().doConfigPrepareCompletionCheck();
      this.getCurrentState().doPrepareCompletionCheck();
      return this.getCurrentState();
   }

   private AdminServerState sendDeploymentRequest() {
      this.sender.sendRequestPrepareMsg(this.getDeploymentRequest());
      return this.getCurrentState();
   }

   public final String toString() {
      return "SendingPrepare";
   }
}
