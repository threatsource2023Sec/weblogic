package weblogic.cluster.leasing.databaseless;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.ClusterService;
import weblogic.cluster.messaging.internal.BaseClusterMessage;
import weblogic.cluster.messaging.internal.ClusterMessage;
import weblogic.cluster.messaging.internal.ClusterMessageEndPoint;
import weblogic.cluster.messaging.internal.ClusterMessageFactory;
import weblogic.cluster.messaging.internal.ClusterMessageSender;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.MachineState;
import weblogic.cluster.messaging.internal.MessageDeliveryFailureListener;
import weblogic.cluster.messaging.internal.RMIClusterMessageEndPointImpl;
import weblogic.cluster.messaging.internal.SRMResult;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.cluster.messaging.internal.ServerInformationImpl;
import weblogic.jndi.Environment;
import weblogic.management.configuration.DatabaseLessLeasingBasisMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.DisconnectMonitorUnavailableException;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

public final class ServerFailureDetectorImpl implements ServerFailureDetector, DisconnectListener, MessageDeliveryFailureListener, ClusterMembersChangeListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ClusterMessage PING_REQUEST = new BaseClusterMessage(createLocalServerInformation(), 9);
   private static final int PING_PERIOD = 10000;
   private static final int NODEMANAGER_QUERY_DELAY = 3000;
   private static final DebugCategory debugFailureDetector = Debug.getCategory("weblogic.cluster.leasing.FailureDetector");
   private static final boolean DEBUG = debugEnabled();
   private ClusterMessageEndPoint remote;
   private ServerInformation serverInfo;
   private ServerFailureListener listener;
   private boolean nmRetryTimerRunning;
   private boolean stopped;
   private Timer pingTimer;
   private long lastStateCheckTime;

   public synchronized void start(ServerInformation server, ServerFailureListener listener) {
      if (DEBUG) {
         debug("--- starting server failure detector for " + server.getServerName());
      }

      this.serverInfo = server;
      this.listener = listener;
      this.stopped = false;
      this.nmRetryTimerRunning = false;
      this.pingTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new PingTimer(), 10000L, 10000L);
      this.registerForDisconnect();
      ClusterMessageFactory.getInstance().registerMessageDeliveryFailureListener(this);
      ClusterService.getClusterServiceInternal().addClusterMembersListener(this);
   }

   private static ServerInformation createLocalServerInformation() {
      return new ServerInformationImpl(ClusterService.getClusterServiceInternal().getLocalMember());
   }

   public synchronized boolean stop() {
      if (this.stopped) {
         return false;
      } else {
         this.stopped = true;
         this.pingTimer.cancel();
         ClusterMessageFactory.getInstance().removeMessageDeliveryFailureListener(this);
         ClusterService.getClusterServiceInternal().removeClusterMembersListener(this);

         try {
            DisconnectMonitorListImpl.getDisconnectMonitor().removeDisconnectListener(this.remote, this);
         } catch (DisconnectMonitorUnavailableException var2) {
            if (DEBUG) {
               debug("unable to stop !");
               var2.printStackTrace();
            }
         }

         return true;
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private void registerForDisconnect() {
      try {
         DatabaseLessLeasingBasisMBean mbean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getDatabaseLessLeasingBasis();
         Environment env = new Environment();
         env.setProviderUrl(getURLManagerService().findAdministrationURL(this.serverInfo.getServerName()));
         env.setConnectionTimeout((long)mbean.getMessageDeliveryTimeout());
         this.remote = (ClusterMessageEndPoint)PortableRemoteObject.narrow(env.getInitialReference(RMIClusterMessageEndPointImpl.class), ClusterMessageEndPoint.class);
         DisconnectMonitorListImpl.getDisconnectMonitor().addDisconnectListener(this.remote, this);
         if (DEBUG) {
            debug("registered for disconnect events from " + this.serverInfo.getServerName());
         }
      } catch (DisconnectMonitorUnavailableException var3) {
         throw new AssertionError("Unable to register with the leader for disconnects!" + var3);
      } catch (UnknownHostException var4) {
         this.onDisconnect((DisconnectEvent)null);
      } catch (NamingException var5) {
         this.onDisconnect((DisconnectEvent)null);
      }

   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[ServerFailureDetector] " + s);
   }

   public synchronized void onDisconnect(DisconnectEvent event) {
      if (this.stateCheckNeeded(event)) {
         ServerMBean serverMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(this.serverInfo.getServerName());

         assert serverMBean != null;

         MachineState machineState = getState(serverMBean);
         if (!this.isServerDead(machineState)) {
            this.lastStateCheckTime = System.currentTimeMillis();
         } else {
            long fenceTimeout = (long)(serverMBean.getCluster().getDatabaseLessLeasingBasis().getFenceTimeout() * 1000);
            if (machineState.isMachineUnavailable() && fenceTimeout > 0L) {
               if (DEBUG) {
                  debug("unable to reach the NodeManager for the server " + serverMBean.getName() + ". Fence timeout is " + fenceTimeout + "ms");
               }

               this.nmRetryTimerRunning = true;
               TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new NMRetryTimer(), fenceTimeout);
            } else {
               this.invokeListener(machineState);
            }

         }
      }
   }

   private void invokeListener(MachineState machineState) {
      if (DEBUG) {
         debug("Invoking onServerFailure() for " + this.serverInfo.getServerName());
      }

      if (this.stop()) {
         WorkManagerFactory.getInstance().getSystem().schedule(new ListenerRunnable(this.listener, this.serverInfo, machineState));
      }

   }

   private boolean stateCheckNeeded(DisconnectEvent event) {
      if (!this.stopped && !this.nmRetryTimerRunning) {
         String nmType = this.getConfiguredMachine().getNodeManager().getNMType();
         if (!"plain".equalsIgnoreCase(nmType) && !"ssl".equalsIgnoreCase(nmType)) {
            return event != null || System.currentTimeMillis() - this.lastStateCheckTime >= 3000L;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private static MachineState getState(ServerMBean serverMBean) {
      SRMResult srmResult = EnvironmentFactory.getServerReachabilityMajorityService().getLastSRMResult();
      MachineMBean machine;
      if (srmResult != null) {
         String machineName = srmResult.getCurrentMachine(serverMBean.getName());
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         if (DEBUG) {
            ClusterLogger.logDebug("SRM returned " + machineName + " for " + serverMBean.getName());
         }

         machine = domain.lookupMachine(machineName);
      } else {
         if (DEBUG) {
            debug("unable to get machine for " + serverMBean.getName());
         }

         machine = serverMBean.getMachine();
      }

      MachineState machineState = MachineState.getMachineState(machine, true);
      if (DEBUG) {
         debug("Machine state for " + serverMBean.getName() + " is " + machineState);
      }

      return machineState;
   }

   private boolean isServerDead(MachineState machineState) {
      if (machineState.isMachineUnavailable()) {
         return true;
      } else {
         String serverState = machineState.getServerState(this.serverInfo.getServerName());
         if (DEBUG) {
            debug("serverstate for " + this.serverInfo.getServerName() + " is " + serverState);
         }

         return serverState != null && !serverState.equals("RUNNING") && !serverState.equals("ADMIN") && !serverState.equals("SUSPENDING") && !serverState.equals("FORCE_SUSPENDING") && !serverState.equals("RESUMING");
      }
   }

   public void onMessageDeliveryFailure(String serverName, RemoteException re) {
      if (DEBUG) {
         debug("received onMessageDeliveryFailure for " + serverName + " due to " + StackTraceUtils.throwable2StackTrace(re));
      }

      if (this.serverInfo.getServerName().equals(serverName)) {
         if (DEBUG) {
            debug("calling onDisconnect due to onMessageDeliveryFailure!");
         }

         this.onDisconnect((DisconnectEvent)null);
      }

   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      if (this.serverInfo.getServerName().equals(cece.getClusterMemberInfo().serverName()) && cece.getAction() == 1) {
         this.onDisconnect((DisconnectEvent)null);
      }

   }

   private MachineMBean getConfiguredMachine() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(this.serverInfo.getServerName()).getMachine();
   }

   private static boolean debugEnabled() {
      return debugFailureDetector.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private class NMRetryTimer implements TimerListener {
      private NMRetryTimer() {
      }

      public void timerExpired(Timer timer) {
         ServerMBean serverMBean = ManagementService.getRuntimeAccess(ServerFailureDetectorImpl.kernelId).getDomain().lookupServer(ServerFailureDetectorImpl.this.serverInfo.getServerName());

         assert serverMBean != null;

         MachineState machineState = ServerFailureDetectorImpl.getState(serverMBean);
         if (!ServerFailureDetectorImpl.this.stopped && ServerFailureDetectorImpl.this.isServerDead(machineState)) {
            if (ServerFailureDetectorImpl.DEBUG) {
               ServerFailureDetectorImpl.debug("NMRetryTimer invoking onServerFailure() for " + serverMBean.getName());
            }

            ServerFailureDetectorImpl.this.invokeListener(machineState);
         } else {
            ServerFailureDetectorImpl.this.nmRetryTimerRunning = false;
         }
      }

      // $FF: synthetic method
      NMRetryTimer(Object x1) {
         this();
      }
   }

   private static class ListenerRunnable implements Runnable {
      private final ServerFailureListener listener;
      private final ServerInformation serverInfo;
      private final MachineState machineState;

      ListenerRunnable(ServerFailureListener listener, ServerInformation serverInfo, MachineState machineState) {
         this.listener = listener;
         this.serverInfo = serverInfo;
         this.machineState = machineState;
      }

      public void run() {
         if (this.machineState.isMachineUnavailable()) {
            this.listener.onMachineFailure(new MachineFailureEvent() {
               public List getFailedServers() {
                  List failedServers = new ArrayList();
                  List configuredServers = ListenerRunnable.this.machineState.getServerNames();
                  SRMResult lastSRMResult = EnvironmentFactory.getServerReachabilityMajorityService().getLastSRMResult();
                  if (lastSRMResult != null) {
                     String machineName = ListenerRunnable.this.machineState.getMachineName();
                     Iterator itr = configuredServers.iterator();

                     while(itr.hasNext()) {
                        String server = (String)itr.next();
                        if (lastSRMResult.getCurrentMachine(server).equals(machineName)) {
                           ((List)failedServers).add(server);
                        }
                     }
                  } else {
                     failedServers = configuredServers;
                  }

                  return (List)failedServers;
               }

               public String getMachineName() {
                  return ListenerRunnable.this.machineState.getMachineName();
               }
            });
         } else {
            this.listener.onServerFailure(new ServerFailureEvent() {
               public ServerInformation getServerInformation() {
                  return ListenerRunnable.this.serverInfo;
               }
            });
         }

      }
   }

   private class PingTimer implements TimerListener {
      private PingTimer() {
      }

      public void timerExpired(Timer timer) {
         if (ServerFailureDetectorImpl.this.stopped) {
            timer.cancel();
         } else {
            ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();

            try {
               messageSender.send(ServerFailureDetectorImpl.PING_REQUEST, ServerFailureDetectorImpl.this.serverInfo);
            } catch (RemoteException var4) {
            }

         }
      }

      // $FF: synthetic method
      PingTimer(Object x1) {
         this();
      }
   }
}
