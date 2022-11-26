package org.python.google.common.util.concurrent;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Throwables;
import org.python.google.common.collect.ImmutableList;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@CanIgnoreReturnValue
@GwtIncompatible
abstract class WrappingExecutorService implements ExecutorService {
   private final ExecutorService delegate;

   protected WrappingExecutorService(ExecutorService delegate) {
      this.delegate = (ExecutorService)Preconditions.checkNotNull(delegate);
   }

   protected abstract Callable wrapTask(Callable var1);

   protected Runnable wrapTask(Runnable command) {
      final Callable wrapped = this.wrapTask(Executors.callable(command, (Object)null));
      return new Runnable() {
         public void run() {
            try {
               wrapped.call();
            } catch (Exception var2) {
               Throwables.throwIfUnchecked(var2);
               throw new RuntimeException(var2);
            }
         }
      };
   }

   private final ImmutableList wrapTasks(Collection tasks) {
      ImmutableList.Builder builder = ImmutableList.builder();
      Iterator var3 = tasks.iterator();

      while(var3.hasNext()) {
         Callable task = (Callable)var3.next();
         builder.add((Object)this.wrapTask(task));
      }

      return builder.build();
   }

   public final void execute(Runnable command) {
      this.delegate.execute(this.wrapTask(command));
   }

   public final Future submit(Callable task) {
      return this.delegate.submit(this.wrapTask((Callable)Preconditions.checkNotNull(task)));
   }

   public final Future submit(Runnable task) {
      return this.delegate.submit(this.wrapTask(task));
   }

   public final Future submit(Runnable task, Object result) {
      return this.delegate.submit(this.wrapTask(task), result);
   }

   public final List invokeAll(Collection tasks) throws InterruptedException {
      return this.delegate.invokeAll(this.wrapTasks(tasks));
   }

   public final List invokeAll(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException {
      return this.delegate.invokeAll(this.wrapTasks(tasks), timeout, unit);
   }

   public final Object invokeAny(Collection tasks) throws InterruptedException, ExecutionException {
      return this.delegate.invokeAny(this.wrapTasks(tasks));
   }

   public final Object invokeAny(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate.invokeAny(this.wrapTasks(tasks), timeout, unit);
   }

   public final void shutdown() {
      this.delegate.shutdown();
   }

   public final List shutdownNow() {
      return this.delegate.shutdownNow();
   }

   public final boolean isShutdown() {
      return this.delegate.isShutdown();
   }

   public final boolean isTerminated() {
      return this.delegate.isTerminated();
   }

   public final boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      return this.delegate.awaitTermination(timeout, unit);
   }
}
