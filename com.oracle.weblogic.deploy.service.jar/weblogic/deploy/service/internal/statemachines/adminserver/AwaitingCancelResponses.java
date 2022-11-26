package weblogic.deploy.service.internal.statemachines.adminserver;

import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.adminserver.AdminRequestManager;
import weblogic.deploy.service.internal.adminserver.TimeAuditorManager;

public final class AwaitingCancelResponses extends AdminServerState {
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
      return this.doCancelCompletionCheck() ? null : this.getCurrentState();
   }

   public final AdminServerState receivedCancelFailed(String serverName, Throwable e) {
      this.fireStateTransitionEvent(this, "receivedCancelFailed", this.requestId);
      this.request.getStatus().receivedCancelFailedFrom(serverName, e);
      return this.doCancelCompletionCheck() ? null : this.getCurrentState();
   }

   protected final boolean doCancelCompletionCheck() {
      if (this.request.getStatus().receivedAllCancelResponses()) {
         TimeAuditorManager.getInstance().endTransition(this.requestId, 3);
         this.signalCancelCompletion();
         return true;
      } else {
         return false;
      }
   }

   private final void signalCancelCompletion() {
      if (this.request.getStatus().cancelFailed()) {
         this.request.getStatus().signalCancelFailed(true);
      } else {
         this.request.getStatus().signalCancelSucceeded();
      }

   }

   public final String toString() {
      return "AwaitingCancelResponses";
   }
}
