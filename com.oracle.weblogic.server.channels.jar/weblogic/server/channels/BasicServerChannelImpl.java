package weblogic.server.channels;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import weblogic.kernel.NetworkAccessPointMBeanStub;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.UnknownProtocolException;
import weblogic.rmi.spi.Channel;
import weblogic.server.GlobalServiceLocator;
import weblogic.socket.SocketRuntimeFactory;
import weblogic.utils.net.AddressUtils;

public class BasicServerChannelImpl implements Externalizable, ServerChannel, ServerChannelConstants {
   static final long serialVersionUID = 3682806476156685669L;
   private static final boolean DEBUG = false;
   protected byte flags;
   protected String address;
   private String resolvedAddress;
   protected String channelName;
   protected int listenPort;
   protected int publicPort;
   protected String publicAddress;
   protected int rawAddress;
   protected Protocol protocol;
   protected int priority;
   protected int weight;
   protected transient InetAddress inetAddress;
   protected transient InetSocketAddress inSockAddress;
   protected transient NetworkAccessPointMBean config;
   protected transient boolean implicitChannel;
   protected transient boolean isLocal;
   protected transient String displayName;
   private transient boolean t3SenderQueueDisabled;
   private String resolvedPublicAddr;
   private static final String PROTOCOL_T3 = "t3";
   private static final String PROTOCOL_T3S = "t3s";
   private static final String PROTOCOL_ADMIN = "admin";
   protected transient String associatedVirtualTargetName;
   private static int PORT_VALUE_NEED_TO_HANDLE = -1;

   public BasicServerChannelImpl() {
      this.listenPort = -1;
      this.publicPort = -1;
   }

