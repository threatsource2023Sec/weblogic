package org.python.google.common.cache;

import java.util.concurrent.ExecutionException;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableMap;

@GwtIncompatible
public abstract class ForwardingLoadingCache extends ForwardingCache implements LoadingCache {
   protected ForwardingLoadingCache() {
   }

   protected abstract LoadingCache delegate();

   public Object get(Object key) throws ExecutionException {
      return this.delegate().get(key);
   }

   public Object getUnchecked(Object key) {
      return this.delegate().getUnchecked(key);
   }

   public ImmutableMap getAll(Iterable keys) throws ExecutionException {
      return this.delegate().getAll(keys);
   }

   public Object apply(Object key) {
      return this.delegate().apply(key);
   }

   public void refresh(Object key) {
      this.delegate().refresh(key);
   }

   public abstract static class SimpleForwardingLoadingCache extends ForwardingLoadingCache {
      private final LoadingCache delegate;

      protected SimpleForwardingLoadingCache(LoadingCache delegate) {
         this.delegate = (LoadingCache)Preconditions.checkNotNull(delegate);
      }

      protected final LoadingCache delegate() {
         return this.delegate;
      }
   }
}
