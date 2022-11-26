package kodo.datacache;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.datacache.DataCacheManager;
import org.apache.openjpa.datacache.DataCachePCData;
import org.apache.openjpa.datacache.ExpirationListener;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.StoreException;

public class FederatingDataCache implements DataCache {
   private static final BitSet EMPTY_BITSET = new BitSet(0);
   private static final Localizer _loc = Localizer.forPackage(FederatingDataCache.class);
   private final KodoDataCacheManager _dcm;
   private final MetaDataRepository _repos;

   public FederatingDataCache(OpenJPAConfiguration conf, KodoDataCacheManager dcm) {
      this._dcm = dcm;
      this._repos = conf.getMetaDataRepositoryInstance();
   }

   public String getName() {
      return "Federating";
   }

   public void setName(String name) {
      throw new UnsupportedOperationException();
   }

   public void initialize(DataCacheManager manager) {
      throw new UnsupportedOperationException();
   }

   public void commit(Collection additions, Collection newUpdates, Collection existingUpdates, Collection deletes) {
      throw new UnsupportedOperationException();
   }

   public boolean contains(Object oid) {
      if (oid == null) {
         return false;
      } else {
         DataCache cache = this.getDataCache(oid);
         return cache != null && cache.contains(oid);
      }
   }

   public BitSet containsAll(Collection oids) {
      if (this._dcm.size() != 0 && !oids.isEmpty()) {
         DataCache cache = this.getDataCache();
         if (cache != null) {
            return cache.containsAll(oids);
         } else {
            BitSet set = new BitSet(oids.size());
            int i = 0;

            for(Iterator itr = oids.iterator(); itr.hasNext(); ++i) {
               if (this.contains(itr.next())) {
                  set.set(i);
               }
            }

            return set;
         }
      } else {
         return EMPTY_BITSET;
      }
   }

   public DataCachePCData get(Object oid) {
      if (oid == null) {
         return null;
      } else {
         DataCache cache = this.getDataCache();
         return cache == null ? null : cache.get(oid);
      }
   }

   public DataCachePCData put(DataCachePCData value) {
      if (value == null) {
         return null;
      } else {
         DataCache cache = this.getDataCache(value.getId());
         return cache == null ? null : cache.put(value);
      }
   }

   public void update(DataCachePCData value) {
      if (value != null) {
         DataCache cache = this.getDataCache(value.getId());
         if (cache != null) {
            cache.update(value);
         }

      }
   }

   public DataCachePCData remove(Object oid) {
      if (oid == null) {
         return null;
      } else {
         DataCache cache = this.getDataCache(oid);
         return cache == null ? null : cache.remove(oid);
      }
   }

   public BitSet removeAll(Collection oids) {
      if (this._dcm.size() != 0 && !oids.isEmpty()) {
         DataCache cache = this.getDataCache();
         if (cache != null) {
            return cache.removeAll(oids);
         } else {
            BitSet set = new BitSet();
            List exceps = null;
            int i = 0;

            for(Iterator itr = oids.iterator(); itr.hasNext(); ++i) {
               try {
                  if (this.remove(itr.next()) != null) {
                     set.set(i);
                  }
               } catch (Exception var8) {
                  exceps = this.addException(exceps, var8);
               }
            }

            this.throwExceptions(exceps);
            return set;
         }
      } else {
         return EMPTY_BITSET;
      }
   }

   public void removeAll(Class cls, boolean subclasses) {
      if (this._dcm.size() != 0 && cls != null) {
         if (this._dcm.size() == 1) {
            this._dcm.getDefaultDataCache().removeAll(cls, subclasses);
         } else {
            ClassMetaData meta = this._repos.getMetaData(cls, (ClassLoader)null, false);
            if (meta != null) {
               DataCache cache = meta.getDataCache();
               if (cache != null) {
                  cache.removeAll(cls, subclasses);
               }

            }
         }
      }
   }

   public void clear() {
      if (this._dcm.size() != 0) {
         String[] names = this._dcm.getDataCacheNames();
         List exceps = null;

         for(int i = 0; i < names.length; ++i) {
            try {
               DataCache cache = this._dcm.getDataCache(names[i], false);
               if (cache != null) {
                  cache.clear();
               }
            } catch (Exception var6) {
               exceps = this.addException(exceps, var6);
            }
         }

         this.throwExceptions(exceps);
      }
   }

   public boolean pin(Object oid) {
      if (oid == null) {
         return false;
      } else {
         DataCache cache = this.getDataCache(oid);
         return cache != null && cache.pin(oid);
      }
   }

   public BitSet pinAll(Collection oids) {
      if (this._dcm.size() != 0 && !oids.isEmpty()) {
         DataCache cache = this.getDataCache();
         if (cache != null) {
            return cache.pinAll(oids);
         } else {
            List exceps = null;
            BitSet set = new BitSet();
            int i = 0;

            for(Iterator itr = oids.iterator(); itr.hasNext(); ++i) {
               try {
                  if (this.pin(itr.next())) {
                     set.set(i);
                  }
               } catch (Exception var8) {
                  exceps = this.addException(exceps, var8);
               }
            }

            this.throwExceptions(exceps);
            return set;
         }
      } else {
         return EMPTY_BITSET;
      }
   }

