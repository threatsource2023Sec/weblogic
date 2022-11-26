package org.python.netty.channel;

import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.python.netty.util.concurrent.AbstractEventExecutorGroup;
import org.python.netty.util.concurrent.DefaultPromise;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.concurrent.GlobalEventExecutor;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.concurrent.ThreadPerTaskExecutor;
import org.python.netty.util.internal.EmptyArrays;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.ReadOnlyIterator;
import org.python.netty.util.internal.ThrowableUtil;

public class ThreadPerChannelEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
   private final Object[] childArgs;
   private final int maxChannels;
   final Executor executor;
   final Set activeChildren;
   final Queue idleChildren;
   private final ChannelException tooManyChannels;
   private volatile boolean shuttingDown;
   private final Promise terminationFuture;
   private final FutureListener childTerminationListener;

   protected ThreadPerChannelEventLoopGroup() {
      this(0);
   }

   protected ThreadPerChannelEventLoopGroup(int maxChannels) {
      this(maxChannels, Executors.defaultThreadFactory());
   }

   protected ThreadPerChannelEventLoopGroup(int maxChannels, ThreadFactory threadFactory, Object... args) {
      this(maxChannels, (Executor)(new ThreadPerTaskExecutor(threadFactory)), args);
   }

   protected ThreadPerChannelEventLoopGroup(int maxChannels, Executor executor, Object... args) {
      this.activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
      this.idleChildren = new ConcurrentLinkedQueue();
      this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
      this.childTerminationListener = new FutureListener() {
         public void operationComplete(Future future) throws Exception {
            if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
               ThreadPerChannelEventLoopGroup.this.terminationFuture.trySuccess((Object)null);
            }

         }
      };
      if (maxChannels < 0) {
         throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", maxChannels));
      } else if (executor == null) {
         throw new NullPointerException("executor");
      } else {
         if (args == null) {
            this.childArgs = EmptyArrays.EMPTY_OBJECTS;
         } else {
            this.childArgs = (Object[])args.clone();
         }

         this.maxChannels = maxChannels;
         this.executor = executor;
         this.tooManyChannels = (ChannelException)ThrowableUtil.unknownStackTrace(new ChannelException("too many channels (max: " + maxChannels + ')'), ThreadPerChannelEventLoopGroup.class, "nextChild()");
      }
   }

   protected EventLoop newChild(Object... args) throws Exception {
      return new ThreadPerChannelEventLoop(this);
   }

   public Iterator iterator() {
      return new ReadOnlyIterator(this.activeChildren.iterator());
   }

   public EventLoop next() {
      throw new UnsupportedOperationException();
   }

   public Future shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      this.shuttingDown = true;
      Iterator var6 = this.activeChildren.iterator();

      EventLoop l;
      while(var6.hasNext()) {
         l = (EventLoop)var6.next();
         l.shutdownGracefully(quietPeriod, timeout, unit);
      }

      var6 = this.idleChildren.iterator();

      while(var6.hasNext()) {
         l = (EventLoop)var6.next();
         l.shutdownGracefully(quietPeriod, timeout, unit);
      }

      if (this.isTerminated()) {
         this.terminationFuture.trySuccess((Object)null);
      }

      return this.terminationFuture();
   }

   public Future terminationFuture() {
      return this.terminationFuture;
   }

   /** @deprecated */
   @Deprecated
   public void shutdown() {
      this.shuttingDown = true;
      Iterator var1 = this.activeChildren.iterator();

      EventLoop l;
      while(var1.hasNext()) {
         l = (EventLoop)var1.next();
         l.shutdown();
      }

      var1 = this.idleChildren.iterator();

      while(var1.hasNext()) {
         l = (EventLoop)var1.next();
         l.shutdown();
      }

      if (this.isTerminated()) {
         this.terminationFuture.trySuccess((Object)null);
      }

   }

   public boolean isShuttingDown() {
      Iterator var1 = this.activeChildren.iterator();

      EventLoop l;
      do {
         if (!var1.hasNext()) {
            var1 = this.idleChildren.iterator();

            do {
               if (!var1.hasNext()) {
                  return true;
               }

               l = (EventLoop)var1.next();
            } while(l.isShuttingDown());

            return false;
         }

         l = (EventLoop)var1.next();
      } while(l.isShuttingDown());

      return false;
   }

   public boolean isShutdown() {
      Iterator var1 = this.activeChildren.iterator();

      EventLoop l;
      do {
         if (!var1.hasNext()) {
            var1 = this.idleChildren.iterator();

            do {
               if (!var1.hasNext()) {
                  return true;
               }

               l = (EventLoop)var1.next();
            } while(l.isShutdown());

            return false;
         }

         l = (EventLoop)var1.next();
      } while(l.isShutdown());

      return false;
   }

   public boolean isTerminated() {
      Iterator var1 = this.activeChildren.iterator();

      EventLoop l;
      do {
         if (!var1.hasNext()) {
            var1 = this.idleChildren.iterator();

            do {
               if (!var1.hasNext()) {
                  return true;
               }

               l = (EventLoop)var1.next();
            } while(l.isTerminated());

            return false;
         }

         l = (EventLoop)var1.next();
      } while(l.isTerminated());

      return false;
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      long deadline = System.nanoTime() + unit.toNanos(timeout);
      Iterator var6 = this.activeChildren.iterator();

      EventLoop l;
      long timeLeft;
      while(var6.hasNext()) {
         l = (EventLoop)var6.next();

         while(true) {
            timeLeft = deadline - System.nanoTime();
            if (timeLeft <= 0L) {
               return this.isTerminated();
            }

            if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
               break;
            }
         }
      }

      var6 = this.idleChildren.iterator();

      while(var6.hasNext()) {
         l = (EventLoop)var6.next();

         while(true) {
            timeLeft = deadline - System.nanoTime();
            if (timeLeft <= 0L) {
               return this.isTerminated();
            }

            if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
               break;
            }
         }
      }

      return this.isTerminated();
   }

   public ChannelFuture register(Channel channel) {
      if (channel == null) {
         throw new NullPointerException("channel");
      } else {
         try {
            EventLoop l = this.nextChild();
            return l.register(new DefaultChannelPromise(channel, l));
         } catch (Throwable var3) {
            return new FailedChannelFuture(channel, GlobalEventExecutor.INSTANCE, var3);
         }
      }
   }

   public ChannelFuture register(ChannelPromise promise) {
      try {
         return this.nextChild().register(promise);
      } catch (Throwable var3) {
         promise.setFailure(var3);
         return promise;
      }
   }

   /** @deprecated */
   @Deprecated
   public ChannelFuture register(Channel channel, ChannelPromise promise) {
      if (channel == null) {
         throw new NullPointerException("channel");
      } else {
         try {
            return this.nextChild().register(channel, promise);
         } catch (Throwable var4) {
            promise.setFailure(var4);
            return promise;
         }
      }
   }

   private EventLoop nextChild() throws Exception {
      if (this.shuttingDown) {
         throw new RejectedExecutionException("shutting down");
      } else {
         EventLoop loop = (EventLoop)this.idleChildren.poll();
         if (loop == null) {
            if (this.maxChannels > 0 && this.activeChildren.size() >= this.maxChannels) {
               throw this.tooManyChannels;
            }

            loop = this.newChild(this.childArgs);
            loop.terminationFuture().addListener(this.childTerminationListener);
         }

         this.activeChildren.add(loop);
         return loop;
      }
   }
}
