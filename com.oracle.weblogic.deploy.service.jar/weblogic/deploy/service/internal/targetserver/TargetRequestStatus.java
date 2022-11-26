package weblogic.deploy.service.internal.targetserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.DeploymentReceiverV2;
import weblogic.deploy.service.internal.statemachines.StateMachinesManager;
import weblogic.deploy.service.internal.statemachines.targetserver.TargetServerState;

public final class TargetRequestStatus {
   private static final TargetDeploymentsManager deploymentsManager = TargetDeploymentsManager.getInstance();
   private static final TargetRequestManager requestManager = TargetRequestManager.getInstance();
   private TargetRequestImpl request;
   private long id = 0L;
   private TargetServerState currentState;
   private ArrayList targetServerStateList;
   private HashSet deploymentsToUpdateContext = new HashSet();
   private HashSet deploymentsToAckPrepare = new HashSet();
   private HashSet deploymentsToAckCommit = new HashSet();
   private HashSet deploymentsToAckCancel = new HashSet();
   private HashSet deploymentsToNotifySkip = new HashSet();
   private boolean isCanceled;
   private boolean isAborted;
   private boolean isTimedOut;
   private boolean isSyncToAdminFailed;
   private boolean cancelDispatched;
   private boolean serverStarting;
   private boolean restartPending;
   private Throwable savedException = null;
   Throwable commitFailureError = null;
   private Throwable cancelFailureError = null;

   private TargetRequestStatus(TargetRequestImpl request) {
      this.request = request;
      this.id = request.getId();
   }

   private final void debug(String message) {
      Debug.serviceDebug(message);
   }

   private final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   public static TargetRequestStatus createTargetRequestStatus(TargetRequestImpl request) {
      TargetRequestStatus result = new TargetRequestStatus(request);

      try {
         result.setTargetServerStates(StateMachinesManager.createTargetServerStates(result));
      } catch (ClassNotFoundException var3) {
      } catch (IllegalAccessException var4) {
      } catch (InstantiationException var5) {
      }

      result.setCurrentState(result.getTargetServerState(0));
      return result;
   }

   private void setTargetServerStates(ArrayList serverStateList) {
      this.targetServerStateList = serverStateList;
   }

   public final long getId() {
      return this.id;
   }

   public final void setServerStarting() {
      this.serverStarting = true;
   }

   public final boolean isServerStarting() {
      return this.serverStarting;
   }

   public final synchronized void setCurrentState(TargetServerState state) {
      this.currentState = state;
   }

   public final synchronized TargetServerState getCurrentState() {
      return this.currentState;
   }

   public final synchronized TargetRequestImpl getDeploymentRequest() {
      return this.request;
   }

   public final TargetServerState getTargetServerState(int stateId) {
      TargetServerState result = (TargetServerState)this.targetServerStateList.get(stateId);
      return result;
   }

   public final boolean isTimedOut() {
      return this.isTimedOut;
   }

   public final void setTimedOut() {
      this.isTimedOut = true;
   }

   public final synchronized void addToDeploymentsAckList(String deploymentType) {
      if (deploymentsManager.getDeploymentReceiver(deploymentType) == null) {
         if (this.isDebugEnabled()) {
            this.debug("TargetRequestStatus: '" + deploymentType + "' not added to ack list - no DeploymentReceiver registered");
         }

      } else {
         this.deploymentsToUpdateContext.add(deploymentType);
         this.deploymentsToAckPrepare.add(deploymentType);
         this.deploymentsToAckCancel.add(deploymentType);
         this.deploymentsToAckCommit.add(deploymentType);
         this.deploymentsToNotifySkip.add(deploymentType);
         if (this.isDebugEnabled()) {
            this.debug("'" + deploymentType + "' added to DeploymentReceiver ack for '" + this.id + "'");
         }

      }
   }

   public final synchronized void receivedContextUpdateCompletedFrom(String deploymentType) {
      this.receivedContextUpdateCompletedFrom(deploymentType, (Throwable)null);
   }

   public final synchronized void receivedContextUpdateCompletedFrom(String deploymentType, Throwable reason) {
      this.deploymentsToUpdateContext.remove(deploymentType);
      if (reason != null) {
         this.savedException = reason;
      }

      TimeAuditorManager.getInstance().endDeploymentTransition(this.request.getId(), deploymentType, 0);
   }

