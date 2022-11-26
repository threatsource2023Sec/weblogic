package weblogic.cluster.leasing.databaseless;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
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
import weblogic.management.configuration.DatabaseLessLeasingBasisMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.work.WorkManagerFactory;

public final class ClusterLeader implements ClusterMembersChangeListener, ClusterMessageReceiver, DisconnectActionListener {
   private static final DebugCategory debugClusterLeader = Debug.getCategory("weblogic.cluster.leasing.ClusterLeader");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean DEBUG = debugEnabled();
   private LeaseServer leaseServer;
   private ClusterGroupView groupView;
   private LeaseView leaseView;
   private Timer heartbeatTimer;
   private boolean stopped;

   synchronized void start(ClusterGroupView groupView, LeaseView leaseView) {
      this.groupView = groupView;
      if (leaseView == null) {
         this.leaseView = new LeaseView(groupView.getLeaderInformation().getServerName(), (HashMap)null);
      } else {
         this.leaseView = leaseView;
      }

      ClusterService.getClusterServiceInternal().addClusterMembersListener(this);
      if (DEBUG) {
         debug("installed ClusterLeader for giving leases and managing cluster view. leader information " + groupView.getLeaderInformation());
      }

      this.leaseServer = new LeaseServer(this, this.leaseView);
      ClusterMessageFactory.getInstance().registerReceiver(this);
      DatabaseLessLeasingService.localServerIsClusterLeader();
      EnvironmentFactory.getClusterMemberDisconnectMonitor().start(this.groupView, this);
      this.joinMembersInWaiting();
      if (ClusterState.getInstance().getState() != "failed") {
         this.startClusterHeartBeats();
      }

   }

   private void startClusterHeartBeats() {
      if (!this.stopped) {
         TimerListener listener = new HeartbeatTimer();
         DatabaseLessLeasingBasisMBean mbean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getDatabaseLessLeasingBasis();
         this.heartbeatTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(listener, (long)(mbean.getLeaderHeartbeatPeriod() * 1000), (long)(mbean.getLeaderHeartbeatPeriod() * 1000));
      }
   }

   private void joinMembersInWaiting() {
      if (!this.stopped) {
         Collection members = ClusterService.getClusterServiceInternal().getAllRemoteMembers();
         Set discoveredSet = this.groupView.getMembers();
         if (!members.isEmpty()) {
            Iterator iter = members.iterator();

            while(iter.hasNext()) {
               ClusterMemberInfo info = (ClusterMemberInfo)iter.next();
               ServerInformation server = new ServerInformationImpl(info);
               if (!discoveredSet.contains(server)) {
                  this.joinServer(server);
                  if (ClusterState.getInstance().getState() == "failed") {
                     break;
                  }
               }
            }

         }
      }
   }

   synchronized ServerInformation getLeaderInformation() {
      if (this.stopped) {
         return null;
      } else {
         return this.groupView != null ? this.groupView.getLeaderInformation() : null;
      }
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      int action = cece.getAction();
      if (action == 0 || action == 3) {
         ServerInformation otherInfo = new ServerInformationImpl(cece.getClusterMemberInfo());
         this.joinServer(otherInfo);
      }

   }

