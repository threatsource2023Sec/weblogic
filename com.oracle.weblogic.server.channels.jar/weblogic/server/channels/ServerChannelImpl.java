package weblogic.server.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.NetworkAccessPointMBeanStub;
import weblogic.management.ManagementException;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerChannelRuntimeMBean;
import weblogic.management.runtime.ServerChannelRuntimeProvider;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.UnknownProtocolException;
import weblogic.protocol.configuration.NetworkAccessPointDefaultMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;

public final class ServerChannelImpl extends BasicServerChannelImpl implements ServerChannelRuntimeProvider {
   static final long serialVersionUID = 3682806476156685669L;
   private static final boolean DEBUG = false;
   private transient ServerChannelRuntimeMBean runtime;
   private boolean valid;

   public ServerChannelImpl() {
      this.valid = true;
   }

   public ServerChannelRuntimeMBean getRuntime() {
      return this.runtime;
   }

   boolean isValid() {
      return this.valid;
   }

   void setValid(boolean valid) {
      this.valid = valid;
   }

   final synchronized ServerChannelRuntimeMBean createRuntime() throws ManagementException {
      Debug.assertion(this.runtime == null);
      this.runtime = new ServerChannelRuntimeMBeanImpl(this);
      return this.runtime;
   }

   final ServerChannelRuntimeMBean deleteRuntime() {
      Debug.assertion(this.runtime != null);
      ServerChannelRuntimeMBeanImpl old = (ServerChannelRuntimeMBeanImpl)this.runtime;
      this.runtime = null;

      try {
         old.unregister();
      } catch (ManagementException var3) {
      }

      return old;
   }

   ServerChannelImpl(NetworkAccessPointMBean napMbean, ServerIdentity id) throws UnknownHostException, UnknownProtocolException {
      this(napMbean, ProtocolManager.findProtocol(napMbean.getProtocol()), napMbean.getName(), id, (String)null);
   }

   public ServerChannelImpl(NetworkAccessPointMBean napMbean, Protocol protocol, ServerIdentity id) throws UnknownHostException {
      this(napMbean, protocol, encodeName(protocol, napMbean.getName()), id, (String)null);
      this.displayName = napMbean.getName();
   }

   private ServerChannelImpl(NetworkAccessPointMBean napMbean, Protocol protocol, String channelName, ServerIdentity id, String address) throws UnknownHostException {
      super(napMbean, protocol, channelName, id, address);
      this.valid = true;
   }

   ServerChannelImpl(ServerChannelImpl channel, String address, String nameModifier) throws UnknownHostException {
      this.valid = true;
      this.flags = channel.flags;
      this.channelName = channel.channelName + nameModifier;
      if (channel.displayName != null) {
         this.displayName = channel.displayName + nameModifier;
      }

      this.associatedVirtualTargetName = channel.associatedVirtualTargetName;
      this.listenPort = channel.listenPort;
      this.publicPort = channel.publicPort;
      this.protocol = channel.protocol;
      this.priority = channel.priority;
      this.weight = channel.weight;
      this.config = channel.config;
      this.implicitChannel = channel.implicitChannel;
      this.isLocal = channel.isLocal;
      this.valid = channel.valid;
      if (channel.hasPublicAddress()) {
         this.setPublicAddress(channel.publicAddress);
      }

      this.inetAddress = InetAddress.getByName(address);
      if (address.indexOf(":") != -1) {
         address = "[" + this.inetAddress.getHostAddress() + "]";
      }

      this.setListenAddress(address);
      byte[] buf = this.inetAddress.getAddress();
      this.rawAddress = ((buf[0] & 255) << 24) + ((buf[1] & 255) << 16) + ((buf[2] & 255) << 8) + ((buf[3] & 255) << 0);
      if (this.getPublicPort() >= 0) {
         this.inSockAddress = new InetSocketAddress(this.getPublicAddress(), this.getPublicPort());
      }

   }

   public ServerChannelImpl createImplicitChannel(Protocol protocol) {
      return new ServerChannelImpl(this, protocol);
   }

   ServerChannelImpl cloneChannel(String identifier) {
      ServerChannelImpl sc = new ServerChannelImpl(this, this.protocol);
      sc.implicitChannel = false;
      sc.channelName = this.channelName + "{" + identifier + "}";
      String displayName = this.displayName != null ? this.displayName : this.channelName;
      sc.displayName = displayName + "{" + identifier + "}";
      sc.associatedVirtualTargetName = this.associatedVirtualTargetName;
      sc.valid = this.valid;
      return sc;
   }

   private ServerChannelImpl(ServerChannelImpl channel, Protocol protocol) {
      this.valid = true;
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
      this.valid = channel.valid;
   }

   private static void p(String s) {
      System.out.println("<ServerChannelImpl>: " + s);
   }

   public static ServerChannel createDefaultServerChannel(Protocol protocol) {
      try {
         if (KernelStatus.isServer()) {
            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            return new ServerChannelImpl(new NetworkAccessPointDefaultMBean(protocol, ManagementService.getRuntimeAccess(kernelId).getServer()), protocol, LocalServerIdentity.getIdentity());
         } else {
            return new ServerChannelImpl(new NetworkAccessPointMBeanStub(protocol.getProtocolName()), protocol, LocalServerIdentity.getIdentity());
         }
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }
   }
}