   public final synchronized boolean receivedAllContextUpdates() {
      boolean receivedAll = this.deploymentsToUpdateContext == null || this.deploymentsToUpdateContext.size() == 0;
      if (receivedAll) {
         TimeAuditorManager.getInstance().endTransition(this.request.getId(), 0);
      }

      return receivedAll;
   }

   public final synchronized void receivedPrepareAckFrom(String deploymentType) {
      this.deploymentsToAckPrepare.remove(deploymentType);
      Iterator iterator = this.deploymentsToAckPrepare.iterator();

      while(iterator.hasNext()) {
         DeploymentReceiver callbackHandler = deploymentsManager.getDeploymentReceiver((String)iterator.next());
         callbackHandler.prepareCompleted(this.request.getDeploymentContext(), deploymentType);
      }

      TimeAuditorManager.getInstance().endDeploymentTransition(this.request.getId(), deploymentType, 1);
   }

   public final synchronized void receivedPrepareNakFrom(String deploymentType, Throwable reason) {
      this.deploymentsToAckPrepare.remove(deploymentType);
      this.deploymentsToAckCommit.remove(deploymentType);
      if (this.savedException == null) {
         this.savedException = reason;
      }

      TimeAuditorManager.getInstance().endDeploymentTransition(this.request.getId(), deploymentType, 1);
   }

   public final synchronized Throwable getSavedError() {
      return this.savedException;
   }

   public final synchronized boolean receivedAllPrepareCompletions() {
      boolean receivedAll = this.deploymentsToAckPrepare == null || this.deploymentsToAckPrepare.size() == 0;
      if (receivedAll) {
         TimeAuditorManager.getInstance().endTransition(this.request.getId(), 1);
      }

      return receivedAll;
   }

   public final synchronized void receivedCommitAckFrom(String deploymentType) {
      if (!this.isCanceled()) {
         this.deploymentsToAckCommit.remove(deploymentType);
         Iterator iterator = this.deploymentsToAckCommit.iterator();

         while(iterator.hasNext()) {
            DeploymentReceiver callbackHandler = deploymentsManager.getDeploymentReceiver((String)iterator.next());
            callbackHandler.commitCompleted(this.request.getDeploymentContext(), deploymentType);
         }

         TimeAuditorManager.getInstance().endDeploymentTransition(this.request.getId(), deploymentType, 2);
      }
   }

   public final synchronized void receivedCommitFailureFrom(String deploymentType, Throwable reason) {
      this.deploymentsToAckCommit.remove(deploymentType);
      if (this.commitFailureError == null) {
         this.commitFailureError = reason;
      }

      TimeAuditorManager.getInstance().endDeploymentTransition(this.request.getId(), deploymentType, 2);
   }

   public final synchronized Throwable getCommitFailureError() {
      return this.commitFailureError;
   }

   public final synchronized boolean receivedAllCommitResponses() {
      boolean receivedAll = this.deploymentsToAckCommit == null || this.deploymentsToAckCommit.size() == 0;
      if (receivedAll) {
         TimeAuditorManager.getInstance().endTransition(this.request.getId(), 2);
      }

      return receivedAll;
   }

   public final synchronized boolean isCanceled() {
      return this.isCanceled;
   }

   public final synchronized void setCanceled() {
      if (!this.isCanceled && this.request != null) {
         this.isCanceled = true;
         if (this.isDebugEnabled()) {
            this.debug("request '" + this.request.getId() + "' set to 'canceled' on target");
         }

      }
   }

   public final synchronized boolean isAborted() {
      return this.isAborted;
   }

   public final synchronized void setAborted() {
      if (!this.isAborted && this.request != null) {
         if (this.isDebugEnabled()) {
            this.debug("request '" + this.request.getId() + "' set to 'aborted' on target");
         }

         this.isAborted = true;
      }
   }

   public final synchronized boolean isCanceledOrAborted() {
      return this.isCanceled || this.isAborted;
   }

   public final synchronized boolean isCancelDispatched() {
      return this.cancelDispatched;
   }

   public final synchronized void setCancelDispatched() {
      this.cancelDispatched = true;
   }

   public final synchronized boolean isSyncToAdminFailed() {
      return this.isSyncToAdminFailed;
   }

   public synchronized void setSyncToAdminFailed() {
      if (this.isDebugEnabled()) {
         this.debug("request '" + this.request.getId() + "'set to syncToAdminFailed on target");
      }

      this.isSyncToAdminFailed = true;
   }

