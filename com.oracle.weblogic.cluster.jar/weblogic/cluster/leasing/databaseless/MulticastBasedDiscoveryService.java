package weblogic.cluster.leasing.databaseless;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterService;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.cluster.messaging.internal.ServerInformationImpl;
import weblogic.health.HealthMonitorService;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

final class MulticastBasedDiscoveryService implements DiscoveryService, TimerListener, DisconnectActionListener {
   private static final DebugCategory debugDiscoveryService = Debug.getCategory("weblogic.cluster.leasing.DiscoveryService");
   private static final boolean DEBUG = debugEnabled();
   private ServerInformation localInformation;
   private boolean stopped;
   private ClusterGroupView groupView;

   public static MulticastBasedDiscoveryService getInstance() {
      return MulticastBasedDiscoveryService.Factory.THE_ONE;
   }

   public void start(int discoveryTimeoutSecs) {
      if (!ClusterState.getInstance().setState("discovery")) {
         if (DEBUG) {
            debug("unable to transition from " + ClusterState.getInstance().getState() + " to " + "discovery");
         }

      } else {
         this.localInformation = createLocalServerInformation();
         if (DEBUG) {
            debug("starting discovery timer that will expire in " + discoveryTimeoutSecs + " seconds");
         }

         TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, (long)(discoveryTimeoutSecs * 1000));
      }
   }

   private static ServerInformation createLocalServerInformation() {
      return new ServerInformationImpl(ClusterService.getClusterServiceInternal().getLocalMember());
   }

   private ClusterGroupView discoveredGroupView() {
      Collection remoteMembers = ClusterService.getClusterServiceInternal().getAllRemoteMembers();
      if (DEBUG) {
         debug("ClusterService has " + remoteMembers.size() + " members");
      }

      if (remoteMembers.size() == 0) {
         if (DEBUG) {
            debug("this is the only running cluster member !");
         }

         return new ClusterGroupView(this.localInformation, (ServerInformation[])null);
      } else {
         Iterator iter = remoteMembers.iterator();
         TreeSet discoveredMembers = new TreeSet();
         discoveredMembers.add(this.localInformation);

         ServerInformationImpl server;
         for(; iter.hasNext(); discoveredMembers.add(server)) {
            ClusterMemberInfo info = (ClusterMemberInfo)iter.next();
            server = new ServerInformationImpl(info);
            if (DEBUG) {
               debug("discovered " + server);
            }
         }

         return new ClusterGroupView(discoveredMembers);
      }
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[DiscoveryService] " + s);
   }

   public void timerExpired(Timer timer) {
      String currentState = ClusterState.getInstance().getState().intern();
      if (DEBUG) {
         debug("discovery timer detected state " + currentState);
      }

      if (currentState != "discovery") {
         if (DEBUG) {
            debug("discovery timer quit as state is " + currentState);
         }

      } else {
         this.groupView = this.discoveredGroupView();
         if (DEBUG) {
            debug("discovered group view " + this.groupView);
         }

         if (this.groupView.isSeniorMost(this.localInformation)) {
            if (DEBUG) {
               debug("we are the seniormost member. try forming the cluster");
            }

            EnvironmentFactory.getClusterFormationService().start(this.groupView, (LeaseView)null);
         } else {
            this.registerForDisconnects();
         }

      }
   }

   private synchronized void registerForDisconnects() {
      if (!this.stopped) {
         if (DEBUG) {
            debug("we are not the senior most. registering for disconnects");
         }

         EnvironmentFactory.getClusterMemberDisconnectMonitor().start(this.groupView, this);
      }
   }

   public synchronized void stop() {
      if (DEBUG) {
         debug("stopped !");
      }

      if (!this.stopped) {
         this.stopped = true;
         EnvironmentFactory.getClusterMemberDisconnectMonitor().stop();
      }
   }

   public void OnBecomingSeniorMostMember() {
      if (DEBUG) {
         debug("DisconnectMonitor notified that we are seniormost! trying to form the cluster");
      }

      if (this.groupView.isSeniorMost(this.localInformation)) {
         if (DEBUG) {
            debug("we are the seniormost member. try forming the cluster");
         }

         EnvironmentFactory.getClusterMemberDisconnectMonitor().stop();
         EnvironmentFactory.getClusterFormationService().start(this.groupView, (LeaseView)null);
      }

   }

   public synchronized void OnLosingServerReachabilityMajority() {
      if (DEBUG) {
         debug("OnLosingServerReachabilityMajority() called ! marking the server as failed");
      }

      String reason = "Server is not in the majority cluster partition";
      ClusterState.getInstance().setState("failed", reason);
      this.stop();
      HealthMonitorService.subsystemFailed("DatabaseLessLeasing", reason);
   }

   public synchronized void onLosingLeader() {
      this.groupView.removeLeader();
   }

   public synchronized void onLosingMember(ServerInformation server) {
      this.groupView.removeMember(server);
   }

   private static boolean debugEnabled() {
      return debugDiscoveryService.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static final class Factory {
      static final MulticastBasedDiscoveryService THE_ONE = new MulticastBasedDiscoveryService();
   }
}
