package weblogic.nodemanager.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import weblogic.nodemanager.util.SSLProtocolsUtil;

public class ThinSSLClient extends SSLClient {
   private final Logger logger = Logger.getLogger("weblogic.nodemanager");

   public ThinSSLClient() {
      this.port = 5556;
   }

   protected Socket createSocket(String host, int port, int timeout) throws IOException {
      SSLSocketFactory factory = this.getSSLSocketFactory();
      Socket s = new Socket();
      InetAddress address = null;
      if (host == null) {
         address = InetAddress.getLocalHost();
      } else {
         address = InetAddress.getByName(host);
      }

      s.connect(new InetSocketAddress(address, port), timeout);
      SSLSocket ret = (SSLSocket)factory.createSocket(s, address.getHostAddress(), port, true);
      ret.setEnabledProtocols(SSLProtocolsUtil.getJSSEProtocolVersions(SSLProtocolsUtil.getMinProtocolVersion(), ret.getSupportedProtocols(), this.logger));
      return ret;
   }

   private SSLSocketFactory getSSLSocketFactory() throws IOException {
      try {
         String ssl_protocol = SSLProtocolsUtil.getSSLContextProtocol();
         if (this.logger.isLoggable(Level.FINEST)) {
            this.logger.finest("Expected SSLContext service protocol: " + ssl_protocol);
         }

         SSLContext sslContext = SSLContext.getInstance(ssl_protocol);
         if (this.logger.isLoggable(Level.FINEST) && null != sslContext) {
            this.logger.finest("Actual SSLContext service protocol: " + sslContext.getProtocol());
         }

         sslContext.init((KeyManager[])null, (TrustManager[])null, new SecureRandom());
         SSLSocketFactory sf = sslContext.getSocketFactory();
         return sf;
      } catch (GeneralSecurityException var4) {
         throw new IllegalStateException(var4.getLocalizedMessage());
      }
   }
}
