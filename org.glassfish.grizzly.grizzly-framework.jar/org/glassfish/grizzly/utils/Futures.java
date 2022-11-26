package org.glassfish.grizzly.utils;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Copyable;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.impl.UnsafeFutureImpl;

public class Futures {
   public static FutureImpl createSafeFuture() {
      return SafeFutureImpl.create();
   }

   public static FutureImpl createUnsafeFuture() {
      return UnsafeFutureImpl.create();
   }

   public static GrizzlyFuture createReadyFuture(Object result) {
      return ReadyFutureImpl.create(result);
   }

   public static GrizzlyFuture createReadyFuture(Throwable error) {
      return ReadyFutureImpl.create(error);
   }

   public static void notifyResult(FutureImpl future, CompletionHandler completionHandler, Object result) {
      if (completionHandler != null) {
         completionHandler.completed(result);
      }

      if (future != null) {
         future.result(result);
      }

   }

   public static void notifyFailure(FutureImpl future, CompletionHandler completionHandler, Throwable error) {
      if (completionHandler != null) {
         completionHandler.failed(error);
      }

      if (future != null) {
         future.failure(error);
      }

   }

   public static void notifyCancel(FutureImpl future, CompletionHandler completionHandler) {
      if (completionHandler != null) {
         completionHandler.cancelled();
      }

      if (future != null) {
         future.cancel(false);
      }

   }

   public static CompletionHandler toCompletionHandler(FutureImpl future) {
      return new FutureToCompletionHandler(future);
   }

   public static CompletionHandler toCompletionHandler(FutureImpl future, CompletionHandler completionHandler) {
      return new CompletionHandlerAdapter(future, completionHandler);
   }

   public static CompletionHandler toAdaptedCompletionHandler(FutureImpl future, GenericAdapter adapter) {
      return toAdaptedCompletionHandler(future, (CompletionHandler)null, adapter);
   }

   public static CompletionHandler toAdaptedCompletionHandler(FutureImpl future, CompletionHandler completionHandler, GenericAdapter adapter) {
      return new CompletionHandlerAdapter(future, completionHandler, adapter);
   }

   private static final class FutureToCompletionHandler extends EmptyCompletionHandler {
      private final FutureImpl future;

      public FutureToCompletionHandler(FutureImpl future) {
         this.future = future;
      }

      public void cancelled() {
         this.future.cancel(false);
      }

      public void completed(Object result) {
         if (result instanceof Copyable) {
            result = ((Copyable)result).copy();
         }

         this.future.result(result);
      }

      public void failed(Throwable throwable) {
         this.future.failure(throwable);
      }
   }
}
