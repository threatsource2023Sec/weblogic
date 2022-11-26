package weblogic.cache.store;

import java.util.HashSet;
import weblogic.cache.CacheMap;
import weblogic.cache.KeyFilter;

public class KeyGroup extends HashSet {
   protected final CacheMap map;

   public KeyGroup(CacheMap map) {
      this.map = map;
   }

   public void addAll(KeyFilter filter, Object criteria) {
      this.addAll(filter.filter(this.map.keySet(), criteria));
   }
}
