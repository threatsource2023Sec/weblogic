package org.python.bouncycastle.cms.bc;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.KEKRecipient;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.operator.OperatorException;
import org.python.bouncycastle.operator.SymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.bc.BcSymmetricKeyUnwrapper;

public abstract class BcKEKRecipient implements KEKRecipient {
   private SymmetricKeyUnwrapper unwrapper;

   public BcKEKRecipient(BcSymmetricKeyUnwrapper var1) {
      this.unwrapper = var1;
   }

   protected CipherParameters extractSecretKey(AlgorithmIdentifier var1, AlgorithmIdentifier var2, byte[] var3) throws CMSException {
      try {
         return CMSUtils.getBcKey(this.unwrapper.generateUnwrappedKey(var2, var3));
      } catch (OperatorException var5) {
         throw new CMSException("exception unwrapping key: " + var5.getMessage(), var5);
      }
   }
}
