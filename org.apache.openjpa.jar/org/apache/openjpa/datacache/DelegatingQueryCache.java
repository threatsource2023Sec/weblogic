package org.apache.openjpa.datacache;

import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingQueryCache implements QueryCache {
   private final QueryCache _cache;
   private final DelegatingQueryCache _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingQueryCache(QueryCache cache) {
      this(cache, (RuntimeExceptionTranslator)null);
   }

   public DelegatingQueryCache(QueryCache cache, RuntimeExceptionTranslator trans) {
      this._cache = cache;
      this._trans = trans;
      if (cache instanceof DelegatingQueryCache) {
         this._del = (DelegatingQueryCache)this._cache;
      } else {
         this._del = null;
      }

   }

   public QueryCache getDelegate() {
      return this._cache;
   }

   public QueryCache getInnermostDelegate() {
      return this._del == null ? this._cache : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingQueryCache) {
            other = ((DelegatingQueryCache)other).getInnermostDelegate();
         }

         return ObjectUtils.equals(this.getInnermostDelegate(), other);
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public void initialize(DataCacheManager mgr) {
      if (this._cache != null) {
         try {
            this._cache.initialize(mgr);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public void onTypesChanged(TypesChangedEvent e) {
      if (this._cache != null) {
         try {
            this._cache.onTypesChanged(e);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public QueryResult get(QueryKey qk) {
      if (this._cache == null) {
         return null;
      } else {
         try {
            return this._cache.get(qk);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public QueryResult put(QueryKey qk, QueryResult oids) {
      if (this._cache == null) {
         return null;
      } else {
         try {
            return this._cache.put(qk, oids);
         } catch (RuntimeException var4) {
            throw this.translate(var4);
         }
      }
   }

   public QueryResult remove(QueryKey qk) {
      if (this._cache == null) {
         return null;
      } else {
         try {
            return this._cache.remove(qk);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
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

   public boolean pin(QueryKey qk) {
      if (this._cache == null) {
         return false;
      } else {
         try {
            return this._cache.pin(qk);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public boolean unpin(QueryKey qk) {
      if (this._cache == null) {
         return false;
      } else {
         try {
            return this._cache.unpin(qk);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
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

   public void addTypesChangedListener(TypesChangedListener listen) {
      if (this._cache != null) {
         try {
            this._cache.addTypesChangedListener(listen);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   public boolean removeTypesChangedListener(TypesChangedListener listen) {
      if (this._cache == null) {
         return false;
      } else {
         try {
            return this._cache.removeTypesChangedListener(listen);
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
}
