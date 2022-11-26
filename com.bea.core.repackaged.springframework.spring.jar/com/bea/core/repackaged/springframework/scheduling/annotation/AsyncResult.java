package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.concurrent.FailureCallback;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFutureCallback;
import com.bea.core.repackaged.springframework.util.concurrent.SuccessCallback;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AsyncResult implements ListenableFuture {
   @Nullable
   private final Object value;
   @Nullable
   private final Throwable executionException;

   public AsyncResult(@Nullable Object value) {
      this(value, (Throwable)null);
   }

   private AsyncResult(@Nullable Object value, @Nullable Throwable ex) {
      this.value = value;
      this.executionException = ex;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return false;
   }

   public boolean isCancelled() {
      return false;
   }

   public boolean isDone() {
      return true;
   }

   @Nullable
   public Object get() throws ExecutionException {
      if (this.executionException != null) {
         throw this.executionException instanceof ExecutionException ? (ExecutionException)this.executionException : new ExecutionException(this.executionException);
      } else {
         return this.value;
      }
   }

   @Nullable
   public Object get(long timeout, TimeUnit unit) throws ExecutionException {
      return this.get();
   }

   public void addCallback(ListenableFutureCallback callback) {
      this.addCallback(callback, callback);
   }

   public void addCallback(SuccessCallback successCallback, FailureCallback failureCallback) {
      try {
         if (this.executionException != null) {
            failureCallback.onFailure(exposedException(this.executionException));
         } else {
            successCallback.onSuccess(this.value);
         }
      } catch (Throwable var4) {
      }

   }

   public CompletableFuture completable() {
      if (this.executionException != null) {
         CompletableFuture completable = new CompletableFuture();
         completable.completeExceptionally(exposedException(this.executionException));
         return completable;
      } else {
         return CompletableFuture.completedFuture(this.value);
      }
   }

   public static ListenableFuture forValue(Object value) {
      return new AsyncResult(value, (Throwable)null);
   }

   public static ListenableFuture forExecutionException(Throwable ex) {
      return new AsyncResult((Object)null, ex);
   }

   private static Throwable exposedException(Throwable original) {
      if (original instanceof ExecutionException) {
         Throwable cause = original.getCause();
         if (cause != null) {
            return cause;
         }
      }

      return original;
   }
}
