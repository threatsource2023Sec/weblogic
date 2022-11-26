package weblogic.jndi.internal.SSL;

import java.io.InputStream;
import java.net.SocketException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import weblogic.security.SSL.TrustManager;

public interface SSLProxy {
   SSLSocketFactory getSSLSocketFactory() throws SocketException;

   boolean isEmpty();

   void setRootCAfingerprints(byte[][] var1);

   void setRootCAfingerprints(String var1);

   byte[][] getRootCAfingerprints();

   void setExpectedName(String var1);

   String getExpectedName();

   InputStream[] getSSLClientCertificate();

   void setSSLClientCertificate(InputStream[] var1);

   void setSSLClientKeyPassword(String var1);

   String getSSLClientKeyPassword();

   void setTrustManager(TrustManager var1);

   TrustManager getTrustManager();

   void loadLocalIdentity(Certificate[] var1, PrivateKey var2);

   void setSSLContext(SSLContext var1);

   boolean isClientCertAvailable();

   boolean isLocalIdentitySet();
}
