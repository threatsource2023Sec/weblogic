package org.python.netty.util.collection;

import java.util.Map;

public interface ShortObjectMap extends Map {
   Object get(short var1);

   Object put(short var1, Object var2);

   Object remove(short var1);

   Iterable entries();

   boolean containsKey(short var1);

   public interface PrimitiveEntry {
      short key();

      Object value();

      void setValue(Object var1);
   }
}
