package org.python.netty.channel.nio;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.AbstractChannel;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.ConnectTimeoutException;
import org.python.netty.channel.EventLoop;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.ReferenceCounted;
import org.python.netty.util.internal.ThrowableUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public abstract class AbstractNioChannel extends AbstractChannel {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
   private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException)ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractNioChannel.class, "doClose()");
   private final SelectableChannel ch;
   protected final int readInterestOp;
   volatile SelectionKey selectionKey;
   boolean readPending;
   private final Runnable clearReadPendingRunnable = new Runnable() {
      public void run() {
         AbstractNioChannel.this.clearReadPending0();
      }
   };
   private ChannelPromise connectPromise;
   private ScheduledFuture connectTimeoutFuture;
   private SocketAddress requestedRemoteAddress;

   protected AbstractNioChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
      super(parent);
      this.ch = ch;
      this.readInterestOp = readInterestOp;

      try {
         ch.configureBlocking(false);
      } catch (IOException var7) {
         try {
            ch.close();
         } catch (IOException var6) {
            if (logger.isWarnEnabled()) {
               logger.warn("Failed to close a partially initialized socket.", (Throwable)var6);
            }
         }

         throw new ChannelException("Failed to enter non-blocking mode.", var7);
      }
   }

   public boolean isOpen() {
      return this.ch.isOpen();
   }

   public NioUnsafe unsafe() {
      return (NioUnsafe)super.unsafe();
   }

   protected SelectableChannel javaChannel() {
      return this.ch;
   }

   public NioEventLoop eventLoop() {
      return (NioEventLoop)super.eventLoop();
   }

   protected SelectionKey selectionKey() {
      assert this.selectionKey != null;

      return this.selectionKey;
   }

   /** @deprecated */
   @Deprecated
   protected boolean isReadPending() {
      return this.readPending;
   }

   /** @deprecated */
   @Deprecated
   protected void setReadPending(final boolean readPending) {
      if (this.isRegistered()) {
         EventLoop eventLoop = this.eventLoop();
         if (eventLoop.inEventLoop()) {
            this.setReadPending0(readPending);
         } else {
            eventLoop.execute(new Runnable() {
               public void run() {
                  AbstractNioChannel.this.setReadPending0(readPending);
               }
            });
         }
      } else {
         this.readPending = readPending;
      }

   }

   protected final void clearReadPending() {
      if (this.isRegistered()) {
         EventLoop eventLoop = this.eventLoop();
         if (eventLoop.inEventLoop()) {
            this.clearReadPending0();
         } else {
            eventLoop.execute(this.clearReadPendingRunnable);
         }
      } else {
         this.readPending = false;
      }

   }

   private void setReadPending0(boolean readPending) {
      this.readPending = readPending;
      if (!readPending) {
         ((AbstractNioUnsafe)this.unsafe()).removeReadOp();
      }

   }

   private void clearReadPending0() {
      this.readPending = false;
      ((AbstractNioUnsafe)this.unsafe()).removeReadOp();
   }

   protected boolean isCompatible(EventLoop loop) {
      return loop instanceof NioEventLoop;
   }

   protected void doRegister() throws Exception {
      boolean selected = false;

      while(true) {
         try {
            this.selectionKey = this.javaChannel().register(this.eventLoop().unwrappedSelector(), 0, this);
            return;
         } catch (CancelledKeyException var3) {
            if (selected) {
               throw var3;
            }

            this.eventLoop().selectNow();
            selected = true;
         }
      }
   }

   protected void doDeregister() throws Exception {
      this.eventLoop().cancel(this.selectionKey());
   }

   protected void doBeginRead() throws Exception {
      SelectionKey selectionKey = this.selectionKey;
      if (selectionKey.isValid()) {
         this.readPending = true;
         int interestOps = selectionKey.interestOps();
         if ((interestOps & this.readInterestOp) == 0) {
            selectionKey.interestOps(interestOps | this.readInterestOp);
         }

      }
   }

   protected abstract boolean doConnect(SocketAddress var1, SocketAddress var2) throws Exception;

   protected abstract void doFinishConnect() throws Exception;

   protected final ByteBuf newDirectBuffer(ByteBuf buf) {
      int readableBytes = buf.readableBytes();
      if (readableBytes == 0) {
         ReferenceCountUtil.safeRelease(buf);
         return Unpooled.EMPTY_BUFFER;
      } else {
         ByteBufAllocator alloc = this.alloc();
         ByteBuf directBuf;
         if (alloc.isDirectBufferPooled()) {
            directBuf = alloc.directBuffer(readableBytes);
            directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(buf);
            return directBuf;
         } else {
            directBuf = ByteBufUtil.threadLocalDirectBuffer();
            if (directBuf != null) {
               directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
               ReferenceCountUtil.safeRelease(buf);
               return directBuf;
            } else {
               return buf;
            }
         }
      }
   }

   protected final ByteBuf newDirectBuffer(ReferenceCounted holder, ByteBuf buf) {
      int readableBytes = buf.readableBytes();
      if (readableBytes == 0) {
         ReferenceCountUtil.safeRelease(holder);
         return Unpooled.EMPTY_BUFFER;
      } else {
         ByteBufAllocator alloc = this.alloc();
         ByteBuf directBuf;
         if (alloc.isDirectBufferPooled()) {
            directBuf = alloc.directBuffer(readableBytes);
            directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(holder);
            return directBuf;
         } else {
            directBuf = ByteBufUtil.threadLocalDirectBuffer();
            if (directBuf != null) {
               directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
               ReferenceCountUtil.safeRelease(holder);
               return directBuf;
            } else {
               if (holder != buf) {
                  buf.retain();
                  ReferenceCountUtil.safeRelease(holder);
               }

               return buf;
            }
         }
      }
   }

   protected void doClose() throws Exception {
      ChannelPromise promise = this.connectPromise;
      if (promise != null) {
         promise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
         this.connectPromise = null;
      }

      ScheduledFuture future = this.connectTimeoutFuture;
      if (future != null) {
         future.cancel(false);
         this.connectTimeoutFuture = null;
      }

   }

   protected abstract class AbstractNioUnsafe extends AbstractChannel.AbstractUnsafe implements NioUnsafe {
      protected AbstractNioUnsafe() {
         super();
      }

      protected final void removeReadOp() {
         SelectionKey key = AbstractNioChannel.this.selectionKey();
         if (key.isValid()) {
            int interestOps = key.interestOps();
            if ((interestOps & AbstractNioChannel.this.readInterestOp) != 0) {
               key.interestOps(interestOps & ~AbstractNioChannel.this.readInterestOp);
            }

         }
      }

      public final SelectableChannel ch() {
         return AbstractNioChannel.this.javaChannel();
      }

      public final void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
         if (promise.setUncancellable() && this.ensureOpen(promise)) {
            try {
               if (AbstractNioChannel.this.connectPromise != null) {
                  throw new ConnectionPendingException();
               }

               boolean wasActive = AbstractNioChannel.this.isActive();
               if (AbstractNioChannel.this.doConnect(remoteAddress, localAddress)) {
                  this.fulfillConnectPromise(promise, wasActive);
               } else {
                  AbstractNioChannel.this.connectPromise = promise;
                  AbstractNioChannel.this.requestedRemoteAddress = remoteAddress;
                  int connectTimeoutMillis = AbstractNioChannel.this.config().getConnectTimeoutMillis();
                  if (connectTimeoutMillis > 0) {
                     AbstractNioChannel.this.connectTimeoutFuture = AbstractNioChannel.this.eventLoop().schedule(new Runnable() {
                        public void run() {
                           ChannelPromise connectPromise = AbstractNioChannel.this.connectPromise;
                           ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
                           if (connectPromise != null && connectPromise.tryFailure(cause)) {
                              AbstractNioUnsafe.this.close(AbstractNioUnsafe.this.voidPromise());
                           }

                        }
                     }, (long)connectTimeoutMillis, TimeUnit.MILLISECONDS);
                  }

                  promise.addListener(new ChannelFutureListener() {
                     public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isCancelled()) {
                           if (AbstractNioChannel.this.connectTimeoutFuture != null) {
                              AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
                           }

                           AbstractNioChannel.this.connectPromise = null;
                           AbstractNioUnsafe.this.close(AbstractNioUnsafe.this.voidPromise());
                        }

                     }
                  });
               }
            } catch (Throwable var6) {
               promise.tryFailure(this.annotateConnectException(var6, remoteAddress));
               this.closeIfClosed();
            }

         }
      }

      private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
         if (promise != null) {
            boolean active = AbstractNioChannel.this.isActive();
            boolean promiseSet = promise.trySuccess();
            if (!wasActive && active) {
               AbstractNioChannel.this.pipeline().fireChannelActive();
            }

            if (!promiseSet) {
               this.close(this.voidPromise());
            }

         }
      }

      private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
         if (promise != null) {
            promise.tryFailure(cause);
            this.closeIfClosed();
         }
      }

      public final void finishConnect() {
         assert AbstractNioChannel.this.eventLoop().inEventLoop();

         try {
            boolean wasActive = AbstractNioChannel.this.isActive();
            AbstractNioChannel.this.doFinishConnect();
            this.fulfillConnectPromise(AbstractNioChannel.this.connectPromise, wasActive);
         } catch (Throwable var5) {
            this.fulfillConnectPromise(AbstractNioChannel.this.connectPromise, this.annotateConnectException(var5, AbstractNioChannel.this.requestedRemoteAddress));
         } finally {
            if (AbstractNioChannel.this.connectTimeoutFuture != null) {
               AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
            }

            AbstractNioChannel.this.connectPromise = null;
         }

      }

      protected final void flush0() {
         if (!this.isFlushPending()) {
            super.flush0();
         }
      }

      public final void forceFlush() {
         super.flush0();
      }

      private boolean isFlushPending() {
         SelectionKey selectionKey = AbstractNioChannel.this.selectionKey();
         return selectionKey.isValid() && (selectionKey.interestOps() & 4) != 0;
      }
   }

   public interface NioUnsafe extends Channel.Unsafe {
      SelectableChannel ch();

      void finishConnect();

      void read();

      void forceFlush();
   }
}
