package kodo.datacache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kodo.util.MonitoringCacheMap;
import org.apache.openjpa.datacache.AbstractQueryCache;
import org.apache.openjpa.datacache.DataCacheManager;
import org.apache.openjpa.datacache.QueryKey;
import org.apache.openjpa.datacache.QueryResult;
import org.apache.openjpa.event.RemoteCommitListener;
import org.apache.openjpa.util.CacheMap;

public class LRUQueryCache extends AbstractQueryCache implements RemoteCommitListener {
   private final CacheMap _cache = new MonitoringCacheMap(true, 1000);

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
      this.conf.getRemoteCommitEventManager().addListener(this);
   }

   public void writeLock() {
      this._cache.writeLock();
   }

   public void writeUnlock() {
      this._cache.writeUnlock();
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
      Collection keys = this._cache.keySet();
      ArrayList copy = new ArrayList(keys.size());
      Iterator itr = keys.iterator();

      while(itr.hasNext()) {
         copy.add(itr.next());
      }

      return copy;
   }
}
