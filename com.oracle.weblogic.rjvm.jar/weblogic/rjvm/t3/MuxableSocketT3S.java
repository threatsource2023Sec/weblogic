package weblogic.rjvm.t3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.RJVMLogger;
import weblogic.security.SSL.CertCallback;
import weblogic.security.SSL.ClientCertificatePlugin;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.utils.SSLCertUtility;
import weblogic.server.channels.ChannelService;
import weblogic.socket.ChannelSSLSocketFactory;
import weblogic.socket.SSLFilter;
import weblogic.socket.SocketMuxer;
import weblogic.utils.io.Chunk;

public final class MuxableSocketT3S extends MuxableSocketT3 {
   private static final long serialVersionUID = -1499853227078510946L;
   private static String clientCertPlugin;

   public MuxableSocketT3S(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      super(head, s, networkChannel);
      if (ChannelHelper.isAdminChannel(networkChannel)) {
         this.connection.setAdminQOS();
      }

   }

   static MuxableSocketT3S createMuxableSocket(InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout, String partitionUrl) throws IOException {
      ServerChannel channelToUse = getPreferredOutboundChannel(new InetSocketAddress(address.getHostAddress(), port), networkChannel);
      MuxableSocketT3S connection = new MuxableSocketT3S(destinationJVMID, channelToUse, partitionUrl);
      int timeout = connectionTimeout > 0 ? connectionTimeout : channelToUse.getConnectTimeout() * 1000;
      Socket s = connection.newSocketWithRetry(address, port, timeout);
      if (channelToUse == networkChannel) {
         Protocol p = channelToUse.getProtocol();
         ServerChannel newChannel = ChannelService.findPreferredChannelFromSocket(s, p, channelToUse);
         if (!newChannel.equals(channelToUse)) {
            connection = new MuxableSocketT3S(destinationJVMID, newChannel, partitionUrl);
         }
      }

      try {
         connection.connectSocket(s, connectionTimeout);
         return connection;
      } catch (SSLException var14) {
         Socket sock = connection.getSocket();
         if (sock != null && !sock.isClosed()) {
            try {
               sock.close();
            } catch (IOException var13) {
            }
         }

         throw var14;
      }
   }

   private void initializeClientCertPlugin(JVMID destinationJVMID, ServerChannel channel) throws IOException {
      if (clientCertPlugin != null) {
         SSLClientInfo sslClientInfo = getClientCertificate(destinationJVMID, channel);
         if (sslClientInfo != null) {
            ChannelSSLSocketFactory channelSSLSocketFactory = (ChannelSSLSocketFactory)this.socketFactory;
            channelSSLSocketFactory.setSSLClientInfo(sslClientInfo);
         }
      }

   }

   private MuxableSocketT3S(JVMID destJVMID, ServerChannel networkChannel, String partitionURL) throws IOException {
      super(networkChannel, partitionURL);
      if (ChannelHelper.isAdminChannel(networkChannel)) {
         this.connection.setAdminQOS();
      }

      this.initializeClientCertPlugin(destJVMID, networkChannel);
   }

   static SSLClientInfo getClientCertificate(JVMID destJVMID, ServerChannel channel) {
      try {
         Class pluginClass = Class.forName(clientCertPlugin);
         ClientCertificatePlugin certPlugin = (ClientCertificatePlugin)pluginClass.newInstance();
         CertCallback callBack = new CertCallback(SocketMuxer.getMuxer().isAsyncMuxer(), channel.getAddress(), channel.getPort(), destJVMID.getPublicAddress(), destJVMID.getPublicPort());
         certPlugin.loadClientCertificate(callBack, destJVMID.getDomainName(), destJVMID.getServerName());
         return callBack.getSSLClientInfo();
      } catch (Throwable var5) {
         RJVMLogger.logFailedGetClientCertificate(var5);
         return null;
      }
   }

   public X509Certificate[] getJavaCertChain() {
      SSLSocket s = (SSLSocket)this.getSocket();

      try {
         return SSLCertUtility.getPeerCertChain(s);
      } catch (Exception var3) {
         return null;
      }
   }

   public void ensureForceClose() {
      ((SSLFilter)this.getSocketFilter()).ensureForceClose();
   }

   static {
      if (KernelStatus.isServer()) {
         clientCertPlugin = System.getProperty("weblogic.security.SSL.ClientCertPlugin");
      }

   }
}
