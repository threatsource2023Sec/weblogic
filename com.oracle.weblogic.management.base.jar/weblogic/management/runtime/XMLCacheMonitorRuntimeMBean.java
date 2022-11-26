package weblogic.management.runtime;

/** @deprecated */
@Deprecated
public interface XMLCacheMonitorRuntimeMBean extends CacheMonitorRuntimeMBean {
   double getAverageTimeout();

   double getMinEntryTimeout();

   double getMaxEntryTimeout();

   double getAveragePerEntryMemorySize();

   double getMinEntryMemorySize();

   double getMaxEntryMemorySize();
}
