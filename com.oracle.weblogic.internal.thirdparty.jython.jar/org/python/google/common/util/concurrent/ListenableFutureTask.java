package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
public class ListenableFutureTask extends FutureTask implements ListenableFuture {
   private final ExecutionList executionList = new ExecutionList();

   public static ListenableFutureTask create(Callable callable) {
      return new ListenableFutureTask(callable);
   }

   public static ListenableFutureTask create(Runnable runnable, @Nullable Object result) {
      return new ListenableFutureTask(runnable, result);
   }

   ListenableFutureTask(Callable callable) {
      super(callable);
   }

   ListenableFutureTask(Runnable runnable, @Nullable Object result) {
      super(runnable, result);
   }

   public void addListener(Runnable listener, Executor exec) {
      this.executionList.add(listener, exec);
   }

   protected void done() {
      this.executionList.execute();
   }
}