   private void joinServer(ServerInformation otherInfo) {
      boolean accepted = false;
      ClusterGroupView view = null;
      JoinResponseMessage response;
      synchronized(this) {
         if (this.stopped || this.groupView.getMembers().contains(otherInfo)) {
            return;
         }

         view = this.groupView;
         ServerInformation leaderInfo = this.groupView.getLeaderInformation();
         if (leaderInfo != null && leaderInfo.compareTo(otherInfo) < 0) {
            if (DEBUG) {
               debug("sending a join response message to " + otherInfo);
            }

            this.groupView.addMember(otherInfo);
            response = JoinResponseMessage.getAcceptedResponse(this.groupView, this.leaseView);
            accepted = true;
         } else {
            if (DEBUG) {
               debug("sending a join rejection message to " + otherInfo);
            }

            response = JoinResponseMessage.getRejectedResponse(this.groupView.getLeaderInformation());
         }
      }

      try {
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         messageSender.send(response, otherInfo.getServerName());
      } catch (LeaderAlreadyExistsException var8) {
         if (DEBUG) {
            debug(otherInfo.getServerName() + " knows about the presence of another live cluster leader. Marking this server as failed and stopping the cluster leader");
         }

         String reason = " knows about the presence of another live cluster leader. Marking this server as failed and stopping the cluster leader ";
         ClusterState.getInstance().setState("failed", reason);
         this.handleExistenceofAnotherLeader(otherInfo, reason);
         accepted = false;
      } catch (RemoteException var9) {
         view.removeMember(otherInfo);
         accepted = false;
      }

      if (accepted) {
         GroupViewUpdateMessage message = GroupViewUpdateMessage.createMemberAdded(this.getLeaderInformation(), otherInfo, view.incrementVersionNumber());
         this.sendGroupMessage(message);
         DatabaseLessLeasingService.fireConsensusServiceGroupViewListenerEvent(otherInfo, true);
      }

   }

