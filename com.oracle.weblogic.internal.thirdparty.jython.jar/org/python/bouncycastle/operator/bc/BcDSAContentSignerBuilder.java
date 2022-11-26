package org.python.bouncycastle.operator.bc;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.signers.DSADigestSigner;
import org.python.bouncycastle.crypto.signers.DSASigner;
import org.python.bouncycastle.operator.OperatorCreationException;

public class BcDSAContentSignerBuilder extends BcContentSignerBuilder {
   public BcDSAContentSignerBuilder(AlgorithmIdentifier var1, AlgorithmIdentifier var2) {
      super(var1, var2);
   }

   protected Signer createSigner(AlgorithmIdentifier var1, AlgorithmIdentifier var2) throws OperatorCreationException {
      ExtendedDigest var3 = this.digestProvider.get(var2);
      return new DSADigestSigner(new DSASigner(), var3);
   }
}
