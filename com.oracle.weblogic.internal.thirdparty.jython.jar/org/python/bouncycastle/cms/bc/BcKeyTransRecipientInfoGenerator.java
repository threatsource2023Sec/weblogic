package org.python.bouncycastle.cms.bc;

import org.python.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cms.KeyTransRecipientInfoGenerator;
import org.python.bouncycastle.operator.bc.BcAsymmetricKeyWrapper;

public abstract class BcKeyTransRecipientInfoGenerator extends KeyTransRecipientInfoGenerator {
   public BcKeyTransRecipientInfoGenerator(X509CertificateHolder var1, BcAsymmetricKeyWrapper var2) {
      super((IssuerAndSerialNumber)(new IssuerAndSerialNumber(var1.toASN1Structure())), var2);
   }

   public BcKeyTransRecipientInfoGenerator(byte[] var1, BcAsymmetricKeyWrapper var2) {
      super((byte[])var1, var2);
   }
}
