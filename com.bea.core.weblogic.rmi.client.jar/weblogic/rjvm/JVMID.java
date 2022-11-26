package weblogic.rjvm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.common.internal.ClusterMessagePeerInfoable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Identity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerChannelStream;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.internal.ReferenceConstants;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.security.HMAC;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.SubjectManager;
import weblogic.utils.Debug;
import weblogic.utils.NestedError;
import weblogic.utils.UnsyncStringBuffer;
import weblogic.utils.io.Immutable;
import weblogic.utils.net.AddressUtils;

public final class JVMID implements ReferenceConstants, Externalizable, ServerIdentity, Channel, Immutable {
   private static final long serialVersionUID = -2573312136796037590L;
   private static final DebugLogger debugJVMID = DebugLogger.getDebugLogger("DebugJVMID");
   private static final boolean DEBUG = false;
   private static final boolean DEBUG_TRANS = false;
   public static final int INVALID_PORT = -1;
   private static final long INVALID_DIFFERENTIATOR = 0L;
   static final String DUMMY_IP = System.getProperty("weblogic.rjvm.unknownHostAddress", "192.0.0.8");
   public static final byte HAS_HOST_ADDRESS = 1;
   private static final byte HAS_ROUTER = 2;
   private static final byte HAS_CLUSTER_ADDRESS = 4;
   public static final byte HAS_DOMAIN_NAME = 8;
   public static final byte HAS_SERVER_NAME = 16;
   public static final byte HAS_DNS_NAME = 32;
   private byte flags;
   private static volatile JVMID localID = null;
   private static volatile JVMID localRemoteID = null;
   private volatile boolean localButWorkingInRemoteWay;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private String hostAddress;
   private String clusterAddress;
   private long differentiator;
   private int rawAddress;
   private int[] ports;
   private JVMID router;
   private String domainName;
   private String serverName;
   private String dnsName;
   private transient InetAddress inetAddress;
   private transient InetSocketAddress inSockAddr;
   private transient Identity transientIdentity;
   private transient Identity persistentIdentity;
   private transient String preambleAsString;
   private transient int hashCodeValue;
   private transient Object channelId;
   String[] uris;
   private static InetAddress localHost = null;

   private static boolean isDebugEnabled() {
      return KernelStatus.DEBUG && debugJVMID.isDebugEnabled();
   }

   public static JVMID localID() {
      if (localID == null) {
         Class var0 = JVMID.class;
         synchronized(JVMID.class) {
            if (localID == null) {
               setLocalClientID();
            }
         }

         RMIRuntime.getRMIRuntime().setLocalHostID(localID);
      }

      return localID;
   }

   public static JVMID localRemoteID() {
      localID();
      if (localRemoteID == null) {
         throw new IllegalAccessError("fails to init localRemoteID ... localID is " + localID);
      } else {
         return localRemoteID;
      }
   }

   private static void setLocalClientID() {
      Debug.assertion(!RJVMEnvironment.getEnvironment().isServer());

      InetAddress ia;
      try {
         ia = InetAddress.getLocalHost();
      } catch (UnknownHostException var4) {
         throw new NestedError("Local host not known?!", var4);
      } catch (SecurityException var5) {
         try {
            ia = InetAddress.getByName("0.0.0.0");
         } catch (Throwable var3) {
            throw new NestedError("Failed to even get InetAddress for unknown host: ", var3);
         }
      }

      localID = new JVMID(ia, false);
      localRemoteID = new JVMID(ia, false, true);
      RMIRuntime.getRMIRuntime().setLocalHostID(localID);
      ServerChannelManager.setServerChannelManager(new ServerChannelManager() {
         public long getAdminChannelCreationTime() {
            return 0L;
         }

         public ServerChannel getServerChannel(HostID identity) {
            return identity == JVMID.localID ? RJVMManager.getRJVMManager().getProtocolHandler((byte)0).getDefaultServerChannel() : null;
         }

         protected ServerChannel getAvailableServerChannel(HostID identity, String channelName) {
            return identity == JVMID.localID ? RJVMManager.getRJVMManager().getProtocolHandler((byte)0).getDefaultServerChannel() : null;
         }

         protected ServerChannel getServerChannel(HostID identity, Protocol protocol) {
            return identity == JVMID.localID ? protocol.getHandler().getDefaultServerChannel() : null;
         }

         protected ServerChannel getIPv4ServerChannel(HostID identity, Protocol protocol) {
            if (identity == JVMID.localID) {
               ServerChannel sc = protocol.getHandler().getDefaultServerChannel();

               try {
                  InetAddress ia = InetAddress.getByName(sc.getPublicAddress());
                  if (ia instanceof Inet4Address) {
                     return sc;
                  }
               } catch (UnknownHostException var5) {
               }
            }

            return null;
         }

         protected ServerChannel getIPv6ServerChannel(HostID identity, Protocol protocol) {
            if (identity == JVMID.localID) {
               ServerChannel sc = protocol.getHandler().getDefaultServerChannel();

               try {
                  InetAddress ia = InetAddress.getByName(sc.getPublicAddress());
                  if (ia instanceof Inet6Address) {
                     return sc;
                  }
               } catch (UnknownHostException var5) {
               }
            }

            return null;
         }

         protected ServerChannel getServerChannel(HostID identity, Protocol protocol, String channelName) {
            ServerChannel ch = this.getServerChannel(identity, protocol);
            return ch != null && ch.getChannelName().equals(channelName) ? ch : null;
         }

         protected ServerChannel getServerChannel(HostID identity, String partitionName, String vtName, Protocol protocol) {
            ServerChannel ch = protocol == null ? this.getServerChannel(identity) : this.getServerChannel(identity, protocol);
            return ch != null && partitionName != null && vtName != null && ch.getChannelName().startsWith(partitionName + "-" + vtName) ? ch : null;
         }

         protected ServerChannel getServerChannel(HostID identity, String channelName) {
            ServerChannel ch = this.getServerChannel(identity);
            return ch != null && ch.getChannelName().equals(channelName) ? ch : null;
         }

         protected ServerChannel getRelatedServerChannel(HostID identity, Protocol protocol, String publicAddress) {
            return null;
         }

         protected ServerChannel getOutboundServerChannel(Protocol protocol, String channel) {
            return protocol.getHandler().getDefaultServerChannel();
         }

         public void restartSSLChannels() {
         }
      });
   }

