package weblogic.net.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class Handler extends BaseHandler {
   public Handler() {
   }

   public Handler(boolean protocolFlag) {
      this.isHTTP = protocolFlag;
   }

   protected URLConnection openConnection(URL u, Proxy p) throws IOException {
      String proto = u.getProtocol().toLowerCase();
      if (proto.equals("http")) {
         return new SOAPHttpURLConnection(u, p);
      } else {
         return proto.equals("https") ? NETEnvironment.getNETEnvironment().getHttpsURLConnection(u, p) : null;
      }
   }

   protected int getDefaultPort() {
      return this.isHTTP ? 80 : 443;
   }
}
