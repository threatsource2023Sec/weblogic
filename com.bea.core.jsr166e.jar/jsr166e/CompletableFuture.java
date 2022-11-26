package jsr166e;

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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import sun.misc.Unsafe;

public class CompletableFuture implements Future {
   static final AltResult NIL = new AltResult((Throwable)null);
   volatile Object result;
   volatile WaitNode waiters;
   volatile CompletionNode completions;
   static final int NCPU = Runtime.getRuntime().availableProcessors();
   static final int SPINS;
   private static final Unsafe UNSAFE;
   private static final long RESULT;
   private static final long WAITERS;
   private static final long COMPLETIONS;

   final void postComplete() {
      WaitNode q;
      while((q = this.waiters) != null) {
         Thread t;
         if (UNSAFE.compareAndSwapObject(this, WAITERS, q, q.next) && (t = q.thread) != null) {
            q.thread = null;
            LockSupport.unpark(t);
         }
      }

      CompletionNode h;
      while((h = this.completions) != null) {
         Completion c;
         if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, h, h.next) && (c = h.completion) != null) {
            c.run();
         }
      }

   }

   final void internalComplete(Object v, Throwable ex) {
      if (this.result == null) {
         UNSAFE.compareAndSwapObject(this, RESULT, (Object)null, ex == null ? (v == null ? NIL : v) : new AltResult((Throwable)(ex instanceof CompletionException ? ex : new CompletionException(ex))));
      }

      this.postComplete();
   }

   final void helpPostComplete() {
      if (this.result != null) {
         this.postComplete();
      }

   }

   private Object waitingGet(boolean interruptible) {
      WaitNode q = null;
      boolean queued = false;
      int spins = SPINS;

      Object r;
      while((r = this.result) == null) {
         if (spins > 0) {
            int rnd = ThreadLocalRandom.current().nextInt();
            if (rnd >= 0) {
               --spins;
            }
         } else if (q == null) {
            q = new WaitNode(interruptible, 0L, 0L);
         } else if (!queued) {
            queued = UNSAFE.compareAndSwapObject(this, WAITERS, q.next = this.waiters, q);
         } else {
            if (interruptible && q.interruptControl < 0) {
               this.removeWaiter(q);
               return null;
            }

            if (q.thread != null && this.result == null) {
               try {
                  ForkJoinPool.managedBlock(q);
               } catch (InterruptedException var7) {
                  q.interruptControl = -1;
               }
            }
         }
      }

      if (q != null) {
         q.thread = null;
         if (q.interruptControl < 0) {
            if (interruptible) {
               this.removeWaiter(q);
               return null;
            }

            Thread.currentThread().interrupt();
         }
      }

      this.postComplete();
      return r;
   }

   private Object timedAwaitDone(long nanos) throws InterruptedException, TimeoutException {
      WaitNode q = null;
      boolean queued = false;

      Object r;
      while((r = this.result) == null) {
         if (q == null) {
            if (nanos <= 0L) {
               throw new TimeoutException();
            }

            long d = System.nanoTime() + nanos;
            q = new WaitNode(true, nanos, d == 0L ? 1L : d);
         } else if (!queued) {
            queued = UNSAFE.compareAndSwapObject(this, WAITERS, q.next = this.waiters, q);
         } else {
            if (q.interruptControl < 0) {
               this.removeWaiter(q);
               throw new InterruptedException();
            }

            if (q.nanos <= 0L) {
               if (this.result == null) {
                  this.removeWaiter(q);
                  throw new TimeoutException();
               }
            } else if (q.thread != null && this.result == null) {
               try {
                  ForkJoinPool.managedBlock(q);
               } catch (InterruptedException var8) {
                  q.interruptControl = -1;
               }
            }
         }
      }

      if (q != null) {
         q.thread = null;
         if (q.interruptControl < 0) {
            this.removeWaiter(q);
            throw new InterruptedException();
         }
      }

      this.postComplete();
      return r;
   }

   private void removeWaiter(WaitNode node) {
      if (node != null) {
         node.thread = null;

         label29:
         while(true) {
            WaitNode pred = null;

            WaitNode s;
            for(WaitNode q = this.waiters; q != null; q = s) {
               s = q.next;
               if (q.thread != null) {
                  pred = q;
               } else if (pred != null) {
                  pred.next = s;
                  if (pred.thread == null) {
                     continue label29;
                  }
               } else if (!UNSAFE.compareAndSwapObject(this, WAITERS, q, s)) {
                  continue label29;
               }
            }

            return;
         }
      }
   }

   public static CompletableFuture supplyAsync(Generator supplier) {
      if (supplier == null) {
         throw new NullPointerException();
      } else {
         CompletableFuture f = new CompletableFuture();
         ForkJoinPool.commonPool().execute((ForkJoinTask)(new AsyncSupply(supplier, f)));
         return f;
      }
   }

   public static CompletableFuture supplyAsync(Generator supplier, Executor executor) {
      if (executor != null && supplier != null) {
         CompletableFuture f = new CompletableFuture();
         executor.execute(new AsyncSupply(supplier, f));
         return f;
      } else {
         throw new NullPointerException();
      }
   }

   public static CompletableFuture runAsync(Runnable runnable) {
      if (runnable == null) {
         throw new NullPointerException();
      } else {
         CompletableFuture f = new CompletableFuture();
         ForkJoinPool.commonPool().execute((ForkJoinTask)(new AsyncRun(runnable, f)));
         return f;
      }
   }

   public static CompletableFuture runAsync(Runnable runnable, Executor executor) {
      if (executor != null && runnable != null) {
         CompletableFuture f = new CompletableFuture();
         executor.execute(new AsyncRun(runnable, f));
         return f;
      } else {
         throw new NullPointerException();
      }
   }

   public static CompletableFuture completedFuture(Object value) {
      CompletableFuture f = new CompletableFuture();
      f.result = value == null ? NIL : value;
      return f;
   }

   public boolean isDone() {
      return this.result != null;
   }

   public Object get() throws InterruptedException, ExecutionException {
      Object r;
      if ((r = this.result) == null && (r = this.waitingGet(true)) == null) {
         throw new InterruptedException();
      } else if (!(r instanceof AltResult)) {
         return r;
      } else {
         Throwable ex;
         if ((ex = ((AltResult)r).ex) == null) {
            return null;
         } else if (ex instanceof CancellationException) {
            throw (CancellationException)ex;
         } else {
            Throwable cause;
            if (ex instanceof CompletionException && (cause = ex.getCause()) != null) {
               ex = cause;
            }

            throw new ExecutionException(ex);
         }
      }
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      long nanos = unit.toNanos(timeout);
      if (Thread.interrupted()) {
         throw new InterruptedException();
      } else {
         Object r;
         if ((r = this.result) == null) {
            r = this.timedAwaitDone(nanos);
         }

         if (!(r instanceof AltResult)) {
            return r;
         } else {
            Throwable ex;
            if ((ex = ((AltResult)r).ex) == null) {
               return null;
            } else if (ex instanceof CancellationException) {
               throw (CancellationException)ex;
            } else {
               Throwable cause;
               if (ex instanceof CompletionException && (cause = ex.getCause()) != null) {
                  ex = cause;
               }

               throw new ExecutionException(ex);
            }
         }
      }
   }

   public Object join() {
      Object r;
      if ((r = this.result) == null) {
         r = this.waitingGet(false);
      }

      if (!(r instanceof AltResult)) {
         return r;
      } else {
         Throwable ex;
         if ((ex = ((AltResult)r).ex) == null) {
            return null;
         } else if (ex instanceof CancellationException) {
            throw (CancellationException)ex;
         } else if (ex instanceof CompletionException) {
            throw (CompletionException)ex;
         } else {
            throw new CompletionException(ex);
         }
      }
   }

   public Object getNow(Object valueIfAbsent) {
      Object r;
      if ((r = this.result) == null) {
         return valueIfAbsent;
      } else if (!(r instanceof AltResult)) {
         return r;
      } else {
         Throwable ex;
         if ((ex = ((AltResult)r).ex) == null) {
            return null;
         } else if (ex instanceof CancellationException) {
            throw (CancellationException)ex;
         } else if (ex instanceof CompletionException) {
            throw (CompletionException)ex;
         } else {
            throw new CompletionException(ex);
         }
      }
   }

   public boolean complete(Object value) {
      boolean triggered = this.result == null && UNSAFE.compareAndSwapObject(this, RESULT, (Object)null, value == null ? NIL : value);
      this.postComplete();
      return triggered;
   }

   public boolean completeExceptionally(Throwable ex) {
      if (ex == null) {
         throw new NullPointerException();
      } else {
         boolean triggered = this.result == null && UNSAFE.compareAndSwapObject(this, RESULT, (Object)null, new AltResult(ex));
         this.postComplete();
         return triggered;
      }
   }

   public CompletableFuture thenApply(Fun fn) {
      return this.doThenApply(fn, (Executor)null);
   }

   public CompletableFuture thenApplyAsync(Fun fn) {
      return this.doThenApply(fn, ForkJoinPool.commonPool());
   }

   public CompletableFuture thenApplyAsync(Fun fn, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doThenApply(fn, executor);
      }
   }

   private CompletableFuture doThenApply(Fun fn, Executor e) {
      if (fn == null) {
         throw new NullPointerException();
      } else {
         CompletableFuture dst = new CompletableFuture();
         ThenApply d = null;
         Object r;
         if ((r = this.result) == null) {
            CompletionNode p = new CompletionNode(d = new ThenApply(this, fn, dst, e));

            while((r = this.result) == null && !UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Throwable ex;
            Object t;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Object u = null;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncApply(t, fn, dst));
                  } else {
                     u = fn.apply(t);
                  }
               } catch (Throwable var10) {
                  ex = var10;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete(u, ex);
            }
         }

         this.helpPostComplete();
         return dst;
      }
   }

   public CompletableFuture thenAccept(Action block) {
      return this.doThenAccept(block, (Executor)null);
   }

   public CompletableFuture thenAcceptAsync(Action block) {
      return this.doThenAccept(block, ForkJoinPool.commonPool());
   }

   public CompletableFuture thenAcceptAsync(Action block, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doThenAccept(block, executor);
      }
   }

   private CompletableFuture doThenAccept(Action fn, Executor e) {
      if (fn == null) {
         throw new NullPointerException();
      } else {
         CompletableFuture dst = new CompletableFuture();
         ThenAccept d = null;
         Object r;
         if ((r = this.result) == null) {
            CompletionNode p = new CompletionNode(d = new ThenAccept(this, fn, dst, e));

            while((r = this.result) == null && !UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Throwable ex;
            Object t;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncAccept(t, fn, dst));
                  } else {
                     fn.accept(t);
                  }
               } catch (Throwable var9) {
                  ex = var9;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

         this.helpPostComplete();
         return dst;
      }
   }

   public CompletableFuture thenRun(Runnable action) {
      return this.doThenRun(action, (Executor)null);
   }

   public CompletableFuture thenRunAsync(Runnable action) {
      return this.doThenRun(action, ForkJoinPool.commonPool());
   }

   public CompletableFuture thenRunAsync(Runnable action, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doThenRun(action, executor);
      }
   }

   private CompletableFuture doThenRun(Runnable action, Executor e) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         CompletableFuture dst = new CompletableFuture();
         ThenRun d = null;
         Object r;
         if ((r = this.result) == null) {
            CompletionNode p = new CompletionNode(d = new ThenRun(this, action, dst, e));

            while((r = this.result) == null && !UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
            } else {
               ex = null;
            }

            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncRun(action, dst));
                  } else {
                     action.run();
                  }
               } catch (Throwable var8) {
                  ex = var8;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

         this.helpPostComplete();
         return dst;
      }
   }

   public CompletableFuture thenCombine(CompletableFuture other, BiFun fn) {
      return this.doThenCombine(other, fn, (Executor)null);
   }

   public CompletableFuture thenCombineAsync(CompletableFuture other, BiFun fn) {
      return this.doThenCombine(other, fn, ForkJoinPool.commonPool());
   }

   public CompletableFuture thenCombineAsync(CompletableFuture other, BiFun fn, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doThenCombine(other, fn, executor);
      }
   }

   private CompletableFuture doThenCombine(CompletableFuture other, BiFun fn, Executor e) {
      if (other != null && fn != null) {
         CompletableFuture dst = new CompletableFuture();
         ThenCombine d = null;
         Object s = null;
         Object r;
         if ((r = this.result) == null || (s = other.result) == null) {
            d = new ThenCombine(this, other, fn, dst, e);
            CompletionNode q = null;
            CompletionNode p = new CompletionNode(d);

            label89:
            do {
               while(true) {
                  if ((r != null || (r = this.result) != null) && (s != null || (s = other.result) != null)) {
                     break label89;
                  }

                  if (q != null) {
                     break;
                  }

                  if (r != null || UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
                     if (s != null) {
                        break label89;
                     }

                     q = new CompletionNode(d);
                  }
               }
            } while(s == null && !UNSAFE.compareAndSwapObject(other, COMPLETIONS, q.next = other.completions, q));
         }

         if (r != null && s != null && (d == null || d.compareAndSet(0, 1))) {
            Throwable ex;
            Object t;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Object u;
            if (ex != null) {
               u = null;
            } else if (s instanceof AltResult) {
               ex = ((AltResult)s).ex;
               u = null;
            } else {
               u = s;
            }

            Object v = null;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncCombine(t, u, fn, dst));
                  } else {
                     v = fn.apply(t, u);
                  }
               } catch (Throwable var13) {
                  ex = var13;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete(v, ex);
            }
         }

         this.helpPostComplete();
         other.helpPostComplete();
         return dst;
      } else {
         throw new NullPointerException();
      }
   }

   public CompletableFuture thenAcceptBoth(CompletableFuture other, BiAction block) {
      return this.doThenAcceptBoth(other, block, (Executor)null);
   }

   public CompletableFuture thenAcceptBothAsync(CompletableFuture other, BiAction block) {
      return this.doThenAcceptBoth(other, block, ForkJoinPool.commonPool());
   }

   public CompletableFuture thenAcceptBothAsync(CompletableFuture other, BiAction block, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doThenAcceptBoth(other, block, executor);
      }
   }

   private CompletableFuture doThenAcceptBoth(CompletableFuture other, BiAction fn, Executor e) {
      if (other != null && fn != null) {
         CompletableFuture dst = new CompletableFuture();
         ThenAcceptBoth d = null;
         Object s = null;
         Object r;
         if ((r = this.result) == null || (s = other.result) == null) {
            d = new ThenAcceptBoth(this, other, fn, dst, e);
            CompletionNode q = null;
            CompletionNode p = new CompletionNode(d);

            label89:
            do {
               while(true) {
                  if ((r != null || (r = this.result) != null) && (s != null || (s = other.result) != null)) {
                     break label89;
                  }

                  if (q != null) {
                     break;
                  }

                  if (r != null || UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
                     if (s != null) {
                        break label89;
                     }

                     q = new CompletionNode(d);
                  }
               }
            } while(s == null && !UNSAFE.compareAndSwapObject(other, COMPLETIONS, q.next = other.completions, q));
         }

         if (r != null && s != null && (d == null || d.compareAndSet(0, 1))) {
            Throwable ex;
            Object t;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Object u;
            if (ex != null) {
               u = null;
            } else if (s instanceof AltResult) {
               ex = ((AltResult)s).ex;
               u = null;
            } else {
               u = s;
            }

            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncAcceptBoth(t, u, fn, dst));
                  } else {
                     fn.accept(t, u);
                  }
               } catch (Throwable var12) {
                  ex = var12;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

         this.helpPostComplete();
         other.helpPostComplete();
         return dst;
      } else {
         throw new NullPointerException();
      }
   }

   public CompletableFuture runAfterBoth(CompletableFuture other, Runnable action) {
      return this.doRunAfterBoth(other, action, (Executor)null);
   }

   public CompletableFuture runAfterBothAsync(CompletableFuture other, Runnable action) {
      return this.doRunAfterBoth(other, action, ForkJoinPool.commonPool());
   }

   public CompletableFuture runAfterBothAsync(CompletableFuture other, Runnable action, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doRunAfterBoth(other, action, executor);
      }
   }

   private CompletableFuture doRunAfterBoth(CompletableFuture other, Runnable action, Executor e) {
      if (other != null && action != null) {
         CompletableFuture dst = new CompletableFuture();
         RunAfterBoth d = null;
         Object s = null;
         Object r;
         if ((r = this.result) == null || (s = other.result) == null) {
            d = new RunAfterBoth(this, other, action, dst, e);
            CompletionNode q = null;
            CompletionNode p = new CompletionNode(d);

            label87:
            do {
               while(true) {
                  if ((r != null || (r = this.result) != null) && (s != null || (s = other.result) != null)) {
                     break label87;
                  }

                  if (q != null) {
                     break;
                  }

                  if (r != null || UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
                     if (s != null) {
                        break label87;
                     }

                     q = new CompletionNode(d);
                  }
               }
            } while(s == null && !UNSAFE.compareAndSwapObject(other, COMPLETIONS, q.next = other.completions, q));
         }

         if (r != null && s != null && (d == null || d.compareAndSet(0, 1))) {
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
            } else {
               ex = null;
            }

            if (ex == null && s instanceof AltResult) {
               ex = ((AltResult)s).ex;
            }

            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncRun(action, dst));
                  } else {
                     action.run();
                  }
               } catch (Throwable var10) {
                  ex = var10;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

         this.helpPostComplete();
         other.helpPostComplete();
         return dst;
      } else {
         throw new NullPointerException();
      }
   }

   public CompletableFuture applyToEither(CompletableFuture other, Fun fn) {
      return this.doApplyToEither(other, fn, (Executor)null);
   }

   public CompletableFuture applyToEitherAsync(CompletableFuture other, Fun fn) {
      return this.doApplyToEither(other, fn, ForkJoinPool.commonPool());
   }

   public CompletableFuture applyToEitherAsync(CompletableFuture other, Fun fn, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doApplyToEither(other, fn, executor);
      }
   }

   private CompletableFuture doApplyToEither(CompletableFuture other, Fun fn, Executor e) {
      if (other != null && fn != null) {
         CompletableFuture dst = new CompletableFuture();
         ApplyToEither d = null;
         Object r;
         if ((r = this.result) == null && (r = other.result) == null) {
            d = new ApplyToEither(this, other, fn, dst, e);
            CompletionNode q = null;
            CompletionNode p = new CompletionNode(d);

            while((r = this.result) == null && (r = other.result) == null) {
               if (q != null) {
                  if (UNSAFE.compareAndSwapObject(other, COMPLETIONS, q.next = other.completions, q)) {
                     break;
                  }
               } else if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
                  q = new CompletionNode(d);
               }
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Object u = null;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncApply(t, fn, dst));
                  } else {
                     u = fn.apply(t);
                  }
               } catch (Throwable var11) {
                  ex = var11;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete(u, ex);
            }
         }

         this.helpPostComplete();
         other.helpPostComplete();
         return dst;
      } else {
         throw new NullPointerException();
      }
   }

   public CompletableFuture acceptEither(CompletableFuture other, Action block) {
      return this.doAcceptEither(other, block, (Executor)null);
   }

   public CompletableFuture acceptEitherAsync(CompletableFuture other, Action block) {
      return this.doAcceptEither(other, block, ForkJoinPool.commonPool());
   }

   public CompletableFuture acceptEitherAsync(CompletableFuture other, Action block, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doAcceptEither(other, block, executor);
      }
   }

   private CompletableFuture doAcceptEither(CompletableFuture other, Action fn, Executor e) {
      if (other != null && fn != null) {
         CompletableFuture dst = new CompletableFuture();
         AcceptEither d = null;
         Object r;
         if ((r = this.result) == null && (r = other.result) == null) {
            d = new AcceptEither(this, other, fn, dst, e);
            CompletionNode q = null;
            CompletionNode p = new CompletionNode(d);

            while((r = this.result) == null && (r = other.result) == null) {
               if (q != null) {
                  if (UNSAFE.compareAndSwapObject(other, COMPLETIONS, q.next = other.completions, q)) {
                     break;
                  }
               } else if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
                  q = new CompletionNode(d);
               }
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncAccept(t, fn, dst));
                  } else {
                     fn.accept(t);
                  }
               } catch (Throwable var10) {
                  ex = var10;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

         this.helpPostComplete();
         other.helpPostComplete();
         return dst;
      } else {
         throw new NullPointerException();
      }
   }

   public CompletableFuture runAfterEither(CompletableFuture other, Runnable action) {
      return this.doRunAfterEither(other, action, (Executor)null);
   }

   public CompletableFuture runAfterEitherAsync(CompletableFuture other, Runnable action) {
      return this.doRunAfterEither(other, action, ForkJoinPool.commonPool());
   }

   public CompletableFuture runAfterEitherAsync(CompletableFuture other, Runnable action, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doRunAfterEither(other, action, executor);
      }
   }

   private CompletableFuture doRunAfterEither(CompletableFuture other, Runnable action, Executor e) {
      if (other != null && action != null) {
         CompletableFuture dst = new CompletableFuture();
         RunAfterEither d = null;
         Object r;
         if ((r = this.result) == null && (r = other.result) == null) {
            d = new RunAfterEither(this, other, action, dst, e);
            CompletionNode q = null;
            CompletionNode p = new CompletionNode(d);

            while((r = this.result) == null && (r = other.result) == null) {
               if (q != null) {
                  if (UNSAFE.compareAndSwapObject(other, COMPLETIONS, q.next = other.completions, q)) {
                     break;
                  }
               } else if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
                  q = new CompletionNode(d);
               }
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
            } else {
               ex = null;
            }

            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncRun(action, dst));
                  } else {
                     action.run();
                  }
               } catch (Throwable var9) {
                  ex = var9;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

         this.helpPostComplete();
         other.helpPostComplete();
         return dst;
      } else {
         throw new NullPointerException();
      }
   }

   public CompletableFuture thenCompose(Fun fn) {
      return this.doThenCompose(fn, (Executor)null);
   }

   public CompletableFuture thenComposeAsync(Fun fn) {
      return this.doThenCompose(fn, ForkJoinPool.commonPool());
   }

   public CompletableFuture thenComposeAsync(Fun fn, Executor executor) {
      if (executor == null) {
         throw new NullPointerException();
      } else {
         return this.doThenCompose(fn, executor);
      }
   }

   private CompletableFuture doThenCompose(Fun fn, Executor e) {
      if (fn == null) {
         throw new NullPointerException();
      } else {
         CompletableFuture dst = null;
         ThenCompose d = null;
         Object r;
         if ((r = this.result) == null) {
            dst = new CompletableFuture();
            CompletionNode p = new CompletionNode(d = new ThenCompose(this, fn, dst, e));

            while((r = this.result) == null && !UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Object ex;
            Object t;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            if (ex == null) {
               if (e != null) {
                  if (dst == null) {
                     dst = new CompletableFuture();
                  }

                  e.execute(new AsyncCompose(t, fn, dst));
               } else {
                  try {
                     if ((dst = (CompletableFuture)fn.apply(t)) == null) {
                        ex = new NullPointerException();
                     }
                  } catch (Throwable var9) {
                     ex = var9;
                  }
               }
            }

            if (dst == null) {
               dst = new CompletableFuture();
            }

            if (ex != null) {
               dst.internalComplete((Object)null, (Throwable)ex);
            }
         }

         this.helpPostComplete();
         dst.helpPostComplete();
         return dst;
      }
   }

   public CompletableFuture exceptionally(Fun fn) {
      if (fn == null) {
         throw new NullPointerException();
      } else {
         CompletableFuture dst = new CompletableFuture();
         ExceptionCompletion d = null;
         Object r;
         if ((r = this.result) == null) {
            CompletionNode p = new CompletionNode(d = new ExceptionCompletion(this, fn, dst));

            while((r = this.result) == null && !UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Object t = null;
            Throwable dx = null;
            if (r instanceof AltResult) {
               Throwable ex;
               if ((ex = ((AltResult)r).ex) != null) {
                  try {
                     t = fn.apply(ex);
                  } catch (Throwable var9) {
                     dx = var9;
                  }
               }
            } else {
               t = r;
            }

            dst.internalComplete(t, dx);
         }

         this.helpPostComplete();
         return dst;
      }
   }

   public CompletableFuture handle(BiFun fn) {
      if (fn == null) {
         throw new NullPointerException();
      } else {
         CompletableFuture dst = new CompletableFuture();
         HandleCompletion d = null;
         Object r;
         if ((r = this.result) == null) {
            CompletionNode p = new CompletionNode(d = new HandleCompletion(this, fn, dst));

            while((r = this.result) == null && !UNSAFE.compareAndSwapObject(this, COMPLETIONS, p.next = this.completions, p)) {
            }
         }

         if (r != null && (d == null || d.compareAndSet(0, 1))) {
            Throwable ex;
            Object t;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Object u;
            Throwable dx;
            try {
               u = fn.apply(t, ex);
               dx = null;
            } catch (Throwable var10) {
               dx = var10;
               u = null;
            }

            dst.internalComplete(u, dx);
         }

         this.helpPostComplete();
         return dst;
      }
   }

   public static CompletableFuture allOf(CompletableFuture... cfs) {
      int len = cfs.length;
      if (len > 1) {
         return allTree(cfs, 0, len - 1);
      } else {
         CompletableFuture dst = new CompletableFuture();
         if (len == 0) {
            dst.result = NIL;
         } else {
            CompletableFuture f;
            if ((f = cfs[0]) == null) {
               throw new NullPointerException();
            }

            ThenPropagate d = null;
            CompletionNode p = null;

            Object r;
            while((r = f.result) == null) {
               if (d == null) {
                  d = new ThenPropagate(f, dst);
               } else if (p == null) {
                  p = new CompletionNode(d);
               } else if (UNSAFE.compareAndSwapObject(f, COMPLETIONS, p.next = f.completions, p)) {
                  break;
               }
            }

            if (r != null && (d == null || d.compareAndSet(0, 1))) {
               dst.internalComplete((Object)null, r instanceof AltResult ? ((AltResult)r).ex : null);
            }

            f.helpPostComplete();
         }

         return dst;
      }
   }

   private static CompletableFuture allTree(CompletableFuture[] cfs, int lo, int hi) {
      int mid = lo + hi >>> 1;
      CompletableFuture var10000 = lo == mid ? cfs[lo] : allTree(cfs, lo, mid);
      CompletableFuture fst = var10000;
      if (var10000 != null) {
         var10000 = hi == mid + 1 ? cfs[hi] : allTree(cfs, mid + 1, hi);
         CompletableFuture snd = var10000;
         if (var10000 != null) {
            CompletableFuture dst = new CompletableFuture();
            AndCompletion d = null;
            CompletionNode p = null;
            CompletionNode q = null;
            Object r = null;
            Object s = null;

            while((r = fst.result) == null || (s = snd.result) == null) {
               if (d == null) {
                  d = new AndCompletion(fst, snd, dst);
               } else if (p == null) {
                  p = new CompletionNode(d);
               } else if (q == null) {
                  if (UNSAFE.compareAndSwapObject(fst, COMPLETIONS, p.next = fst.completions, p)) {
                     q = new CompletionNode(d);
                  }
               } else if (UNSAFE.compareAndSwapObject(snd, COMPLETIONS, q.next = snd.completions, q)) {
                  break;
               }
            }

            if ((r != null || (r = fst.result) != null) && (s != null || (s = snd.result) != null) && (d == null || d.compareAndSet(0, 1))) {
               Throwable ex;
               if (r instanceof AltResult) {
                  ex = ((AltResult)r).ex;
               } else {
                  ex = null;
               }

               if (ex == null && s instanceof AltResult) {
                  ex = ((AltResult)s).ex;
               }

               dst.internalComplete((Object)null, ex);
            }

            fst.helpPostComplete();
            snd.helpPostComplete();
            return dst;
         }
      }

      throw new NullPointerException();
   }

   public static CompletableFuture anyOf(CompletableFuture... cfs) {
      int len = cfs.length;
      if (len > 1) {
         return anyTree(cfs, 0, len - 1);
      } else {
         CompletableFuture dst = new CompletableFuture();
         if (len != 0) {
            CompletableFuture f;
            if ((f = cfs[0]) == null) {
               throw new NullPointerException();
            }

            ThenCopy d = null;
            CompletionNode p = null;

            Object r;
            while((r = f.result) == null) {
               if (d == null) {
                  d = new ThenCopy(f, dst);
               } else if (p == null) {
                  p = new CompletionNode(d);
               } else if (UNSAFE.compareAndSwapObject(f, COMPLETIONS, p.next = f.completions, p)) {
                  break;
               }
            }

            if (r != null && (d == null || d.compareAndSet(0, 1))) {
               Throwable ex;
               Object t;
               if (r instanceof AltResult) {
                  ex = ((AltResult)r).ex;
                  t = null;
               } else {
                  ex = null;
                  t = r;
               }

               dst.internalComplete(t, ex);
            }

            f.helpPostComplete();
         }

         return dst;
      }
   }

   private static CompletableFuture anyTree(CompletableFuture[] cfs, int lo, int hi) {
      int mid = lo + hi >>> 1;
      CompletableFuture var10000 = lo == mid ? cfs[lo] : anyTree(cfs, lo, mid);
      CompletableFuture fst = var10000;
      if (var10000 != null) {
         var10000 = hi == mid + 1 ? cfs[hi] : anyTree(cfs, mid + 1, hi);
         CompletableFuture snd = var10000;
         if (var10000 != null) {
            CompletableFuture dst = new CompletableFuture();
            OrCompletion d = null;
            CompletionNode p = null;
            CompletionNode q = null;

            Object r;
            while((r = fst.result) == null && (r = snd.result) == null) {
               if (d == null) {
                  d = new OrCompletion(fst, snd, dst);
               } else if (p == null) {
                  p = new CompletionNode(d);
               } else if (q == null) {
                  if (UNSAFE.compareAndSwapObject(fst, COMPLETIONS, p.next = fst.completions, p)) {
                     q = new CompletionNode(d);
                  }
               } else if (UNSAFE.compareAndSwapObject(snd, COMPLETIONS, q.next = snd.completions, q)) {
                  break;
               }
            }

            if ((r != null || (r = fst.result) != null || (r = snd.result) != null) && (d == null || d.compareAndSet(0, 1))) {
               Throwable ex;
               Object t;
               if (r instanceof AltResult) {
                  ex = ((AltResult)r).ex;
                  t = null;
               } else {
                  ex = null;
                  t = r;
               }

               dst.internalComplete(t, ex);
            }

            fst.helpPostComplete();
            snd.helpPostComplete();
            return dst;
         }
      }

      throw new NullPointerException();
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      boolean cancelled = this.result == null && UNSAFE.compareAndSwapObject(this, RESULT, (Object)null, new AltResult(new CancellationException()));
      this.postComplete();
      return cancelled || this.isCancelled();
   }

   public boolean isCancelled() {
      Object r;
      return (r = this.result) instanceof AltResult && ((AltResult)r).ex instanceof CancellationException;
   }

   public void obtrudeValue(Object value) {
      this.result = value == null ? NIL : value;
      this.postComplete();
   }

   public void obtrudeException(Throwable ex) {
      if (ex == null) {
         throw new NullPointerException();
      } else {
         this.result = new AltResult(ex);
         this.postComplete();
      }
   }

   public int getNumberOfDependents() {
      int count = 0;

      for(CompletionNode p = this.completions; p != null; p = p.next) {
         ++count;
      }

      return count;
   }

   public String toString() {
      Object r = this.result;
      int count;
      return super.toString() + (r == null ? ((count = this.getNumberOfDependents()) == 0 ? "[Not completed]" : "[Not completed, " + count + " dependents]") : (r instanceof AltResult && ((AltResult)r).ex != null ? "[Completed exceptionally]" : "[Completed normally]"));
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            return (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Unsafe run() throws Exception {
                  Class k = Unsafe.class;
                  Field[] arr$ = k.getDeclaredFields();
                  int len$ = arr$.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     Field f = arr$[i$];
                     f.setAccessible(true);
                     Object x = f.get((Object)null);
                     if (k.isInstance(x)) {
                        return (Unsafe)k.cast(x);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
         }
      }
   }

   static {
      SPINS = NCPU > 1 ? 256 : 0;

      try {
         UNSAFE = getUnsafe();
         Class k = CompletableFuture.class;
         RESULT = UNSAFE.objectFieldOffset(k.getDeclaredField("result"));
         WAITERS = UNSAFE.objectFieldOffset(k.getDeclaredField("waiters"));
         COMPLETIONS = UNSAFE.objectFieldOffset(k.getDeclaredField("completions"));
      } catch (Exception var1) {
         throw new Error(var1);
      }
   }

   static final class ThenCompose extends Completion {
      final CompletableFuture src;
      final Fun fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      ThenCompose(CompletableFuture src, Fun fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         Fun fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && this.compareAndSet(0, 1)) {
            Object t;
            Object ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            CompletableFuture c = null;
            Object u = null;
            boolean complete = false;
            if (ex == null) {
               Executor e;
               if ((e = this.executor) != null) {
                  e.execute(new AsyncCompose(t, fn, dst));
               } else {
                  try {
                     if ((c = (CompletableFuture)fn.apply(t)) == null) {
                        ex = new NullPointerException();
                     }
                  } catch (Throwable var14) {
                     ex = var14;
                  }
               }
            }

            if (c != null) {
               ThenCopy d = null;
               Object s;
               if ((s = c.result) == null) {
                  CompletionNode p = new CompletionNode(d = new ThenCopy(c, dst));

                  while((s = c.result) == null && !CompletableFuture.UNSAFE.compareAndSwapObject(c, CompletableFuture.COMPLETIONS, p.next = c.completions, p)) {
                  }
               }

               if (s != null && (d == null || d.compareAndSet(0, 1))) {
                  complete = true;
                  if (s instanceof AltResult) {
                     ex = ((AltResult)s).ex;
                     u = null;
                  } else {
                     u = s;
                  }
               }
            }

            if (complete || ex != null) {
               dst.internalComplete(u, (Throwable)ex);
            }

            if (c != null) {
               c.helpPostComplete();
            }
         }

      }
   }

   static final class HandleCompletion extends Completion {
      final CompletableFuture src;
      final BiFun fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      HandleCompletion(CompletableFuture src, BiFun fn, CompletableFuture dst) {
         this.src = src;
         this.fn = fn;
         this.dst = dst;
      }

      public final void run() {
         CompletableFuture a;
         BiFun fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Object u = null;
            Throwable dx = null;

            try {
               u = fn.apply(t, ex);
            } catch (Throwable var10) {
               dx = var10;
            }

            dst.internalComplete(u, dx);
         }

      }
   }

   static final class ThenPropagate extends Completion {
      final CompletableFuture src;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      ThenPropagate(CompletableFuture src, CompletableFuture dst) {
         this.src = src;
         this.dst = dst;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (a = this.src) != null && (r = a.result) != null && this.compareAndSet(0, 1)) {
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
            } else {
               ex = null;
            }

            dst.internalComplete((Object)null, ex);
         }

      }
   }

   static final class ThenCopy extends Completion {
      final CompletableFuture src;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      ThenCopy(CompletableFuture src, CompletableFuture dst) {
         this.src = src;
         this.dst = dst;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (a = this.src) != null && (r = a.result) != null && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            dst.internalComplete(t, ex);
         }

      }
   }

   static final class ExceptionCompletion extends Completion {
      final CompletableFuture src;
      final Fun fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      ExceptionCompletion(CompletableFuture src, Fun fn, CompletableFuture dst) {
         this.src = src;
         this.fn = fn;
         this.dst = dst;
      }

      public final void run() {
         Object t = null;
         Throwable dx = null;
         CompletableFuture a;
         Fun fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && this.compareAndSet(0, 1)) {
            Throwable ex;
            if (r instanceof AltResult && (ex = ((AltResult)r).ex) != null) {
               try {
                  t = fn.apply(ex);
               } catch (Throwable var9) {
                  dx = var9;
               }
            } else {
               t = r;
            }

            dst.internalComplete(t, dx);
         }

      }
   }

   static final class OrCompletion extends Completion {
      final CompletableFuture src;
      final CompletableFuture snd;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      OrCompletion(CompletableFuture src, CompletableFuture snd, CompletableFuture dst) {
         this.src = src;
         this.snd = snd;
         this.dst = dst;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture b;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && ((a = this.src) != null && (r = a.result) != null || (b = this.snd) != null && (r = b.result) != null) && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            dst.internalComplete(t, ex);
         }

      }
   }

   static final class RunAfterEither extends Completion {
      final CompletableFuture src;
      final CompletableFuture snd;
      final Runnable fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      RunAfterEither(CompletableFuture src, CompletableFuture snd, Runnable fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.snd = snd;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture b;
         Runnable fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && ((a = this.src) != null && (r = a.result) != null || (b = this.snd) != null && (r = b.result) != null) && this.compareAndSet(0, 1)) {
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
            } else {
               ex = null;
            }

            Executor e = this.executor;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncRun(fn, dst));
                  } else {
                     fn.run();
                  }
               } catch (Throwable var9) {
                  ex = var9;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

      }
   }

   static final class AcceptEither extends Completion {
      final CompletableFuture src;
      final CompletableFuture snd;
      final Action fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      AcceptEither(CompletableFuture src, CompletableFuture snd, Action fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.snd = snd;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture b;
         Action fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && ((a = this.src) != null && (r = a.result) != null || (b = this.snd) != null && (r = b.result) != null) && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Executor e = this.executor;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncAccept(t, fn, dst));
                  } else {
                     fn.accept(t);
                  }
               } catch (Throwable var10) {
                  ex = var10;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

      }
   }

   static final class ApplyToEither extends Completion {
      final CompletableFuture src;
      final CompletableFuture snd;
      final Fun fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      ApplyToEither(CompletableFuture src, CompletableFuture snd, Fun fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.snd = snd;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture b;
         Fun fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && ((a = this.src) != null && (r = a.result) != null || (b = this.snd) != null && (r = b.result) != null) && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Executor e = this.executor;
            Object u = null;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncApply(t, fn, dst));
                  } else {
                     u = fn.apply(t);
                  }
               } catch (Throwable var11) {
                  ex = var11;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete(u, ex);
            }
         }

      }
   }

   static final class AndCompletion extends Completion {
      final CompletableFuture src;
      final CompletableFuture snd;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      AndCompletion(CompletableFuture src, CompletableFuture snd, CompletableFuture dst) {
         this.src = src;
         this.snd = snd;
         this.dst = dst;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture b;
         CompletableFuture dst;
         Object r;
         Object s;
         if ((dst = this.dst) != null && (a = this.src) != null && (r = a.result) != null && (b = this.snd) != null && (s = b.result) != null && this.compareAndSet(0, 1)) {
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
            } else {
               ex = null;
            }

            if (ex == null && s instanceof AltResult) {
               ex = ((AltResult)s).ex;
            }

            dst.internalComplete((Object)null, ex);
         }

      }
   }

   static final class RunAfterBoth extends Completion {
      final CompletableFuture src;
      final CompletableFuture snd;
      final Runnable fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      RunAfterBoth(CompletableFuture src, CompletableFuture snd, Runnable fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.snd = snd;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture b;
         Runnable fn;
         CompletableFuture dst;
         Object r;
         Object s;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && (b = this.snd) != null && (s = b.result) != null && this.compareAndSet(0, 1)) {
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
            } else {
               ex = null;
            }

            if (ex == null && s instanceof AltResult) {
               ex = ((AltResult)s).ex;
            }

            Executor e = this.executor;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncRun(fn, dst));
                  } else {
                     fn.run();
                  }
               } catch (Throwable var10) {
                  ex = var10;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

      }
   }

   static final class ThenAcceptBoth extends Completion {
      final CompletableFuture src;
      final CompletableFuture snd;
      final BiAction fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      ThenAcceptBoth(CompletableFuture src, CompletableFuture snd, BiAction fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.snd = snd;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture b;
         BiAction fn;
         CompletableFuture dst;
         Object r;
         Object s;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && (b = this.snd) != null && (s = b.result) != null && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Object u;
            if (ex != null) {
               u = null;
            } else if (s instanceof AltResult) {
               ex = ((AltResult)s).ex;
               u = null;
            } else {
               u = s;
            }

            Executor e = this.executor;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncAcceptBoth(t, u, fn, dst));
                  } else {
                     fn.accept(t, u);
                  }
               } catch (Throwable var12) {
                  ex = var12;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

      }
   }

   static final class ThenCombine extends Completion {
      final CompletableFuture src;
      final CompletableFuture snd;
      final BiFun fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      ThenCombine(CompletableFuture src, CompletableFuture snd, BiFun fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.snd = snd;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         CompletableFuture b;
         BiFun fn;
         CompletableFuture dst;
         Object r;
         Object s;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && (b = this.snd) != null && (s = b.result) != null && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Object u;
            if (ex != null) {
               u = null;
            } else if (s instanceof AltResult) {
               ex = ((AltResult)s).ex;
               u = null;
            } else {
               u = s;
            }

            Executor e = this.executor;
            Object v = null;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncCombine(t, u, fn, dst));
                  } else {
                     v = fn.apply(t, u);
                  }
               } catch (Throwable var13) {
                  ex = var13;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete(v, ex);
            }
         }

      }
   }

   static final class ThenRun extends Completion {
      final CompletableFuture src;
      final Runnable fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      ThenRun(CompletableFuture src, Runnable fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         Runnable fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && this.compareAndSet(0, 1)) {
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
            } else {
               ex = null;
            }

            Executor e = this.executor;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncRun(fn, dst));
                  } else {
                     fn.run();
                  }
               } catch (Throwable var8) {
                  ex = var8;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

      }
   }

   static final class ThenAccept extends Completion {
      final CompletableFuture src;
      final Action fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      ThenAccept(CompletableFuture src, Action fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         Action fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Executor e = this.executor;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncAccept(t, fn, dst));
                  } else {
                     fn.accept(t);
                  }
               } catch (Throwable var9) {
                  ex = var9;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete((Object)null, ex);
            }
         }

      }
   }

   static final class ThenApply extends Completion {
      final CompletableFuture src;
      final Fun fn;
      final CompletableFuture dst;
      final Executor executor;
      private static final long serialVersionUID = 5232453952276885070L;

      ThenApply(CompletableFuture src, Fun fn, CompletableFuture dst, Executor executor) {
         this.src = src;
         this.fn = fn;
         this.dst = dst;
         this.executor = executor;
      }

      public final void run() {
         CompletableFuture a;
         Fun fn;
         CompletableFuture dst;
         Object r;
         if ((dst = this.dst) != null && (fn = this.fn) != null && (a = this.src) != null && (r = a.result) != null && this.compareAndSet(0, 1)) {
            Object t;
            Throwable ex;
            if (r instanceof AltResult) {
               ex = ((AltResult)r).ex;
               t = null;
            } else {
               ex = null;
               t = r;
            }

            Executor e = this.executor;
            Object u = null;
            if (ex == null) {
               try {
                  if (e != null) {
                     e.execute(new AsyncApply(t, fn, dst));
                  } else {
                     u = fn.apply(t);
                  }
               } catch (Throwable var10) {
                  ex = var10;
               }
            }

            if (e == null || ex != null) {
               dst.internalComplete(u, ex);
            }
         }

      }
   }

   abstract static class Completion extends AtomicInteger implements Runnable {
   }

   static final class CompletionNode {
      final Completion completion;
      volatile CompletionNode next;

      CompletionNode(Completion completion) {
         this.completion = completion;
      }
   }

   static final class AsyncCompose extends Async {
      final Object arg;
      final Fun fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      AsyncCompose(Object arg, Fun fn, CompletableFuture dst) {
         this.arg = arg;
         this.fn = fn;
         this.dst = dst;
      }

      public final boolean exec() {
         CompletableFuture d;
         if ((d = this.dst) != null && d.result == null) {
            CompletableFuture fr;
            Object ex;
            try {
               fr = (CompletableFuture)this.fn.apply(this.arg);
               ex = fr == null ? new NullPointerException() : null;
            } catch (Throwable var7) {
               ex = var7;
               fr = null;
            }

            Object u;
            if (ex != null) {
               u = null;
            } else {
               Object r = fr.result;
               if (r == null) {
                  r = fr.waitingGet(false);
               }

               if (r instanceof AltResult) {
                  ex = ((AltResult)r).ex;
                  u = null;
               } else {
                  u = r;
               }
            }

            d.internalComplete(u, (Throwable)ex);
         }

         return true;
      }
   }

   static final class AsyncAcceptBoth extends Async {
      final Object arg1;
      final Object arg2;
      final BiAction fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      AsyncAcceptBoth(Object arg1, Object arg2, BiAction fn, CompletableFuture dst) {
         this.arg1 = arg1;
         this.arg2 = arg2;
         this.fn = fn;
         this.dst = dst;
      }

      public final boolean exec() {
         CompletableFuture d;
         if ((d = this.dst) != null && d.result == null) {
            Throwable ex;
            try {
               this.fn.accept(this.arg1, this.arg2);
               ex = null;
            } catch (Throwable var4) {
               ex = var4;
            }

            d.internalComplete((Object)null, ex);
         }

         return true;
      }
   }

   static final class AsyncAccept extends Async {
      final Object arg;
      final Action fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      AsyncAccept(Object arg, Action fn, CompletableFuture dst) {
         this.arg = arg;
         this.fn = fn;
         this.dst = dst;
      }

      public final boolean exec() {
         CompletableFuture d;
         if ((d = this.dst) != null && d.result == null) {
            Throwable ex;
            try {
               this.fn.accept(this.arg);
               ex = null;
            } catch (Throwable var4) {
               ex = var4;
            }

            d.internalComplete((Object)null, ex);
         }

         return true;
      }
   }

   static final class AsyncCombine extends Async {
      final Object arg1;
      final Object arg2;
      final BiFun fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      AsyncCombine(Object arg1, Object arg2, BiFun fn, CompletableFuture dst) {
         this.arg1 = arg1;
         this.arg2 = arg2;
         this.fn = fn;
         this.dst = dst;
      }

      public final boolean exec() {
         CompletableFuture d;
         if ((d = this.dst) != null && d.result == null) {
            Object v;
            Throwable ex;
            try {
               v = this.fn.apply(this.arg1, this.arg2);
               ex = null;
            } catch (Throwable var5) {
               ex = var5;
               v = null;
            }

            d.internalComplete(v, ex);
         }

         return true;
      }
   }

   static final class AsyncApply extends Async {
      final Object arg;
      final Fun fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      AsyncApply(Object arg, Fun fn, CompletableFuture dst) {
         this.arg = arg;
         this.fn = fn;
         this.dst = dst;
      }

      public final boolean exec() {
         CompletableFuture d;
         if ((d = this.dst) != null && d.result == null) {
            Object u;
            Throwable ex;
            try {
               u = this.fn.apply(this.arg);
               ex = null;
            } catch (Throwable var5) {
               ex = var5;
               u = null;
            }

            d.internalComplete(u, ex);
         }

         return true;
      }
   }

   static final class AsyncSupply extends Async {
      final Generator fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      AsyncSupply(Generator fn, CompletableFuture dst) {
         this.fn = fn;
         this.dst = dst;
      }

      public final boolean exec() {
         CompletableFuture d;
         if ((d = this.dst) != null && d.result == null) {
            Object u;
            Throwable ex;
            try {
               u = this.fn.get();
               ex = null;
            } catch (Throwable var5) {
               ex = var5;
               u = null;
            }

            d.internalComplete(u, ex);
         }

         return true;
      }
   }

   static final class AsyncRun extends Async {
      final Runnable fn;
      final CompletableFuture dst;
      private static final long serialVersionUID = 5232453952276885070L;

      AsyncRun(Runnable fn, CompletableFuture dst) {
         this.fn = fn;
         this.dst = dst;
      }

      public final boolean exec() {
         CompletableFuture d;
         if ((d = this.dst) != null && d.result == null) {
            Throwable ex;
            try {
               this.fn.run();
               ex = null;
            } catch (Throwable var4) {
               ex = var4;
            }

            d.internalComplete((Object)null, ex);
         }

         return true;
      }
   }

   abstract static class Async extends ForkJoinTask implements Runnable, AsynchronousCompletionTask {
      public final Void getRawResult() {
         return null;
      }

      public final void setRawResult(Void v) {
      }

      public final void run() {
         this.exec();
      }
   }

   public interface AsynchronousCompletionTask {
   }

   static final class WaitNode implements ForkJoinPool.ManagedBlocker {
      long nanos;
      final long deadline;
      volatile int interruptControl;
      volatile Thread thread = Thread.currentThread();
      volatile WaitNode next;

      WaitNode(boolean interruptible, long nanos, long deadline) {
         this.interruptControl = interruptible ? 1 : 0;
         this.nanos = nanos;
         this.deadline = deadline;
      }

      public boolean isReleasable() {
         if (this.thread == null) {
            return true;
         } else {
            if (Thread.interrupted()) {
               int i = this.interruptControl;
               this.interruptControl = -1;
               if (i > 0) {
                  return true;
               }
            }

            if (this.deadline == 0L || this.nanos > 0L && (this.nanos = this.deadline - System.nanoTime()) > 0L) {
               return false;
            } else {
               this.thread = null;
               return true;
            }
         }
      }

      public boolean block() {
         if (this.isReleasable()) {
            return true;
         } else {
            if (this.deadline == 0L) {
               LockSupport.park(this);
            } else if (this.nanos > 0L) {
               LockSupport.parkNanos(this, this.nanos);
            }

            return this.isReleasable();
         }
      }
   }

   static final class AltResult {
      final Throwable ex;

      AltResult(Throwable ex) {
         this.ex = ex;
      }
   }

   public interface Generator {
      Object get();
   }

   public interface BiFun {
      Object apply(Object var1, Object var2);
   }

   public interface Fun {
      Object apply(Object var1);
   }

   public interface BiAction {
      void accept(Object var1, Object var2);
   }

   public interface Action {
      void accept(Object var1);
   }
}
