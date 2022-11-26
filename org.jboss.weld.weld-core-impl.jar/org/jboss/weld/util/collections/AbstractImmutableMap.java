package org.jboss.weld.util.collections;

import java.util.AbstractMap;
import java.util.Map;
import org.jboss.weld.exceptions.UnsupportedOperationException;

public abstract class AbstractImmutableMap extends AbstractMap {
   public boolean isEmpty() {
      return false;
   }

   public Object put(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   public Object remove(Object key) {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map m) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }
}
