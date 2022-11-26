package weblogic.deploy.service.internal.adminserver;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.FailureDescription;
import weblogic.deploy.service.RequiresRestartFailureDescription;
import weblogic.deploy.service.internal.DeploymentRequestTaskRuntimeMBeanImpl;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.statemachines.StateMachinesManager;
import weblogic.deploy.service.internal.statemachines.adminserver.AdminServerState;
import weblogic.logging.Loggable;
import weblogic.logging.NonCatalogLogger;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.ServerDisconnectEvent;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

public final class AdminRequestStatus {
   private static final NonCatalogLogger timeoutLogger = new NonCatalogLogger("DeploymentRequestTimeoutLogger");
   private static final AdminDeploymentsManager adminDeploymentsManager = AdminDeploymentsManager.getInstance();
   private static final AdminRequestManager adminRequestManager = AdminRequestManager.getInstance();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String localServer;
   private AdminRequestImpl request;
   private final long requestId;
   private AdminServerState currentState;
   private ArrayList adminServerStateList;
   private boolean timedOut;
   private static boolean clusterConstraintsInitialized;
   private static boolean clusterConstraintsEnabled;
   private HashMap prepareDisconnectListeners = new HashMap();
   private HashMap commitDisconnectListeners = new HashMap();
   private HashMap cancelDisconnectListeners = new HashMap();
   private HashSet targetedServers = new HashSet();
   private HashSet targetsToBeCommited = new HashSet();
   private Set targetsToRespondToPrepare = Collections.synchronizedSet(new HashSet());
   private Set targetsToRespondToConfigPrepare = Collections.synchronizedSet(new HashSet());
   private Map prepareDeliveryFailureTargets = new HashMap();
   private int prepareTargetsCount;
   private Throwable prepareFailure = null;
   private String prepareFailureSource = null;
   private Map prepareFailuresMap = Collections.synchronizedMap(new HashMap());
   private Set targetsToBeRestarted = new HashSet();
   private Set clusterConstraintTargets = new HashSet();
   private Set targetsToRespondToCommit = Collections.synchronizedSet(new HashSet());
   private Map commitDeliveryFailureTargets = new HashMap();
   private Map commitFailureSet = new HashMap();
   private int commitTargetsCount;
   private int commitDeliveryCount = 0;
   private HashSet targetsToBeCanceled = new HashSet();
   private Set targetsToRespondToCancel = Collections.synchronizedSet(new HashSet());
   private Map cancelDeliveryFailureTargets = new HashMap();
   private Set cancelFailureSet = new HashSet();
   private int cancelTargetsCount;
   private int cancelDeliveryCount = 0;
   private boolean isCancelledByUser;
   private boolean isCancelledByClusterConstraints;

   private AdminRequestStatus(AdminRequestImpl incomingRequest) {
      this.request = incomingRequest;
      this.requestId = incomingRequest.getId();
   }

   public static AdminRequestStatus createAdminRequestStatus(AdminRequestImpl incomingRequest) {
      AdminRequestStatus result = new AdminRequestStatus(incomingRequest);

      try {
         StateMachinesManager.getStateMachinesManager();
         result.setAdminServerStates(StateMachinesManager.createAdminServerStates(incomingRequest));
      } catch (ClassNotFoundException var3) {
      } catch (IllegalAccessException var4) {
      } catch (InstantiationException var5) {
      }

      result.setCurrentState(result.getAdminServerState(0));
      return result;
   }

   public final AdminRequestImpl getRequest() {
      return this.request;
   }

   public final long getId() {
      return this.requestId;
   }

   protected Map getPrepareDeliveryFailureTargets() {
      return this.prepareDeliveryFailureTargets;
   }

   protected Set getTargetsToRespondToPrepare() {
      return this.targetsToRespondToPrepare;
   }

   protected Set getTargetsToRespondToConfigPrepare() {
      return this.targetsToRespondToConfigPrepare;
   }

   protected Set getTargetsToBeRestarted() {
      return this.targetsToBeRestarted;
   }

   protected Set getTargetsToBeCommited() {
      return this.targetsToBeCommited;
   }

   protected Map getCommitDeliveryFailureTargets() {
      return this.commitDeliveryFailureTargets;
   }

   protected Set getTargetsToRespondToCommit() {
      return this.targetsToRespondToCommit;
   }

   protected Map getCommitFailureSet() {
      return this.commitFailureSet;
   }

   protected Set getTargetsToBeCanceled() {
      return this.targetsToBeCanceled;
   }

   protected Map getCancelDeliveryFailureTargets() {
      return this.cancelDeliveryFailureTargets;
   }

   protected Set getTargetsToRespondToCancel() {
      return this.targetsToRespondToCancel;
   }

   protected Set getCancelFailureSet() {
      return this.cancelFailureSet;
   }

   protected String getPrepareFailureSource() {
      return this.prepareFailureSource;
   }

   private static final void debug(String message) {
      Debug.serviceDebug(message);
   }

   private static final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   private void setAdminServerStates(ArrayList serverStateList) {
      this.adminServerStateList = serverStateList;
   }

   public final void setCurrentState(AdminServerState state) {
      if (isDebugEnabled()) {
         debug("admin state set to '" + state.toString() + "' for id '" + this.requestId);
      }

      this.currentState = state;
   }

   public final AdminServerState getCurrentState() {
      return this.currentState;
   }

   public final AdminServerState getAdminServerState(int stateId) {
      return (AdminServerState)this.adminServerStateList.get(stateId);
   }

   public final void addTargetedServer(String server, boolean clusterConstrained) {
      if (clusterConstrained && isClusterConstraintsEnabled()) {
         this.clusterConstraintTargets.add(server);
      }

      if (!this.targetedServers.contains(server)) {
         this.targetedServers.add(server);
         ++this.prepareTargetsCount;
         this.targetsToRespondToPrepare.add(server);
         this.targetsToRespondToConfigPrepare.add(server);
         this.targetsToBeCanceled.add(server);
      }
   }

   public final Iterator getTargetedServers() {
      return this.targetedServers.iterator();
   }

   public final void prepareDeliveredTo(String server) {
      if (isDebugEnabled()) {
         debug("prepare delivered to '" + server + "' for request '" + this.requestId + "'");
      }

      this.targetsToBeCommited.add(server);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      if (this.allPreparesDelivered()) {
         this.getCurrentState().allPreparesDelivered();
      }

      TimeAuditorManager.getInstance().startTargetTransition(this.requestId, server, 1);
   }

