package org.python.bouncycastle.cms.bc;

import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.cms.KEKIdentifier;
import org.python.bouncycastle.asn1.cms.OtherKeyAttribute;
import org.python.bouncycastle.cms.KEKRecipientInfoGenerator;
import org.python.bouncycastle.operator.bc.BcSymmetricKeyWrapper;

public class BcKEKRecipientInfoGenerator extends KEKRecipientInfoGenerator {
   public BcKEKRecipientInfoGenerator(KEKIdentifier var1, BcSymmetricKeyWrapper var2) {
      super(var1, var2);
   }

   public BcKEKRecipientInfoGenerator(byte[] var1, BcSymmetricKeyWrapper var2) {
      this(new KEKIdentifier(var1, (ASN1GeneralizedTime)null, (OtherKeyAttribute)null), var2);
   }
}
