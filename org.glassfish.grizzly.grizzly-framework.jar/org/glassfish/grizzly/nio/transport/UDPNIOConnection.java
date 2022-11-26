package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.SelectorRunner;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Holder;
import org.glassfish.grizzly.utils.JdkVersion;
import org.glassfish.grizzly.utils.NullaryFunction;

public class UDPNIOConnection extends NIOConnection {
   private static final Logger LOGGER = Grizzly.logger(UDPNIOConnection.class);
   private static final boolean IS_MULTICAST_SUPPORTED;
   private static final Method JOIN_METHOD;
   private static final Method JOIN_WITH_SOURCE_METHOD;
   private static final Method MK_GET_NETWORK_INTERFACE_METHOD;
   private static final Method MK_GET_SOURCE_ADDRESS_METHOD;
   private static final Method MK_DROP_METHOD;
   private static final Method MK_BLOCK_METHOD;
   private static final Method MK_UNBLOCK_METHOD;
   private final Object multicastSync;
   private Map membershipKeysMap;
   Holder localSocketAddressHolder;
   Holder peerSocketAddressHolder;
   private int readBufferSize = -1;
   private int writeBufferSize = -1;

   public UDPNIOConnection(UDPNIOTransport transport, DatagramChannel channel) {
      super(transport);
      this.channel = channel;
      this.resetProperties();
      this.multicastSync = IS_MULTICAST_SUPPORTED ? new Object() : null;
   }

   public boolean isConnected() {
      return this.channel != null && ((DatagramChannel)this.channel).isConnected();
   }

   public void join(InetAddress group, NetworkInterface networkInterface) throws IOException {
      this.join(group, networkInterface, (InetAddress)null);
   }

   public void join(InetAddress group, NetworkInterface networkInterface, InetAddress source) throws IOException {
      if (!IS_MULTICAST_SUPPORTED) {
         throw new UnsupportedOperationException("JDK 1.7+ required");
      } else if (group == null) {
         throw new IllegalArgumentException("group parameter can't be null");
      } else if (networkInterface == null) {
         throw new IllegalArgumentException("networkInterface parameter can't be null");
      } else {
         synchronized(this.multicastSync) {
            Object membershipKey = join0((DatagramChannel)this.channel, group, networkInterface, source);
            if (this.membershipKeysMap == null) {
               this.membershipKeysMap = new HashMap();
            }

            Set keySet = (Set)this.membershipKeysMap.get(group);
            if (keySet == null) {
               keySet = new HashSet();
               this.membershipKeysMap.put(group, keySet);
            }

            ((Set)keySet).add(membershipKey);
         }
      }
   }

   public void drop(InetAddress group, NetworkInterface networkInterface) throws IOException {
      this.drop(group, networkInterface, (InetAddress)null);
   }

   public void drop(InetAddress group, NetworkInterface networkInterface, InetAddress source) throws IOException {
      if (!IS_MULTICAST_SUPPORTED) {
         throw new UnsupportedOperationException("JDK 1.7+ required");
      } else if (group == null) {
         throw new IllegalArgumentException("group parameter can't be null");
      } else if (networkInterface == null) {
         throw new IllegalArgumentException("networkInterface parameter can't be null");
      } else {
         synchronized(this.multicastSync) {
            Set keys;
            if (this.membershipKeysMap != null && (keys = (Set)this.membershipKeysMap.get(group)) != null) {
               Iterator it = keys.iterator();

               while(it.hasNext()) {
                  Object key = it.next();
                  if (networkInterface.equals(networkInterface0(key)) && (source == null && sourceAddress0(key) == null || source != null && source.equals(sourceAddress0(key)))) {
                     drop0(key);
                     it.remove();
                  }

                  if (keys.isEmpty()) {
                     this.membershipKeysMap.remove(group);
                  }
               }
            }

         }
      }
   }

