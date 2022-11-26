package org.python.netty.channel.socket;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.ChannelOption;
import org.python.netty.channel.DefaultChannelConfig;
import org.python.netty.channel.FixedRecvByteBufAllocator;
import org.python.netty.channel.MessageSizeEstimator;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.WriteBufferWaterMark;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class DefaultDatagramChannelConfig extends DefaultChannelConfig implements DatagramChannelConfig {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultDatagramChannelConfig.class);
   private final DatagramSocket javaSocket;
   private volatile boolean activeOnOpen;

   public DefaultDatagramChannelConfig(DatagramChannel channel, DatagramSocket javaSocket) {
      super(channel, new FixedRecvByteBufAllocator(2048));
      if (javaSocket == null) {
         throw new NullPointerException("javaSocket");
      } else {
         this.javaSocket = javaSocket;
      }
   }

   public Map getOptions() {
      return this.getOptions(super.getOptions(), new ChannelOption[]{ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION});
   }

   public Object getOption(ChannelOption option) {
      if (option == ChannelOption.SO_BROADCAST) {
         return this.isBroadcast();
      } else if (option == ChannelOption.SO_RCVBUF) {
         return this.getReceiveBufferSize();
      } else if (option == ChannelOption.SO_SNDBUF) {
         return this.getSendBufferSize();
      } else if (option == ChannelOption.SO_REUSEADDR) {
         return this.isReuseAddress();
      } else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
         return this.isLoopbackModeDisabled();
      } else if (option == ChannelOption.IP_MULTICAST_ADDR) {
         return this.getInterface();
      } else if (option == ChannelOption.IP_MULTICAST_IF) {
         return this.getNetworkInterface();
      } else if (option == ChannelOption.IP_MULTICAST_TTL) {
         return this.getTimeToLive();
      } else if (option == ChannelOption.IP_TOS) {
         return this.getTrafficClass();
      } else {
         return option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION ? this.activeOnOpen : super.getOption(option);
      }
   }

   public boolean setOption(ChannelOption option, Object value) {
      this.validate(option, value);
      if (option == ChannelOption.SO_BROADCAST) {
         this.setBroadcast((Boolean)value);
      } else if (option == ChannelOption.SO_RCVBUF) {
         this.setReceiveBufferSize((Integer)value);
      } else if (option == ChannelOption.SO_SNDBUF) {
         this.setSendBufferSize((Integer)value);
      } else if (option == ChannelOption.SO_REUSEADDR) {
         this.setReuseAddress((Boolean)value);
      } else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
         this.setLoopbackModeDisabled((Boolean)value);
      } else if (option == ChannelOption.IP_MULTICAST_ADDR) {
         this.setInterface((InetAddress)value);
      } else if (option == ChannelOption.IP_MULTICAST_IF) {
         this.setNetworkInterface((NetworkInterface)value);
      } else if (option == ChannelOption.IP_MULTICAST_TTL) {
         this.setTimeToLive((Integer)value);
      } else if (option == ChannelOption.IP_TOS) {
         this.setTrafficClass((Integer)value);
      } else {
         if (option != ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return super.setOption(option, value);
         }

         this.setActiveOnOpen((Boolean)value);
      }

      return true;
   }

   private void setActiveOnOpen(boolean activeOnOpen) {
      if (this.channel.isRegistered()) {
         throw new IllegalStateException("Can only changed before channel was registered");
      } else {
         this.activeOnOpen = activeOnOpen;
      }
   }

   public boolean isBroadcast() {
      try {
         return this.javaSocket.getBroadcast();
      } catch (SocketException var2) {
         throw new ChannelException(var2);
      }
   }

   public DatagramChannelConfig setBroadcast(boolean broadcast) {
      try {
         if (broadcast && !this.javaSocket.getLocalAddress().isAnyLocalAddress() && !PlatformDependent.isWindows() && !PlatformDependent.maybeSuperUser()) {
            logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to " + this.javaSocket.getLocalSocketAddress() + '.');
         }

         this.javaSocket.setBroadcast(broadcast);
         return this;
      } catch (SocketException var3) {
         throw new ChannelException(var3);
      }
   }

   public InetAddress getInterface() {
      if (this.javaSocket instanceof MulticastSocket) {
         try {
            return ((MulticastSocket)this.javaSocket).getInterface();
         } catch (SocketException var2) {
            throw new ChannelException(var2);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public DatagramChannelConfig setInterface(InetAddress interfaceAddress) {
      if (this.javaSocket instanceof MulticastSocket) {
         try {
            ((MulticastSocket)this.javaSocket).setInterface(interfaceAddress);
            return this;
         } catch (SocketException var3) {
            throw new ChannelException(var3);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public boolean isLoopbackModeDisabled() {
      if (this.javaSocket instanceof MulticastSocket) {
         try {
            return ((MulticastSocket)this.javaSocket).getLoopbackMode();
         } catch (SocketException var2) {
            throw new ChannelException(var2);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
      if (this.javaSocket instanceof MulticastSocket) {
         try {
            ((MulticastSocket)this.javaSocket).setLoopbackMode(loopbackModeDisabled);
            return this;
         } catch (SocketException var3) {
            throw new ChannelException(var3);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public NetworkInterface getNetworkInterface() {
      if (this.javaSocket instanceof MulticastSocket) {
         try {
            return ((MulticastSocket)this.javaSocket).getNetworkInterface();
         } catch (SocketException var2) {
            throw new ChannelException(var2);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
      if (this.javaSocket instanceof MulticastSocket) {
         try {
            ((MulticastSocket)this.javaSocket).setNetworkInterface(networkInterface);
            return this;
         } catch (SocketException var3) {
            throw new ChannelException(var3);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public boolean isReuseAddress() {
      try {
         return this.javaSocket.getReuseAddress();
      } catch (SocketException var2) {
         throw new ChannelException(var2);
      }
   }

   public DatagramChannelConfig setReuseAddress(boolean reuseAddress) {
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

   public DatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
      try {
         this.javaSocket.setReceiveBufferSize(receiveBufferSize);
         return this;
      } catch (SocketException var3) {
         throw new ChannelException(var3);
      }
   }

   public int getSendBufferSize() {
      try {
         return this.javaSocket.getSendBufferSize();
      } catch (SocketException var2) {
         throw new ChannelException(var2);
      }
   }

   public DatagramChannelConfig setSendBufferSize(int sendBufferSize) {
      try {
         this.javaSocket.setSendBufferSize(sendBufferSize);
         return this;
      } catch (SocketException var3) {
         throw new ChannelException(var3);
      }
   }

   public int getTimeToLive() {
      if (this.javaSocket instanceof MulticastSocket) {
         try {
            return ((MulticastSocket)this.javaSocket).getTimeToLive();
         } catch (IOException var2) {
            throw new ChannelException(var2);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public DatagramChannelConfig setTimeToLive(int ttl) {
      if (this.javaSocket instanceof MulticastSocket) {
         try {
            ((MulticastSocket)this.javaSocket).setTimeToLive(ttl);
            return this;
         } catch (IOException var3) {
            throw new ChannelException(var3);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public int getTrafficClass() {
      try {
         return this.javaSocket.getTrafficClass();
      } catch (SocketException var2) {
         throw new ChannelException(var2);
      }
   }

   public DatagramChannelConfig setTrafficClass(int trafficClass) {
      try {
         this.javaSocket.setTrafficClass(trafficClass);
         return this;
      } catch (SocketException var3) {
         throw new ChannelException(var3);
      }
   }

   public DatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
      super.setWriteSpinCount(writeSpinCount);
      return this;
   }

   public DatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
      super.setConnectTimeoutMillis(connectTimeoutMillis);
      return this;
   }

   /** @deprecated */
   @Deprecated
   public DatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
      super.setMaxMessagesPerRead(maxMessagesPerRead);
      return this;
   }

   public DatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
      super.setAllocator(allocator);
      return this;
   }

   public DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
      super.setRecvByteBufAllocator(allocator);
      return this;
   }

   public DatagramChannelConfig setAutoRead(boolean autoRead) {
      super.setAutoRead(autoRead);
      return this;
   }

   public DatagramChannelConfig setAutoClose(boolean autoClose) {
      super.setAutoClose(autoClose);
      return this;
   }

   public DatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
      super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
      return this;
   }

   public DatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
      super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
      return this;
   }

   public DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
      super.setWriteBufferWaterMark(writeBufferWaterMark);
      return this;
   }

   public DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
      super.setMessageSizeEstimator(estimator);
      return this;
   }
}
