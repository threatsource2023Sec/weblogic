package weblogic.nodemanager.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

class PlainClient extends NMServerClient {
   public static final int LISTEN_PORT = 5556;

   public PlainClient() {
      this.port = 5556;
   }

   protected Socket createSocket(String host, int port, int timeout) throws IOException {
      Socket s = new Socket();
      InetAddress address = null;
      if (host == null) {
         address = InetAddress.getLocalHost();
      } else {
         address = InetAddress.getByName(host);
      }

      s.connect(new InetSocketAddress(address, port), timeout);
      return s;
   }
}
