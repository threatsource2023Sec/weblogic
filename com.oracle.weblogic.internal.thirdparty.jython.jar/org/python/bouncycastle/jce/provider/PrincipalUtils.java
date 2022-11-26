package org.python.bouncycastle.jce.provider;

import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.x509.X509AttributeCertificate;

class PrincipalUtils {
   static X500Name getSubjectPrincipal(X509Certificate var0) {
      return X500Name.getInstance(var0.getSubjectX500Principal().getEncoded());
   }

   static X500Name getIssuerPrincipal(X509CRL var0) {
      return X500Name.getInstance(var0.getIssuerX500Principal().getEncoded());
   }

   static X500Name getIssuerPrincipal(X509Certificate var0) {
      return X500Name.getInstance(var0.getIssuerX500Principal().getEncoded());
   }

   static X500Name getCA(TrustAnchor var0) {
      return X500Name.getInstance(var0.getCA().getEncoded());
   }

   static X500Name getEncodedIssuerPrincipal(Object var0) {
      return var0 instanceof X509Certificate ? getIssuerPrincipal((X509Certificate)var0) : X500Name.getInstance(((X500Principal)((X509AttributeCertificate)var0).getIssuer().getPrincipals()[0]).getEncoded());
   }
}
