package weblogic.jndi.internal.SSL;

import java.io.InputStream;
import java.net.SocketException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.SSL.TrustManager;
import weblogic.security.acl.internal.SSLClientInfoService;
import weblogic.security.acl.internal.Security;

public final class WLSSSLProxyImpl implements SSLProxy {
   private SSLClientInfoService findOrCreateSSLClientInfo() {
      return Security.getThreadSSLClientInfo();
   }

   public SSLSocketFactory getSSLSocketFactory() throws SocketException {
      return this.findOrCreateSSLClientInfo().getSSLSocketFactory();
   }

   public boolean isEmpty() {
      return this.findOrCreateSSLClientInfo().isEmpty();
   }

   public void setRootCAfingerprints(byte[][] fingerprints) {
      this.findOrCreateSSLClientInfo().setRootCAfingerprints(fingerprints);
   }

   public void setRootCAfingerprints(String fingerprints) {
      this.findOrCreateSSLClientInfo().setRootCAfingerprints(fingerprints);
   }

   public byte[][] getRootCAfingerprints() {
      return this.findOrCreateSSLClientInfo().getRootCAfingerprints();
   }

   public void setExpectedName(String s) {
      this.findOrCreateSSLClientInfo().setExpectedName(s);
   }

   public String getExpectedName() {
      return this.findOrCreateSSLClientInfo().getExpectedName();
   }

   public InputStream[] getSSLClientCertificate() {
      return this.findOrCreateSSLClientInfo().getSSLClientCertificate();
   }

   public void setSSLClientCertificate(InputStream[] chain) {
      this.findOrCreateSSLClientInfo().setSSLClientCertificate(chain);
   }

   public void setSSLClientKeyPassword(String pass) {
      this.findOrCreateSSLClientInfo().setSSLClientKeyPassword(pass);
   }

   public String getSSLClientKeyPassword() {
      return this.findOrCreateSSLClientInfo().getSSLClientKeyPassword();
   }

   public void setTrustManager(TrustManager trustManager) {
      ((SSLClientInfo)this.findOrCreateSSLClientInfo()).setTrustManager(trustManager);
   }

   public TrustManager getTrustManager() {
      return ((SSLClientInfo)this.findOrCreateSSLClientInfo()).getTrustManager();
   }

   public void loadLocalIdentity(Certificate[] certs, PrivateKey privateKey) {
      this.findOrCreateSSLClientInfo().loadLocalIdentity(certs, privateKey);
   }

   public final void setSSLContext(SSLContext sslctx) {
      this.findOrCreateSSLClientInfo().setSSLContext(sslctx);
   }

   public boolean isClientCertAvailable() {
      return this.findOrCreateSSLClientInfo().isClientCertAvailable();
   }

   public boolean isLocalIdentitySet() {
      return this.findOrCreateSSLClientInfo().isLocalIdentitySet();
   }
}
