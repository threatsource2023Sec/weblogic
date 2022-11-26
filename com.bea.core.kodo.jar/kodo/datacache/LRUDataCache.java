package kodo.datacache;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kodo.util.MonitoringCacheMap;
import org.apache.openjpa.datacache.AbstractDataCache;
import org.apache.openjpa.datacache.DataCacheManager;
import org.apache.openjpa.datacache.DataCachePCData;
import org.apache.openjpa.event.RemoteCommitListener;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.ReferenceHashSet;
import org.apache.openjpa.util.CacheMap;
import org.apache.openjpa.util.UnsupportedException;

public class LRUDataCache extends AbstractDataCache implements RemoteCommitListener {
   private static final Localizer s_loc = Localizer.forPackage(LRUDataCache.class);
   private final ClassRefLRUCacheMap _cache = new ClassRefLRUCacheMap();

   public CacheMap getCacheMap() {
      return this._cache;
   }

   public void setCacheSize(int size) {
      this._cache.setCacheSize(size);
   }

   public int getCacheSize() {
      return this._cache.getCacheSize();
   }

   public void setSoftReferenceSize(int size) {
      this._cache.setSoftReferenceSize(size);
   }

   public int getSoftReferenceSize() {
      return this._cache.getSoftReferenceSize();
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

   protected DataCachePCData getInternal(Object key) {
      return (DataCachePCData)this._cache.get(key);
   }

   protected DataCachePCData putInternal(Object key, DataCachePCData pc) {
      return (DataCachePCData)this._cache.put(key, pc);
   }

   protected DataCachePCData removeInternal(Object key) {
      return (DataCachePCData)this._cache.remove(key);
   }

   protected void removeAllInternal(Class cls, boolean subs) {
      if (subs) {
         throw new UnsupportedException(s_loc.get("removeall-byclass"));
      } else {
         this._cache.removeAll(cls);
      }
   }

   protected void clearInternal() {
      this._cache.clear();
   }

   protected boolean pinInternal(Object key) {
      return this._cache.pin(key);
   }

   protected boolean unpinInternal(Object key) {
      return this._cache.unpin(key);
   }

   protected void keyRemoved(Object key, boolean expired) {
      super.keyRemoved(key, expired);
   }

   private class ClassRefLRUCacheMap extends MonitoringCacheMap {
      private final Map classMap = new HashMap();

      public ClassRefLRUCacheMap() {
         super(true, 1000);
      }

      public void removeAll(Class cls) {
         this.writeLock();

         try {
            Collection oids = (Collection)this.classMap.remove(cls);
            if (oids != null) {
               Iterator itr = oids.iterator();

               while(itr.hasNext()) {
                  Object oid = itr.next();
                  itr.remove();
                  if (oid != null) {
                     this.remove(oid);
                  }
               }
            }
         } finally {
            this.writeUnlock();
         }

      }

      public void clear() {
         this.writeLock();

         try {
            super.clear();
            this.classMap.clear();
         } finally {
            this.writeUnlock();
         }

      }

      public void setSoftReferenceSize(int size) {
         this.writeLock();

         try {
            int oldSize = this.getSoftReferenceSize();
            Map.Entry entry;
            if (oldSize == 0 && size != 0) {
               Iterator itrx = this.classMap.entrySet().iterator();

               while(itrx.hasNext()) {
                  entry = (Map.Entry)itrx.next();
                  Set copy = new ReferenceHashSet(2);
                  copy.addAll((Set)entry.getValue());
                  entry.setValue(copy);
               }
            } else if (oldSize != 0 && size == 0) {
               Iterator itr = this.classMap.entrySet().iterator();

               while(itr.hasNext()) {
                  entry = (Map.Entry)itr.next();
                  Set orig = (Set)entry.getValue();
                  Set copyx = new HashSet();
                  Iterator oids = orig.iterator();

                  while(oids.hasNext()) {
                     copyx.add(oids.next());
                  }

                  entry.setValue(copyx);
               }
            }

            super.setSoftReferenceSize(size);
         } finally {
            this.writeUnlock();
         }

      }

      protected void entryRemoved(Object key, Object value, boolean expired) {
         LRUDataCache.this.keyRemoved(key, expired);
         if (value != null) {
            Class cls = ((DataCachePCData)value).getType();
            Set oids = (Set)this.classMap.get(cls);
            if (oids != null) {
               oids.remove(key);
            }

         }
      }

      protected void entryAdded(Object key, Object value) {
         super.entryAdded(key, value);
         if (value != null) {
            Class cls = ((DataCachePCData)value).getType();
            Set oids = (Set)this.classMap.get(cls);
            if (oids == null) {
               if (this.getSoftReferenceSize() == 0) {
                  oids = new HashSet();
               } else {
                  oids = new ReferenceHashSet(2);
               }

               this.classMap.put(cls, oids);
            }

            ((Set)oids).add(key);
         }
      }
   }
}
