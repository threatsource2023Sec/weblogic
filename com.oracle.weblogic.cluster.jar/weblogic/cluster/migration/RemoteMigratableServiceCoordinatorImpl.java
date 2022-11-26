package weblogic.cluster.migration;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.migration.management.MigratableServiceCoordinatorRuntime;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jndi.Environment;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.store.DefaultObjectHandler;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.utils.Debug;

public class RemoteMigratableServiceCoordinatorImpl implements RemoteMigratableServiceCoordinator {
   private MigratableServiceCoordinatorRuntime runtimeMBeanDelegate;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final DebugLogger JTAMigration = DebugLogger.getDebugLogger("DebugJTAMigration");

   public RemoteMigratableServiceCoordinatorImpl(MigratableServiceCoordinatorRuntime runtime) throws NamingException {
      this.runtimeMBeanDelegate = runtime;
      Context ctx = this.getInitialContext();
      ctx.bind("weblogic.cluster.migration.migratableServiceCoordinator", this);
   }

   public void migrateJTA(String migratableName, String serverName, boolean sourceUp, boolean destinationUp) throws MigrationException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      MigratableTargetMBean migratableTarget = domain.lookupServer(migratableName).getJTAMigratableTarget();
      ServerMBean destination = domain.lookupServer(serverName);
      if (JTAMigration.isDebugEnabled()) {
         JTAMigration.debug("RemoteMigratableServiceCoordinatorImpl.migrateJTA() migratableTarget=" + migratableTarget + ",destination=" + destination + ",sourceUp=" + sourceUp + ",destinationUp=" + destinationUp);
      }

      try {
         this.runtimeMBeanDelegate.migrateJTA((MigratableTargetMBean)migratableTarget, (ServerMBean)destination, sourceUp, destinationUp);
      } catch (weblogic.management.runtime.MigrationException var9) {
         throw new MigrationException(var9);
      }
   }

   public void deactivateJTA(String targetName, String hostSrvr) throws RemoteException, MigrationException {
      String currentLocation = null;

      try {
         currentLocation = this.getCurrentLocationOfJTA(targetName);
      } catch (PersistentStoreException var7) {
         throw new MigrationException("Unexpected exception accessing store", var7);
      }

      if (currentLocation != null && !currentLocation.equals(hostSrvr)) {
         Debug.say("Current " + currentLocation + " destination " + hostSrvr);
         RemoteMigrationControl control = this.getRemoteMigrationControl(currentLocation);
         if (control != null) {
            control.deactivateTarget(targetName, hostSrvr);

            try {
               this.getPersistentStoreMap().put(targetName, hostSrvr);
            } catch (PersistentStoreException var6) {
               throw new MigrationException("Failed to contact server " + currentLocation + " hosting '" + targetName + "' to deactivate");
            }
         } else {
            throw new MigrationException("Failed to contact server " + currentLocation + " hosting '" + targetName + "' to deactivate");
         }
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private RemoteMigrationControl getRemoteMigrationControl(String server) {
      Environment env = new Environment();
      Context ctx = null;

      RemoteMigrationControl var5;
      try {
         String url = getURLManagerService().findAdministrationURL(server);
         if (url == null) {
            var5 = null;
            return var5;
         }

         env.setProviderUrl(url);
         ctx = env.getInitialContext();
         var5 = (RemoteMigrationControl)ctx.lookup("weblogic.cluster.migrationControl");
         return var5;
      } catch (NamingException var18) {
         var5 = null;
      } catch (UnknownHostException var19) {
         var5 = null;
         return var5;
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var17) {
            }
         }

      }

      return var5;
   }

   private PersistentMap getPersistentStoreMap() throws PersistentStoreException {
      PersistentStore store = PersistentStoreManager.getManager().getDefaultStore();
      String storeName = "weblogic_migratable_services_store";
      if (store == null) {
         throw new PersistentStoreException("No store found");
      } else {
         return store.createPersistentMap(storeName, DefaultObjectHandler.THE_ONE);
      }
   }

   public String getCurrentLocationOfJTA(String targetName) throws PersistentStoreException {
      return (String)this.getPersistentStoreMap().get(targetName);
   }

   public void setCurrentLocation(String targetName, String hostserver) throws PersistentStoreException {
      this.getPersistentStoreMap().put(targetName, hostserver);
   }

   private Context getInitialContext() throws NamingException {
      Environment env = new Environment();
      env.setCreateIntermediateContexts(true);
      return env.getInitialContext();
   }
}
