package weblogic.management.runtime;

public interface EntityCacheCurrentStateRuntimeMBean extends EntityCacheRuntimeMBean {
   long getMemoryUsage();

   long getDiskUsage();
}
