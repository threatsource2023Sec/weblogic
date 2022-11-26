package weblogic.cache.query.filter.index;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.cache.CacheRuntimeException;

public class IndexDescription {
   private final Map indexes = new HashMap();
   final Map roIndexes;
   final IndexListener listener;

   IndexDescription() {
      this.roIndexes = Collections.unmodifiableMap(Collections.synchronizedMap(this.indexes));
      this.listener = new IndexListener(this.indexes);
   }

   void addIndex(IndexExtractor extractor, Map index) {
      synchronized(this.indexes) {
         if (!this.indexes.containsKey(extractor)) {
            this.indexes.put(extractor, index);
         } else {
            throw new CacheRuntimeException("Duplicate index extractor, " + extractor);
         }
      }
   }

   int getNoIndexes() {
      return this.indexes.size();
   }

   void removeIndex(IndexExtractor extractor) {
      synchronized(this.indexes) {
         this.indexes.remove(extractor);
      }
   }
}
