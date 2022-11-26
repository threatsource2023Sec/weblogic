package org.python.google.common.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.Maps;
import org.python.google.common.util.concurrent.UncheckedExecutionException;

@GwtIncompatible
public abstract class AbstractLoadingCache extends AbstractCache implements LoadingCache {
   protected AbstractLoadingCache() {
   }

   public Object getUnchecked(Object key) {
      try {
         return this.get(key);
      } catch (ExecutionException var3) {
         throw new UncheckedExecutionException(var3.getCause());
      }
   }

   public ImmutableMap getAll(Iterable keys) throws ExecutionException {
      Map result = Maps.newLinkedHashMap();
      Iterator var3 = keys.iterator();

      while(var3.hasNext()) {
         Object key = var3.next();
         if (!result.containsKey(key)) {
            result.put(key, this.get(key));
         }
      }

      return ImmutableMap.copyOf((Map)result);
   }

   public final Object apply(Object key) {
      return this.getUnchecked(key);
   }

   public void refresh(Object key) {
      throw new UnsupportedOperationException();
   }
}
