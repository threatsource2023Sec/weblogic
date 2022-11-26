package weblogic.management.configuration;

public class DynamicServersValidator {
   public static void validateDynamicServers(DynamicServersMBean dynServers) throws IllegalArgumentException {
      if (dynServers != null && dynServers.getMaximumDynamicServerCount() > 0) {
         ServerTemplateMBean template = dynServers.getServerTemplate();
         if (template == null) {
            throw new IllegalArgumentException("Server template must be set in DynamicServers");
         } else if (template != null && template.getCluster() != null && !dynServers.getParent().getName().equals(template.getCluster().getName())) {
            throw new IllegalArgumentException("Cluster set in Server template must be null or must match cluster of Dynamic Server");
         } else if (dynServers.getMinDynamicClusterSize() > dynServers.getDynamicClusterSize()) {
            throw new IllegalArgumentException("MinDynamicClusterSize cannot be greater than DynamicClusterSize");
         } else if (dynServers.getMaxDynamicClusterSize() < dynServers.getDynamicClusterSize()) {
            throw new IllegalArgumentException("MaxDynamicClusterSize cannot be smaller than DynamicClusterSize");
         }
      }
   }
}