   private void handleExistenceofAnotherLeader(final ServerInformation otherInfo, final String reason) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            synchronized(EnvironmentFactory.getClusterMemberDisconnectMonitor()) {
               ClusterLeader.this.stop();
               HealthMonitorService.subsystemFailedForceShutdown("Consensus-Leasing", otherInfo.getServerName() + reason);
            }
         }
      });
   }

   public boolean sendGroupMessage(ClusterMessage message) {
      ServerInformation[] servers;
      synchronized(this) {
         if (this.stopped) {
            return false;
         }

         servers = this.groupView.getRemoteMembers(this.getLeaderInformation());
      }

      if (servers != null && servers.length != 0) {
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         if (DEBUG) {
            debug("sending " + message + " to " + servers.length + " running servers");
         }

         try {
            messageSender.send(message, servers);
            return true;
         } catch (ClusterMessageProcessingException var5) {
            return false;
         }
      } else {
         return true;
      }
   }

   public synchronized void stop() {
      this.stopped = true;
      this.groupView = null;
      EnvironmentFactory.getClusterMemberDisconnectMonitor().stop();
      if (this.heartbeatTimer != null) {
         this.heartbeatTimer.cancel();
      }

   }

   public void OnBecomingSeniorMostMember() {
   }

   public void OnLosingServerReachabilityMajority() {
      if (DEBUG) {
         debug("OnLosingServerReachabilityMajority() called ! marking the server as failed and stopping the cluster leader");
      }

      String reason = "Server is not in the majority cluster partition";
      ClusterState.getInstance().setState("failed", reason);
      this.stop();
      HealthMonitorService.subsystemFailed("DatabaseLessLeasing", reason);
      DatabaseLessLeasingService.localServerLostClusterLeadership();
   }

   public void onLosingLeader() {
      if (DEBUG) {
         debug("onLosingLeader() called on the leader itself ! marking the server as failed and stopping the cluster leader");
      }

      String reason = "NodeManager associated with the local server is unreachable !";
      ClusterState.getInstance().setState("failed", reason);
      this.stop();
      HealthMonitorService.subsystemFailed("DatabaseLessLeasing", reason);
      DatabaseLessLeasingService.localServerLostClusterLeadership();
   }

   public void onLosingMember(ServerInformation server) {
      GroupViewUpdateMessage message;
      synchronized(this) {
         if (this.stopped) {
            return;
         }

         this.groupView.removeMember(server);
         message = GroupViewUpdateMessage.createMemberRemoved(this.getLeaderInformation(), server, this.groupView.incrementVersionNumber());
      }

      this.sendGroupMessage(message);
      DatabaseLessLeasingService.fireConsensusServiceGroupViewListenerEvent(server, false);
   }

   public static ClusterLeader getInstance() {
      return ClusterLeader.Factory.THE_ONE;
   }

   public boolean accept(ClusterMessage message) {
      return message.getMessageType() == 4 || message.getMessageType() == 2 || message.getMessageType() == 8;
   }

   public ClusterResponse process(ClusterMessage message) throws ClusterMessageProcessingException {
      ClusterGroupView view;
      synchronized(this) {
         if (this.stopped) {
            throw new ClusterMessageProcessingException("ClusterLeader is not running as it is not in the majority cluster partition");
         }

         view = this.groupView;
      }

      if (message.getMessageType() == 4) {
         return this.leaseServer.process((LeaseMessage)message);
      } else if (message.getMessageType() == 2) {
         this.handleJoinRequest((JoinRequestMessage)message);
         return null;
      } else if (message.getMessageType() == 8) {
         if (DEBUG) {
            debug("sending a state dump response with group version " + view.getVersionNumber() + " and lease version " + this.leaseView.getVersionNumber());
         }

         return new StateDumpResponse(view, this.leaseView);
      } else {
         throw new AssertionError("Unknown message received by leader " + message);
      }
   }

   private void handleJoinRequest(final JoinRequestMessage joinRequestMessage) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            ClusterLeader.this.joinServer(joinRequestMessage.getSenderInformation());
         }
      });
   }

   private static boolean debugEnabled() {
      return debugClusterLeader.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug(Thread.currentThread().getName() + " [ClusterLeader] " + s);
   }

   private DatabaseLessLeasingService getConsensusLeasingImpl() {
      return (DatabaseLessLeasingService)GlobalServiceLocator.getServiceLocator().getService(DatabaseLessLeasingService.class, new Annotation[0]);
   }

   private class HeartbeatTimer implements TimerListener {
      private HeartbeatTimer() {
      }

      public void timerExpired(Timer timer) {
         ClusterGroupView view;
         synchronized(ClusterLeader.this) {
            if (ClusterLeader.this.stopped) {
               timer.cancel();
               return;
            }

            view = ClusterLeader.this.groupView;
         }

         Set remoteMembers = new HashSet();
         ServerInformation[] discoveredServers = this.getDiscoveredMembers();
         if (discoveredServers != null) {
            remoteMembers = new HashSet(Arrays.asList(discoveredServers));
         }

         ServerInformation[] groupViewMembers = view.getRemoteMembers(view.getLeaderInformation());
         if (groupViewMembers != null) {
            remoteMembers.addAll(Arrays.asList(groupViewMembers));
         }

         if (remoteMembers.size() != 0) {
            ServerInformation[] servers = new ServerInformation[remoteMembers.size()];
            servers = (ServerInformation[])((ServerInformation[])remoteMembers.toArray(servers));
            ClusterLeaderHeartbeatMessage heartbeat = ClusterLeaderHeartbeatMessage.create(view, ClusterLeader.this.leaseView);
            ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getOneWayMessageSender();
            if (ClusterLeader.DEBUG) {
               ClusterLeader.debug("sending " + heartbeat + " to " + servers.length + " running servers");
            }

            try {
               messageSender.send(heartbeat, servers);
            } catch (ClusterMessageProcessingException var10) {
            }

         }
      }

      private ServerInformation[] getDiscoveredMembers() {
         Collection members = ClusterService.getClusterServiceInternal().getAllRemoteMembers();
         if (members.isEmpty()) {
            return null;
         } else {
            ServerInformation[] servers = new ServerInformation[members.size()];
            Iterator iter = members.iterator();

            ClusterMemberInfo info;
            for(int count = 0; iter.hasNext(); servers[count++] = new ServerInformationImpl(info)) {
               info = (ClusterMemberInfo)iter.next();
            }

            return servers;
         }
      }

      // $FF: synthetic method
      HeartbeatTimer(Object x1) {
         this();
      }
   }

   private static final class Factory {
      static final ClusterLeader THE_ONE = new ClusterLeader();
   }
}
