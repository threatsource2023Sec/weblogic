package org.python.netty.util.collection;

import java.util.Map;

public interface CharObjectMap extends Map {
   Object get(char var1);

   Object put(char var1, Object var2);

   Object remove(char var1);

   Iterable entries();

   boolean containsKey(char var1);

   public interface PrimitiveEntry {
      char key();

      Object value();

      void setValue(Object var1);
   }
}
