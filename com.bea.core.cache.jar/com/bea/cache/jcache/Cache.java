package com.bea.cache.jcache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Cache extends Map {
   boolean containsKey(Object var1);

   boolean containsValue(Object var1);

   Set entrySet();

   boolean equals(Object var1);

   int hashCode();

   boolean isEmpty();

   Set keySet();

   void putAll(Map var1);

   int size();

   Collection values();

   Object get(Object var1);

   void load(Object var1);

   void loadAll(Collection var1);

   void loadAll();

   Object put(Object var1, Object var2);

   CacheEntry getCacheEntry(Object var1);

   CacheStatistics getCacheStatistics();

   Object remove(Object var1);

   void clear();

   void addListener(CacheListener var1);

   void removeListener(CacheListener var1);

   int getCapacity();

   long getTTL();
}
