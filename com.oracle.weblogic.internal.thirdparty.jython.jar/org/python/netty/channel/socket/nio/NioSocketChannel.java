package org.python.netty.channel.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelOutboundBuffer;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.EventLoop;
import org.python.netty.channel.FileRegion;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.nio.AbstractNioByteChannel;
import org.python.netty.channel.nio.AbstractNioChannel;
import org.python.netty.channel.socket.DefaultSocketChannelConfig;
import org.python.netty.channel.socket.ServerSocketChannel;
import org.python.netty.channel.socket.SocketChannel;
import org.python.netty.channel.socket.SocketChannelConfig;
import org.python.netty.util.concurrent.GlobalEventExecutor;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.SocketUtils;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class NioSocketChannel extends AbstractNioByteChannel implements SocketChannel {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSocketChannel.class);
   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
   private final SocketChannelConfig config;

   private static java.nio.channels.SocketChannel newSocket(SelectorProvider provider) {
      try {
         return provider.openSocketChannel();
      } catch (IOException var2) {
         throw new ChannelException("Failed to open a socket.", var2);
      }
   }

   public NioSocketChannel() {
      this(DEFAULT_SELECTOR_PROVIDER);
   }

   public NioSocketChannel(SelectorProvider provider) {
      this(newSocket(provider));
   }

   public NioSocketChannel(java.nio.channels.SocketChannel socket) {
      this((Channel)null, socket);
   }

   public NioSocketChannel(Channel parent, java.nio.channels.SocketChannel socket) {
      super(parent, socket);
      this.config = new NioSocketChannelConfig(this, socket.socket());
   }

   public ServerSocketChannel parent() {
      return (ServerSocketChannel)super.parent();
   }

   public SocketChannelConfig config() {
      return this.config;
   }

   protected java.nio.channels.SocketChannel javaChannel() {
      return (java.nio.channels.SocketChannel)super.javaChannel();
   }

   public boolean isActive() {
      java.nio.channels.SocketChannel ch = this.javaChannel();
      return ch.isOpen() && ch.isConnected();
   }

   public boolean isOutputShutdown() {
      return this.javaChannel().socket().isOutputShutdown() || !this.isActive();
   }

   public boolean isInputShutdown() {
      return this.javaChannel().socket().isInputShutdown() || !this.isActive();
   }

   public boolean isShutdown() {
      Socket socket = this.javaChannel().socket();
      return socket.isInputShutdown() && socket.isOutputShutdown() || !this.isActive();
   }

   public InetSocketAddress localAddress() {
      return (InetSocketAddress)super.localAddress();
   }

   public InetSocketAddress remoteAddress() {
      return (InetSocketAddress)super.remoteAddress();
   }

   public ChannelFuture shutdownOutput() {
      return this.shutdownOutput(this.newPromise());
   }

   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
      Executor closeExecutor = ((NioSocketChannelUnsafe)this.unsafe()).prepareToClose();
      if (closeExecutor != null) {
         closeExecutor.execute(new Runnable() {
            public void run() {
               NioSocketChannel.this.shutdownOutput0(promise);
            }
         });
      } else {
         EventLoop loop = this.eventLoop();
         if (loop.inEventLoop()) {
            this.shutdownOutput0(promise);
         } else {
            loop.execute(new Runnable() {
               public void run() {
                  NioSocketChannel.this.shutdownOutput0(promise);
               }
            });
         }
      }

      return promise;
   }

   public ChannelFuture shutdownInput() {
      return this.shutdownInput(this.newPromise());
   }

   protected boolean isInputShutdown0() {
      return this.isInputShutdown();
   }

   public ChannelFuture shutdownInput(final ChannelPromise promise) {
      Executor closeExecutor = ((NioSocketChannelUnsafe)this.unsafe()).prepareToClose();
      if (closeExecutor != null) {
         closeExecutor.execute(new Runnable() {
            public void run() {
               NioSocketChannel.this.shutdownInput0(promise);
            }
         });
      } else {
         EventLoop loop = this.eventLoop();
         if (loop.inEventLoop()) {
            this.shutdownInput0(promise);
         } else {
            loop.execute(new Runnable() {
               public void run() {
                  NioSocketChannel.this.shutdownInput0(promise);
               }
            });
         }
      }

      return promise;
   }

   public ChannelFuture shutdown() {
      return this.shutdown(this.newPromise());
   }

   public ChannelFuture shutdown(final ChannelPromise promise) {
      Executor closeExecutor = ((NioSocketChannelUnsafe)this.unsafe()).prepareToClose();
      if (closeExecutor != null) {
         closeExecutor.execute(new Runnable() {
            public void run() {
               NioSocketChannel.this.shutdown0(promise);
            }
         });
      } else {
         EventLoop loop = this.eventLoop();
         if (loop.inEventLoop()) {
            this.shutdown0(promise);
         } else {
            loop.execute(new Runnable() {
               public void run() {
                  NioSocketChannel.this.shutdown0(promise);
               }
            });
         }
      }

      return promise;
   }

   private void shutdownOutput0(ChannelPromise promise) {
      try {
         this.shutdownOutput0();
         promise.setSuccess();
      } catch (Throwable var3) {
         promise.setFailure(var3);
      }

   }

   private void shutdownOutput0() throws Exception {
      if (PlatformDependent.javaVersion() >= 7) {
         this.javaChannel().shutdownOutput();
      } else {
         this.javaChannel().socket().shutdownOutput();
      }

   }

   private void shutdownInput0(ChannelPromise promise) {
      try {
         this.shutdownInput0();
         promise.setSuccess();
      } catch (Throwable var3) {
         promise.setFailure(var3);
      }

   }

   private void shutdownInput0() throws Exception {
      if (PlatformDependent.javaVersion() >= 7) {
         this.javaChannel().shutdownInput();
      } else {
         this.javaChannel().socket().shutdownInput();
      }

   }

   private void shutdown0(ChannelPromise promise) {
      Throwable cause = null;

      try {
         this.shutdownOutput0();
      } catch (Throwable var4) {
         cause = var4;
      }

      try {
         this.shutdownInput0();
      } catch (Throwable var5) {
         if (cause == null) {
            promise.setFailure(var5);
         } else {
            logger.debug("Exception suppressed because a previous exception occurred.", var5);
            promise.setFailure(cause);
         }

         return;
      }

      if (cause == null) {
         promise.setSuccess();
      } else {
         promise.setFailure(cause);
      }

   }

   protected SocketAddress localAddress0() {
      return this.javaChannel().socket().getLocalSocketAddress();
   }

   protected SocketAddress remoteAddress0() {
      return this.javaChannel().socket().getRemoteSocketAddress();
   }

   protected void doBind(SocketAddress localAddress) throws Exception {
      this.doBind0(localAddress);
   }

   private void doBind0(SocketAddress localAddress) throws Exception {
      if (PlatformDependent.javaVersion() >= 7) {
         SocketUtils.bind(this.javaChannel(), localAddress);
      } else {
         SocketUtils.bind(this.javaChannel().socket(), localAddress);
      }

   }

   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
      if (localAddress != null) {
         this.doBind0(localAddress);
      }

      boolean success = false;

      boolean var5;
      try {
         boolean connected = SocketUtils.connect(this.javaChannel(), remoteAddress);
         if (!connected) {
            this.selectionKey().interestOps(8);
         }

         success = true;
         var5 = connected;
      } finally {
         if (!success) {
            this.doClose();
         }

      }

      return var5;
   }

   protected void doFinishConnect() throws Exception {
      if (!this.javaChannel().finishConnect()) {
         throw new Error();
      }
   }

   protected void doDisconnect() throws Exception {
      this.doClose();
   }

   protected void doClose() throws Exception {
      super.doClose();
      this.javaChannel().close();
   }

   protected int doReadBytes(ByteBuf byteBuf) throws Exception {
      RecvByteBufAllocator.Handle allocHandle = this.unsafe().recvBufAllocHandle();
      allocHandle.attemptedBytesRead(byteBuf.writableBytes());
      return byteBuf.writeBytes((ScatteringByteChannel)this.javaChannel(), allocHandle.attemptedBytesRead());
   }

   protected int doWriteBytes(ByteBuf buf) throws Exception {
      int expectedWrittenBytes = buf.readableBytes();
      return buf.readBytes((GatheringByteChannel)this.javaChannel(), expectedWrittenBytes);
   }

   protected long doWriteFileRegion(FileRegion region) throws Exception {
      long position = region.transferred();
      return region.transferTo(this.javaChannel(), position);
   }

   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      while(true) {
         int size = in.size();
         if (size == 0) {
            this.clearOpWrite();
            break;
         }

         long writtenBytes;
         boolean done;
         boolean setOpWrite;
         writtenBytes = 0L;
         done = false;
         setOpWrite = false;
         ByteBuffer[] nioBuffers = in.nioBuffers();
         int nioBufferCnt = in.nioBufferCount();
         long expectedWrittenBytes = in.nioBufferSize();
         java.nio.channels.SocketChannel ch = this.javaChannel();
         int i;
         label51:
         switch (nioBufferCnt) {
            case 0:
               super.doWrite(in);
               return;
            case 1:
               ByteBuffer nioBuffer = nioBuffers[0];
               i = this.config().getWriteSpinCount() - 1;

               while(true) {
                  if (i < 0) {
                     break label51;
                  }

                  int localWrittenBytes = ch.write(nioBuffer);
                  if (localWrittenBytes == 0) {
                     setOpWrite = true;
                     break label51;
                  }

                  expectedWrittenBytes -= (long)localWrittenBytes;
                  writtenBytes += (long)localWrittenBytes;
                  if (expectedWrittenBytes == 0L) {
                     done = true;
                     break label51;
                  }

                  --i;
               }
            default:
               for(i = this.config().getWriteSpinCount() - 1; i >= 0; --i) {
                  long localWrittenBytes = ch.write(nioBuffers, 0, nioBufferCnt);
                  if (localWrittenBytes == 0L) {
                     setOpWrite = true;
                     break;
                  }

                  expectedWrittenBytes -= localWrittenBytes;
                  writtenBytes += localWrittenBytes;
                  if (expectedWrittenBytes == 0L) {
                     done = true;
                     break;
                  }
               }
         }

         in.removeBytes(writtenBytes);
         if (!done) {
            this.incompleteWrite(setOpWrite);
            break;
         }
      }

   }

   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
      return new NioSocketChannelUnsafe();
   }

   private final class NioSocketChannelConfig extends DefaultSocketChannelConfig {
      private NioSocketChannelConfig(NioSocketChannel channel, Socket javaSocket) {
         super(channel, javaSocket);
      }

      protected void autoReadCleared() {
         NioSocketChannel.this.clearReadPending();
      }

      // $FF: synthetic method
      NioSocketChannelConfig(NioSocketChannel x1, Socket x2, Object x3) {
         this(x1, x2);
      }
   }

   private final class NioSocketChannelUnsafe extends AbstractNioByteChannel.NioByteUnsafe {
      private NioSocketChannelUnsafe() {
         super();
      }

      protected Executor prepareToClose() {
         try {
            if (NioSocketChannel.this.javaChannel().isOpen() && NioSocketChannel.this.config().getSoLinger() > 0) {
               NioSocketChannel.this.doDeregister();
               return GlobalEventExecutor.INSTANCE;
            }
         } catch (Throwable var2) {
         }

         return null;
      }

      // $FF: synthetic method
      NioSocketChannelUnsafe(Object x1) {
         this();
      }
   }
}