   public final void prepareDeliveryFailureWhenContacting(String server, Exception e) {
      if (isDebugEnabled()) {
         debug("prepare delivery to '" + server + "' failed for request '" + this.requestId + "'");
      }

      String operation = DeploymentServiceLogger.operationDelivery(DeploymentServiceLogger.prepareOperation());
      FailureDescription failureDescription = new FailureDescription(server, e, operation);
      this.targetsToRespondToPrepare.remove(server);
      this.targetsToRespondToConfigPrepare.remove(server);
      this.targetsToBeCanceled.remove(server);
      this.prepareDeliveryFailureTargets.put(server, failureDescription);
      this.removePrepareDisconnectListener(server);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      if (isClusterConstraintsEnabled() && this.clusterConstraintTargets.contains(server)) {
         if (isDebugEnabled()) {
            debug("ClusterConstraints is enabled and not all targets are reachable, so cancelling request '" + this.requestId + "'");
         }

         try {
            this.request.cancelDueToClusterConstraints();
            if (this.allPreparesDelivered()) {
               this.getCurrentState().allPreparesDelivered();
            }

            return;
         } catch (InvalidStateException var6) {
            if (isDebugEnabled()) {
               debug("attempt to 'cancel' id '" + this.requestId + "' failed due to '" + var6.getMessage() + "'");
            }

            this.signalCancelFailed(false);
         }
      }

      if (this.allTargetsUnreachableForPrepareDelivery()) {
         this.signalDeployDeferredDueToUnreachableTargets();
      } else {
         if (this.allPreparesDelivered()) {
            this.getCurrentState().allPreparesDelivered();
         }

      }
   }

   private final int currentTargetCount() {
      return this.targetsToBeCommited.size() + this.prepareDeliveryFailureTargets.size();
   }

   private final boolean allPreparesDelivered() {
      return this.prepareTargetsCount > 0 && this.currentTargetCount() == this.prepareTargetsCount;
   }

   public final void receivedPrepareSucceededFrom(String server, boolean requiresRestart) {
      if (isDebugEnabled()) {
         debug("prepare succeeded on '" + server + "' for request '" + this.requestId + "'");
      }

      this.targetsToRespondToPrepare.remove(server);
      if (requiresRestart) {
         this.targetsToBeRestarted.add(server);
      }

      this.removePrepareDisconnectListener(server);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      adminRequestManager.deliverRequestStatusUpdateCallback(this.request, "PrepareSuccessReceived", server);
      TimeAuditorManager.getInstance().endTargetTransition(this.requestId, server, 1);
   }

   public final void receivedPrepareFailedFrom(String server, Throwable reason, boolean failedWhileSending) {
      if (isDebugEnabled()) {
         debug("prepare failed on '" + server + "' for request '" + this.requestId + "' due to '" + reason.toString() + "'");
      }

      this.targetsToRespondToPrepare.remove(server);
      this.doPrepareFailureActions(server, reason, failedWhileSending);
   }

   private final void doPrepareFailureActions(String server, Throwable reason, boolean failedWhileSending) {
      if (this.prepareFailure == null) {
         this.prepareFailure = reason;
         this.prepareFailureSource = server;
      } else {
         this.prepareFailuresMap.put(server, reason);
      }

      DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)this.request.getTaskRuntime();
      Exception theReason = reason instanceof Exception ? (Exception)reason : new Exception(reason);
      taskRuntime.addFailedTarget(server, theReason);
      if (failedWhileSending) {
         this.targetsToBeCanceled.remove(server);
         if (isDebugEnabled()) {
            debug("Removed server '" + server + "' from toBeCancelled list since prepare failed on this server while sending...");
         }
      }

