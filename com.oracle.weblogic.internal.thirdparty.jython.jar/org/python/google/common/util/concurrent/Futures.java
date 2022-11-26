package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableList;
import org.python.google.common.collect.UnmodifiableIterator;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible(
   emulated = true
)
public final class Futures extends GwtFuturesCatchingSpecialization {
   private static final AsyncFunction DEREFERENCER = new AsyncFunction() {
      public ListenableFuture apply(ListenableFuture input) {
         return input;
      }
   };

   private Futures() {
   }

   /** @deprecated */
   @Deprecated
   @GwtIncompatible
   public static CheckedFuture makeChecked(ListenableFuture future, Function mapper) {
      return new MappingCheckedFuture((ListenableFuture)Preconditions.checkNotNull(future), mapper);
   }

   public static ListenableFuture immediateFuture(@Nullable Object value) {
      if (value == null) {
         ListenableFuture typedNull = ImmediateFuture.ImmediateSuccessfulFuture.NULL;
         return typedNull;
      } else {
         return new ImmediateFuture.ImmediateSuccessfulFuture(value);
      }
   }

   /** @deprecated */
   @Deprecated
   @GwtIncompatible
   public static CheckedFuture immediateCheckedFuture(@Nullable Object value) {
      return new ImmediateFuture.ImmediateSuccessfulCheckedFuture(value);
   }

   public static ListenableFuture immediateFailedFuture(Throwable throwable) {
      Preconditions.checkNotNull(throwable);
      return new ImmediateFuture.ImmediateFailedFuture(throwable);
   }

   public static ListenableFuture immediateCancelledFuture() {
      return new ImmediateFuture.ImmediateCancelledFuture();
   }

   /** @deprecated */
   @Deprecated
   @GwtIncompatible
   public static CheckedFuture immediateFailedCheckedFuture(Exception exception) {
      Preconditions.checkNotNull(exception);
      return new ImmediateFuture.ImmediateFailedCheckedFuture(exception);
   }

   /** @deprecated */
   @Deprecated
   @Partially.GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
   public static ListenableFuture catching(ListenableFuture input, Class exceptionType, Function fallback) {
      return AbstractCatchingFuture.create(input, exceptionType, fallback);
   }

   @Partially.GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
   public static ListenableFuture catching(ListenableFuture input, Class exceptionType, Function fallback, Executor executor) {
      return AbstractCatchingFuture.create(input, exceptionType, fallback, executor);
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   @Partially.GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
   public static ListenableFuture catchingAsync(ListenableFuture input, Class exceptionType, AsyncFunction fallback) {
      return AbstractCatchingFuture.create(input, exceptionType, fallback);
   }

   @CanIgnoreReturnValue
   @Partially.GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
   public static ListenableFuture catchingAsync(ListenableFuture input, Class exceptionType, AsyncFunction fallback, Executor executor) {
      return AbstractCatchingFuture.create(input, exceptionType, fallback, executor);
   }

   @GwtIncompatible
   public static ListenableFuture withTimeout(ListenableFuture delegate, long time, TimeUnit unit, ScheduledExecutorService scheduledExecutor) {
      return TimeoutFuture.create(delegate, time, unit, scheduledExecutor);
   }

   /** @deprecated */
   @Deprecated
   public static ListenableFuture transformAsync(ListenableFuture input, AsyncFunction function) {
      return AbstractTransformFuture.create(input, function);
   }

   public static ListenableFuture transformAsync(ListenableFuture input, AsyncFunction function, Executor executor) {
      return AbstractTransformFuture.create(input, function, executor);
   }

   /** @deprecated */
   @Deprecated
   public static ListenableFuture transform(ListenableFuture input, Function function) {
      return AbstractTransformFuture.create(input, function);
   }

   public static ListenableFuture transform(ListenableFuture input, Function function, Executor executor) {
      return AbstractTransformFuture.create(input, function, executor);
   }

   @GwtIncompatible
   public static Future lazyTransform(final Future input, final Function function) {
      Preconditions.checkNotNull(input);
      Preconditions.checkNotNull(function);
      return new Future() {
         public boolean cancel(boolean mayInterruptIfRunning) {
            return input.cancel(mayInterruptIfRunning);
         }

         public boolean isCancelled() {
            return input.isCancelled();
         }

         public boolean isDone() {
            return input.isDone();
         }

         public Object get() throws InterruptedException, ExecutionException {
            return this.applyTransformation(input.get());
         }

         public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.applyTransformation(input.get(timeout, unit));
         }

         private Object applyTransformation(Object inputx) throws ExecutionException {
            try {
               return function.apply(inputx);
            } catch (Throwable var3) {
               throw new ExecutionException(var3);
            }
         }
      };
   }

