package org.python.google.common.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.collect.ImmutableMap;

@GwtCompatible
public interface Cache {
   @Nullable
   Object getIfPresent(Object var1);

   Object get(Object var1, Callable var2) throws ExecutionException;

   ImmutableMap getAllPresent(Iterable var1);

   void put(Object var1, Object var2);

   void putAll(Map var1);

   void invalidate(Object var1);

   void invalidateAll(Iterable var1);

   void invalidateAll();

   long size();

   CacheStats stats();

   ConcurrentMap asMap();

   void cleanUp();
}
