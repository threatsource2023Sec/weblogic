package com.oracle.jrf.concurrent.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class DefaultExecutorService implements ExecutorService {
   private final ExecutorService delegate;

   public DefaultExecutorService(ExecutorService delegate) {
      this.delegate = delegate;
   }

   ExecutorService getDelegate() {
      return this.delegate;
   }

   public void shutdown() {
      throw new IllegalStateException();
   }

   public List shutdownNow() {
      throw new IllegalStateException();
   }

   public boolean isShutdown() {
      throw new IllegalStateException();
   }

   public boolean isTerminated() {
      throw new IllegalStateException();
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      throw new IllegalStateException();
   }

   public Future submit(Callable task) {
      return this.delegate.submit(this.wrap(task));
   }

   public Future submit(Runnable task, Object result) {
      return this.delegate.submit(this.wrap(task), result);
   }

   public Future submit(Runnable task) {
      return this.delegate.submit(this.wrap(task));
   }

   public List invokeAll(Collection tasks) throws InterruptedException {
      return this.delegate.invokeAll(this.wrap(tasks));
   }

   public List invokeAll(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException {
      return this.delegate.invokeAll(this.wrap(tasks), timeout, unit);
   }

   public Object invokeAny(Collection tasks) throws InterruptedException, ExecutionException {
      return this.delegate.invokeAny(this.wrap(tasks));
   }

   public Object invokeAny(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate.invokeAny(this.wrap(tasks), timeout, unit);
   }

   public void execute(Runnable command) {
      this.delegate.execute(this.wrap(command));
   }

   protected Runnable wrap(Runnable runnable) {
      return ContextService.getInstance().decorate(runnable);
   }

   protected Callable wrap(Callable callable) {
      return ContextService.getInstance().decorate(callable);
   }

   private Collection wrap(Collection tasks) {
      return ContextService.getInstance().decorate(tasks);
   }
}