   public static ListenableFuture dereference(ListenableFuture nested) {
      return transformAsync(nested, DEREFERENCER, MoreExecutors.directExecutor());
   }

   @SafeVarargs
   @Beta
   public static ListenableFuture allAsList(ListenableFuture... futures) {
      return new CollectionFuture.ListFuture(ImmutableList.copyOf((Object[])futures), true);
   }

   @Beta
   public static ListenableFuture allAsList(Iterable futures) {
      return new CollectionFuture.ListFuture(ImmutableList.copyOf(futures), true);
   }

   @SafeVarargs
   public static FutureCombiner whenAllComplete(ListenableFuture... futures) {
      return new FutureCombiner(false, ImmutableList.copyOf((Object[])futures));
   }

   public static FutureCombiner whenAllComplete(Iterable futures) {
      return new FutureCombiner(false, ImmutableList.copyOf(futures));
   }

   @SafeVarargs
   public static FutureCombiner whenAllSucceed(ListenableFuture... futures) {
      return new FutureCombiner(true, ImmutableList.copyOf((Object[])futures));
   }

   public static FutureCombiner whenAllSucceed(Iterable futures) {
      return new FutureCombiner(true, ImmutableList.copyOf(futures));
   }

   public static ListenableFuture nonCancellationPropagating(ListenableFuture future) {
      return (ListenableFuture)(future.isDone() ? future : new NonCancellationPropagatingFuture(future));
   }

   @SafeVarargs
   @Beta
   public static ListenableFuture successfulAsList(ListenableFuture... futures) {
      return new CollectionFuture.ListFuture(ImmutableList.copyOf((Object[])futures), false);
   }

   @Beta
   public static ListenableFuture successfulAsList(Iterable futures) {
      return new CollectionFuture.ListFuture(ImmutableList.copyOf(futures), false);
   }

   @Beta
   public static ImmutableList inCompletionOrder(Iterable futures) {
      ImmutableList copy = ImmutableList.copyOf(futures);
      ImmutableList.Builder delegatesBuilder = ImmutableList.builder();

      for(int i = 0; i < copy.size(); ++i) {
         delegatesBuilder.add((Object)SettableFuture.create());
      }

      final ImmutableList delegates = delegatesBuilder.build();
      final AtomicInteger delegateIndex = new AtomicInteger();
      UnmodifiableIterator var5 = copy.iterator();

      while(var5.hasNext()) {
         final ListenableFuture future = (ListenableFuture)var5.next();
         future.addListener(new Runnable() {
            public void run() {
               for(int i = delegateIndex.get(); i < delegates.size(); ++i) {
                  if (((SettableFuture)delegates.get(i)).setFuture(future)) {
                     delegateIndex.set(i + 1);
                     return;
                  }
               }

            }
         }, MoreExecutors.directExecutor());
      }

      return delegates;
   }

   /** @deprecated */
   @Deprecated
   public static void addCallback(ListenableFuture future, FutureCallback callback) {
      addCallback(future, callback, MoreExecutors.directExecutor());
   }

   public static void addCallback(ListenableFuture future, FutureCallback callback, Executor executor) {
      Preconditions.checkNotNull(callback);
      future.addListener(new CallbackListener(future, callback), executor);
   }

