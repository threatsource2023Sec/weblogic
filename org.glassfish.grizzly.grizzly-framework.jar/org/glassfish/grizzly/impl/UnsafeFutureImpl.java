package org.glassfish.grizzly.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.ThreadCache;

public final class UnsafeFutureImpl implements FutureImpl {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(UnsafeFutureImpl.class, 4);
   protected boolean isDone;
   protected boolean isCancelled;
   protected Throwable failure;
   protected Set completionHandlers;
   protected Object result;
   protected int recycleMark;

   public static UnsafeFutureImpl create() {
      UnsafeFutureImpl future = (UnsafeFutureImpl)ThreadCache.takeFromCache(CACHE_IDX);
      return future != null ? future : new UnsafeFutureImpl();
   }

   private UnsafeFutureImpl() {
   }

   public void addCompletionHandler(CompletionHandler completionHandler) {
      if (this.isDone) {
         this.notifyCompletionHandler(completionHandler);
      } else {
         if (this.completionHandlers == null) {
            this.completionHandlers = new HashSet(2);
         }

         this.completionHandlers.add(completionHandler);
      }

   }

   public Object getResult() {
      return this.result;
   }

   public void result(Object result) {
      this.result = result;
      this.notifyHaveResult();
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      this.isCancelled = true;
      this.notifyHaveResult();
      return true;
   }

   public boolean isCancelled() {
      return this.isCancelled;
   }

   public boolean isDone() {
      return this.isDone;
   }

   public Object get() throws InterruptedException, ExecutionException {
      if (this.isDone) {
         if (this.isCancelled) {
            throw new CancellationException();
         }

         if (this.failure != null) {
            throw new ExecutionException(this.failure);
         }

         if (this.result != null) {
            return this.result;
         }
      }

      throw new ExecutionException(new IllegalStateException("Result is not ready"));
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.get();
   }

   public void failure(Throwable failure) {
      this.failure = failure;
      this.notifyHaveResult();
   }

   protected void notifyHaveResult() {
      if (this.recycleMark == 0) {
         this.isDone = true;
         this.notifyCompletionHandlers();
      } else {
         this.recycle(this.recycleMark == 2);
      }

   }

   private void notifyCompletionHandlers() {
      if (this.completionHandlers != null) {
         Iterator var1 = this.completionHandlers.iterator();

         while(var1.hasNext()) {
            CompletionHandler completionHandler = (CompletionHandler)var1.next();
            this.notifyCompletionHandler(completionHandler);
         }

         this.completionHandlers = null;
      }

   }

   private void notifyCompletionHandler(CompletionHandler completionHandler) {
      try {
         if (this.isCancelled) {
            completionHandler.cancelled();
         } else if (this.failure != null) {
            completionHandler.failed(this.failure);
         } else if (this.result != null) {
            completionHandler.completed(this.result);
         }
      } catch (Exception var3) {
      }

   }

   public void markForRecycle(boolean recycleResult) {
      if (this.isDone) {
         this.recycle(recycleResult);
      } else {
         this.recycleMark = 1 + (recycleResult ? 1 : 0);
      }

   }

   protected void reset() {
      this.completionHandlers = null;
      this.result = null;
      this.failure = null;
      this.isCancelled = false;
      this.isDone = false;
      this.recycleMark = 0;
   }

   public void recycle(boolean recycleResult) {
      if (recycleResult && this.result != null && this.result instanceof Cacheable) {
         ((Cacheable)this.result).recycle();
      }

      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public void recycle() {
      this.recycle(false);
   }
}
