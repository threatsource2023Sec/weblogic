package weblogic.net.https;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import weblogic.net.http.BaseHandler;
import weblogic.net.http.NETEnvironment;

/** @deprecated */
@Deprecated
public final class Handler extends BaseHandler {
   public Handler() {
      this.isHTTP = false;
   }

   public Handler(boolean protocolFlag) {
      this.isHTTP = protocolFlag;
   }

   protected URLConnection openConnection(URL u, Proxy p) throws IOException {
      return NETEnvironment.getNETEnvironment().getHttpsURLConnection(u, p);
   }

   protected int getDefaultPort() {
      return 443;
   }
}
