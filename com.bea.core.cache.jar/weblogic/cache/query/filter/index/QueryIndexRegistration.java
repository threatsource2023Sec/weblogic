package weblogic.cache.query.filter.index;

import java.util.HashMap;
import java.util.Map;
import weblogic.cache.CacheMap;

class QueryIndexRegistration extends IndexRegistration {
   static final QueryIndexRegistration instance = new QueryIndexRegistration();
   private final Map indexes = new HashMap();

   public void addIndex(CacheMap cache, IndexExtractor extractor, Map index) {
      IndexDescription id;
      synchronized(this.indexes) {
         id = (IndexDescription)this.indexes.get(cache);
         if (id == null) {
            id = new IndexDescription();
            this.indexes.put(cache, id);
         }
      }

      synchronized(id) {
         id.addIndex(extractor, index);
         if (id.getNoIndexes() == 1) {
            cache.addChangeListener(id.listener);
            cache.addCacheLoadListener(id.listener);
            cache.addEvictionListener(id.listener);
         }
      }

      id.listener.rebuild(cache, extractor);
   }

   public Map getIndexes(CacheMap cache) {
      IndexDescription id;
      synchronized(this.indexes) {
         id = (IndexDescription)this.indexes.get(cache);
      }

      return id != null ? id.roIndexes : null;
   }

   public void removeIndex(CacheMap cache, IndexExtractor extractor) {
      IndexDescription id;
      synchronized(this.indexes) {
         id = (IndexDescription)this.indexes.get(cache);
      }

      synchronized(id) {
         if (id != null) {
            id.removeIndex(extractor);
            if (id.getNoIndexes() == 0) {
               cache.removeEvictionListener(id.listener);
               cache.removeCacheLoadListener(id.listener);
               cache.removeChangeListener(id.listener);
            }
         }
      }

      id.listener.clear(extractor);
   }
}
