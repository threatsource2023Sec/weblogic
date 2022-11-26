package org.python.bouncycastle.operator.bc;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.operator.AsymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorException;

public abstract class BcAsymmetricKeyUnwrapper extends AsymmetricKeyUnwrapper {
   private AsymmetricKeyParameter privateKey;

   public BcAsymmetricKeyUnwrapper(AlgorithmIdentifier var1, AsymmetricKeyParameter var2) {
      super(var1);
      this.privateKey = var2;
   }

   public GenericKey generateUnwrappedKey(AlgorithmIdentifier var1, byte[] var2) throws OperatorException {
      AsymmetricBlockCipher var3 = this.createAsymmetricUnwrapper(this.getAlgorithmIdentifier().getAlgorithm());
      var3.init(false, this.privateKey);

      try {
         byte[] var4 = var3.processBlock(var2, 0, var2.length);
         return var1.getAlgorithm().equals(PKCSObjectIdentifiers.des_EDE3_CBC) ? new GenericKey(var1, var4) : new GenericKey(var1, var4);
      } catch (InvalidCipherTextException var5) {
         throw new OperatorException("unable to recover secret key: " + var5.getMessage(), var5);
      }
   }

   protected abstract AsymmetricBlockCipher createAsymmetricUnwrapper(ASN1ObjectIdentifier var1);
}
