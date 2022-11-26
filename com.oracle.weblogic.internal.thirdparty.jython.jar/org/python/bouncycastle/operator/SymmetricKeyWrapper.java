package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public abstract class SymmetricKeyWrapper implements KeyWrapper {
   private AlgorithmIdentifier algorithmId;

   protected SymmetricKeyWrapper(AlgorithmIdentifier var1) {
      this.algorithmId = var1;
   }

   public AlgorithmIdentifier getAlgorithmIdentifier() {
      return this.algorithmId;
   }
}
