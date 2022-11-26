package org.python.netty.channel.embedded;

import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import org.python.netty.channel.AbstractChannel;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelId;
import org.python.netty.channel.ChannelInitializer;
import org.python.netty.channel.ChannelMetadata;
import org.python.netty.channel.ChannelOutboundBuffer;
import org.python.netty.channel.ChannelPipeline;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.DefaultChannelConfig;
import org.python.netty.channel.DefaultChannelPipeline;
import org.python.netty.channel.EventLoop;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.RecyclableArrayList;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class EmbeddedChannel extends AbstractChannel {
   private static final SocketAddress LOCAL_ADDRESS = new EmbeddedSocketAddress();
   private static final SocketAddress REMOTE_ADDRESS = new EmbeddedSocketAddress();
   private static final ChannelHandler[] EMPTY_HANDLERS = new ChannelHandler[0];
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
   private static final ChannelMetadata METADATA_NO_DISCONNECT = new ChannelMetadata(false);
   private static final ChannelMetadata METADATA_DISCONNECT = new ChannelMetadata(true);
   private final EmbeddedEventLoop loop;
   private final ChannelFutureListener recordExceptionListener;
   private final ChannelMetadata metadata;
   private final ChannelConfig config;
   private Queue inboundMessages;
   private Queue outboundMessages;
   private Throwable lastException;
   private State state;

   public EmbeddedChannel() {
      this(EMPTY_HANDLERS);
   }

   public EmbeddedChannel(ChannelId channelId) {
      this(channelId, EMPTY_HANDLERS);
   }

   public EmbeddedChannel(ChannelHandler... handlers) {
      this(EmbeddedChannelId.INSTANCE, handlers);
   }

   public EmbeddedChannel(boolean hasDisconnect, ChannelHandler... handlers) {
      this(EmbeddedChannelId.INSTANCE, hasDisconnect, handlers);
   }

   public EmbeddedChannel(ChannelId channelId, ChannelHandler... handlers) {
      this(channelId, false, handlers);
   }

   public EmbeddedChannel(ChannelId channelId, boolean hasDisconnect, ChannelHandler... handlers) {
      super((Channel)null, channelId);
      this.loop = new EmbeddedEventLoop();
      this.recordExceptionListener = new ChannelFutureListener() {
         public void operationComplete(ChannelFuture future) throws Exception {
            EmbeddedChannel.this.recordException(future);
         }
      };
      this.metadata = metadata(hasDisconnect);
      this.config = new DefaultChannelConfig(this);
      this.setup(handlers);
   }

   public EmbeddedChannel(ChannelId channelId, boolean hasDisconnect, ChannelConfig config, ChannelHandler... handlers) {
      super((Channel)null, channelId);
      this.loop = new EmbeddedEventLoop();
      this.recordExceptionListener = new ChannelFutureListener() {
         public void operationComplete(ChannelFuture future) throws Exception {
            EmbeddedChannel.this.recordException(future);
         }
      };
      this.metadata = metadata(hasDisconnect);
      this.config = (ChannelConfig)ObjectUtil.checkNotNull(config, "config");
      this.setup(handlers);
   }

   private static ChannelMetadata metadata(boolean hasDisconnect) {
      return hasDisconnect ? METADATA_DISCONNECT : METADATA_NO_DISCONNECT;
   }

   private void setup(final ChannelHandler... handlers) {
      ObjectUtil.checkNotNull(handlers, "handlers");
      ChannelPipeline p = this.pipeline();
      p.addLast(new ChannelInitializer() {
         protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            ChannelHandler[] var3 = handlers;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ChannelHandler h = var3[var5];
               if (h == null) {
                  break;
               }

               pipeline.addLast(h);
            }

         }
      });
      ChannelFuture future = this.loop.register((Channel)this);

      assert future.isDone();

   }

   protected final DefaultChannelPipeline newChannelPipeline() {
      return new EmbeddedChannelPipeline(this);
   }

   public ChannelMetadata metadata() {
      return this.metadata;
   }

   public ChannelConfig config() {
      return this.config;
   }

   public boolean isOpen() {
      return this.state != EmbeddedChannel.State.CLOSED;
   }

   public boolean isActive() {
      return this.state == EmbeddedChannel.State.ACTIVE;
   }

   public Queue inboundMessages() {
      if (this.inboundMessages == null) {
         this.inboundMessages = new ArrayDeque();
      }

      return this.inboundMessages;
   }

   /** @deprecated */
   @Deprecated
   public Queue lastInboundBuffer() {
      return this.inboundMessages();
   }

   public Queue outboundMessages() {
      if (this.outboundMessages == null) {
         this.outboundMessages = new ArrayDeque();
      }

      return this.outboundMessages;
   }

   /** @deprecated */
   @Deprecated
   public Queue lastOutboundBuffer() {
      return this.outboundMessages();
   }

   public Object readInbound() {
      return poll(this.inboundMessages);
   }

   public Object readOutbound() {
      return poll(this.outboundMessages);
   }

   public boolean writeInbound(Object... msgs) {
      this.ensureOpen();
      if (msgs.length == 0) {
         return isNotEmpty(this.inboundMessages);
      } else {
         ChannelPipeline p = this.pipeline();
         Object[] var3 = msgs;
         int var4 = msgs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object m = var3[var5];
            p.fireChannelRead(m);
         }

         this.flushInbound(false, this.voidPromise());
         return isNotEmpty(this.inboundMessages);
      }
   }

   public ChannelFuture writeOneInbound(Object msg) {
      return this.writeOneInbound(msg, this.newPromise());
   }

   public ChannelFuture writeOneInbound(Object msg, ChannelPromise promise) {
      if (this.checkOpen(true)) {
         this.pipeline().fireChannelRead(msg);
      }

      return this.checkException(promise);
   }

   public EmbeddedChannel flushInbound() {
      this.flushInbound(true, this.voidPromise());
      return this;
   }

   private ChannelFuture flushInbound(boolean recordException, ChannelPromise promise) {
      if (this.checkOpen(recordException)) {
         this.pipeline().fireChannelReadComplete();
         this.runPendingTasks();
      }

      return this.checkException(promise);
   }

   public boolean writeOutbound(Object... msgs) {
      this.ensureOpen();
      if (msgs.length == 0) {
         return isNotEmpty(this.outboundMessages);
      } else {
         RecyclableArrayList futures = RecyclableArrayList.newInstance(msgs.length);

         try {
            Object[] var3 = msgs;
            int i = msgs.length;

            for(int var5 = 0; var5 < i; ++var5) {
               Object m = var3[var5];
               if (m == null) {
                  break;
               }

               futures.add(this.write(m));
            }

            this.flushOutbound0();
            int size = futures.size();

            for(i = 0; i < size; ++i) {
               ChannelFuture future = (ChannelFuture)futures.get(i);
               if (future.isDone()) {
                  this.recordException(future);
               } else {
                  future.addListener(this.recordExceptionListener);
               }
            }

            this.checkException();
            boolean var11 = isNotEmpty(this.outboundMessages);
            return var11;
         } finally {
            futures.recycle();
         }
      }
   }

   public ChannelFuture writeOneOutbound(Object msg) {
      return this.writeOneOutbound(msg, this.newPromise());
   }

   public ChannelFuture writeOneOutbound(Object msg, ChannelPromise promise) {
      return this.checkOpen(true) ? this.write(msg, promise) : this.checkException(promise);
   }

   public EmbeddedChannel flushOutbound() {
      if (this.checkOpen(true)) {
         this.flushOutbound0();
      }

      this.checkException(this.voidPromise());
      return this;
   }

   private void flushOutbound0() {
      this.runPendingTasks();
      this.flush();
   }

   public boolean finish() {
      return this.finish(false);
   }

   public boolean finishAndReleaseAll() {
      return this.finish(true);
   }

   private boolean finish(boolean releaseAll) {
      this.close();

      boolean var2;
      try {
         this.checkException();
         var2 = isNotEmpty(this.inboundMessages) || isNotEmpty(this.outboundMessages);
      } finally {
         if (releaseAll) {
            releaseAll(this.inboundMessages);
            releaseAll(this.outboundMessages);
         }

      }

      return var2;
   }

   public boolean releaseInbound() {
      return releaseAll(this.inboundMessages);
   }

   public boolean releaseOutbound() {
      return releaseAll(this.outboundMessages);
   }

   private static boolean releaseAll(Queue queue) {
      if (!isNotEmpty(queue)) {
         return false;
      } else {
         while(true) {
            Object msg = queue.poll();
            if (msg == null) {
               return true;
            }

            ReferenceCountUtil.release(msg);
         }
      }
   }

   private void finishPendingTasks(boolean cancel) {
      this.runPendingTasks();
      if (cancel) {
         this.loop.cancelScheduledTasks();
      }

   }

   public final ChannelFuture close() {
      return this.close(this.newPromise());
   }

   public final ChannelFuture disconnect() {
      return this.disconnect(this.newPromise());
   }

   public final ChannelFuture close(ChannelPromise promise) {
      this.runPendingTasks();
      ChannelFuture future = super.close(promise);
      this.finishPendingTasks(true);
      return future;
   }

   public final ChannelFuture disconnect(ChannelPromise promise) {
      ChannelFuture future = super.disconnect(promise);
      this.finishPendingTasks(!this.metadata.hasDisconnect());
      return future;
   }

   private static boolean isNotEmpty(Queue queue) {
      return queue != null && !queue.isEmpty();
   }

   private static Object poll(Queue queue) {
      return queue != null ? queue.poll() : null;
   }

   public void runPendingTasks() {
      try {
         this.loop.runTasks();
      } catch (Exception var3) {
         this.recordException((Throwable)var3);
      }

      try {
         this.loop.runScheduledTasks();
      } catch (Exception var2) {
         this.recordException((Throwable)var2);
      }

   }

   public long runScheduledPendingTasks() {
      try {
         return this.loop.runScheduledTasks();
      } catch (Exception var2) {
         this.recordException((Throwable)var2);
         return this.loop.nextScheduledTask();
      }
   }

   private void recordException(ChannelFuture future) {
      if (!future.isSuccess()) {
         this.recordException(future.cause());
      }

   }

   private void recordException(Throwable cause) {
      if (this.lastException == null) {
         this.lastException = cause;
      } else {
         logger.warn("More than one exception was raised. Will report only the first one and log others.", cause);
      }

   }

   private ChannelFuture checkException(ChannelPromise promise) {
      Throwable t = this.lastException;
      if (t != null) {
         this.lastException = null;
         if (promise.isVoid()) {
            PlatformDependent.throwException(t);
         }

         return promise.setFailure(t);
      } else {
         return promise.setSuccess();
      }
   }

   public void checkException() {
      this.checkException(this.voidPromise());
   }

   private boolean checkOpen(boolean recordException) {
      if (!this.isOpen()) {
         if (recordException) {
            this.recordException((Throwable)(new ClosedChannelException()));
         }

         return false;
      } else {
         return true;
      }
   }

   protected final void ensureOpen() {
      if (!this.checkOpen(true)) {
         this.checkException();
      }

   }

   protected boolean isCompatible(EventLoop loop) {
      return loop instanceof EmbeddedEventLoop;
   }

   protected SocketAddress localAddress0() {
      return this.isActive() ? LOCAL_ADDRESS : null;
   }

   protected SocketAddress remoteAddress0() {
      return this.isActive() ? REMOTE_ADDRESS : null;
   }

   protected void doRegister() throws Exception {
      this.state = EmbeddedChannel.State.ACTIVE;
   }

   protected void doBind(SocketAddress localAddress) throws Exception {
   }

   protected void doDisconnect() throws Exception {
      if (!this.metadata.hasDisconnect()) {
         this.doClose();
      }

   }

   protected void doClose() throws Exception {
      this.state = EmbeddedChannel.State.CLOSED;
   }

   protected void doBeginRead() throws Exception {
   }

   protected AbstractChannel.AbstractUnsafe newUnsafe() {
      return new DefaultUnsafe();
   }

   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      while(true) {
         Object msg = in.current();
         if (msg == null) {
            return;
         }

         ReferenceCountUtil.retain(msg);
         this.handleOutboundMessage(msg);
         in.remove();
      }
   }

   protected void handleOutboundMessage(Object msg) {
      this.outboundMessages().add(msg);
   }

   protected void handleInboundMessage(Object msg) {
      this.inboundMessages().add(msg);
   }

   private final class EmbeddedChannelPipeline extends DefaultChannelPipeline {
      public EmbeddedChannelPipeline(EmbeddedChannel channel) {
         super(channel);
      }

      protected void onUnhandledInboundException(Throwable cause) {
         EmbeddedChannel.this.recordException(cause);
      }

      protected void onUnhandledInboundMessage(Object msg) {
         EmbeddedChannel.this.handleInboundMessage(msg);
      }
   }

   private class DefaultUnsafe extends AbstractChannel.AbstractUnsafe {
      private DefaultUnsafe() {
         super();
      }

      public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
         this.safeSetSuccess(promise);
      }

      // $FF: synthetic method
      DefaultUnsafe(Object x1) {
         this();
      }
   }

   private static enum State {
      OPEN,
      ACTIVE,
      CLOSED;
   }
}
