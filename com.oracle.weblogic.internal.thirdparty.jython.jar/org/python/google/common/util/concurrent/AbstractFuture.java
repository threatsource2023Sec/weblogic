package org.python.google.common.util.concurrent;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Throwables;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.DoNotMock;
import sun.misc.Unsafe;

@DoNotMock("Use Futures.immediate*Future or SettableFuture")
@GwtCompatible(
   emulated = true
)
public abstract class AbstractFuture implements ListenableFuture {
   private static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
   private static final Logger log = Logger.getLogger(AbstractFuture.class.getName());
   private static final long SPIN_THRESHOLD_NANOS = 1000L;
   private static final AtomicHelper ATOMIC_HELPER;
   private static final Object NULL;
   private volatile Object value;
   private volatile Listener listeners;
   private volatile Waiter waiters;

   private void removeWaiter(Waiter node) {
      node.thread = null;

      label28:
      while(true) {
         Waiter pred = null;
         Waiter curr = this.waiters;
         if (curr == AbstractFuture.Waiter.TOMBSTONE) {
            return;
         }

         Waiter succ;
         for(; curr != null; curr = succ) {
            succ = curr.next;
            if (curr.thread != null) {
               pred = curr;
            } else if (pred != null) {
               pred.next = succ;
               if (pred.thread == null) {
                  continue label28;
               }
            } else if (!ATOMIC_HELPER.casWaiters(this, curr, succ)) {
               continue label28;
            }
         }

         return;
      }
   }

   protected AbstractFuture() {
   }

