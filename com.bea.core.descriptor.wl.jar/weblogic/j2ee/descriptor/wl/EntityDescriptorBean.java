package weblogic.j2ee.descriptor.wl;

public interface EntityDescriptorBean {
   PoolBean getPool();

   boolean isPoolSet();

   TimerDescriptorBean getTimerDescriptor();

   boolean isTimerDescriptorSet();

   EntityCacheBean getEntityCache();

   boolean isEntityCacheSet();

   EntityCacheRefBean getEntityCacheRef();

   boolean isEntityCacheRefSet();

   PersistenceBean getPersistence();

   boolean isPersistenceSet();

   EntityClusteringBean getEntityClustering();

   boolean isEntityClusteringSet();

   InvalidationTargetBean getInvalidationTarget();

   boolean isInvalidationTargetSet();

   boolean isEnableDynamicQueries();

   void setEnableDynamicQueries(boolean var1);

   String getId();

   void setId(String var1);
}
