package org.python.netty.util.concurrent;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup {
   private final EventExecutor[] children;
   private final Set readonlyChildren;
   private final AtomicInteger terminatedChildren;
   private final Promise terminationFuture;
   private final EventExecutorChooserFactory.EventExecutorChooser chooser;

   protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
      this(nThreads, (Executor)(threadFactory == null ? null : new ThreadPerTaskExecutor(threadFactory)), args);
   }

   protected MultithreadEventExecutorGroup(int nThreads, Executor executor, Object... args) {
      this(nThreads, executor, DefaultEventExecutorChooserFactory.INSTANCE, args);
   }

   protected MultithreadEventExecutorGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, Object... args) {
      this.terminatedChildren = new AtomicInteger();
      this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
      if (nThreads <= 0) {
         throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", nThreads));
      } else {
         if (executor == null) {
            executor = new ThreadPerTaskExecutor(this.newDefaultThreadFactory());
         }

         this.children = new EventExecutor[nThreads];

         int j;
         for(int i = 0; i < nThreads; ++i) {
            boolean success = false;
            boolean var18 = false;

            try {
               var18 = true;
               this.children[i] = this.newChild((Executor)executor, args);
               success = true;
               var18 = false;
            } catch (Exception var19) {
               throw new IllegalStateException("failed to create a child event loop", var19);
            } finally {
               if (var18) {
                  if (!success) {
                     int j;
                     for(j = 0; j < i; ++j) {
                        this.children[j].shutdownGracefully();
                     }

                     for(j = 0; j < i; ++j) {
                        EventExecutor e = this.children[j];

                        try {
                           while(!e.isTerminated()) {
                              e.awaitTermination(2147483647L, TimeUnit.SECONDS);
                           }
                        } catch (InterruptedException var20) {
                           Thread.currentThread().interrupt();
                           break;
                        }
                     }
                  }

               }
            }

            if (!success) {
               for(j = 0; j < i; ++j) {
                  this.children[j].shutdownGracefully();
               }

               for(j = 0; j < i; ++j) {
                  EventExecutor e = this.children[j];

                  try {
                     while(!e.isTerminated()) {
                        e.awaitTermination(2147483647L, TimeUnit.SECONDS);
                     }
                  } catch (InterruptedException var22) {
                     Thread.currentThread().interrupt();
                     break;
                  }
               }
            }
         }

         this.chooser = chooserFactory.newChooser(this.children);
         FutureListener terminationListener = new FutureListener() {
            public void operationComplete(Future future) throws Exception {
               if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
                  MultithreadEventExecutorGroup.this.terminationFuture.setSuccess((Object)null);
               }

            }
         };
         EventExecutor[] var24 = this.children;
         j = var24.length;

         for(int var26 = 0; var26 < j; ++var26) {
            EventExecutor e = var24[var26];
            e.terminationFuture().addListener(terminationListener);
         }

         Set childrenSet = new LinkedHashSet(this.children.length);
         Collections.addAll(childrenSet, this.children);
         this.readonlyChildren = Collections.unmodifiableSet(childrenSet);
      }
   }

   protected ThreadFactory newDefaultThreadFactory() {
      return new DefaultThreadFactory(this.getClass());
   }

   public EventExecutor next() {
      return this.chooser.next();
   }

   public Iterator iterator() {
      return this.readonlyChildren.iterator();
   }

   public final int executorCount() {
      return this.children.length;
   }

   protected abstract EventExecutor newChild(Executor var1, Object... var2) throws Exception;

   public Future shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      EventExecutor[] var6 = this.children;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         EventExecutor l = var6[var8];
         l.shutdownGracefully(quietPeriod, timeout, unit);
      }

      return this.terminationFuture();
   }

   public Future terminationFuture() {
      return this.terminationFuture;
   }

   /** @deprecated */
   @Deprecated
   public void shutdown() {
      EventExecutor[] var1 = this.children;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EventExecutor l = var1[var3];
         l.shutdown();
      }

   }

   public boolean isShuttingDown() {
      EventExecutor[] var1 = this.children;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EventExecutor l = var1[var3];
         if (!l.isShuttingDown()) {
            return false;
         }
      }

      return true;
   }

   public boolean isShutdown() {
      EventExecutor[] var1 = this.children;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EventExecutor l = var1[var3];
         if (!l.isShutdown()) {
            return false;
         }
      }

      return true;
   }

   public boolean isTerminated() {
      EventExecutor[] var1 = this.children;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EventExecutor l = var1[var3];
         if (!l.isTerminated()) {
            return false;
         }
      }

      return true;
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      long deadline = System.nanoTime() + unit.toNanos(timeout);
      EventExecutor[] var6 = this.children;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         EventExecutor l = var6[var8];

         long timeLeft;
         do {
            timeLeft = deadline - System.nanoTime();
            if (timeLeft <= 0L) {
               return this.isTerminated();
            }
         } while(!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
      }

      return this.isTerminated();
   }
}
