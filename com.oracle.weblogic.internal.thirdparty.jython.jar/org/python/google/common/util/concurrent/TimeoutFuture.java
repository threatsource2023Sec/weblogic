package org.python.google.common.util.concurrent;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtIncompatible
final class TimeoutFuture extends AbstractFuture.TrustedFuture {
   @Nullable
   private ListenableFuture delegateRef;
   @Nullable
   private Future timer;

   static ListenableFuture create(ListenableFuture delegate, long time, TimeUnit unit, ScheduledExecutorService scheduledExecutor) {
      TimeoutFuture result = new TimeoutFuture(delegate);
      Fire fire = new Fire(result);
      result.timer = scheduledExecutor.schedule(fire, time, unit);
      delegate.addListener(fire, MoreExecutors.directExecutor());
      return result;
   }

   private TimeoutFuture(ListenableFuture delegate) {
      this.delegateRef = (ListenableFuture)Preconditions.checkNotNull(delegate);
   }

   protected void afterDone() {
      this.maybePropagateCancellation(this.delegateRef);
      Future localTimer = this.timer;
      if (localTimer != null) {
         localTimer.cancel(false);
      }

      this.delegateRef = null;
      this.timer = null;
   }

   private static final class Fire implements Runnable {
      @Nullable
      TimeoutFuture timeoutFutureRef;

      Fire(TimeoutFuture timeoutFuture) {
         this.timeoutFutureRef = timeoutFuture;
      }

      public void run() {
         TimeoutFuture timeoutFuture = this.timeoutFutureRef;
         if (timeoutFuture != null) {
            ListenableFuture delegate = timeoutFuture.delegateRef;
            if (delegate != null) {
               this.timeoutFutureRef = null;
               if (delegate.isDone()) {
                  timeoutFuture.setFuture(delegate);
               } else {
                  try {
                     timeoutFuture.setException(new TimeoutException("Future timed out: " + delegate));
                  } finally {
                     delegate.cancel(true);
                  }
               }

            }
         }
      }
   }
}