   public static void setLocalID(String hostAddress, String dnsName, String domainName, String serverName) throws UnknownHostException {
      InetAddress ia;
      if (hostAddress != null && !hostAddress.isEmpty()) {
         ia = InetAddress.getByName(hostAddress);
         localID = new JVMID(ia, false);
         localRemoteID = new JVMID(ia, false, true);
         localID.hostAddress = hostAddress;
         localRemoteID.hostAddress = hostAddress;
      } else {
         ia = AddressUtils.getIPForLocalHost();
         localID = new JVMID(ia, false);
         localRemoteID = new JVMID(ia, false, true);
      }

      localID.setDNSName(dnsName);
      localRemoteID.setDNSName(dnsName);
      localID.setDomainName(domainName);
      localRemoteID.setDomainName(domainName);
      localID.setServerName(serverName);
      localRemoteID.setServerName(serverName);
      RMIRuntime.getRMIRuntime().setLocalHostID(localID);
   }

   JVMID withPortFor(Protocol protocol) {
      if (this.isValidPort(this.getPort()) && !this.isValidPort(this.getPort(protocol))) {
         int[] ports = (int[])this.ports().clone();
         ports[ProtocolManager.getRealProtocol(protocol).toByte()] = this.getPort();
         return new JVMID(this.getInetAddress(), ports);
      } else {
         return this;
      }
   }

   private boolean isValidPort(int port) {
      return port != -1;
   }

   private int getUnique32BitNumber(String hostAddress) {
      byte[] saltBytes = this.serverName == null ? "WebLogicClient".getBytes() : this.serverName.getBytes();
      byte[] digest = HMAC.digest(hostAddress.getBytes(), saltBytes, saltBytes);
      int address = ((digest[0] & 255) << 24) + ((digest[1] & 255) << 16) + ((digest[2] & 255) << 8) + ((digest[3] & 255) << 0);
      return address;
   }

   private boolean hasHostAddress() {
      return (this.flags & 1) != 0;
   }

   private boolean hasRouter() {
      return (this.flags & 2) != 0;
   }

   private void setHasRouterFlag() {
      this.flags = (byte)(this.flags | 2);
   }

   private void setHasNoRouterFlag() {
      this.flags &= -3;
   }

   public boolean hasDomainName() {
      return (this.flags & 8) != 0;
   }

   public boolean hasServerName() {
      return (this.flags & 16) != 0;
   }

   private boolean hasDNSName() {
      return (this.flags & 32) != 0;
   }

   private void setDomainName(String aDomainName) {
      this.domainName = aDomainName;
      this.flags = (byte)(this.flags | 8);
   }

   private void setDNSName(String aDNSName) {
      this.dnsName = aDNSName;
      if (this.dnsName != null) {
         this.flags = (byte)(this.flags | 32);
      }

   }

   private void setServerName(String aServerName) {
      this.serverName = aServerName;
      this.flags = (byte)(this.flags | 16);
   }

   private boolean hasClusterAddress() {
      return (this.flags & 4) != 0;
   }

   private void setHasClusterAddressFlag() {
      this.flags = (byte)(this.flags | 4);
   }

