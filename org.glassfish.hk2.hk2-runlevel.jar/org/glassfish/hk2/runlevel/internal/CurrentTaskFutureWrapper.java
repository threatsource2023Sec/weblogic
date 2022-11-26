package org.glassfish.hk2.runlevel.internal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.hk2.runlevel.RunLevelFuture;

public class CurrentTaskFutureWrapper implements RunLevelFuture {
   private final CurrentTaskFuture delegate;

   CurrentTaskFutureWrapper(CurrentTaskFuture delegate) {
      this.delegate = delegate;
   }

   public boolean isCancelled() {
      return this.delegate.isCancelled();
   }

   public boolean isDone() {
      return this.delegate.isDone();
   }

   public Object get() throws InterruptedException, ExecutionException {
      return this.delegate.get();
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate.get(timeout, unit);
   }

   public int getProposedLevel() {
      return this.delegate.getProposedLevel();
   }

   public boolean isUp() {
      return this.delegate.isUp();
   }

   public boolean isDown() {
      return this.delegate.isDown();
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return this.delegate.cancel(mayInterruptIfRunning);
   }

   CurrentTaskFuture getDelegate() {
      return this.delegate;
   }

   public String toString() {
      return "CurrentTaskFutureWrapper(" + this.delegate.toString() + "," + System.identityHashCode(this) + ")";
   }
}
