package kodo.persistence;

import javax.persistence.Query;
import org.apache.openjpa.datacache.QueryCache;

/** @deprecated */
public class QueryResultCacheImpl implements QueryResultCache {
   private final org.apache.openjpa.persistence.QueryResultCacheImpl _delegate;

   QueryResultCacheImpl(org.apache.openjpa.persistence.QueryResultCacheImpl del) {
      this._delegate = del;
   }

   public QueryCache getDelegate() {
      return this._delegate.getDelegate();
   }

   public void pin(Query q) {
      this._delegate.pin(q);
   }

   public void unpin(Query q) {
      this._delegate.unpin(q);
   }

   public void evict(Query q) {
      this._delegate.evict(q);
   }

   public void evictAll() {
      this._delegate.evictAll();
   }

   public void evictAll(Class cls) {
      this._delegate.evictAll(cls);
   }

   public int hashCode() {
      return this._delegate.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof QueryResultCacheImpl) ? false : this._delegate.equals(((QueryResultCacheImpl)other)._delegate);
      }
   }
}
