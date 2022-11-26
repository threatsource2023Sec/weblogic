package weblogic.rjvm.http;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import weblogic.net.http.HttpsURLConnection;
import weblogic.protocol.ServerChannel;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.socket.ChannelSSLSocketFactory;

public final class HTTPSClientJVMConnection extends HTTPClientJVMConnection {
   private ChannelSSLSocketFactory factory;

   HTTPSClientJVMConnection(ServerChannel networkChannel, String partitionUrl) throws IOException {
      super(networkChannel, partitionUrl);
      this.factory = new ChannelSSLSocketFactory(networkChannel);
      this.factory.initializeFromThread();
   }

   URLConnection createURLConnection(URL u) {
      HttpsURLConnection conn = new HttpsURLConnection(u, (SSLClientInfo)((SSLClientInfo)this.factory.getSSLClientInfo()));
      conn.setSSLSocketFactory(this.factory);
      conn.u11();
      return conn;
   }
}
