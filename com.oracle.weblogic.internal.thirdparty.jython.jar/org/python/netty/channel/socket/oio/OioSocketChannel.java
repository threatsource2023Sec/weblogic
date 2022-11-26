package org.python.netty.channel.socket.oio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.ConnectTimeoutException;
import org.python.netty.channel.EventLoop;
import org.python.netty.channel.oio.OioByteStreamChannel;
import org.python.netty.channel.socket.ServerSocketChannel;
import org.python.netty.channel.socket.SocketChannel;
import org.python.netty.util.internal.SocketUtils;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class OioSocketChannel extends OioByteStreamChannel implements SocketChannel {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
   private final Socket socket;
   private final OioSocketChannelConfig config;

   public OioSocketChannel() {
      this(new Socket());
   }

   public OioSocketChannel(Socket socket) {
      this((Channel)null, socket);
   }

   public OioSocketChannel(Channel parent, Socket socket) {
      super(parent);
      this.socket = socket;
      this.config = new DefaultOioSocketChannelConfig(this, socket);
      boolean success = false;

      try {
         if (socket.isConnected()) {
            this.activate(socket.getInputStream(), socket.getOutputStream());
         }

         socket.setSoTimeout(1000);
         success = true;
      } catch (Exception var12) {
         throw new ChannelException("failed to initialize a socket", var12);
      } finally {
         if (!success) {
            try {
               socket.close();
            } catch (IOException var11) {
               logger.warn("Failed to close a socket.", (Throwable)var11);
            }
         }

      }

   }

   public ServerSocketChannel parent() {
      return (ServerSocketChannel)super.parent();
   }

   public OioSocketChannelConfig config() {
      return this.config;
   }

   public boolean isOpen() {
      return !this.socket.isClosed();
   }

   public boolean isActive() {
      return !this.socket.isClosed() && this.socket.isConnected();
   }

   public boolean isOutputShutdown() {
      return this.socket.isOutputShutdown() || !this.isActive();
   }

   public boolean isInputShutdown() {
      return this.socket.isInputShutdown() || !this.isActive();
   }

   public boolean isShutdown() {
      return this.socket.isInputShutdown() && this.socket.isOutputShutdown() || !this.isActive();
   }

   public ChannelFuture shutdownOutput() {
      return this.shutdownOutput(this.newPromise());
   }

   public ChannelFuture shutdownInput() {
      return this.shutdownInput(this.newPromise());
   }

   public ChannelFuture shutdown() {
      return this.shutdown(this.newPromise());
   }

   protected int doReadBytes(ByteBuf buf) throws Exception {
      if (this.socket.isClosed()) {
         return -1;
      } else {
         try {
            return super.doReadBytes(buf);
         } catch (SocketTimeoutException var3) {
            return 0;
         }
      }
   }

   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
      EventLoop loop = this.eventLoop();
      if (loop.inEventLoop()) {
         this.shutdownOutput0(promise);
      } else {
         loop.execute(new Runnable() {
            public void run() {
               OioSocketChannel.this.shutdownOutput0(promise);
            }
         });
      }

      return promise;
   }

   private void shutdownOutput0(ChannelPromise promise) {
      try {
         this.socket.shutdownOutput();
         promise.setSuccess();
      } catch (Throwable var3) {
         promise.setFailure(var3);
      }

   }

   public ChannelFuture shutdownInput(final ChannelPromise promise) {
      EventLoop loop = this.eventLoop();
      if (loop.inEventLoop()) {
         this.shutdownInput0(promise);
      } else {
         loop.execute(new Runnable() {
            public void run() {
               OioSocketChannel.this.shutdownInput0(promise);
            }
         });
      }

      return promise;
   }

   private void shutdownInput0(ChannelPromise promise) {
      try {
         this.socket.shutdownInput();
         promise.setSuccess();
      } catch (Throwable var3) {
         promise.setFailure(var3);
      }

   }

   public ChannelFuture shutdown(final ChannelPromise promise) {
      EventLoop loop = this.eventLoop();
      if (loop.inEventLoop()) {
         this.shutdown0(promise);
      } else {
         loop.execute(new Runnable() {
            public void run() {
               OioSocketChannel.this.shutdown0(promise);
            }
         });
      }

      return promise;
   }

   private void shutdown0(ChannelPromise promise) {
      Throwable cause = null;

      try {
         this.socket.shutdownOutput();
      } catch (Throwable var4) {
         cause = var4;
      }

      try {
         this.socket.shutdownInput();
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

   public InetSocketAddress localAddress() {
      return (InetSocketAddress)super.localAddress();
   }

   public InetSocketAddress remoteAddress() {
      return (InetSocketAddress)super.remoteAddress();
   }

   protected SocketAddress localAddress0() {
      return this.socket.getLocalSocketAddress();
   }

   protected SocketAddress remoteAddress0() {
      return this.socket.getRemoteSocketAddress();
   }

   protected void doBind(SocketAddress localAddress) throws Exception {
      SocketUtils.bind(this.socket, localAddress);
   }

   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
      if (localAddress != null) {
         SocketUtils.bind(this.socket, localAddress);
      }

      boolean success = false;

      try {
         SocketUtils.connect(this.socket, remoteAddress, this.config().getConnectTimeoutMillis());
         this.activate(this.socket.getInputStream(), this.socket.getOutputStream());
         success = true;
      } catch (SocketTimeoutException var9) {
         ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
         cause.setStackTrace(var9.getStackTrace());
         throw cause;
      } finally {
         if (!success) {
            this.doClose();
         }

      }

   }

   protected void doDisconnect() throws Exception {
      this.doClose();
   }

   protected void doClose() throws Exception {
      this.socket.close();
   }

   protected boolean checkInputShutdown() {
      if (this.isInputShutdown()) {
         try {
            Thread.sleep((long)this.config().getSoTimeout());
         } catch (Throwable var2) {
         }

         return true;
      } else {
         return false;
      }
   }

   /** @deprecated */
   @Deprecated
   protected void setReadPending(boolean readPending) {
      super.setReadPending(readPending);
   }

   final void clearReadPending0() {
      this.clearReadPending();
   }
}