      this.removePrepareDisconnectListener(server);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      adminRequestManager.deliverRequestStatusUpdateCallback(this.request, "PrepareFailedReceived", server);
      TimeAuditorManager.getInstance().endTargetTransition(this.requestId, server, 1);
   }

   public final void receivedConfigPrepareSucceededFrom(String server, boolean requiresRestart) {
      if (isDebugEnabled()) {
         debug("config prepare succeeded on '" + server + "' for request '" + this.requestId + "'");
      }

      this.targetsToRespondToConfigPrepare.remove(server);
      if (requiresRestart) {
         this.targetsToBeRestarted.add(server);
      }

      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      adminRequestManager.deliverRequestStatusUpdateCallback(this.request, "PrepareSuccessReceived", server);
   }

   public final void receivedConfigPrepareFailedFrom(String server, Throwable reason) {
      if (isDebugEnabled()) {
         debug("config prepare failed on '" + server + "' for request '" + this.requestId + "' due to '" + reason.toString() + "'");
      }

      this.targetsToRespondToConfigPrepare.remove(server);
      this.targetsToRespondToPrepare.remove(server);
      this.doPrepareFailureActions(server, reason, false);
   }

   public final Throwable getPrepareFailure() {
      return this.prepareFailure;
   }

   public final boolean failed() {
      return this.prepareFailure != null;
   }

   public final boolean receivedAllPrepareResponses() {
      return this.targetsToRespondToPrepare.isEmpty() || this.targetsToRespondToPrepare.size() == 0;
   }

   public final boolean receivedAllConfigPrepareResponses() {
      return this.targetsToRespondToConfigPrepare.isEmpty() || this.targetsToRespondToConfigPrepare.size() == 0;
   }

   private boolean allTargetsUnreachableForPrepareDelivery() {
      return this.prepareDeliveryFailureTargets.size() == this.prepareTargetsCount;
   }

   public final Iterator getTargetsToBeCommitted() {
      this.commitTargetsCount = this.targetsToBeCommited.size();
      Iterator iterator = this.targetsToBeCommited.iterator();

      while(iterator.hasNext()) {
         this.targetsToRespondToCommit.add(iterator.next());
      }

      return ((HashSet)this.targetsToBeCommited.clone()).iterator();
   }

   public final void commitDeliveredTo(String server) {
      if (isDebugEnabled()) {
         debug("'commit' delivered to target '" + server + "' for id '" + this.requestId + "'");
      }

      ++this.commitDeliveryCount;
      if (this.allCommitsDelivered()) {
         this.getCurrentState().allCommitsDelivered();
      }

      TimeAuditorManager.getInstance().startTargetTransition(this.requestId, server, 2);
   }

   public final void receivedCommitSucceededFrom(String server) {
      if (isDebugEnabled()) {
         debug("commit succeeded on '" + server + "' for request '" + this.requestId + "'");
      }

      this.targetsToRespondToCommit.remove(server);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      this.removeCommitDisconnectListener(server);
      adminRequestManager.deliverRequestStatusUpdateCallback(this.request, "CommitSuccessReceived", server);
      TimeAuditorManager.getInstance().endTargetTransition(this.requestId, server, 2);
   }

   public final void receivedCommitFailedFrom(String server, Throwable e) {
      if (isDebugEnabled()) {
         debug("commit failed on '" + server + "' for request '" + this.requestId + "' due to '" + e.toString() + "'");
      }

      String operation = DeploymentServiceLogger.commitOperation();
      Exception theReason = e instanceof Exception ? (Exception)e : new Exception(e);
      FailureDescription failureDescription = new FailureDescription(server, theReason, operation);
      this.targetsToRespondToCommit.remove(server);
      this.commitFailureSet.put(server, failureDescription);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      this.removeCommitDisconnectListener(server);
      adminRequestManager.deliverRequestStatusUpdateCallback(this.request, "CommitFailedReceived", server);
      TimeAuditorManager.getInstance().endTargetTransition(this.requestId, server, 2);
   }

   public final boolean commitFailed() {
      return !this.commitFailureSet.isEmpty();
   }

   public final void commitDeliveryFailureWhenContacting(String server, Exception e) {
      if (isDebugEnabled()) {
         debug("commit delivery to '" + server + "' failed for request '" + this.requestId + "'");
      }

      String operation = DeploymentServiceLogger.operationDelivery(DeploymentServiceLogger.commitOperation());
      FailureDescription failureDescription = new FailureDescription(server, e, operation, true);
      this.commitDeliveryFailureTargets.put(server, failureDescription);
      this.targetsToRespondToCommit.remove(server);
      this.removeCommitDisconnectListener(server);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      if (isClusterConstraintsEnabled() && this.clusterConstraintTargets.contains(server)) {
         if (isDebugEnabled()) {
            debug("ClusterConstraints is enabled and not all targets are reachable, so cancelling request '" + this.requestId + "'");
         }

         try {
            this.request.cancelDueToClusterConstraints();
            if (this.allCommitsDelivered()) {
               this.getCurrentState().allCommitsDelivered();
            }

            return;
         } catch (InvalidStateException var6) {
            if (isDebugEnabled()) {
               debug("attempt to 'cancel' id '" + this.requestId + "' failed due to '" + var6.getMessage() + "'");
            }

            this.signalCancelFailed(false);
         }
      }

      if (this.allTargetsUnreachableForCommitDelivery()) {
         this.signalCommitFailed();
      } else {
         if (this.allCommitsDelivered()) {
            this.getCurrentState().allCommitsDelivered();
         }

      }
   }

   private boolean allTargetsUnreachableForCommitDelivery() {
      return this.commitDeliveryFailureTargets.size() == this.commitTargetsCount;
   }

   public final boolean receivedAllCommitResponses() {
      boolean receivedAll = this.targetsToRespondToCommit.isEmpty() || this.targetsToRespondToCommit.size() == 0;
      if (isDebugEnabled()) {
         debug(" **** Request '" + this.getRequest().getId() + "' receivedAllCommitResponses() : " + receivedAll);
      }

      return receivedAll;
   }

   private final boolean allCommitsDelivered() {
      return this.commitTargetsCount > 0 && this.commitDeliveryFailureTargets != null && this.commitDeliveryCount + this.commitDeliveryFailureTargets.size() == this.commitTargetsCount;
   }

   public final boolean isCancelledByUser() {
      return this.isCancelledByUser;
   }

   final void setCancelledByUser() {
      this.isCancelledByUser = true;
   }

   public final boolean isCancelledByClusterConstraints() {
      return this.isCancelledByClusterConstraints;
   }

   final void setCancelledByClusterConstraints() {
      this.isCancelledByClusterConstraints = true;
   }

   public final Iterator getTargetsToBeCancelled() {
      this.cancelTargetsCount = this.targetsToBeCanceled.size();
      Iterator iterator = this.targetsToBeCanceled.iterator();

      while(iterator.hasNext()) {
         this.targetsToRespondToCancel.add(iterator.next());
      }

      return ((HashSet)this.targetsToBeCanceled.clone()).iterator();
   }

   public final boolean hasTargetsToBeCancelled() {
      return !this.targetsToBeCanceled.isEmpty();
   }

   public final void cancelDeliveredTo(String server) {
      ++this.cancelDeliveryCount;
      if (isDebugEnabled()) {
         debug("'cancel' delivered to target '" + server + "' for id '" + this.requestId + "'");
      }

      if (this.allCancelsDelivered()) {
         this.getCurrentState().allCancelsDelivered();
      }

      TimeAuditorManager.getInstance().startTargetTransition(this.requestId, server, 3);
   }

   public final void receivedCancelSucceededFrom(String server) {
      if (isDebugEnabled()) {
         debug("cancel succeeded on '" + server + "' for request '" + this.requestId + "'");
      }

      this.targetsToRespondToCancel.remove(server);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      this.removeCancelDisconnectListener(server);
      adminRequestManager.deliverRequestStatusUpdateCallback(this.request, "CancelSuccessReceived", server);
      TimeAuditorManager.getInstance().endTargetTransition(this.requestId, server, 3);
   }

   public final void receivedCancelFailedFrom(String server, Throwable e) {
      if (isDebugEnabled()) {
         debug("cancel failed on '" + server + "' for request '" + this.requestId + "' due to '" + e.toString() + "'");
      }

      String operation = DeploymentServiceLogger.cancelOperation();
      Exception theReason = e instanceof Exception ? (Exception)e : new Exception(e);
      FailureDescription failureDescription = new FailureDescription(server, theReason, operation);
      this.targetsToRespondToCancel.remove(server);
      this.cancelFailureSet.add(failureDescription);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      this.removeCancelDisconnectListener(server);
      adminRequestManager.deliverRequestStatusUpdateCallback(this.request, "CancelFailedReceived", server);
      TimeAuditorManager.getInstance().endTargetTransition(this.requestId, server, 3);
   }

   public final boolean cancelFailed() {
      return !this.cancelFailureSet.isEmpty();
   }

   public final void cancelDeliveryFailureWhenContacting(String server, Exception e) {
      if (isDebugEnabled()) {
         debug("cancel delivery to '" + server + "' failed for request '" + this.requestId + "'");
      }

      String operation = DeploymentServiceLogger.operationDelivery(DeploymentServiceLogger.cancelOperation());
      FailureDescription failureDescription = new FailureDescription(server, e, operation);
      this.cancelDeliveryFailureTargets.put(server, failureDescription);
      this.targetsToRespondToCancel.remove(server);
      this.removeCancelDisconnectListener(server);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      if (this.allTargetsUnreachableForCancelDelivery()) {
         this.signalCancelFailed(true);
      } else {
         if (this.allCancelsDelivered()) {
            this.getCurrentState().allCancelsDelivered();
         }

      }
   }

   private boolean allTargetsUnreachableForCancelDelivery() {
      return this.cancelDeliveryFailureTargets.size() == this.cancelTargetsCount;
   }

   public final boolean receivedAllCancelResponses() {
      return this.targetsToRespondToCancel.isEmpty() || this.targetsToRespondToCancel.size() == 0;
   }

   private final boolean allCancelsDelivered() {
      return this.cancelTargetsCount > 0 && this.cancelDeliveryCount + this.cancelDeliveryFailureTargets.size() == this.cancelTargetsCount;
   }

   public final boolean timedOut() {
      return this.timedOut;
   }

   public final void setTimedOut() {
      this.timedOut = true;
      DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)this.request.getTaskRuntime();
      String msg = DeploymentServiceLogger.timedOut(this.request.getId());
      Exception timedOutException = new Exception(msg);
      taskRuntime.addFailedTarget(localServer, timedOutException);
   }

   public final void signalDeploySucceeded() {
      this.updateTargetsToRestartInTaskRuntime();
      List restartFailureList = new ArrayList();
      if (this.targetsToBeRestarted != null && !this.targetsToBeRestarted.isEmpty()) {
         Iterator restartList = this.targetsToBeRestarted.iterator();

         while(restartList.hasNext()) {
            restartFailureList.add(new RequiresRestartFailureDescription((String)restartList.next()));
         }
      }

      adminRequestManager.deliverDeploySucceededCallback(this.request, this.prepareDeliveryFailureTargets, restartFailureList);
      boolean needsVersionUpdate = !this.request.isControlRequest();
      if (isDebugEnabled()) {
         debug("AdminRequestStatus.signalDeploySucceeded(): Needs Version Update for request '" + this.request.getId() + "' is : " + needsVersionUpdate);
      }

      if (needsVersionUpdate) {
         adminDeploymentsManager.setCurrentDomainVersion(this.request.getProposedDomainVersion());
      }

      DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)this.request.getTaskRuntime();
      taskRuntime.setState(2);
   }

   private void updateTargetsToRestartInTaskRuntime() {
      if (!this.targetsToBeRestarted.isEmpty()) {
         StringBuffer sb = new StringBuffer();
         sb.append("*** The following servers need to be restarted for deployment request '" + this.requestId + "' to complete: ");
         DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)this.request.getTaskRuntime();
         Iterator iterator = this.targetsToBeRestarted.iterator();

         while(iterator.hasNext()) {
            String server = (String)iterator.next();
            taskRuntime.addServerToRestartSet(server);
            sb.append(server);
            sb.append(" ");
         }

         if (isDebugEnabled()) {
            debug(sb.toString());
         }
      }

   }

   private final void signalDeployDeferredDueToUnreachableTargets() {
      if (isDebugEnabled()) {
         debug("'deploy deferred' for id '" + this.requestId + "' since no targets are reachable");
      }

      this.signalDeploySucceeded();
      this.scheduleNextRequest();
      this.request.reset();
   }

   public final void signalCommitSucceeded() {
      long deploymentId = this.request.getId();
      DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)this.request.getTaskRuntime();
      boolean succeeded = true;
      Iterator iterator = this.commitDeliveryFailureTargets.values().iterator();

      while(iterator.hasNext()) {
         Object obj = iterator.next();
         FailureDescription desc = (FailureDescription)obj;
         if (!desc.isConnectFailure()) {
            succeeded = false;
         }
      }

      if (succeeded) {
         if (isDebugEnabled()) {
            debug("'commit succeeded' for id '" + deploymentId + "'");
         }

         adminRequestManager.deliverCommitSucceededCallback(this.request);
         taskRuntime.setState(8);
      } else {
         iterator = this.commitDeliveryFailureTargets.keySet().iterator();
         StringBuffer sb = new StringBuffer();

         while(iterator.hasNext()) {
            sb.append((String)iterator.next());
            sb.append(" ");
         }

         if (isDebugEnabled()) {
            debug("'commit' succeeded but could not be delivered to the following targets '" + sb.toString() + "' for id '" + deploymentId + "'");
         }

         String msg = DeploymentServiceLogger.commitDeliveryFailure(sb.toString());
         taskRuntime.addFailedTarget(localServer, new Exception(msg));
         adminRequestManager.deliverCommitFailureCallback(this.request, this.commitDeliveryFailureTargets);
         taskRuntime.setState(8);
      }

      this.request.reset();
   }

   public final void signalCommitFailed() {
      long deploymentId = this.request.getId();
      if (isDebugEnabled()) {
         debug("'commit failed' for id '" + deploymentId + "'");
      }

      DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)this.request.getTaskRuntime();
      StringBuffer sb = new StringBuffer();
      String server;
      Exception commitFailureReason;
      if (this.timedOut()) {
         String message = DeploymentServiceLogger.commitTimedOut(deploymentId);
         Set targetsToRespond = this.targetsToRespondToCommit;
         server = message + " : Targets to respond : " + targetsToRespond;
         timeoutLogger.notice(server);
         String threadDumps = getThreadDumpsOnServers(targetsToRespond);
         timeoutLogger.notice(threadDumps);
         if (isDebugEnabled()) {
            debug(message);
         }

         commitFailureReason = new Exception(message);
         String operation = DeploymentServiceLogger.operationTimeout(DeploymentServiceLogger.commitOperation());
         FailureDescription failureDescription = new FailureDescription(localServer, commitFailureReason, operation);
         this.commitDeliveryFailureTargets.put(localServer, failureDescription);
         sb.append(message);
      }

      if (this.commitDeliveryFailureTargets.size() > 0) {
         Iterator iterator = this.commitDeliveryFailureTargets.keySet().iterator();
         StringBuffer sb1 = new StringBuffer();

         while(iterator.hasNext()) {
            sb1.append((String)iterator.next());
            sb1.append(" ");
         }

         server = DeploymentServiceLogger.commitDeliveryFailure(sb1.toString());
         if (isDebugEnabled()) {
            debug(server);
         }

         sb.append(sb1.toString());
         Exception deliveryFailureException = new Exception(server);
         if (!this.timedOut()) {
            taskRuntime.addFailedTarget(localServer, deliveryFailureException);
         }
      }

      if (this.commitFailureSet.size() > 0) {
         StringBuffer sb2 = new StringBuffer();
         Iterator iterator = this.commitFailureSet.keySet().iterator();

         while(iterator.hasNext()) {
            server = (String)iterator.next();
            sb2.append(server);
            sb2.append(" ");
            FailureDescription failureDescription = (FailureDescription)this.commitFailureSet.get(server);
            commitFailureReason = failureDescription.getReason();
            this.commitDeliveryFailureTargets.put(server, failureDescription);
            if (!this.timedOut()) {
               taskRuntime.addFailedTarget(server, commitFailureReason);
            }
         }

         if (isDebugEnabled()) {
            debug("'commit' failed on these targets '" + sb2.toString() + "'");
         }

         sb.append(sb2.toString());
      }

      adminRequestManager.deliverCommitFailureCallback(this.request, this.commitDeliveryFailureTargets);
      taskRuntime.setState(8);
      this.request.reset();
   }

   public final void signalCancelSucceeded() {
      if (isDebugEnabled()) {
         debug("'cancel success' for id '" + this.requestId + "'");
      }

      try {
         DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)this.request.getTaskRuntime();
         if (this.isCancelledByUser()) {
            adminRequestManager.deliverDeployCancelSucceededCallback(this.request, this.cancelDeliveryFailureTargets);
            taskRuntime.setState(6);
         } else {
            AdminDeploymentException ade;
            if (this.isCancelledByClusterConstraints()) {
               taskRuntime.setState(6);
               ade = new AdminDeploymentException();
               boolean prepareFailed = true;
               Map failureTargets = this.getPrepareDeliveryFailureTargets();
               if (failureTargets.isEmpty()) {
                  prepareFailed = false;
                  failureTargets = this.getCommitDeliveryFailureTargets();
               }

               StringBuffer sb = new StringBuffer();
               if (!failureTargets.isEmpty()) {
                  Iterator iterator = failureTargets.entrySet().iterator();

                  while(iterator.hasNext()) {
                     Map.Entry entry = (Map.Entry)iterator.next();
                     String server = (String)entry.getKey();
                     if (this.clusterConstraintTargets.contains(server)) {
                        sb.append(server);
                        if (iterator.hasNext()) {
                           sb.append(", ");
                        }

                        ade.addFailureDescription((FailureDescription)entry.getValue());
                     }
                  }
               }

               Exception reason = new Exception(DeploymentServiceLogger.cancelledDueToClusterConstraints(this.requestId, sb.toString()));
               FailureDescription fd = new FailureDescription(localServer, reason, prepareFailed ? "prepare" : "commit");
               ade.addFailureDescription(fd);
               adminRequestManager.deliverDeployFailedCallback(this.request, ade);
               taskRuntime.setState(3);
            } else {
               ade = new AdminDeploymentException();
               this.includePrepareDeliveryFailure(ade);
               this.includeCancelDeliveryFailures(ade);
               this.includeDeploymentFailure(ade);
               adminRequestManager.deliverDeployFailedCallback(this.request, ade);
               taskRuntime.setState(3);
            }
         }
      } finally {
         this.scheduleNextRequest();
         this.request.reset();
      }

   }

   private void includePrepareDeliveryFailure(AdminDeploymentException ade) {
      if (this.prepareDeliveryFailureTargets.size() > 0) {
         Iterator iterator = this.prepareDeliveryFailureTargets.values().iterator();

         while(iterator.hasNext()) {
            ade.addFailureDescription((FailureDescription)iterator.next());
         }
      }

   }

   private void includeDeploymentFailure(AdminDeploymentException ade) {
      String failureSource = localServer;
      Object reason;
      if (this.timedOut()) {
         reason = new Exception(DeploymentServiceLogger.timedOutAdmin(this.request.getId()));
      } else {
         reason = this.prepareFailure;
         failureSource = this.prepareFailureSource;
      }

      String operation = DeploymentServiceLogger.prepareOperation();
      Exception theReason = reason instanceof Exception ? (Exception)reason : new Exception((Throwable)reason);
      FailureDescription failureDescription = new FailureDescription(failureSource, theReason, operation);
      ade.addFailureDescription(failureDescription);
      Iterator iterator = this.prepareFailuresMap.entrySet().iterator();

      while(iterator.hasNext()) {
         Map.Entry entry = (Map.Entry)iterator.next();
         Exception exc = entry.getValue() instanceof Exception ? (Exception)entry.getValue() : new Exception((Throwable)entry.getValue());
         FailureDescription prepareFailureDescription = new FailureDescription((String)entry.getKey(), exc, operation);
         ade.addFailureDescription(prepareFailureDescription);
      }

   }

   public final void signalCancelFailed(boolean doReset) {
      if (isDebugEnabled()) {
         debug("'cancel failed' for id '" + this.requestId + "'");
      }

      AdminDeploymentException ade = new AdminDeploymentException();
      String cancelSource = localServer;
      DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)this.request.getTaskRuntime();
      if (!this.isCancelledByUser() && !this.isCancelledByClusterConstraints()) {
         this.includePrepareFailure(ade);
         this.includeTimeoutFailure(ade);
         this.includeCancelDeliveryFailures(ade);
         this.includeCancelFailures(ade);
         adminRequestManager.deliverDeployFailedCallback(this.request, ade);
         taskRuntime.setState(3);
      } else {
         String operation = DeploymentServiceLogger.prepareOperation();
         Exception cancelReason = new Exception(DeploymentServiceLogger.deploymentCancelled(this.requestId));
         FailureDescription failureDescription = new FailureDescription(cancelSource, cancelReason, operation);
         ade.addFailureDescription(failureDescription);
         taskRuntime.addFailedTarget(localServer, taskRuntime.getError());
         adminRequestManager.deliverDeployCancelFailedCallback(this.request, ade);
         taskRuntime.setState(7);
      }

      if (doReset) {
         this.scheduleNextRequest();
         this.request.reset();
      }

   }

   public final void signalAppPrepareFailed() {
      if (isDebugEnabled()) {
         debug("'app prepare failed' for id '" + this.requestId + "'");
      }

      AdminDeploymentException ade = new AdminDeploymentException();
      this.includePrepareFailure(ade);
      this.includeTimeoutFailure(ade);
      adminRequestManager.deliverAppPrepareFailedCallback(this.request, ade);
   }

   DisconnectListener getPrepareDisconnectListener(String server) {
      Object listener = this.prepareDisconnectListeners.get(server);
      if (listener == null) {
         synchronized(this.prepareDisconnectListeners) {
            listener = this.prepareDisconnectListeners.get(server);
            if (listener == null) {
               listener = new PrepareDisconnectListenerImpl(server);
               this.prepareDisconnectListeners.put(server, listener);
            }
         }
      }

      if (isDebugEnabled()) {
         debug(" +++ Returning Prepare DisconnectListener : " + listener);
      }

      return (DisconnectListener)listener;
   }

   DisconnectListener getCommitDisconnectListener(String server) {
      Object listener = this.commitDisconnectListeners.get(server);
      if (listener == null) {
         synchronized(this.commitDisconnectListeners) {
            listener = this.commitDisconnectListeners.get(server);
            if (listener == null) {
               listener = new CommitDisconnectListenerImpl(server);
               this.commitDisconnectListeners.put(server, listener);
            }
         }
      }

      if (isDebugEnabled()) {
         debug(" +++ Returning Commit DisconnectListener : " + listener);
      }

      return (DisconnectListener)listener;
   }

   DisconnectListener getCancelDisconnectListener(String server) {
      Object listener = this.cancelDisconnectListeners.get(server);
      if (listener == null) {
         synchronized(this.cancelDisconnectListeners) {
            listener = this.cancelDisconnectListeners.get(server);
            if (listener == null) {
               listener = new CancelDisconnectListenerImpl(server);
               this.cancelDisconnectListeners.put(server, listener);
            }
         }
      }

      if (isDebugEnabled()) {
         debug(" +++ Returning Cancel DisconnectListener : " + listener);
      }

      return (DisconnectListener)listener;
   }

   private void includeCancelFailures(AdminDeploymentException ade) {
      if (this.cancelFailureSet.size() > 0) {
         Iterator iterator = this.cancelFailureSet.iterator();

         while(iterator.hasNext()) {
            ade.addFailureDescription((FailureDescription)iterator.next());
         }
      }

   }

   private void includeCancelDeliveryFailures(AdminDeploymentException ade) {
      if (this.cancelDeliveryFailureTargets.size() > 0) {
         Iterator iterator = this.cancelDeliveryFailureTargets.values().iterator();

         while(iterator.hasNext()) {
            ade.addFailureDescription((FailureDescription)iterator.next());
         }
      }

   }

   private void includeTimeoutFailure(AdminDeploymentException ade) {
      if (this.timedOut()) {
         String message;
         if (this.prepareFailure == null) {
            message = DeploymentServiceLogger.timedOutDuring(this.request.getId(), DeploymentServiceLogger.prepareOperation());
         } else {
            message = DeploymentServiceLogger.timedOutDuring(this.request.getId(), DeploymentServiceLogger.cancelOperation());
         }

         Exception cancelReason = new Exception(message);
         String cancelSource = localServer;
         String operation = DeploymentServiceLogger.prepareOperation();
         FailureDescription failureDescription = new FailureDescription(cancelSource, cancelReason, operation);
         ade.addFailureDescription(failureDescription);
      }

   }

   private void includePrepareFailure(AdminDeploymentException ade) {
      if (this.prepareFailure != null) {
         Throwable cancelReason = this.prepareFailure;
         String cancelSource = this.prepareFailureSource;
         String operation = DeploymentServiceLogger.prepareOperation();
         Exception theReason = cancelReason instanceof Exception ? (Exception)cancelReason : new Exception(cancelReason);
         FailureDescription failureDescription = new FailureDescription(cancelSource, theReason, operation);
         ade.addFailureDescription(failureDescription);
      }

   }

   public final void scheduleNextRequest() {
      if (this.request != null && isDebugEnabled()) {
         debug("scheduling next admin request after id '" + this.request.getId() + "'");
      }

      adminRequestManager.scheduleNextRequest();
   }

   final void reset() {
      if (this.request != null) {
         if (isDebugEnabled()) {
            debug("resetting admin request of id '" + this.request.getId() + "'");
         }

         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               TimeAuditorManager.getInstance().printAuditor(AdminRequestStatus.this.requestId, System.out);
               TimeAuditorManager.getInstance().endAuditor(AdminRequestStatus.this.requestId);
            }
         });
         this.request.cancelTimeoutMonitor();
         this.targetedServers.clear();
         this.targetedServers = null;
         this.targetsToRespondToPrepare.clear();
         this.targetsToRespondToPrepare = null;
         this.targetsToBeRestarted.clear();
         this.targetsToBeRestarted = null;
         this.prepareDeliveryFailureTargets.clear();
         this.prepareDeliveryFailureTargets = null;
         this.targetsToBeCommited.clear();
         this.targetsToBeCommited = null;
         this.targetsToRespondToCommit.clear();
         this.targetsToRespondToCommit = null;
         this.commitDeliveryFailureTargets.clear();
         this.commitDeliveryFailureTargets = null;
         this.commitFailureSet.clear();
         this.commitFailureSet = null;
         this.targetsToBeCanceled.clear();
         this.targetsToBeCanceled = null;
         this.targetsToRespondToCancel.clear();
         this.targetsToRespondToCancel = null;
         this.cancelDeliveryFailureTargets.clear();
         this.cancelDeliveryFailureTargets = null;
         this.cancelFailureSet.clear();
         this.cancelFailureSet = null;
         Iterator iterator = this.adminServerStateList.iterator();

         while(iterator.hasNext()) {
            AdminServerState state = (AdminServerState)iterator.next();
            state.reset();
         }

         this.adminServerStateList.clear();
         this.adminServerStateList = null;
         this.currentState = null;
         this.prepareFailure = null;
         this.prepareFailureSource = null;
         this.prepareFailuresMap.clear();
         adminRequestManager.removeRequest(this.request.getId());
         this.request = null;
      }

   }

   private String dumpStatus() {
      StringBuffer sb = new StringBuffer();
      sb.append("admin request status [ ");
      sb.append("\n\tid: ");
      sb.append(this.requestId);
      sb.append(", ");
      if (this.request == null) {
         sb.append(" is reset");
         sb.append("\n]");
         return sb.toString();
      } else {
         sb.append("\n \tcurrent state: ");
         sb.append(this.currentState.toString());
         Iterator iterator = this.targetedServers.iterator();
         sb.append("\n \ttargets: ");

         while(iterator.hasNext()) {
            sb.append((String)iterator.next());
            sb.append(" ");
         }

         if (!this.prepareDeliveryFailureTargets.isEmpty()) {
            dumpCollection(sb, this.prepareDeliveryFailureTargets.keySet().iterator(), "prepare failed to be delivered to: ");
         }

         if (!this.targetsToRespondToPrepare.isEmpty()) {
            dumpCollection(sb, this.targetsToRespondToPrepare.iterator(), "targets to respond to prepare: ");
         }

         if (!this.targetsToBeRestarted.isEmpty()) {
            dumpCollection(sb, this.targetsToBeRestarted.iterator(), "targets to be restarted: ");
         }

         if (!this.targetsToBeCommited.isEmpty()) {
            dumpCollection(sb, this.targetsToBeCommited.iterator(), "targets that are to be commited: ");
         }

         if (!this.commitDeliveryFailureTargets.isEmpty()) {
            dumpCollection(sb, this.commitDeliveryFailureTargets.keySet().iterator(), "commit failed to be delivered to: ");
         }

         if (!this.targetsToRespondToCommit.isEmpty()) {
            dumpCollection(sb, this.targetsToRespondToCommit.iterator(), "targets to respond to commit: ");
         }

         if (!this.commitFailureSet.isEmpty()) {
            dumpCollection(sb, this.commitFailureSet.keySet().iterator(), "targets commit failed on : ");
         }

         if (!this.targetsToBeCanceled.isEmpty()) {
            dumpCollection(sb, this.targetsToBeCanceled.iterator(), "targets that are to be canceled: ");
         }

         if (!this.cancelDeliveryFailureTargets.isEmpty()) {
            dumpCollection(sb, this.cancelDeliveryFailureTargets.keySet().iterator(), "cancel failed to be delivered to: ");
         }

         if (!this.targetsToRespondToCancel.isEmpty()) {
            dumpCollection(sb, this.targetsToRespondToCancel.iterator(), "targets to respond to cancel: ");
         }

         if (!this.cancelFailureSet.isEmpty()) {
            sb.append("\n \t");
            sb.append("targets cancel failed on : ");
            Iterator iter = this.cancelFailureSet.iterator();

            while(iter.hasNext()) {
               FailureDescription fd = (FailureDescription)iter.next();
               sb.append(fd.getServer());
               sb.append(" ");
            }
         }

         if (this.prepareFailure != null) {
            sb.append("\n \t prepare failure: " + this.prepareFailure.toString() + " on " + this.prepareFailureSource);
         }

         if (this.timedOut()) {
            sb.append("\n \t has timed out");
         }

         if (this.isCancelledByUser()) {
            sb.append("\n \t was cancelled by the user / administrator");
         }

         if (this.isCancelledByClusterConstraints()) {
            sb.append("\n \t was cancelled due to all targets not available and cluster-constraints-enabled being set to true");
         }

         sb.append("\n]");
         return sb.toString();
      }
   }

   private static void dumpCollection(StringBuffer sb, Iterator iterator, String prefix) {
      sb.append("\n \t");
      sb.append(prefix);

      while(iterator.hasNext()) {
         sb.append((String)iterator.next());
         sb.append(" ");
      }

   }

   private void prepareFailedDueToServerDisconnect(String server) {
      this.targetsToBeCommited.remove(server);
      Loggable logger = DeploymentServiceLogger.logDeferredDueToDisconnectLoggable(Long.toString(this.requestId), server);
      String operation = DeploymentServiceLogger.operationDelivery(DeploymentServiceLogger.prepareOperation());
      FailureDescription failureDescription = new FailureDescription(server, new Exception(logger.getMessage()), operation);
      this.targetsToRespondToPrepare.remove(server);
      this.targetsToBeCanceled.remove(server);
      this.prepareDeliveryFailureTargets.put(server, failureDescription);
      if (isClusterConstraintsEnabled() && this.clusterConstraintTargets.contains(server)) {
         if (isDebugEnabled()) {
            debug("ClusterConstraints is enabled and not all targets are reachable, so cancelling request '" + this.requestId + "'");
         }

         try {
            this.request.cancelDueToClusterConstraints();
            if (this.allPreparesDelivered()) {
               this.getCurrentState().allPreparesDelivered();
            }

            return;
         } catch (InvalidStateException var6) {
            if (isDebugEnabled()) {
               debug("attempt to 'cancel' id '" + this.requestId + "' failed due to '" + var6.getMessage() + "'");
            }

            this.signalCancelFailed(false);
         }
      }

      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      this.request.receivedPrepareSucceeded(this.requestId, server, false);
   }

   private void commitFailedDueToServerDisconnect(String server) {
      Loggable logger = DeploymentServiceLogger.logDeferredDueToDisconnectLoggable(Long.toString(this.requestId), server);
      String operation = DeploymentServiceLogger.operationDelivery(DeploymentServiceLogger.commitOperation());
      FailureDescription failureDescription = new FailureDescription(server, new Exception(logger.getMessage()), operation, true);
      this.targetsToRespondToCommit.remove(server);
      this.targetsToBeCanceled.remove(server);
      this.commitDeliveryFailureTargets.put(server, failureDescription);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      if (isClusterConstraintsEnabled() && this.clusterConstraintTargets.contains(server)) {
         if (isDebugEnabled()) {
            debug("ClusterConstraints is enabled and not all targets are reachable, so cancelling request '" + this.requestId + "'");
         }

         try {
            this.request.cancelDueToClusterConstraints();
            if (this.allCommitsDelivered()) {
               this.getCurrentState().allCommitsDelivered();
            }

            return;
         } catch (InvalidStateException var6) {
            if (isDebugEnabled()) {
               debug("attempt to 'cancel' id '" + this.requestId + "' failed due to '" + var6.getMessage() + "'");
            }

            this.signalCancelFailed(false);
         }
      }

      this.request.receivedCommitSucceeded(server);
   }

   private void cancelFailedDueToServerDisconnect(String server) {
      Loggable logger = DeploymentServiceLogger.logDeferredDueToDisconnectLoggable(Long.toString(this.requestId), server);
      String operation = DeploymentServiceLogger.operationDelivery(DeploymentServiceLogger.cancelOperation());
      FailureDescription failureDescription = new FailureDescription(server, new Exception(logger.getMessage()), operation);
      this.targetsToRespondToCancel.remove(server);
      this.cancelDeliveryFailureTargets.put(server, failureDescription);
      if (isDebugEnabled()) {
         debug(this.dumpStatus());
      }

      if (this.allTargetsUnreachableForCancelDelivery()) {
         this.signalCancelFailed(true);
      } else {
         this.request.receivedCancelSucceeded(server);
      }
   }

   private void removePrepareDisconnectListener(String server) {
      DisconnectListener listener = null;
      synchronized(this.prepareDisconnectListeners) {
         listener = (DisconnectListener)((HashMap)this.prepareDisconnectListeners.clone()).get(server);
      }

      if (listener != null) {
         adminRequestManager.removeDisconnectListener(server, listener);
      }

   }

   private void removeCommitDisconnectListener(String server) {
      DisconnectListener listener = null;
      synchronized(this.commitDisconnectListeners) {
         listener = (DisconnectListener)((HashMap)this.commitDisconnectListeners.clone()).get(server);
      }

      if (listener != null) {
         adminRequestManager.removeDisconnectListener(server, listener);
      }

   }

   private void removeCancelDisconnectListener(String server) {
      DisconnectListener listener = null;
      synchronized(this.cancelDisconnectListeners) {
         listener = (DisconnectListener)((HashMap)this.cancelDisconnectListeners.clone()).get(server);
      }

      if (listener != null) {
         adminRequestManager.removeDisconnectListener(server, listener);
      }

   }

   private static boolean isClusterConstraintsEnabled() {
      if (!clusterConstraintsInitialized) {
         clusterConstraintsEnabled = ManagementService.getRuntimeAccess(kernelId).getDomain().isClusterConstraintsEnabled();
         clusterConstraintsInitialized = true;
      }

      return clusterConstraintsEnabled;
   }

   private static String getThreadDumpsOnServers(Set servers) {
      if (servers == null) {
         return null;
      } else {
         Iterator theIter = servers.iterator();
         StringBuffer sBuffer = new StringBuffer();
         sBuffer.append("ThreadDumps on servers ").append(servers).append("{\n");

         while(theIter.hasNext()) {
            String eachTarget = (String)theIter.next();

            try {
               DomainRuntimeServiceMBean drsm = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
               ServerRuntimeMBean srm = drsm.lookupServerRuntime(eachTarget);
               String dump = srm.getJVMRuntime().getThreadStackDump();
               sBuffer.append(" ThreadDump for ").append(eachTarget).append("<\n");
               sBuffer.append(dump);
               sBuffer.append("\n>\n\n");
            } catch (Throwable var7) {
               sBuffer.append(" Exception while getting ThreadDump for ");
               sBuffer.append(eachTarget).append(" :: ");
               sBuffer.append(StackTraceUtils.throwable2StackTrace(var7));
               sBuffer.append("\n\n");
            }
         }

         sBuffer.append("\n}\n");
         return sBuffer.toString();
      }
   }

   static {
      localServer = ManagementService.getRuntimeAccess(kernelId).getServerName();
      clusterConstraintsInitialized = false;
      clusterConstraintsEnabled = false;
   }

   private class CancelDisconnectListenerImpl extends DisconnectListenerImpl {
      CancelDisconnectListenerImpl(String server) {
         super(server);
      }

      public void onDisconnect(DisconnectEvent event) {
         String server = this.serverName;
         if (event instanceof ServerDisconnectEvent) {
            server = ((ServerDisconnectEvent)event).getServerName();
         }

         if (AdminRequestStatus.isDebugEnabled()) {
            AdminRequestStatus.debug(" +++ Got Disconnect event... : " + event + " on : " + this.toString());
         }

         boolean isPendingServer = AdminRequestStatus.this.targetsToRespondToCancel != null && AdminRequestStatus.this.targetsToRespondToCancel.contains(server);
         if (isPendingServer) {
            AdminRequestStatus.this.cancelFailedDueToServerDisconnect(server);
         }

      }
   }

   private class CommitDisconnectListenerImpl extends DisconnectListenerImpl {
      CommitDisconnectListenerImpl(String server) {
         super(server);
      }

      public void onDisconnect(DisconnectEvent event) {
         String server = this.serverName;
         if (event instanceof ServerDisconnectEvent) {
            server = ((ServerDisconnectEvent)event).getServerName();
         }

         if (AdminRequestStatus.isDebugEnabled()) {
            AdminRequestStatus.debug(" +++ Got Disconnect event... : " + event + " on : " + this.toString());
         }

         boolean isPendingServer = AdminRequestStatus.this.targetsToRespondToCommit != null && AdminRequestStatus.this.targetsToRespondToCommit.contains(server);
         if (isPendingServer) {
            AdminRequestStatus.this.commitFailedDueToServerDisconnect(server);
         }

      }
   }

   private class PrepareDisconnectListenerImpl extends DisconnectListenerImpl {
      PrepareDisconnectListenerImpl(String server) {
         super(server);
      }

      public void onDisconnect(DisconnectEvent event) {
         String server = this.serverName;
         if (event instanceof ServerDisconnectEvent) {
            server = ((ServerDisconnectEvent)event).getServerName();
         }

         if (AdminRequestStatus.isDebugEnabled()) {
            AdminRequestStatus.debug(" +++ Got Disconnect event... : " + event + " on : " + this.toString());
         }

         boolean isPendingServer = AdminRequestStatus.this.targetsToRespondToPrepare != null && AdminRequestStatus.this.targetsToRespondToPrepare.contains(server);
         if (isPendingServer) {
            AdminRequestStatus.this.prepareFailedDueToServerDisconnect(server);
         }

      }
   }

   private abstract class DisconnectListenerImpl implements DisconnectListener {
      protected String serverName = null;

      DisconnectListenerImpl(String server) {
         this.serverName = server;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append(super.toString()).append("(");
         sb.append("Server=").append(this.serverName).append(", ");
         sb.append("requestId=").append(AdminRequestStatus.this.requestId);
         return sb.toString();
      }
   }
}
