package kodo.datacache;

import com.solarmetric.manage.BucketStatistic;
import com.solarmetric.manage.jmx.StatisticsProvider;
import java.util.Arrays;
import java.util.Collection;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.datacache.DelegatingQueryCache;
import org.apache.openjpa.datacache.QueryCache;
import org.apache.openjpa.datacache.QueryKey;
import org.apache.openjpa.datacache.QueryResult;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class MonitoringQueryCache extends DelegatingQueryCache implements StatisticsProvider {
   private static final Localizer s_loc = Localizer.forPackage(MonitoringQueryCache.class);
   private BucketStatistic _numHits = new BucketStatistic("cache.hits", "Cache Hits", "Count", 1, 1, 5000L);
   private BucketStatistic _numMisses = new BucketStatistic("cache.misses", "Cache Misses", "Count", 1, 1, 5000L);
   private Collection _stats;
   private Log _log;

   public MonitoringQueryCache(OpenJPAConfiguration conf, QueryCache cache) {
      super(cache);
      this._stats = Arrays.asList(this._numHits, this._numMisses);
      this._log = conf.getLog("openjpa.DataCache");
      this.resetStatistics();
   }

   public int getHits() {
      return (int)this._numHits.getValue();
   }

   public int getMisses() {
      return (int)this._numMisses.getValue();
   }

   public double getHitRate() {
      double hits = this._numHits.getValue();
      double misses = this._numMisses.getValue();
      double ratio = 0.0;
      if (hits + misses > 0.0) {
         ratio = hits / (hits + misses);
      }

      return ratio;
   }

   public String getStatisticsString() {
      return s_loc.get("cache-stats", new Object[]{"<query-cache>", String.valueOf(this.getHits()), String.valueOf(this.getMisses()), String.valueOf(this.getHitRate())}).getMessage();
   }

   public Collection getStatistics() {
      return this._stats;
   }

   public int getCacheSize() {
      if (this.getDelegate() instanceof LRUQueryCache) {
         return ((LRUQueryCache)this.getDelegate()).getCacheSize();
      } else {
         return this.getDelegate() instanceof KodoConcurrentQueryCache ? ((KodoConcurrentQueryCache)this.getDelegate()).getCacheSize() : -1;
      }
   }

   public int getSoftReferenceSize() {
      if (this.getDelegate() instanceof LRUQueryCache) {
         return ((LRUQueryCache)this.getDelegate()).getSoftReferenceSize();
      } else {
         return this.getDelegate() instanceof KodoConcurrentQueryCache ? ((KodoConcurrentQueryCache)this.getDelegate()).getSoftReferenceSize() : -1;
      }
   }

   public void resetStatistics() {
      this._numHits.setValue(0.0);
      this._numMisses.setValue(0.0);
   }

   public QueryResult get(QueryKey key) {
      QueryResult res = super.get(key);
      if (res == null) {
         this._numMisses.increment(1);
      } else {
         this._numHits.increment(1);
      }

      return res;
   }

   public void close() {
      if (this._log.isInfoEnabled()) {
         this._log.info(this.getStatisticsString());
      }

      super.close();
   }
}
