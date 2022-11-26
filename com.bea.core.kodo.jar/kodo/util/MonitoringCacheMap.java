package kodo.util;

import com.solarmetric.manage.BucketStatistic;
import com.solarmetric.manage.jmx.StatisticsProvider;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.CacheMap;

public class MonitoringCacheMap extends CacheMap implements StatisticsProvider {
   private static final Localizer _loc = Localizer.forPackage(MonitoringCacheMap.class);
   private BucketStatistic _pinnedStat;
   private BucketStatistic _cacheStat;
   private BucketStatistic _softStat;
   private final Collection _stats;

   public MonitoringCacheMap() {
      this._pinnedStat = new BucketStatistic(_loc.get("cachemap.stat.pinned").getMessage(), _loc.get("cachemap.stat.pinned.desc").getMessage(), _loc.get("cachemap.stat.pinned.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._cacheStat = new BucketStatistic(_loc.get("cachemap.stat.cache").getMessage(), _loc.get("cachemap.stat.cache.desc").getMessage(), _loc.get("cachemap.stat.cache.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._softStat = new BucketStatistic(_loc.get("cachemap.stat.soft").getMessage(), _loc.get("cachemap.stat.soft.desc").getMessage(), _loc.get("cachemap.stat.soft.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._stats = Arrays.asList(this._pinnedStat, this._cacheStat, this._softStat);
      this.resetStatistics();
   }

   public MonitoringCacheMap(boolean lru, int max) {
      super(lru, max);
      this._pinnedStat = new BucketStatistic(_loc.get("cachemap.stat.pinned").getMessage(), _loc.get("cachemap.stat.pinned.desc").getMessage(), _loc.get("cachemap.stat.pinned.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._cacheStat = new BucketStatistic(_loc.get("cachemap.stat.cache").getMessage(), _loc.get("cachemap.stat.cache.desc").getMessage(), _loc.get("cachemap.stat.cache.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._softStat = new BucketStatistic(_loc.get("cachemap.stat.soft").getMessage(), _loc.get("cachemap.stat.soft.desc").getMessage(), _loc.get("cachemap.stat.soft.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._stats = Arrays.asList(this._pinnedStat, this._cacheStat, this._softStat);
      this.resetStatistics();
   }

   public MonitoringCacheMap(boolean lru, int max, int size, float load) {
      super(lru, max, size, load);
      this._pinnedStat = new BucketStatistic(_loc.get("cachemap.stat.pinned").getMessage(), _loc.get("cachemap.stat.pinned.desc").getMessage(), _loc.get("cachemap.stat.pinned.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._cacheStat = new BucketStatistic(_loc.get("cachemap.stat.cache").getMessage(), _loc.get("cachemap.stat.cache.desc").getMessage(), _loc.get("cachemap.stat.cache.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._softStat = new BucketStatistic(_loc.get("cachemap.stat.soft").getMessage(), _loc.get("cachemap.stat.soft.desc").getMessage(), _loc.get("cachemap.stat.soft.ordinate").getMessage(), 2, 1, 2, 5000L);
      this._stats = Arrays.asList(this._pinnedStat, this._cacheStat, this._softStat);
      this.resetStatistics();
   }

   public Collection getStatistics() {
      return this._stats;
   }

   public void resetStatistics() {
      this.writeLock();

      try {
         this._pinnedStat.setValue(0.0);
         this._cacheStat.setValue(0.0);
         this._softStat.setValue(0.0);
      } finally {
         this.writeUnlock();
      }

   }

   public void clear() {
      this.writeLock();

      try {
         super.clear();
         this.resetStatistics();
      } finally {
         this.writeUnlock();
      }

   }

   protected void cacheMapOverflowRemoved(Object key, Object value) {
      super.cacheMapOverflowRemoved(key, value);
      this._cacheStat.decrement(1);
   }

   protected void softMapOverflowRemoved(Object key, Object value) {
      this._softStat.decrement(1);
      super.softMapOverflowRemoved(key, value);
   }

   protected void softMapValueExpired(Object key) {
      this._softStat.decrement(1);
      super.softMapValueExpired(key);
   }

   protected Object put(Map map, Object key, Object value) {
      Object ret = super.put(map, key, value);
      if (ret == null) {
         if (map == this.cacheMap) {
            this._cacheStat.increment(1);
         } else if (map == this.softMap) {
            this._softStat.increment(1);
         } else if (map == this.pinnedMap && value != null) {
            this._pinnedStat.increment(1);
         }
      } else if (value == null && map == this.pinnedMap) {
         this._pinnedStat.decrement(1);
      }

      return ret;
   }

   protected Object remove(Map map, Object key) {
      Object ret = super.remove(map, key);
      if (ret != null) {
         if (map == this.cacheMap) {
            this._cacheStat.decrement(1);
         } else if (map == this.softMap) {
            this._softStat.decrement(1);
         } else if (map == this.pinnedMap) {
            this._pinnedStat.decrement(1);
         }
      }

      return ret;
   }
}
