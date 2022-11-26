package weblogic.cluster.leasing.databaseless;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.util.HashMap;
import weblogic.cluster.ClusterService;
import weblogic.cluster.messaging.internal.ClusterMessage;
import weblogic.cluster.messaging.internal.ClusterMessageFactory;
import weblogic.cluster.messaging.internal.ClusterMessageProcessingException;
import weblogic.cluster.messaging.internal.ClusterMessageReceiver;
import weblogic.cluster.messaging.internal.ClusterMessageSender;
import weblogic.cluster.messaging.internal.ClusterResponse;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.cluster.messaging.internal.ServerInformationImpl;
import weblogic.health.HealthMonitorService;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.work.WorkManagerFactory;

public class ClusterMember implements ClusterMessageReceiver, DisconnectActionListener {
   private static final DebugCategory debugClusterMember = Debug.getCategory("weblogic.cluster.leasing.ClusterMember");
   private static final boolean DEBUG = debugEnabled();
   private final ServerInformation localInformation;
   private ClusterGroupView groupView;
   private LeaseView leaseView;
   private ServerInformation leaderInformation;
   private ClusterFormationMessage acceptedFormationMessage;

   public static ClusterMember getInstance() {
      return ClusterMember.Factory.THE_ONE;
   }

   private ClusterMember() {
      this.localInformation = createLocalServerInformation();
      ClusterMessageFactory.getInstance().registerReceiver(this);
   }

   private static ServerInformation createLocalServerInformation() {
      return new ServerInformationImpl(ClusterService.getClusterServiceInternal().getLocalMember());
   }

   public ServerInformation getLeaderInformation() {
      return this.leaderInformation;
   }

   ServerInformation getLocalServerInformation() {
      return this.localInformation;
   }

   ClusterGroupView getGroupView() {
      return this.groupView;
   }

   LeaseView getLeaseView() {
      return this.leaseView;
   }

   void addMember(ServerInformation serverInformation) {
      this.groupView.addMember(serverInformation);
   }

   void removeMember(ServerInformation serverInformation) {
      this.groupView.removeMember(serverInformation);
   }

   public String getLeaderName() {
      ServerInformation leaderInfo = this.getLeaderInformation();
      return leaderInfo != null ? leaderInfo.getServerName() : null;
   }

   public boolean accept(ClusterMessage message) {
      int type = message.getMessageType();
      return type == 1 || type == 3 || type == 5 || type == 6 || type == 7 || type == 10;
   }

   public synchronized ClusterResponse process(ClusterMessage message) throws ClusterMessageProcessingException {
      if (DEBUG) {
         debug("received remote message " + message);
      }

      if ("failed".equals(ClusterState.getInstance().getState())) {
         throw new ClusterMessageProcessingException("cannot process message '" + message + "' as the server is in failed state");
      } else if (message.getMessageType() == 1) {
         return this.handleFormationRequest((ClusterFormationMessage)message);
      } else if (message.getMessageType() == 3) {
         return this.handleJoinResponseRequest((JoinResponseMessage)message);
      } else if (message.getMessageType() == 5) {
         return this.handleLeaseTableUpdateRequest((LeaseTableUpdateMessage)message);
      } else if (message.getMessageType() == 6) {
         return this.handleGroupViewUpdateRequest((GroupViewUpdateMessage)message);
      } else if (message.getMessageType() == 7) {
         return this.handleLeaderHeartbeatRequest((ClusterLeaderHeartbeatMessage)message);
      } else if (message.getMessageType() == 10) {
         return this.handleLeaderQuery((LeaderQueryMessage)message);
      } else {
         throw new AssertionError("Received an unsolicited request " + message);
      }
   }

   private void sendJoinRequestMessage(ServerInformation leader) {
      JoinRequestMessage joinRequest = JoinRequestMessage.create(this.localInformation);
      ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getOneWayMessageSender();
      if (DEBUG) {
         debug("sending join request message to " + leader);
      }

      try {
         messageSender.send(joinRequest, leader);
      } catch (RemoteException var5) {
         if (DEBUG) {
            debug("join request message to " + leader + " failed with " + var5.getMessage());
            var5.printStackTrace();
         }
      }

   }