   @CanIgnoreReturnValue
   public static Object getDone(Future future) throws ExecutionException {
      Preconditions.checkState(future.isDone(), "Future was expected to be done: %s", (Object)future);
      return Uninterruptibles.getUninterruptibly(future);
   }

   @CanIgnoreReturnValue
   @GwtIncompatible
   public static Object getChecked(Future future, Class exceptionClass) throws Exception {
      return FuturesGetChecked.getChecked(future, exceptionClass);
   }

   @CanIgnoreReturnValue
   @GwtIncompatible
   public static Object getChecked(Future future, Class exceptionClass, long timeout, TimeUnit unit) throws Exception {
      return FuturesGetChecked.getChecked(future, exceptionClass, timeout, unit);
   }

   @CanIgnoreReturnValue
   @GwtIncompatible
   public static Object getUnchecked(Future future) {
      Preconditions.checkNotNull(future);

      try {
         return Uninterruptibles.getUninterruptibly(future);
      } catch (ExecutionException var2) {
         wrapAndThrowUnchecked(var2.getCause());
         throw new AssertionError();
      }
   }

   @GwtIncompatible
   private static void wrapAndThrowUnchecked(Throwable cause) {
      if (cause instanceof Error) {
         throw new ExecutionError((Error)cause);
      } else {
         throw new UncheckedExecutionException(cause);
      }
   }

   @GwtIncompatible
   private static class MappingCheckedFuture extends AbstractCheckedFuture {
      final Function mapper;

      MappingCheckedFuture(ListenableFuture delegate, Function mapper) {
         super(delegate);
         this.mapper = (Function)Preconditions.checkNotNull(mapper);
      }

      protected Exception mapException(Exception e) {
         return (Exception)this.mapper.apply(e);
      }
   }

   private static final class CallbackListener implements Runnable {
      final Future future;
      final FutureCallback callback;

      CallbackListener(Future future, FutureCallback callback) {
         this.future = future;
         this.callback = callback;
      }

      public void run() {
         Object value;
         try {
            value = Futures.getDone(this.future);
         } catch (ExecutionException var3) {
            this.callback.onFailure(var3.getCause());
            return;
         } catch (RuntimeException var4) {
            this.callback.onFailure(var4);
            return;
         } catch (Error var5) {
            this.callback.onFailure(var5);
            return;
         }

         this.callback.onSuccess(value);
      }

      public String toString() {
         return MoreObjects.toStringHelper((Object)this).addValue(this.callback).toString();
      }
   }

   private static final class NonCancellationPropagatingFuture extends AbstractFuture.TrustedFuture {
      NonCancellationPropagatingFuture(final ListenableFuture delegate) {
         delegate.addListener(new Runnable() {
            public void run() {
               NonCancellationPropagatingFuture.this.setFuture(delegate);
            }
         }, MoreExecutors.directExecutor());
      }
   }

   @Beta
   @CanIgnoreReturnValue
   @GwtCompatible
   public static final class FutureCombiner {
      private final boolean allMustSucceed;
      private final ImmutableList futures;

      private FutureCombiner(boolean allMustSucceed, ImmutableList futures) {
         this.allMustSucceed = allMustSucceed;
         this.futures = futures;
      }

      public ListenableFuture callAsync(AsyncCallable combiner, Executor executor) {
         return new CombinedFuture(this.futures, this.allMustSucceed, executor, combiner);
      }

      /** @deprecated */
      @Deprecated
      public ListenableFuture callAsync(AsyncCallable combiner) {
         return this.callAsync(combiner, MoreExecutors.directExecutor());
      }

      @CanIgnoreReturnValue
      public ListenableFuture call(Callable combiner, Executor executor) {
         return new CombinedFuture(this.futures, this.allMustSucceed, executor, combiner);
      }

      /** @deprecated */
      @Deprecated
      @CanIgnoreReturnValue
      public ListenableFuture call(Callable combiner) {
         return this.call(combiner, MoreExecutors.directExecutor());
      }

      // $FF: synthetic method
      FutureCombiner(boolean x0, ImmutableList x1, Object x2) {
         this(x0, x1);
      }
   }
}
