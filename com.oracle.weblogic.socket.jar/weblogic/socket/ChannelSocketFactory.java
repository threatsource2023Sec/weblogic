package weblogic.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ServerChannel;

public final class ChannelSocketFactory extends WeblogicSocketFactory {
   private ServerChannel channel;

   public ChannelSocketFactory(ServerChannel channel) {
      if (channel == null) {
         throw new IllegalArgumentException("Channel must not be null");
      } else {
         this.channel = channel;
      }
   }

   public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
      return this.createSocket(InetAddress.getByName(host), port);
   }

   public Socket createSocket(String address, int port, InetAddress localHost, int localPort) {
      throw new UnsupportedOperationException("Binding characteristics are determined by the channel");
   }

   public Socket createSocket(InetAddress host, int port) throws IOException {
      return this.createSocket(host, port, 0);
   }

   public Socket createSocket(InetAddress host, int port, int connectionTimeoutMillis) throws IOException {
      int timeout = connectionTimeoutMillis > 0 ? connectionTimeoutMillis : this.channel.getConnectTimeout() * 1000;
      if (!KernelStatus.isServer()) {
         return SocketMuxer.getMuxer().newClientSocket(host, port, this.getInetAddressFor(this.channel.getAddress()), 0, timeout);
      } else if (this.channel.getProxyAddress() != null) {
         return SocketMuxer.getMuxer().newProxySocket(host, port, this.getInetAddressFor(this.channel.getAddress()), 0, InetAddress.getByName(this.channel.getProxyAddress()), this.channel.getProxyPort(), timeout);
      } else if (this.channel.isSDPEnabled()) {
         return SocketMuxer.getMuxer().newSDPSocket(host, port, this.getInetAddressFor(this.channel.getAddress()), 0, timeout);
      } else {
         if (this.channel.isOutboundEnabled()) {
            try {
               return SocketMuxer.getMuxer().newSocket(host, port, this.getInetAddressFor(this.channel.getAddress()), 0, timeout);
            } catch (IOException var6) {
               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                  SocketLogger.logDebugThrowable("Unable to create new socket with local channel : '" + this.channel + "' due to IOException: ", var6);
               }
            }
         }

         return SocketMuxer.getMuxer().newSocket(host, port, timeout);
      }
   }

   private InetAddress getInetAddressFor(String channelAddress) throws UnknownHostException {
      return channelAddress != null ? InetAddress.getByName(channelAddress) : null;
   }

   public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) {
      throw new UnsupportedOperationException("Binding characteristics are determined by the channel");
   }
}
