package org.python.netty.channel.local;

import java.net.ConnectException;
import java.net.SocketAddress;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.python.netty.channel.AbstractChannel;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.ChannelMetadata;
import org.python.netty.channel.ChannelOutboundBuffer;
import org.python.netty.channel.ChannelPipeline;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.DefaultChannelConfig;
import org.python.netty.channel.EventLoop;
import org.python.netty.channel.SingleThreadEventLoop;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.SingleThreadEventExecutor;
import org.python.netty.util.internal.InternalThreadLocalMap;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.ThrowableUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class LocalChannel extends AbstractChannel {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(LocalChannel.class);
   private static final AtomicReferenceFieldUpdater FINISH_READ_FUTURE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(LocalChannel.class, Future.class, "finishReadFuture");
   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
   private static final int MAX_READER_STACK_DEPTH = 8;
   private static final ClosedChannelException DO_WRITE_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException)ThrowableUtil.unknownStackTrace(new ClosedChannelException(), LocalChannel.class, "doWrite(...)");
   private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException)ThrowableUtil.unknownStackTrace(new ClosedChannelException(), LocalChannel.class, "doClose()");
   private final ChannelConfig config = new DefaultChannelConfig(this);
   final Queue inboundBuffer = PlatformDependent.newSpscQueue();
   private final Runnable readTask = new Runnable() {
      public void run() {
         ChannelPipeline pipeline = LocalChannel.this.pipeline();

         while(true) {
            Object m = LocalChannel.this.inboundBuffer.poll();
            if (m == null) {
               pipeline.fireChannelReadComplete();
               return;
            }

            pipeline.fireChannelRead(m);
         }
      }
   };
   private final Runnable shutdownHook = new Runnable() {
      public void run() {
         LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
      }
   };
   private volatile State state;
   private volatile LocalChannel peer;
   private volatile LocalAddress localAddress;
   private volatile LocalAddress remoteAddress;
   private volatile ChannelPromise connectPromise;
   private volatile boolean readInProgress;
   private volatile boolean registerInProgress;
   private volatile boolean writeInProgress;
   private volatile Future finishReadFuture;

   public LocalChannel() {
      super((Channel)null);
      this.config().setAllocator(new PreferHeapByteBufAllocator(this.config.getAllocator()));
   }

   protected LocalChannel(LocalServerChannel parent, LocalChannel peer) {
      super(parent);
      this.config().setAllocator(new PreferHeapByteBufAllocator(this.config.getAllocator()));
      this.peer = peer;
      this.localAddress = parent.localAddress();
      this.remoteAddress = peer.localAddress();
   }

   public ChannelMetadata metadata() {
      return METADATA;
   }

   public ChannelConfig config() {
      return this.config;
   }

   public LocalServerChannel parent() {
      return (LocalServerChannel)super.parent();
   }

   public LocalAddress localAddress() {
      return (LocalAddress)super.localAddress();
   }

   public LocalAddress remoteAddress() {
      return (LocalAddress)super.remoteAddress();
   }

   public boolean isOpen() {
      return this.state != LocalChannel.State.CLOSED;
   }

   public boolean isActive() {
      return this.state == LocalChannel.State.CONNECTED;
   }

   protected AbstractChannel.AbstractUnsafe newUnsafe() {
      return new LocalUnsafe();
   }

   protected boolean isCompatible(EventLoop loop) {
      return loop instanceof SingleThreadEventLoop;
   }

   protected SocketAddress localAddress0() {
      return this.localAddress;
   }

   protected SocketAddress remoteAddress0() {
      return this.remoteAddress;
   }

   protected void doRegister() throws Exception {
      if (this.peer != null && this.parent() != null) {
         final LocalChannel peer = this.peer;
         this.registerInProgress = true;
         this.state = LocalChannel.State.CONNECTED;
         peer.remoteAddress = this.parent() == null ? null : this.parent().localAddress();
         peer.state = LocalChannel.State.CONNECTED;
         peer.eventLoop().execute(new Runnable() {
            public void run() {
               LocalChannel.this.registerInProgress = false;
               ChannelPromise promise = peer.connectPromise;
               if (promise != null && promise.trySuccess()) {
                  peer.pipeline().fireChannelActive();
               }

            }
         });
      }

      ((SingleThreadEventExecutor)this.eventLoop()).addShutdownHook(this.shutdownHook);
   }

   protected void doBind(SocketAddress localAddress) throws Exception {
      this.localAddress = LocalChannelRegistry.register(this, this.localAddress, localAddress);
      this.state = LocalChannel.State.BOUND;
   }

   protected void doDisconnect() throws Exception {
      this.doClose();
   }

   protected void doClose() throws Exception {
      final LocalChannel peer = this.peer;
      State oldState = this.state;

      try {
         if (oldState != LocalChannel.State.CLOSED) {
            if (this.localAddress != null) {
               if (this.parent() == null) {
                  LocalChannelRegistry.unregister(this.localAddress);
               }

               this.localAddress = null;
            }

            this.state = LocalChannel.State.CLOSED;
            this.finishPeerRead(this);
            ChannelPromise promise = this.connectPromise;
            if (promise != null) {
               promise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
               this.connectPromise = null;
            }
         }

         if (peer != null) {
            this.peer = null;
            EventLoop peerEventLoop = peer.eventLoop();
            final boolean peerIsActive = peer.isActive();
            if (peerEventLoop.inEventLoop() && !this.registerInProgress) {
               peer.tryClose(peerIsActive);
            } else {
               try {
                  peerEventLoop.execute(new Runnable() {
                     public void run() {
                        peer.tryClose(peerIsActive);
                     }
                  });
               } catch (Throwable var9) {
                  logger.warn("Releasing Inbound Queues for channels {}-{} because exception occurred!", this, peer, var9);
                  if (peerEventLoop.inEventLoop()) {
                     peer.releaseInboundBuffers();
                  } else {
                     peer.close();
                  }

                  PlatformDependent.throwException(var9);
               }
            }
         }
      } finally {
         if (oldState != null && oldState != LocalChannel.State.CLOSED) {
            this.releaseInboundBuffers();
         }

      }

   }

   private void tryClose(boolean isActive) {
      if (isActive) {
         this.unsafe().close(this.unsafe().voidPromise());
      } else {
         this.releaseInboundBuffers();
      }

   }

   protected void doDeregister() throws Exception {
      ((SingleThreadEventExecutor)this.eventLoop()).removeShutdownHook(this.shutdownHook);
   }

   protected void doBeginRead() throws Exception {
      if (!this.readInProgress) {
         ChannelPipeline pipeline = this.pipeline();
         Queue inboundBuffer = this.inboundBuffer;
         if (inboundBuffer.isEmpty()) {
            this.readInProgress = true;
         } else {
            InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
            Integer stackDepth = threadLocals.localChannelReaderStackDepth();
            if (stackDepth < 8) {
               threadLocals.setLocalChannelReaderStackDepth(stackDepth + 1);

               try {
                  while(true) {
                     Object received = inboundBuffer.poll();
                     if (received == null) {
                        pipeline.fireChannelReadComplete();
                        break;
                     }

                     pipeline.fireChannelRead(received);
                  }
               } finally {
                  threadLocals.setLocalChannelReaderStackDepth(stackDepth);
               }
            } else {
               try {
                  this.eventLoop().execute(this.readTask);
               } catch (Throwable var9) {
                  logger.warn("Closing Local channels {}-{} because exception occurred!", this, this.peer, var9);
                  this.close();
                  this.peer.close();
                  PlatformDependent.throwException(var9);
               }
            }

         }
      }
   }

   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      switch (this.state) {
         case OPEN:
         case BOUND:
            throw new NotYetConnectedException();
         case CLOSED:
            throw DO_WRITE_CLOSED_CHANNEL_EXCEPTION;
         case CONNECTED:
         default:
            LocalChannel peer = this.peer;
            this.writeInProgress = true;

            try {
               while(true) {
                  Object msg = in.current();
                  if (msg == null) {
                     break;
                  }

                  try {
                     if (peer.state == LocalChannel.State.CONNECTED) {
                        peer.inboundBuffer.add(ReferenceCountUtil.retain(msg));
                        in.remove();
                     } else {
                        in.remove(DO_WRITE_CLOSED_CHANNEL_EXCEPTION);
                     }
                  } catch (Throwable var8) {
                     in.remove(var8);
                  }
               }
            } finally {
               this.writeInProgress = false;
            }

            this.finishPeerRead(peer);
      }
   }

   private void finishPeerRead(LocalChannel peer) {
      if (peer.eventLoop() == this.eventLoop() && !peer.writeInProgress) {
         this.finishPeerRead0(peer);
      } else {
         this.runFinishPeerReadTask(peer);
      }

   }

   private void runFinishPeerReadTask(final LocalChannel peer) {
      Runnable finishPeerReadTask = new Runnable() {
         public void run() {
            LocalChannel.this.finishPeerRead0(peer);
         }
      };

      try {
         if (peer.writeInProgress) {
            peer.finishReadFuture = peer.eventLoop().submit(finishPeerReadTask);
         } else {
            peer.eventLoop().execute(finishPeerReadTask);
         }
      } catch (Throwable var4) {
         logger.warn("Closing Local channels {}-{} because exception occurred!", this, peer, var4);
         this.close();
         peer.close();
         PlatformDependent.throwException(var4);
      }

   }

   private void releaseInboundBuffers() {
      assert this.eventLoop() == null || this.eventLoop().inEventLoop();

      this.readInProgress = false;
      Queue inboundBuffer = this.inboundBuffer;

      Object msg;
      while((msg = inboundBuffer.poll()) != null) {
         ReferenceCountUtil.release(msg);
      }

   }

   private void finishPeerRead0(LocalChannel peer) {
      Future peerFinishReadFuture = peer.finishReadFuture;
      if (peerFinishReadFuture != null) {
         if (!peerFinishReadFuture.isDone()) {
            this.runFinishPeerReadTask(peer);
            return;
         }

         FINISH_READ_FUTURE_UPDATER.compareAndSet(peer, peerFinishReadFuture, (Object)null);
      }

      ChannelPipeline peerPipeline = peer.pipeline();
      if (peer.readInProgress) {
         peer.readInProgress = false;

         while(true) {
            Object received = peer.inboundBuffer.poll();
            if (received == null) {
               peerPipeline.fireChannelReadComplete();
               break;
            }

            peerPipeline.fireChannelRead(received);
         }
      }

   }

   private class LocalUnsafe extends AbstractChannel.AbstractUnsafe {
      private LocalUnsafe() {
         super();
      }

      public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
         if (promise.setUncancellable() && this.ensureOpen(promise)) {
            if (LocalChannel.this.state == LocalChannel.State.CONNECTED) {
               Exception causex = new AlreadyConnectedException();
               this.safeSetFailure(promise, causex);
               LocalChannel.this.pipeline().fireExceptionCaught(causex);
            } else if (LocalChannel.this.connectPromise != null) {
               throw new ConnectionPendingException();
            } else {
               LocalChannel.this.connectPromise = promise;
               if (LocalChannel.this.state != LocalChannel.State.BOUND && localAddress == null) {
                  localAddress = new LocalAddress(LocalChannel.this);
               }

               if (localAddress != null) {
                  try {
                     LocalChannel.this.doBind((SocketAddress)localAddress);
                  } catch (Throwable var6) {
                     this.safeSetFailure(promise, var6);
                     this.close(this.voidPromise());
                     return;
                  }
               }

               Channel boundChannel = LocalChannelRegistry.get(remoteAddress);
               if (!(boundChannel instanceof LocalServerChannel)) {
                  Exception cause = new ConnectException("connection refused: " + remoteAddress);
                  this.safeSetFailure(promise, cause);
                  this.close(this.voidPromise());
               } else {
                  LocalServerChannel serverChannel = (LocalServerChannel)boundChannel;
                  LocalChannel.this.peer = serverChannel.serve(LocalChannel.this);
               }
            }
         }
      }

      // $FF: synthetic method
      LocalUnsafe(Object x1) {
         this();
      }
   }

   private static enum State {
      OPEN,
      BOUND,
      CONNECTED,
      CLOSED;
   }
}
