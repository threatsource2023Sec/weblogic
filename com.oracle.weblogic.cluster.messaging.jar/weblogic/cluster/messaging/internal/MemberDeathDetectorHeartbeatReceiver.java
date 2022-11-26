package weblogic.cluster.messaging.internal;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.singleton.MemberDeathDetectorHeartbeatReceiverIntf;
import weblogic.cluster.singleton.MemberLivenessDetector;
import weblogic.health.HealthMonitorService;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

@Service
public class MemberDeathDetectorHeartbeatReceiver implements MemberLivenessDetector, MemberDeathDetectorHeartbeatReceiverIntf, PingMessageListener {
   private static final DebugCategory debugDisconnectMonitor = Debug.getCategory("weblogic.cluster.leasing.DisconnectMonitor");
   private static final boolean DEBUG = debugEnabled();
   private long lastHeartbeatReceived;
   private SuspectedMemberInfo leader;
   private long healthCheckInterval;
   private boolean timerStarted = false;
   private Timer heartbeatMonitorTimer;
   @Inject
   private RuntimeAccess runtimeAccess;

   @PostConstruct
   public void postConstruct() {
      ServerMBean localServer = this.runtimeAccess.getServer();
      ClusterMBean cluster = localServer.getCluster();
      this.healthCheckInterval = (long)cluster.getHealthCheckIntervalMillis();
   }

   public synchronized void pingReceived(ServerInformation server) {
      if (this.leader == null || !server.getServerName().equals(this.leader.getServerName())) {
         ClusterMemberInfo leaderInfo = null;
         ClusterServices clusterServices = (ClusterServices)GlobalServiceLocator.getServiceLocator().getService(ClusterServices.class, new Annotation[0]);
         Collection remoteMembers = clusterServices.getAllRemoteMembers();
         Iterator itr = remoteMembers.iterator();

         while(itr.hasNext()) {
            leaderInfo = (ClusterMemberInfo)itr.next();
            if (leaderInfo.serverName().equals(server.getServerName())) {
               break;
            }
         }

         if (leaderInfo != null) {
            this.leader = new SuspectedMemberInfoImpl(leaderInfo);
         }
      }

      this.lastHeartbeatReceived = System.currentTimeMillis();
      if (!this.timerStarted) {
         this.timerStarted = true;
         debug("Starting Heartbeat Monitor");
         this.heartbeatMonitorTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new HeartbeatMonitor(), this.healthCheckInterval, this.healthCheckInterval);
      }
   }

   public void startDetector() throws ServiceFailureException {
      try {
         if (DEBUG) {
            debug("Starting Member Death Detector HeartbeatReceiver ");
         }

         this.enableHeartbeatReceiver();
      } catch (RemoteException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   public void stopDetector() throws ServiceFailureException {
      this.halt();
   }

   public void stop() {
      this.halt();
   }

   private void halt() {
      try {
         if (this.heartbeatMonitorTimer != null) {
            this.heartbeatMonitorTimer.cancel();
         }
      } finally {
         this.timerStarted = false;
      }

      if (DEBUG) {
         debug("Halted Member Death Detector HeartbeatReceiver");
      }

   }

   public boolean isStarted() {
      return this.timerStarted;
   }

   public long getCheckInterval() {
      return this.healthCheckInterval;
   }

   public long getHealthCheckInterval() {
      return this.healthCheckInterval;
   }

   static void fatalError(String reason) {
      debug(" fatalError: " + reason);
      HealthMonitorService.subsystemFailed("MemberDeathDetectorHeartbeatReceiver", reason);
   }

   private static void debug(String s) {
      System.out.println("[MemberDeathDetectorHeartbeatReceiver] " + s);
   }

   private static boolean debugEnabled() {
      return debugDisconnectMonitor.isEnabled();
   }

   private void enableHeartbeatReceiver() throws RemoteException {
      RMIClusterMessageEndPointImpl endpoint = RMIClusterMessageEndPointImpl.getInstance();
      endpoint.registerPingMessageListener(this);
      ServerHelper.exportObject(endpoint);
   }

   private class HeartbeatMonitor implements TimerListener {
      private HeartbeatMonitor() {
      }

      public void timerExpired(Timer timer) {
         if (System.currentTimeMillis() > MemberDeathDetectorHeartbeatReceiver.this.lastHeartbeatReceived + MemberDeathDetectorHeartbeatReceiver.this.healthCheckInterval) {
            ProbeContext context = new ProbeContextImpl(MemberDeathDetectorHeartbeatReceiver.this.leader);
            ProbeManager.getClusterMemberProbeManager().invoke(context);
            if (context.getResult() == 1 || context.getResult() == 0) {
               return;
            }

            MemberDeathDetectorHeartbeatReceiver.fatalError(context.getMessage());
         }

      }

      // $FF: synthetic method
      HeartbeatMonitor(Object x1) {
         this();
      }
   }
}
