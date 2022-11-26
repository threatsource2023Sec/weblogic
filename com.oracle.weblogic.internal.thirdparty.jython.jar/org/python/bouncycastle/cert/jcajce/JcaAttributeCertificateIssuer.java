package org.python.bouncycastle.cert.jcajce;

import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.cert.AttributeCertificateIssuer;

public class JcaAttributeCertificateIssuer extends AttributeCertificateIssuer {
   public JcaAttributeCertificateIssuer(X509Certificate var1) {
      this(var1.getIssuerX500Principal());
   }

   public JcaAttributeCertificateIssuer(X500Principal var1) {
      super(X500Name.getInstance(var1.getEncoded()));
   }
}
