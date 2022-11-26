package org.python.netty.util.collection;

import java.util.Map;

public interface ByteObjectMap extends Map {
   Object get(byte var1);

   Object put(byte var1, Object var2);

   Object remove(byte var1);

   Iterable entries();

   boolean containsKey(byte var1);

   public interface PrimitiveEntry {
      byte key();

      Object value();

      void setValue(Object var1);
   }
}
