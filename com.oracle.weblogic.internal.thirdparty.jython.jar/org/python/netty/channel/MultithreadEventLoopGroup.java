package org.python.netty.channel;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import org.python.netty.util.NettyRuntime;
import org.python.netty.util.concurrent.DefaultThreadFactory;
import org.python.netty.util.concurrent.EventExecutorChooserFactory;
import org.python.netty.util.concurrent.MultithreadEventExecutorGroup;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
   private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("org.python.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));

   protected MultithreadEventLoopGroup(int nThreads, Executor executor, Object... args) {
      super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor, args);
   }

   protected MultithreadEventLoopGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
      super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, threadFactory, args);
   }

   protected MultithreadEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, Object... args) {
      super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor, chooserFactory, args);
   }

   protected ThreadFactory newDefaultThreadFactory() {
      return new DefaultThreadFactory(this.getClass(), 10);
   }

   public EventLoop next() {
      return (EventLoop)super.next();
   }

   protected abstract EventLoop newChild(Executor var1, Object... var2) throws Exception;

   public ChannelFuture register(Channel channel) {
      return this.next().register(channel);
   }

   public ChannelFuture register(ChannelPromise promise) {
      return this.next().register(promise);
   }

   /** @deprecated */
   @Deprecated
   public ChannelFuture register(Channel channel, ChannelPromise promise) {
      return this.next().register(channel, promise);
   }

   static {
      if (logger.isDebugEnabled()) {
         logger.debug("-Dio.netty.eventLoopThreads: {}", (Object)DEFAULT_EVENT_LOOP_THREADS);
      }

   }
}
