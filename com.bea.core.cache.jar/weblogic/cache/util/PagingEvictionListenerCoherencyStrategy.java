package weblogic.cache.util;

import java.util.Iterator;
import java.util.Map;
import weblogic.cache.EvictionListener;

class PagingEvictionListenerCoherencyStrategy implements EvictionListener, TieredMap.CoherencyStrategy {
   private final Map target;

   public PagingEvictionListenerCoherencyStrategy(Map m) {
      this.target = m;
   }

   public void onEviction(Map m) {
      this.target.putAll(m);
   }

   public Object onPut(Object key, Object value) {
      return this.target.remove(key);
   }

   public void onPutAll(Map m) {
      Iterator it = m.keySet().iterator();

      while(it.hasNext()) {
         this.target.remove(it.next());
      }

   }

   public Object onRemove(Object key) {
      return this.target.remove(key);
   }

   public void onClear() {
      this.target.clear();
   }
}
