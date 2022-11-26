package weblogic.cluster.migration;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.migration.management.MigratableServiceCoordinatorRuntime;
import weblogic.cluster.singleton.LeasingException;
import weblogic.cluster.singleton.MigratableServerService;
import weblogic.cluster.singleton.MigrationDebugLogger;
import weblogic.cluster.singleton.SingletonMonitorRemote;
import weblogic.jndi.Environment;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitor;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.DisconnectMonitorUnavailableException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.store.PersistentStoreException;

@Service
public class JTAMigrationHandlerImpl implements JTAMigrationHandler {
   private static int SINGLETON_MASTER_RETRY_COUNT;
   static final String SINGLETON_MASTER_RTERY_COUNT_PROP = "weblogic.cluster.jta.SingletonMasterRetryCount";
   private static RemoteMigratableServiceCoordinator remoteMigratableServiceCoordinator;
   private static final boolean DEBUG = MigrationDebugLogger.isDebugEnabled();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   @PostConstruct
   public void postConstruct() {
      setSingletonMasterRetryCount();
   }

   private static void deactivateJTA(JTAMigratableTargetMBean target, String server) throws MigrationException {
      if (isTargetAutoMigratable(target)) {
         try {
            SingletonMonitorRemote smr = MigratableServerService.theOne().getSingletonMasterRemote(SINGLETON_MASTER_RETRY_COUNT);
            if (smr == null) {
               throw new MigrationException("Could not deactivate JTA, the singleton monitor is unreachable.");
            } else {
               smr.deactivateJTA(server, server);
            }
         } catch (Exception var3) {
            throw new MigrationException("Could not deactivate JTA: " + var3, var3);
         }
      } else {
         if (remoteMigratableServiceCoordinator == null) {
            initializeRemoteMigratableServiceCoordinator();
         }

         try {
            remoteMigratableServiceCoordinator.deactivateJTA(server, server);
         } catch (RemoteException var4) {
            throw new MigrationException("Could not deactivate JTA: " + var4, var4);
         }
      }
   }

   public void deactivateJTA(String target, String server) throws MigrationException {
      deactivateJTA(getJTAMigratableTarget(target), server);
   }

   public void migrateJTA(String migratableName, String serverName, boolean sourceUp, boolean destinationUp) throws MigrationException {
      MigratableTargetMBean target = getJTAMigratableTarget(migratableName);
      if (isTargetAutoMigratable(target)) {
         if (!destinationUp) {
            this.deactivateJTA(migratableName, serverName);
         }

         try {
            SingletonMonitorRemote smr = MigratableServerService.theOne().getSingletonMasterRemote();
            if (!smr.migrate(migratableName, serverName)) {
               throw new MigrationException("Migration of " + migratableName + " to " + serverName + " failed.");
            }
         } catch (LeasingException var7) {
            throw new MigrationException("Could not migrate JTA: " + var7, var7);
         } catch (RemoteException var8) {
            throw new MigrationException("Could not migrate JTA: " + var8, var8);
         }
      } else {
         if (remoteMigratableServiceCoordinator == null) {
            initializeRemoteMigratableServiceCoordinator();
         }

         remoteMigratableServiceCoordinator.migrateJTA(migratableName, serverName, sourceUp, destinationUp);
      }

   }

   public static String findJTA(String serverName) throws RemoteException, PersistentStoreException {
      MigratableTargetMBean target = getJTAMigratableTarget(serverName);
      if (isTargetAutoMigratable(target)) {
         try {
            SingletonMonitorRemote smr = MigratableServerService.theOne().getSingletonMasterRemote();
            return smr.findServiceLocation(serverName);
         } catch (LeasingException var3) {
            throw new RemoteException("Could not find JTA: " + var3);
         }
      } else {
         if (remoteMigratableServiceCoordinator == null) {
            initializeRemoteMigratableServiceCoordinator();
         }

         return remoteMigratableServiceCoordinator.getCurrentLocationOfJTA(serverName);
      }
   }