   public int hashCode() {
      return this.channelName.hashCode() ^ this.rawAddress ^ this.listenPort ^ this.protocol.getAsURLPrefix().hashCode();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BasicServerChannelImpl)) {
         return false;
      } else {
         BasicServerChannelImpl other = (BasicServerChannelImpl)o;
         return other.channelName.equals(this.channelName) && other.rawAddress == this.rawAddress && other.listenPort == this.listenPort && other.protocol.getAsURLPrefix().equals(this.protocol.getAsURLPrefix());
      }
   }

   public boolean requiresNAT() {
      return this.hasPublicAddress() || this.hasPublicPort();
   }

   public boolean supportsTLS() {
      return (this.flags & 32) != 0;
   }

   boolean isImplicitChannel() {
      return this.implicitChannel;
   }

   boolean isLocal() {
      return this.isLocal;
   }

   public boolean supportsHttp() {
      return (this.flags & 16) != 0;
   }

   public NetworkAccessPointMBean getConfig() {
      return this.config;
   }

   public final String getAddress() {
      return this.address;
   }

   public final String getResolvedAddress() {
      if (this.resolvedAddress == null) {
         if (this.isLocal) {
            if (this.inetAddress == null) {
               InetAddress ia = AddressUtils.getIPForLocalHost();
               this.resolvedAddress = ia.getHostAddress();
            } else {
               this.resolvedAddress = this.inetAddress.getHostAddress();
            }
         } else {
            this.resolvedAddress = this.address == null ? this.publicAddress : this.address;
         }
      }

      return this.resolvedAddress;
   }

   public String getPublicAddressResolvedIfNeeded() {
      if (!this.getResolveDNSName()) {
         return this.getPublicAddress();
      } else if (!this.isT3ProtocolFamily()) {
         return this.getPublicAddress();
      } else if (this.resolvedPublicAddr != null) {
         return this.resolvedPublicAddr;
      } else {
         if (this.hasPublicAddress() && this.publicAddress != null) {
            try {
               this.resolvedPublicAddr = InetAddress.getByName(this.publicAddress).getHostAddress();
            } catch (UnknownHostException var2) {
            }
         } else {
            this.resolvedPublicAddr = this.getResolvedAddress();
         }

         return this.resolvedPublicAddr;
      }
   }

   private boolean isT3ProtocolFamily() {
      Protocol p = this.protocol;
      if (this.protocol.getProtocolName().equalsIgnoreCase("admin")) {
         p = ProtocolManager.getRealProtocol(this.protocol);
      }

      return p.getProtocolName().equalsIgnoreCase("t3") || p.getProtocolName().equalsIgnoreCase("t3s");
   }

   public final int getRawAddress() {
      return this.rawAddress;
   }

   public String getChannelName() {
      return this.channelName;
   }

   public int getChannelWeight() {
      return this.weight;
   }

   public String getPublicAddress() {
      return this.publicAddress != null ? this.publicAddress : this.getAddress();
   }

   public int getPublicPort() {
      return this.hasPublicPort() ? this.publicPort : this.listenPort;
   }

   public int getPort() {
      return this.listenPort;
   }

   public Protocol getProtocol() {
      return this.protocol;
   }

   public String getProtocolPrefix() {
      return this.protocol.getAsURLPrefix();
   }

   public String getProtocolName() {
      return this.protocol.getProtocolName();
   }

   /** @deprecated */
   @Deprecated
   public final InetAddress address() {
      return this.getInetAddress();
   }

   public InetSocketAddress getPublicInetAddress() {
      return this.inSockAddress;
   }

   public final String getClusterAddress() {
      String ca = this.getConfig().getClusterAddress();
      return ca == null ? this.getPublicAddress() : ca;
   }

   public InetAddress getInetAddress() {
      return this.inetAddress;
   }

   public String getListenerKey() {
      return this.getResolvedAddress().toLowerCase() + this.getPort();
   }

   public boolean hasPublicAddress() {
      return (this.flags & 1) != 0;
   }

   protected void setPublicAddress(String addr) {
      if (addr != null && addr.length() > 0 && !addr.equals(this.address)) {
         this.publicAddress = addr;
         this.flags = (byte)(this.flags | 1);
      }

   }

   protected boolean hasListenAddress() {
      return (this.flags & 8) != 0;
   }

   protected void setListenAddress(String la) {
      if (la != null && la.length() > 0) {
         this.address = la;
         this.flags = (byte)(this.flags | 8);
      }

   }

   protected boolean hasListenPort() {
      return (this.flags & 2) != 0;
   }

   protected void setListenPort(int port) {
      if (port != -1) {
         this.listenPort = port;
         this.flags = (byte)(this.flags | 2);
      }

   }

   protected boolean hasPublicPort() {
      return (this.flags & 4) != 0;
   }

   protected void setPublicPort(int port) {
      if (port != -1 && port != this.listenPort) {
         this.publicPort = port;
         this.flags = (byte)(this.flags | 4);
      }

   }

   public boolean isSDPEnabled() {
      return (this.flags & 64) != 0;
   }

   protected void setSDPEnabled(boolean enable) {
      if (enable) {
         this.flags = (byte)(this.flags | 64);
      } else {
         this.flags &= -65;
      }

   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      b.append(this.channelName);
      b.append(':').append(this.getProtocol().getProtocolName()).append('(').append(this.getProtocol().getAsURLPrefix()).append(')');
      b.append(':').append(this.address);
      b.append(":" + this.listenPort);
      b.append(':').append(this.publicAddress);
      b.append(":" + this.publicPort);
      return b.toString();
   }

   public int compareTo(Object o) {
      BasicServerChannelImpl other = (BasicServerChannelImpl)o;
      int d = this.priority - other.priority;
      if (d == 0 && this.isImplicitChannel() != other.isImplicitChannel()) {
         d = this.isImplicitChannel() ? 1 : -1;
      }

      if (d == 0) {
         NetworkAccessPointMBean otherConfig = other.getConfig();
         if (this.config.isOutboundEnabled() && !otherConfig.isOutboundEnabled()) {
            d = -1;
         } else if (!this.config.isOutboundEnabled() && otherConfig.isOutboundEnabled()) {
            d = 1;
         }
      }

      boolean b;
      if (d == 0) {
         b = this.getChannelName().startsWith("Default");
         if (b != other.getChannelName().startsWith("Default")) {
            d = b ? -1 : 1;
         }
      }

      if (d == 0) {
         b = this.requiresNAT();
         if (b != other.requiresNAT()) {
            d = b ? 1 : -1;
         }
      }

      if (d == 0) {
         b = this.address() != null && this.address().isLoopbackAddress();
         if (b != (other.address() != null && other.address().isLoopbackAddress())) {
            d = b ? 1 : -1;
         }
      }

      if (d == 0) {
         d = this.getChannelName().compareTo(other.getChannelName());
      }

      return d;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(this.flags);
      out.writeUTF(this.channelName);
      if (this.hasListenAddress()) {
         if (this.getResolveDNSName() && this.isT3ProtocolFamily()) {
            out.writeUTF(this.getResolvedAddress());
         } else {
            out.writeUTF(this.address);
         }
      }

      out.writeInt(this.rawAddress);
      out.writeObject(this.protocol);
      out.writeInt(this.priority);
      out.writeInt(this.weight);
      if (this.hasPublicAddress() || !this.hasListenAddress()) {
         out.writeUTF(this.getPublicAddressResolvedIfNeeded());
      }

      if (this.hasListenPort()) {
         out.writeInt(this.listenPort);
      }

      if (this.hasPublicPort()) {
         out.writeInt(this.publicPort);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.flags = in.readByte();
      this.channelName = in.readUTF();
      if (this.hasListenAddress()) {
         this.address = in.readUTF();
      }

      this.rawAddress = in.readInt();
      this.protocol = (Protocol)in.readObject();
      this.priority = in.readInt();
      this.weight = in.readInt();
      if (this.hasPublicAddress() || !this.hasListenAddress()) {
         this.publicAddress = in.readUTF();
      }

      if (this.hasListenPort()) {
         this.listenPort = in.readInt();
      }

      if (this.hasPublicPort()) {
         this.publicPort = in.readInt();
      }

      if (this.getPublicPort() >= 0) {
         this.inSockAddress = new InetSocketAddress(this.getPublicAddress(), this.getPublicPort());
      }

   }

   BasicServerChannelImpl(NetworkAccessPointMBean napMbean, ServerIdentity id) throws UnknownHostException, UnknownProtocolException {
      this(napMbean, ProtocolManager.findProtocol(napMbean.getProtocol()), napMbean.getName(), id, (String)null);
   }

   public BasicServerChannelImpl(NetworkAccessPointMBean napMbean, Protocol protocol, ServerIdentity id) throws UnknownHostException {
      this(napMbean, protocol, encodeName(protocol, napMbean.getName()), id, (String)null);
      this.displayName = napMbean.getName();
   }

   protected static final String encodeName(Protocol protocol, String name) {
      return name + "[" + protocol.getAsURLPrefix() + "]";
   }

   String getRealName() {
      return this.displayName != null ? this.displayName : this.channelName;
   }

   public String getAssociatedVirtualTargetName() {
      return this.associatedVirtualTargetName;
   }

   protected BasicServerChannelImpl(NetworkAccessPointMBean napMbean, Protocol protocol, String channelName, ServerIdentity id, String address) throws UnknownHostException {
      this.listenPort = -1;
      this.publicPort = -1;
      if (protocol.isSecure()) {
         this.flags = (byte)(this.flags | 32);
      }

      if (!protocol.getProtocolName().equalsIgnoreCase(napMbean.getProtocol())) {
         this.implicitChannel = true;
      }

      this.protocol = protocol;
      this.priority = protocol.getHandler().getPriority();
      this.channelName = channelName;
      this.config = napMbean;
      this.setListenAddress(address);
      this.update(id);
   }

   public static ServerChannel createBootstrapChannel(String protocol) throws UnknownHostException {
      SocketRuntimeFactory scs = (SocketRuntimeFactory)GlobalServiceLocator.getServiceLocator().getService(SocketRuntimeFactory.class, new Annotation[0]);
      if (scs == null) {
         throw new RuntimeException("Implementation of ServerChannelService not found in classpath");
      } else {
         return scs.createBootstrapChannel(protocol);
      }
   }

   private boolean isSecure(NetworkAccessPointMBean nap) {
      byte qos = ProtocolManager.getProtocolByName(nap.getProtocol()).getQOS();
      return qos == 102 || qos == 103;
   }

   private int findListenPort(NetworkAccessPointMBean nap) {
      int listenPort = nap.getListenPort();
      if (nap instanceof NetworkAccessPointMBeanStub) {
         return !nap.isEnabled() ? PORT_VALUE_NEED_TO_HANDLE : listenPort;
      } else {
         if (listenPort == PORT_VALUE_NEED_TO_HANDLE) {
            ServerTemplateMBean parent = (ServerTemplateMBean)nap.getParent();
            listenPort = this.isSecure(nap) ? parent.getSSL().getListenPort() : parent.getListenPort();
         }

         return listenPort;
      }
   }

   private int findPublicPort(NetworkAccessPointMBean nap) {
      int publicPort = nap.getPublicPort();
      if (nap instanceof NetworkAccessPointMBeanStub) {
         return !nap.isEnabled() ? PORT_VALUE_NEED_TO_HANDLE : publicPort;
      } else {
         if (publicPort == PORT_VALUE_NEED_TO_HANDLE) {
            publicPort = this.findListenPort(nap);
         }

         return publicPort;
      }
   }

   void update(ServerIdentity id) throws UnknownHostException {
      this.setListenAddress(this.config.getListenAddress());
      InetAddress ia = null;
      this.setListenPort(this.findListenPort(this.config));
      this.setPublicPort(this.findPublicPort(this.config));
      this.setPublicAddress(this.config.getPublicAddress());
      this.setSDPEnabled(this.config.isSDPEnabled());
      if (!this.config.isHttpEnabledForThisProtocol() && !this.config.isTunnelingEnabled()) {
         this.flags &= -17;
      } else {
         this.flags = (byte)(this.flags | 16);
      }

      if (id != null && id.isLocal()) {
         this.isLocal = true;
         if (this.address == null) {
            ia = AddressUtils.getIPForLocalHost();
            if (!this.hasPublicAddress()) {
               this.publicAddress = ia.getHostAddress();
            }
         } else {
            ia = InetAddress.getByName(this.address);
            this.inetAddress = ia;
            if (this.address.indexOf(":") != -1) {
               this.address = "[" + this.inetAddress.getHostAddress() + "]";
            }
         }
      } else {
         String pa = this.getPublicAddress();
         if (pa == null) {
            if (id instanceof Channel) {
               pa = ((Channel)id).getPublicAddress();
            }

            if (pa == null) {
               throw new UnknownHostException("Couldn't determine usable host address for remote channel: " + this.config.getName());
            }

            this.setPublicAddress(pa);
         }

         ia = InetAddress.getByName(pa);
         this.inetAddress = ia;
      }

      byte[] buf = ia.getAddress();
      this.rawAddress = ((buf[0] & 255) << 24) + ((buf[1] & 255) << 16) + ((buf[2] & 255) << 8) + ((buf[3] & 255) << 0);
      if (this.getPublicPort() >= 0) {
         this.inSockAddress = new InetSocketAddress(this.getPublicAddress(), this.getPublicPort());
      }

      this.weight = this.config.getChannelWeight();
   }

   void update() {
      this.weight = this.config.getChannelWeight();
      if (this.address != null) {
         this.setPublicAddress(this.config.getPublicAddress());
      }

      this.setPublicPort(this.findPublicPort(this.config));
   }

   protected BasicServerChannelImpl(BasicServerChannelImpl channel, Protocol protocol) {
      this.listenPort = -1;
      this.publicPort = -1;
      this.flags = channel.flags;
      this.displayName = channel.displayName;
      this.associatedVirtualTargetName = channel.associatedVirtualTargetName;
      this.listenPort = channel.listenPort;
      this.publicPort = channel.publicPort;
      this.weight = channel.weight;
      this.config = channel.config;
      this.publicAddress = channel.publicAddress;
      this.address = channel.address;
      this.inetAddress = channel.inetAddress;
      this.rawAddress = channel.rawAddress;
      this.inSockAddress = channel.inSockAddress;
      this.implicitChannel = true;
      this.protocol = protocol;
      this.isLocal = channel.isLocal;
      this.priority = protocol.getHandler().getPriority();
      if (this.displayName == null) {
         this.displayName = channel.channelName;
      }

      this.channelName = encodeName(protocol, this.displayName);
   }

   private static void p(String s) {
      System.out.println("<BasicServerChannelImpl>: " + s);
   }

   public static ServerChannel createDefaultServerChannel(Protocol protocol) {
      try {
         return new BasicServerChannelImpl(new NetworkAccessPointMBeanStub(protocol.getProtocolName()), protocol, LocalServerIdentity.getIdentity());
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }
   }

   public String getConfiguredProtocol() {
      return this.config.getProtocol();
   }

   public int getAcceptBacklog() {
      return this.config.getAcceptBacklog();
   }

   public int getCompleteMessageTimeout() {
      return this.config.getCompleteMessageTimeout();
   }

   public int getConnectTimeout() {
      return this.config.getConnectTimeout();
   }

   public int getIdleConnectionTimeout() {
      return this.config.getIdleConnectionTimeout();
   }

   public int getLoginTimeoutMillis() {
      return this.config.getLoginTimeoutMillis();
   }

   public int getMaxBackoffBetweenFailures() {
      return this.config.getMaxBackoffBetweenFailures();
   }

   public int getMaxConnectedClients() {
      return this.config.getMaxConnectedClients();
   }

   public int getMaxMessageSize() {
      return this.config.getMaxMessageSize();
   }

   public String getProxyAddress() {
      return this.config.getProxyAddress();
   }

   public int getProxyPort() {
      return this.config.getProxyPort();
   }

   public boolean getTimeoutConnectionWithPendingResponses() {
      return this.config.getTimeoutConnectionWithPendingResponses();
   }

   public int getTunnelingClientPingSecs() {
      return this.config.getTunnelingClientPingSecs();
   }

   public int getTunnelingClientTimeoutSecs() {
      return this.config.getTunnelingClientTimeoutSecs();
   }

   public boolean getUseFastSerialization() {
      return this.config.getUseFastSerialization();
   }

   public boolean isClientCertificateEnforced() {
      return this.config.isClientCertificateEnforced();
   }

   public boolean isHttpEnabledForThisProtocol() {
      return this.config.isHttpEnabledForThisProtocol();
   }

   public boolean isOutboundEnabled() {
      return this.config.isOutboundEnabled();
   }

   public boolean isOutboundPrivateKeyEnabled() {
      return this.config.isOutboundPrivateKeyEnabled();
   }

   public boolean isTunnelingEnabled() {
      return this.config.isTunnelingEnabled();
   }

   public boolean isTwoWaySSLEnabled() {
      return this.config.isTwoWaySSLEnabled();
   }

   public boolean isT3SenderQueueDisabled() {
      return this.t3SenderQueueDisabled;
   }

   void setT3SenderQueueDisabled(boolean enable) {
      this.t3SenderQueueDisabled = enable;
   }

   public boolean getResolveDNSName() {
      return this.getConfig() != null && this.getConfig().getResolveDNSName();
   }

   public boolean isHostnameVerificationIgnored() {
      return this.config.isHostnameVerificationIgnored();
   }

   public String getHostnameVerifier() {
      return this.config.getHostnameVerifier();
   }

   public String[] getCiphersuites() {
      return this.config.getCiphersuites();
   }

   public String[] getExcludedCiphersuites() {
      return this.config.getExcludedCiphersuites();
   }

   public boolean isAllowUnencryptedNullCipher() {
      return this.config.isAllowUnencryptedNullCipher();
   }

   public String getInboundCertificateValidation() {
      return this.config.getInboundCertificateValidation();
   }

   public String getOutboundCertificateValidation() {
      return this.config.getOutboundCertificateValidation();
   }

   public String getMinimumTLSProtocolVersion() {
      return this.config.getMinimumTLSProtocolVersion();
   }

   public boolean isSSLv2HelloEnabled() {
      return this.config.isSSLv2HelloEnabled();
   }

   public boolean isClientInitSecureRenegotiationAccepted() {
      return this.config.isClientInitSecureRenegotiationAccepted();
   }
}
