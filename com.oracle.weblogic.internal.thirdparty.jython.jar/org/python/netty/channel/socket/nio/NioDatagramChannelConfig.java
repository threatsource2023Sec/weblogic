package org.python.netty.channel.socket.nio;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.util.Enumeration;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.socket.DatagramChannelConfig;
import org.python.netty.channel.socket.DefaultDatagramChannelConfig;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.SocketUtils;

class NioDatagramChannelConfig extends DefaultDatagramChannelConfig {
   private static final Object IP_MULTICAST_TTL;
   private static final Object IP_MULTICAST_IF;
   private static final Object IP_MULTICAST_LOOP;
   private static final Method GET_OPTION;
   private static final Method SET_OPTION;
   private final DatagramChannel javaChannel;

   NioDatagramChannelConfig(NioDatagramChannel channel, DatagramChannel javaChannel) {
      super(channel, javaChannel.socket());
      this.javaChannel = javaChannel;
   }

   public int getTimeToLive() {
      return (Integer)this.getOption0(IP_MULTICAST_TTL);
   }

   public DatagramChannelConfig setTimeToLive(int ttl) {
      this.setOption0(IP_MULTICAST_TTL, ttl);
      return this;
   }

   public InetAddress getInterface() {
      NetworkInterface inf = this.getNetworkInterface();
      if (inf == null) {
         return null;
      } else {
         Enumeration addresses = SocketUtils.addressesFromNetworkInterface(inf);
         return addresses.hasMoreElements() ? (InetAddress)addresses.nextElement() : null;
      }
   }

   public DatagramChannelConfig setInterface(InetAddress interfaceAddress) {
      try {
         this.setNetworkInterface(NetworkInterface.getByInetAddress(interfaceAddress));
         return this;
      } catch (SocketException var3) {
         throw new ChannelException(var3);
      }
   }

   public NetworkInterface getNetworkInterface() {
      return (NetworkInterface)this.getOption0(IP_MULTICAST_IF);
   }

   public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
      this.setOption0(IP_MULTICAST_IF, networkInterface);
      return this;
   }

   public boolean isLoopbackModeDisabled() {
      return (Boolean)this.getOption0(IP_MULTICAST_LOOP);
   }

   public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
      this.setOption0(IP_MULTICAST_LOOP, loopbackModeDisabled);
      return this;
   }

   public DatagramChannelConfig setAutoRead(boolean autoRead) {
      super.setAutoRead(autoRead);
      return this;
   }

   protected void autoReadCleared() {
      ((NioDatagramChannel)this.channel).clearReadPending0();
   }

   private Object getOption0(Object option) {
      if (GET_OPTION == null) {
         throw new UnsupportedOperationException();
      } else {
         try {
            return GET_OPTION.invoke(this.javaChannel, option);
         } catch (Exception var3) {
            throw new ChannelException(var3);
         }
      }
   }

   private void setOption0(Object option, Object value) {
      if (SET_OPTION == null) {
         throw new UnsupportedOperationException();
      } else {
         try {
            SET_OPTION.invoke(this.javaChannel, option, value);
         } catch (Exception var4) {
            throw new ChannelException(var4);
         }
      }
   }

   static {
      ClassLoader classLoader = PlatformDependent.getClassLoader(DatagramChannel.class);
      Class socketOptionType = null;

      try {
         socketOptionType = Class.forName("java.net.SocketOption", true, classLoader);
      } catch (Exception var17) {
      }

      Class stdSocketOptionType = null;

      try {
         stdSocketOptionType = Class.forName("java.net.StandardSocketOptions", true, classLoader);
      } catch (Exception var16) {
      }

      Object ipMulticastTtl = null;
      Object ipMulticastIf = null;
      Object ipMulticastLoop = null;
      Method getOption = null;
      Method setOption = null;
      if (socketOptionType != null) {
         try {
            ipMulticastTtl = stdSocketOptionType.getDeclaredField("IP_MULTICAST_TTL").get((Object)null);
         } catch (Exception var15) {
            throw new Error("cannot locate the IP_MULTICAST_TTL field", var15);
         }

         try {
            ipMulticastIf = stdSocketOptionType.getDeclaredField("IP_MULTICAST_IF").get((Object)null);
         } catch (Exception var14) {
            throw new Error("cannot locate the IP_MULTICAST_IF field", var14);
         }

         try {
            ipMulticastLoop = stdSocketOptionType.getDeclaredField("IP_MULTICAST_LOOP").get((Object)null);
         } catch (Exception var13) {
            throw new Error("cannot locate the IP_MULTICAST_LOOP field", var13);
         }

         Class networkChannelClass = null;

         try {
            networkChannelClass = Class.forName("java.nio.channels.NetworkChannel", true, classLoader);
         } catch (Throwable var12) {
         }

         if (networkChannelClass == null) {
            getOption = null;
            setOption = null;
         } else {
            try {
               getOption = networkChannelClass.getDeclaredMethod("getOption", socketOptionType);
            } catch (Exception var11) {
               throw new Error("cannot locate the getOption() method", var11);
            }

            try {
               setOption = networkChannelClass.getDeclaredMethod("setOption", socketOptionType, Object.class);
            } catch (Exception var10) {
               throw new Error("cannot locate the setOption() method", var10);
            }
         }
      }

      IP_MULTICAST_TTL = ipMulticastTtl;
      IP_MULTICAST_IF = ipMulticastIf;
      IP_MULTICAST_LOOP = ipMulticastLoop;
      GET_OPTION = getOption;
      SET_OPTION = setOption;
   }
}
