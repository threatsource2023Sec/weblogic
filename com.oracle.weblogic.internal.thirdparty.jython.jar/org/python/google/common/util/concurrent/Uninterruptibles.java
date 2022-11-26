package org.python.google.common.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible(
   emulated = true
)
public final class Uninterruptibles {
   @GwtIncompatible
   public static void awaitUninterruptibly(CountDownLatch latch) {
      boolean interrupted = false;

      try {
         while(true) {
            try {
               latch.await();
               return;
            } catch (InterruptedException var6) {
               interrupted = true;
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @CanIgnoreReturnValue
   @GwtIncompatible
   public static boolean awaitUninterruptibly(CountDownLatch latch, long timeout, TimeUnit unit) {
      boolean interrupted = false;

      try {
         long remainingNanos = unit.toNanos(timeout);
         long end = System.nanoTime() + remainingNanos;

         while(true) {
            try {
               boolean var9 = latch.await(remainingNanos, TimeUnit.NANOSECONDS);
               return var9;
            } catch (InterruptedException var13) {
               interrupted = true;
               remainingNanos = end - System.nanoTime();
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @GwtIncompatible
   public static void joinUninterruptibly(Thread toJoin) {
      boolean interrupted = false;

      try {
         while(true) {
            try {
               toJoin.join();
               return;
            } catch (InterruptedException var6) {
               interrupted = true;
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @CanIgnoreReturnValue
   public static Object getUninterruptibly(Future future) throws ExecutionException {
      boolean interrupted = false;

      try {
         while(true) {
            try {
               Object var2 = future.get();
               return var2;
            } catch (InterruptedException var6) {
               interrupted = true;
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @CanIgnoreReturnValue
   @GwtIncompatible
   public static Object getUninterruptibly(Future future, long timeout, TimeUnit unit) throws ExecutionException, TimeoutException {
      boolean interrupted = false;

      try {
         long remainingNanos = unit.toNanos(timeout);
         long end = System.nanoTime() + remainingNanos;

         while(true) {
            try {
               Object var9 = future.get(remainingNanos, TimeUnit.NANOSECONDS);
               return var9;
            } catch (InterruptedException var13) {
               interrupted = true;
               remainingNanos = end - System.nanoTime();
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @GwtIncompatible
   public static void joinUninterruptibly(Thread toJoin, long timeout, TimeUnit unit) {
      Preconditions.checkNotNull(toJoin);
      boolean interrupted = false;

      try {
         long remainingNanos = unit.toNanos(timeout);
         long end = System.nanoTime() + remainingNanos;

         while(true) {
            try {
               TimeUnit.NANOSECONDS.timedJoin(toJoin, remainingNanos);
               return;
            } catch (InterruptedException var13) {
               interrupted = true;
               remainingNanos = end - System.nanoTime();
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @GwtIncompatible
   public static Object takeUninterruptibly(BlockingQueue queue) {
      boolean interrupted = false;

      try {
         while(true) {
            try {
               Object var2 = queue.take();
               return var2;
            } catch (InterruptedException var6) {
               interrupted = true;
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @GwtIncompatible
   public static void putUninterruptibly(BlockingQueue queue, Object element) {
      boolean interrupted = false;

      try {
         while(true) {
            try {
               queue.put(element);
               return;
            } catch (InterruptedException var7) {
               interrupted = true;
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @GwtIncompatible
   public static void sleepUninterruptibly(long sleepFor, TimeUnit unit) {
      boolean interrupted = false;

      try {
         long remainingNanos = unit.toNanos(sleepFor);
         long end = System.nanoTime() + remainingNanos;

         while(true) {
            try {
               TimeUnit.NANOSECONDS.sleep(remainingNanos);
               return;
            } catch (InterruptedException var12) {
               interrupted = true;
               remainingNanos = end - System.nanoTime();
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   @GwtIncompatible
   public static boolean tryAcquireUninterruptibly(Semaphore semaphore, long timeout, TimeUnit unit) {
      return tryAcquireUninterruptibly(semaphore, 1, timeout, unit);
   }

   @GwtIncompatible
   public static boolean tryAcquireUninterruptibly(Semaphore semaphore, int permits, long timeout, TimeUnit unit) {
      boolean interrupted = false;

      try {
         long remainingNanos = unit.toNanos(timeout);
         long end = System.nanoTime() + remainingNanos;

         while(true) {
            try {
               boolean var10 = semaphore.tryAcquire(permits, remainingNanos, TimeUnit.NANOSECONDS);
               return var10;
            } catch (InterruptedException var14) {
               interrupted = true;
               remainingNanos = end - System.nanoTime();
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }

      }
   }

   private Uninterruptibles() {
   }
}
