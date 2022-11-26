package org.python.google.common.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.ForOverride;

@GwtCompatible
abstract class AbstractCatchingFuture extends AbstractFuture.TrustedFuture implements Runnable {
   @Nullable
   ListenableFuture inputFuture;
   @Nullable
   Class exceptionType;
   @Nullable
   Object fallback;

   static ListenableFuture create(ListenableFuture input, Class exceptionType, Function fallback) {
      CatchingFuture future = new CatchingFuture(input, exceptionType, fallback);
      input.addListener(future, MoreExecutors.directExecutor());
      return future;
   }

   static ListenableFuture create(ListenableFuture input, Class exceptionType, Function fallback, Executor executor) {
      CatchingFuture future = new CatchingFuture(input, exceptionType, fallback);
      input.addListener(future, MoreExecutors.rejectionPropagatingExecutor(executor, future));
      return future;
   }

   static ListenableFuture create(ListenableFuture input, Class exceptionType, AsyncFunction fallback) {
      AsyncCatchingFuture future = new AsyncCatchingFuture(input, exceptionType, fallback);
      input.addListener(future, MoreExecutors.directExecutor());
      return future;
   }

   static ListenableFuture create(ListenableFuture input, Class exceptionType, AsyncFunction fallback, Executor executor) {
      AsyncCatchingFuture future = new AsyncCatchingFuture(input, exceptionType, fallback);
      input.addListener(future, MoreExecutors.rejectionPropagatingExecutor(executor, future));
      return future;
   }

   AbstractCatchingFuture(ListenableFuture inputFuture, Class exceptionType, Object fallback) {
      this.inputFuture = (ListenableFuture)Preconditions.checkNotNull(inputFuture);
      this.exceptionType = (Class)Preconditions.checkNotNull(exceptionType);
      this.fallback = Preconditions.checkNotNull(fallback);
   }

   public final void run() {
      ListenableFuture localInputFuture = this.inputFuture;
      Class localExceptionType = this.exceptionType;
      Object localFallback = this.fallback;
      if (!(localInputFuture == null | localExceptionType == null | localFallback == null | this.isCancelled())) {
         this.inputFuture = null;
         this.exceptionType = null;
         this.fallback = null;
         Object sourceResult = null;
         Throwable throwable = null;

         try {
            sourceResult = Futures.getDone(localInputFuture);
         } catch (ExecutionException var10) {
            throwable = (Throwable)Preconditions.checkNotNull(var10.getCause());
         } catch (Throwable var11) {
            throwable = var11;
         }

         if (throwable == null) {
            this.set(sourceResult);
         } else if (!Platform.isInstanceOfThrowableClass(throwable, localExceptionType)) {
            this.setException(throwable);
         } else {
            Throwable castThrowable = throwable;

            Object fallbackResult;
            try {
               fallbackResult = this.doFallback(localFallback, castThrowable);
            } catch (Throwable var9) {
               this.setException(var9);
               return;
            }

            this.setResult(fallbackResult);
         }
      }
   }

   @Nullable
   @ForOverride
   abstract Object doFallback(Object var1, Throwable var2) throws Exception;

   @ForOverride
   abstract void setResult(@Nullable Object var1);

   protected final void afterDone() {
      this.maybePropagateCancellation(this.inputFuture);
      this.inputFuture = null;
      this.exceptionType = null;
      this.fallback = null;
   }

   private static final class CatchingFuture extends AbstractCatchingFuture {
      CatchingFuture(ListenableFuture input, Class exceptionType, Function fallback) {
         super(input, exceptionType, fallback);
      }

      @Nullable
      Object doFallback(Function fallback, Throwable cause) throws Exception {
         return fallback.apply(cause);
      }

      void setResult(@Nullable Object result) {
         this.set(result);
      }
   }

   private static final class AsyncCatchingFuture extends AbstractCatchingFuture {
      AsyncCatchingFuture(ListenableFuture input, Class exceptionType, AsyncFunction fallback) {
         super(input, exceptionType, fallback);
      }

      ListenableFuture doFallback(AsyncFunction fallback, Throwable cause) throws Exception {
         ListenableFuture replacement = fallback.apply(cause);
         Preconditions.checkNotNull(replacement, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
         return replacement;
      }

      void setResult(ListenableFuture result) {
         this.setFuture(result);
      }
   }
}
