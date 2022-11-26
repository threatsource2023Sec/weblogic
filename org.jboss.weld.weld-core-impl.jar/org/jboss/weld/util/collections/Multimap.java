package org.jboss.weld.util.collections;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Multimap {
   int size();

   boolean isEmpty();

   Collection get(Object var1);

   boolean put(Object var1, Object var2);

   boolean putAll(Object var1, Collection var2);

   Collection replaceValues(Object var1, Iterable var2);

   boolean containsKey(Object var1);

   Set keySet();

   List values();

   Set uniqueValues();

   Set entrySet();

   void clear();
}
