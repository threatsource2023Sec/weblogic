package org.apache.openjpa.datacache;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingDataCache implements DataCache {
   private static final BitSet EMPTY_BITSET = new BitSet(0);
   private final DataCache _cache;
   private final DelegatingDataCache _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingDataCache(DataCache cache) {
      this(cache, (RuntimeExceptionTranslator)null);
   }

   public DelegatingDataCache(DataCache cache, RuntimeExceptionTranslator trans) {
      this._cache = cache;
      this._trans = trans;
      if (cache instanceof DelegatingDataCache) {
         this._del = (DelegatingDataCache)this._cache;
      } else {
         this._del = null;
      }

   }

   public DataCache getDelegate() {
      return this._cache;
   }

   public DataCache getInnermostDelegate() {
      return this._del == null ? this._cache : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this._cache == null ? super.hashCode() : this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingDataCache) {
            other = ((DelegatingDataCache)other).getInnermostDelegate();
         }

         return ObjectUtils.equals(this.getInnermostDelegate(), other);
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public String getName() {
      if (this._cache == null) {
         return null;
      } else {
         try {
            return this._cache.getName();
         } catch (RuntimeException var2) {
            throw this.translate(var2);
         }
      }
   }

   public void setName(String name) {
      if (this._cache != null) {
         try {
            this._cache.setName(name);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public void initialize(DataCacheManager manager) {
      if (this._cache != null) {
         try {
            this._cache.initialize(manager);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public void commit(Collection additions, Collection newUpdates, Collection existingUpdates, Collection deletes) {
      if (this._cache != null) {
         try {
            this._cache.commit(additions, newUpdates, existingUpdates, deletes);
         } catch (RuntimeException var6) {
            throw this.translate(var6);
         }
      }
   }

   public boolean contains(Object oid) {
      if (this._cache == null) {
         return false;
      } else {
         try {
            return this._cache.contains(oid);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public BitSet containsAll(Collection oids) {
      if (this._cache == null) {
         return EMPTY_BITSET;
      } else {
         try {
            return this._cache.containsAll(oids);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public DataCachePCData get(Object oid) {
      if (this._cache == null) {
         return null;
      } else {
         try {
            return this._cache.get(oid);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public DataCachePCData put(DataCachePCData value) {
      if (this._cache == null) {
         return null;
      } else {
         try {
            return this._cache.put(value);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public void update(DataCachePCData value) {
      if (this._cache != null) {
         try {
            this._cache.update(value);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public DataCachePCData remove(Object oid) {
      if (this._cache == null) {
         return null;
      } else {
         try {
            return this._cache.remove(oid);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public BitSet removeAll(Collection oids) {
      if (this._cache == null) {
         return EMPTY_BITSET;
      } else {
         try {
            return this._cache.removeAll(oids);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public void removeAll(Class cls, boolean subclasses) {
      if (this._cache != null) {
         try {
            this._cache.removeAll(cls, subclasses);
         } catch (RuntimeException var4) {
            throw this.translate(var4);
         }
      }
   }

   public void clear() {
      if (this._cache != null) {
         try {
            this._cache.clear();
         } catch (RuntimeException var2) {
            throw this.translate(var2);
         }
      }
   }

   public boolean pin(Object oid) {
      if (this._cache == null) {
         return false;
      } else {
         try {
            return this._cache.pin(oid);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public BitSet pinAll(Collection oids) {
      if (this._cache == null) {
         return EMPTY_BITSET;
      } else {
         try {
            return this._cache.pinAll(oids);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public void pinAll(Class cls, boolean subs) {
      if (this._cache != null) {
         try {
            this._cache.pinAll(cls, subs);
         } catch (RuntimeException var4) {
            throw this.translate(var4);
         }
      }
   }

   public boolean unpin(Object oid) {
      if (this._cache == null) {
         return false;
      } else {
         try {
            return this._cache.unpin(oid);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public BitSet unpinAll(Collection oids) {
      if (this._cache == null) {
         return EMPTY_BITSET;
      } else {
         try {
            return this._cache.unpinAll(oids);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public void unpinAll(Class cls, boolean subs) {
      if (this._cache != null) {
         try {
            this._cache.unpinAll(cls, subs);
         } catch (RuntimeException var4) {
            throw this.translate(var4);
         }
      }
   }

   public void writeLock() {
      if (this._cache != null) {
         try {
            this._cache.writeLock();
         } catch (RuntimeException var2) {
            throw this.translate(var2);
         }
      }
   }

   public void writeUnlock() {
      if (this._cache != null) {
         try {
            this._cache.writeUnlock();
         } catch (RuntimeException var2) {
            throw this.translate(var2);
         }
      }
   }

   public void addExpirationListener(ExpirationListener listen) {
      if (this._cache != null) {
         try {
            this._cache.addExpirationListener(listen);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public boolean removeExpirationListener(ExpirationListener listen) {
      if (this._cache == null) {
         return false;
      } else {
         try {
            return this._cache.removeExpirationListener(listen);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public void close() {
      if (this._cache != null) {
         try {
            this._cache.close();
         } catch (RuntimeException var2) {
            throw this.translate(var2);
         }
      }
   }

   public Map getAll(List keys) {
      if (this._cache == null) {
         return null;
      } else {
         try {
            return this._cache.getAll(keys);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }
}