   private ClusterResponse handleLeaderHeartbeatRequest(ClusterLeaderHeartbeatMessage heartbeat) {
      if (DEBUG) {
         debug("cluster state: " + ClusterState.getInstance().getState());
      }

      if (ClusterState.getInstance().getState() == "discovery") {
         this.sendJoinRequestMessage(heartbeat.getSenderInformation());
         return null;
      } else if (ClusterState.getInstance().getState() != "stable") {
         return null;
      } else {
         if (heartbeat.getGroupViewVersion() != this.groupView.getVersionNumber() || heartbeat.getLeaseViewVersion() != this.leaseView.getVersionNumber()) {
            StateDumpResponse response = this.sendStateDumpRequest();
            if (response != null) {
               this.groupView.processStateDump(response.getGroupView());
               this.leaseView.processStateDump(response.getLeaseView());
            }
         }

         return null;
      }
   }

   private synchronized ClusterResponse handleGroupViewUpdateRequest(GroupViewUpdateMessage groupViewUpdateMessage) throws ClusterMessageProcessingException {
      if (this.groupView == null) {
         throw new ClusterMessageProcessingException("Unacceptable group view update.  Cluster member has stopped.  Received:" + groupViewUpdateMessage);
      } else if (this.groupView.getVersionNumber() + 1L != groupViewUpdateMessage.getVersionNumber()) {
         throw new ClusterMessageProcessingException("unacceptable group view update. local version " + this.groupView.getVersionNumber() + " and received version is " + groupViewUpdateMessage.getVersionNumber());
      } else {
         if (groupViewUpdateMessage.getOperation() == 1) {
            this.addMember(groupViewUpdateMessage.getServerInformation());
            this.groupView.incrementVersionNumber();
         } else {
            if (groupViewUpdateMessage.getOperation() != 2) {
               throw new AssertionError("unsupported group view update message " + groupViewUpdateMessage);
            }

            this.removeMember(groupViewUpdateMessage.getServerInformation());
            this.groupView.incrementVersionNumber();
         }

         return null;
      }
   }

   private StateDumpResponse sendStateDumpRequest() {
      if (this.leaderInformation == null) {
         if (DEBUG) {
            debug("leader is null, skip sending stateDump request to leader.");
         }

         return null;
      } else {
         StateDumpRequestMessage statedump = StateDumpRequestMessage.create(this.localInformation, this.groupView, this.leaseView);
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         if (DEBUG) {
            debug("sending statedump request [" + statedump + "] to leader " + this.leaderInformation);
         }

         try {
            StateDumpResponse response = (StateDumpResponse)messageSender.send(statedump, this.leaderInformation);
            return response;
         } catch (RemoteException var4) {
            if (DEBUG) {
               debug("sending statedump request [" + statedump + "] to leader " + this.leaderInformation + " failed!", var4);
            }

            return null;
         }
      }
   }

   private ClusterResponse handleLeaseTableUpdateRequest(LeaseTableUpdateMessage leaseTableUpdateMessage) throws ClusterMessageProcessingException {
      assert this.leaseView != null;

      if (leaseTableUpdateMessage.getVersion() != this.leaseView.getVersionNumber() + 1L) {
         StateDumpResponse response = this.sendStateDumpRequest();
         if (response != null) {
            this.leaseView.processStateDump(response.getLeaseView());
            this.leaseView.process(leaseTableUpdateMessage);
         }
      } else {
         this.leaseView.process(leaseTableUpdateMessage);
      }

      return null;
   }

