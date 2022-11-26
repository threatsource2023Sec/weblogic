package weblogic.deploy.service.internal.statemachines.adminserver;

import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.adminserver.AdminRequestImpl;
import weblogic.deploy.service.internal.adminserver.TimeAuditorManager;
import weblogic.deploy.service.internal.statemachines.State;

public abstract class AdminServerState extends State {
   protected long requestId;
   protected AdminRequestImpl request = null;

   public AdminServerState start() throws InvalidStateException {
      throw new InvalidStateException(DeploymentServiceLogger.logInvalidStateLoggable().getMessage());
   }

   public AdminServerState cancel() throws InvalidStateException {
      this.fireStateTransitionEvent(this, "requestCancelled", this.requestId);
      return this.getCurrentState();
   }

   public AdminServerState requestTimedOut() {
      this.fireStateTransitionEvent(this, "requestTimedout", this.requestId);
      return this.getCurrentState();
   }

   public AdminServerState receivedPrepareSucceeded(String serverName, boolean requiresRestart) {
      this.fireStateTransitionEvent(this, "receivedPrepareSuceeded", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - receivedPrepareSucceeded is an invalid +transition event");
      }

      return this.getCurrentState();
   }

   public AdminServerState receivedPrepareFailed(String serverName, Throwable e, boolean failedWhileSending) {
      this.fireStateTransitionEvent(this, "receivedPrepareFailed", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - receivedPrepareFailed is an invalid transition event");
      }

      return this.getCurrentState();
   }

   public AdminServerState receivedConfigPrepareSucceeded(String serverName, boolean requiresRestart) {
      this.fireStateTransitionEvent(this, "receivedConfigPrepareSuceeded", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - receivedConfigPrepareSucceeded is an invalid +transition event");
      }

      return this.getCurrentState();
   }

   public AdminServerState receivedConfigPrepareFailed(String serverName, Throwable e) {
      this.fireStateTransitionEvent(this, "receivedConfigPrepareFailed", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - receivedConfigPrepareFailed is an invalid +transition event");
      }

      return this.getCurrentState();
   }

   public final AdminServerState receivedCommitSucceeded(String serverName) {
      this.fireStateTransitionEvent(this, "receivedCommitSucceeded", this.requestId);
      this.request.getStatus().receivedCommitSucceededFrom(serverName);
      return this.doCommitCompletionCheck() ? null : this.getCurrentState();
   }

   public final AdminServerState receivedCommitFailed(String serverName, Throwable e) {
      this.fireStateTransitionEvent(this, "receivedCommitFailed", this.requestId);
      this.request.getStatus().receivedCommitFailedFrom(serverName, e);
      return this.doCommitCompletionCheck() ? null : this.getCurrentState();
   }

   public AdminServerState receivedCancelSucceeded(String serverName) {
      this.fireStateTransitionEvent(this, "receivedCancelSucceeded", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - receivedCancelSucceeded is an invalid +transition event");
      }

      return this.getCurrentState();
   }

   public AdminServerState receivedCancelFailed(String serverName, Throwable e) {
      this.fireStateTransitionEvent(this, "receivedCancelFailed", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - receivedCancelfailed is an invalid +transition event");
      }

      return this.getCurrentState();
   }

   public AdminServerState allPreparesDelivered() {
      this.fireStateTransitionEvent(this, "preparesDelivered", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - allPreparesDelivered is an invalid transition event");
      }

      return this.getCurrentState();
   }

   public AdminServerState allCommitsDelivered() {
      this.fireStateTransitionEvent(this, "commitsDelivered", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - allCommitsDelivered is an invalid transition event");
      }

      return this.getCurrentState();
   }

   public AdminServerState allCancelsDelivered() {
      this.fireStateTransitionEvent(this, "cancelsDelivered", this.requestId);
      if (this.isDebugEnabled()) {
         this.debug("deployment '" + this.requestId + "' is in ' " + this.getCurrentState() + "' - allCancelsDelivered is an invalid transition event");
      }

      return this.getCurrentState();
   }

   public final void initialize(AdminRequestImpl req) {
      this.request = req;
      this.requestId = req.getId();
   }

   protected void doPrepareCompletionCheck() {
   }

   protected void doConfigPrepareCompletionCheck() {
   }

   protected final boolean doCommitCompletionCheck() {
      if (this.request.getStatus().receivedAllCommitResponses()) {
         TimeAuditorManager.getInstance().endTransition(this.requestId, 2);
         if (this.request.getStatus().commitFailed()) {
            this.request.getStatus().signalCommitFailed();
         } else {
            this.request.getStatus().signalCommitSucceeded();
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean doCancelCompletionCheck() {
      return false;
   }

   protected final AdminServerState getCurrentState() {
      return this.request == null ? null : this.request.getCurrentState();
   }

   protected final AdminRequestImpl getDeploymentRequest() {
      return this.request;
   }

   protected final void setExpectedNextState(int stateId) {
      AdminServerState state = this.request.getStatus().getAdminServerState(stateId);
      if (this.isDebugEnabled()) {
         this.debug("Setting admin state of deployment '" + this.requestId + "' to : " + this.getAdminStateString(stateId));
      }

      this.request.getStatus().setCurrentState(state);
   }

   public void reset() {
      this.request = null;
   }

   protected final void fireStateTransitionEvent(State state, String method, long id) {
      if (this.isDebugEnabled()) {
         this.debug("Admin DeploymentService event : '" + state.toString() + "." + method + "()' for deployment id '" + id + "'");
      }

   }

   protected final String getAdminStateString(int stateId) {
      String adminStateString = null;
      switch (stateId) {
         case 0:
            adminStateString = "SendingPrepare";
            break;
         case 1:
            adminStateString = "AwaitingPrepareResponses";
            break;
         case 2:
            adminStateString = "SendingCommit";
            break;
         case 3:
            adminStateString = "AwaitingCommitResponses";
            break;
         case 4:
            adminStateString = "SendingCancel";
            break;
         case 5:
            adminStateString = "AwaitingCancelResponses";
      }

      return adminStateString;
   }
}
