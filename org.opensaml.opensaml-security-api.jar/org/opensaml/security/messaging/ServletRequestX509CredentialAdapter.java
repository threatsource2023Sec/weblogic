package org.opensaml.security.messaging;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletRequest;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.AbstractCredential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.x509.X509Credential;

public class ServletRequestX509CredentialAdapter extends AbstractCredential implements X509Credential {
   public static final String X509_CERT_REQUEST_ATTRIBUTE = "javax.servlet.request.X509Certificate";
   private X509Certificate cert;
   private List certChain;

   public ServletRequestX509CredentialAdapter(ServletRequest request) throws SecurityException {
      X509Certificate[] chain = (X509Certificate[])((X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate"));
      if (chain != null && chain.length != 0) {
         this.cert = chain[0];
         this.certChain = Arrays.asList(chain);
         this.setUsageType(UsageType.SIGNING);
      } else {
         throw new SecurityException("Servlet request does not contain X.509 certificates in attribute javax.servlet.request.X509Certificate");
      }
   }

   public Class getCredentialType() {
      return X509Credential.class;
   }

   public X509Certificate getEntityCertificate() {
      return this.cert;
   }

   public Collection getEntityCertificateChain() {
      return this.certChain;
   }

   public Collection getCRLs() {
      return null;
   }

   public PublicKey getPublicKey() {
      return this.getEntityCertificate().getPublicKey();
   }
}
