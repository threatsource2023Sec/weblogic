package org.python.netty.channel;

import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.Attribute;
import org.python.netty.util.AttributeKey;
import org.python.netty.util.DefaultAttributeMap;
import org.python.netty.util.Recycler;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.ResourceLeakHint;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.OrderedEventExecutor;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PromiseNotificationUtil;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.ThrowableUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

abstract class AbstractChannelHandlerContext extends DefaultAttributeMap implements ChannelHandlerContext, ResourceLeakHint {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannelHandlerContext.class);
   volatile AbstractChannelHandlerContext next;
   volatile AbstractChannelHandlerContext prev;
   private static final AtomicIntegerFieldUpdater HANDLER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(AbstractChannelHandlerContext.class, "handlerState");
   private static final int ADD_PENDING = 1;
   private static final int ADD_COMPLETE = 2;
   private static final int REMOVE_COMPLETE = 3;
   private static final int INIT = 0;
   private final boolean inbound;
   private final boolean outbound;
   private final DefaultChannelPipeline pipeline;
   private final String name;
   private final boolean ordered;
   final EventExecutor executor;
   private ChannelFuture succeededFuture;
   private Runnable invokeChannelReadCompleteTask;
   private Runnable invokeReadTask;
   private Runnable invokeChannelWritableStateChangedTask;
   private Runnable invokeFlushTask;
   private volatile int handlerState = 0;

   AbstractChannelHandlerContext(DefaultChannelPipeline pipeline, EventExecutor executor, String name, boolean inbound, boolean outbound) {
      this.name = (String)ObjectUtil.checkNotNull(name, "name");
      this.pipeline = pipeline;
      this.executor = executor;
      this.inbound = inbound;
      this.outbound = outbound;
      this.ordered = executor == null || executor instanceof OrderedEventExecutor;
   }

   public Channel channel() {
      return this.pipeline.channel();
   }

   public ChannelPipeline pipeline() {
      return this.pipeline;
   }

   public ByteBufAllocator alloc() {
      return this.channel().config().getAllocator();
   }

   public EventExecutor executor() {
      return (EventExecutor)(this.executor == null ? this.channel().eventLoop() : this.executor);
   }

   public String name() {
      return this.name;
   }

   public ChannelHandlerContext fireChannelRegistered() {
      invokeChannelRegistered(this.findContextInbound());
      return this;
   }

   static void invokeChannelRegistered(final AbstractChannelHandlerContext next) {
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeChannelRegistered();
      } else {
         executor.execute(new Runnable() {
            public void run() {
               next.invokeChannelRegistered();
            }
         });
      }

   }

   private void invokeChannelRegistered() {
      if (this.invokeHandler()) {
         try {
            ((ChannelInboundHandler)this.handler()).channelRegistered(this);
         } catch (Throwable var2) {
            this.notifyHandlerException(var2);
         }
      } else {
         this.fireChannelRegistered();
      }

   }

   public ChannelHandlerContext fireChannelUnregistered() {
      invokeChannelUnregistered(this.findContextInbound());
      return this;
   }

   static void invokeChannelUnregistered(final AbstractChannelHandlerContext next) {
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeChannelUnregistered();
      } else {
         executor.execute(new Runnable() {
            public void run() {
               next.invokeChannelUnregistered();
            }
         });
      }

   }

   private void invokeChannelUnregistered() {
      if (this.invokeHandler()) {
         try {
            ((ChannelInboundHandler)this.handler()).channelUnregistered(this);
         } catch (Throwable var2) {
            this.notifyHandlerException(var2);
         }
      } else {
         this.fireChannelUnregistered();
      }

   }

   public ChannelHandlerContext fireChannelActive() {
      invokeChannelActive(this.findContextInbound());
      return this;
   }

   static void invokeChannelActive(final AbstractChannelHandlerContext next) {
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeChannelActive();
      } else {
         executor.execute(new Runnable() {
            public void run() {
               next.invokeChannelActive();
            }
         });
      }

   }

   private void invokeChannelActive() {
      if (this.invokeHandler()) {
         try {
            ((ChannelInboundHandler)this.handler()).channelActive(this);
         } catch (Throwable var2) {
            this.notifyHandlerException(var2);
         }
      } else {
         this.fireChannelActive();
      }

   }

   public ChannelHandlerContext fireChannelInactive() {
      invokeChannelInactive(this.findContextInbound());
      return this;
   }

   static void invokeChannelInactive(final AbstractChannelHandlerContext next) {
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeChannelInactive();
      } else {
         executor.execute(new Runnable() {
            public void run() {
               next.invokeChannelInactive();
            }
         });
      }

   }

   private void invokeChannelInactive() {
      if (this.invokeHandler()) {
         try {
            ((ChannelInboundHandler)this.handler()).channelInactive(this);
         } catch (Throwable var2) {
            this.notifyHandlerException(var2);
         }
      } else {
         this.fireChannelInactive();
      }

   }

   public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
      invokeExceptionCaught(this.next, cause);
      return this;
   }

   static void invokeExceptionCaught(final AbstractChannelHandlerContext next, final Throwable cause) {
      ObjectUtil.checkNotNull(cause, "cause");
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeExceptionCaught(cause);
      } else {
         try {
            executor.execute(new Runnable() {
               public void run() {
                  next.invokeExceptionCaught(cause);
               }
            });
         } catch (Throwable var4) {
            if (logger.isWarnEnabled()) {
               logger.warn("Failed to submit an exceptionCaught() event.", var4);
               logger.warn("The exceptionCaught() event that was failed to submit was:", cause);
            }
         }
      }

   }

   private void invokeExceptionCaught(Throwable cause) {
      if (this.invokeHandler()) {
         try {
            this.handler().exceptionCaught(this, cause);
         } catch (Throwable var3) {
            if (logger.isDebugEnabled()) {
               logger.debug("An exception {}was thrown by a user handler's exceptionCaught() method while handling the following exception:", ThrowableUtil.stackTraceToString(var3), cause);
            } else if (logger.isWarnEnabled()) {
               logger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", var3, cause);
            }
         }
      } else {
         this.fireExceptionCaught(cause);
      }

   }

   public ChannelHandlerContext fireUserEventTriggered(Object event) {
      invokeUserEventTriggered(this.findContextInbound(), event);
      return this;
   }

   static void invokeUserEventTriggered(final AbstractChannelHandlerContext next, final Object event) {
      ObjectUtil.checkNotNull(event, "event");
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeUserEventTriggered(event);
      } else {
         executor.execute(new Runnable() {
            public void run() {
               next.invokeUserEventTriggered(event);
            }
         });
      }

   }

   private void invokeUserEventTriggered(Object event) {
      if (this.invokeHandler()) {
         try {
            ((ChannelInboundHandler)this.handler()).userEventTriggered(this, event);
         } catch (Throwable var3) {
            this.notifyHandlerException(var3);
         }
      } else {
         this.fireUserEventTriggered(event);
      }

   }

   public ChannelHandlerContext fireChannelRead(Object msg) {
      invokeChannelRead(this.findContextInbound(), msg);
      return this;
   }

   static void invokeChannelRead(final AbstractChannelHandlerContext next, Object msg) {
      final Object m = next.pipeline.touch(ObjectUtil.checkNotNull(msg, "msg"), next);
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeChannelRead(m);
      } else {
         executor.execute(new Runnable() {
            public void run() {
               next.invokeChannelRead(m);
            }
         });
      }

   }

   private void invokeChannelRead(Object msg) {
      if (this.invokeHandler()) {
         try {
            ((ChannelInboundHandler)this.handler()).channelRead(this, msg);
         } catch (Throwable var3) {
            this.notifyHandlerException(var3);
         }
      } else {
         this.fireChannelRead(msg);
      }

   }

   public ChannelHandlerContext fireChannelReadComplete() {
      invokeChannelReadComplete(this.findContextInbound());
      return this;
   }

   static void invokeChannelReadComplete(final AbstractChannelHandlerContext next) {
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeChannelReadComplete();
      } else {
         Runnable task = next.invokeChannelReadCompleteTask;
         if (task == null) {
            next.invokeChannelReadCompleteTask = task = new Runnable() {
               public void run() {
                  next.invokeChannelReadComplete();
               }
            };
         }

         executor.execute(task);
      }

   }

   private void invokeChannelReadComplete() {
      if (this.invokeHandler()) {
         try {
            ((ChannelInboundHandler)this.handler()).channelReadComplete(this);
         } catch (Throwable var2) {
            this.notifyHandlerException(var2);
         }
      } else {
         this.fireChannelReadComplete();
      }

   }

   public ChannelHandlerContext fireChannelWritabilityChanged() {
      invokeChannelWritabilityChanged(this.findContextInbound());
      return this;
   }

   static void invokeChannelWritabilityChanged(final AbstractChannelHandlerContext next) {
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeChannelWritabilityChanged();
      } else {
         Runnable task = next.invokeChannelWritableStateChangedTask;
         if (task == null) {
            next.invokeChannelWritableStateChangedTask = task = new Runnable() {
               public void run() {
                  next.invokeChannelWritabilityChanged();
               }
            };
         }

         executor.execute(task);
      }

   }

   private void invokeChannelWritabilityChanged() {
      if (this.invokeHandler()) {
         try {
            ((ChannelInboundHandler)this.handler()).channelWritabilityChanged(this);
         } catch (Throwable var2) {
            this.notifyHandlerException(var2);
         }
      } else {
         this.fireChannelWritabilityChanged();
      }

   }

   public ChannelFuture bind(SocketAddress localAddress) {
      return this.bind(localAddress, this.newPromise());
   }

   public ChannelFuture connect(SocketAddress remoteAddress) {
      return this.connect(remoteAddress, this.newPromise());
   }

   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
      return this.connect(remoteAddress, localAddress, this.newPromise());
   }

   public ChannelFuture disconnect() {
      return this.disconnect(this.newPromise());
   }

   public ChannelFuture close() {
      return this.close(this.newPromise());
   }

   public ChannelFuture deregister() {
      return this.deregister(this.newPromise());
   }

   public ChannelFuture bind(final SocketAddress localAddress, final ChannelPromise promise) {
      if (localAddress == null) {
         throw new NullPointerException("localAddress");
      } else if (this.isNotValidPromise(promise, false)) {
         return promise;
      } else {
         final AbstractChannelHandlerContext next = this.findContextOutbound();
         EventExecutor executor = next.executor();
         if (executor.inEventLoop()) {
            next.invokeBind(localAddress, promise);
         } else {
            safeExecute(executor, new Runnable() {
               public void run() {
                  next.invokeBind(localAddress, promise);
               }
            }, promise, (Object)null);
         }

         return promise;
      }
   }

   private void invokeBind(SocketAddress localAddress, ChannelPromise promise) {
      if (this.invokeHandler()) {
         try {
            ((ChannelOutboundHandler)this.handler()).bind(this, localAddress, promise);
         } catch (Throwable var4) {
            notifyOutboundHandlerException(var4, promise);
         }
      } else {
         this.bind(localAddress, promise);
      }

   }

   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
      return this.connect(remoteAddress, (SocketAddress)null, promise);
   }

   public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
      if (remoteAddress == null) {
         throw new NullPointerException("remoteAddress");
      } else if (this.isNotValidPromise(promise, false)) {
         return promise;
      } else {
         final AbstractChannelHandlerContext next = this.findContextOutbound();
         EventExecutor executor = next.executor();
         if (executor.inEventLoop()) {
            next.invokeConnect(remoteAddress, localAddress, promise);
         } else {
            safeExecute(executor, new Runnable() {
               public void run() {
                  next.invokeConnect(remoteAddress, localAddress, promise);
               }
            }, promise, (Object)null);
         }

         return promise;
      }
   }

   private void invokeConnect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
      if (this.invokeHandler()) {
         try {
            ((ChannelOutboundHandler)this.handler()).connect(this, remoteAddress, localAddress, promise);
         } catch (Throwable var5) {
            notifyOutboundHandlerException(var5, promise);
         }
      } else {
         this.connect(remoteAddress, localAddress, promise);
      }

   }

   public ChannelFuture disconnect(final ChannelPromise promise) {
      if (this.isNotValidPromise(promise, false)) {
         return promise;
      } else {
         final AbstractChannelHandlerContext next = this.findContextOutbound();
         EventExecutor executor = next.executor();
         if (executor.inEventLoop()) {
            if (!this.channel().metadata().hasDisconnect()) {
               next.invokeClose(promise);
            } else {
               next.invokeDisconnect(promise);
            }
         } else {
            safeExecute(executor, new Runnable() {
               public void run() {
                  if (!AbstractChannelHandlerContext.this.channel().metadata().hasDisconnect()) {
                     next.invokeClose(promise);
                  } else {
                     next.invokeDisconnect(promise);
                  }

               }
            }, promise, (Object)null);
         }

         return promise;
      }
   }

   private void invokeDisconnect(ChannelPromise promise) {
      if (this.invokeHandler()) {
         try {
            ((ChannelOutboundHandler)this.handler()).disconnect(this, promise);
         } catch (Throwable var3) {
            notifyOutboundHandlerException(var3, promise);
         }
      } else {
         this.disconnect(promise);
      }

   }

   public ChannelFuture close(final ChannelPromise promise) {
      if (this.isNotValidPromise(promise, false)) {
         return promise;
      } else {
         final AbstractChannelHandlerContext next = this.findContextOutbound();
         EventExecutor executor = next.executor();
         if (executor.inEventLoop()) {
            next.invokeClose(promise);
         } else {
            safeExecute(executor, new Runnable() {
               public void run() {
                  next.invokeClose(promise);
               }
            }, promise, (Object)null);
         }

         return promise;
      }
   }

   private void invokeClose(ChannelPromise promise) {
      if (this.invokeHandler()) {
         try {
            ((ChannelOutboundHandler)this.handler()).close(this, promise);
         } catch (Throwable var3) {
            notifyOutboundHandlerException(var3, promise);
         }
      } else {
         this.close(promise);
      }

   }

   public ChannelFuture deregister(final ChannelPromise promise) {
      if (this.isNotValidPromise(promise, false)) {
         return promise;
      } else {
         final AbstractChannelHandlerContext next = this.findContextOutbound();
         EventExecutor executor = next.executor();
         if (executor.inEventLoop()) {
            next.invokeDeregister(promise);
         } else {
            safeExecute(executor, new Runnable() {
               public void run() {
                  next.invokeDeregister(promise);
               }
            }, promise, (Object)null);
         }

         return promise;
      }
   }

   private void invokeDeregister(ChannelPromise promise) {
      if (this.invokeHandler()) {
         try {
            ((ChannelOutboundHandler)this.handler()).deregister(this, promise);
         } catch (Throwable var3) {
            notifyOutboundHandlerException(var3, promise);
         }
      } else {
         this.deregister(promise);
      }

   }

   public ChannelHandlerContext read() {
      final AbstractChannelHandlerContext next = this.findContextOutbound();
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeRead();
      } else {
         Runnable task = next.invokeReadTask;
         if (task == null) {
            next.invokeReadTask = task = new Runnable() {
               public void run() {
                  next.invokeRead();
               }
            };
         }

         executor.execute(task);
      }

      return this;
   }

   private void invokeRead() {
      if (this.invokeHandler()) {
         try {
            ((ChannelOutboundHandler)this.handler()).read(this);
         } catch (Throwable var2) {
            this.notifyHandlerException(var2);
         }
      } else {
         this.read();
      }

   }

   public ChannelFuture write(Object msg) {
      return this.write(msg, this.newPromise());
   }

   public ChannelFuture write(Object msg, ChannelPromise promise) {
      if (msg == null) {
         throw new NullPointerException("msg");
      } else {
         try {
            if (this.isNotValidPromise(promise, true)) {
               ReferenceCountUtil.release(msg);
               return promise;
            }
         } catch (RuntimeException var4) {
            ReferenceCountUtil.release(msg);
            throw var4;
         }

         this.write(msg, false, promise);
         return promise;
      }
   }

   private void invokeWrite(Object msg, ChannelPromise promise) {
      if (this.invokeHandler()) {
         this.invokeWrite0(msg, promise);
      } else {
         this.write(msg, promise);
      }

   }

   private void invokeWrite0(Object msg, ChannelPromise promise) {
      try {
         ((ChannelOutboundHandler)this.handler()).write(this, msg, promise);
      } catch (Throwable var4) {
         notifyOutboundHandlerException(var4, promise);
      }

   }

   public ChannelHandlerContext flush() {
      final AbstractChannelHandlerContext next = this.findContextOutbound();
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         next.invokeFlush();
      } else {
         Runnable task = next.invokeFlushTask;
         if (task == null) {
            next.invokeFlushTask = task = new Runnable() {
               public void run() {
                  next.invokeFlush();
               }
            };
         }

         safeExecute(executor, task, this.channel().voidPromise(), (Object)null);
      }

      return this;
   }

   private void invokeFlush() {
      if (this.invokeHandler()) {
         this.invokeFlush0();
      } else {
         this.flush();
      }

   }

   private void invokeFlush0() {
      try {
         ((ChannelOutboundHandler)this.handler()).flush(this);
      } catch (Throwable var2) {
         this.notifyHandlerException(var2);
      }

   }

   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
      if (msg == null) {
         throw new NullPointerException("msg");
      } else if (this.isNotValidPromise(promise, true)) {
         ReferenceCountUtil.release(msg);
         return promise;
      } else {
         this.write(msg, true, promise);
         return promise;
      }
   }

   private void invokeWriteAndFlush(Object msg, ChannelPromise promise) {
      if (this.invokeHandler()) {
         this.invokeWrite0(msg, promise);
         this.invokeFlush0();
      } else {
         this.writeAndFlush(msg, promise);
      }

   }

   private void write(Object msg, boolean flush, ChannelPromise promise) {
      AbstractChannelHandlerContext next = this.findContextOutbound();
      Object m = this.pipeline.touch(msg, next);
      EventExecutor executor = next.executor();
      if (executor.inEventLoop()) {
         if (flush) {
            next.invokeWriteAndFlush(m, promise);
         } else {
            next.invokeWrite(m, promise);
         }
      } else {
         Object task;
         if (flush) {
            task = AbstractChannelHandlerContext.WriteAndFlushTask.newInstance(next, m, promise);
         } else {
            task = AbstractChannelHandlerContext.WriteTask.newInstance(next, m, promise);
         }

         safeExecute(executor, (Runnable)task, promise, m);
      }

   }

   public ChannelFuture writeAndFlush(Object msg) {
      return this.writeAndFlush(msg, this.newPromise());
   }

   private static void notifyOutboundHandlerException(Throwable cause, ChannelPromise promise) {
      PromiseNotificationUtil.tryFailure(promise, cause, promise instanceof VoidChannelPromise ? null : logger);
   }

   private void notifyHandlerException(Throwable cause) {
      if (inExceptionCaught(cause)) {
         if (logger.isWarnEnabled()) {
            logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", cause);
         }

      } else {
         this.invokeExceptionCaught(cause);
      }
   }

   private static boolean inExceptionCaught(Throwable cause) {
      do {
         StackTraceElement[] trace = cause.getStackTrace();
         if (trace != null) {
            StackTraceElement[] var2 = trace;
            int var3 = trace.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               StackTraceElement t = var2[var4];
               if (t == null) {
                  break;
               }

               if ("exceptionCaught".equals(t.getMethodName())) {
                  return true;
               }
            }
         }

         cause = cause.getCause();
      } while(cause != null);

      return false;
   }

   public ChannelPromise newPromise() {
      return new DefaultChannelPromise(this.channel(), this.executor());
   }

   public ChannelProgressivePromise newProgressivePromise() {
      return new DefaultChannelProgressivePromise(this.channel(), this.executor());
   }

   public ChannelFuture newSucceededFuture() {
      ChannelFuture succeededFuture = this.succeededFuture;
      if (succeededFuture == null) {
         this.succeededFuture = (ChannelFuture)(succeededFuture = new SucceededChannelFuture(this.channel(), this.executor()));
      }

      return (ChannelFuture)succeededFuture;
   }

   public ChannelFuture newFailedFuture(Throwable cause) {
      return new FailedChannelFuture(this.channel(), this.executor(), cause);
   }

   private boolean isNotValidPromise(ChannelPromise promise, boolean allowVoidPromise) {
      if (promise == null) {
         throw new NullPointerException("promise");
      } else if (promise.isDone()) {
         if (promise.isCancelled()) {
            return true;
         } else {
            throw new IllegalArgumentException("promise already done: " + promise);
         }
      } else if (promise.channel() != this.channel()) {
         throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", promise.channel(), this.channel()));
      } else if (promise.getClass() == DefaultChannelPromise.class) {
         return false;
      } else if (!allowVoidPromise && promise instanceof VoidChannelPromise) {
         throw new IllegalArgumentException(StringUtil.simpleClassName(VoidChannelPromise.class) + " not allowed for this operation");
      } else if (promise instanceof AbstractChannel.CloseFuture) {
         throw new IllegalArgumentException(StringUtil.simpleClassName(AbstractChannel.CloseFuture.class) + " not allowed in a pipeline");
      } else {
         return false;
      }
   }

   private AbstractChannelHandlerContext findContextInbound() {
      AbstractChannelHandlerContext ctx = this;

      do {
         ctx = ctx.next;
      } while(!ctx.inbound);

      return ctx;
   }

   private AbstractChannelHandlerContext findContextOutbound() {
      AbstractChannelHandlerContext ctx = this;

      do {
         ctx = ctx.prev;
      } while(!ctx.outbound);

      return ctx;
   }

   public ChannelPromise voidPromise() {
      return this.channel().voidPromise();
   }

   final void setRemoved() {
      this.handlerState = 3;
   }

   final void setAddComplete() {
      int oldState;
      do {
         oldState = this.handlerState;
      } while(oldState != 3 && !HANDLER_STATE_UPDATER.compareAndSet(this, oldState, 2));

   }

   final void setAddPending() {
      boolean updated = HANDLER_STATE_UPDATER.compareAndSet(this, 0, 1);

      assert updated;

   }

   private boolean invokeHandler() {
      int handlerState = this.handlerState;
      return handlerState == 2 || !this.ordered && handlerState == 1;
   }

   public boolean isRemoved() {
      return this.handlerState == 3;
   }

   public Attribute attr(AttributeKey key) {
      return this.channel().attr(key);
   }

   public boolean hasAttr(AttributeKey key) {
      return this.channel().hasAttr(key);
   }

   private static void safeExecute(EventExecutor executor, Runnable runnable, ChannelPromise promise, Object msg) {
      try {
         executor.execute(runnable);
      } catch (Throwable var9) {
         Throwable cause = var9;

         try {
            promise.setFailure(cause);
         } finally {
            if (msg != null) {
               ReferenceCountUtil.release(msg);
            }

         }
      }

   }

   public String toHintString() {
      return '\'' + this.name + "' will handle the message from this point.";
   }

   public String toString() {
      return StringUtil.simpleClassName(ChannelHandlerContext.class) + '(' + this.name + ", " + this.channel() + ')';
   }

   static final class WriteAndFlushTask extends AbstractWriteTask {
      private static final Recycler RECYCLER = new Recycler() {
         protected WriteAndFlushTask newObject(Recycler.Handle handle) {
            return new WriteAndFlushTask(handle);
         }
      };

      private static WriteAndFlushTask newInstance(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
         WriteAndFlushTask task = (WriteAndFlushTask)RECYCLER.get();
         init(task, ctx, msg, promise);
         return task;
      }

      private WriteAndFlushTask(Recycler.Handle handle) {
         super(handle, null);
      }

      public void write(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
         super.write(ctx, msg, promise);
         ctx.invokeFlush();
      }

      // $FF: synthetic method
      WriteAndFlushTask(Recycler.Handle x0, Object x1) {
         this(x0);
      }
   }

   static final class WriteTask extends AbstractWriteTask implements SingleThreadEventLoop.NonWakeupRunnable {
      private static final Recycler RECYCLER = new Recycler() {
         protected WriteTask newObject(Recycler.Handle handle) {
            return new WriteTask(handle);
         }
      };

      private static WriteTask newInstance(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
         WriteTask task = (WriteTask)RECYCLER.get();
         init(task, ctx, msg, promise);
         return task;
      }

      private WriteTask(Recycler.Handle handle) {
         super(handle, null);
      }

      // $FF: synthetic method
      WriteTask(Recycler.Handle x0, Object x1) {
         this(x0);
      }
   }

   abstract static class AbstractWriteTask implements Runnable {
      private static final boolean ESTIMATE_TASK_SIZE_ON_SUBMIT = SystemPropertyUtil.getBoolean("org.python.netty.transport.estimateSizeOnSubmit", true);
      private static final int WRITE_TASK_OVERHEAD = SystemPropertyUtil.getInt("org.python.netty.transport.writeTaskSizeOverhead", 48);
      private final Recycler.Handle handle;
      private AbstractChannelHandlerContext ctx;
      private Object msg;
      private ChannelPromise promise;
      private int size;

      private AbstractWriteTask(Recycler.Handle handle) {
         this.handle = handle;
      }

      protected static void init(AbstractWriteTask task, AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
         task.ctx = ctx;
         task.msg = msg;
         task.promise = promise;
         if (ESTIMATE_TASK_SIZE_ON_SUBMIT) {
            ChannelOutboundBuffer buffer = ctx.channel().unsafe().outboundBuffer();
            if (buffer != null) {
               task.size = ctx.pipeline.estimatorHandle().size(msg) + WRITE_TASK_OVERHEAD;
               buffer.incrementPendingOutboundBytes((long)task.size);
            } else {
               task.size = 0;
            }
         } else {
            task.size = 0;
         }

      }

      public final void run() {
         try {
            ChannelOutboundBuffer buffer = this.ctx.channel().unsafe().outboundBuffer();
            if (ESTIMATE_TASK_SIZE_ON_SUBMIT && buffer != null) {
               buffer.decrementPendingOutboundBytes((long)this.size);
            }

            this.write(this.ctx, this.msg, this.promise);
         } finally {
            this.ctx = null;
            this.msg = null;
            this.promise = null;
            this.handle.recycle(this);
         }

      }

      protected void write(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
         ctx.invokeWrite(msg, promise);
      }

      // $FF: synthetic method
      AbstractWriteTask(Recycler.Handle x0, Object x1) {
         this(x0);
      }
   }
}
