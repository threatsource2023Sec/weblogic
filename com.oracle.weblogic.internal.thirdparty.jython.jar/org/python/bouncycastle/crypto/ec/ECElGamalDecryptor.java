package org.python.bouncycastle.crypto.ec;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.math.ec.ECPoint;

public class ECElGamalDecryptor implements ECDecryptor {
   private ECPrivateKeyParameters key;

   public void init(CipherParameters var1) {
      if (!(var1 instanceof ECPrivateKeyParameters)) {
         throw new IllegalArgumentException("ECPrivateKeyParameters are required for decryption.");
      } else {
         this.key = (ECPrivateKeyParameters)var1;
      }
   }

   public ECPoint decrypt(ECPair var1) {
      if (this.key == null) {
         throw new IllegalStateException("ECElGamalDecryptor not initialised");
      } else {
         ECPoint var2 = var1.getX().multiply(this.key.getD());
         return var1.getY().subtract(var2).normalize();
      }
   }
}
