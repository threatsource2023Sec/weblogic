package org.python.google.common.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.collect.ImmutableMap;

@GwtCompatible
public interface LoadingCache extends Cache, Function {
   Object get(Object var1) throws ExecutionException;

   Object getUnchecked(Object var1);

   ImmutableMap getAll(Iterable var1) throws ExecutionException;

   /** @deprecated */
   @Deprecated
   Object apply(Object var1);

   void refresh(Object var1);

   ConcurrentMap asMap();
}
