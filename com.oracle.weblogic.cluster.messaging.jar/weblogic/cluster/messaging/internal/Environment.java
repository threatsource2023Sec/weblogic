package weblogic.cluster.messaging.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.util.Timer;
import java.util.TimerTask;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class Environment {
   public static final boolean HTTP_PING = initProperty("weblogic.unicast.HttpPing");
   public static final boolean DEBUG = initProperty("weblogic.debug.DebugUnicastMessaging");
   private static ConfiguredServersMonitor configuration;
   private static ConnectionManager connectionManager;
   private static LogService logService;
   private static PropertyService propertyService;
   private static WorkManager dispatchWM;
   private static WorkManager forwardingWM;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private static boolean initProperty(String name) {
      try {
         return Boolean.getBoolean(name);
      } catch (SecurityException var2) {
         return false;
      }
   }

   public static synchronized void initialize(ConfiguredServersMonitor config, ConnectionManager mgr) {
      setLogService(LogServiceImpl.getInstance());
      initialize(config, mgr, PropertyServiceImpl.getInstance());
   }

   public static synchronized boolean isInitialized() {
      return configuration != null;
   }

   public static synchronized void initialize(ConfiguredServersMonitor config, ConnectionManager mgr, PropertyService property) {
      if (configuration != null) {
         throw new AssertionError("double initialization of unicast messaging!");
      } else {
         configuration = config;
         connectionManager = mgr;
         propertyService = property;
         ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
         if (cluster.isMessageOrderingEnabled()) {
            dispatchWM = WorkManagerFactory.getInstance().findOrCreate("weblogic.unicast.DispatchWorkManager", 1, 1);
            forwardingWM = WorkManagerFactory.getInstance().findOrCreate("weblogic.unicast.ForwardingWorkManager", 1, 1);
         } else {
            dispatchWM = WorkManagerFactory.getInstance().findOrCreate("weblogic.unicast.DispatchWorkManager", 5, -1);
            forwardingWM = WorkManagerFactory.getInstance().findOrCreate("weblogic.unicast.ForwardingWorkManager", 5, -1);
         }

      }
   }

   public static void setLogService(LogService logger) {
      logService = logger;
   }

   public static ConfiguredServersMonitor getConfiguredServersMonitor() {
      return configuration;
   }

   public static void executeForwardMessage(Runnable runnable) {
      forwardingWM.schedule(runnable);
   }

   public static void executeDispatchMessage(Runnable runnable) {
      dispatchWM.schedule(runnable);
   }

   public static Object startTimer(final Runnable timer, int initial, int period) {
      Timer time = new Timer();
      time.schedule(new TimerTask() {
         public void run() {
            timer.run();
         }
      }, (long)initial, (long)period);
      return time;
   }

   public static PingRoutine getPingRoutine() {
      return (PingRoutine)(HTTP_PING ? HttpPingRoutineImpl.getInstance() : PingRoutineImpl.getInstance());
   }

   public static void stopTimer(Object handle) {
      ((Timer)handle).cancel();
   }

   public static ConnectionManager getConnectionManager() {
      return connectionManager;
   }

   public static GroupManager getGroupManager() {
      return GroupManagerImpl.getInstance();
   }

   public static LogService getLogService() {
      return logService;
   }

   public static PropertyService getPropertyService() {
      return propertyService;
   }

   public static InetAddress getInetAddressFromHostname(String hostname) {
      try {
         if (hostname != null) {
            return InetAddress.getByName(hostname);
         }
      } catch (UnknownHostException var3) {
         ClusterExtensionLogger.logFailedToResolveHostname(hostname);
      }

      try {
         return InetAddress.getLocalHost();
      } catch (UnknownHostException var2) {
         ClusterExtensionLogger.logFailedToResolveHostname("localhost");
         return null;
      }
   }
}
