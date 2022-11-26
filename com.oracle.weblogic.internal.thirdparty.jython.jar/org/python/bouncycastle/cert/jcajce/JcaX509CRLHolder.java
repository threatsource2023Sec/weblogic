package org.python.bouncycastle.cert.jcajce;

import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import org.python.bouncycastle.asn1.x509.CertificateList;
import org.python.bouncycastle.cert.X509CRLHolder;

public class JcaX509CRLHolder extends X509CRLHolder {
   public JcaX509CRLHolder(X509CRL var1) throws CRLException {
      super(CertificateList.getInstance(var1.getEncoded()));
   }
}
