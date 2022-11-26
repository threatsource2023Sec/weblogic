package kodo.jdo;

import java.util.Collections;
import java.util.Map;
import javax.jdo.Query;
import org.apache.openjpa.datacache.DelegatingQueryCache;
import org.apache.openjpa.datacache.QueryCache;
import org.apache.openjpa.datacache.QueryKey;
import org.apache.openjpa.datacache.TypesChangedEvent;

public class QueryResultCache {
   private final DelegatingQueryCache _cache;

   public QueryResultCache(QueryCache cache) {
      this._cache = new DelegatingQueryCache(cache, JDOExceptions.TRANSLATOR);
   }

   public QueryCache getDelegate() {
      return this._cache.getDelegate();
   }

   public void pin(Query q) {
      if (this._cache.getDelegate() != null) {
         this._cache.pin(QueryKey.newInstance(((QueryImpl)q).getDelegate()));
      }

   }

   public void pin(Query q, Object[] params) {
      if (this._cache.getDelegate() != null) {
         QueryImpl impl = (QueryImpl)q;
         this._cache.pin(QueryKey.newInstance(impl.getDelegate(), impl.toKodoParameters(params)));
      }

   }

   public void pin(Query q, Map params) {
      if (this._cache.getDelegate() != null) {
         QueryImpl impl = (QueryImpl)q;
         this._cache.pin(QueryKey.newInstance(impl.getDelegate(), impl.toKodoParameters(params)));
      }

   }

   public void unpin(Query q) {
      if (this._cache.getDelegate() != null) {
         this._cache.unpin(QueryKey.newInstance(((QueryImpl)q).getDelegate()));
      }

   }

   public void unpin(Query q, Object[] params) {
      if (this._cache.getDelegate() != null) {
         QueryImpl impl = (QueryImpl)q;
         this._cache.unpin(QueryKey.newInstance(impl.getDelegate(), impl.toKodoParameters(params)));
      }

   }

   public void unpin(Query q, Map params) {
      if (this._cache.getDelegate() != null) {
         QueryImpl impl = (QueryImpl)q;
         this._cache.unpin(QueryKey.newInstance(impl.getDelegate(), impl.toKodoParameters(params)));
      }

   }

   public void evict(Query q) {
      if (this._cache.getDelegate() != null) {
         this._cache.remove(QueryKey.newInstance(((QueryImpl)q).getDelegate()));
      }

   }

   public void evict(Query q, Object[] params) {
      if (this._cache.getDelegate() != null) {
         QueryImpl impl = (QueryImpl)q;
         this._cache.remove(QueryKey.newInstance(impl.getDelegate(), impl.toKodoParameters(params)));
      }

   }

   public void evict(Query q, Map params) {
      if (this._cache.getDelegate() != null) {
         QueryImpl impl = (QueryImpl)q;
         this._cache.remove(QueryKey.newInstance(impl.getDelegate(), impl.toKodoParameters(params)));
      }

   }

   public void evictAll() {
      this._cache.clear();
   }

   public void evictAll(Class cls) {
      if (this._cache.getDelegate() != null) {
         this._cache.onTypesChanged(new TypesChangedEvent(this, Collections.singleton(cls)));
      }

   }

   public int hashCode() {
      return this._cache.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof QueryResultCache) ? false : this._cache.equals(((QueryResultCache)other)._cache);
      }
   }
}
