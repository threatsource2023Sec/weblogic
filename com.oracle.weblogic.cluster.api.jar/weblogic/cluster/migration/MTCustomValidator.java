package weblogic.cluster.migration;

import java.io.File;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;

public final class MTCustomValidator {
   private static final MigratableTargetValidator validator = MigratableTargetValidator.Locator.locate();

   public static void validateMigratableTarget(MigratableTargetMBean migratableTarget) {
      validator.validateTarget(migratableTarget);
   }

   public static void destroyMigratableTarget(MigratableTargetMBean mt) {
      validator.destroyMigratableTarget(mt);
   }

   public static void destroyServer(ServerMBean server) {
      validator.destroyServer(server);
   }

   public static void destroyCluster(ClusterMBean cluster) {
      validator.destroyCluster(cluster);
   }

   public static void removeConstrainedCandidateServer(MigratableTargetMBean mt, ServerMBean server) {
      validator.removeConstrainedCandidateServer(mt, server);
   }

   public static void canSetCluster(MigratableTargetMBean mt, ClusterMBean cluster) {
      validator.canSetCluster(mt, cluster);
   }

   public static void validateMigrationPolicy(MigratableTargetMBean mt, String value) {
      validator.validateMigrationPolicy(mt, value);
   }

   public static void validateCritical(MigratableTargetMBean mt, boolean value) {
      validator.validateCritical(mt, value);
   }

   public static void validateScriptPath(String value) {
      if (value != null && !value.isEmpty()) {
         File f = new File(value);
         if (f.isAbsolute()) {
            throw new IllegalArgumentException("This value must specify a path relative to the service_migration directory in your domain." + value);
         } else if (value.indexOf("..") > -1) {
            throw new IllegalArgumentException("This value must specify a path relative to the service_migration directory in your domain." + value);
         }
      }
   }
}
