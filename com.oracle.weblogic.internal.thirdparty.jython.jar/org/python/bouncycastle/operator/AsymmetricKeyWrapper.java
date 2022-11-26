package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public abstract class AsymmetricKeyWrapper implements KeyWrapper {
   private AlgorithmIdentifier algorithmId;

   protected AsymmetricKeyWrapper(AlgorithmIdentifier var1) {
      this.algorithmId = var1;
   }

   public AlgorithmIdentifier getAlgorithmIdentifier() {
      return this.algorithmId;
   }
}
