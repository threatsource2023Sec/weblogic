package org.apache.openjpa.persistence;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import org.apache.openjpa.datacache.DelegatingDataCache;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;

public class StoreCacheImpl implements StoreCache {
   private final MetaDataRepository _repos;
   private final DelegatingDataCache _cache;

   public StoreCacheImpl(EntityManagerFactoryImpl emf, org.apache.openjpa.datacache.DataCache cache) {
      this._repos = emf.getConfiguration().getMetaDataRepositoryInstance();
      this._cache = new DelegatingDataCache(cache, PersistenceExceptions.TRANSLATOR);
   }

   public org.apache.openjpa.datacache.DataCache getDelegate() {
      return this._cache.getDelegate();
   }

   public boolean contains(Class cls, Object oid) {
      return this._cache.getDelegate() != null && this._cache.contains(JPAFacadeHelper.toOpenJPAObjectId(this.getMetaData(cls), oid));
   }

   public boolean containsAll(Class cls, Object... oids) {
      return this.containsAll(cls, (Collection)Arrays.asList(oids));
   }

   public boolean containsAll(Class cls, Collection oids) {
      if (this._cache.getDelegate() == null) {
         return oids.isEmpty();
      } else {
         BitSet set = this._cache.containsAll(JPAFacadeHelper.toOpenJPAObjectIds(this.getMetaData(cls), oids));

         for(int i = 0; i < oids.size(); ++i) {
            if (!set.get(i)) {
               return false;
            }
         }

         return true;
      }
   }

   public void pin(Class cls, Object oid) {
      if (this._cache.getDelegate() != null) {
         this._cache.pin(JPAFacadeHelper.toOpenJPAObjectId(this.getMetaData(cls), oid));
      }

   }

   public void pinAll(Class cls, Object... oids) {
      this.pinAll(cls, (Collection)Arrays.asList(oids));
   }

   public void pinAll(Class cls, Collection oids) {
      if (this._cache.getDelegate() != null) {
         this._cache.pinAll(JPAFacadeHelper.toOpenJPAObjectIds(this.getMetaData(cls), oids));
      }

   }

   public void unpin(Class cls, Object oid) {
      if (this._cache.getDelegate() != null) {
         this._cache.unpin(JPAFacadeHelper.toOpenJPAObjectId(this.getMetaData(cls), oid));
      }

   }

   public void unpinAll(Class cls, Object... oids) {
      this.unpinAll(cls, (Collection)Arrays.asList(oids));
   }

   public void unpinAll(Class cls, Collection oids) {
      if (this._cache.getDelegate() != null) {
         this._cache.unpinAll(JPAFacadeHelper.toOpenJPAObjectIds(this.getMetaData(cls), oids));
      }

   }

   public void evict(Class cls, Object oid) {
      if (this._cache.getDelegate() != null) {
         this._cache.remove(JPAFacadeHelper.toOpenJPAObjectId(this.getMetaData(cls), oid));
      }

   }

   public void evictAll(Class cls, Object... oids) {
      this.evictAll(cls, (Collection)Arrays.asList(oids));
   }

   public void evictAll(Class cls, Collection oids) {
      if (this._cache.getDelegate() != null) {
         this._cache.removeAll(JPAFacadeHelper.toOpenJPAObjectIds(this.getMetaData(cls), oids));
      }

   }

   public void evictAll() {
      this._cache.clear();
   }

   private ClassMetaData getMetaData(Class cls) {
      try {
         return this._repos.getMetaData((Class)cls, (ClassLoader)null, true);
      } catch (RuntimeException var3) {
         throw PersistenceExceptions.toPersistenceException(var3);
      }
   }

   public int hashCode() {
      return this._cache.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof StoreCacheImpl) ? false : this._cache.equals(((StoreCacheImpl)other)._cache);
      }
   }
}
