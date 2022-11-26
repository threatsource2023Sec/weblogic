package weblogic.cache.util;

import java.util.concurrent.ConcurrentMap;
import weblogic.utils.collections.ConcurrentHashMap;

public class WLConcurrentMap extends ConcurrentHashMap implements ConcurrentMap {
   public WLConcurrentMap(int size) {
      super(size);
   }

   public boolean remove(Object key, Object value) {
      return false;
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      return false;
   }

   public Object replace(Object key, Object value) {
      return null;
   }
}
