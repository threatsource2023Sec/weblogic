package weblogic.nodemanager.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import weblogic.security.SSL.SSLSocketFactory;

public class DefaultSSLClient extends SSLClient {
   public DefaultSSLClient() {
      this.port = 5556;
   }

   protected Socket createSocket(String host, int port, int timeout) throws IOException {
      SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
      Socket s = new Socket();
      InetAddress address = null;
      if (host == null) {
         address = InetAddress.getLocalHost();
      } else {
         address = InetAddress.getByName(host);
      }

      s.connect(new InetSocketAddress(address, port), timeout);
      return factory.createSocket(s, address.getHostAddress(), port, true);
   }
}
