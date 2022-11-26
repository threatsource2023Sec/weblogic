package org.python.netty.util.collection;

import java.util.Map;

public interface LongObjectMap extends Map {
   Object get(long var1);

   Object put(long var1, Object var3);

   Object remove(long var1);

   Iterable entries();

   boolean containsKey(long var1);

   public interface PrimitiveEntry {
      long key();

      Object value();

      void setValue(Object var1);
   }
}
