package weblogic.security.SSL;

import javax.net.ssl.SSLException;

public interface SSLEngineFactory {
   String[] getDefaultCipherSuites();

   String[] getSupportedCipherSuites();

   WeblogicSSLEngine createSSLEngine() throws SSLException;

   WeblogicSSLEngine createSSLEngine(String var1, int var2) throws SSLException;

   WeblogicSSLEngine createSSLEngine(String var1, int var2, boolean var3) throws SSLException;
}
