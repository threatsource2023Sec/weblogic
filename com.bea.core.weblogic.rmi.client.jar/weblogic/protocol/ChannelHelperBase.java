package weblogic.protocol;

import java.io.ObjectOutput;
import weblogic.rmi.spi.Channel;
import weblogic.utils.net.InetAddressHelper;

public class ChannelHelperBase {
   public static boolean isLocalAdminChannelEnabled() {
      return ServerChannelManager.findLocalServerChannel(ProtocolHandlerAdmin.PROTOCOL_ADMIN) != null;
   }

   public static long getAdminChannelCreationTime() {
      return ServerChannelManager.getServerChannelManager().getAdminChannelCreationTime();
   }

   public static boolean isAdminChannel(ServerChannel channel) {
      return channel.getProtocol().isSatisfactoryQOS((byte)103) || channel.getConfiguredProtocol().equalsIgnoreCase("ADMIN");
   }

   public static String getDefaultURL() {
      return createURL(ServerChannelManager.findDefaultLocalServerChannel());
   }

   public static String createURL(Channel ch) {
      return ch == null ? null : ch.getProtocolPrefix() + "://" + InetAddressHelper.convertHostIfIPV6(ch.getPublicAddress()) + ':' + ch.getPublicPort();
   }

   public static String getChannelURL(ObjectOutput out) {
      if (out instanceof ServerChannelStream) {
         ServerChannel sc = ((ServerChannelStream)out).getServerChannel();
         if (sc != null) {
            return createURL(sc);
         }
      }

      return getDefaultURL();
   }
}
