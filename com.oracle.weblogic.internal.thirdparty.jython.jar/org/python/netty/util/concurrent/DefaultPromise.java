package org.python.netty.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.python.netty.util.Signal;
import org.python.netty.util.internal.InternalThreadLocalMap;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.ThrowableUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class DefaultPromise extends AbstractFuture implements Promise {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
   private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
   private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, SystemPropertyUtil.getInt("org.python.netty.defaultPromise.maxListenerStackDepth", 8));
   private static final AtomicReferenceFieldUpdater RESULT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");
   private static final Signal SUCCESS = Signal.valueOf(DefaultPromise.class, "SUCCESS");
   private static final Signal UNCANCELLABLE = Signal.valueOf(DefaultPromise.class, "UNCANCELLABLE");
   private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(ThrowableUtil.unknownStackTrace(new CancellationException(), DefaultPromise.class, "cancel(...)"));
   private volatile Object result;
   private final EventExecutor executor;
   private Object listeners;
   private short waiters;
   private boolean notifyingListeners;

   public DefaultPromise(EventExecutor executor) {
      this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
   }

   protected DefaultPromise() {
      this.executor = null;
   }

   public Promise setSuccess(Object result) {
      if (this.setSuccess0(result)) {
         this.notifyListeners();
         return this;
      } else {
         throw new IllegalStateException("complete already: " + this);
      }
   }

   public boolean trySuccess(Object result) {
      if (this.setSuccess0(result)) {
         this.notifyListeners();
         return true;
      } else {
         return false;
      }
   }

   public Promise setFailure(Throwable cause) {
      if (this.setFailure0(cause)) {
         this.notifyListeners();
         return this;
      } else {
         throw new IllegalStateException("complete already: " + this, cause);
      }
   }

   public boolean tryFailure(Throwable cause) {
      if (this.setFailure0(cause)) {
         this.notifyListeners();
         return true;
      } else {
         return false;
      }
   }

   public boolean setUncancellable() {
      if (RESULT_UPDATER.compareAndSet(this, (Object)null, UNCANCELLABLE)) {
         return true;
      } else {
         Object result = this.result;
         return !isDone0(result) || !isCancelled0(result);
      }
   }

   public boolean isSuccess() {
      Object result = this.result;
      return result != null && result != UNCANCELLABLE && !(result instanceof CauseHolder);
   }

   public boolean isCancellable() {
      return this.result == null;
   }

   public Throwable cause() {
      Object result = this.result;
      return result instanceof CauseHolder ? ((CauseHolder)result).cause : null;
   }

   public Promise addListener(GenericFutureListener listener) {
      ObjectUtil.checkNotNull(listener, "listener");
      synchronized(this) {
         this.addListener0(listener);
      }

      if (this.isDone()) {
         this.notifyListeners();
      }

      return this;
   }

   public Promise addListeners(GenericFutureListener... listeners) {
      ObjectUtil.checkNotNull(listeners, "listeners");
      synchronized(this) {
         GenericFutureListener[] var3 = listeners;
         int var4 = listeners.length;
         int var5 = 0;

         while(var5 < var4) {
            GenericFutureListener listener = var3[var5];
            if (listener != null) {
               this.addListener0(listener);
               ++var5;
               continue;
            }
         }
      }

      if (this.isDone()) {
         this.notifyListeners();
      }

      return this;
   }

   public Promise removeListener(GenericFutureListener listener) {
      ObjectUtil.checkNotNull(listener, "listener");
      synchronized(this) {
         this.removeListener0(listener);
         return this;
      }
   }

   public Promise removeListeners(GenericFutureListener... listeners) {
      ObjectUtil.checkNotNull(listeners, "listeners");
      synchronized(this) {
         GenericFutureListener[] var3 = listeners;
         int var4 = listeners.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            GenericFutureListener listener = var3[var5];
            if (listener == null) {
               break;
            }

            this.removeListener0(listener);
         }

         return this;
      }
   }

   public Promise await() throws InterruptedException {
      if (this.isDone()) {
         return this;
      } else if (Thread.interrupted()) {
         throw new InterruptedException(this.toString());
      } else {
         this.checkDeadLock();
         synchronized(this) {
            while(!this.isDone()) {
               this.incWaiters();

               try {
                  this.wait();
               } finally {
                  this.decWaiters();
               }
            }

            return this;
         }
      }
   }

   public Promise awaitUninterruptibly() {
      if (this.isDone()) {
         return this;
      } else {
         this.checkDeadLock();
         boolean interrupted = false;
         synchronized(this) {
            while(!this.isDone()) {
               this.incWaiters();

               try {
                  this.wait();
               } catch (InterruptedException var9) {
                  interrupted = true;
               } finally {
                  this.decWaiters();
               }
            }
         }

         if (interrupted) {
            Thread.currentThread().interrupt();
         }

         return this;
      }
   }

   public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
      return this.await0(unit.toNanos(timeout), true);
   }

   public boolean await(long timeoutMillis) throws InterruptedException {
      return this.await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
   }

   public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
      try {
         return this.await0(unit.toNanos(timeout), false);
      } catch (InterruptedException var5) {
         throw new InternalError();
      }
   }

   public boolean awaitUninterruptibly(long timeoutMillis) {
      try {
         return this.await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
      } catch (InterruptedException var4) {
         throw new InternalError();
      }
   }

   public Object getNow() {
      Object result = this.result;
      return !(result instanceof CauseHolder) && result != SUCCESS ? result : null;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      if (RESULT_UPDATER.compareAndSet(this, (Object)null, CANCELLATION_CAUSE_HOLDER)) {
         this.checkNotifyWaiters();
         this.notifyListeners();
         return true;
      } else {
         return false;
      }
   }

   public boolean isCancelled() {
      return isCancelled0(this.result);
   }

   public boolean isDone() {
      return isDone0(this.result);
   }

   public Promise sync() throws InterruptedException {
      this.await();
      this.rethrowIfFailed();
      return this;
   }

   public Promise syncUninterruptibly() {
      this.awaitUninterruptibly();
      this.rethrowIfFailed();
      return this;
   }

   public String toString() {
      return this.toStringBuilder().toString();
   }

   protected StringBuilder toStringBuilder() {
      StringBuilder buf = (new StringBuilder(64)).append(StringUtil.simpleClassName((Object)this)).append('@').append(Integer.toHexString(this.hashCode()));
      Object result = this.result;
      if (result == SUCCESS) {
         buf.append("(success)");
      } else if (result == UNCANCELLABLE) {
         buf.append("(uncancellable)");
      } else if (result instanceof CauseHolder) {
         buf.append("(failure: ").append(((CauseHolder)result).cause).append(')');
      } else if (result != null) {
         buf.append("(success: ").append(result).append(')');
      } else {
         buf.append("(incomplete)");
      }

      return buf;
   }

   protected EventExecutor executor() {
      return this.executor;
   }

   protected void checkDeadLock() {
      EventExecutor e = this.executor();
      if (e != null && e.inEventLoop()) {
         throw new BlockingOperationException(this.toString());
      }
   }

   protected static void notifyListener(EventExecutor eventExecutor, Future future, GenericFutureListener listener) {
      ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
      ObjectUtil.checkNotNull(future, "future");
      ObjectUtil.checkNotNull(listener, "listener");
      notifyListenerWithStackOverFlowProtection(eventExecutor, future, listener);
   }

   private void notifyListeners() {
      EventExecutor executor = this.executor();
      if (executor.inEventLoop()) {
         InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
         int stackDepth = threadLocals.futureListenerStackDepth();
         if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
            threadLocals.setFutureListenerStackDepth(stackDepth + 1);

            try {
               this.notifyListenersNow();
            } finally {
               threadLocals.setFutureListenerStackDepth(stackDepth);
            }

            return;
         }
      }

      safeExecute(executor, new Runnable() {
         public void run() {
            DefaultPromise.this.notifyListenersNow();
         }
      });
   }

   private static void notifyListenerWithStackOverFlowProtection(EventExecutor executor, final Future future, final GenericFutureListener listener) {
      if (executor.inEventLoop()) {
         InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
         int stackDepth = threadLocals.futureListenerStackDepth();
         if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
            threadLocals.setFutureListenerStackDepth(stackDepth + 1);

            try {
               notifyListener0(future, listener);
            } finally {
               threadLocals.setFutureListenerStackDepth(stackDepth);
            }

            return;
         }
      }

      safeExecute(executor, new Runnable() {
         public void run() {
            DefaultPromise.notifyListener0(future, listener);
         }
      });
   }

   private void notifyListenersNow() {
      Object listeners;
      synchronized(this) {
         if (this.notifyingListeners || this.listeners == null) {
            return;
         }

         this.notifyingListeners = true;
         listeners = this.listeners;
         this.listeners = null;
      }

      while(true) {
         if (listeners instanceof DefaultFutureListeners) {
            this.notifyListeners0((DefaultFutureListeners)listeners);
         } else {
            notifyListener0(this, (GenericFutureListener)listeners);
         }

         synchronized(this) {
            if (this.listeners == null) {
               this.notifyingListeners = false;
               return;
            }

            listeners = this.listeners;
            this.listeners = null;
         }
      }
   }

   private void notifyListeners0(DefaultFutureListeners listeners) {
      GenericFutureListener[] a = listeners.listeners();
      int size = listeners.size();

      for(int i = 0; i < size; ++i) {
         notifyListener0(this, a[i]);
      }

   }

   private static void notifyListener0(Future future, GenericFutureListener l) {
      try {
         l.operationComplete(future);
      } catch (Throwable var3) {
         logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", var3);
      }

   }

   private void addListener0(GenericFutureListener listener) {
      if (this.listeners == null) {
         this.listeners = listener;
      } else if (this.listeners instanceof DefaultFutureListeners) {
         ((DefaultFutureListeners)this.listeners).add(listener);
      } else {
         this.listeners = new DefaultFutureListeners((GenericFutureListener)this.listeners, listener);
      }

   }

   private void removeListener0(GenericFutureListener listener) {
      if (this.listeners instanceof DefaultFutureListeners) {
         ((DefaultFutureListeners)this.listeners).remove(listener);
      } else if (this.listeners == listener) {
         this.listeners = null;
      }

   }

   private boolean setSuccess0(Object result) {
      return this.setValue0(result == null ? SUCCESS : result);
   }

   private boolean setFailure0(Throwable cause) {
      return this.setValue0(new CauseHolder((Throwable)ObjectUtil.checkNotNull(cause, "cause")));
   }

   private boolean setValue0(Object objResult) {
      if (!RESULT_UPDATER.compareAndSet(this, (Object)null, objResult) && !RESULT_UPDATER.compareAndSet(this, UNCANCELLABLE, objResult)) {
         return false;
      } else {
         this.checkNotifyWaiters();
         return true;
      }
   }

   private synchronized void checkNotifyWaiters() {
      if (this.waiters > 0) {
         this.notifyAll();
      }

   }

   private void incWaiters() {
      if (this.waiters == 32767) {
         throw new IllegalStateException("too many waiters: " + this);
      } else {
         ++this.waiters;
      }
   }

   private void decWaiters() {
      --this.waiters;
   }

   private void rethrowIfFailed() {
      Throwable cause = this.cause();
      if (cause != null) {
         PlatformDependent.throwException(cause);
      }
   }

   private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
      if (this.isDone()) {
         return true;
      } else if (timeoutNanos <= 0L) {
         return this.isDone();
      } else if (interruptable && Thread.interrupted()) {
         throw new InterruptedException(this.toString());
      } else {
         this.checkDeadLock();
         long startTime = System.nanoTime();
         long waitTime = timeoutNanos;
         boolean interrupted = false;

         try {
            boolean var9;
            do {
               synchronized(this) {
                  if (this.isDone()) {
                     boolean var10 = true;
                     return var10;
                  }

                  this.incWaiters();

                  try {
                     this.wait(waitTime / 1000000L, (int)(waitTime % 1000000L));
                  } catch (InterruptedException var22) {
                     if (interruptable) {
                        throw var22;
                     }

                     interrupted = true;
                  } finally {
                     this.decWaiters();
                  }
               }

               if (this.isDone()) {
                  var9 = true;
                  return var9;
               }

               waitTime = timeoutNanos - (System.nanoTime() - startTime);
            } while(waitTime > 0L);

            var9 = this.isDone();
            return var9;
         } finally {
            if (interrupted) {
               Thread.currentThread().interrupt();
            }

         }
      }
   }

   void notifyProgressiveListeners(final long progress, final long total) {
      Object listeners = this.progressiveListeners();
      if (listeners != null) {
         final ProgressiveFuture self = (ProgressiveFuture)this;
         EventExecutor executor = this.executor();
         if (executor.inEventLoop()) {
            if (listeners instanceof GenericProgressiveFutureListener[]) {
               notifyProgressiveListeners0(self, (GenericProgressiveFutureListener[])((GenericProgressiveFutureListener[])listeners), progress, total);
            } else {
               notifyProgressiveListener0(self, (GenericProgressiveFutureListener)listeners, progress, total);
            }
         } else if (listeners instanceof GenericProgressiveFutureListener[]) {
            final GenericProgressiveFutureListener[] array = (GenericProgressiveFutureListener[])((GenericProgressiveFutureListener[])listeners);
            safeExecute(executor, new Runnable() {
               public void run() {
                  DefaultPromise.notifyProgressiveListeners0(self, array, progress, total);
               }
            });
         } else {
            final GenericProgressiveFutureListener l = (GenericProgressiveFutureListener)listeners;
            safeExecute(executor, new Runnable() {
               public void run() {
                  DefaultPromise.notifyProgressiveListener0(self, l, progress, total);
               }
            });
         }

      }
   }

   private synchronized Object progressiveListeners() {
      Object listeners = this.listeners;
      if (listeners == null) {
         return null;
      } else if (listeners instanceof DefaultFutureListeners) {
         DefaultFutureListeners dfl = (DefaultFutureListeners)listeners;
         int progressiveSize = dfl.progressiveSize();
         GenericFutureListener[] array;
         int i;
         switch (progressiveSize) {
            case 0:
               return null;
            case 1:
               array = dfl.listeners();
               int var5 = array.length;

               for(i = 0; i < var5; ++i) {
                  GenericFutureListener l = array[i];
                  if (l instanceof GenericProgressiveFutureListener) {
                     return l;
                  }
               }

               return null;
            default:
               array = dfl.listeners();
               GenericProgressiveFutureListener[] copy = new GenericProgressiveFutureListener[progressiveSize];
               i = 0;

               for(int j = 0; j < progressiveSize; ++i) {
                  GenericFutureListener l = array[i];
                  if (l instanceof GenericProgressiveFutureListener) {
                     copy[j++] = (GenericProgressiveFutureListener)l;
                  }
               }

               return copy;
         }
      } else {
         return listeners instanceof GenericProgressiveFutureListener ? listeners : null;
      }
   }

   private static void notifyProgressiveListeners0(ProgressiveFuture future, GenericProgressiveFutureListener[] listeners, long progress, long total) {
      GenericProgressiveFutureListener[] var6 = listeners;
      int var7 = listeners.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         GenericProgressiveFutureListener l = var6[var8];
         if (l == null) {
            break;
         }

         notifyProgressiveListener0(future, l, progress, total);
      }

   }

   private static void notifyProgressiveListener0(ProgressiveFuture future, GenericProgressiveFutureListener l, long progress, long total) {
      try {
         l.operationProgressed(future, progress, total);
      } catch (Throwable var7) {
         logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationProgressed()", var7);
      }

   }

   private static boolean isCancelled0(Object result) {
      return result instanceof CauseHolder && ((CauseHolder)result).cause instanceof CancellationException;
   }

   private static boolean isDone0(Object result) {
      return result != null && result != UNCANCELLABLE;
   }

   private static void safeExecute(EventExecutor executor, Runnable task) {
      try {
         executor.execute(task);
      } catch (Throwable var3) {
         rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", var3);
      }

   }

   private static final class CauseHolder {
      final Throwable cause;

      CauseHolder(Throwable cause) {
         this.cause = cause;
      }
   }
}
