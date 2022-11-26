package weblogic.net.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class WLSNETEnvironmentImpl extends NETEnvironment {
   private static final boolean useJSSECompatibleHttpsHandler = SecurityHelper.getBoolean("UseJSSECompatibleHttpsHandler");

   public boolean useSunHttpHandler() {
      return SecurityHelper.getSystemProperty("UseSunHttpHandler") != null;
   }

   public SSLMBean getSSLMBean(AuthenticatedSubject subject) {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(subject);
      if (ra == null) {
         return null;
      } else {
         SSLMBean sslbean = ra.getServer().getSSL();
         return sslbean == null ? null : sslbean;
      }
   }

   public URLConnection getHttpsURLConnection(URL url, Proxy proxy) throws IOException {
      return (URLConnection)(useJSSECompatibleHttpsHandler && KernelStatus.isServer() ? new CompatibleSOAPHttpsURLConnection(url, proxy) : new SOAPHttpsURLConnection(url, proxy));
   }
}