   private ClusterResponse handleJoinResponseRequest(JoinResponseMessage message) throws LeaderAlreadyExistsException {
      String previousState = ClusterState.getInstance().getState();
      if (previousState.equals("stable")) {
         if (this.leaderInformation == null || !this.leaderInformation.equals(message.getSenderInformation())) {
            throw new LeaderAlreadyExistsException(ClusterState.getInstance().getErrorMessage("stable"));
         }
      } else if (!ClusterState.getInstance().setState("stable")) {
         throw new LeaderAlreadyExistsException(ClusterState.getInstance().getErrorMessage("stable"));
      }

      if (previousState.equals("discovery")) {
         EnvironmentFactory.getDiscoveryService().stop();
      } else {
         EnvironmentFactory.getClusterMemberDisconnectMonitor().stop();
      }

      if (!message.isAccepted()) {
         this.fatalError();
         return null;
      } else {
         this.groupView = message.getGroupView();
         if (this.leaseView == null) {
            this.leaseView = new LeaseView(this.localInformation.getServerName(), (HashMap)null);
         }

         this.leaseView.processStateDump(message.getLeaseView());
         this.leaderInformation = this.groupView.getLeaderInformation();
         final ClusterGroupView clusterGroupView = this.groupView;
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               EnvironmentFactory.getClusterMemberDisconnectMonitor().start(clusterGroupView, ClusterMember.this);
            }
         });
         if (DEBUG) {
            debug("installed leader with group view " + this.groupView);
         }

         return null;
      }
   }

   private synchronized ClusterResponse handleFormationRequest(ClusterFormationMessage clusterFormationMessage) {
      String previousState = ClusterState.getInstance().getState();
      if (this.acceptFormationRequest(clusterFormationMessage) && ClusterState.getInstance().setState("formation")) {
         this.acceptedFormationMessage = clusterFormationMessage;
         this.groupView = this.acceptedFormationMessage.getGroupView();
         if (previousState.equals("discovery")) {
            EnvironmentFactory.getDiscoveryService().stop();
         }

         final ClusterGroupView clusterGroupView = this.acceptedFormationMessage.getGroupView();
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               if (ClusterMember.DEBUG) {
                  ClusterMember.debug("stopping current disconnect monitor during formation");
               }

               EnvironmentFactory.getClusterMemberDisconnectMonitor().stop();
               if (ClusterMember.DEBUG) {
                  ClusterMember.debug("starting new disconnect monitor...");
               }

               EnvironmentFactory.getClusterMemberDisconnectMonitor().start(clusterGroupView, ClusterMember.this);
            }
         });
         if (DEBUG) {
            debug("sending accepted formation response");
         }

         return ClusterFormationResponse.getAcceptedResponse(clusterFormationMessage, this.acceptedFormationMessage, this.localInformation, this.leaseView);
      } else if (this.acceptedFormationMessage != null && this.acceptedFormationMessage.getSenderInformation().equals(clusterFormationMessage.getSenderInformation())) {
         return ClusterFormationResponse.getAcceptedResponse(clusterFormationMessage, this.acceptedFormationMessage, this.localInformation, this.leaseView);
      } else {
         if (DEBUG) {
            debug("sending rejected formation response for message " + clusterFormationMessage);
         }

         return ClusterFormationResponse.getRejectedResponse(clusterFormationMessage, this.acceptedFormationMessage, this.getLeaderInformation(), this.localInformation);
      }
   }

   private boolean acceptFormationRequest(ClusterFormationMessage clusterFormationMessage) {
      if (this.acceptedFormationMessage == null && this.leaderInformation == null) {
         return this.localInformation.compareTo(clusterFormationMessage.getSenderInformation()) > 0;
      } else {
         return false;
      }
   }

   public synchronized void stop() {
      this.groupView = null;
      this.acceptedFormationMessage = null;
      this.leaderInformation = null;
      EnvironmentFactory.getClusterMemberDisconnectMonitor().stop();
   }

   private void fatalError() {
      if (DEBUG) {
         debug("OnLosingServerReachabilityMajority() called ! marking the server as failed");
      }

      String reason = "Server is not in the majority cluster partition";
      ClusterState.getInstance().setState("failed", reason);
      this.stop();
      HealthMonitorService.subsystemFailed("DatabaseLessLeasing", reason);
   }

   public void OnBecomingSeniorMostMember() {
      assert this.groupView != null;

      ClusterGroupView tempView = this.groupView;
      this.stop();
      EnvironmentFactory.getClusterFormationService().start(tempView, this.leaseView);
   }

   public void OnLosingServerReachabilityMajority() {
      this.fatalError();
   }

   public synchronized void onLosingLeader() {
      if (!ClusterState.getInstance().setState("discovery")) {
         throw new AssertionError(ClusterState.getInstance().getErrorMessage("discovery"));
      } else {
         assert this.groupView != null;

         this.groupView.removeLeader();
         this.acceptedFormationMessage = null;
         this.leaderInformation = null;
      }
   }

   public void onLosingMember(ServerInformation server) {
      this.groupView.removeMember(server);
   }

   private ClusterResponse handleLeaderQuery(LeaderQueryMessage message) {
      DatabaseLessLeasingService svc = (DatabaseLessLeasingService)GlobalServiceLocator.getServiceLocator().getService(DatabaseLessLeasingService.class, new Annotation[0]);
      if (svc.isClusterLeader()) {
         return new LeaderQueryResponse(this.localInformation);
      } else {
         return this.leaderInformation != null ? new LeaderQueryResponse(this.leaderInformation) : new LeaderQueryResponse();
      }
   }

   public String toString() {
      return "[ClusterMember with view " + this.groupView + "]";
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[ClusterMember] " + s);
   }

   private static void debug(String s, Throwable t) {
      ConsensusLeasingDebugLogger.debug("[ClusterMember] " + s, t);
   }

   private static boolean debugEnabled() {
      return debugClusterMember.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   // $FF: synthetic method
   ClusterMember(Object x0) {
      this();
   }

   private static final class Factory {
      static final ClusterMember THE_ONE = new ClusterMember();
   }
}
