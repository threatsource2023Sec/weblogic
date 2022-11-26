package org.python.bouncycastle.cert.ocsp.jcajce;

import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.python.bouncycastle.cert.ocsp.CertificateID;
import org.python.bouncycastle.cert.ocsp.OCSPException;
import org.python.bouncycastle.operator.DigestCalculator;

public class JcaCertificateID extends CertificateID {
   public JcaCertificateID(DigestCalculator var1, X509Certificate var2, BigInteger var3) throws OCSPException, CertificateEncodingException {
      super(var1, new JcaX509CertificateHolder(var2), var3);
   }
}
