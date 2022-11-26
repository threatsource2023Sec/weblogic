package org.python.google.common.util.concurrent;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.ForOverride;

@GwtCompatible
abstract class AbstractTransformFuture extends AbstractFuture.TrustedFuture implements Runnable {
   @Nullable
   ListenableFuture inputFuture;
   @Nullable
   Object function;

   static ListenableFuture create(ListenableFuture input, AsyncFunction function) {
      AsyncTransformFuture output = new AsyncTransformFuture(input, function);
      input.addListener(output, MoreExecutors.directExecutor());
      return output;
   }

   static ListenableFuture create(ListenableFuture input, AsyncFunction function, Executor executor) {
      Preconditions.checkNotNull(executor);
      AsyncTransformFuture output = new AsyncTransformFuture(input, function);
      input.addListener(output, MoreExecutors.rejectionPropagatingExecutor(executor, output));
      return output;
   }

   static ListenableFuture create(ListenableFuture input, Function function) {
      Preconditions.checkNotNull(function);
      TransformFuture output = new TransformFuture(input, function);
      input.addListener(output, MoreExecutors.directExecutor());
      return output;
   }

   static ListenableFuture create(ListenableFuture input, Function function, Executor executor) {
      Preconditions.checkNotNull(function);
      TransformFuture output = new TransformFuture(input, function);
      input.addListener(output, MoreExecutors.rejectionPropagatingExecutor(executor, output));
      return output;
   }

   AbstractTransformFuture(ListenableFuture inputFuture, Object function) {
      this.inputFuture = (ListenableFuture)Preconditions.checkNotNull(inputFuture);
      this.function = Preconditions.checkNotNull(function);
   }

   public final void run() {
      ListenableFuture localInputFuture = this.inputFuture;
      Object localFunction = this.function;
      if (!(this.isCancelled() | localInputFuture == null | localFunction == null)) {
         this.inputFuture = null;
         this.function = null;

         Object sourceResult;
         try {
            sourceResult = Futures.getDone(localInputFuture);
         } catch (CancellationException var8) {
            this.cancel(false);
            return;
         } catch (ExecutionException var9) {
            this.setException(var9.getCause());
            return;
         } catch (RuntimeException var10) {
            this.setException(var10);
            return;
         } catch (Error var11) {
            this.setException(var11);
            return;
         }

         Object transformResult;
         try {
            transformResult = this.doTransform(localFunction, sourceResult);
         } catch (UndeclaredThrowableException var6) {
            this.setException(var6.getCause());
            return;
         } catch (Throwable var7) {
            this.setException(var7);
            return;
         }

         this.setResult(transformResult);
      }
   }

   @Nullable
   @ForOverride
   abstract Object doTransform(Object var1, @Nullable Object var2) throws Exception;

   @ForOverride
   abstract void setResult(@Nullable Object var1);

   protected final void afterDone() {
      this.maybePropagateCancellation(this.inputFuture);
      this.inputFuture = null;
      this.function = null;
   }

   private static final class TransformFuture extends AbstractTransformFuture {
      TransformFuture(ListenableFuture inputFuture, Function function) {
         super(inputFuture, function);
      }

      @Nullable
      Object doTransform(Function function, @Nullable Object input) {
         return function.apply(input);
      }

      void setResult(@Nullable Object result) {
         this.set(result);
      }
   }

   private static final class AsyncTransformFuture extends AbstractTransformFuture {
      AsyncTransformFuture(ListenableFuture inputFuture, AsyncFunction function) {
         super(inputFuture, function);
      }

      ListenableFuture doTransform(AsyncFunction function, @Nullable Object input) throws Exception {
         ListenableFuture outputFuture = function.apply(input);
         Preconditions.checkNotNull(outputFuture, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
         return outputFuture;
      }

      void setResult(ListenableFuture result) {
         this.setFuture(result);
      }
   }
}
