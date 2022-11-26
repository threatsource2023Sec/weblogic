package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableCollection;

@GwtCompatible
final class CombinedFuture extends AggregateFuture {
   CombinedFuture(ImmutableCollection futures, boolean allMustSucceed, Executor listenerExecutor, AsyncCallable callable) {
      this.init(new CombinedFutureRunningState(futures, allMustSucceed, new AsyncCallableInterruptibleTask(callable, listenerExecutor)));
   }

   CombinedFuture(ImmutableCollection futures, boolean allMustSucceed, Executor listenerExecutor, Callable callable) {
      this.init(new CombinedFutureRunningState(futures, allMustSucceed, new CallableInterruptibleTask(callable, listenerExecutor)));
   }

   private final class CallableInterruptibleTask extends CombinedFutureInterruptibleTask {
      private final Callable callable;

      public CallableInterruptibleTask(Callable callable, Executor listenerExecutor) {
         super(listenerExecutor);
         this.callable = (Callable)Preconditions.checkNotNull(callable);
      }

      void setValue() throws Exception {
         CombinedFuture.this.set(this.callable.call());
      }
   }

   private final class AsyncCallableInterruptibleTask extends CombinedFutureInterruptibleTask {
      private final AsyncCallable callable;

      public AsyncCallableInterruptibleTask(AsyncCallable callable, Executor listenerExecutor) {
         super(listenerExecutor);
         this.callable = (AsyncCallable)Preconditions.checkNotNull(callable);
      }

      void setValue() throws Exception {
         CombinedFuture.this.setFuture(this.callable.call());
      }
   }

   private abstract class CombinedFutureInterruptibleTask extends InterruptibleTask {
      private final Executor listenerExecutor;
      volatile boolean thrownByExecute = true;

      public CombinedFutureInterruptibleTask(Executor listenerExecutor) {
         this.listenerExecutor = (Executor)Preconditions.checkNotNull(listenerExecutor);
      }

      final void runInterruptibly() {
         this.thrownByExecute = false;
         if (!CombinedFuture.this.isDone()) {
            try {
               this.setValue();
            } catch (ExecutionException var2) {
               CombinedFuture.this.setException(var2.getCause());
            } catch (CancellationException var3) {
               CombinedFuture.this.cancel(false);
            } catch (Throwable var4) {
               CombinedFuture.this.setException(var4);
            }
         }

      }

      final boolean wasInterrupted() {
         return CombinedFuture.this.wasInterrupted();
      }

      final void execute() {
         try {
            this.listenerExecutor.execute(this);
         } catch (RejectedExecutionException var2) {
            if (this.thrownByExecute) {
               CombinedFuture.this.setException(var2);
            }
         }

      }

      abstract void setValue() throws Exception;
   }

   private final class CombinedFutureRunningState extends AggregateFuture.RunningState {
      private CombinedFutureInterruptibleTask task;

      CombinedFutureRunningState(ImmutableCollection futures, boolean allMustSucceed, CombinedFutureInterruptibleTask task) {
         super(futures, allMustSucceed, false);
         this.task = task;
      }

      void collectOneValue(boolean allMustSucceed, int index, @Nullable Object returnValue) {
      }

      void handleAllCompleted() {
         CombinedFutureInterruptibleTask localTask = this.task;
         if (localTask != null) {
            localTask.execute();
         } else {
            Preconditions.checkState(CombinedFuture.this.isDone());
         }

      }

      void releaseResourcesAfterFailure() {
         super.releaseResourcesAfterFailure();
         this.task = null;
      }

      void interruptTask() {
         CombinedFutureInterruptibleTask localTask = this.task;
         if (localTask != null) {
            localTask.interruptTask();
         }

      }
   }
}
