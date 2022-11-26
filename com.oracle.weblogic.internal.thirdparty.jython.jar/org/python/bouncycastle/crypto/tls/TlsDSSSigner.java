package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.python.bouncycastle.crypto.signers.DSASigner;
import org.python.bouncycastle.crypto.signers.HMacDSAKCalculator;

public class TlsDSSSigner extends TlsDSASigner {
   public boolean isValidPublicKey(AsymmetricKeyParameter var1) {
      return var1 instanceof DSAPublicKeyParameters;
   }

   protected DSA createDSAImpl(short var1) {
      return new DSASigner(new HMacDSAKCalculator(TlsUtils.createHash(var1)));
   }

   protected short getSignatureAlgorithm() {
      return 2;
   }
}
