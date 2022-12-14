package org.python.netty.channel;

import java.net.SocketAddress;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.Attribute;
import org.python.netty.util.AttributeKey;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.internal.ThrowableUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class CombinedChannelDuplexHandler extends ChannelDuplexHandler {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CombinedChannelDuplexHandler.class);
   private DelegatingChannelHandlerContext inboundCtx;
   private DelegatingChannelHandlerContext outboundCtx;
   private volatile boolean handlerAdded;
   private ChannelInboundHandler inboundHandler;
   private ChannelOutboundHandler outboundHandler;

   protected CombinedChannelDuplexHandler() {
      this.ensureNotSharable();
   }

   public CombinedChannelDuplexHandler(ChannelInboundHandler inboundHandler, ChannelOutboundHandler outboundHandler) {
      this.ensureNotSharable();
      this.init(inboundHandler, outboundHandler);
   }

   protected final void init(ChannelInboundHandler inboundHandler, ChannelOutboundHandler outboundHandler) {
      this.validate(inboundHandler, outboundHandler);
      this.inboundHandler = inboundHandler;
      this.outboundHandler = outboundHandler;
   }

   private void validate(ChannelInboundHandler inboundHandler, ChannelOutboundHandler outboundHandler) {
      if (this.inboundHandler != null) {
         throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
      } else if (inboundHandler == null) {
         throw new NullPointerException("inboundHandler");
      } else if (outboundHandler == null) {
         throw new NullPointerException("outboundHandler");
      } else if (inboundHandler instanceof ChannelOutboundHandler) {
         throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
      } else if (outboundHandler instanceof ChannelInboundHandler) {
         throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
      }
   }

   protected final ChannelInboundHandler inboundHandler() {
      return this.inboundHandler;
   }

   protected final ChannelOutboundHandler outboundHandler() {
      return this.outboundHandler;
   }

   private void checkAdded() {
      if (!this.handlerAdded) {
         throw new IllegalStateException("handler not added to pipeline yet");
      }
   }

   public final void removeInboundHandler() {
      this.checkAdded();
      this.inboundCtx.remove();
   }

   public final void removeOutboundHandler() {
      this.checkAdded();
      this.outboundCtx.remove();
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      if (this.inboundHandler == null) {
         throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
      } else {
         this.outboundCtx = new DelegatingChannelHandlerContext(ctx, this.outboundHandler);
         this.inboundCtx = new DelegatingChannelHandlerContext(ctx, this.inboundHandler) {
            public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
               if (!CombinedChannelDuplexHandler.this.outboundCtx.removed) {
                  try {
                     CombinedChannelDuplexHandler.this.outboundHandler.exceptionCaught(CombinedChannelDuplexHandler.this.outboundCtx, cause);
                  } catch (Throwable var3) {
                     if (CombinedChannelDuplexHandler.logger.isDebugEnabled()) {
                        CombinedChannelDuplexHandler.logger.debug("An exception {}was thrown by a user handler's exceptionCaught() method while handling the following exception:", ThrowableUtil.stackTraceToString(var3), cause);
                     } else if (CombinedChannelDuplexHandler.logger.isWarnEnabled()) {
                        CombinedChannelDuplexHandler.logger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", var3, cause);
                     }
                  }
               } else {
                  super.fireExceptionCaught(cause);
               }

               return this;
            }
         };
         this.handlerAdded = true;

         try {
            this.inboundHandler.handlerAdded(this.inboundCtx);
         } finally {
            this.outboundHandler.handlerAdded(this.outboundCtx);
         }

      }
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      try {
         this.inboundCtx.remove();
      } finally {
         this.outboundCtx.remove();
      }

   }

   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.channelRegistered(this.inboundCtx);
      } else {
         this.inboundCtx.fireChannelRegistered();
      }

   }

   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.channelUnregistered(this.inboundCtx);
      } else {
         this.inboundCtx.fireChannelUnregistered();
      }

   }

   public void channelActive(ChannelHandlerContext ctx) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.channelActive(this.inboundCtx);
      } else {
         this.inboundCtx.fireChannelActive();
      }

   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.channelInactive(this.inboundCtx);
      } else {
         this.inboundCtx.fireChannelInactive();
      }

   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.exceptionCaught(this.inboundCtx, cause);
      } else {
         this.inboundCtx.fireExceptionCaught(cause);
      }

   }

   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.userEventTriggered(this.inboundCtx, evt);
      } else {
         this.inboundCtx.fireUserEventTriggered(evt);
      }

   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.channelRead(this.inboundCtx, msg);
      } else {
         this.inboundCtx.fireChannelRead(msg);
      }

   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.channelReadComplete(this.inboundCtx);
      } else {
         this.inboundCtx.fireChannelReadComplete();
      }

   }

   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
      assert ctx == this.inboundCtx.ctx;

      if (!this.inboundCtx.removed) {
         this.inboundHandler.channelWritabilityChanged(this.inboundCtx);
      } else {
         this.inboundCtx.fireChannelWritabilityChanged();
      }

   }

   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      assert ctx == this.outboundCtx.ctx;

      if (!this.outboundCtx.removed) {
         this.outboundHandler.bind(this.outboundCtx, localAddress, promise);
      } else {
         this.outboundCtx.bind(localAddress, promise);
      }

   }

   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      assert ctx == this.outboundCtx.ctx;

      if (!this.outboundCtx.removed) {
         this.outboundHandler.connect(this.outboundCtx, remoteAddress, localAddress, promise);
      } else {
         this.outboundCtx.connect(localAddress, promise);
      }

   }

   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      assert ctx == this.outboundCtx.ctx;

      if (!this.outboundCtx.removed) {
         this.outboundHandler.disconnect(this.outboundCtx, promise);
      } else {
         this.outboundCtx.disconnect(promise);
      }

   }

   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      assert ctx == this.outboundCtx.ctx;

      if (!this.outboundCtx.removed) {
         this.outboundHandler.close(this.outboundCtx, promise);
      } else {
         this.outboundCtx.close(promise);
      }

   }

   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      assert ctx == this.outboundCtx.ctx;

      if (!this.outboundCtx.removed) {
         this.outboundHandler.deregister(this.outboundCtx, promise);
      } else {
         this.outboundCtx.deregister(promise);
      }

   }

   public void read(ChannelHandlerContext ctx) throws Exception {
      assert ctx == this.outboundCtx.ctx;

      if (!this.outboundCtx.removed) {
         this.outboundHandler.read(this.outboundCtx);
      } else {
         this.outboundCtx.read();
      }

   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      assert ctx == this.outboundCtx.ctx;

      if (!this.outboundCtx.removed) {
         this.outboundHandler.write(this.outboundCtx, msg, promise);
      } else {
         this.outboundCtx.write(msg, promise);
      }

   }

   public void flush(ChannelHandlerContext ctx) throws Exception {
      assert ctx == this.outboundCtx.ctx;

      if (!this.outboundCtx.removed) {
         this.outboundHandler.flush(this.outboundCtx);
      } else {
         this.outboundCtx.flush();
      }

   }

   private static class DelegatingChannelHandlerContext implements ChannelHandlerContext {
      private final ChannelHandlerContext ctx;
      private final ChannelHandler handler;
      boolean removed;

      DelegatingChannelHandlerContext(ChannelHandlerContext ctx, ChannelHandler handler) {
         this.ctx = ctx;
         this.handler = handler;
      }

      public Channel channel() {
         return this.ctx.channel();
      }

      public EventExecutor executor() {
         return this.ctx.executor();
      }

      public String name() {
         return this.ctx.name();
      }

      public ChannelHandler handler() {
         return this.ctx.handler();
      }

      public boolean isRemoved() {
         return this.removed || this.ctx.isRemoved();
      }

      public ChannelHandlerContext fireChannelRegistered() {
         this.ctx.fireChannelRegistered();
         return this;
      }

      public ChannelHandlerContext fireChannelUnregistered() {
         this.ctx.fireChannelUnregistered();
         return this;
      }

      public ChannelHandlerContext fireChannelActive() {
         this.ctx.fireChannelActive();
         return this;
      }

      public ChannelHandlerContext fireChannelInactive() {
         this.ctx.fireChannelInactive();
         return this;
      }

      public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
         this.ctx.fireExceptionCaught(cause);
         return this;
      }

      public ChannelHandlerContext fireUserEventTriggered(Object event) {
         this.ctx.fireUserEventTriggered(event);
         return this;
      }

      public ChannelHandlerContext fireChannelRead(Object msg) {
         this.ctx.fireChannelRead(msg);
         return this;
      }

      public ChannelHandlerContext fireChannelReadComplete() {
         this.ctx.fireChannelReadComplete();
         return this;
      }

      public ChannelHandlerContext fireChannelWritabilityChanged() {
         this.ctx.fireChannelWritabilityChanged();
         return this;
      }

      public ChannelFuture bind(SocketAddress localAddress) {
         return this.ctx.bind(localAddress);
      }

      public ChannelFuture connect(SocketAddress remoteAddress) {
         return this.ctx.connect(remoteAddress);
      }

      public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
         return this.ctx.connect(remoteAddress, localAddress);
      }

      public ChannelFuture disconnect() {
         return this.ctx.disconnect();
      }

      public ChannelFuture close() {
         return this.ctx.close();
      }

      public ChannelFuture deregister() {
         return this.ctx.deregister();
      }

      public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
         return this.ctx.bind(localAddress, promise);
      }

      public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
         return this.ctx.connect(remoteAddress, promise);
      }

      public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
         return this.ctx.connect(remoteAddress, localAddress, promise);
      }

      public ChannelFuture disconnect(ChannelPromise promise) {
         return this.ctx.disconnect(promise);
      }

      public ChannelFuture close(ChannelPromise promise) {
         return this.ctx.close(promise);
      }

      public ChannelFuture deregister(ChannelPromise promise) {
         return this.ctx.deregister(promise);
      }

      public ChannelHandlerContext read() {
         this.ctx.read();
         return this;
      }

      public ChannelFuture write(Object msg) {
         return this.ctx.write(msg);
      }

      public ChannelFuture write(Object msg, ChannelPromise promise) {
         return this.ctx.write(msg, promise);
      }

      public ChannelHandlerContext flush() {
         this.ctx.flush();
         return this;
      }

      public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
         return this.ctx.writeAndFlush(msg, promise);
      }

      public ChannelFuture writeAndFlush(Object msg) {
         return this.ctx.writeAndFlush(msg);
      }

      public ChannelPipeline pipeline() {
         return this.ctx.pipeline();
      }

      public ByteBufAllocator alloc() {
         return this.ctx.alloc();
      }

      public ChannelPromise newPromise() {
         return this.ctx.newPromise();
      }

      public ChannelProgressivePromise newProgressivePromise() {
         return this.ctx.newProgressivePromise();
      }

      public ChannelFuture newSucceededFuture() {
         return this.ctx.newSucceededFuture();
      }

      public ChannelFuture newFailedFuture(Throwable cause) {
         return this.ctx.newFailedFuture(cause);
      }

      public ChannelPromise voidPromise() {
         return this.ctx.voidPromise();
      }

      public Attribute attr(AttributeKey key) {
         return this.ctx.attr(key);
      }

      public boolean hasAttr(AttributeKey key) {
         return this.ctx.hasAttr(key);
      }

      final void remove() {
         EventExecutor executor = this.executor();
         if (executor.inEventLoop()) {
            this.remove0();
         } else {
            executor.execute(new Runnable() {
               public void run() {
                  DelegatingChannelHandlerContext.this.remove0();
               }
            });
         }

      }

      private void remove0() {
         if (!this.removed) {
            this.removed = true;

            try {
               this.handler.handlerRemoved(this);
            } catch (Throwable var2) {
               this.fireExceptionCaught(new ChannelPipelineException(this.handler.getClass().getName() + ".handlerRemoved() has thrown an exception.", var2));
            }
         }

      }
   }
}
