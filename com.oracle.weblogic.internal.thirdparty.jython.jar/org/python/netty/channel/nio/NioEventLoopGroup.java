package org.python.netty.channel.nio;

import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import org.python.netty.channel.DefaultSelectStrategyFactory;
import org.python.netty.channel.EventLoop;
import org.python.netty.channel.MultithreadEventLoopGroup;
import org.python.netty.channel.SelectStrategyFactory;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.EventExecutorChooserFactory;
import org.python.netty.util.concurrent.RejectedExecutionHandler;
import org.python.netty.util.concurrent.RejectedExecutionHandlers;

public class NioEventLoopGroup extends MultithreadEventLoopGroup {
   public NioEventLoopGroup() {
      this(0);
   }

   public NioEventLoopGroup(int nThreads) {
      this(nThreads, (Executor)null);
   }

   public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
      this(nThreads, threadFactory, SelectorProvider.provider());
   }

   public NioEventLoopGroup(int nThreads, Executor executor) {
      this(nThreads, executor, SelectorProvider.provider());
   }

   public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
      this(nThreads, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
   }

   public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
      super(nThreads, threadFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
   }

   public NioEventLoopGroup(int nThreads, Executor executor, SelectorProvider selectorProvider) {
      this(nThreads, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
   }

   public NioEventLoopGroup(int nThreads, Executor executor, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
      super(nThreads, executor, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
   }

   public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
      super(nThreads, executor, chooserFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
   }

   public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
      super(nThreads, executor, chooserFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler);
   }

   public void setIoRatio(int ioRatio) {
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         EventExecutor e = (EventExecutor)var2.next();
         ((NioEventLoop)e).setIoRatio(ioRatio);
      }

   }

   public void rebuildSelectors() {
      Iterator var1 = this.iterator();

      while(var1.hasNext()) {
         EventExecutor e = (EventExecutor)var1.next();
         ((NioEventLoop)e).rebuildSelector();
      }

   }

   protected EventLoop newChild(Executor executor, Object... args) throws Exception {
      return new NioEventLoop(this, executor, (SelectorProvider)args[0], ((SelectStrategyFactory)args[1]).newSelectStrategy(), (RejectedExecutionHandler)args[2]);
   }
}
