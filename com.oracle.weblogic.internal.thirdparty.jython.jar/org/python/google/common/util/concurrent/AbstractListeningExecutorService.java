package org.python.google.common.util.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@CanIgnoreReturnValue
@GwtIncompatible
public abstract class AbstractListeningExecutorService extends AbstractExecutorService implements ListeningExecutorService {
   protected final RunnableFuture newTaskFor(Runnable runnable, Object value) {
      return TrustedListenableFutureTask.create(runnable, value);
   }

   protected final RunnableFuture newTaskFor(Callable callable) {
      return TrustedListenableFutureTask.create(callable);
   }

   public ListenableFuture submit(Runnable task) {
      return (ListenableFuture)super.submit(task);
   }

   public ListenableFuture submit(Runnable task, @Nullable Object result) {
      return (ListenableFuture)super.submit(task, result);
   }

   public ListenableFuture submit(Callable task) {
      return (ListenableFuture)super.submit(task);
   }
}
