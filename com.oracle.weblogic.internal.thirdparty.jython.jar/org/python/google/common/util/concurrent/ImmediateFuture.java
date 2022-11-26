package org.python.google.common.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   emulated = true
)
abstract class ImmediateFuture implements ListenableFuture {
   private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());

   public void addListener(Runnable listener, Executor executor) {
      Preconditions.checkNotNull(listener, "Runnable was null.");
      Preconditions.checkNotNull(executor, "Executor was null.");

      try {
         executor.execute(listener);
      } catch (RuntimeException var4) {
         log.log(Level.SEVERE, "RuntimeException while executing runnable " + listener + " with executor " + executor, var4);
      }

   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return false;
   }

   public abstract Object get() throws ExecutionException;

   public Object get(long timeout, TimeUnit unit) throws ExecutionException {
      Preconditions.checkNotNull(unit);
      return this.get();
   }

   public boolean isCancelled() {
      return false;
   }

   public boolean isDone() {
      return true;
   }

   @GwtIncompatible
   static class ImmediateFailedCheckedFuture extends ImmediateFuture implements CheckedFuture {
      private final Exception thrown;

      ImmediateFailedCheckedFuture(Exception thrown) {
         this.thrown = thrown;
      }

      public Object get() throws ExecutionException {
         throw new ExecutionException(this.thrown);
      }

      public Object checkedGet() throws Exception {
         throw this.thrown;
      }

      public Object checkedGet(long timeout, TimeUnit unit) throws Exception {
         Preconditions.checkNotNull(unit);
         throw this.thrown;
      }
   }

   static final class ImmediateCancelledFuture extends AbstractFuture.TrustedFuture {
      ImmediateCancelledFuture() {
         this.cancel(false);
      }
   }

   static final class ImmediateFailedFuture extends AbstractFuture.TrustedFuture {
      ImmediateFailedFuture(Throwable thrown) {
         this.setException(thrown);
      }
   }

   @GwtIncompatible
   static class ImmediateSuccessfulCheckedFuture extends ImmediateFuture implements CheckedFuture {
      @Nullable
      private final Object value;

      ImmediateSuccessfulCheckedFuture(@Nullable Object value) {
         this.value = value;
      }

      public Object get() {
         return this.value;
      }

      public Object checkedGet() {
         return this.value;
      }

      public Object checkedGet(long timeout, TimeUnit unit) {
         Preconditions.checkNotNull(unit);
         return this.value;
      }
   }

   static class ImmediateSuccessfulFuture extends ImmediateFuture {
      static final ImmediateSuccessfulFuture NULL = new ImmediateSuccessfulFuture((Object)null);
      @Nullable
      private final Object value;

      ImmediateSuccessfulFuture(@Nullable Object value) {
         this.value = value;
      }

      public Object get() {
         return this.value;
      }
   }
}
