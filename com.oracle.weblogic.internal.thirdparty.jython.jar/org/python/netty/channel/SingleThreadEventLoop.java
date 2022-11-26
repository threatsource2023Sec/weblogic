package org.python.netty.channel;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import org.python.netty.util.concurrent.RejectedExecutionHandler;
import org.python.netty.util.concurrent.RejectedExecutionHandlers;
import org.python.netty.util.concurrent.SingleThreadEventExecutor;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.SystemPropertyUtil;

public abstract class SingleThreadEventLoop extends SingleThreadEventExecutor implements EventLoop {
   protected static final int DEFAULT_MAX_PENDING_TASKS = Math.max(16, SystemPropertyUtil.getInt("org.python.netty.eventLoop.maxPendingTasks", Integer.MAX_VALUE));
   private final Queue tailTasks;

   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
      this(parent, threadFactory, addTaskWakesUp, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
   }

   protected SingleThreadEventLoop(EventLoopGroup parent, Executor executor, boolean addTaskWakesUp) {
      this(parent, executor, addTaskWakesUp, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
   }

   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
      super(parent, (ThreadFactory)threadFactory, addTaskWakesUp, maxPendingTasks, rejectedExecutionHandler);
      this.tailTasks = this.newTaskQueue(maxPendingTasks);
   }

   protected SingleThreadEventLoop(EventLoopGroup parent, Executor executor, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
      super(parent, (Executor)executor, addTaskWakesUp, maxPendingTasks, rejectedExecutionHandler);
      this.tailTasks = this.newTaskQueue(maxPendingTasks);
   }

   public EventLoopGroup parent() {
      return (EventLoopGroup)super.parent();
   }

   public EventLoop next() {
      return (EventLoop)super.next();
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
      if (channel == null) {
         throw new NullPointerException("channel");
      } else if (promise == null) {
         throw new NullPointerException("promise");
      } else {
         channel.unsafe().register(this, promise);
         return promise;
      }
   }

   public final void executeAfterEventLoopIteration(Runnable task) {
      ObjectUtil.checkNotNull(task, "task");
      if (this.isShutdown()) {
         reject();
      }

      if (!this.tailTasks.offer(task)) {
         this.reject(task);
      }

      if (this.wakesUpForTask(task)) {
         this.wakeup(this.inEventLoop());
      }

   }

   final boolean removeAfterEventLoopIterationTask(Runnable task) {
      return this.tailTasks.remove(ObjectUtil.checkNotNull(task, "task"));
   }

   protected boolean wakesUpForTask(Runnable task) {
      return !(task instanceof NonWakeupRunnable);
   }

   protected void afterRunningAllTasks() {
      this.runAllTasksFrom(this.tailTasks);
   }

   protected boolean hasTasks() {
      return super.hasTasks() || !this.tailTasks.isEmpty();
   }

   public int pendingTasks() {
      return super.pendingTasks() + this.tailTasks.size();
   }

   interface NonWakeupRunnable extends Runnable {
   }
}
