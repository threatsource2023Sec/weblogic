package weblogic.security.utils;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import weblogic.security.SSL.WeblogicSSLEngine;

public interface SSLContextDelegate2 extends SSLContextDelegate {
   void addIdentity(X509Certificate[] var1, PrivateKey var2, String var3);

   void enableUnencryptedNullCipher(boolean var1);

   boolean isUnencryptedNullCipherEnabled();

   /** @deprecated */
   @Deprecated
   SSLServerSocketFactory getSSLNioServerSocketFactory();

   /** @deprecated */
   @Deprecated
   SSLSocketFactory getSSLNioSocketFactory();

   String[] getDefaultCipherSuites();

   String[] getSupportedCipherSuites();

   String[] getDefaultProtocols();

   String[] getSupportedProtocols();

   WeblogicSSLEngine createSSLEngine() throws SSLException;

   WeblogicSSLEngine createSSLEngine(String var1, int var2) throws SSLException;

   WeblogicSSLEngine createSSLEngine(String var1, int var2, boolean var3) throws SSLException;

   void setMinimumTLSProtocolVersion(String var1);

   void setSSLv2HelloEnabled(boolean var1);
}
