package weblogic.cache.util;

import java.util.Map;

class WriteThroughCoherencyStrategy implements TieredMap.CoherencyStrategy {
   private final Map target;

   public WriteThroughCoherencyStrategy(Map m) {
      this.target = m;
   }

   public Object onRemove(Object key) {
      return this.target.remove(key);
   }

   public Object onPut(Object key, Object value) {
      return this.target.put(key, value);
   }

   public void onPutAll(Map m) {
      this.target.putAll(m);
   }

   public void onClear() {
      this.target.clear();
   }
}
