package org.apache.openjpa.persistence;

import java.util.Collections;
import javax.persistence.Query;
import org.apache.openjpa.datacache.DelegatingQueryCache;
import org.apache.openjpa.datacache.QueryCache;
import org.apache.openjpa.datacache.QueryKey;
import org.apache.openjpa.datacache.TypesChangedEvent;

public class QueryResultCacheImpl implements QueryResultCache {
   private final DelegatingQueryCache _cache;

   public QueryResultCacheImpl(QueryCache cache) {
      this._cache = new DelegatingQueryCache(cache, PersistenceExceptions.TRANSLATOR);
   }

   public QueryCache getDelegate() {
      return this._cache.getDelegate();
   }

   public void pin(Query q) {
      if (this._cache.getDelegate() != null) {
         this._cache.pin(this.toQueryKey(q));
      }

   }

   public void unpin(Query q) {
      if (this._cache.getDelegate() != null) {
         this._cache.unpin(this.toQueryKey(q));
      }

   }

   public void evict(Query q) {
      if (this._cache.getDelegate() != null) {
         this._cache.remove(this.toQueryKey(q));
      }

   }

   public void evictAll() {
      this._cache.clear();
   }

   public void evictAll(Class cls) {
      this._cache.onTypesChanged(new TypesChangedEvent(this, Collections.singleton(cls)));
   }

   private QueryKey toQueryKey(Query q) {
      QueryImpl impl = (QueryImpl)q;
      return impl.hasPositionalParameters() ? QueryKey.newInstance(impl.getDelegate(), impl.getPositionalParameters()) : QueryKey.newInstance(impl.getDelegate(), impl.getNamedParameters());
   }

   public int hashCode() {
      return this._cache.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof QueryResultCacheImpl) ? false : this._cache.equals(((QueryResultCacheImpl)other)._cache);
      }
   }
}
