package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public abstract class AsymmetricKeyUnwrapper implements KeyUnwrapper {
   private AlgorithmIdentifier algorithmId;

   protected AsymmetricKeyUnwrapper(AlgorithmIdentifier var1) {
      this.algorithmId = var1;
   }

   public AlgorithmIdentifier getAlgorithmIdentifier() {
      return this.algorithmId;
   }
}
