package org.glassfish.grizzly.impl;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.ThreadCache;

public final class ReadyFutureImpl implements FutureImpl {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(ReadyFutureImpl.class, 4);
   protected Object result;
   private Throwable failure;
   private boolean isCancelled;

   public static ReadyFutureImpl create() {
      ReadyFutureImpl future = takeFromCache();
      if (future != null) {
         future.isCancelled = true;
         return future;
      } else {
         return new ReadyFutureImpl();
      }
   }

   public static ReadyFutureImpl create(Object result) {
      ReadyFutureImpl future = takeFromCache();
      if (future != null) {
         future.result = result;
         return future;
      } else {
         return new ReadyFutureImpl(result);
      }
   }

   public static ReadyFutureImpl create(Throwable failure) {
      ReadyFutureImpl future = takeFromCache();
      if (future != null) {
         future.failure = failure;
         return future;
      } else {
         return new ReadyFutureImpl(failure);
      }
   }

   private static ReadyFutureImpl takeFromCache() {
      return (ReadyFutureImpl)ThreadCache.takeFromCache(CACHE_IDX);
   }

   private ReadyFutureImpl() {
      this((Object)null, (Throwable)null, true);
   }

   private ReadyFutureImpl(Object result) {
      this(result, (Throwable)null, false);
   }

   private ReadyFutureImpl(Throwable failure) {
      this((Object)null, failure, false);
   }

   private ReadyFutureImpl(Object result, Throwable failure, boolean isCancelled) {
      this.result = result;
      this.failure = failure;
      this.isCancelled = isCancelled;
   }

   public void addCompletionHandler(CompletionHandler completionHandler) {
      if (this.isCancelled) {
         completionHandler.cancelled();
      } else if (this.failure != null) {
         completionHandler.failed(this.failure);
      } else {
         completionHandler.completed(this.result);
      }

   }

   public Object getResult() {
      return this.result;
   }

   public void setResult(Object result) {
      throw new IllegalStateException("Can not be reset on ReadyFutureImpl");
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return this.isCancelled;
   }

   public boolean isCancelled() {
      return this.isCancelled;
   }

   public boolean isDone() {
      return true;
   }

   public Object get() throws InterruptedException, ExecutionException {
      if (this.isCancelled) {
         throw new CancellationException();
      } else if (this.failure != null) {
         throw new ExecutionException(this.failure);
      } else {
         return this.result;
      }
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      if (this.isCancelled) {
         throw new CancellationException();
      } else if (this.failure != null) {
         throw new ExecutionException(this.failure);
      } else if (this.result != null) {
         return this.result;
      } else {
         throw new TimeoutException();
      }
   }

   public void failure(Throwable failure) {
      throw new IllegalStateException("Can not be reset on ReadyFutureImpl");
   }

   public void result(Object result) {
      throw new IllegalStateException("Can not be reset on ReadyFutureImpl");
   }

   private void reset() {
      this.result = null;
      this.failure = null;
      this.isCancelled = false;
   }

   public void markForRecycle(boolean recycleResult) {
      this.recycle(recycleResult);
   }

   public void recycle() {
      this.recycle(false);
   }

   public void recycle(boolean recycleResult) {
      if (recycleResult && this.result != null && this.result instanceof Cacheable) {
         ((Cacheable)this.result).recycle();
      }

      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }
}
