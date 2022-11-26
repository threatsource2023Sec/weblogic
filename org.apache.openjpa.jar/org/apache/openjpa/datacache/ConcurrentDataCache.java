package org.apache.openjpa.datacache;

import org.apache.openjpa.event.RemoteCommitListener;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.CacheMap;

public class ConcurrentDataCache extends AbstractDataCache implements RemoteCommitListener {
   private static final Localizer _loc = Localizer.forPackage(ConcurrentDataCache.class);
   private final CacheMap _cache = this.newCacheMap();

   public CacheMap getCacheMap() {
      return this._cache;
   }

   public void setCacheSize(int size) {
      this._cache.setCacheSize(size);
   }

   public int getCacheSize() {
      return this._cache.getCacheSize();
   }

   public void setSoftReferenceSize(int size) {
      this._cache.setSoftReferenceSize(size);
   }

   public int getSoftReferenceSize() {
      return this._cache.getSoftReferenceSize();
   }

   public void initialize(DataCacheManager mgr) {
      super.initialize(mgr);
      this.conf.getRemoteCommitEventManager().addInternalListener(this);
   }

   public void unpinAll(Class cls, boolean subs) {
      if (this.log.isWarnEnabled()) {
         this.log.warn(_loc.get("cache-class-unpin-all", (Object)this.getName()));
      }

      this.unpinAll(this._cache.getPinnedKeys());
   }

   public void writeLock() {
      this._cache.writeLock();
   }

   public void writeUnlock() {
      this._cache.writeUnlock();
   }

   protected CacheMap newCacheMap() {
      return new CacheMap() {
         protected void entryRemoved(Object key, Object value, boolean expired) {
            ConcurrentDataCache.this.keyRemoved(key, expired);
         }
      };
   }

   protected DataCachePCData getInternal(Object key) {
      return (DataCachePCData)this._cache.get(key);
   }

   protected DataCachePCData putInternal(Object key, DataCachePCData pc) {
      return (DataCachePCData)this._cache.put(key, pc);
   }

   protected DataCachePCData removeInternal(Object key) {
      return (DataCachePCData)this._cache.remove(key);
   }

   protected void removeAllInternal(Class cls, boolean subs) {
      this._cache.clear();
   }

   protected void clearInternal() {
      this._cache.clear();
   }

   protected boolean pinInternal(Object key) {
      return this._cache.pin(key);
   }

   protected boolean unpinInternal(Object key) {
      return this._cache.unpin(key);
   }
}
