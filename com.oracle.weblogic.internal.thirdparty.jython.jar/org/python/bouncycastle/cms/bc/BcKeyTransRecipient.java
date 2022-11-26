package org.python.bouncycastle.cms.bc;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.KeyTransRecipient;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.operator.OperatorException;
import org.python.bouncycastle.operator.bc.BcRSAAsymmetricKeyUnwrapper;

public abstract class BcKeyTransRecipient implements KeyTransRecipient {
   private AsymmetricKeyParameter recipientKey;

   public BcKeyTransRecipient(AsymmetricKeyParameter var1) {
      this.recipientKey = var1;
   }

   protected CipherParameters extractSecretKey(AlgorithmIdentifier var1, AlgorithmIdentifier var2, byte[] var3) throws CMSException {
      BcRSAAsymmetricKeyUnwrapper var4 = new BcRSAAsymmetricKeyUnwrapper(var1, this.recipientKey);

      try {
         return CMSUtils.getBcKey(var4.generateUnwrappedKey(var2, var3));
      } catch (OperatorException var6) {
         throw new CMSException("exception unwrapping key: " + var6.getMessage(), var6);
      }
   }
}
