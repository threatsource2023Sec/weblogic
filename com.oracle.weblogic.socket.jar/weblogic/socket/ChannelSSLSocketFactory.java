package weblogic.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AccessController;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ServerChannel;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.SSL.SSLSocketFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.SSLClientInfoService;
import weblogic.security.acl.internal.Security;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.SSLContextManager;
import weblogic.security.utils.SSLSetup;

public final class ChannelSSLSocketFactory extends SSLSocketFactory {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final ServerChannel channel;
   private SSLClientInfoService sslInfo;

   public ChannelSSLSocketFactory(ServerChannel channel) {
      super((javax.net.ssl.SSLSocketFactory)null);
      if (channel == null) {
         throw new IllegalArgumentException("Channel must not be null");
      } else {
         this.channel = channel;
      }
   }

   public Socket createSocket(String address, int port) throws IOException, UnknownHostException {
      return this.createSocket(InetAddress.getByName(address), port);
   }

   public SSLSocketFactory initializeFromThread() throws IOException {
      this.sslInfo = this.createSSLClientInfo();
      return this;
   }

   public Socket createSocket(String address, int port, InetAddress localHost, int localPort) {
      throw new UnsupportedOperationException("Binding characteristics are determined by the channel");
   }

   public Socket createSocket(InetAddress host, int port) throws IOException {
      javax.net.ssl.SSLSocketFactory sf = this.getSocketFactory();
      if (KernelStatus.isServer()) {
         if (this.channel.isOutboundEnabled()) {
            return sf.createSocket(host, port, this.getInetAddressFor(this.channel.getAddress()), 0);
         }
      } else {
         Socket s = SocketMuxer.getMuxer().newSSLClientSocket(host, port);
         if (s != null) {
            return sf.createSocket(s, host.getHostAddress(), port, true);
         }
      }

      return sf.createSocket(host, port);
   }

   public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) {
      throw new UnsupportedOperationException("Binding characteristics are determined by the channel");
   }

   public Socket createSocket(InetAddress host, int port, int connectionTimeoutMillis) throws IOException {
      int timeout = connectionTimeoutMillis > 0 ? connectionTimeoutMillis : this.channel.getConnectTimeout() * 1000;
      if (timeout == 0) {
         return this.createSocket(host, port);
      } else {
         Socket socket;
         if (KernelStatus.isServer()) {
            if (this.channel.getProxyAddress() != null) {
               socket = SocketMuxer.getMuxer().newProxySocket(host, port, this.getInetAddressFor(this.channel.getAddress()), 0, InetAddress.getByName(this.channel.getProxyAddress()), this.channel.getProxyPort(), timeout);
            } else if (this.channel.isOutboundEnabled()) {
               try {
                  socket = SocketMuxer.getMuxer().newSocket(host, port, this.getInetAddressFor(this.channel.getAddress()), 0, timeout);
               } catch (IOException var7) {
                  if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                     SocketLogger.logDebugThrowable("Unable to create new socket with local channel : '" + this.channel + "' due to IOException: ", var7);
                  }

                  socket = SocketMuxer.getMuxer().newSocket(host, port, timeout);
               }
            } else {
               socket = SocketMuxer.getMuxer().newSocket(host, port, timeout);
            }
         } else {
            socket = SocketMuxer.getMuxer().newSSLClientSocket(host, port, timeout);
            if (socket == null) {
               socket = SocketMuxer.getMuxer().newSocket(host, port, timeout);
            }
         }

         return this.createSocket(socket, host.getHostName(), port, true);
      }
   }

   private InetAddress getInetAddressFor(String channelAddress) throws UnknownHostException {
      return channelAddress != null ? InetAddress.getByName(channelAddress) : (InetAddress)null;
   }

   public String[] getDefaultCipherSuites() {
      try {
         return this.getSocketFactory().getDefaultCipherSuites();
      } catch (IOException var2) {
         throw (RuntimeException)(new IllegalStateException()).initCause(var2);
      }
   }

   public String[] getSupportedCipherSuites() {
      try {
         return this.getSocketFactory().getSupportedCipherSuites();
      } catch (IOException var2) {
         throw (RuntimeException)(new IllegalStateException()).initCause(var2);
      }
   }

   public Socket createSocket(Socket socket, String host, int port, boolean autoclose) throws IOException {
      return this.getSocketFactory().createSocket(socket, host, port, autoclose);
   }

   private javax.net.ssl.SSLSocketFactory getSocketFactory() throws IOException {
      if (this.sslInfo == null) {
         this.sslInfo = this.createSSLClientInfo();
         this.sslInfo.setNio(SocketMuxer.getMuxer().isAsyncMuxer());
      }

      return this.sslInfo.getSSLSocketFactory();
   }

   public SSLClientInfoService getSSLClientInfo() {
      return this.sslInfo;
   }

   private SSLClientInfoService createSSLClientInfo() throws IOException {
      SSLClientInfoService sslInfo = Security.getThreadSSLClientInfo();
      if ((!KernelStatus.isServer() || sslInfo != null && !sslInfo.isEmpty() || kernelId != SecurityServiceManager.getCurrentSubject(kernelId)) && (!this.channel.isOutboundEnabled() || !this.channel.isOutboundPrivateKeyEnabled())) {
         return sslInfo;
      } else {
         try {
            return SSLContextManager.getChannelSSLClientInfo(this.channel, kernelId);
         } catch (Exception var3) {
            throw (IOException)(new IOException(var3.getMessage())).initCause(var3);
         }
      }
   }

   public void setSSLClientInfo(SSLClientInfo sslCI) {
      try {
         if (SocketMuxer.getMuxer().isAsyncMuxer()) {
            if (sslCI != null && !sslCI.isNioSet()) {
               sslCI.setNio(true);
            }

            this.jsseFactory = sslCI == null ? SSLSetup.getSSLContext(sslCI, this.channel).getSSLNioSocketFactory() : sslCI.getSSLSocketFactory();
         } else {
            this.jsseFactory = sslCI == null ? SSLSetup.getSSLContext(sslCI, this.channel).getSSLSocketFactory() : sslCI.getSSLSocketFactory();
         }

         this.sslInfo = sslCI;
      } catch (SocketException var3) {
         SSLSetup.debug(3, var3, "Failed to create context");
         throw new RuntimeException("Failed to update factory: " + var3.getMessage());
      }
   }
}
