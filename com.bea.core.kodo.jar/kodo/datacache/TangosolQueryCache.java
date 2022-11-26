package kodo.datacache;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.openjpa.datacache.AbstractQueryCache;
import org.apache.openjpa.datacache.QueryKey;
import org.apache.openjpa.datacache.QueryResult;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.UserException;

public class TangosolQueryCache extends AbstractQueryCache {
   public static final int TYPE_DISTRIBUTED = 0;
   public static final int TYPE_REPLICATED = 1;
   public static final int TYPE_NAMED = 2;
   private static final Localizer s_loc = Localizer.forPackage(TangosolQueryCache.class);
   private NamedCache _cache = null;
   private String _cacheName = "kodo-query";
   private int _cacheType = 2;
   private boolean _configured = false;
   private boolean _clearOnClose = false;
   private final ReentrantLock _lock = new ReentrantLock();

   public NamedCache getTangosolCache() {
      return this._cache;
   }

   public void setTangosolCacheName(String name) {
      if (this._configured) {
         throw new InvalidStateException(s_loc.get("cache-already-configured"));
      } else {
         this._cacheName = name;
      }
   }

   public final String getTangosolCacheName() {
      return this._cacheName;
   }

   public void setTangosolCacheType(int type) {
      if (this._configured) {
         throw new InvalidStateException(s_loc.get("cache-already-configured"));
      } else {
         this._cacheType = type;
      }
   }

   public int getTangosolCacheType() {
      return this._cacheType;
   }

   public void setTangosolCacheType(String type) {
      if ("distributed".equals(type)) {
         this.setTangosolCacheType(0);
      } else if ("replicated".equals(type)) {
         this.setTangosolCacheType(1);
      } else {
         if (!"named".equals(type) && type != null) {
            throw new UserException(s_loc.get("invalid-type", type));
         }

         this.setTangosolCacheType(2);
      }

   }

   public void setClearOnClose(boolean clearOnClose) {
      if (this._configured) {
         throw new InvalidStateException(s_loc.get("cache-already-configured"));
      } else {
         this._clearOnClose = clearOnClose;
      }
   }

   public boolean getClearOnClose() {
      return this._clearOnClose;
   }

   public void writeLock() {
      this._lock.lock();
   }

   public void writeUnlock() {
      this._lock.unlock();
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

   protected boolean pinInternal(QueryKey key) {
      return this._cache.containsKey(key);
   }

   protected boolean unpinInternal(QueryKey key) {
      return this._cache.containsKey(key);
   }

   protected boolean recacheUpdates() {
      return true;
   }

   protected Collection keySet() {
      return this._cache.keySet();
   }

   public void close() {
      super.close(this._clearOnClose);
   }

   public void endConfiguration() {
      super.endConfiguration();
      this._cache = this.newTangosolQueryCache(this._cacheName);
      this._configured = true;
   }

   protected NamedCache newTangosolQueryCache(String name) {
      if (this._cacheType == 0) {
         return CacheFactory.getDistributedCache(name);
      } else {
         return this._cacheType == 1 ? CacheFactory.getReplicatedCache(name) : CacheFactory.getCache(name);
      }
   }
}
