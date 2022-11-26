package weblogic.security.acl.internal;

import java.io.InputStream;
import java.net.SocketException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface SSLClientInfoService {
   byte[][] getRootCAfingerprints();

   void setRootCAfingerprints(byte[][] var1);

   void setRootCAfingerprints(String var1);

   boolean isEmpty();

   /** @deprecated */
   @Deprecated
   String getExpectedName();

   /** @deprecated */
   @Deprecated
   void setExpectedName(String var1);

   InputStream[] getSSLClientCertificate();

   void setSSLClientCertificate(InputStream[] var1);

   void setSSLClientKeyPassword(String var1);

   String getSSLClientKeyPassword();

   void loadLocalIdentity(Certificate[] var1, PrivateKey var2);

   boolean isClientCertAvailable();

   SSLSocketFactory getSSLSocketFactory() throws SocketException;

   void setNio(boolean var1);

   boolean isLocalIdentitySet();

   void loadLocalIdentity(InputStream var1, InputStream var2, char[] var3);

   void setSSLContext(SSLContext var1);
}
