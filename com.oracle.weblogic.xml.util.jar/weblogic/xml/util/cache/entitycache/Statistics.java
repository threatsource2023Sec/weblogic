package weblogic.xml.util.cache.entitycache;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Statistics implements Serializable, Cloneable {
   static final long serialVersionUID = 1L;
   transient boolean isPersistent = false;
   transient EntityCache cache = null;
   transient String path = null;
   StatsData statsData = new StatsData();

   Statistics(EntityCache cache) {
      this.cache = cache;
   }

   private void touch() {
      this.cache.statsCumulativeModification = true;
   }

   static Statistics initialize(EntityCache cache, String path) {
      Statistics stats = null;
      if ((new File(path)).exists()) {
         try {
            stats = (Statistics)EntityCache.loadFile(path);
            stats.statsData.sessionStartClock = (double)(new Date()).getTime();
            stats.statsData.lastOngoingClockUpdate = stats.statsData.sessionStartClock;
         } catch (CX.FileLoadOutOfMemory var8) {
            try {
               cache.notifyListener(new Event.OutOfMemoryLoadingStatisticsEvent(cache, path));
            } catch (Exception var7) {
            }

            stats = null;
         } catch (Exception var9) {
            if (EntityCache.canRead(path)) {
               try {
                  cache.notifyListener(new Event.StatisticsCorruptionEvent(cache, path));
               } catch (Exception var6) {
               }
            } else {
               try {
                  cache.notifyListener(new Event.FileAccessErrorForStatisticsEvent(cache, path, false));
               } catch (Exception var5) {
               }
            }

            stats = null;
         }
      }

      if (stats == null) {
         stats = new Statistics(cache);
         stats.touch();
      }

      stats.cache = cache;
      stats.isPersistent = true;
      stats.path = path;
      return stats;
   }

   synchronized Statistics copy() {
      try {
         Statistics copy = (Statistics)this.clone();
         copy.statsData = this.statsData.copy();
         return copy;
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   void clear() {
      this.statsData = new StatsData();
   }

   public Date getStartOfSampleCollection() {
      return this.statsData.startOfSampleCollection;
   }

   public synchronized double getHoursUpSinceSampleStart() {
      this.updateClock();
      return this.statsData.ongoingClock / 3600000.0;
   }

   public synchronized double getHoursThisSession() {
      return ((double)(new Date()).getTime() - this.statsData.sessionStartClock) / 3600000.0;
   }

   public long getTotalEntries() {
      return this.statsData.totalEntries;
   }

   public long getTotalTransientEntries() {
      return this.statsData.totalTransientEntries;
   }

   public long getTotalPersistentEntries() {
      return this.statsData.totalEntries - this.statsData.totalTransientEntries;
   }

   public long getTotalPersistedEntries() {
      return this.statsData.totalPersistedEntries;
   }

   public double getAvgPercentTransient() {
      return this.statsData.totalEntries != 0L ? (double)this.statsData.totalTransientEntries / (double)this.statsData.totalEntries * 100.0 : 0.0;
   }

   public double getAvgPercentPersistent() {
      return this.statsData.totalEntries != 0L ? ((double)this.statsData.totalEntries - (double)this.statsData.totalTransientEntries) / (double)this.statsData.totalEntries * 100.0 : 0.0;
   }

   public double getAvgTimout() {
      return this.statsData.avgTimout;
   }

   public double getMinEntryTimeout() {
      return this.statsData.minEntryTimeout;
   }

   public double getMaxEntryTimeout() {
      return this.statsData.maxEntryTimeout;
   }

   public double getAvgPerEntryMemorySize() {
      return this.statsData.avgPerEntryMemorySize;
   }

   public long getMaxEntryMemorySize() {
      return this.statsData.maxEntryMemorySize;
   }

   public long getMinEntryMemorySize() {
      return this.statsData.minEntryMemorySize;
   }

   public long getMaxEntryMemorySizeRequested() {
      return this.statsData.maxEntryMemorySizeRequested;
   }

   public double getAvgPerEntryDiskSize() {
      return this.statsData.avgPerEntryDiskSize;
   }

   public long getTotalNumberMemoryPurges() {
      return this.statsData.totalNumberMemoryPurges;
   }

   public long getTotalItemsMemoryPurged() {
      return this.statsData.totalItemsMemoryPurged;
   }

   public double getAvgEntrySizeMemoryPurged() {
      return this.statsData.avgEntrySizeMemoryPurged;
   }

   public Date getMostRecentMemoryPurge() {
      return this.statsData.mostRecentMemoryPurge;
   }

   public synchronized double getMemoryPurgesPerHour() {
      this.updateClock();
      double timeLapsed = this.statsData.ongoingClock;
      if (timeLapsed == 0.0) {
         timeLapsed = 1.0;
      }

      double hours = timeLapsed / 3600000.0;
      double purgesPerHour = (double)this.statsData.totalNumberMemoryPurges / hours;
      return purgesPerHour;
   }

   public long getTotalNumberDiskPurges() {
      return this.statsData.totalNumberDiskPurges;
   }

   public long getTotalItemsDiskPurged() {
      return this.statsData.totalItemsDiskPurged;
   }

   public double getAvgEntrySizeDiskPurged() {
      return this.statsData.avgEntrySizeDiskPurged;
   }

   public Date getMostRecentDiskPurge() {
      return this.statsData.mostRecentDiskPurge;
   }

   public synchronized double getDiskPurgesPerHour() {
      this.updateClock();
      double timeLapsed = this.statsData.ongoingClock;
      if (timeLapsed == 0.0) {
         timeLapsed = 1.0;
      }

      double hours = timeLapsed / 3600000.0;
      double purgesPerHour = (double)this.statsData.totalNumberDiskPurges / hours;
      return purgesPerHour;
   }

   public long getTotalNumberOfRejections() {
      return this.statsData.totalNumberOfRejections;
   }

   public long getTotalSizeOfRejections() {
      return this.statsData.totalSizeOfRejections;
   }

   public synchronized double getPercentRejected() {
      if (this.statsData.totalEntries == 0L) {
         return 0.0;
      } else {
         double totalTried = (double)this.statsData.totalNumberOfRejections + (double)this.statsData.totalEntries;
         return (double)this.statsData.totalNumberOfRejections / totalTried * 100.0;
      }
   }

   public long getTotalNumberOfRenewals() {
      return this.statsData.totalNumberOfRenewals;
   }

   synchronized void addEntry(CacheEntry ce) {
      long size = ce.getSize();
      if (this.statsData.minEntryMemorySize == 0L) {
         this.statsData.minEntryMemorySize = size;
      } else if (size < this.statsData.minEntryMemorySize) {
         this.statsData.minEntryMemorySize = size;
      }

      if (size > this.statsData.maxEntryMemorySize) {
         this.statsData.maxEntryMemorySize = size;
      }

      this.statsData.avgPerEntryMemorySize = ((double)this.statsData.totalEntries * this.statsData.avgPerEntryMemorySize + (double)size) / (double)(this.statsData.totalEntries + 1L);
      long leaseInterval = ce.getLeaseInterval();
      if (this.statsData.minEntryTimeout == 0.0) {
         this.statsData.minEntryTimeout = (double)leaseInterval;
      } else if ((double)leaseInterval < this.statsData.minEntryTimeout) {
         this.statsData.minEntryTimeout = (double)leaseInterval;
      }

      if ((double)leaseInterval > this.statsData.maxEntryTimeout) {
         this.statsData.maxEntryTimeout = (double)leaseInterval;
      }

      this.statsData.avgTimout = ((double)this.statsData.totalEntries * this.statsData.avgTimout + (double)leaseInterval) / (double)(this.statsData.totalEntries + 1L);
      this.statsData.totalEntries++;
      if (!ce.isPersistentNoStupidException()) {
         this.statsData.totalTransientEntries++;
      }

      this.touch();
   }

   synchronized void writeEntry(CacheEntry ce) {
      this.statsData.totalPersistedEntries++;
      this.statsData.avgPerEntryDiskSize = ((double)(this.statsData.totalPersistedEntries - 1L) * this.statsData.avgPerEntryDiskSize + (double)ce.getDiskSizeNoStupidException()) / (double)this.statsData.totalPersistedEntries;
      this.touch();
   }

   synchronized void memoryPurge(long itemsPurged, long spacePurged) {
      this.statsData.avgEntrySizeMemoryPurged = ((double)this.statsData.totalItemsMemoryPurged * this.statsData.avgEntrySizeMemoryPurged + (double)spacePurged) / (double)(this.statsData.totalItemsMemoryPurged + itemsPurged);
      this.statsData.totalNumberMemoryPurges++;
      StatsData var5 = this.statsData;
      var5.totalItemsMemoryPurged = var5.totalItemsMemoryPurged + itemsPurged;
      this.statsData.mostRecentMemoryPurge = new Date();
      this.touch();
   }

   synchronized void diskPurge(long itemsPurged, long spacePurged) {
      this.statsData.avgEntrySizeDiskPurged = ((double)this.statsData.totalItemsDiskPurged * this.statsData.avgEntrySizeDiskPurged + (double)spacePurged) / (double)(this.statsData.totalItemsDiskPurged + itemsPurged);
      this.statsData.totalNumberDiskPurges++;
      StatsData var5 = this.statsData;
      var5.totalItemsDiskPurged = var5.totalItemsDiskPurged + itemsPurged;
      this.statsData.mostRecentDiskPurge = new Date();
      this.touch();
   }

   synchronized void rejection(CacheEntry ce) {
      this.statsData.totalNumberOfRejections++;
      StatsData var2 = this.statsData;
      var2.totalSizeOfRejections = var2.totalSizeOfRejections + ce.getSize();
      this.touch();
   }

   void renewal(CacheEntry ce) {
      this.statsData.totalNumberOfRenewals++;
      this.touch();
   }

   private void updateClock() {
      StatsData var1 = this.statsData;
      var1.ongoingClock = var1.ongoingClock + ((double)(new Date()).getTime() - this.statsData.lastOngoingClockUpdate);
      this.statsData.lastOngoingClockUpdate = (double)(new Date()).getTime();
   }

   synchronized void save() throws CX.EntityCacheException {
      if (this.isPersistent) {
         if (this.cache.statsCumulativeModification) {
            this.updateClock();

            try {
               EntityCache.saveFile(this, this.path);
            } catch (Exception var2) {
               this.cache.notifyListener(new Event.FileAccessErrorForStatisticsEvent(this.cache, this.path, true));
            }

         }
      }
   }

   class StatsData implements Serializable, Cloneable {
      static final long serialVersionUID = 1L;
      Date startOfSampleCollection = new Date();
      volatile long totalDiskUpdates = 0L;
      volatile long totalLostUpdates = 0L;
      private volatile double ongoingClock = 0.0;
      private transient volatile double sessionStartClock = (double)System.currentTimeMillis();
      private transient volatile double lastOngoingClockUpdate;
      private volatile long totalEntries;
      private volatile long totalPersistedEntries;
      private volatile long totalTransientEntries;
      private volatile double avgTimout;
      private volatile double minEntryTimeout;
      private volatile double maxEntryTimeout;
      private volatile double avgPerEntryMemorySize;
      private volatile long maxEntryMemorySize;
      private volatile long minEntryMemorySize;
      private volatile long maxEntryMemorySizeRequested;
      private volatile double avgPerEntryDiskSize;
      private volatile long totalNumberMemoryPurges;
      private volatile long totalItemsMemoryPurged;
      private volatile double avgEntrySizeMemoryPurged;
      private Date mostRecentMemoryPurge;
      private volatile long totalNumberDiskPurges;
      private volatile long totalItemsDiskPurged;
      private volatile double avgEntrySizeDiskPurged;
      private Date mostRecentDiskPurge;
      private volatile long totalNumberOfRejections;
      private volatile long totalSizeOfRejections;
      private volatile long totalNumberOfRenewals;

      StatsData() {
         this.lastOngoingClockUpdate = this.sessionStartClock;
         this.totalEntries = 0L;
         this.totalPersistedEntries = 0L;
         this.totalTransientEntries = 0L;
         this.avgTimout = 0.0;
         this.minEntryTimeout = 0.0;
         this.maxEntryTimeout = 0.0;
         this.avgPerEntryMemorySize = 0.0;
         this.maxEntryMemorySize = 0L;
         this.minEntryMemorySize = 0L;
         this.maxEntryMemorySizeRequested = 0L;
         this.avgPerEntryDiskSize = 0.0;
         this.totalNumberMemoryPurges = 0L;
         this.totalItemsMemoryPurged = 0L;
         this.avgEntrySizeMemoryPurged = 0.0;
         this.mostRecentMemoryPurge = null;
         this.totalNumberDiskPurges = 0L;
         this.totalItemsDiskPurged = 0L;
         this.avgEntrySizeDiskPurged = 0.0;
         this.mostRecentDiskPurge = null;
         this.totalNumberOfRejections = 0L;
         this.totalSizeOfRejections = 0L;
         this.totalNumberOfRenewals = 0L;
      }

      synchronized StatsData copy() {
         try {
            StatsData copy = (StatsData)this.clone();
            return copy;
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }
}
