package org.python.netty.channel.pool;

import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.netty.bootstrap.Bootstrap;
import org.python.netty.channel.Channel;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.ThrowableUtil;

public class FixedChannelPool extends SimpleChannelPool {
   private static final IllegalStateException FULL_EXCEPTION = (IllegalStateException)ThrowableUtil.unknownStackTrace(new IllegalStateException("Too many outstanding acquire operations"), FixedChannelPool.class, "acquire0(...)");
   private static final TimeoutException TIMEOUT_EXCEPTION = (TimeoutException)ThrowableUtil.unknownStackTrace(new TimeoutException("Acquire operation took longer then configured maximum time"), FixedChannelPool.class, "<init>(...)");
   private final EventExecutor executor;
   private final long acquireTimeoutNanos;
   private final Runnable timeoutTask;
   private final Queue pendingAcquireQueue;
   private final int maxConnections;
   private final int maxPendingAcquires;
   private int acquiredChannelCount;
   private int pendingAcquireCount;
   private boolean closed;

   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections) {
      this(bootstrap, handler, maxConnections, Integer.MAX_VALUE);
   }

   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections, int maxPendingAcquires) {
      this(bootstrap, handler, ChannelHealthChecker.ACTIVE, (AcquireTimeoutAction)null, -1L, maxConnections, maxPendingAcquires);
   }

   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires) {
      this(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires, true);
   }

   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires, boolean releaseHealthCheck) {
      super(bootstrap, handler, healthCheck, releaseHealthCheck);
      this.pendingAcquireQueue = new ArrayDeque();
      if (maxConnections < 1) {
         throw new IllegalArgumentException("maxConnections: " + maxConnections + " (expected: >= 1)");
      } else if (maxPendingAcquires < 1) {
         throw new IllegalArgumentException("maxPendingAcquires: " + maxPendingAcquires + " (expected: >= 1)");
      } else {
         if (action == null && acquireTimeoutMillis == -1L) {
            this.timeoutTask = null;
            this.acquireTimeoutNanos = -1L;
         } else {
            if (action == null && acquireTimeoutMillis != -1L) {
               throw new NullPointerException("action");
            }

            if (action != null && acquireTimeoutMillis < 0L) {
               throw new IllegalArgumentException("acquireTimeoutMillis: " + acquireTimeoutMillis + " (expected: >= 1)");
            }

            this.acquireTimeoutNanos = TimeUnit.MILLISECONDS.toNanos(acquireTimeoutMillis);
            switch (action) {
               case FAIL:
                  this.timeoutTask = new TimeoutTask() {
                     public void onTimeout(AcquireTask task) {
                        task.promise.setFailure(FixedChannelPool.TIMEOUT_EXCEPTION);
                     }
                  };
                  break;
               case NEW:
                  this.timeoutTask = new TimeoutTask() {
                     public void onTimeout(AcquireTask task) {
                        task.acquired();
                        FixedChannelPool.super.acquire(task.promise);
                     }
                  };
                  break;
               default:
                  throw new Error();
            }
         }

         this.executor = bootstrap.config().group().next();
         this.maxConnections = maxConnections;
         this.maxPendingAcquires = maxPendingAcquires;
      }
   }

   public Future acquire(final Promise promise) {
      try {
         if (this.executor.inEventLoop()) {
            this.acquire0(promise);
         } else {
            this.executor.execute(new Runnable() {
               public void run() {
                  FixedChannelPool.this.acquire0(promise);
               }
            });
         }
      } catch (Throwable var3) {
         promise.setFailure(var3);
      }

      return promise;
   }

   private void acquire0(Promise promise) {
      assert this.executor.inEventLoop();

      if (this.closed) {
         promise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
      } else {
         if (this.acquiredChannelCount < this.maxConnections) {
            assert this.acquiredChannelCount >= 0;

            Promise p = this.executor.newPromise();
            AcquireListener l = new AcquireListener(promise);
            l.acquired();
            p.addListener(l);
            super.acquire(p);
         } else {
            if (this.pendingAcquireCount >= this.maxPendingAcquires) {
               promise.setFailure(FULL_EXCEPTION);
            } else {
               AcquireTask task = new AcquireTask(promise);
               if (this.pendingAcquireQueue.offer(task)) {
                  ++this.pendingAcquireCount;
                  if (this.timeoutTask != null) {
                     task.timeoutFuture = this.executor.schedule(this.timeoutTask, this.acquireTimeoutNanos, TimeUnit.NANOSECONDS);
                  }
               } else {
                  promise.setFailure(FULL_EXCEPTION);
               }
            }

            assert this.pendingAcquireCount > 0;
         }

      }
   }

   public Future release(Channel channel, final Promise promise) {
      Promise p = this.executor.newPromise();
      super.release(channel, p.addListener(new FutureListener() {
         public void operationComplete(Future future) throws Exception {
            assert FixedChannelPool.this.executor.inEventLoop();

            if (FixedChannelPool.this.closed) {
               promise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
            } else {
               if (future.isSuccess()) {
                  FixedChannelPool.this.decrementAndRunTaskQueue();
                  promise.setSuccess((Object)null);
               } else {
                  Throwable cause = future.cause();
                  if (!(cause instanceof IllegalArgumentException)) {
                     FixedChannelPool.this.decrementAndRunTaskQueue();
                  }

                  promise.setFailure(future.cause());
               }

            }
         }
      }));
      return p;
   }

   private void decrementAndRunTaskQueue() {
      --this.acquiredChannelCount;

      assert this.acquiredChannelCount >= 0;

      this.runTaskQueue();
   }

   private void runTaskQueue() {
      while(true) {
         if (this.acquiredChannelCount < this.maxConnections) {
            AcquireTask task = (AcquireTask)this.pendingAcquireQueue.poll();
            if (task != null) {
               ScheduledFuture timeoutFuture = task.timeoutFuture;
               if (timeoutFuture != null) {
                  timeoutFuture.cancel(false);
               }

               --this.pendingAcquireCount;
               task.acquired();
               super.acquire(task.promise);
               continue;
            }
         }

         assert this.pendingAcquireCount >= 0;

         assert this.acquiredChannelCount >= 0;

         return;
      }
   }

   public void close() {
      this.executor.execute(new Runnable() {
         public void run() {
            if (!FixedChannelPool.this.closed) {
               FixedChannelPool.this.closed = true;

               while(true) {
                  AcquireTask task = (AcquireTask)FixedChannelPool.this.pendingAcquireQueue.poll();
                  if (task == null) {
                     FixedChannelPool.this.acquiredChannelCount = 0;
                     FixedChannelPool.this.pendingAcquireCount = 0;
                     FixedChannelPool.super.close();
                     break;
                  }

                  ScheduledFuture f = task.timeoutFuture;
                  if (f != null) {
                     f.cancel(false);
                  }

                  task.promise.setFailure(new ClosedChannelException());
               }
            }

         }
      });
   }

   private class AcquireListener implements FutureListener {
      private final Promise originalPromise;
      protected boolean acquired;

      AcquireListener(Promise originalPromise) {
         this.originalPromise = originalPromise;
      }

      public void operationComplete(Future future) throws Exception {
         assert FixedChannelPool.this.executor.inEventLoop();

         if (FixedChannelPool.this.closed) {
            this.originalPromise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
         } else {
            if (future.isSuccess()) {
               this.originalPromise.setSuccess(future.getNow());
            } else {
               if (this.acquired) {
                  FixedChannelPool.this.decrementAndRunTaskQueue();
               } else {
                  FixedChannelPool.this.runTaskQueue();
               }

               this.originalPromise.setFailure(future.cause());
            }

         }
      }

      public void acquired() {
         if (!this.acquired) {
            FixedChannelPool.this.acquiredChannelCount++;
            this.acquired = true;
         }
      }
   }

   private abstract class TimeoutTask implements Runnable {
      private TimeoutTask() {
      }

      public final void run() {
         assert FixedChannelPool.this.executor.inEventLoop();

         long nanoTime = System.nanoTime();

         while(true) {
            AcquireTask task = (AcquireTask)FixedChannelPool.this.pendingAcquireQueue.peek();
            if (task == null || nanoTime - task.expireNanoTime < 0L) {
               return;
            }

            FixedChannelPool.this.pendingAcquireQueue.remove();
            --FixedChannelPool.this.pendingAcquireCount;
            this.onTimeout(task);
         }
      }

      public abstract void onTimeout(AcquireTask var1);

      // $FF: synthetic method
      TimeoutTask(Object x1) {
         this();
      }
   }

   private final class AcquireTask extends AcquireListener {
      final Promise promise;
      final long expireNanoTime;
      ScheduledFuture timeoutFuture;

      public AcquireTask(Promise promise) {
         super(promise);
         this.expireNanoTime = System.nanoTime() + FixedChannelPool.this.acquireTimeoutNanos;
         this.promise = FixedChannelPool.this.executor.newPromise().addListener(this);
      }
   }

   public static enum AcquireTimeoutAction {
      NEW,
      FAIL;
   }
}
