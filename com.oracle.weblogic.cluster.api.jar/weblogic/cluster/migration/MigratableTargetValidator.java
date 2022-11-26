package weblogic.cluster.migration;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface MigratableTargetValidator {
   void validateTarget(MigratableTargetMBean var1) throws IllegalArgumentException;

   void validateMigrationPolicy(MigratableTargetMBean var1, String var2) throws IllegalArgumentException;

   void validateCritical(MigratableTargetMBean var1, boolean var2) throws IllegalArgumentException;

   void canSetCluster(MigratableTargetMBean var1, ClusterMBean var2) throws IllegalArgumentException;

   void removeConstrainedCandidateServer(MigratableTargetMBean var1, ServerMBean var2) throws IllegalArgumentException;

   void destroyMigratableTarget(MigratableTargetMBean var1) throws IllegalArgumentException;

   void destroyServer(ServerMBean var1) throws IllegalArgumentException;

   void destroyCluster(ClusterMBean var1) throws IllegalArgumentException;

   public static class Locator {
      public static MigratableTargetValidator locate() {
         return (MigratableTargetValidator)GlobalServiceLocator.getServiceLocator().getService(MigratableTargetValidator.class, new Annotation[0]);
      }
   }
}
