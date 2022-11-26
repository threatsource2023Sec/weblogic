package weblogic.jndi.internal.SSL;

import java.io.InputStream;
import java.net.SocketException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.security.SSL.TrustManager;

public final class ClientSSLProxyImpl implements SSLProxy {
   public SSLSocketFactory getSSLSocketFactory() throws SocketException {
      SSLSocketFactory sslSocketFactory = null;
      SSLContext sslContext = (SSLContext)RJVMEnvironment.getEnvironment().getSSLContext();
      if (sslContext != null) {
         sslSocketFactory = sslContext.getSocketFactory();
      }

      return sslSocketFactory;
   }

   public boolean isEmpty() {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public void setRootCAfingerprints(byte[][] fingerprints) {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public void setRootCAfingerprints(String fingerprints) {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public byte[][] getRootCAfingerprints() {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public void setExpectedName(String s) {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public String getExpectedName() {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public InputStream[] getSSLClientCertificate() {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public void setSSLClientCertificate(InputStream[] chain) {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public void setSSLClientKeyPassword(String pass) {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public String getSSLClientKeyPassword() {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public void setTrustManager(TrustManager trustManager) {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public TrustManager getTrustManager() {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public void loadLocalIdentity(Certificate[] certs, PrivateKey privateKey) {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public final void setSSLContext(SSLContext sslctx) {
      RJVMEnvironment.getEnvironment().setSSLContext(sslctx);
   }

   public boolean isClientCertAvailable() {
      return RJVMEnvironment.getEnvironment().getSSLContext() != null;
   }

   public boolean isLocalIdentitySet() {
      return RJVMEnvironment.getEnvironment().getSSLContext() != null;
   }
}
