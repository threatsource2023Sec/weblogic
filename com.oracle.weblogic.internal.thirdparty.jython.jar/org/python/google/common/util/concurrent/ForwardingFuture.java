package org.python.google.common.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ForwardingObject;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@CanIgnoreReturnValue
@GwtCompatible
public abstract class ForwardingFuture extends ForwardingObject implements Future {
   protected ForwardingFuture() {
   }

   protected abstract Future delegate();

   public boolean cancel(boolean mayInterruptIfRunning) {
      return this.delegate().cancel(mayInterruptIfRunning);
   }

   public boolean isCancelled() {
      return this.delegate().isCancelled();
   }

   public boolean isDone() {
      return this.delegate().isDone();
   }

   public Object get() throws InterruptedException, ExecutionException {
      return this.delegate().get();
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate().get(timeout, unit);
   }

   public abstract static class SimpleForwardingFuture extends ForwardingFuture {
      private final Future delegate;

      protected SimpleForwardingFuture(Future delegate) {
         this.delegate = (Future)Preconditions.checkNotNull(delegate);
      }

      protected final Future delegate() {
         return this.delegate;
      }
   }
}
