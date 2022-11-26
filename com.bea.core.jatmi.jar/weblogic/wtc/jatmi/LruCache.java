package weblogic.wtc.jatmi;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache extends LinkedHashMap {
   private int max_entries;

   LruCache(int max) {
      super((max + 1) / 2, 2.0F, true);
      this.max_entries = max;
   }

   protected boolean removeEldestEntry(Map.Entry eldest) {
      return this.size() > this.max_entries;
   }

   public void setCacheSize(int size) {
      this.max_entries = size;
   }
}
