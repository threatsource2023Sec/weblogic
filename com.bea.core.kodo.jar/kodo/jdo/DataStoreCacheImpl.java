package kodo.jdo;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import javax.jdo.PersistenceManager;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.datacache.DelegatingDataCache;

public class DataStoreCacheImpl implements KodoDataStoreCache {
   private final DelegatingDataCache _cache;

   public DataStoreCacheImpl(DataCache cache) {
      this._cache = new DelegatingDataCache(cache, JDOExceptions.TRANSLATOR);
   }

   public DataCache getDelegate() {
      return this._cache.getDelegate();
   }

   public boolean contains(Object oid) {
      return this._cache.getDelegate() != null && this._cache.contains(KodoJDOHelper.toKodoObjectId(oid, (PersistenceManager)null));
   }

   public boolean containsAll(Object[] oids) {
      return this.containsAll((Collection)Arrays.asList(oids));
   }

   public boolean containsAll(Collection oids) {
      if (this._cache.getDelegate() == null) {
         return oids.isEmpty();
      } else {
         BitSet set = this._cache.containsAll(KodoJDOHelper.toKodoObjectIds((Collection)oids, (PersistenceManager)null));

         for(int i = 0; i < oids.size(); ++i) {
            if (!set.get(i)) {
               return false;
            }
         }

         return true;
      }
   }

   public void pin(Object oid) {
      if (this._cache.getDelegate() != null) {
         this._cache.pin(KodoJDOHelper.toKodoObjectId(oid, (PersistenceManager)null));
      }

   }

   public void pinAll(Object[] oids) {
      this.pinAll((Collection)Arrays.asList(oids));
   }

   public void pinAll(Collection oids) {
      if (this._cache.getDelegate() != null) {
         this._cache.pinAll(KodoJDOHelper.toKodoObjectIds((Collection)oids, (PersistenceManager)null));
      }

   }

   public void pinAll(Class cls, boolean subs) {
      if (this._cache.getDelegate() != null) {
         this._cache.pinAll(cls, subs);
      }

   }

   public void unpin(Object oid) {
      if (this._cache.getDelegate() != null) {
         this._cache.unpin(KodoJDOHelper.toKodoObjectId(oid, (PersistenceManager)null));
      }

   }

   public void unpinAll(Object[] oids) {
      this.unpinAll((Collection)Arrays.asList(oids));
   }

   public void unpinAll(Collection oids) {
      if (this._cache.getDelegate() != null) {
         this._cache.unpinAll(KodoJDOHelper.toKodoObjectIds((Collection)oids, (PersistenceManager)null));
      }

   }

   public void unpinAll(Class cls, boolean subs) {
      if (this._cache.getDelegate() != null) {
         this._cache.unpinAll(cls, subs);
      }

   }

   public void evict(Object oid) {
      if (this._cache.getDelegate() != null) {
         this._cache.remove(KodoJDOHelper.toKodoObjectId(oid, (PersistenceManager)null));
      }

   }

   public void evictAll(Object[] oids) {
      this.evictAll((Collection)Arrays.asList(oids));
   }

   public void evictAll(Collection oids) {
      if (this._cache.getDelegate() != null) {
         this._cache.removeAll(KodoJDOHelper.toKodoObjectIds((Collection)oids, (PersistenceManager)null));
      }

   }

   public void evictAll() {
      this._cache.clear();
   }

   public void evictAll(Class cls, boolean subs) {
      if (this._cache.getDelegate() != null) {
         this._cache.removeAll(cls, subs);
      }

   }

   public int hashCode() {
      return this._cache.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof DataStoreCacheImpl) ? false : this._cache.equals(((DataStoreCacheImpl)other)._cache);
      }
   }
}
