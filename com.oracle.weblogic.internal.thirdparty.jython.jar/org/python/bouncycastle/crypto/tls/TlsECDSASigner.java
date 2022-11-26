package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.signers.ECDSASigner;
import org.python.bouncycastle.crypto.signers.HMacDSAKCalculator;

public class TlsECDSASigner extends TlsDSASigner {
   public boolean isValidPublicKey(AsymmetricKeyParameter var1) {
      return var1 instanceof ECPublicKeyParameters;
   }

   protected DSA createDSAImpl(short var1) {
      return new ECDSASigner(new HMacDSAKCalculator(TlsUtils.createHash(var1)));
   }

   protected short getSignatureAlgorithm() {
      return 3;
   }
}
