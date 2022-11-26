package org.apache.openjpa.datacache;

import java.util.Collection;
import org.apache.openjpa.event.RemoteCommitListener;
import org.apache.openjpa.util.CacheMap;

public class ConcurrentQueryCache extends AbstractQueryCache implements RemoteCommitListener {
   private CacheMap _cache = this.newCacheMap();

   public CacheMap getCacheMap() {
      return this._cache;
   }

   public int getCacheSize() {
      return this._cache.getCacheSize();
   }

   public void setCacheSize(int size) {
      this._cache.setCacheSize(size);
   }

   public int getSoftReferenceSize() {
      return this._cache.getSoftReferenceSize();
   }

   public void setSoftReferenceSize(int size) {
      this._cache.setSoftReferenceSize(size);
   }

   public void initialize(DataCacheManager mgr) {
      super.initialize(mgr);
      this.conf.getRemoteCommitEventManager().addInternalListener(this);
   }

   public void writeLock() {
      this._cache.writeLock();
   }

   public void writeUnlock() {
      this._cache.writeUnlock();
   }

   protected CacheMap newCacheMap() {
      return new CacheMap();
   }

   protected QueryResult getInternal(QueryKey qk) {
      return (QueryResult)this._cache.get(qk);
   }

   protected QueryResult putInternal(QueryKey qk, QueryResult result) {
      return (QueryResult)this._cache.put(qk, result);
   }

   protected QueryResult removeInternal(QueryKey qk) {
      return (QueryResult)this._cache.remove(qk);
   }

   protected void clearInternal() {
      this._cache.clear();
   }

   protected boolean pinInternal(QueryKey qk) {
      return this._cache.pin(qk);
   }

   protected boolean unpinInternal(QueryKey qk) {
      return this._cache.unpin(qk);
   }

   protected Collection keySet() {
      return this._cache.keySet();
   }
}