   public void dropAll(InetAddress group, NetworkInterface networkInterface) throws IOException {
      if (!IS_MULTICAST_SUPPORTED) {
         throw new UnsupportedOperationException("JDK 1.7+ required");
      } else if (group == null) {
         throw new IllegalArgumentException("group parameter can't be null");
      } else if (networkInterface == null) {
         throw new IllegalArgumentException("networkInterface parameter can't be null");
      } else {
         synchronized(this.multicastSync) {
            Set keys;
            if (this.membershipKeysMap != null && (keys = (Set)this.membershipKeysMap.get(group)) != null) {
               Iterator it = keys.iterator();

               while(it.hasNext()) {
                  Object key = it.next();
                  if (networkInterface.equals(networkInterface0(key))) {
                     drop0(key);
                     it.remove();
                  }
               }

               if (keys.isEmpty()) {
                  this.membershipKeysMap.remove(group);
               }
            }

         }
      }
   }

   public void block(InetAddress group, NetworkInterface networkInterface, InetAddress source) throws IOException {
      if (!IS_MULTICAST_SUPPORTED) {
         throw new UnsupportedOperationException("JDK 1.7+ required");
      } else if (group == null) {
         throw new IllegalArgumentException("group parameter can't be null");
      } else if (networkInterface == null) {
         throw new IllegalArgumentException("networkInterface parameter can't be null");
      } else {
         synchronized(this.multicastSync) {
            Set keys;
            if (this.membershipKeysMap != null && (keys = (Set)this.membershipKeysMap.get(group)) != null) {
               Iterator it = keys.iterator();

               while(it.hasNext()) {
                  Object key = it.next();
                  if (networkInterface.equals(networkInterface0(key)) && sourceAddress0(key) == null) {
                     block0(key, source);
                  }
               }
            }

         }
      }
   }

   public void unblock(InetAddress group, NetworkInterface networkInterface, InetAddress source) throws IOException {
      if (!IS_MULTICAST_SUPPORTED) {
         throw new UnsupportedOperationException("JDK 1.7+ required");
      } else if (group == null) {
         throw new IllegalArgumentException("group parameter can't be null");
      } else if (networkInterface == null) {
         throw new IllegalArgumentException("networkInterface parameter can't be null");
      } else {
         synchronized(this.multicastSync) {
            Set keys;
            if (this.membershipKeysMap != null && (keys = (Set)this.membershipKeysMap.get(group)) != null) {
               Iterator it = keys.iterator();

               while(it.hasNext()) {
                  Object key = it.next();
                  if (networkInterface.equals(networkInterface0(key)) && sourceAddress0(key) == null) {
                     unblock0(key, source);
                  }
               }
            }

         }
      }
   }

   protected void setSelectionKey(SelectionKey selectionKey) {
      super.setSelectionKey(selectionKey);
   }

   protected void setSelectorRunner(SelectorRunner selectorRunner) {
      super.setSelectorRunner(selectorRunner);
   }

   protected boolean notifyReady() {
      return connectCloseSemaphoreUpdater.compareAndSet(this, (Object)null, NOTIFICATION_INITIALIZED);
   }

   public SocketAddress getPeerAddress() {
      return (SocketAddress)this.peerSocketAddressHolder.get();
   }

   public SocketAddress getLocalAddress() {
      return (SocketAddress)this.localSocketAddressHolder.get();
   }

   protected final void resetProperties() {
      if (this.channel != null) {
         this.setReadBufferSize(this.transport.getReadBufferSize());
         this.setWriteBufferSize(this.transport.getWriteBufferSize());
         int transportMaxAsyncWriteQueueSize = this.transport.getAsyncQueueIO().getWriter().getMaxPendingBytesPerConnection();
         this.setMaxAsyncWriteQueueSize(transportMaxAsyncWriteQueueSize == -2 ? this.getWriteBufferSize() * 4 : transportMaxAsyncWriteQueueSize);
         this.localSocketAddressHolder = Holder.lazyHolder(new NullaryFunction() {
            public SocketAddress evaluate() {
               return ((DatagramChannel)UDPNIOConnection.this.channel).socket().getLocalSocketAddress();
            }
         });
         this.peerSocketAddressHolder = Holder.lazyHolder(new NullaryFunction() {
            public SocketAddress evaluate() {
               return ((DatagramChannel)UDPNIOConnection.this.channel).socket().getRemoteSocketAddress();
            }
         });
      }

   }

