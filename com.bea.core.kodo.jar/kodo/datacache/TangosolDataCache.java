package kodo.datacache;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.openjpa.datacache.AbstractDataCache;
import org.apache.openjpa.datacache.DataCachePCData;
import org.apache.openjpa.datacache.ExpirationListener;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;

public class TangosolDataCache extends AbstractDataCache {
   public static final int TYPE_DISTRIBUTED = 0;
   public static final int TYPE_REPLICATED = 1;
   public static final int TYPE_NAMED = 2;
   private static final Localizer s_loc = Localizer.forPackage(TangosolDataCache.class);
   private final ReentrantLock _lock = new ReentrantLock();
   private NamedCache _cache = null;
   private String _cacheName = "kodo";
   private int _cacheType = 2;
   private boolean _configured = false;
   private boolean _clearOnClose = false;

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

   public void addExpirationListener(ExpirationListener listen) {
      throw new UnsupportedException(s_loc.get("tangosol-exp"));
   }

   protected DataCachePCData getInternal(Object key) {
      return (DataCachePCData)this._cache.get(key);
   }

   protected DataCachePCData putInternal(Object key, DataCachePCData pc) {
      this.writeLock();

      DataCachePCData var3;
      try {
         var3 = (DataCachePCData)this._cache.put(key, pc);
      } finally {
         this.writeUnlock();
      }

      return var3;
   }

   protected DataCachePCData removeInternal(Object key) {
      this.writeLock();

      DataCachePCData var2;
      try {
         var2 = (DataCachePCData)this._cache.remove(key);
      } finally {
         this.writeUnlock();
      }

      return var2;
   }

   protected void removeAllInternal(Class cls, boolean subclasses) {
      throw new UnsupportedException(s_loc.get("removeall-byclass"));
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
      return this._cache.containsKey(key);
   }

   protected boolean unpinInternal(Object key) {
      return this._cache.containsKey(key);
   }

   protected boolean recacheUpdates() {
      return true;
   }

   public void close() {
      super.close(this._clearOnClose);
   }

   public void endConfiguration() {
      super.endConfiguration();
      this._cache = this.newTangosolCache(this._cacheName);
      this._configured = true;
   }

   protected NamedCache newTangosolCache(String name) {
      if (this._cacheType == 0) {
         return CacheFactory.getDistributedCache(name);
      } else {
         return this._cacheType == 1 ? CacheFactory.getReplicatedCache(name) : CacheFactory.getCache(name);
      }
   }
}
