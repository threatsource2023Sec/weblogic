package org.glassfish.tyrus.core.uri.internal;

import java.util.List;
import java.util.Map;

public interface MultivaluedMap extends Map {
   void putSingle(Object var1, Object var2);

   void add(Object var1, Object var2);

   Object getFirst(Object var1);

   void addAll(Object var1, Object... var2);

   void addAll(Object var1, List var2);

   void addFirst(Object var1, Object var2);

   boolean equalsIgnoreValueOrder(MultivaluedMap var1);
}