   public int getReadBufferSize() {
      if (this.readBufferSize >= 0) {
         return this.readBufferSize;
      } else {
         try {
            this.readBufferSize = ((DatagramChannel)this.channel).socket().getReceiveBufferSize();
         } catch (IOException var2) {
            LOGGER.log(Level.FINE, LogMessages.WARNING_GRIZZLY_CONNECTION_GET_READBUFFER_SIZE_EXCEPTION(), var2);
            this.readBufferSize = 0;
         }

         return this.readBufferSize;
      }
   }

   public void setReadBufferSize(int readBufferSize) {
      if (readBufferSize > 0) {
         try {
            int currentReadBufferSize = ((DatagramChannel)this.channel).socket().getReceiveBufferSize();
            if (readBufferSize > currentReadBufferSize) {
               ((DatagramChannel)this.channel).socket().setReceiveBufferSize(readBufferSize);
            }

            this.readBufferSize = readBufferSize;
         } catch (IOException var3) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_SET_READBUFFER_SIZE_EXCEPTION(), var3);
         }
      }

   }

   public int getWriteBufferSize() {
      if (this.writeBufferSize >= 0) {
         return this.writeBufferSize;
      } else {
         try {
            this.writeBufferSize = ((DatagramChannel)this.channel).socket().getSendBufferSize();
         } catch (IOException var2) {
            LOGGER.log(Level.FINE, LogMessages.WARNING_GRIZZLY_CONNECTION_GET_WRITEBUFFER_SIZE_EXCEPTION(), var2);
            this.writeBufferSize = 0;
         }

         return this.writeBufferSize;
      }
   }

   public void setWriteBufferSize(int writeBufferSize) {
      if (writeBufferSize > 0) {
         try {
            int currentSendBufferSize = ((DatagramChannel)this.channel).socket().getSendBufferSize();
            if (writeBufferSize > currentSendBufferSize) {
               ((DatagramChannel)this.channel).socket().setSendBufferSize(writeBufferSize);
            }

            this.writeBufferSize = writeBufferSize;
         } catch (IOException var3) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_SET_WRITEBUFFER_SIZE_EXCEPTION(), var3);
         }
      }

   }

   protected void enableInitialOpRead() throws IOException {
      super.enableInitialOpRead();
   }

   protected final void onConnect() throws IOException {
      notifyProbesConnect(this);
   }

   protected final void onRead(Buffer data, int size) {
      if (size > 0) {
         notifyProbesRead(this, data, size);
      }

      this.checkEmptyRead(size);
   }

   protected final void onWrite(Buffer data, int size) {
      notifyProbesWrite(this, data, (long)size);
   }

   public boolean canWrite() {
      return this.transport.getWriter(this).canWrite(this);
   }

   /** @deprecated */
   @Deprecated
   public boolean canWrite(int length) {
      return this.transport.getWriter(this).canWrite(this);
   }

   public void notifyCanWrite(WriteHandler writeHandler) {
      this.transport.getWriter(this).notifyWritePossible(this, writeHandler);
   }

   /** @deprecated */
   @Deprecated
   public void notifyCanWrite(WriteHandler handler, int length) {
      this.transport.getWriter(this).notifyWritePossible(this, handler);
   }

   void setMonitoringProbes(ConnectionProbe[] monitoringProbes) {
      this.monitoringConfig.addProbes(monitoringProbes);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("UDPNIOConnection");
      sb.append("{localSocketAddress=").append(this.localSocketAddressHolder);
      sb.append(", peerSocketAddress=").append(this.peerSocketAddressHolder);
      sb.append('}');
      return sb.toString();
   }

   private static Object join0(DatagramChannel channel, InetAddress group, NetworkInterface networkInterface, InetAddress source) throws IOException {
      return source == null ? invoke(channel, JOIN_METHOD, group, networkInterface) : invoke(channel, JOIN_WITH_SOURCE_METHOD, group, networkInterface, source);
   }

   private static NetworkInterface networkInterface0(Object membershipKey) throws IOException {
      return (NetworkInterface)invoke(membershipKey, MK_GET_NETWORK_INTERFACE_METHOD);
   }

   private static InetAddress sourceAddress0(Object membershipKey) throws IOException {
      return (InetAddress)invoke(membershipKey, MK_GET_SOURCE_ADDRESS_METHOD);
   }

   private static void drop0(Object membershipKey) throws IOException {
      invoke(membershipKey, MK_DROP_METHOD);
   }

   private static void block0(Object membershipKey, InetAddress sourceAddress) throws IOException {
      invoke(membershipKey, MK_BLOCK_METHOD, sourceAddress);
   }

   private static void unblock0(Object membershipKey, InetAddress sourceAddress) throws IOException {
      invoke(membershipKey, MK_UNBLOCK_METHOD, sourceAddress);
   }

   private static Object invoke(Object object, Method method, Object... params) throws IOException {
      try {
         return method.invoke(object, params);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
         } else {
            throw Exceptions.makeIOException(t);
         }
      } catch (Throwable var6) {
         throw Exceptions.makeIOException(var6);
      }
   }

   private static Class loadClass(String cname) throws Throwable {
      return ClassLoader.getSystemClassLoader().loadClass(cname);
   }

   static {
      JdkVersion jdkVersion = JdkVersion.getJdkVersion();
      JdkVersion minimumVersion = JdkVersion.parseVersion("1.7.0");
      boolean isInitialized = false;
      Method join = null;
      Method joinWithSource = null;
      Method mkGetNetworkInterface = null;
      Method mkGetSourceAddress = null;
      Method mkDrop = null;
      Method mkBlock = null;
      Method mkUnblock = null;
      if (minimumVersion.compareTo(jdkVersion) <= 0) {
         try {
            join = DatagramChannel.class.getMethod("join", InetAddress.class, NetworkInterface.class);
            joinWithSource = DatagramChannel.class.getMethod("join", InetAddress.class, NetworkInterface.class, InetAddress.class);
            Class membershipKeyClass = loadClass("java.nio.channels.MembershipKey");
            mkGetNetworkInterface = membershipKeyClass.getDeclaredMethod("networkInterface");
            mkGetSourceAddress = membershipKeyClass.getDeclaredMethod("sourceAddress");
            mkDrop = membershipKeyClass.getDeclaredMethod("drop");
            mkBlock = membershipKeyClass.getDeclaredMethod("block", InetAddress.class);
            mkUnblock = membershipKeyClass.getDeclaredMethod("unblock", InetAddress.class);
            isInitialized = true;
         } catch (Throwable var11) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_UDPMULTICASTING_EXCEPTIONE(), var11);
         }
      }

      if (isInitialized) {
         IS_MULTICAST_SUPPORTED = true;
         JOIN_METHOD = join;
         JOIN_WITH_SOURCE_METHOD = joinWithSource;
         MK_GET_NETWORK_INTERFACE_METHOD = mkGetNetworkInterface;
         MK_GET_SOURCE_ADDRESS_METHOD = mkGetSourceAddress;
         MK_DROP_METHOD = mkDrop;
         MK_BLOCK_METHOD = mkBlock;
         MK_UNBLOCK_METHOD = mkUnblock;
      } else {
         IS_MULTICAST_SUPPORTED = false;
         MK_UNBLOCK_METHOD = null;
         MK_BLOCK_METHOD = null;
         MK_DROP_METHOD = null;
         MK_GET_SOURCE_ADDRESS_METHOD = null;
         MK_GET_NETWORK_INTERFACE_METHOD = null;
         JOIN_WITH_SOURCE_METHOD = null;
         JOIN_METHOD = null;
      }

   }
}