   @CanIgnoreReturnValue
   public Object get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
      long remainingNanos = unit.toNanos(timeout);
      if (Thread.interrupted()) {
         throw new InterruptedException();
      } else {
         Object localValue = this.value;
         if (localValue != null & !(localValue instanceof SetFuture)) {
            return this.getDoneValue(localValue);
         } else {
            long endNanos = remainingNanos > 0L ? System.nanoTime() + remainingNanos : 0L;
            if (remainingNanos >= 1000L) {
               label117: {
                  Waiter oldHead = this.waiters;
                  if (oldHead != AbstractFuture.Waiter.TOMBSTONE) {
                     Waiter node = new Waiter();

                     do {
                        node.setNext(oldHead);
                        if (ATOMIC_HELPER.casWaiters(this, oldHead, node)) {
                           do {
                              LockSupport.parkNanos(this, remainingNanos);
                              if (Thread.interrupted()) {
                                 this.removeWaiter(node);
                                 throw new InterruptedException();
                              }

                              localValue = this.value;
                              if (localValue != null & !(localValue instanceof SetFuture)) {
                                 return this.getDoneValue(localValue);
                              }

                              remainingNanos = endNanos - System.nanoTime();
                           } while(remainingNanos >= 1000L);

                           this.removeWaiter(node);
                           break label117;
                        }

                        oldHead = this.waiters;
                     } while(oldHead != AbstractFuture.Waiter.TOMBSTONE);
                  }

                  return this.getDoneValue(this.value);
               }
            }

            while(remainingNanos > 0L) {
               localValue = this.value;
               if (localValue != null & !(localValue instanceof SetFuture)) {
                  return this.getDoneValue(localValue);
               }

               if (Thread.interrupted()) {
                  throw new InterruptedException();
               }

               remainingNanos = endNanos - System.nanoTime();
            }

            throw new TimeoutException();
         }
      }
   }

   @CanIgnoreReturnValue
   public Object get() throws InterruptedException, ExecutionException {
      if (Thread.interrupted()) {
         throw new InterruptedException();
      } else {
         Object localValue = this.value;
         if (localValue != null & !(localValue instanceof SetFuture)) {
            return this.getDoneValue(localValue);
         } else {
            Waiter oldHead = this.waiters;
            if (oldHead != AbstractFuture.Waiter.TOMBSTONE) {
               Waiter node = new Waiter();

               do {
                  node.setNext(oldHead);
                  if (ATOMIC_HELPER.casWaiters(this, oldHead, node)) {
                     do {
                        LockSupport.park(this);
                        if (Thread.interrupted()) {
                           this.removeWaiter(node);
                           throw new InterruptedException();
                        }

                        localValue = this.value;
                     } while(!(localValue != null & !(localValue instanceof SetFuture)));

                     return this.getDoneValue(localValue);
                  }

                  oldHead = this.waiters;
               } while(oldHead != AbstractFuture.Waiter.TOMBSTONE);
            }

            return this.getDoneValue(this.value);
         }
      }
   }

   private Object getDoneValue(Object obj) throws ExecutionException {
      if (obj instanceof Cancellation) {
         throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation)obj).cause);
      } else if (obj instanceof Failure) {
         throw new ExecutionException(((Failure)obj).exception);
      } else {
         return obj == NULL ? null : obj;
      }
   }

   public boolean isDone() {
      Object localValue = this.value;
      return localValue != null & !(localValue instanceof SetFuture);
   }

   public boolean isCancelled() {
      Object localValue = this.value;
      return localValue instanceof Cancellation;
   }

   @CanIgnoreReturnValue
   public boolean cancel(boolean mayInterruptIfRunning) {
      Object localValue = this.value;
      boolean rValue = false;
      if (localValue == null | localValue instanceof SetFuture) {
         Throwable cause = GENERATE_CANCELLATION_CAUSES ? new CancellationException("Future.cancel() was called.") : null;
         Object valueToSet = new Cancellation(mayInterruptIfRunning, cause);
         AbstractFuture abstractFuture = this;

         while(true) {
            while(!ATOMIC_HELPER.casValue(abstractFuture, localValue, valueToSet)) {
               localValue = abstractFuture.value;
               if (!(localValue instanceof SetFuture)) {
                  return rValue;
               }
            }

            rValue = true;
            if (mayInterruptIfRunning) {
               abstractFuture.interruptTask();
            }

            complete(abstractFuture);
            if (!(localValue instanceof SetFuture)) {
               break;
            }

            ListenableFuture futureToPropagateTo = ((SetFuture)localValue).future;
            if (!(futureToPropagateTo instanceof TrustedFuture)) {
               futureToPropagateTo.cancel(mayInterruptIfRunning);
               break;
            }

            AbstractFuture trusted = (AbstractFuture)futureToPropagateTo;
            localValue = trusted.value;
            if (!(localValue == null | localValue instanceof SetFuture)) {
               break;
            }

            abstractFuture = trusted;
         }
      }

      return rValue;
   }

   protected void interruptTask() {
   }

   protected final boolean wasInterrupted() {
      Object localValue = this.value;
      return localValue instanceof Cancellation && ((Cancellation)localValue).wasInterrupted;
   }

   public void addListener(Runnable listener, Executor executor) {
      Preconditions.checkNotNull(listener, "Runnable was null.");
      Preconditions.checkNotNull(executor, "Executor was null.");
      Listener oldHead = this.listeners;
      if (oldHead != AbstractFuture.Listener.TOMBSTONE) {
         Listener newNode = new Listener(listener, executor);

         do {
            newNode.next = oldHead;
            if (ATOMIC_HELPER.casListeners(this, oldHead, newNode)) {
               return;
            }

            oldHead = this.listeners;
         } while(oldHead != AbstractFuture.Listener.TOMBSTONE);
      }

      executeListener(listener, executor);
   }

   @CanIgnoreReturnValue
   protected boolean set(@Nullable Object value) {
      Object valueToSet = value == null ? NULL : value;
      if (ATOMIC_HELPER.casValue(this, (Object)null, valueToSet)) {
         complete(this);
         return true;
      } else {
         return false;
      }
   }

   @CanIgnoreReturnValue
   protected boolean setException(Throwable throwable) {
      Object valueToSet = new Failure((Throwable)Preconditions.checkNotNull(throwable));
      if (ATOMIC_HELPER.casValue(this, (Object)null, valueToSet)) {
         complete(this);
         return true;
      } else {
         return false;
      }
   }

   @Beta
   @CanIgnoreReturnValue
   protected boolean setFuture(ListenableFuture future) {
      Preconditions.checkNotNull(future);
      Object localValue = this.value;
      if (localValue == null) {
         if (future.isDone()) {
            Object value = getFutureValue(future);
            if (ATOMIC_HELPER.casValue(this, (Object)null, value)) {
               complete(this);
               return true;
            }

            return false;
         }

         SetFuture valueToSet = new SetFuture(this, future);
         if (ATOMIC_HELPER.casValue(this, (Object)null, valueToSet)) {
            try {
               future.addListener(valueToSet, MoreExecutors.directExecutor());
            } catch (Throwable var8) {
               Throwable t = var8;

               Failure failure;
               try {
                  failure = new Failure(t);
               } catch (Throwable var7) {
                  failure = AbstractFuture.Failure.FALLBACK_INSTANCE;
               }

               ATOMIC_HELPER.casValue(this, valueToSet, failure);
            }

            return true;
         }

         localValue = this.value;
      }

      if (localValue instanceof Cancellation) {
         future.cancel(((Cancellation)localValue).wasInterrupted);
      }

      return false;
   }

   private static Object getFutureValue(ListenableFuture future) {
      if (future instanceof TrustedFuture) {
         return ((AbstractFuture)future).value;
      } else {
         Object valueToSet;
         try {
            Object v = Futures.getDone(future);
            valueToSet = v == null ? NULL : v;
         } catch (ExecutionException var3) {
            valueToSet = new Failure(var3.getCause());
         } catch (CancellationException var4) {
            valueToSet = new Cancellation(false, var4);
         } catch (Throwable var5) {
            valueToSet = new Failure(var5);
         }

         return valueToSet;
      }
   }

   private static void complete(AbstractFuture future) {
      Listener next = null;

      label23:
      while(true) {
         future.releaseWaiters();
         future.afterDone();
         next = future.clearListeners(next);
         future = null;

         while(next != null) {
            Listener curr = next;
            next = next.next;
            Runnable task = curr.task;
            if (task instanceof SetFuture) {
               SetFuture setFuture = (SetFuture)task;
               future = setFuture.owner;
               if (future.value == setFuture) {
                  Object valueToSet = getFutureValue(setFuture.future);
                  if (ATOMIC_HELPER.casValue(future, setFuture, valueToSet)) {
                     continue label23;
                  }
               }
            } else {
               executeListener(task, curr.executor);
            }
         }

         return;
      }
   }

   @Beta
   protected void afterDone() {
   }

   final Throwable trustedGetException() {
      return ((Failure)this.value).exception;
   }

   final void maybePropagateCancellation(@Nullable Future related) {
      if (related != null & this.isCancelled()) {
         related.cancel(this.wasInterrupted());
      }

   }

   private void releaseWaiters() {
      Waiter head;
      do {
         head = this.waiters;
      } while(!ATOMIC_HELPER.casWaiters(this, head, AbstractFuture.Waiter.TOMBSTONE));

      for(Waiter currentWaiter = head; currentWaiter != null; currentWaiter = currentWaiter.next) {
         currentWaiter.unpark();
      }

   }

   private Listener clearListeners(Listener onto) {
      Listener head;
      do {
         head = this.listeners;
      } while(!ATOMIC_HELPER.casListeners(this, head, AbstractFuture.Listener.TOMBSTONE));

      Listener reversedList;
      Listener tmp;
      for(reversedList = onto; head != null; reversedList = tmp) {
         tmp = head;
         head = head.next;
         tmp.next = reversedList;
      }

      return reversedList;
   }

   private static void executeListener(Runnable runnable, Executor executor) {
      try {
         executor.execute(runnable);
      } catch (RuntimeException var3) {
         log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, var3);
      }

   }

   private static CancellationException cancellationExceptionWithCause(@Nullable String message, @Nullable Throwable cause) {
      CancellationException exception = new CancellationException(message);
      exception.initCause(cause);
      return exception;
   }

   static {
      Object helper;
      try {
         helper = new UnsafeAtomicHelper();
      } catch (Throwable var4) {
         try {
            helper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "thread"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "next"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Waiter.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Listener.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Object.class, "value"));
         } catch (Throwable var3) {
            log.log(Level.SEVERE, "UnsafeAtomicHelper is broken!", var4);
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", var3);
            helper = new SynchronizedHelper();
         }
      }

      ATOMIC_HELPER = (AtomicHelper)helper;
      Class var1 = LockSupport.class;
      NULL = new Object();
   }

   private static final class SynchronizedHelper extends AtomicHelper {
      private SynchronizedHelper() {
         super(null);
      }

      void putThread(Waiter waiter, Thread newValue) {
         waiter.thread = newValue;
      }

      void putNext(Waiter waiter, Waiter newValue) {
         waiter.next = newValue;
      }

      boolean casWaiters(AbstractFuture future, Waiter expect, Waiter update) {
         synchronized(future) {
            if (future.waiters == expect) {
               future.waiters = update;
               return true;
            } else {
               return false;
            }
         }
      }

      boolean casListeners(AbstractFuture future, Listener expect, Listener update) {
         synchronized(future) {
            if (future.listeners == expect) {
               future.listeners = update;
               return true;
            } else {
               return false;
            }
         }
      }

      boolean casValue(AbstractFuture future, Object expect, Object update) {
         synchronized(future) {
            if (future.value == expect) {
               future.value = update;
               return true;
            } else {
               return false;
            }
         }
      }

      // $FF: synthetic method
      SynchronizedHelper(Object x0) {
         this();
      }
   }

   private static final class SafeAtomicHelper extends AtomicHelper {
      final AtomicReferenceFieldUpdater waiterThreadUpdater;
      final AtomicReferenceFieldUpdater waiterNextUpdater;
      final AtomicReferenceFieldUpdater waitersUpdater;
      final AtomicReferenceFieldUpdater listenersUpdater;
      final AtomicReferenceFieldUpdater valueUpdater;

      SafeAtomicHelper(AtomicReferenceFieldUpdater waiterThreadUpdater, AtomicReferenceFieldUpdater waiterNextUpdater, AtomicReferenceFieldUpdater waitersUpdater, AtomicReferenceFieldUpdater listenersUpdater, AtomicReferenceFieldUpdater valueUpdater) {
         super(null);
         this.waiterThreadUpdater = waiterThreadUpdater;
         this.waiterNextUpdater = waiterNextUpdater;
         this.waitersUpdater = waitersUpdater;
         this.listenersUpdater = listenersUpdater;
         this.valueUpdater = valueUpdater;
      }

      void putThread(Waiter waiter, Thread newValue) {
         this.waiterThreadUpdater.lazySet(waiter, newValue);
      }

      void putNext(Waiter waiter, Waiter newValue) {
         this.waiterNextUpdater.lazySet(waiter, newValue);
      }

      boolean casWaiters(AbstractFuture future, Waiter expect, Waiter update) {
         return this.waitersUpdater.compareAndSet(future, expect, update);
      }

      boolean casListeners(AbstractFuture future, Listener expect, Listener update) {
         return this.listenersUpdater.compareAndSet(future, expect, update);
      }

      boolean casValue(AbstractFuture future, Object expect, Object update) {
         return this.valueUpdater.compareAndSet(future, expect, update);
      }
   }

   private static final class UnsafeAtomicHelper extends AtomicHelper {
      static final Unsafe UNSAFE;
      static final long LISTENERS_OFFSET;
      static final long WAITERS_OFFSET;
      static final long VALUE_OFFSET;
      static final long WAITER_THREAD_OFFSET;
      static final long WAITER_NEXT_OFFSET;

      private UnsafeAtomicHelper() {
         super(null);
      }

      void putThread(Waiter waiter, Thread newValue) {
         UNSAFE.putObject(waiter, WAITER_THREAD_OFFSET, newValue);
      }

      void putNext(Waiter waiter, Waiter newValue) {
         UNSAFE.putObject(waiter, WAITER_NEXT_OFFSET, newValue);
      }

      boolean casWaiters(AbstractFuture future, Waiter expect, Waiter update) {
         return UNSAFE.compareAndSwapObject(future, WAITERS_OFFSET, expect, update);
      }

      boolean casListeners(AbstractFuture future, Listener expect, Listener update) {
         return UNSAFE.compareAndSwapObject(future, LISTENERS_OFFSET, expect, update);
      }

      boolean casValue(AbstractFuture future, Object expect, Object update) {
         return UNSAFE.compareAndSwapObject(future, VALUE_OFFSET, expect, update);
      }

      // $FF: synthetic method
      UnsafeAtomicHelper(Object x0) {
         this();
      }

      static {
         Unsafe unsafe = null;

         try {
            unsafe = Unsafe.getUnsafe();
         } catch (SecurityException var5) {
            try {
               unsafe = (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                  public Unsafe run() throws Exception {
                     Class k = Unsafe.class;
                     Field[] var2 = k.getDeclaredFields();
                     int var3 = var2.length;

                     for(int var4 = 0; var4 < var3; ++var4) {
                        Field f = var2[var4];
                        f.setAccessible(true);
                        Object x = f.get((Object)null);
                        if (k.isInstance(x)) {
                           return (Unsafe)k.cast(x);
                        }
                     }

                     throw new NoSuchFieldError("the Unsafe");
                  }
               });
            } catch (PrivilegedActionException var4) {
               throw new RuntimeException("Could not initialize intrinsics", var4.getCause());
            }
         }

         try {
            Class abstractFuture = AbstractFuture.class;
            WAITERS_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField("waiters"));
            LISTENERS_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField("listeners"));
            VALUE_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField("value"));
            WAITER_THREAD_OFFSET = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("thread"));
            WAITER_NEXT_OFFSET = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("next"));
            UNSAFE = unsafe;
         } catch (Exception var3) {
            Throwables.throwIfUnchecked(var3);
            throw new RuntimeException(var3);
         }
      }
   }

   private abstract static class AtomicHelper {
      private AtomicHelper() {
      }

      abstract void putThread(Waiter var1, Thread var2);

      abstract void putNext(Waiter var1, Waiter var2);

      abstract boolean casWaiters(AbstractFuture var1, Waiter var2, Waiter var3);

      abstract boolean casListeners(AbstractFuture var1, Listener var2, Listener var3);

      abstract boolean casValue(AbstractFuture var1, Object var2, Object var3);

      // $FF: synthetic method
      AtomicHelper(Object x0) {
         this();
      }
   }

   private static final class SetFuture implements Runnable {
      final AbstractFuture owner;
      final ListenableFuture future;

      SetFuture(AbstractFuture owner, ListenableFuture future) {
         this.owner = owner;
         this.future = future;
      }

      public void run() {
         if (this.owner.value == this) {
            Object valueToSet = AbstractFuture.getFutureValue(this.future);
            if (AbstractFuture.ATOMIC_HELPER.casValue(this.owner, this, valueToSet)) {
               AbstractFuture.complete(this.owner);
            }

         }
      }
   }

   private static final class Cancellation {
      final boolean wasInterrupted;
      @Nullable
      final Throwable cause;

      Cancellation(boolean wasInterrupted, @Nullable Throwable cause) {
         this.wasInterrupted = wasInterrupted;
         this.cause = cause;
      }
   }

   private static final class Failure {
      static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") {
         public synchronized Throwable fillInStackTrace() {
            return this;
         }
      });
      final Throwable exception;

      Failure(Throwable exception) {
         this.exception = (Throwable)Preconditions.checkNotNull(exception);
      }
   }

   private static final class Listener {
      static final Listener TOMBSTONE = new Listener((Runnable)null, (Executor)null);
      final Runnable task;
      final Executor executor;
      @Nullable
      Listener next;

      Listener(Runnable task, Executor executor) {
         this.task = task;
         this.executor = executor;
      }
   }

   private static final class Waiter {
      static final Waiter TOMBSTONE = new Waiter(false);
      @Nullable
      volatile Thread thread;
      @Nullable
      volatile Waiter next;

      Waiter(boolean unused) {
      }

      Waiter() {
         AbstractFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
      }

      void setNext(Waiter next) {
         AbstractFuture.ATOMIC_HELPER.putNext(this, next);
      }

      void unpark() {
         Thread w = this.thread;
         if (w != null) {
            this.thread = null;
            LockSupport.unpark(w);
         }

      }
   }

   abstract static class TrustedFuture extends AbstractFuture {
      @CanIgnoreReturnValue
      public final Object get() throws InterruptedException, ExecutionException {
         return super.get();
      }

      @CanIgnoreReturnValue
      public final Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
         return super.get(timeout, unit);
      }

      public final boolean isDone() {
         return super.isDone();
      }

      public final boolean isCancelled() {
         return super.isCancelled();
      }

      public final void addListener(Runnable listener, Executor executor) {
         super.addListener(listener, executor);
      }

      @CanIgnoreReturnValue
      public final boolean cancel(boolean mayInterruptIfRunning) {
         return super.cancel(mayInterruptIfRunning);
      }
   }
}
