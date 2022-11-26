package org.python.bouncycastle.cert.ocsp.jcajce;

import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cert.ocsp.OCSPException;
import org.python.bouncycastle.cert.ocsp.RespID;
import org.python.bouncycastle.operator.DigestCalculator;

public class JcaRespID extends RespID {
   public JcaRespID(X500Principal var1) {
      super(X500Name.getInstance(var1.getEncoded()));
   }

   public JcaRespID(PublicKey var1, DigestCalculator var2) throws OCSPException {
      super(SubjectPublicKeyInfo.getInstance(var1.getEncoded()), var2);
   }
}
