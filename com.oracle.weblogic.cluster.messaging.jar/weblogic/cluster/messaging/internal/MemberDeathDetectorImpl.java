package weblogic.cluster.messaging.internal;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServices.Locator;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingFactory;
import weblogic.cluster.singleton.MemberDeathDetector;
import weblogic.cluster.singleton.Leasing.LeaseOwnerIdentity;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rjvm.PeerGoneEvent;
import weblogic.rjvm.PeerGoneListener;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.collections.ConcurrentHashSet;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
public class MemberDeathDetectorImpl implements MemberDeathDetector, ClusterMembersChangeListener, PeerGoneListener, MessageDeliveryFailureListener {
   private static Map members = Collections.synchronizedMap(new LinkedHashMap());
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory debugDisconnectMonitor = Debug.getCategory("weblogic.cluster.leasing.DisconnectMonitor");
   private static final boolean DEBUG = debugEnabled();
   private static long SUSPECT_TIMEOUT_INTERVAL = 300000L;
   private static long SUSPECTED_MEMBER_MONITOR_INTERVAL = 60000L;
   private SuspectedMemberInfo localServerInfo;
   private Leasing servicesLeaseManager;
   private Leasing serverLeaseManager;
   private Timer heartbeatTimerManager;
   private HeartbeatTimer heartbeatTimer;
   private static final Map suspectedMembers = Collections.synchronizedMap(new LinkedHashMap());
   private boolean started = false;
   protected WorkManager workManager;
   private static ClusterMessage HEARTBEAT_REQUEST;
   private static int HEARTBEAT_INTERVAL;
   private static final Set pendingProbes = new ConcurrentHashSet();
   private Timer suspectedMemberTimer;
   private ClusterServices clusterServices;

   @PostConstruct
   public void postConstruct() {
      this.clusterServices = Locator.locate();
      Debug.assertion(this.clusterServices != null);
      this.localServerInfo = new SuspectedMemberInfoImpl(this.clusterServices.getLocalMember());
      this.servicesLeaseManager = this.findOrCreateLeasingService("service");
      this.serverLeaseManager = this.findOrCreateLeasingService("wlsserver");
      this.workManager = WorkManagerFactory.getInstance().getDefault();
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      HEARTBEAT_INTERVAL = cluster.getDeathDetectorHeartbeatPeriod() * 1000;
      this.createHeartbeatMessage();
   }

   private Leasing findOrCreateLeasingService(String leaseTypeName) {
      LeasingFactory factory = (LeasingFactory)GlobalServiceLocator.getServiceLocator().getService(LeasingFactory.class, new Annotation[0]);
      return factory.findOrCreateLeasingService(leaseTypeName);
   }

   private String getLocalServerName() {
      ServerMBean localServer = ManagementService.getRuntimeAccess(kernelId).getServer();
      return localServer.getName();
   }

