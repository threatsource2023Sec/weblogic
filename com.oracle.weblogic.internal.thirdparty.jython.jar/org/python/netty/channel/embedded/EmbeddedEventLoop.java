package org.python.netty.channel.embedded;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.DefaultChannelPromise;
import org.python.netty.channel.EventLoop;
import org.python.netty.channel.EventLoopGroup;
import org.python.netty.util.concurrent.AbstractScheduledEventExecutor;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.internal.ObjectUtil;

final class EmbeddedEventLoop extends AbstractScheduledEventExecutor implements EventLoop {
   private final Queue tasks = new ArrayDeque(2);

   public EventLoopGroup parent() {
      return (EventLoopGroup)super.parent();
   }

   public EventLoop next() {
      return (EventLoop)super.next();
   }

   public void execute(Runnable command) {
      if (command == null) {
         throw new NullPointerException("command");
      } else {
         this.tasks.add(command);
      }
   }

   void runTasks() {
      while(true) {
         Runnable task = (Runnable)this.tasks.poll();
         if (task == null) {
            return;
         }

         task.run();
      }
   }

   long runScheduledTasks() {
      long time = AbstractScheduledEventExecutor.nanoTime();

      while(true) {
         Runnable task = this.pollScheduledTask(time);
         if (task == null) {
            return this.nextScheduledTaskNano();
         }

         task.run();
      }
   }

   long nextScheduledTask() {
      return this.nextScheduledTaskNano();
   }

   protected void cancelScheduledTasks() {
      super.cancelScheduledTasks();
   }

   public Future shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      throw new UnsupportedOperationException();
   }

   public Future terminationFuture() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void shutdown() {
      throw new UnsupportedOperationException();
   }

   public boolean isShuttingDown() {
      return false;
   }

   public boolean isShutdown() {
      return false;
   }

   public boolean isTerminated() {
      return false;
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) {
      return false;
   }

   public ChannelFuture register(Channel channel) {
      return this.register((ChannelPromise)(new DefaultChannelPromise(channel, this)));
   }

   public ChannelFuture register(ChannelPromise promise) {
      ObjectUtil.checkNotNull(promise, "promise");
      promise.channel().unsafe().register(this, promise);
      return promise;
   }

   /** @deprecated */
   @Deprecated
   public ChannelFuture register(Channel channel, ChannelPromise promise) {
      channel.unsafe().register(this, promise);
      return promise;
   }

   public boolean inEventLoop() {
      return true;
   }

   public boolean inEventLoop(Thread thread) {
      return true;
   }
}
