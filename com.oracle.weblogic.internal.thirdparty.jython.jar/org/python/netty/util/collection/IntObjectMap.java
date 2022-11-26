package org.python.netty.util.collection;

import java.util.Map;

public interface IntObjectMap extends Map {
   Object get(int var1);

   Object put(int var1, Object var2);

   Object remove(int var1);

   Iterable entries();

   boolean containsKey(int var1);

   public interface PrimitiveEntry {
      int key();

      Object value();

      void setValue(Object var1);
   }
}
