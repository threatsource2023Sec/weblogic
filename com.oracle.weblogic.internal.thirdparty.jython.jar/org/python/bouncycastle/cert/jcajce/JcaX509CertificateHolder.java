package org.python.bouncycastle.cert.jcajce;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.asn1.x509.Certificate;
import org.python.bouncycastle.cert.X509CertificateHolder;

public class JcaX509CertificateHolder extends X509CertificateHolder {
   public JcaX509CertificateHolder(X509Certificate var1) throws CertificateEncodingException {
      super(Certificate.getInstance(var1.getEncoded()));
   }
}