   public boolean isAvailable(String serviceName) {
      MigratableTargetMBean target = getJTAMigratableTarget(serviceName);
      if (isTargetAutoMigratable(target)) {
         try {
            SingletonMonitorRemote smr = MigratableServerService.theOne().getSingletonMasterRemote();
            return smr != null;
         } catch (LeasingException var4) {
            return false;
         }
      } else {
         try {
            if (remoteMigratableServiceCoordinator == null) {
               initializeRemoteMigratableServiceCoordinator();
            }

            return true;
         } catch (MigrationException var5) {
            return false;
         }
      }
   }

   private static JTAMigratableTargetMBean getJTAMigratableTarget(String name) {
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(name);
      if (server == null) {
         throw new MigrationException("No server found named " + name);
      } else {
         JTAMigratableTargetMBean bean = server.getJTAMigratableTarget();
         if (bean == null) {
            throw new MigrationException("No JTA migratable target found on " + name);
         } else {
            return bean;
         }
      }
   }

   private static boolean isTargetAutoMigratable(MigratableTargetMBean target) throws MigrationException {
      return !target.getMigrationPolicy().equals("manual");
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private static void initializeRemoteMigratableServiceCoordinator() {
      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();

      String url;
      try {
         url = getURLManagerService().findAdministrationURL(domainMBean.getAdminServerName());
      } catch (UnknownHostException var6) {
         throw new MigrationException("Cannot contact the administration server to deactivate JTA.", var6);
      }

      Environment env = new Environment();
      env.setProviderUrl(url);
      if (domainMBean.getAdminServerName().equals(ManagementService.getRuntimeAccess(kernelId).getServer().getName())) {
         ServerRuntimeMBean srmb = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
         if (2 != srmb.getStateVal()) {
            throw new MigrationException("Admin server is not available");
         } else {
            remoteMigratableServiceCoordinator = MigratableServiceCoordinatorRuntime.remoteCoordinator;
         }
      } else {
         try {
            Context ctx = env.getInitialContext();
            remoteMigratableServiceCoordinator = (RemoteMigratableServiceCoordinator)ctx.lookup("weblogic.cluster.migration.migratableServiceCoordinator");
            if (remoteMigratableServiceCoordinator != null) {
               DisconnectMonitor dm = DisconnectMonitorListImpl.getDisconnectMonitor();

               try {
                  dm.addDisconnectListener(remoteMigratableServiceCoordinator, new MigratableServiceCoordinatorDisconnectListener());
                  return;
               } catch (DisconnectMonitorUnavailableException var7) {
               } catch (Exception var8) {
                  if (DEBUG) {
                     p("Unexpected exception while getting RemoteMigratableServiceCoordinator", var8);
                  }
               }
            }
         } catch (NamingException var9) {
            if (DEBUG) {
               p("Unexpected exception while getting RemoteMigratableServiceCoordinator", var9);
            }
         }

         throw new MigrationException("Cannot contact the administration server to deactivate JTA.");
      }
   }

   private static void p(String s, Exception e) {
      MigrationDebugLogger.debug("<JTAMigrationHandlerImpl> " + s, e);
   }

   private static void setSingletonMasterRetryCount() {
      try {
         String count = System.getProperty("weblogic.cluster.jta.SingletonMasterRetryCount", "20");
         SINGLETON_MASTER_RETRY_COUNT = Integer.parseInt(count);
      } catch (Exception var1) {
         SINGLETON_MASTER_RETRY_COUNT = 20;
      }

   }

   private static final class MigratableServiceCoordinatorDisconnectListener implements DisconnectListener {
      private MigratableServiceCoordinatorDisconnectListener() {
      }

      public void onDisconnect(DisconnectEvent reason) {
         JTAMigrationHandlerImpl.remoteMigratableServiceCoordinator = null;
      }

      // $FF: synthetic method
      MigratableServiceCoordinatorDisconnectListener(Object x0) {
         this();
      }
   }
}
