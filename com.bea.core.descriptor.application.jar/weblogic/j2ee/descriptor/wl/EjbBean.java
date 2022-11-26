package weblogic.j2ee.descriptor.wl;

public interface EjbBean {
   ApplicationEntityCacheBean[] getEntityCaches();

   ApplicationEntityCacheBean createEntityCache();

   void destroyEntityCache(ApplicationEntityCacheBean var1);

   boolean isStartMdbsWithApplication();

   void setStartMdbsWithApplication(boolean var1);
}
