package weblogic.management.runtime;

import java.util.Date;

public interface EntityCacheCumulativeRuntimeMBean extends EntityCacheRuntimeMBean {
   long getTotalNumberMemoryPurges();

   long getTotalItemsMemoryPurged();

   double getAvgEntrySizeMemoryPurged();

   Date getMostRecentMemoryPurge();

   double getMemoryPurgesPerHour();

   long getTotalNumberDiskPurges();

   long getTotalItemsDiskPurged();

   double getAvgEntrySizeDiskPurged();

   Date getMostRecentDiskPurge();

   double getDiskPurgesPerHour();

   long getTotalNumberOfRejections();

   long getTotalSizeOfRejections();

   double getPercentRejected();

   long getTotalNumberOfRenewals();
}
