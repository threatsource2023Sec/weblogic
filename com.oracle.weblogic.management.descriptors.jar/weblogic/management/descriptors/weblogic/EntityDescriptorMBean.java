package weblogic.management.descriptors.weblogic;

public interface EntityDescriptorMBean extends WeblogicBeanDescriptorMBean {
   PoolMBean getPool();

   void setPool(PoolMBean var1);

   EntityCacheMBean getEntityCache();

   void setEntityCache(EntityCacheMBean var1);

   EntityCacheRefMBean getEntityCacheRef();

   void setEntityCacheRef(EntityCacheRefMBean var1);

   PersistenceMBean getPersistence();

   void setPersistence(PersistenceMBean var1);

   EntityClusteringMBean getEntityClustering();

   void setEntityClustering(EntityClusteringMBean var1);

   InvalidationTargetMBean getInvalidationTarget();

   void setInvalidationTarget(InvalidationTargetMBean var1);

   boolean isEnableDynamicQueries();

   void setEnableDynamicQueries(boolean var1);
}