   public boolean supportsTLS() {
      return false;
   }

   public JVMID() {
      this.localButWorkingInRemoteWay = false;
      this.preambleAsString = null;
      this.uris = new String[24];
   }

   JVMID(InetAddress address, int[] ports) {
      this(address, true);
      this.ports = ports;
   }

   private JVMID(InetAddress address, boolean bootstrapping) {
      this(address, bootstrapping, false);
   }

   private JVMID(InetAddress address, boolean bootstrapping, boolean remote) {
      this.localButWorkingInRemoteWay = false;
      this.preambleAsString = null;
      this.uris = new String[24];
      byte[] buf;
      if (bootstrapping) {
         this.differentiator = 0L;
         this.setDNSName(address.getHostName());
      } else {
         buf = LocalRJVM.getLocalRJVM().getPublicKey();
         this.differentiator = 0L;
         byte[] var5 = buf;
         int var6 = buf.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            byte aPk = var5[var7];
            this.differentiator = this.differentiator << 8 ^ this.differentiator ^ (long)aPk;
         }
      }

      this.hostAddress = address.getHostAddress();
      this.flags = (byte)(this.flags | 1);
      buf = address.getAddress();
      this.inetAddress = address;
      this.rawAddress = this.getUnique32BitNumber(this.hostAddress);
      this.router = null;
      if (this.getPublicPort() >= 0 && this.getPublicAddress() != null) {
         this.inSockAddr = new InetSocketAddress(this.getPublicAddress(), this.getPublicPort());
      }

