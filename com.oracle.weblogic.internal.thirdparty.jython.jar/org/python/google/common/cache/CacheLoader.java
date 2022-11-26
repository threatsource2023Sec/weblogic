package org.python.google.common.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Supplier;
import org.python.google.common.util.concurrent.Futures;
import org.python.google.common.util.concurrent.ListenableFuture;
import org.python.google.common.util.concurrent.ListenableFutureTask;

@GwtCompatible(
   emulated = true
)
public abstract class CacheLoader {
   protected CacheLoader() {
   }

   public abstract Object load(Object var1) throws Exception;

   @GwtIncompatible
   public ListenableFuture reload(Object key, Object oldValue) throws Exception {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(oldValue);
      return Futures.immediateFuture(this.load(key));
   }

   public Map loadAll(Iterable keys) throws Exception {
      throw new UnsupportedLoadingOperationException();
   }

   public static CacheLoader from(Function function) {
      return new FunctionToCacheLoader(function);
   }

   public static CacheLoader from(Supplier supplier) {
      return new SupplierToCacheLoader(supplier);
   }

   @GwtIncompatible
   public static CacheLoader asyncReloading(final CacheLoader loader, final Executor executor) {
      Preconditions.checkNotNull(loader);
      Preconditions.checkNotNull(executor);
      return new CacheLoader() {
         public Object load(Object key) throws Exception {
            return loader.load(key);
         }

         public ListenableFuture reload(final Object key, final Object oldValue) throws Exception {
            ListenableFutureTask task = ListenableFutureTask.create(new Callable() {
               public Object call() throws Exception {
                  return loader.reload(key, oldValue).get();
               }
            });
            executor.execute(task);
            return task;
         }

         public Map loadAll(Iterable keys) throws Exception {
            return loader.loadAll(keys);
         }
      };
   }

   public static final class InvalidCacheLoadException extends RuntimeException {
      public InvalidCacheLoadException(String message) {
         super(message);
      }
   }

   public static final class UnsupportedLoadingOperationException extends UnsupportedOperationException {
      UnsupportedLoadingOperationException() {
      }
   }

   private static final class SupplierToCacheLoader extends CacheLoader implements Serializable {
      private final Supplier computingSupplier;
      private static final long serialVersionUID = 0L;

      public SupplierToCacheLoader(Supplier computingSupplier) {
         this.computingSupplier = (Supplier)Preconditions.checkNotNull(computingSupplier);
      }

      public Object load(Object key) {
         Preconditions.checkNotNull(key);
         return this.computingSupplier.get();
      }
   }

   private static final class FunctionToCacheLoader extends CacheLoader implements Serializable {
      private final Function computingFunction;
      private static final long serialVersionUID = 0L;

      public FunctionToCacheLoader(Function computingFunction) {
         this.computingFunction = (Function)Preconditions.checkNotNull(computingFunction);
      }

      public Object load(Object key) {
         return this.computingFunction.apply(Preconditions.checkNotNull(key));
      }
   }
}
