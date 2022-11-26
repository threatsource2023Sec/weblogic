package weblogic.transaction.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.migration.Migratable;
import weblogic.cluster.migration.MigrationException;
import weblogic.cluster.migration.MigrationManager;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class TransactionRecoveryNoOpService extends AbstractServerService implements Migratable {
   @Inject
   @Named("MigrationManagerService")
   private MigrationManager migrationService;
   @Inject
   @Named("ClusterServiceActivator")
   private ServerService dependencyOnClusterServiceActivator;
   private static final boolean DEBUG = false;

   public void start() throws ServiceFailureException {
      if (TransactionRecoveryService.isInCluster()) {
         JTAMigratableTargetMBean jtaMT = TransactionRecoveryService.getLocalServer().getJTAMigratableTarget();
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("Register no-op migratable on JTAMT [" + TransactionRecoveryService.getLocalServerName() + "] ...");
         }

         try {
            this.migrationService.register(this, jtaMT);
         } catch (MigrationException var3) {
            throw new ServiceFailureException("Error occurred while registering Transaction Recovery No-Op Service.", var3);
         }
      }
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      if (TransactionRecoveryService.isInCluster()) {
         JTAMigratableTargetMBean jtaMT = TransactionRecoveryService.getLocalServer().getJTAMigratableTarget();
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("UnRegister no-op migratable on JTAMT [" + TransactionRecoveryService.getLocalServerName() + "] ...");
         }

         try {
            this.migrationService.unregister(this, jtaMT);
         } catch (MigrationException var3) {
            throw new ServiceFailureException("Error occurred while unregistering Transaction Recovery No-Op Service.", var3);
         }
      }
   }

   public int getOrder() {
      return -901;
   }

   public void migratableActivate() throws MigrationException {
   }

   public void migratableDeactivate() throws MigrationException {
   }

   public void migratableInitialize() {
   }
}
