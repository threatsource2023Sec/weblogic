package org.opensaml.core.xml.util;

import java.util.WeakHashMap;

public abstract class AbstractSimpleSingletonFactory extends AbstractSingletonFactory {
   private WeakHashMap map = new WeakHashMap();

   protected synchronized Object get(Object input) {
      return this.map.get(input);
   }

   protected synchronized void put(Object input, Object output) {
      this.map.put(input, output);
   }
}
