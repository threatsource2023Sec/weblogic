package org.python.bouncycastle.cert.ocsp.jcajce;

import java.security.PublicKey;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cert.ocsp.BasicOCSPRespBuilder;
import org.python.bouncycastle.cert.ocsp.OCSPException;
import org.python.bouncycastle.operator.DigestCalculator;

public class JcaBasicOCSPRespBuilder extends BasicOCSPRespBuilder {
   public JcaBasicOCSPRespBuilder(PublicKey var1, DigestCalculator var2) throws OCSPException {
      super(SubjectPublicKeyInfo.getInstance(var1.getEncoded()), var2);
   }
}
