package weblogic.management.configuration;

public class LifecycleManagerConfigValidator {
   public static void validateConfig(LifecycleManagerConfigMBean config) throws IllegalArgumentException {
      if (config != null) {
         if ("cluster".equals(config.getDeploymentType())) {
            TargetMBean target = config.getTarget();
            if (target == null) {
               throw new IllegalArgumentException("A cluster target must be specified for an HA cluster deployment type");
            }

            if (!(target instanceof ClusterMBean)) {
               throw new IllegalArgumentException("A cluster target must be specified for an HA cluster deployment type");
            }

            String datasource = config.getDataSourceName();
            if (datasource == null || datasource.length() == 0) {
               throw new IllegalArgumentException("A datasource name must be specified for an HA cluster deployment type");
            }
         }

         LifecycleManagerEndPointMBean[] endPoints = config.getConfiguredEndPoints();
         if (endPoints != null && endPoints.length > 1) {
            throw new IllegalArgumentException("Only one endpoint can be explicitly configured for Lifecycle Manager.");
         } else {
            DomainMBean domain = (DomainMBean)((DomainMBean)config.getParent());
            String adminServerName = domain.getAdminServerName();
            if (endPoints != null) {
               LifecycleManagerEndPointMBean[] var4 = endPoints;
               int var5 = endPoints.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  LifecycleManagerEndPointMBean endPoint = var4[var6];
                  if (adminServerName.equals(endPoint.getName())) {
                     throw new IllegalArgumentException("An endpoint was configured in Lifecycle Manager with the name of the local admin server. This name is reserved for use by the local admin server configuration.");
                  }
               }
            }

         }
      }
   }
}
