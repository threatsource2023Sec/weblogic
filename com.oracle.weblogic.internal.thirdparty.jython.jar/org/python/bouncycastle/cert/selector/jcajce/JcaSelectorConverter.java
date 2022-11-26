package org.python.bouncycastle.cert.selector.jcajce;

import java.io.IOException;
import java.security.cert.X509CertSelector;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.cert.selector.X509CertificateHolderSelector;

public class JcaSelectorConverter {
   public X509CertificateHolderSelector getCertificateHolderSelector(X509CertSelector var1) {
      try {
         return var1.getSubjectKeyIdentifier() != null ? new X509CertificateHolderSelector(X500Name.getInstance(var1.getIssuerAsBytes()), var1.getSerialNumber(), ASN1OctetString.getInstance(var1.getSubjectKeyIdentifier()).getOctets()) : new X509CertificateHolderSelector(X500Name.getInstance(var1.getIssuerAsBytes()), var1.getSerialNumber());
      } catch (IOException var3) {
         throw new IllegalArgumentException("unable to convert issuer: " + var3.getMessage());
      }
   }
}
