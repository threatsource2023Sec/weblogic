package kodo.datacache;

import java.util.concurrent.locks.ReentrantLock;
import org.apache.openjpa.datacache.AbstractDataCache;
import org.apache.openjpa.datacache.DataCachePCData;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;

public class GemFireDataCache extends AbstractDataCache {
   public static final String DEFAULT_CACHE_NAME = "root/kodo-data-cache";
   private static final Localizer s_loc = Localizer.forPackage(GemFireDataCache.class);
   private GemFireCacheWrapper _cache;
   private String _regionName;
   private boolean _transactional = false;
   private final ReentrantLock _lock = new ReentrantLock();

   public void setGemFireCacheName(String name) {
      if (this.log.isTraceEnabled()) {
         this.log.trace(s_loc.get("gemfire-datacache-setname", name));
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

   public GemFireCacheWrapper getCacheWrapper() {
      return this._cache;
   }

   public void startConfiguration() {
      super.startConfiguration();
      this._regionName = "root/kodo-data-cache";
   }

   public void endConfiguration() {
      super.endConfiguration();
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

   protected DataCachePCData getInternal(Object key) {
      if (key == null) {
         return null;
      } else {
         try {
            return (DataCachePCData)this._cache.get(key);
         } catch (IllegalArgumentException var3) {
            throw new UserException(s_loc.get("gemfire-key-isntserializable", key, key.getClass().getName()), var3);
         }
      }
   }

   protected DataCachePCData putInternal(Object key, DataCachePCData value) {
      if (key == null) {
         return null;
      } else {
         this.writeLock();

         DataCachePCData var3;
         try {
            var3 = (DataCachePCData)this._cache.put(key, value);
         } catch (IllegalArgumentException var7) {
            throw new UserException(s_loc.get("gemfire-key-isntserializable", key), var7);
         } finally {
            this.writeUnlock();
         }

         return var3;
      }
   }

   protected void removeAllInternal(Class cls, boolean subclasses) {
      throw new UnsupportedException(s_loc.get("removeall-byclass"));
   }

   protected DataCachePCData removeInternal(Object key) {
      this.writeLock();

      DataCachePCData var2;
      try {
         var2 = (DataCachePCData)this._cache.remove(key);
      } catch (IllegalArgumentException var6) {
         throw new UserException(s_loc.get("gemfire-key-isntserializable", key), var6);
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

   protected boolean pinInternal(Object key) {
      return this.contains(key);
   }

   protected boolean unpinInternal(Object key) {
      return this.contains(key);
   }

   protected boolean recacheUpdates() {
      return true;
   }

   public void setTransactional(boolean transactional) {
      this._transactional = transactional;
   }

   public boolean isTransactional() {
      return this._transactional;
   }
}
