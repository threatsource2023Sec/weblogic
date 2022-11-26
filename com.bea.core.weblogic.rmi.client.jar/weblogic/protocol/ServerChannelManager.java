package weblogic.protocol;

import java.net.SocketAddress;
import weblogic.rmi.spi.HostID;

public abstract class ServerChannelManager {
   private static volatile ServerChannelManager singleton;

   /** @deprecated */
   @Deprecated
   public static ServerChannel findServerChannel(HostID identity) {
      return getServerChannelManager().getServerChannel(identity);
   }

   public static ServerChannel findServerChannel(HostID identity, Protocol protocol) {
      return getServerChannelManager().getServerChannel(identity, protocol);
   }

   public static ServerChannel findServerChannel(HostID identity, Protocol protocol, String channelName) {
      return getServerChannelManager().getServerChannel(identity, protocol, channelName);
   }

   public static ServerChannel findServerChannel(HostID identity, String channelName) {
      return getServerChannelManager().getServerChannel(identity, channelName);
   }

   public static ServerChannel lookupServerChannel(HostID identity, String channelName) {
      return getServerChannelManager().getAvailableServerChannel(identity, channelName);
   }

   public static int findLocalServerPort(Protocol protocol) {
      ServerChannel sc = getServerChannelManager().getServerChannel(LocalServerIdentity.getIdentity(), (Protocol)protocol);
      return sc == null ? -1 : sc.getPublicPort();
   }

   public static ServerChannel findLocalServerChannel(Protocol protocol) {
      return getServerChannelManager().getServerChannel(LocalServerIdentity.getIdentity(), (Protocol)protocol);
   }

   public static ServerChannel findIPv4LocalServerChannel(Protocol protocol) {
      return getServerChannelManager().getIPv4ServerChannel(LocalServerIdentity.getIdentity(), protocol);
   }

   public static ServerChannel findIPv6LocalServerChannel(Protocol protocol) {
      return getServerChannelManager().getIPv6ServerChannel(LocalServerIdentity.getIdentity(), protocol);
   }

   public static ServerChannel findLocalServerChannel(String partitionName, String vtName, Protocol protocol) {
      return getServerChannelManager().getServerChannel(LocalServerIdentity.getIdentity(), partitionName, vtName, protocol);
   }

   public static ServerChannel findLocalServerChannel(Protocol protocol, String channelName) {
      return getServerChannelManager().getServerChannel(LocalServerIdentity.getIdentity(), protocol, channelName);
   }

   public static ServerChannel findLocalServerChannel(String channelName) {
      return getServerChannelManager().getServerChannel(LocalServerIdentity.getIdentity(), (String)channelName);
   }

   public static ServerChannel findDefaultLocalServerChannel() {
      ServerChannel sc = findLocalServerChannel(ProtocolManager.getDefaultProtocol());
      if (sc == null) {
         sc = findLocalServerChannel(ProtocolManager.getDefaultSecureProtocol());
         if (sc == null) {
            sc = findLocalServerChannel(ProtocolHandlerAdmin.PROTOCOL_ADMIN);
         }
      }

      return sc;
   }

   public static String findLocalServerAddress(Protocol protocol) {
      ServerChannel sc = getServerChannelManager().getServerChannel(LocalServerIdentity.getIdentity(), (Protocol)protocol);
      return sc == null ? null : sc.getPublicAddress();
   }

   public static ServerChannel findRelatedServerChannel(HostID identity, Protocol protocol, String publicAddress) {
      return getServerChannelManager().getRelatedServerChannel(identity, protocol, publicAddress);
   }

   public static ServerChannel findOutboundServerChannel(Protocol protocol, String channelName) {
      return getServerChannelManager().getOutboundServerChannel(protocol, channelName);
   }

   public static ServerChannel findOutboundServerChannel(Protocol protocol) {
      return getServerChannelManager().getOutboundServerChannel(protocol, (String)null);
   }

   public static ServerChannel findOutboundServerChannel(String channelName) {
      return getServerChannelManager().getOutboundServerChannel((Protocol)null, channelName);
   }

   public static ChannelList getLocalChannelsForExport() {
      return getServerChannelManager().getLocalChannelList();
   }

   public static String findServerChannelNameForPeer(SocketAddress sa) {
      return getServerChannelManager().findPreferredChannelName(sa);
   }

   public static final ServerChannelManager getServerChannelManager() {
      if (singleton == null) {
         singleton = ServerChannelManager.SingletonMaker.singleton;
      }

      return singleton;
   }

   public static final void setServerChannelManager(ServerChannelManager manager) {
      singleton = manager;
   }

   public abstract long getAdminChannelCreationTime();

   public abstract ServerChannel getServerChannel(HostID var1);

   protected abstract ServerChannel getServerChannel(HostID var1, Protocol var2);

   protected abstract ServerChannel getIPv4ServerChannel(HostID var1, Protocol var2);

   protected abstract ServerChannel getIPv6ServerChannel(HostID var1, Protocol var2);

   protected abstract ServerChannel getServerChannel(HostID var1, Protocol var2, String var3);

   protected abstract ServerChannel getServerChannel(HostID var1, String var2, String var3, Protocol var4);

   protected abstract ServerChannel getServerChannel(HostID var1, String var2);

   protected abstract ServerChannel getAvailableServerChannel(HostID var1, String var2);

   protected abstract ServerChannel getRelatedServerChannel(HostID var1, Protocol var2, String var3);

   protected abstract ServerChannel getOutboundServerChannel(Protocol var1, String var2);

   protected ChannelList getLocalChannelList() {
      return null;
   }

   protected String findPreferredChannelName(SocketAddress sa) {
      return null;
   }

   public abstract void restartSSLChannels();

   private static class SingletonMaker {
      private static ServerChannelManager singleton = new ServerChannelManager() {
         public long getAdminChannelCreationTime() {
            return 0L;
         }

         public ServerChannel getServerChannel(HostID identity) {
            return null;
         }

         protected ServerChannel getServerChannel(HostID identity, Protocol protocol) {
            return null;
         }

         protected ServerChannel getIPv4ServerChannel(HostID identity, Protocol protocol) {
            return null;
         }

         protected ServerChannel getIPv6ServerChannel(HostID identity, Protocol protocol) {
            return null;
         }

         protected ServerChannel getServerChannel(HostID identity, Protocol protocol, String channelName) {
            return null;
         }

         protected ServerChannel getServerChannel(HostID identity, String partitionName, String vtName, Protocol protocol) {
            return null;
         }

         protected ServerChannel getServerChannel(HostID identity, String channelName) {
            return null;
         }

         protected ServerChannel getAvailableServerChannel(HostID identity, String channelName) {
            return null;
         }

         protected ServerChannel getRelatedServerChannel(HostID identity, Protocol protocol, String publicAddress) {
            return null;
         }

         protected ServerChannel getOutboundServerChannel(Protocol protocol, String channel) {
            return protocol.getHandler().getDefaultServerChannel();
         }

         public void restartSSLChannels() {
         }
      };
   }
}
