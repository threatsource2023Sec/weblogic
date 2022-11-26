package com.oracle.jrf.concurrent.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class ExecutorServiceDecorator implements ExecutorService {
   protected final ExecutorService delegate;

   public ExecutorServiceDecorator(ExecutorService delegate) {
      Util.checkNull(delegate);
      this.delegate = delegate;
   }

   public void shutdown() {
      this.delegate.shutdown();
   }

   public List shutdownNow() {
      return this.delegate.shutdownNow();
   }

   public boolean isShutdown() {
      return this.delegate.isShutdown();
   }

   public boolean isTerminated() {
      return this.delegate.isTerminated();
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      return this.delegate.awaitTermination(timeout, unit);
   }

   public Future submit(Callable task) {
      return this.delegate.submit(task);
   }

   public Future submit(Runnable task, Object result) {
      return this.delegate.submit(task, result);
   }

   public Future submit(Runnable task) {
      return this.delegate.submit(task);
   }

   public List invokeAll(Collection tasks) throws InterruptedException {
      return this.delegate.invokeAll(tasks);
   }

   public List invokeAll(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException {
      return this.delegate.invokeAll(tasks);
   }

   public Object invokeAny(Collection tasks) throws InterruptedException, ExecutionException {
      return this.delegate.invokeAny(tasks);
   }

   public Object invokeAny(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate.invokeAny(tasks, timeout, unit);
   }

   public void execute(Runnable command) {
      this.delegate.execute(command);
   }
}
