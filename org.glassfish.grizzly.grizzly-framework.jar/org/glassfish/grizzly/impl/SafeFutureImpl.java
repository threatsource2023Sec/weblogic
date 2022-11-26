package org.glassfish.grizzly.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import org.glassfish.grizzly.CompletionHandler;

public class SafeFutureImpl implements FutureImpl {
   private final Object chSync = new Object();
   private Set completionHandlers;
   private final Sync sync = new Sync();

   public void addCompletionHandler(CompletionHandler completionHandler) {
      if (this.isDone()) {
         this.notifyCompletionHandler(completionHandler);
      } else {
         synchronized(this.chSync) {
            if (!this.isDone()) {
               if (this.completionHandlers == null) {
                  this.completionHandlers = new HashSet(2);
               }

               this.completionHandlers.add(completionHandler);
               return;
            }
         }

         this.notifyCompletionHandler(completionHandler);
      }

   }

   public static SafeFutureImpl create() {
      return new SafeFutureImpl();
   }

   public void result(Object result) {
      this.sync.innerSet(result);
   }

   public void failure(Throwable failure) {
      this.sync.innerSetException(failure);
   }

   public void markForRecycle(boolean recycleResult) {
   }

   public void recycle(boolean recycleResult) {
   }

   public void recycle() {
   }

   public Object getResult() {
      if (this.isDone()) {
         try {
            return this.get();
         } catch (Throwable var2) {
         }
      }

      return null;
   }

   protected void onComplete() {
   }

   private void notifyCompletionHandlers() {
      assert this.isDone();

      Set localSet;
      synchronized(this.chSync) {
         if (this.completionHandlers == null) {
            return;
         }

         localSet = this.completionHandlers;
         this.completionHandlers = null;
      }

      boolean isCancelled = this.isCancelled();
      Object result = this.sync.result;
      Throwable error = this.sync.exception;
      Iterator it = localSet.iterator();

      while(it.hasNext()) {
         CompletionHandler completionHandler = (CompletionHandler)it.next();
         it.remove();

         try {
            if (isCancelled) {
               completionHandler.cancelled();
            } else if (error != null) {
               completionHandler.failed(error);
            } else {
               completionHandler.completed(result);
            }
         } catch (Exception var8) {
         }
      }

   }

   private void notifyCompletionHandler(CompletionHandler completionHandler) {
      if (this.isCancelled()) {
         completionHandler.cancelled();
      } else {
         try {
            Object result = this.get();

            try {
               completionHandler.completed(result);
            } catch (Exception var4) {
            }
         } catch (ExecutionException var5) {
            completionHandler.failed(var5.getCause());
         } catch (Exception var6) {
            completionHandler.failed(var6);
         }
      }

   }

   public boolean isCancelled() {
      return this.sync.innerIsCancelled();
   }

   public boolean isDone() {
      return this.sync.ranOrCancelled();
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return this.sync.innerCancel(mayInterruptIfRunning);
   }

   public Object get() throws InterruptedException, ExecutionException {
      return this.sync.innerGet();
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.sync.innerGet(unit.toNanos(timeout));
   }

   protected void done() {
      this.notifyCompletionHandlers();
      this.onComplete();
   }

   private final class Sync extends AbstractQueuedSynchronizer {
      private static final long serialVersionUID = -7828117401763700385L;
      private static final int READY = 0;
      private static final int RESULT = 1;
      private static final int RAN = 2;
      private static final int CANCELLED = 3;
      private Object result;
      private Throwable exception;

      private Sync() {
      }

      private boolean ranOrCancelled() {
         return (this.getState() & 3) != 0;
      }

      protected int tryAcquireShared(int ignore) {
         return this.ranOrCancelled() ? 1 : -1;
      }

      protected boolean tryReleaseShared(int ignore) {
         return true;
      }

      boolean innerIsCancelled() {
         return this.getState() == 3;
      }

      Object innerGet() throws InterruptedException, ExecutionException {
         this.acquireSharedInterruptibly(0);
         if (this.getState() == 3) {
            throw new CancellationException();
         } else if (this.exception != null) {
            throw new ExecutionException(this.exception);
         } else {
            return this.result;
         }
      }

      Object innerGet(long nanosTimeout) throws InterruptedException, ExecutionException, TimeoutException {
         if (!this.tryAcquireSharedNanos(0, nanosTimeout)) {
            throw new TimeoutException();
         } else if (this.getState() == 3) {
            throw new CancellationException();
         } else if (this.exception != null) {
            throw new ExecutionException(this.exception);
         } else {
            return this.result;
         }
      }

      void innerSet(Object v) {
         if (this.compareAndSetState(0, 1)) {
            this.result = v;
            this.setState(2);
            this.releaseShared(0);
            SafeFutureImpl.this.done();
         }

      }

      void innerSetException(Throwable t) {
         if (this.compareAndSetState(0, 1)) {
            this.exception = t;
            this.setState(2);
            this.releaseShared(0);
            SafeFutureImpl.this.done();
         }

      }

      boolean innerCancel(boolean mayInterruptIfRunning) {
         if (this.compareAndSetState(0, 3)) {
            this.releaseShared(0);
            SafeFutureImpl.this.done();
            return true;
         } else {
            return false;
         }
      }

      // $FF: synthetic method
      Sync(Object x1) {
         this();
      }
   }
}