   public void pinAll(Class cls, boolean subs) {
      if (this._dcm.size() != 0) {
         DataCache cache = this.getDataCache(cls);
         if (cache != null) {
            cache.pinAll(cls, subs);
         }

      }
   }

   public boolean unpin(Object oid) {
      if (oid == null) {
         return false;
      } else {
         DataCache cache = this.getDataCache(oid);
         return cache != null && cache.unpin(oid);
      }
   }

   public BitSet unpinAll(Collection oids) {
      if (this._dcm.size() != 0 && !oids.isEmpty()) {
         DataCache cache = this.getDataCache();
         if (cache != null) {
            return cache.unpinAll(oids);
         } else {
            BitSet set = new BitSet();
            List exceps = null;
            int i = 0;

            for(Iterator itr = oids.iterator(); itr.hasNext(); ++i) {
               try {
                  if (this.unpin(itr.next())) {
                     set.set(i);
                  }
               } catch (Exception var8) {
                  exceps = this.addException(exceps, var8);
               }
            }

            this.throwExceptions(exceps);
            return set;
         }
      } else {
         return EMPTY_BITSET;
      }
   }

   public void unpinAll(Class cls, boolean subs) {
      if (this._dcm.size() != 0) {
         DataCache cache = this.getDataCache(cls);
         if (cache != null) {
            cache.unpinAll(cls, subs);
         }

      }
   }

   public void writeLock() {
      if (this._dcm.size() != 0) {
         String[] names = this._dcm.getDataCacheNames();

         for(int i = 0; i < names.length; ++i) {
            try {
               DataCache cache = this._dcm.getDataCache(names[i], false);
               if (cache != null) {
                  cache.writeLock();
               }
            } catch (RuntimeException var8) {
               for(int j = 0; j < i; ++j) {
                  try {
                     this._dcm.getDataCache(names[j], false).writeUnlock();
                  } catch (Exception var7) {
                  }
               }

               throw var8;
            }
         }

      }
   }

   public void writeUnlock() {
      if (this._dcm.size() != 0) {
         String[] names = this._dcm.getDataCacheNames();
         List exceps = null;

         for(int i = 0; i < names.length; ++i) {
            try {
               DataCache cache = this._dcm.getDataCache(names[i], false);
               if (cache != null) {
                  cache.writeUnlock();
               }
            } catch (Exception var6) {
               exceps = this.addException(exceps, var6);
            }
         }

         this.throwExceptions(exceps);
      }
   }

   public void addExpirationListener(ExpirationListener listen) {
      if (this._dcm.size() != 0 && listen != null) {
         String[] names = this._dcm.getDataCacheNames();
         List exceps = null;

         for(int i = 0; i < names.length; ++i) {
            try {
               DataCache cache = this._dcm.getDataCache(names[i], false);
               if (cache != null) {
                  cache.addExpirationListener(listen);
               }
            } catch (Exception var7) {
               exceps = this.addException(exceps, var7);
            }
         }

         this.throwExceptions(exceps);
      }
   }

   public boolean removeExpirationListener(ExpirationListener listen) {
      if (this._dcm.size() != 0 && listen != null) {
         String[] names = this._dcm.getDataCacheNames();
         boolean rem = false;
         List exceps = null;

         for(int i = 0; i < names.length; ++i) {
            try {
               DataCache cache = this._dcm.getDataCache(names[i], false);
               if (cache != null) {
                  rem |= cache.removeExpirationListener(listen);
               }
            } catch (Exception var8) {
               exceps = this.addException(exceps, var8);
            }
         }

         this.throwExceptions(exceps);
         return rem;
      } else {
         return false;
      }
   }

   public void close() {
      String[] names = this._dcm.getDataCacheNames();
      List exceps = null;

      for(int i = 0; i < names.length; ++i) {
         try {
            DataCache cache = this._dcm.getDataCache(names[i], false);
            if (cache != null) {
               cache.clear();
            }
         } catch (Exception var6) {
            exceps = this.addException(exceps, var6);
         }
      }

      this.throwExceptions(exceps);
   }

   public Map getAll(List list) {
      throw new UnsupportedOperationException();
   }

   private List addException(List exceps, Exception e) {
      if (exceps == null) {
         exceps = new LinkedList();
      }

      ((List)exceps).add(e);
      return (List)exceps;
   }

   private void throwExceptions(List exceps) {
      if (exceps != null) {
         if (exceps.size() == 1) {
            throw (RuntimeException)exceps.get(0);
         } else {
            throw (new StoreException(_loc.get("nested-exceps"))).setNestedThrowables((Throwable[])((Throwable[])exceps.toArray(new Throwable[exceps.size()])));
         }
      }
   }

   private DataCache getDataCache(Object oid) {
      if (this._dcm.size() == 0) {
         return null;
      } else {
         ClassMetaData meta = this._repos.getMetaData(oid, (ClassLoader)null, false);
         return meta == null ? null : meta.getDataCache();
      }
   }

   private DataCache getDataCache() {
      return this._dcm.size() != 1 ? null : this._dcm.getDefaultDataCache();
   }
}
