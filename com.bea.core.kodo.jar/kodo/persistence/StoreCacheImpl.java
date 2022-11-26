package kodo.persistence;

import java.util.Arrays;
import java.util.Collection;
import org.apache.openjpa.datacache.DataCache;

/** @deprecated */
public class StoreCacheImpl implements StoreCache {
   private final org.apache.openjpa.persistence.StoreCacheImpl _delegate;

   public StoreCacheImpl(org.apache.openjpa.persistence.StoreCacheImpl del) {
      this._delegate = del;
   }

   public DataCache getDelegate() {
      return this._delegate.getDelegate();
   }

   public boolean contains(Class cls, Object oid) {
      return this._delegate.contains(cls, oid);
   }

   public boolean containsAll(Class cls, Object... oids) {
      return this._delegate.containsAll(cls, Arrays.asList(oids));
   }

   public boolean containsAll(Class cls, Collection oids) {
      return this._delegate.containsAll(cls, oids);
   }

   public void pin(Class cls, Object oid) {
      this._delegate.pin(cls, oid);
   }

   public void pinAll(Class cls, Object... oids) {
      this._delegate.pinAll(cls, oids);
   }

   public void pinAll(Class cls, Collection oids) {
      this._delegate.pinAll(cls, oids);
   }

   public void unpin(Class cls, Object oid) {
      this._delegate.unpin(cls, oid);
   }

   public void unpinAll(Class cls, Object... oids) {
      this._delegate.unpinAll(cls, oids);
   }

   public void unpinAll(Class cls, Collection oids) {
      this._delegate.unpinAll(cls, oids);
   }

   public void evict(Class cls, Object oid) {
      this._delegate.evict(cls, oid);
   }

   public void evictAll(Class cls, Object... oids) {
      this._delegate.evictAll(cls, oids);
   }

   public void evictAll(Class cls, Collection oids) {
      this._delegate.evictAll(cls, oids);
   }

   public void evictAll() {
      this._delegate.evictAll();
   }

   public int hashCode() {
      return this._delegate.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof StoreCacheImpl) ? false : this._delegate.equals(((StoreCacheImpl)other)._delegate);
      }
   }
}
