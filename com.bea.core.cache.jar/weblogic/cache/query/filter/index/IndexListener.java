package weblogic.cache.query.filter.index;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.cache.CacheEntry;
import weblogic.cache.CacheLoadListener;
import weblogic.cache.CacheMap;
import weblogic.cache.ChangeListener;
import weblogic.cache.EvictionListener;

public class IndexListener implements ChangeListener, EvictionListener, CacheLoadListener {
   private final Map indexes;

   IndexListener(Map indexes) {
      this.indexes = indexes;
   }

   public void onClear() {
      synchronized(this.indexes) {
         Iterator var2 = this.indexes.values().iterator();

         while(var2.hasNext()) {
            Map index = (Map)var2.next();
            index.clear();
         }

      }
   }

   public void onCreate(CacheEntry entry) {
      synchronized(this.indexes) {
         Iterator var3 = this.indexes.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry index = (Map.Entry)var3.next();
            Object indexKey = ((IndexExtractor)index.getKey()).extract(entry.getKey(), entry.getValue());
            this.addKey(indexKey, entry.getKey(), (Map)index.getValue());
         }

      }
   }

   public void onDelete(CacheEntry entry) {
      synchronized(this.indexes) {
         Iterator var3 = this.indexes.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry index = (Map.Entry)var3.next();
            Object indexKey = ((IndexExtractor)index.getKey()).extract(entry.getKey(), entry.getValue());
            this.removeKey(indexKey, entry.getKey(), (Map)index.getValue());
         }

      }
   }

   public void onUpdate(CacheEntry entry, Object oldValue) {
      synchronized(this.indexes) {
         Iterator var4 = this.indexes.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry index = (Map.Entry)var4.next();
            Object oldIndexKey = ((IndexExtractor)index.getKey()).extract(entry.getKey(), oldValue);
            Object indexKey = ((IndexExtractor)index.getKey()).extract(entry.getKey(), entry.getValue());
            this.removeKey(oldIndexKey, entry.getKey(), (Map)index.getValue());
            this.addKey(indexKey, entry.getKey(), (Map)index.getValue());
         }

      }
   }

   public void onEviction(Map evictedMap) {
      Iterator var2 = evictedMap.entrySet().iterator();

      while(var2.hasNext()) {
         Object entry = var2.next();
         synchronized(this.indexes) {
            Iterator var5 = this.indexes.entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry index = (Map.Entry)var5.next();
               Object indexKey = ((IndexExtractor)index.getKey()).extract(((Map.Entry)entry).getKey(), ((Map.Entry)entry).getValue());
               this.removeKey(indexKey, ((Map.Entry)entry).getKey(), (Map)index.getValue());
            }
         }
      }

   }

   public void onLoadError(Collection keys, Throwable error) {
   }

   public void onLoad(Map loadedMap) {
      Iterator var2 = loadedMap.entrySet().iterator();

      while(var2.hasNext()) {
         Object entry = var2.next();
         synchronized(this.indexes) {
            Iterator var5 = this.indexes.entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry index = (Map.Entry)var5.next();
               Object indexKey = ((IndexExtractor)index.getKey()).extract(((Map.Entry)entry).getKey(), ((Map.Entry)entry).getValue());
               this.addKey(indexKey, ((Map.Entry)entry).getKey(), (Map)index.getValue());
            }
         }
      }

   }

   void clear(IndexExtractor extractor) {
      synchronized(this.indexes) {
         Map index = (Map)this.indexes.get(extractor);
         if (index != null) {
            index.clear();
         }

      }
   }

   void rebuild(CacheMap cache, IndexExtractor extractor) {
      synchronized(this.indexes) {
         Map index = (Map)this.indexes.get(extractor);
         if (index != null) {
            index.clear();
            Iterator var5 = cache.entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry entry = (Map.Entry)var5.next();
               Object indexKey = extractor.extract(entry.getKey(), entry.getValue());
               this.addKey(indexKey, entry.getKey(), index);
            }
         }

      }
   }

   private void addKey(Object indexKey, Object key, Map index) {
      Set keys = this.getKeys(indexKey, index);
      keys.add(key);
   }

   private Set getKeys(Object indexKey, Map index) {
      Set keys = (Set)index.get(indexKey);
      if (keys == null) {
         keys = Collections.synchronizedSet(new HashSet());
         index.put(indexKey, keys);
      }

      return keys;
   }

   private void removeKey(Object indexKey, Object key, Map index) {
      Set keys = (Set)index.get(indexKey);
      if (keys != null) {
         keys.remove(key);
         if (keys.size() == 0) {
            index.remove(indexKey);
         }
      }

   }
}
