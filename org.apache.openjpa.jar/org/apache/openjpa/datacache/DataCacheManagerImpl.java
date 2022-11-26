package org.apache.openjpa.datacache;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.ObjectValue;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.util.ImplHelper;

public class DataCacheManagerImpl implements Closeable, DataCacheManager {
   private DataCache _cache = null;
   private QueryCache _queryCache = null;
   private DataCachePCDataGenerator _pcGenerator = null;
   private DataCacheScheduler _scheduler = null;

   public void initialize(OpenJPAConfiguration conf, ObjectValue dataCache, ObjectValue queryCache) {
      this._cache = (DataCache)dataCache.instantiate(DataCache.class, conf);
      if (this._cache != null) {
         if (conf.getDynamicDataStructs()) {
            this._pcGenerator = new DataCachePCDataGenerator(conf);
         }

         this._scheduler = new DataCacheScheduler(conf);
         this._cache.initialize(this);
         this._queryCache = (QueryCache)queryCache.instantiate(QueryCache.class, conf);
         if (this._queryCache != null) {
            this._queryCache.initialize(this);
         }

      }
   }

   public DataCache getSystemDataCache() {
      return this.getDataCache((String)null, false);
   }

   public DataCache getDataCache(String name) {
      return this.getDataCache(name, false);
   }

   public DataCache getDataCache(String name, boolean create) {
      return name != null && (this._cache == null || !name.equals(this._cache.getName())) ? null : this._cache;
   }

   public QueryCache getSystemQueryCache() {
      return this._queryCache;
   }

   public DataCachePCDataGenerator getPCDataGenerator() {
      return this._pcGenerator;
   }

   public DataCacheScheduler getDataCacheScheduler() {
      return this._scheduler;
   }

   public void close() {
      ImplHelper.close(this._cache);
      ImplHelper.close(this._queryCache);
      if (this._scheduler != null) {
         this._scheduler.stop();
      }

   }
}
