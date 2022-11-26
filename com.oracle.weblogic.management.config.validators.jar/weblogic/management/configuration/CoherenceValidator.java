package weblogic.management.configuration;

public class CoherenceValidator {
   public static void validateCoherenceCluster(DomainMBean domain) throws IllegalArgumentException {
      validateCoherenceManagementCluster(domain);
   }

   private static void validateCoherenceManagementCluster(DomainMBean domain) throws IllegalArgumentException {
      CoherenceManagementClusterMBean[] cohMgmtClusters = domain.getCoherenceManagementClusters();
      CoherenceManagementClusterMBean[] var2 = cohMgmtClusters;
      int var3 = cohMgmtClusters.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         CoherenceManagementClusterMBean cluster = var2[var4];
         if (cluster.getReportGroupFile() == null) {
            throw new IllegalArgumentException("All CoherenceManagementClusterMBeans must have a reportGroupFile");
         }

         CoherenceManagementAddressProviderMBean[] aProviders = cluster.getCoherenceManagementAddressProviders();
         if (aProviders == null || aProviders.length == 0) {
            throw new IllegalArgumentException("Addresses needs to be provided for CoherenceManagementClusterMBean " + cluster.getName());
         }
      }

   }

   public static void validateCoherencePartitionCacheConfiguration(CoherencePartitionCacheConfigMBean cacheConfigMBean) throws IllegalArgumentException {
      if (cacheConfigMBean.isShared()) {
         CoherencePartitionCachePropertyMBean[] cachePropertyMBeans = cacheConfigMBean.getCoherencePartitionCacheProperties();
         if (cachePropertyMBeans != null && cachePropertyMBeans.length > 0) {
            throw new IllegalArgumentException("Coherence Cache Properties cannot be provided if the cache is shared");
         }
      }

      if (cacheConfigMBean.getApplicationName() == null) {
         throw new IllegalArgumentException("Application Name must be provided");
      } else {
         String thisCacheName = cacheConfigMBean.getCacheName();
         String thisApplicationName = cacheConfigMBean.getApplicationName();
         PartitionMBean partitionMBean = (PartitionMBean)cacheConfigMBean.getParentBean();
         if (partitionMBean != null) {
            int c = 0;
            CoherencePartitionCacheConfigMBean[] var5 = partitionMBean.getCoherencePartitionCacheConfigs();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               CoherencePartitionCacheConfigMBean beans = var5[var7];
               if (beans.getCacheName().equals(thisCacheName) && beans.getApplicationName().equals(thisApplicationName)) {
                  ++c;
               }
            }

            if (c > 1) {
               throw new IllegalArgumentException("Cache Name and Application Name must be unique for a given domain partition");
            }
         }

      }
   }
}
