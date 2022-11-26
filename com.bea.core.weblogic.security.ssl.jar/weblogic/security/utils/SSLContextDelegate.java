package weblogic.security.utils;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

public interface SSLContextDelegate {
   int TLS1_ONLY = 0;
   int SSL3_ONLY = 1;
   int SSL3_TLS1 = 2;
   int V2HELLO_SSL3_TLS1 = 3;
   int V2HELLO_SSL3 = 4;
   int CONSTRAINTS_OFF = 0;
   int CONSTRAINTS_STRONG = 1;
   int CONSTRAINTS_STRICT = 2;
   int CONSTRAINTS_STRICT_NOV1CAS = 3;
   int CONSTRAINTS_STRONG_NOV1CAS = 4;

   void addTrustedCA(X509Certificate var1) throws CertificateException;

   X509Certificate[] getTrustedCAs();

   PrivateKey inputPrivateKey(InputStream var1, char[] var2) throws KeyManagementException;

   X509Certificate[] inputCertChain(InputStream var1) throws KeyManagementException;

   void loadLocalIdentity(InputStream var1, char[] var2) throws KeyManagementException;

   void loadTrustedCerts(InputStream var1) throws CertificateException, KeyManagementException;

   void addIdentity(X509Certificate[] var1, PrivateKey var2);

   boolean doKeysMatch(PublicKey var1, PrivateKey var2) throws KeyManagementException;

   void setExportRefreshCount(int var1);

   void setProtocolVersion(int var1) throws IllegalArgumentException;

   SSLServerSocketFactory getSSLServerSocketFactory();

   SSLSocketFactory getSSLSocketFactory();

   void setTrustManager(SSLTruster var1);

   SSLTruster getTrustManager();

   void setHostnameVerifier(SSLHostnameVerifier var1);

   SSLHostnameVerifier getHostnameVerifier();

   void enforceConstraints(int var1);

   boolean isJsseEnabled();

   void setSendEmptyCertRequest(boolean var1);

   String getDescription();

   Class getListenerThreadFactoryClass();
}
