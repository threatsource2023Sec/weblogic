package weblogic.security.SSL;

import javax.net.ssl.SSLSession;

public interface HostnameVerifier {
   boolean verify(String var1, SSLSession var2);
}
