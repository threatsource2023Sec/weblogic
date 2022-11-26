package org.python.netty.channel.socket;

import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Map;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.ChannelOption;
import org.python.netty.channel.DefaultChannelConfig;
import org.python.netty.channel.MessageSizeEstimator;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.WriteBufferWaterMark;
import org.python.netty.util.NetUtil;

public class DefaultServerSocketChannelConfig extends DefaultChannelConfig implements ServerSocketChannelConfig {
   protected final ServerSocket javaSocket;
   private volatile int backlog;

   public DefaultServerSocketChannelConfig(ServerSocketChannel channel, ServerSocket javaSocket) {
      super(channel);
      this.backlog = NetUtil.SOMAXCONN;
      if (javaSocket == null) {
         throw new NullPointerException("javaSocket");
      } else {
         this.javaSocket = javaSocket;
      }
   }

   public Map getOptions() {
      return this.getOptions(super.getOptions(), new ChannelOption[]{ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG});
   }

   public Object getOption(ChannelOption option) {
      if (option == ChannelOption.SO_RCVBUF) {
         return this.getReceiveBufferSize();
      } else if (option == ChannelOption.SO_REUSEADDR) {
         return this.isReuseAddress();
      } else {
         return option == ChannelOption.SO_BACKLOG ? this.getBacklog() : super.getOption(option);
      }
   }

   public boolean setOption(ChannelOption option, Object value) {
      this.validate(option, value);
      if (option == ChannelOption.SO_RCVBUF) {
         this.setReceiveBufferSize((Integer)value);
      } else if (option == ChannelOption.SO_REUSEADDR) {
         this.setReuseAddress((Boolean)value);
      } else {
         if (option != ChannelOption.SO_BACKLOG) {
            return super.setOption(option, value);
         }

         this.setBacklog((Integer)value);
      }

      return true;
   }

   public boolean isReuseAddress() {
      try {
         return this.javaSocket.getReuseAddress();
      } catch (SocketException var2) {
         throw new ChannelException(var2);
      }
   }

   public ServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
      try {
         this.javaSocket.setReuseAddress(reuseAddress);
         return this;
      } catch (SocketException var3) {
         throw new ChannelException(var3);
      }
   }

   public int getReceiveBufferSize() {
      try {
         return this.javaSocket.getReceiveBufferSize();
      } catch (SocketException var2) {
         throw new ChannelException(var2);
      }
   }

   public ServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
      try {
         this.javaSocket.setReceiveBufferSize(receiveBufferSize);
         return this;
      } catch (SocketException var3) {
         throw new ChannelException(var3);
      }
   }

   public ServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
      this.javaSocket.setPerformancePreferences(connectionTime, latency, bandwidth);
      return this;
   }

   public int getBacklog() {
      return this.backlog;
   }

   public ServerSocketChannelConfig setBacklog(int backlog) {
      if (backlog < 0) {
         throw new IllegalArgumentException("backlog: " + backlog);
      } else {
         this.backlog = backlog;
         return this;
      }
   }

   public ServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
      super.setConnectTimeoutMillis(connectTimeoutMillis);
      return this;
   }

   /** @deprecated */
   @Deprecated
   public ServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
      super.setMaxMessagesPerRead(maxMessagesPerRead);
      return this;
   }

   public ServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
      super.setWriteSpinCount(writeSpinCount);
      return this;
   }

   public ServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
      super.setAllocator(allocator);
      return this;
   }

   public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
      super.setRecvByteBufAllocator(allocator);
      return this;
   }

   public ServerSocketChannelConfig setAutoRead(boolean autoRead) {
      super.setAutoRead(autoRead);
      return this;
   }

   public ServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
      super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
      return this;
   }

   public ServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
      super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
      return this;
   }

   public ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
      super.setWriteBufferWaterMark(writeBufferWaterMark);
      return this;
   }

   public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
      super.setMessageSizeEstimator(estimator);
      return this;
   }
}
