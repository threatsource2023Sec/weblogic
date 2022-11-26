package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
class TrustedListenableFutureTask extends AbstractFuture.TrustedFuture implements RunnableFuture {
   private TrustedFutureInterruptibleTask task;

   static TrustedListenableFutureTask create(Callable callable) {
      return new TrustedListenableFutureTask(callable);
   }

   static TrustedListenableFutureTask create(Runnable runnable, @Nullable Object result) {
      return new TrustedListenableFutureTask(Executors.callable(runnable, result));
   }

   TrustedListenableFutureTask(Callable callable) {
      this.task = new TrustedFutureInterruptibleTask(callable);
   }

   public void run() {
      TrustedFutureInterruptibleTask localTask = this.task;
      if (localTask != null) {
         localTask.run();
      }

   }

   protected void afterDone() {
      super.afterDone();
      if (this.wasInterrupted()) {
         TrustedFutureInterruptibleTask localTask = this.task;
         if (localTask != null) {
            localTask.interruptTask();
         }
      }

      this.task = null;
   }

   public String toString() {
      return super.toString() + " (delegate = " + this.task + ")";
   }

   private final class TrustedFutureInterruptibleTask extends InterruptibleTask {
      private final Callable callable;

      TrustedFutureInterruptibleTask(Callable callable) {
         this.callable = (Callable)Preconditions.checkNotNull(callable);
      }

      void runInterruptibly() {
         if (!TrustedListenableFutureTask.this.isDone()) {
            try {
               TrustedListenableFutureTask.this.set(this.callable.call());
            } catch (Throwable var2) {
               TrustedListenableFutureTask.this.setException(var2);
            }
         }

      }

      boolean wasInterrupted() {
         return TrustedListenableFutureTask.this.wasInterrupted();
      }

      public String toString() {
         return this.callable.toString();
      }
   }
}
