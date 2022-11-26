package com.bea.core.repackaged.springframework.util.concurrent;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SettableListenableFuture implements ListenableFuture {
   private static final Callable DUMMY_CALLABLE = () -> {
      throw new IllegalStateException("Should never be called");
   };
   private final SettableTask settableTask = new SettableTask();

   public boolean set(@Nullable Object value) {
      return this.settableTask.setResultValue(value);
   }

   public boolean setException(Throwable exception) {
      Assert.notNull(exception, (String)"Exception must not be null");
      return this.settableTask.setExceptionResult(exception);
   }

   public void addCallback(ListenableFutureCallback callback) {
      this.settableTask.addCallback(callback);
   }

   public void addCallback(SuccessCallback successCallback, FailureCallback failureCallback) {
      this.settableTask.addCallback(successCallback, failureCallback);
   }

   public CompletableFuture completable() {
      return this.settableTask.completable();
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      boolean cancelled = this.settableTask.cancel(mayInterruptIfRunning);
      if (cancelled && mayInterruptIfRunning) {
         this.interruptTask();
      }

      return cancelled;
   }

   public boolean isCancelled() {
      return this.settableTask.isCancelled();
   }

   public boolean isDone() {
      return this.settableTask.isDone();
   }

   public Object get() throws InterruptedException, ExecutionException {
      return this.settableTask.get();
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.settableTask.get(timeout, unit);
   }

   protected void interruptTask() {
   }

   private static class SettableTask extends ListenableFutureTask {
      @Nullable
      private volatile Thread completingThread;

      public SettableTask() {
         super(SettableListenableFuture.DUMMY_CALLABLE);
      }

      public boolean setResultValue(@Nullable Object value) {
         this.set(value);
         return this.checkCompletingThread();
      }

      public boolean setExceptionResult(Throwable exception) {
         this.setException(exception);
         return this.checkCompletingThread();
      }

      protected void done() {
         if (!this.isCancelled()) {
            this.completingThread = Thread.currentThread();
         }

         super.done();
      }

      private boolean checkCompletingThread() {
         boolean check = this.completingThread == Thread.currentThread();
         if (check) {
            this.completingThread = null;
         }

         return check;
      }
   }
}