   public void start() {
      Collection remoteMembers = this.clusterServices.getRemoteMembers();
      Iterator itr = remoteMembers.iterator();

      while(itr.hasNext()) {
         ClusterMemberInfo info = (ClusterMemberInfo)itr.next();
         members.put(info.serverName(), new SuspectedMemberInfoImpl(info));
      }

      if (DEBUG) {
         debug(" initial set of members: " + members);
      }

      ClusterMessageFactory.getInstance().registerMessageDeliveryFailureListener(this);
      this.clusterServices.addClusterMembersListener(this);
      this.heartbeatTimer = new HeartbeatTimer();
      this.heartbeatTimerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this.heartbeatTimer, 0L, (long)HEARTBEAT_INTERVAL);
      this.suspectedMemberTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new SuspectedMemberListMonitor(), SUSPECTED_MEMBER_MONITOR_INTERVAL, SUSPECTED_MEMBER_MONITOR_INTERVAL);
      this.started = true;
   }

   public void stop() {
      if (this.heartbeatTimerManager != null) {
         this.heartbeatTimerManager.cancel();
      }

      if (this.suspectedMemberTimer != null) {
         this.suspectedMemberTimer.cancel();
      }

      this.started = false;
      if (DEBUG) {
         debug("Halting Member Death Detector");
      }

   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      String serverName = cece.getClusterMemberInfo().serverName();
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(serverName);
      switch (cece.getAction()) {
         case 0:
            this.removeSuspect(serverName);
            members.put(serverName, new SuspectedMemberInfoImpl(cece.getClusterMemberInfo()));
            if (DEBUG) {
               debug("MemberDeathDetectorImpl.clusterMembersChanged: Adding member: " + LeaseOwnerIdentity.getIdentity(cece.getClusterMemberInfo().identity()));
            }
            break;
         case 1:
            SuspectedMemberInfo suspect = (SuspectedMemberInfo)members.remove(serverName);
            if (suspect != null && !suspectedMembers.containsKey(suspect.getServerName())) {
               suspectedMembers.put(suspect.getServerName(), suspect);
               suspect.setSuspectedStartTime(System.currentTimeMillis());
               if (DEBUG) {
                  debug("MemberDeathDetectorImpl.clusterMembersChanged: Suspecting member: " + LeaseOwnerIdentity.getIdentity(suspect.getServerIdentity()));
               }
            }
            break;
         case 2:
            this.removeSuspect(serverName);
            members.put(serverName, new SuspectedMemberInfoImpl(cece.getClusterMemberInfo()));
            if (DEBUG) {
               debug("MemberDeathDetectorImpl.clusterMembersChanged: Update member: " + cece.getClusterMemberInfo().serverName());
            }
            break;
         case 3:
            if (DEBUG) {
               debug("MemberDeathDetectorImpl.clusterMembersChanged: Discover member: " + cece.getClusterMemberInfo().serverName());
            }

            return;
         default:
            if (DEBUG) {
               debug("MemberDeathDetectorImpl.clusterMembersChanged: Unknown ClusterMembersChangeEvent: " + cece.getAction() + " for members: " + cece.getClusterMemberInfo().serverName());
            }

            return;
      }

      if (DEBUG) {
         debug("MemberDeathDetectorImpl.clusterMembersChanged: members: " + members);
      }

   }

   public void peerGone(PeerGoneEvent e) {
      if (DEBUG) {
         debug("MemberDeathDetectorImpl.peerGone event: " + e);
      }

   }

   private void createHeartbeatMessage() {
      ServerInformation serverInformation = new ServerInformationImpl(this.clusterServices.getLocalMember());
      HEARTBEAT_REQUEST = new BaseClusterMessage(serverInformation, 9);
   }

   public void onMessageDeliveryFailure(String serverName, RemoteException re) {
      if (DEBUG) {
         debug("received onMessageDeliveryFailure for " + serverName + " due to " + StackTraceUtils.throwable2StackTrace(re));
      }

      final SuspectedMemberInfo info = (SuspectedMemberInfo)suspectedMembers.get(serverName);
      if (info == null) {
         if (DEBUG) {
            debug(" Suspected member: " + serverName + " not found! Was probably suspended or shutdown");
         }

      } else if (pendingProbes.contains(info)) {
         if (DEBUG) {
            debug("There is already a probe pending for " + info);
         }

      } else {
         pendingProbes.add(info);
         WorkAdapter req = new WorkAdapter() {
            public void run() {
               ProbeContext context = new ProbeContextImpl(info);

               try {
                  ProbeManager.getClusterMasterProbeManager().invoke(context);
               } finally {
                  MemberDeathDetectorImpl.pendingProbes.remove(info);
               }

               if (MemberDeathDetectorImpl.DEBUG) {
                  MemberDeathDetectorImpl.debug("Probe of server: " + LeaseOwnerIdentity.getIdentity(info.getServerIdentity()) + " returned result: " + context.getResult());
               }

               if (context.getResult() == 1) {
                  MemberDeathDetectorImpl.this.removeSuspect(context.getSuspectedMemberInfo().getServerName());
               } else if (context.getResult() != 0) {
                  context = new ProbeContextImpl(MemberDeathDetectorImpl.this.localServerInfo);

                  try {
                     if (MemberDeathDetectorImpl.pendingProbes.contains(MemberDeathDetectorImpl.this.localServerInfo)) {
                        if (MemberDeathDetectorImpl.DEBUG) {
                           MemberDeathDetectorImpl.debug("There is already a probe pending for " + MemberDeathDetectorImpl.this.localServerInfo);
                        }

                        return;
                     }

                     MemberDeathDetectorImpl.pendingProbes.add(MemberDeathDetectorImpl.this.localServerInfo);
                     ProbeManager.getClusterMemberProbeManager().invoke(context);
                  } finally {
                     MemberDeathDetectorImpl.pendingProbes.remove(MemberDeathDetectorImpl.this.localServerInfo);
                  }

                  if (MemberDeathDetectorImpl.DEBUG) {
                     MemberDeathDetectorImpl.debug("Probe of server: " + LeaseOwnerIdentity.getIdentity(MemberDeathDetectorImpl.this.localServerInfo.getServerIdentity()) + " returned result: " + context.getResult());
                  }

                  if (context.getResult() != 1 && context.getResult() != 0) {
                     MemberDeathDetectorHeartbeatReceiver.fatalError(context.getMessage());
                  } else {
                     MemberDeathDetectorImpl.this.voidMemberLeases(info);
                  }
               }
            }

            public String toString() {
               return "Invoking probes for: " + LeaseOwnerIdentity.getIdentity(info.getServerIdentity());
            }
         };
         this.workManager.schedule(req);
      }
   }

   private void voidMemberLeases(SuspectedMemberInfo info) {
      if (DEBUG) {
         debug("WorkAdapter removing suspected member: " + info.getServerName());
      }

      this.removeSuspect(info.getServerName());
      String ownerIdentity = LeaseOwnerIdentity.getIdentity(info.getServerIdentity());
      if (DEBUG) {
         debug(" Voiding all its leases with ownerIdentity: " + ownerIdentity);
      }

      if (!info.hasVoidedSingletonServices()) {
         this.servicesLeaseManager.voidLeases(ownerIdentity);
         info.voidedSingletonServices();
      }

      this.serverLeaseManager.voidLeases(ownerIdentity);
   }

   public String removeMember(String serverName) {
      SuspectedMemberInfo suspect = (SuspectedMemberInfo)members.remove(serverName);
      this.removeSuspect(serverName);
      String suspectName = suspect != null ? suspect.getServerName() : null;
      return suspectName;
   }

   SuspectedMemberInfo removeSuspect(String serverName) {
      SuspectedMemberInfo suspect = (SuspectedMemberInfo)suspectedMembers.remove(serverName);
      if (suspect != null) {
         if (DEBUG) {
            debug("removeSuspect suspect: " + LeaseOwnerIdentity.getIdentity(suspect.getServerIdentity()));
         }
      } else if (DEBUG) {
         debug("removeSuspect attempted to remove suspect: " + serverName + " but SuspectedMemberInfo not found");
      }

      return suspect;
   }

   private static void debug(String s) {
      System.out.println("[MemberDeathDetectorImpl] " + s);
   }

   private static boolean debugEnabled() {
      return debugDisconnectMonitor.isEnabled();
   }

   public boolean isStarted() {
      return this.started;
   }

   private class SuspectedMemberListMonitor implements TimerListener {
      private SuspectedMemberListMonitor() {
      }

      public void timerExpired(Timer timer) {
         Set suspects = new HashSet(MemberDeathDetectorImpl.suspectedMembers.values());
         Iterator itr = suspects.iterator();

         while(true) {
            SuspectedMemberInfo suspect;
            do {
               if (!itr.hasNext()) {
                  return;
               }

               suspect = (SuspectedMemberInfo)itr.next();
            } while(System.currentTimeMillis() <= suspect.getSuspectedStartTime() + MemberDeathDetectorImpl.SUSPECT_TIMEOUT_INTERVAL);

            try {
               if (MemberDeathDetectorImpl.DEBUG) {
                  MemberDeathDetectorImpl.debug(suspect.getServerName() + " has been marked suspect for more than " + MemberDeathDetectorImpl.SUSPECT_TIMEOUT_INTERVAL / 60000L + " minutes. Removing it from suspect list.");
               }

               MemberDeathDetectorImpl.suspectedMembers.remove(suspect);
            } catch (Exception var6) {
               if (MemberDeathDetectorImpl.DEBUG) {
                  var6.printStackTrace();
               }
            }
         }
      }

      // $FF: synthetic method
      SuspectedMemberListMonitor(Object x1) {
         this();
      }
   }

   private class HeartbeatTimer implements TimerListener {
      private final RMIClusterMessageSenderImpl messageSender = (RMIClusterMessageSenderImpl)ClusterMessageFactory.getInstance().getOneWayMessageSender();
      private final String localServerName = MemberDeathDetectorImpl.this.getLocalServerName();

      HeartbeatTimer() {
      }

      public void timerExpired(Timer timer) {
         Set remoteMembers = new HashSet(MemberDeathDetectorImpl.members.values());
         remoteMembers.addAll(MemberDeathDetectorImpl.suspectedMembers.values());
         Iterator itr = remoteMembers.iterator();

         while(itr.hasNext()) {
            SuspectedMemberInfo info = null;

            try {
               info = (SuspectedMemberInfo)itr.next();
               if (!this.localServerName.equals(info.getServerName())) {
                  this.messageSender.send(MemberDeathDetectorImpl.HEARTBEAT_REQUEST, info.getServerInformation().getServerName(), 1000);
               }
            } catch (RemoteException var6) {
               if (MemberDeathDetectorImpl.DEBUG) {
                  MemberDeathDetectorImpl.debug(var6.getMessage());
               }

               if (info != null) {
                  MemberDeathDetectorImpl.this.onMessageDeliveryFailure(info.getServerInformation().getServerName(), var6);
               }
            }
         }

      }
   }
}
