package weblogic.management.runtime;

import weblogic.cluster.singleton.MigratorInterface;
import weblogic.management.ManagementException;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;

public interface MigratableServiceCoordinatorRuntimeMBean extends RuntimeMBean, MigratorInterface {
   void migrate(MigratableTargetMBean var1, ServerMBean var2) throws MigrationException;

   void migrateSingleton(SingletonServiceMBean var1, ServerMBean var2) throws MigrationException;

   void migrate(MigratableTargetMBean var1, ServerMBean var2, boolean var3, boolean var4) throws MigrationException;

   void migrateJTA(MigratableTargetMBean var1, ServerMBean var2, boolean var3, boolean var4) throws MigrationException;

   MigrationTaskRuntimeMBean startMigrateTask(MigratableTargetMBean var1, ServerMBean var2, boolean var3) throws ManagementException;

   MigrationTaskRuntimeMBean startMigrateTask(MigratableTargetMBean var1, ServerMBean var2, boolean var3, boolean var4, boolean var5) throws ManagementException;

   void deactivateJTATarget(MigratableTargetMBean var1, String var2) throws MigrationException;

   void clearOldMigrationTaskRuntimes();
}
