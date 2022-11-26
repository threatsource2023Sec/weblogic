package kodo.datacache;

import com.gemstone.gemfire.DataSerializer;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.openjpa.datacache.AbstractQueryCache;
import org.apache.openjpa.datacache.DataCacheManager;
import org.apache.openjpa.datacache.QueryKey;
import org.apache.openjpa.datacache.QueryResult;
import org.apache.openjpa.datacache.TypesChangedEvent;
import org.apache.openjpa.lib.util.Localizer;

public class GemFireQueryCache extends AbstractQueryCache {
   public static final String DEFAULT_CACHE_NAME = "root/kodo-query-cache";
   private static final Localizer s_loc = Localizer.forPackage(GemFireDataCache.class);
   private GemFireCacheWrapper _cache;
   private String _regionName;
   private final ReentrantLock _lock = new ReentrantLock();
   private boolean _transactional = false;

   public void setGemFireCacheName(String name) {
      if (this.log.isTraceEnabled()) {
         this.log.trace(s_loc.get("gemfire-querycache-setname", name));
      }

      this._regionName = name;
   }

   /** @deprecated */
   public void setGemfireCacheName(String name) {
      this.setGemFireCacheName(name);
   }

   public String getGemFireCacheName() {
      return this._regionName;
   }

   public void startConfiguration() {
      super.startConfiguration();
      this._regionName = "root/kodo-query-cache";
   }

   public void endConfiguration() {
      super.endConfiguration();
      DataSerializer.register(GemFireQueryResultSerializer.class, (byte)42);
      this._cache = this.newCacheWrapper();
   }

   protected GemFireCacheWrapper newCacheWrapper() {
      return new GemFireCacheWrapper(this._regionName, this.log);
   }

   public void writeLock() {
      if (this._transactional && !this._lock.isLocked()) {
         this._cache.beginGemFireTransaction();
      }

      this._lock.lock();
   }

   public void writeUnlock() {
      this._lock.unlock();
      if (this._transactional && !this._lock.isLocked()) {
         boolean committed = false;

         try {
            committed = this._cache.commitGemFireTransaction();
         } finally {
            if (!committed) {
               this._cache.rollbackGemFireTransaction();
            }

         }
      }

   }

   protected QueryResult getInternal(QueryKey key) {
      return key == null ? null : (QueryResult)this._cache.get(key);
   }

   protected QueryResult putInternal(QueryKey key, QueryResult value) {
      if (key == null) {
         return null;
      } else {
         this.writeLock();

         QueryResult var3;
         try {
            var3 = (QueryResult)this._cache.put(key, value);
         } finally {
            this.writeUnlock();
         }

         return var3;
      }
   }

   protected QueryResult removeInternal(QueryKey key) {
      this.writeLock();

      QueryResult var2;
      try {
         var2 = (QueryResult)this._cache.remove(key);
      } finally {
         this.writeUnlock();
      }

      return var2;
   }

   protected void clearInternal() {
      this.writeLock();

      try {
         this._cache.clear();
      } finally {
         this.writeUnlock();
      }

   }

   public void onTypesChanged(TypesChangedEvent ev) {
      this.writeLock();

      try {
         super.onTypesChanged(ev);
      } finally {
         this.writeUnlock();
      }

   }

   protected boolean pinInternal(QueryKey key) {
      return this._cache.get(key) != null;
   }

   protected boolean unpinInternal(QueryKey key) {
      return this._cache.get(key) != null;
   }

   protected boolean recacheUpdates() {
      return true;
   }

   protected Collection keySet() {
      return this._cache.keySet();
   }

   public void initialize(DataCacheManager mgr) {
   }

   public void setTransactional(boolean transactional) {
      this._transactional = transactional;
   }

   public boolean isTransactional() {
      return this._transactional;
   }
}
