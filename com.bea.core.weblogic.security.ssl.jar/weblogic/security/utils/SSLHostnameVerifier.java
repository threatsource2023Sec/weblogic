package weblogic.security.utils;

import javax.net.ssl.SSLSocket;

public interface SSLHostnameVerifier {
   boolean hostnameValidationCallback(String var1, SSLSocket var2);
}
