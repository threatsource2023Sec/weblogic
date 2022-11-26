package weblogic.deploy.service.internal.statemachines.adminserver;

import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.adminserver.TimeAuditorManager;

public final class AwaitingPrepareResponses extends AdminServerState {
   private boolean releasedExclusiveLock = false;

   public AdminServerState cancel() throws InvalidStateException {
      this.fireStateTransitionEvent(this, "requestCancelled", this.requestId);
      return this.getCurrentState();
   }

   public AdminServerState requestTimedOut() {
      this.fireStateTransitionEvent(this, "requestTimedout", this.requestId);
      this.doCancelCheck(false);
      return this.getCurrentState();
   }

   public final synchronized AdminServerState receivedPrepareSucceeded(String serverName, boolean requiresRestart) {
      this.fireStateTransitionEvent(this, "receivedPrepareSucceeded", this.requestId);
      this.request.getStatus().receivedPrepareSucceededFrom(serverName, requiresRestart);
      this.doPrepareCompletionCheck();
      return this.getCurrentState();
   }

   public final synchronized AdminServerState receivedPrepareFailed(String serverName, Throwable e, boolean failedWhileSending) {
      this.fireStateTransitionEvent(this, "receivedPrepareFailed", this.requestId);
      this.request.getStatus().receivedPrepareFailedFrom(serverName, e, failedWhileSending);
      this.doPrepareCompletionCheck();
      return this.getCurrentState();
   }

   public synchronized AdminServerState receivedConfigPrepareSucceeded(String serverName, boolean requiresRestart) {
      this.fireStateTransitionEvent(this, "receivedConfigPrepareSucceeded", this.requestId);
      this.request.getStatus().receivedConfigPrepareSucceededFrom(serverName, requiresRestart);
      this.doConfigPrepareCompletionCheck();
      return this.getCurrentState();
   }

   public final synchronized AdminServerState receivedConfigPrepareFailed(String serverName, Throwable e) {
      if (!this.concurrentAppPrepareEnabled(this.getDeploymentRequest())) {
         return this.getCurrentState();
      } else {
         this.fireStateTransitionEvent(this, "receivedConfigPrepareFailed", this.requestId);
         this.request.getStatus().receivedConfigPrepareFailedFrom(serverName, e);
         this.doConfigPrepareCompletionCheck();
         return this.getCurrentState();
      }
   }

   private final boolean doCancelCheck(boolean isConfigPrepareFailed) {
      if (this.request.toBeCancelled()) {
         this.sendCancel(isConfigPrepareFailed);
         return true;
      } else {
         return false;
      }
   }

   protected final void doPrepareCompletionCheck() {
      if (this.request.getStatus().receivedAllPrepareResponses()) {
         if (this.doCancelCheck(false)) {
            return;
         }

         TimeAuditorManager.getInstance().endTransition(this.requestId, 1);
         if (this.isDebugEnabled()) {
            this.debug("Admin DeploymentService event : 'AwaitingPrepareResponses.doPrepareCompletionCheck()' for deployment id '" + this.requestId + "'");
         }

         this.doConfigAndAppPrepareCompletionCheck();
      }

   }

   private void doConfigAndAppPrepareCompletionCheck() {
      boolean configPreparesDone = !this.concurrentAppPrepareEnabled(this.request) || this.request.getStatus().receivedAllConfigPrepareResponses();
      boolean appPreparesDone = this.request.getStatus().receivedAllPrepareResponses();
      if (this.isDebugEnabled()) {
         this.debug("Admin DeploymentService event : 'AwaitingPrepareResponses.doConfigAndAppPrepareCompletionCheck()' for deployment id '" + this.requestId + "', configdone=" + configPreparesDone + ", appDone=" + appPreparesDone);
      }

      if (configPreparesDone && appPreparesDone) {
         this.sendCommit();
         this.moveToNextStatePrepareCompleted();
      }

   }

   private void moveToNextStatePrepareCompleted() {
      this.setExpectedNextState(2);
   }

   protected final void doConfigPrepareCompletionCheck() {
      if (this.concurrentAppPrepareEnabled(this.request)) {
         if (this.request.getStatus().receivedAllConfigPrepareResponses()) {
            if (this.doCancelCheck(true)) {
               return;
            }

            this.releaseExclusiveLock();
            this.doConfigAndAppPrepareCompletionCheck();
         }

      }
   }

   private void releaseExclusiveLock() {
      this.request.getStatus().signalDeploySucceeded();
      this.releasedExclusiveLock = true;
      this.request.getStatus().scheduleNextRequest();
   }

   private final void sendCommit() {
      if (this.releasedExclusiveLock) {
         this.sendCommitMessage();
      } else {
         this.request.getStatus().signalDeploySucceeded();
         this.sendCommitMessage();
         this.request.getStatus().scheduleNextRequest();
         this.releasedExclusiveLock = true;
      }

   }

   private void sendCommitMessage() {
      TimeAuditorManager.getInstance().startTransition(this.requestId, 2);
      this.sender.sendRequestCommitMsg(this.request);
   }

   private final void sendCancel(boolean isConfigPrepareFailed) {
      this.setExpectedNextState(4);
      final Object reason;
      if (this.request.getStatus().isCancelledByUser()) {
         reason = new Exception(DeploymentServiceLogger.logCancelledLoggable(this.requestId).getMessage());
      } else if (this.request.getStatus().isCancelledByClusterConstraints()) {
         reason = new Exception(DeploymentServiceLogger.cancelledDueToClusterConstraints(this.requestId, ""));
      } else if (this.request.getStatus().timedOut()) {
         reason = new Exception(DeploymentServiceLogger.logRequestTimedOutLoggable(this.requestId).getMessage());
      } else {
         reason = this.request.getStatus().getPrepareFailure();
      }

      TimeAuditorManager.getInstance().startTransition(this.requestId, 3);
      if (this.request.getStatus().hasTargetsToBeCancelled()) {
         if (this.concurrentAppPrepareEnabled(this.getDeploymentRequest()) && !isConfigPrepareFailed && this.releasedExclusiveLock) {
            this.request.setSendCancelAction(new Runnable() {
               public void run() {
                  AwaitingPrepareResponses.this.sender.sendRequestCancelMsg(AwaitingPrepareResponses.this.getDeploymentRequest(), (Throwable)reason);
               }
            });
            this.request.getStatus().signalAppPrepareFailed();
         } else {
            this.sender.sendRequestCancelMsg(this.getDeploymentRequest(), (Throwable)reason);
         }
      } else {
         this.getCurrentState().allCancelsDelivered();
      }

   }

   public final String toString() {
      return "AwaitingPrepareResponses";
   }
}
