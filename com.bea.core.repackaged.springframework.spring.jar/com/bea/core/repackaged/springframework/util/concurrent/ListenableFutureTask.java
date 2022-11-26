package com.bea.core.repackaged.springframework.util.concurrent;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ListenableFutureTask extends FutureTask implements ListenableFuture {
   private final ListenableFutureCallbackRegistry callbacks = new ListenableFutureCallbackRegistry();

   public ListenableFutureTask(Callable callable) {
      super(callable);
   }

   public ListenableFutureTask(Runnable runnable, @Nullable Object result) {
      super(runnable, result);
   }

   public void addCallback(ListenableFutureCallback callback) {
      this.callbacks.addCallback(callback);
   }

   public void addCallback(SuccessCallback successCallback, FailureCallback failureCallback) {
      this.callbacks.addSuccessCallback(successCallback);
      this.callbacks.addFailureCallback(failureCallback);
   }

   public CompletableFuture completable() {
      CompletableFuture completable = new DelegatingCompletableFuture(this);
      this.callbacks.addSuccessCallback(completable::complete);
      this.callbacks.addFailureCallback(completable::completeExceptionally);
      return completable;
   }

   protected void done() {
      Object cause;
      try {
         Object result = this.get();
         this.callbacks.success(result);
         return;
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         return;
      } catch (ExecutionException var4) {
         cause = var4.getCause();
         if (cause == null) {
            cause = var4;
         }
      } catch (Throwable var5) {
         cause = var5;
      }

      this.callbacks.failure((Throwable)cause);
   }
}
