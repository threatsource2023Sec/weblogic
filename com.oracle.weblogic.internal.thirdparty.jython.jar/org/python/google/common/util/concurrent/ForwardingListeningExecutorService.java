package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@CanIgnoreReturnValue
@GwtIncompatible
public abstract class ForwardingListeningExecutorService extends ForwardingExecutorService implements ListeningExecutorService {
   protected ForwardingListeningExecutorService() {
   }

   protected abstract ListeningExecutorService delegate();

   public ListenableFuture submit(Callable task) {
      return this.delegate().submit(task);
   }

   public ListenableFuture submit(Runnable task) {
      return this.delegate().submit(task);
   }

   public ListenableFuture submit(Runnable task, Object result) {
      return this.delegate().submit(task, result);
   }
}
