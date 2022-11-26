package weblogic.net.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import weblogic.management.configuration.SSLMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class WLSClientNETEnvironmentImpl extends NETEnvironment {
   public boolean useSunHttpHandler() {
      return true;
   }

   public SSLMBean getSSLMBean(AuthenticatedSubject subject) {
      return null;
   }

   public URLConnection getHttpsURLConnection(URL url, Proxy proxy) throws IOException {
      throw new RuntimeException("It is not implemented yet");
   }
}
