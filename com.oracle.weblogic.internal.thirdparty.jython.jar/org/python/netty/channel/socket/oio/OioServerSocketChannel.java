package org.python.netty.channel.socket.oio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.ChannelMetadata;
import org.python.netty.channel.ChannelOutboundBuffer;
import org.python.netty.channel.oio.AbstractOioMessageChannel;
import org.python.netty.channel.socket.ServerSocketChannel;
import org.python.netty.util.internal.SocketUtils;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class OioServerSocketChannel extends AbstractOioMessageChannel implements ServerSocketChannel {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioServerSocketChannel.class);
   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 1);
   final ServerSocket socket;
   final Lock shutdownLock;
   private final OioServerSocketChannelConfig config;

   private static ServerSocket newServerSocket() {
      try {
         return new ServerSocket();
      } catch (IOException var1) {
         throw new ChannelException("failed to create a server socket", var1);
      }
   }

   public OioServerSocketChannel() {
      this(newServerSocket());
   }

   public OioServerSocketChannel(ServerSocket socket) {
      super((Channel)null);
      this.shutdownLock = new ReentrantLock();
      if (socket == null) {
         throw new NullPointerException("socket");
      } else {
         boolean success = false;

         try {
            socket.setSoTimeout(1000);
            success = true;
         } catch (IOException var10) {
            throw new ChannelException("Failed to set the server socket timeout.", var10);
         } finally {
            if (!success) {
               try {
                  socket.close();
               } catch (IOException var11) {
                  if (logger.isWarnEnabled()) {
                     logger.warn("Failed to close a partially initialized socket.", (Throwable)var11);
                  }
               }
            }

         }

         this.socket = socket;
         this.config = new DefaultOioServerSocketChannelConfig(this, socket);
      }
   }

   public InetSocketAddress localAddress() {
      return (InetSocketAddress)super.localAddress();
   }

   public ChannelMetadata metadata() {
      return METADATA;
   }

   public OioServerSocketChannelConfig config() {
      return this.config;
   }

   public InetSocketAddress remoteAddress() {
      return null;
   }

   public boolean isOpen() {
      return !this.socket.isClosed();
   }

   public boolean isActive() {
      return this.isOpen() && this.socket.isBound();
   }

   protected SocketAddress localAddress0() {
      return SocketUtils.localSocketAddress(this.socket);
   }

   protected void doBind(SocketAddress localAddress) throws Exception {
      this.socket.bind(localAddress, this.config.getBacklog());
   }

   protected void doClose() throws Exception {
      this.socket.close();
   }

   protected int doReadMessages(List buf) throws Exception {
      if (this.socket.isClosed()) {
         return -1;
      } else {
         try {
            Socket s = this.socket.accept();

            try {
               buf.add(new OioSocketChannel(this, s));
               return 1;
            } catch (Throwable var6) {
               logger.warn("Failed to create a new channel from an accepted socket.", var6);

               try {
                  s.close();
               } catch (Throwable var5) {
                  logger.warn("Failed to close a socket.", var5);
               }
            }
         } catch (SocketTimeoutException var7) {
         }

         return 0;
      }
   }

   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      throw new UnsupportedOperationException();
   }

   protected Object filterOutboundMessage(Object msg) throws Exception {
      throw new UnsupportedOperationException();
   }

   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
      throw new UnsupportedOperationException();
   }

   protected SocketAddress remoteAddress0() {
      return null;
   }

   protected void doDisconnect() throws Exception {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   protected void setReadPending(boolean readPending) {
      super.setReadPending(readPending);
   }

   final void clearReadPending0() {
      super.clearReadPending();
   }
}
