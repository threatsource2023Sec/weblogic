package weblogic.transaction.internal;

import weblogic.transaction.ChannelInterface;

public class Channel implements ChannelInterface {
   weblogic.rmi.spi.Channel m_serverChannel;

   public Channel(weblogic.rmi.spi.Channel serverChannel) {
      this.m_serverChannel = serverChannel;
   }

   public String getPublicAddress() {
      return this.m_serverChannel.getPublicAddress();
   }

   public int getPublicPort() {
      return this.m_serverChannel.getPublicPort();
   }

   public String getProtocolPrefix() {
      return this.m_serverChannel.getProtocolPrefix();
   }
}