   public final synchronized void cancelSuccessFrom(String deploymentType) {
      this.deploymentsToAckCancel.remove(deploymentType);
      TimeAuditorManager.getInstance().endDeploymentTransition(this.request.getId(), deploymentType, 3);
   }

   public final synchronized void cancelFailureFrom(String deploymentType, Throwable e) {
      this.deploymentsToAckCancel.remove(deploymentType);
      if (this.cancelFailureError == null) {
         this.cancelFailureError = e;
      }

      TimeAuditorManager.getInstance().endDeploymentTransition(this.request.getId(), deploymentType, 3);
   }

   public final Throwable getCancelFailureError() {
      return this.cancelFailureError;
   }

   public final synchronized Iterator getDeploymentsToBeCancelled() {
      if (this.deploymentsToAckCancel != null) {
         HashSet result = (HashSet)this.deploymentsToAckCancel.clone();
         return result.iterator();
      } else {
         return null;
      }
   }

   public final synchronized boolean receivedAllCancelResponses() {
      boolean receivedAll = this.deploymentsToAckCancel.isEmpty();
      if (receivedAll) {
         TimeAuditorManager.getInstance().endTransition(this.request.getId(), 2);
      }

      return receivedAll;
   }

   public final synchronized void scheduleNextRequest() {
      if (this.request != null && this.request.getSyncToAdminDeployments() == null) {
         requestManager.scheduleNextRequest();
      }

   }

   public final void setRestartPending() {
      this.restartPending = true;
   }

   public final boolean isRestartPending() {
      return this.restartPending;
   }

   public final synchronized void commitSkipped() {
      Iterator iterator = this.deploymentsToNotifySkip.iterator();

      while(iterator.hasNext()) {
         DeploymentReceiver callbackHandler = deploymentsManager.getDeploymentReceiver((String)iterator.next());
         if (callbackHandler instanceof DeploymentReceiverV2) {
            ((DeploymentReceiverV2)callbackHandler).commitSkipped(this.request.getDeploymentContext());
         }
      }

   }

   public final synchronized void reset() {
      if (this.request != null) {
         long requestId = this.request.getId();
         if (this.isDebugEnabled()) {
            this.debug("resetting id '" + requestId + "' heartbeat?:" + this.request.isHeartbeatRequest() + " on target");
         }

         TimeAuditorManager.getInstance().printAuditor(requestId, System.out);
         TimeAuditorManager.getInstance().endAuditor(requestId);
         if (this.request.getSyncToAdminDeployments() != null) {
            this.request.resetSyncToAdminDeployments();
            if (this.isSyncToAdminFailed()) {
               return;
            }

            if (!this.request.isHeartbeatRequest()) {
               if (this.isDebugEnabled()) {
                  this.debug("handling pending request '" + this.request.getId() + " on target");
               }

               TargetServerState targetState;
               if (this.request.getDeploymentContext().getContextComponent("calloutAbortedProposedConfig") != null) {
                  if (this.isDebugEnabled()) {
                     this.debug("reset(): Abort due to callouts, set to AWAITING_COMMIT_COMPLETION and calling commitSucceeded(). ");
                  }

                  targetState = this.getTargetServerState(4);
                  this.setCurrentState(targetState);
                  this.getCurrentState().commitSucceeded();
                  return;
               }

               targetState = this.getTargetServerState(0);
               this.setCurrentState(targetState);
               this.getCurrentState().receivedPrepare();
               return;
            }
         } else if (this.isDebugEnabled()) {
            this.debug("reset(): TargetRequestStatus request.getSyncToAdminDeployments() is null ");
         }

         this.request.cancelTimeoutMonitor();
         this.deploymentsToUpdateContext.clear();
         this.deploymentsToUpdateContext = null;
         this.deploymentsToAckPrepare.clear();
         this.deploymentsToAckPrepare = null;
         this.deploymentsToAckCommit.clear();
         this.deploymentsToAckCommit = null;
         this.deploymentsToAckCancel.clear();
         this.deploymentsToAckCancel = null;
         this.deploymentsToNotifySkip.clear();
         this.deploymentsToNotifySkip = null;
         this.savedException = null;
         this.cancelFailureError = null;
         this.targetServerStateList.clear();
         this.targetServerStateList = null;
         requestManager.removeRequest(this.id);
         this.request = null;
         requestManager.scheduleNextRequest();
      }
   }
}
