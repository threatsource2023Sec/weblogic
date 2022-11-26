package org.python.netty.channel.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.ChannelMetadata;
import org.python.netty.channel.ChannelOutboundBuffer;
import org.python.netty.channel.nio.AbstractNioMessageChannel;
import org.python.netty.channel.socket.DefaultServerSocketChannelConfig;
import org.python.netty.channel.socket.ServerSocketChannel;
import org.python.netty.channel.socket.ServerSocketChannelConfig;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.SocketUtils;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class NioServerSocketChannel extends AbstractNioMessageChannel implements ServerSocketChannel {
   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
   private final ServerSocketChannelConfig config;

   private static java.nio.channels.ServerSocketChannel newSocket(SelectorProvider provider) {
      try {
         return provider.openServerSocketChannel();
      } catch (IOException var2) {
         throw new ChannelException("Failed to open a server socket.", var2);
      }
   }

   public NioServerSocketChannel() {
      this(newSocket(DEFAULT_SELECTOR_PROVIDER));
   }

   public NioServerSocketChannel(SelectorProvider provider) {
      this(newSocket(provider));
   }

   public NioServerSocketChannel(java.nio.channels.ServerSocketChannel channel) {
      super((Channel)null, channel, 16);
      this.config = new NioServerSocketChannelConfig(this, this.javaChannel().socket());
   }

   public InetSocketAddress localAddress() {
      return (InetSocketAddress)super.localAddress();
   }

   public ChannelMetadata metadata() {
      return METADATA;
   }

   public ServerSocketChannelConfig config() {
      return this.config;
   }

   public boolean isActive() {
      return this.javaChannel().socket().isBound();
   }

   public InetSocketAddress remoteAddress() {
      return null;
   }

   protected java.nio.channels.ServerSocketChannel javaChannel() {
      return (java.nio.channels.ServerSocketChannel)super.javaChannel();
   }

   protected SocketAddress localAddress0() {
      return SocketUtils.localSocketAddress(this.javaChannel().socket());
   }

   protected void doBind(SocketAddress localAddress) throws Exception {
      if (PlatformDependent.javaVersion() >= 7) {
         this.javaChannel().bind(localAddress, this.config.getBacklog());
      } else {
         this.javaChannel().socket().bind(localAddress, this.config.getBacklog());
      }

   }

   protected void doClose() throws Exception {
      this.javaChannel().close();
   }

   protected int doReadMessages(List buf) throws Exception {
      SocketChannel ch = SocketUtils.accept(this.javaChannel());

      try {
         if (ch != null) {
            buf.add(new NioSocketChannel(this, ch));
            return 1;
         }
      } catch (Throwable var6) {
         logger.warn("Failed to create a new channel from an accepted socket.", var6);

         try {
            ch.close();
         } catch (Throwable var5) {
            logger.warn("Failed to close a socket.", var5);
         }
      }

      return 0;
   }

   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
      throw new UnsupportedOperationException();
   }

   protected void doFinishConnect() throws Exception {
      throw new UnsupportedOperationException();
   }

   protected SocketAddress remoteAddress0() {
      return null;
   }

   protected void doDisconnect() throws Exception {
      throw new UnsupportedOperationException();
   }

   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
      throw new UnsupportedOperationException();
   }

   protected final Object filterOutboundMessage(Object msg) throws Exception {
      throw new UnsupportedOperationException();
   }

   private final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig {
      private NioServerSocketChannelConfig(NioServerSocketChannel channel, ServerSocket javaSocket) {
         super(channel, javaSocket);
      }

      protected void autoReadCleared() {
         NioServerSocketChannel.this.clearReadPending();
      }

      // $FF: synthetic method
      NioServerSocketChannelConfig(NioServerSocketChannel x1, ServerSocket x2, Object x3) {
         this(x1, x2);
      }
   }
}
