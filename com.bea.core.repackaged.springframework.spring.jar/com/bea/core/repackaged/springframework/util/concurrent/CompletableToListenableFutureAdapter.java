package com.bea.core.repackaged.springframework.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableToListenableFutureAdapter implements ListenableFuture {
   private final CompletableFuture completableFuture;
   private final ListenableFutureCallbackRegistry callbacks;

   public CompletableToListenableFutureAdapter(CompletionStage completionStage) {
      this(completionStage.toCompletableFuture());
   }

   public CompletableToListenableFutureAdapter(CompletableFuture completableFuture) {
      this.callbacks = new ListenableFutureCallbackRegistry();
      this.completableFuture = completableFuture;
      this.completableFuture.whenComplete((result, ex) -> {
         if (ex != null) {
            this.callbacks.failure(ex);
         } else {
            this.callbacks.success(result);
         }

      });
   }

   public void addCallback(ListenableFutureCallback callback) {
      this.callbacks.addCallback(callback);
   }

   public void addCallback(SuccessCallback successCallback, FailureCallback failureCallback) {
      this.callbacks.addSuccessCallback(successCallback);
      this.callbacks.addFailureCallback(failureCallback);
   }

   public CompletableFuture completable() {
      return this.completableFuture;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return this.completableFuture.cancel(mayInterruptIfRunning);
   }

   public boolean isCancelled() {
      return this.completableFuture.isCancelled();
   }

   public boolean isDone() {
      return this.completableFuture.isDone();
   }

   public Object get() throws InterruptedException, ExecutionException {
      return this.completableFuture.get();
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.completableFuture.get(timeout, unit);
   }
}