      this.localButWorkingInRemoteWay = remote;
   }

   public boolean isServer() {
      return this.serverName != null || this.isBootstrapping();
   }

   public boolean isBootstrapping() {
      return this.differentiator == 0L;
   }

   public String getHostAddress() {
      return this.hostAddress;
   }

   public final String getClusterAddress() {
      return this.clusterAddress;
   }

   public final void setClusterAddress(String clusterAddress) {
      this.clusterAddress = clusterAddress;
      if (clusterAddress != null) {
         this.setHasClusterAddressFlag();
      }

   }

   public final int getPublicPort() {
      return this.getPort();
   }

   public final String getPublicAddress() {
      if (this.hasDNSName()) {
         return this.getDNSName();
      } else {
         return this.hasHostAddress() ? this.getHostAddress() : this.address().getHostAddress();
      }
   }

   public final String getAddress() {
      return this.address().getHostAddress();
   }

   public final InetAddress getInetAddress() {
      return this.address();
   }

   public InetSocketAddress getPublicInetAddress() {
      return this.inSockAddr;
   }

   final int getPort() {
      return this.getDefaultPort();
   }

   public final Identity getPersistentIdentity() {
      if (this.persistentIdentity == null && !this.isBootstrapping()) {
         long rawAddressLong;
         if (RJVMEnvironment.getEnvironment().isServer() && localID != null && this.isLocal()) {
            rawAddressLong = (long)this.rawAddress;
            this.persistentIdentity = new Identity((rawAddressLong << 32) + (long)(ServerChannelManager.findLocalServerPort(ProtocolManager.getDefaultProtocol()) << 16) + (long)this.serverName.hashCode());
         } else {
            rawAddressLong = (long)this.rawAddress;
            this.persistentIdentity = new Identity((rawAddressLong << 32) + (long)(this.getDefaultPort() << 16) + (long)(this.serverName == null ? "WebLogicClient" : this.serverName).hashCode());
         }
      }

      return this.persistentIdentity;
   }

   public final Identity getTransientIdentity() {
      if (this.transientIdentity == null && !this.isBootstrapping()) {
         this.transientIdentity = new Identity(this.differentiator ^ (long)this.rawAddress);
      }

      return this.transientIdentity;
   }

   public final boolean isLocal() {
      return this.equals(localID());
   }

   private boolean isAsLocal() {
      return this.isLocal() || this.localButWorkingInRemoteWay;
   }

   public final boolean isClient() {
      return this.serverName == null;
   }

   /** @deprecated */
   @Deprecated
   public InetAddress address() {
      return this.address(false);
   }

   public InetAddress address(boolean allowUnknownHost) {
      if (this.inetAddress == null) {
         try {
            if (System.getSecurityManager() == null) {
               this.inetAddress = InetAddress.getByName(this.hostAddress);
            } else {
               try {
                  this.inetAddress = (InetAddress)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                     public InetAddress run() throws Exception {
                        return InetAddress.getByName(JVMID.this.hostAddress);
                     }
                  });
               } catch (PrivilegedActionException var4) {
                  Exception cause = var4.getException();
                  if (cause instanceof UnknownHostException) {
                     throw (UnknownHostException)cause;
                  }

                  throw new RuntimeException(cause);
               }
            }
         } catch (UnknownHostException var5) {
            this.handleUnknownHostException(var5, allowUnknownHost);
         }
      }

      return this.inetAddress;
   }

   private void handleUnknownHostException(UnknownHostException uhe, boolean allowUnknownHost) throws NestedError {
      if (allowUnknownHost) {
         try {
            this.inetAddress = InetAddress.getByName(DUMMY_IP);
            if (isDebugEnabled()) {
               debugJVMID.debug("allowUnknowHost is set to true so setting JVMID inetAddress to " + DUMMY_IP + " for " + uhe);
            }

            return;
         } catch (UnknownHostException var4) {
         }
      }

      throw new NestedError("This address was valid earlier, but now we get: ", uhe);
   }

   int getPort(Protocol protocol) {
      if (protocol == null) {
         return -1;
      } else {
         byte index = ProtocolManager.getRealProtocol(protocol).toByte();
         return this.ports != null && this.ports.length > index ? this.ports[index] : -1;
      }
   }

   private int getDefaultPort() {
      if (this.ports != null) {
         for(byte idx = 0; idx < 6; ++idx) {
            if (this.ports.length > idx && this.ports[idx] != -1) {
               return this.ports[idx];
            }
         }
      }

      return -1;
   }

   private int getDefaultProtocolIndex() {
      if (this.ports != null) {
         for(byte idx = 0; idx < 6; ++idx) {
            if (this.ports.length > idx && this.ports[idx] != -1) {
               return idx;
            }
         }
      }

      return -1;
   }

   public int getConfiguredProtocolIndex() {
      return this.ports != null && this.ports.length > 6 && this.ports[6] != -1 ? 6 : this.getDefaultProtocolIndex();
   }

   public int[] ports() {
      return this.ports;
   }

   public void setRouter(JVMID router) {
      this.router = router;
      if (router != null) {
         this.setHasRouterFlag();
      } else {
         this.setHasNoRouterFlag();
      }

   }

   public JVMID getRouter() {
      return this.router;
   }

   public boolean precedes(JVMID other) {
      return this.differentiator > other.differentiator || this.differentiator == other.differentiator && this.rawAddress > other.rawAddress;
   }

   public int compareTo(Object object) {
      try {
         JVMID other = (JVMID)object;
         if (this.differentiator == other.differentiator) {
            if (this.rawAddress == other.rawAddress) {
               return 0;
            } else {
               return this.rawAddress < other.rawAddress ? -1 : 1;
            }
         } else {
            return this.differentiator < other.differentiator ? -1 : 1;
         }
      } catch (ClassCastException var3) {
         throw new AssertionError(var3);
      }
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o instanceof JVMID) {
         JVMID other = (JVMID)o;
         return this.equals(other.differentiator, other.rawAddress, other.localButWorkingInRemoteWay);
      } else {
         return false;
      }
   }

   private boolean isMe() {
      return localID != null && this.differentiator == localID.differentiator && this.rawAddress == localID.rawAddress;
   }

   private boolean equals(long differentiator, int rawAddress, boolean localButWorkingInRemoteWay) {
      return this.differentiator == differentiator && this.rawAddress == rawAddress && this.localButWorkingInRemoteWay == localButWorkingInRemoteWay;
   }

   Object identityWithChannel() {
      if (this.channelId == null) {
         this.channelId = new ChannelIdentity(this);
      }

      return this.channelId;
   }

   public long getDifferentiator() {
      return this.differentiator;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public String getChannelName() {
      return "Default";
   }

   public String getDNSName() {
      return this.dnsName;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getHostURI(Protocol protocol) {
      if (this.uris[protocol.toByte()] == null) {
         StringBuilder sb = new StringBuilder(96);
         JVMID theJVMID = this.router == null ? this : this.router;
         InetAddress address = theJVMID.address();
         sb.append(protocol.getAsURLPrefix());
         if (address instanceof Inet6Address && this.isValidPort(theJVMID.getHostAddress().indexOf(58)) && theJVMID.getHostAddress().indexOf(91) == -1) {
            sb.append("://[").append(theJVMID.getHostAddress()).append("]:");
         } else {
            sb.append("://").append(theJVMID.getHostAddress()).append(':');
         }

         sb.append(theJVMID.getPort(protocol));
         this.uris[protocol.toByte()] = sb.toString();
      }

      return this.uris[protocol.toByte()];
   }

   public String getHostURI() {
      JVMID theJVMID = this.router == null ? this : this.router;
      String hostID;
      if (theJVMID.getPublicPort() == -1) {
         hostID = theJVMID.getHostURI(ProtocolManager.getProtocolByName(theJVMID.getProtocolPrefix()));
      } else {
         hostID = theJVMID.getHostURI(ProtocolManager.getProtocolByIndex(theJVMID.getConfiguredProtocolIndex()));
      }

      if (isDebugEnabled()) {
         debugJVMID.debug("getHostURI() configured public port:" + theJVMID.getPublicPort() + ", generated URL:" + hostID);
      }

      return hostID;
   }

   public int hashCode() {
      if (this.hashCodeValue == 0) {
         this.hashCodeValue = (int)this.differentiator ^ this.rawAddress;
      }

      return this.hashCodeValue;
   }

   public String toString() {
      UnsyncStringBuffer b = new UnsyncStringBuffer(Long.toString(this.differentiator));
      b.append((char)(this.isBootstrapping() ? 'B' : (this.isServer() ? 'S' : 'C'))).append(':');
      b.append(this.hostAddress);
      if (this.ports != null) {
         b.append(":[");

         for(int i = 0; i < this.ports.length; ++i) {
            if (i > 0) {
               b.append(',');
            }

            b.append(this.ports[i]);
         }

         b.append(']');
      }

      if (this.hasClusterAddress()) {
         b.append(":").append(this.clusterAddress);
      }

      if (this.domainName != null) {
         b.append(':').append(this.domainName);
      }

      if (this.serverName != null) {
         b.append(':').append(this.serverName);
      }

      if (this.hasRouter()) {
         b.append("R:").append(this.router.toString());
      }

      if (this.localButWorkingInRemoteWay) {
         b.append("Local-Remote");
      }

      return b.toString();
   }

   public String toPrettyString() {
      try {
         UnsyncStringBuffer b = new UnsyncStringBuffer(addressToString(this.address()));
         if (this.domainName != null) {
            b.append(':').append(this.domainName);
         }

         if (this.serverName != null) {
            b.append(':').append(this.serverName);
         }

         if (this.ports != null) {
            for(byte i = 0; i < this.ports.length; ++i) {
               if (this.ports[i] != -1) {
                  b.append(' ').append(ProtocolManager.getProtocolByIndex(i).getProtocolName());
                  b.append(':').append(this.ports[i]);
               }
            }
         }

         b.append(' ').append(this.differentiator);
         if (this.hasClusterAddress()) {
            b.append(":");
            b.append(this.clusterAddress);
         }

         if (this.localButWorkingInRemoteWay) {
            b.append("Local-Remote");
         }

         return b.toString();
      } catch (SecurityException var3) {
         return this.toString();
      }
   }

   static String addressToString(InetAddress address) {
      if (localHost == null) {
         localHost = AddressUtils.getLocalHost();
      }

      return address.equals(localHost) ? "localhost/127.0.0.1" : address.getHostAddress();
   }

   private void debugWriteExternal(ObjectOutput out) {
      debugJVMID.debug("writeExternal(" + out.getClass().getName() + "): " + this.toString());
      debugJVMID.debug("Env.isServer: " + RJVMEnvironment.getEnvironment().isServer());
      debugJVMID.debug("isLocal: " + this.isLocal() + "\nisLocalRemote: " + this.localButWorkingInRemoteWay + "\nisServer(): " + this.isServer());
      if (this.getRouter() != null) {
         debugJVMID.debug("\ngetRouter(): " + this.getRouter());
      } else {
         debugJVMID.debug("router is null");
      }

      if (localID.getDomainName() != null) {
         debugJVMID.debug("\nequaldomain: " + localID().getDomainName().equals(this.domainName));
      } else {
         debugJVMID.debug("domain name is null");
      }

      debugJVMID.debug("\nlocalID.serverName: " + localID().getServerName() + "\nserverName:" + this.serverName);
      if (out instanceof ServerChannelStream) {
         debugJVMID.debug("ServerChannel: " + ((ServerChannelStream)out).getServerChannel());
      } else {
         debugJVMID.debug("out is NOT ServerChannelStream");
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (RJVMEnvironment.getEnvironment().isServer()) {
         if (out instanceof ServerChannelStream) {
            ServerChannel sc = ((ServerChannelStream)out).getServerChannel();
            if (sc != null) {
               if (this.isAsLocal()) {
                  this.writeLocalForServerChannel(out, sc);
                  out.writeBoolean(this.localButWorkingInRemoteWay);
                  return;
               }

               if (this.isServer() && this.getRouter() == null && localID().getDomainName().equals(this.domainName) && (sc = ServerChannelManager.lookupServerChannel(this, sc.getChannelName())) != null) {
                  this.writeTranslatedForServerChannel(out, sc);
                  out.writeBoolean(this.localButWorkingInRemoteWay);
                  return;
               }
            }
         }

         if (this.isAsLocal()) {
            this.writeLocalForServerChannel(out, ServerChannelManager.findDefaultLocalServerChannel());
            out.writeBoolean(this.localButWorkingInRemoteWay);
            return;
         }
      }

      if (this.ports == null && !this.isClient()) {
         throw new AssertionError("FIXME andyp 1-Jan-04 -- unreplaced JVMID: " + this);
      } else {
         out.writeByte(this.flags);
         out.writeLong(this.differentiator);
         if (this.hasHostAddress()) {
            out.writeUTF(this.hostAddress);
         }

         if (this.hasDNSName()) {
            out.writeUTF(this.dnsName);
         }

         out.writeInt(this.rawAddress);
         if (this.ports == null) {
            out.writeInt(0);
         } else {
            int numPorts = 7;
            out.writeInt(numPorts);

            for(int i = 0; i < numPorts; ++i) {
               out.writeInt(this.ports[i]);
            }
         }

         if (this.hasDomainName()) {
            out.writeUTF(this.domainName);
         }

         if (this.hasServerName()) {
            out.writeUTF(this.serverName);
         }

         if (this.hasRouter()) {
            this.router.writeExternal(out);
         }

         this.writeClusterAddress(out);
         out.writeBoolean(this.localButWorkingInRemoteWay);
      }
   }

   private void writeClusterAddress(ObjectOutput out) throws IOException {
      if (this.hasClusterAddress() && this.clusterAddress != null) {
         if (!(out instanceof ClusterMessagePeerInfoable)) {
            out.writeUTF(this.clusterAddress);
         } else {
            out.writeUTF("");
         }
      }

   }

   private void writeLocalForServerChannel(ObjectOutput out, ServerChannel nap) throws IOException {
      Debug.assertion(this.isAsLocal());
      out.writeByte(this.flags);
      out.writeLong(this.differentiator);
      out.writeUTF(nap.getPublicAddressResolvedIfNeeded());
      if (this.hasDNSName()) {
         out.writeUTF(nap.getPublicAddressResolvedIfNeeded());
      }

      out.writeInt(this.rawAddress);
      int numPorts = 7;
      out.writeInt(numPorts);
      int aindex = nap.getProtocol().toByte();
      int pindex = ProtocolManager.getRealProtocol(nap.getProtocol()).toByte();
      int hindex = nap.supportsHttp() ? (nap.supportsTLS() ? 3 : 1) : -1;

      for(int i = 0; i < numPorts; ++i) {
         if (i != pindex && i != aindex && i != hindex) {
            out.writeInt(-1);
         } else {
            out.writeInt(nap.getPublicPort());
         }
      }

      if (this.hasDomainName()) {
         out.writeUTF(this.domainName);
      }

      if (this.hasServerName()) {
         out.writeUTF(this.serverName);
      }

      if (this.hasRouter()) {
         this.router.writeExternal(out);
      }

      this.writeClusterAddress(out);
   }

   private void writeTranslatedForServerChannel(ObjectOutput out, ServerChannel nap) throws IOException {
      out.writeByte(1 | (nap.hasPublicAddress() ? 32 : 0) | (this.hasServerName() ? 16 : 0) | (this.hasDomainName() ? 8 : 0));
      out.writeLong(this.differentiator);
      out.writeUTF(nap.getPublicAddress());
      if (nap.hasPublicAddress()) {
         out.writeUTF(nap.getPublicAddress());
      }

      out.writeInt(this.rawAddress);
      int numPorts = 7;
      out.writeInt(numPorts);
      int aindex = nap.getProtocol().toByte();
      int pindex = ProtocolManager.getRealProtocol(nap.getProtocol()).toByte();
      int hindex = nap.supportsHttp() ? (nap.supportsTLS() ? 3 : 1) : -1;

      for(int i = 0; i < numPorts; ++i) {
         if (i != pindex && i != hindex && i != aindex) {
            out.writeInt(-1);
         } else {
            out.writeInt(nap.getPublicPort());
         }
      }

      if (this.hasDomainName()) {
         out.writeUTF(this.getDomainName());
      }

      if (this.hasServerName()) {
         out.writeUTF(this.getServerName());
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.flags = in.readByte();
      this.differentiator = in.readLong();
      if (this.hasHostAddress()) {
         this.hostAddress = in.readUTF();
      }

      if (this.hasDNSName()) {
         this.dnsName = in.readUTF();
      }

      this.rawAddress = in.readInt();
      this.ports = null;
      int numPorts = in.readInt();
      if (numPorts > 0) {
         if (numPorts > 7) {
            throw new IOException("Corrupted JVMID: Number of ports exceeded valid value");
         }

         this.ports = new int[numPorts];
         this.preambleAsString = null;

         for(int i = 0; i < numPorts; ++i) {
            this.ports[i] = in.readInt();
         }
      }

      if (this.hasDomainName()) {
         this.domainName = in.readUTF();
      }

      if (this.hasServerName()) {
         this.serverName = in.readUTF();
      }

      if (this.hasRouter()) {
         this.router = new JVMID();
         this.router.readExternal(in);
      }

      if (this.hasClusterAddress()) {
         this.clusterAddress = in.readUTF();
      }

      if (this.getPublicPort() >= 0 && this.getPublicAddress() != null) {
         this.inSockAddr = new InetSocketAddress(this.getPublicAddress(), this.getPublicPort());
      }

      if (this.setInboundChannelInIdentity(in)) {
         this.setChannelIdentity((ServerChannelStream)in);
      }

      try {
         boolean readLocalButWorkingInRemoteWay = in.readBoolean();
         if (this.isMe()) {
            this.localButWorkingInRemoteWay = readLocalButWorkingInRemoteWay;
         }
      } catch (Throwable var4) {
         this.localButWorkingInRemoteWay = false;
      }

   }

   private boolean setInboundChannelInIdentity(ObjectInput objectInput) {
      if (objectInput instanceof ServerChannelStream && RJVMEnvironment.getEnvironment().isServer()) {
         if (this.isClient()) {
            return true;
         }

         ServerChannel sc = ((ServerChannelStream)objectInput).getServerChannel();
         if (sc != null) {
            return !sc.equals(sc.getProtocol() != null ? ServerChannelManager.findOutboundServerChannel(sc.getProtocol()) : ServerChannelManager.findDefaultLocalServerChannel());
         }
      }

      return false;
   }

   void cleanupPorts() {
      if (this.router != null) {
         this.router.cleanupPorts();
      }

      if (this.ports != null) {
         if (this.ports.length > 0 && this.ports.length > 1 && this.ports[0] != -1) {
            this.ports[1] = -1;
         }

         if (this.ports.length > 2 && this.ports.length > 3 && this.ports[2] != -1) {
            this.ports[3] = -1;
         }

      }
   }

   public String objectToString() {
      UnsyncStringBuffer b = new UnsyncStringBuffer(Long.toString(this.differentiator));
      if (!this.isServer()) {
         b.append("/").append(Integer.toString(this.rawAddress));
         b.append("/").append('0');
         if (this.router == null) {
            return b.toString();
         } else {
            b.append("/").append(this.router.objectToString());
            return b.toString();
         }
      } else {
         if (this.preambleAsString == null) {
            b.append("/").append(this.hostAddress);
            b.append("/").append(this.dnsName);
            b.append("/").append(this.clusterAddress);
            b.append("/").append(Integer.toString(this.rawAddress));
            if (this.ports == null) {
               b.append("/").append(0);
            } else {
               b.append("/").append(this.ports.length);
               int[] var2 = this.ports;
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  int port = var2[var4];
                  b.append("/").append(port);
               }
            }

            b.append("/").append(this.domainName);
            b.append("/").append(this.serverName);
            if (this.router != null) {
               b.append("/").append(this.router.objectToString());
            }

            this.preambleAsString = b.toString();
         }

         return this.preambleAsString;
      }
   }

   public String getProtocolPrefix() {
      return ProtocolManager.getDefaultProtocol().getAsURLPrefix();
   }

   public boolean matchAddressPortAndProtocolIndex(InetAddress address, int port, int protocolIndex) {
      return this.address().equals(address) && this.getPort() == port && this.getDefaultProtocolIndex() == protocolIndex;
   }

   boolean matchAddressAndPort(InetAddress address, int port, Protocol protocol) {
      if (this.address().equals(address) && this.getPort(protocol) == port) {
         return true;
      } else {
         return protocol.equals(ProtocolManager.getDefaultAdminProtocol()) && this.address().equals(address) && this.getPort(RJVMManager.getRJVMManager().getProtocol((byte)6)) == port;
      }
   }

   ClusterInfo generate61ClusterInfo(String protocolName, boolean isAdmin) {
      String cA = this.clusterAddress;
      if (cA == null) {
         cA = this.getHostAddress();
      }

      int protocolMask = 0;
      int port = this.getPort(RJVMManager.getRJVMManager().getProtocol((byte)0));
      if (port != -1) {
         protocolMask |= 1;
      }

      int sslPort = this.getPort(RJVMManager.getRJVMManager().getProtocol((byte)2));
      if (sslPort != -1) {
         protocolMask |= 4;
      }

      int adminPort = this.getPort(RJVMManager.getRJVMManager().getProtocol((byte)6));
      if (adminPort != -1) {
         protocolMask |= 64;
      }

      return new ClusterInfo(this.domainName, cA, "Unknown", port, sslPort, adminPort, protocolMask, protocolName, isAdmin);
   }

   private static String constructURL(Protocol protocol, String address, int port) {
      UnsyncStringBuffer sb = new UnsyncStringBuffer(protocol.getAsURLPrefix());
      if (address.indexOf(58) == -1) {
         sb.append("://").append(address).append(':').append(port);
      } else {
         sb.append("://").append(address);
      }

      return sb.toString();
   }

   public String getClusterURL(ObjectInput in) {
      return in instanceof ClusterInfoable ? this.getClusterURL(((ClusterInfoable)in).getClusterInfo()) : this.getClusterURL((ClusterInfo)null);
   }

   private String getClusterURL(ClusterInfo ci) {
      int port = -1;
      String cA = null;
      Protocol lUProtocol = null;
      AuthenticatedSubject user = SecurityServiceManager.getCurrentSubject(kernelId);
      byte userQOS;
      if (user != null) {
         userQOS = user.getQOS();
      } else {
         userQOS = 101;
      }

      if (ci != null && ci instanceof ClusterInfo90) {
         return ((ClusterInfo90)ci).getUrl();
      } else {
         if (ci != null && ci.getDomainName().equals(this.domainName)) {
            lUProtocol = ProtocolManager.getProtocolByName(ci.getProtocolName());
            cA = ci.getClusterAddress();
            if (userQOS == 103) {
               lUProtocol = lUProtocol.upgrade();
               port = ci.getAdminPort();
            } else {
               switch (lUProtocol.getQOS()) {
                  case 101:
                     if (userQOS == 101) {
                        port = ci.getPort();
                        if (port != -1 && ci.isProtocolEnabled(lUProtocol.toByte())) {
                           break;
                        }
                     }

                     lUProtocol = lUProtocol.upgrade();
                  case 102:
                     port = ci.getSSLPort();
                     if (port != -1 && ci.isProtocolEnabled(lUProtocol.toByte())) {
                        break;
                     }
                  case 103:
                     port = ci.getAdminPort();
               }
            }

            if (!ci.isProtocolEnabled(lUProtocol.toByte())) {
               port = -1;
            }
         }

         if (port == -1) {
            cA = this.getClusterAddress() == null ? this.getHostAddress() : this.getClusterAddress();
            lUProtocol = ProtocolManager.getDefaultProtocol();
            switch (userQOS) {
               case 101:
                  if (this.getPort(lUProtocol) != -1) {
                     break;
                  }
               case 102:
                  lUProtocol = lUProtocol.upgrade();
                  if (this.getPort(lUProtocol) != -1) {
                     break;
                  }
               case 103:
                  if (this.getPort(ProtocolManager.getDefaultAdminProtocol()) != -1) {
                     lUProtocol = ProtocolManager.getDefaultAdminProtocol();
                  } else {
                     lUProtocol = lUProtocol.upgrade();
                  }
            }

            port = this.getPort(lUProtocol);
         }

         String result = constructURL(lUProtocol, cA, port);
         return result;
      }
   }

   private void setChannelIdentity(ServerChannelStream scs) {
      ServerChannel sc = scs.getServerChannel();
      if (sc != null) {
         this.channelId = new ChannelIdentity(this, sc);
      }

   }

   private static final class ChannelIdentity {
      private final JVMID id;
      private final ServerChannel sc;

      private ChannelIdentity(JVMID id) {
         this.id = id;
         this.sc = null;
      }

      private ChannelIdentity(JVMID id, ServerChannel sc) {
         this.id = id;
         this.sc = sc;
      }

      public int hashCode() {
         return this.id.hashCode();
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            if (o instanceof ChannelIdentity) {
               ChannelIdentity ci = (ChannelIdentity)o;
               if (this.equalsRemote(ci.id) && this.equalsInboundChannel(ci.sc)) {
                  return true;
               }
            }

            return false;
         }
      }

      private boolean equalsRemote(JVMID other) {
         return this.id.equals(other) && this.id.getPublicPort() == other.getPublicPort() && this.id.getPublicAddress().equals(other.getPublicAddress()) && this.id.getDefaultProtocolIndex() == other.getDefaultProtocolIndex();
      }

      private boolean equalsInboundChannel(ServerChannel other) {
         return this.sc == null && other == null || this.sc != null && other != null && this.sc.getPublicPort() == other.getPublicPort() && this.sc.getPublicAddress().equals(other.getPublicAddress()) && this.sc.getProtocol().toByte() == other.getProtocol().toByte();
      }

      public String toString() {
         return this.id.toString() + " on [" + (this.sc != null ? this.sc.toString() : this.id.getPublicAddress() + ':' + this.id.getPublicPort()) + ']';
      }

      // $FF: synthetic method
      ChannelIdentity(JVMID x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      ChannelIdentity(JVMID x0, ServerChannel x1, Object x2) {
         this(x0, x1);
      }
   }
}
