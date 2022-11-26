package weblogic.cache.util;

import java.util.Map;

class ReadOnlyCoherencyStrategy implements TieredMap.CoherencyStrategy {
   public Object onPut(Object key, Object value) {
      throw new UnsupportedOperationException("CoherencyStrategy is read-only");
   }

   public void onPutAll(Map m) {
      throw new UnsupportedOperationException("CoherencyStrategy is read-only");
   }

   public Object onRemove(Object key) {
      throw new UnsupportedOperationException("CoherencyStrategy is read-only");
   }

   public void onClear() {
      throw new UnsupportedOperationException("CoherencyStrategy is read-only");
   }
}
