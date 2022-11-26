package weblogic.cluster.leasing.databaseless;

import java.security.AccessController;
import weblogic.cluster.ClusterService;
import weblogic.cluster.messaging.internal.ClusterMessage;
import weblogic.cluster.messaging.internal.ClusterMessageFactory;
import weblogic.cluster.messaging.internal.ClusterMessageProcessingException;
import weblogic.cluster.messaging.internal.ClusterMessageSender;
import weblogic.cluster.messaging.internal.ClusterResponse;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.SRMResult;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.cluster.messaging.internal.ServerInformationImpl;
import weblogic.health.HealthMonitorService;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class ClusterFormationServiceImpl implements ClusterFormationService, TimerListener, DisconnectActionListener {
   private static final DebugCategory debugClusterFormation = Debug.getCategory("weblogic.cluster.leasing.ClusterFormation");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean DEBUG = debugEnabled();
   private static final int FORMATION_RETRY_PERIOD = 5000;
   private ServerInformation localInformation;
   private ClusterGroupView groupView;
   private LeaseView leaseView;
   private SRMResult result;

   public void start(ClusterGroupView groupView, LeaseView leaseView) {
      if (!ClusterState.getInstance().setState("formation_leader")) {
         if (DEBUG) {
            debug("unable to transition from " + ClusterState.getInstance().getState() + " to " + "formation_leader");
         }

      } else {
         this.groupView = groupView;
         this.leaseView = leaseView;
         this.localInformation = createLocalServerInformation();
         if (DEBUG) {
            debug("starting cluster formation with group view " + groupView + " and leaseView " + leaseView);
         }

         if (!this.ensureServerReachabilityMajority()) {
            if (DEBUG) {
               debug("SRM CHECK RETURNED FALSE ! cannot create the cluster");
            }

            this.OnLosingServerReachabilityMajority();
         } else {
            EnvironmentFactory.getClusterMemberDisconnectMonitor().start(this.groupView, this);

            try {
               if (!this.formClusterInternal()) {
                  TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, 5000L, 5000L);
               }
            } catch (IllegalStateException var4) {
               this.formationFailed(var4);
            }

         }
      }
   }

   private boolean formClusterInternal() {
      ServerInformation[] runningServers = this.groupView.getRemoteMembers(this.localInformation);
      if (runningServers.length == 0) {
         if (DEBUG) {
            debug("there are no other running servers. group formation is complete");
         }

         this.consolidateLeaseView((ClusterResponse[])null);
         this.leaderInitialization();
         return true;
      } else {
         try {
            ClusterResponse[] responses = this.sendFormationMessage(runningServers);
            if (DEBUG) {
               debug("received responses to the formation message from all servers. checking responses ...");
            }

            if (this.isFormationRejectionPresent(responses)) {
               if (DEBUG) {
                  debug("formation message rejected !!");
               }

               this.handleFormationRejection(responses);
               return false;
            } else {
               this.consolidateLeaseView(responses);
               this.becomeLeader(runningServers);
               return true;
            }
         } catch (ClusterMessageProcessingException var3) {
            if (DEBUG) {
               debug("formation message rejected !!");
               var3.printStackTrace();
            }

            this.handleFormationRejection(var3.getResponses());
            return false;
         }
      }
   }

   private void consolidateLeaseView(ClusterResponse[] responses) {
      if (this.leaseView != null) {
         if (responses != null) {
            for(int count = 0; count < responses.length; ++count) {
               ClusterFormationResponse response = (ClusterFormationResponse)responses[count];
               this.leaseView.merge(response.getLeaseView());
            }
         }

         this.leaseView.prepareToBecomeLeader();
         this.leaseView = new LeaseView(this.localInformation.getServerName(), this.leaseView.getLeaseTableReplica(), this.leaseView.getVersionNumber());
      }
   }

   private ClusterResponse[] sendFormationMessage(ServerInformation[] runningServers) throws ClusterMessageProcessingException {
      ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
      ClusterGroupView proposerView = new ClusterGroupView(this.localInformation, runningServers);
      ClusterMessage formationMessage = new ClusterFormationMessage(proposerView);
      if (DEBUG) {
         debug("sending " + formationMessage + " to " + runningServers.length + " running servers");
      }

      return messageSender.send(formationMessage, runningServers);
   }

   private boolean isFormationRejectionPresent(ClusterResponse[] responses) {
      for(int count = 0; count < responses.length; ++count) {
         ClusterFormationResponse response = (ClusterFormationResponse)responses[count];
         if (response == null || !response.isAccepted()) {
            return true;
         }
      }

      return false;
   }

   private void handleFormationRejection(ClusterResponse[] responses) {
      for(int count = 0; count < responses.length; ++count) {
         ClusterFormationResponse response = (ClusterFormationResponse)responses[count];
         if (response != null && !response.isAccepted()) {
            if (DEBUG) {
               debug("got a rejection formation response " + response);
            }

            if (response.getLeaderInformation() != null) {
               if (this.isServerAlive(response.getLeaderInformation().getServerName())) {
                  if (DEBUG) {
                     debug("rejection was caused by the presence of a leader.restarting the server ...");
                  }

                  this.forceShutdown();
               }
            } else {
               if (DEBUG) {
                  debug("rejection was caused by a senior server which is not yet the leader. restarting the server ...");
               }

               this.forceShutdown();
            }
         }
      }

   }

   private boolean isServerAlive(String serverName) {
      if (this.result == null) {
         return true;
      } else {
         String state = this.result.getServerState(serverName);
         if (state == null) {
            return false;
         } else {
            return state.equals("RUNNING") || state.equals("ADMIN");
         }
      }
   }

   private void forceShutdown() {
      try {
         ManagementService.getRuntimeAccess(kernelId).getServerRuntime().forceShutdown();
         throw new IllegalStateException("Server failed formation due to the presence of another leader in the cluster!");
      } catch (ServerLifecycleException var2) {
         System.exit(1);
      }
   }

   private void becomeLeader(ServerInformation[] runningServers) {
      ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
      ClusterMessage joinResponseMessage = JoinResponseMessage.getAcceptedResponse(this.groupView, this.leaseView);
      if (DEBUG) {
         debug("sending join responses to running members with the leadership information");
      }

      try {
         messageSender.send(joinResponseMessage, runningServers);
      } catch (ClusterMessageProcessingException var5) {
         if (DEBUG) {
            debug("got exception while sending join responses. reason:" + var5);
         }
      }

      if (DEBUG) {
         debug("starting cluster leader locally after sending join responses");
      }

      this.leaderInitialization();
      this.groupView = null;
   }

   private boolean ensureServerReachabilityMajority() {
      String clusterName = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getName();
      this.result = EnvironmentFactory.getServerReachabilityMajorityService().performSRMCheck((ServerInformation)null, clusterName);
      return this.result.hasReachabilityMajority();
   }

   private void leaderInitialization() {
      if (!ClusterState.getInstance().setState("stable_leader")) {
         throw new AssertionError(ClusterState.getInstance().getErrorMessage("stable_leader"));
      } else {
         if (DEBUG) {
            debug("starting the ClusterLeader on local server");
         }

         this.stop();
         EnvironmentFactory.getClusterLeader().start(this.groupView, this.leaseView);
      }
   }

   private void stop() {
      EnvironmentFactory.getClusterMemberDisconnectMonitor().stop();
   }

   private static ServerInformation createLocalServerInformation() {
      return new ServerInformationImpl(ClusterService.getClusterServiceInternal().getLocalMember());
   }

   public void timerExpired(Timer timer) {
      try {
         if (this.formClusterInternal()) {
            timer.cancel();
         }
      } catch (IllegalStateException var3) {
         this.formationFailed(var3);
         timer.cancel();
      }

   }

   private void formationFailed(IllegalStateException ise) {
      ClusterState.getInstance().setState("discovery");
      if (DEBUG) {
         debug("Server cannot form cluster due to " + ise.getMessage());
         ise.printStackTrace();
      }

   }

   public void OnBecomingSeniorMostMember() {
   }

   public void OnLosingServerReachabilityMajority() {
      String reason = "Server is not in the majority cluster partition";
      ClusterState.getInstance().setState("failed", reason);
      this.stop();
      HealthMonitorService.subsystemFailed("DatabaseLessLeasing", reason);
   }

   public void onLosingLeader() {
      throw new AssertionError("onLosingLeader() invoked on the ClusterFormationService");
   }

   public void onLosingMember(ServerInformation server) {
      this.groupView.removeMember(server);
   }

   public static ClusterFormationService getInstance() {
      return ClusterFormationServiceImpl.Factory.THE_ONE;
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[ClusterFormationService] " + s);
   }

   private static boolean debugEnabled() {
      return debugClusterFormation.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static final class Factory {
      static final ClusterFormationServiceImpl THE_ONE = new ClusterFormationServiceImpl();
   }
}
