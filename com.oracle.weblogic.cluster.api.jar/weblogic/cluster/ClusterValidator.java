package weblogic.cluster;

import java.lang.annotation.Annotation;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.server.GlobalServiceLocator;

/** @deprecated */
@Deprecated
public final class ClusterValidator {
   private static ClusterConfigurationValidator validator;

   public static void validateMulticastAddress(String address) {
      loadValidator();
      validator.validateMulticastAddress(address);
   }

   public static void validateAutoMigration(boolean enabled) {
   }

   public static void canSetCluster(ServerMBean server, ClusterMBean cluster) {
      loadValidator();
      validator.canSetCluster(server, cluster);
   }

   public static void validateUnicastCluster(ServerTemplateMBean server, ClusterMBean cluster) {
      loadValidator();
      validator.validateUnicastCluster(server, cluster);
   }

   private static synchronized void loadValidator() {
      if (validator == null) {
         validator = (ClusterConfigurationValidator)GlobalServiceLocator.getServiceLocator().getService(ClusterConfigurationValidator.class, new Annotation[0]);
      }

   }
}
