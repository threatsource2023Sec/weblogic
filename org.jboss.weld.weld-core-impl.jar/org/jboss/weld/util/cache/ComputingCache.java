package org.jboss.weld.util.cache;

import java.util.function.Consumer;

public interface ComputingCache {
   Object getValue(Object var1);

   Object getCastValue(Object var1);

   Object getValueIfPresent(Object var1);

   long size();

   void clear();

   void invalidate(Object var1);

   Iterable getAllPresentValues();

   void forEachValue(Consumer var1);
}
