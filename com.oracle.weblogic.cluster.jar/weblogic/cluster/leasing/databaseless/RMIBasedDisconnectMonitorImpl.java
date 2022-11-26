package weblogic.cluster.leasing.databaseless;

import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.SRMResult;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.management.configuration.DatabaseLessLeasingBasisMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class RMIBasedDisconnectMonitorImpl implements ClusterMemberDisconnectMonitor, ServerFailureListener, GroupViewListener, TimerListener {
   private static final DebugCategory debugDisconnectMonitor = Debug.getCategory("weblogic.cluster.leasing.DisconnectMonitor");
   private static final boolean DEBUG = debugEnabled();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ClusterGroupView groupView;
   private DisconnectActionListener listener;
   private ServerInformation localInformation;
   private Map detectorMap = new HashMap();
   private ServerInformation leaderInfo;
   private boolean isLeader;
   private Timer srmCheckTimer;
   private Set deadServers;

   public static ClusterMemberDisconnectMonitor getInstance() {
      return RMIBasedDisconnectMonitorImpl.Factory.THE_ONE;
   }

   public synchronized void start(ClusterGroupView groupView, DisconnectActionListener listener) {
      if (DEBUG) {
         debug("inside start()");
      }

      assert this.listener == null && this.groupView == null;

      this.groupView = groupView;
      this.listener = listener;
      this.deadServers = new HashSet();
      this.leaderInfo = groupView.getLeaderInformation();

      assert this.leaderInfo != null;

      this.localInformation = ClusterLeaderService.getInstance().getLocalServerInformation();
      if (this.leaderInfo.equals(this.localInformation)) {
         this.isLeader = true;
         ServerInformation[] remoteMembers = groupView.getRemoteMembers(this.localInformation);
         if (DEBUG) {
            debug("we are the leader ! create server failure detectors for all members in the group view");
         }

         int srmCheckPeriod;
         for(srmCheckPeriod = 0; srmCheckPeriod < remoteMembers.length; ++srmCheckPeriod) {
            this.createFailureDetector(remoteMembers[srmCheckPeriod]);
         }

         srmCheckPeriod = getLeaderSRMCheckPeriod();
         this.srmCheckTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, 0L, (long)srmCheckPeriod);
      } else {
         if (DEBUG) {
            debug("creating server failure detector for " + this.leaderInfo);
         }

         this.createFailureDetector(this.leaderInfo);
         this.srmCheckTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, 0L);
      }

      this.groupView.setGroupViewListener(this);
   }

   private static int getLeaderSRMCheckPeriod() {
      DatabaseLessLeasingBasisMBean mbean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getDatabaseLessLeasingBasis();
      if (!mbean.isPeriodicSRMCheckEnabled()) {
         if (DEBUG) {
            debug("periodic SRM check is disabled !");
         }

         return 0;
      } else {
         if (DEBUG) {
            debug("periodic SRM check is enabled !");
         }

         int discoveryTimeout = mbean.getMemberDiscoveryTimeout();
         return discoveryTimeout * 1000 / 2;
      }
   }

   public synchronized void stop() {
      if (this.srmCheckTimer != null) {
         this.srmCheckTimer.cancel();
      }

      Iterator iter = this.detectorMap.values().iterator();

      while(iter.hasNext()) {
         ((ServerFailureDetector)iter.next()).stop();
      }

      this.listener = null;
      if (this.groupView != null) {
         this.groupView.setGroupViewListener((GroupViewListener)null);
      }

      this.groupView = null;
      this.deadServers = null;
   }

   public synchronized void memberAdded(ServerInformation server) {
      if (this.isLeader) {
         this.createFailureDetector(server);
      }

   }

   public void memberRemoved(ServerInformation server) {
      if (this.detectorMap.get(server) == null) {
         this.onServerFailure(server);
      }

   }

   public void onServerFailure(ServerFailureEvent failureEvent) {
      this.onServerFailure(failureEvent.getServerInformation());
   }

   public synchronized void onMachineFailure(MachineFailureEvent machineFailureEvent) {
      if (this.listener != null) {
         List servers = machineFailureEvent.getFailedServers();
         if (servers != null) {
            Iterator iter = servers.iterator();

            while(iter.hasNext()) {
               ServerInformation server = this.groupView.getServerInformation((String)iter.next());
               if (server != null && !this.deadServers.contains(server)) {
                  this.deadServers.add(server);
                  if (this.leaderInfo.equals(server)) {
                     if (DEBUG) {
                        debug("MachineFailure: leader is really dead ! Invoking onLosingLeader()");
                     }

                     this.listener.onLosingLeader();
                  } else {
                     if (DEBUG) {
                        debug("MachineFailure: " + server.getServerName() + " is dead ! Invoking onLosingMember()");
                     }

                     this.listener.onLosingMember(server);
                  }
               }
            }

            this.reachabilityCheckHelper(machineFailureEvent.getMachineName());
         }
      }
   }

   private synchronized void onServerFailure(ServerInformation server) {
      if (this.listener != null && !this.deadServers.contains(server)) {
         this.deadServers.add(server);
         if (this.leaderInfo.equals(server)) {
            if (DEBUG) {
               debug("leader is really dead ! Invoking onLosingLeader()");
            }

            this.listener.onLosingLeader();
         } else {
            if (DEBUG) {
               debug(server.getServerName() + " is dead ! Invoking onLosingMember()");
            }

            this.listener.onLosingMember(server);
         }

         this.reachabilityCheckHelper((String)null);
      }
   }

   private void reachabilityCheckHelper(String machineToIgnore) {
      SRMResult srm = this.getSRMResult(machineToIgnore, true);
      if (!srm.hasReachabilityMajority()) {
         if (DEBUG) {
            debug("a member is dead but this server does not have reachability majority. OnLosingServerReachabilityMajority()");
         }

         this.listener.OnLosingServerReachabilityMajority();
      } else if (this.groupView.isSeniorMost(this.localInformation)) {
         if (DEBUG) {
            debug("local server is the seniormost member !");
         }

         this.listener.OnBecomingSeniorMostMember();
      } else {
         ServerInformation seniormost = this.groupView.getSeniorMost();
         if (this.detectorMap.get(seniormost) == null) {
            this.createFailureDetector(seniormost);
         }

      }
   }

   protected boolean createFailureDetector(ServerInformation server) {
      if (server == null) {
         if (DEBUG) {
            debug("Failed to create Failure Detector monitor since server information is NULL");
         }

         return false;
      } else {
         ServerFailureDetector detector = EnvironmentFactory.getFailureDetector(server.getServerName());
         detector.start(server, this);
         this.detectorMap.put(server, detector);
         return true;
      }
   }

   private SRMResult getSRMResult(String machineToIgnore, boolean useConnectionTimeout) {
      String clusterName = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getName();
      return EnvironmentFactory.getServerReachabilityMajorityService().performSRMCheck(this.leaderInfo, clusterName, machineToIgnore, useConnectionTimeout);
   }

   public void timerExpired(Timer timer) {
      if (this.listener != null) {
         SRMResult srm = this.getSRMResult((String)null, false);
         if (!srm.hasReachabilityMajority()) {
            if (DEBUG) {
               debug("SRM timer determined that the leader does not have reachability majority. OnLosingServerReachabilityMajority()");
            }

            synchronized(this) {
               if (this.listener != null) {
                  this.listener.OnLosingServerReachabilityMajority();
               }
            }
         }
      }
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[RMIDisconnectMonitor] " + s);
   }

   private static boolean debugEnabled() {
      return debugDisconnectMonitor.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static final class Factory {
      static final RMIBasedDisconnectMonitorImpl THE_ONE = new RMIBasedDisconnectMonitorImpl();
   }
}
