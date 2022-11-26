package weblogic.management.runtime;

public interface EntityCacheRuntimeMBean extends RuntimeMBean {
   long getTotalCurrentEntries();

   long getTotalPersistentCurrentEntries();

   long getTotalTransientCurrentEntries();

   double getAvgPercentTransient();

   double getAvgPercentPersistent();

   double getAvgTimeout();

   double getMinEntryTimeout();

   double getMaxEntryTimeout();

   double getAvgPerEntryMemorySize();

   long getMaxEntryMemorySize();

   long getMinEntryMemorySize();

   double getAvgPerEntryDiskSize();
}
