package org.python.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.BasicAgreement;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.math.ec.ECPoint;

public class ECDHCBasicAgreement implements BasicAgreement {
   ECPrivateKeyParameters key;

   public void init(CipherParameters var1) {
      this.key = (ECPrivateKeyParameters)var1;
   }

   public int getFieldSize() {
      return (this.key.getParameters().getCurve().getFieldSize() + 7) / 8;
   }

   public BigInteger calculateAgreement(CipherParameters var1) {
      ECPublicKeyParameters var2 = (ECPublicKeyParameters)var1;
      ECDomainParameters var3 = var2.getParameters();
      if (!var3.equals(this.key.getParameters())) {
         throw new IllegalStateException("ECDHC public key has wrong domain parameters");
      } else {
         BigInteger var4 = var3.getH().multiply(this.key.getD()).mod(var3.getN());
         ECPoint var5 = var2.getQ().multiply(var4).normalize();
         if (var5.isInfinity()) {
            throw new IllegalStateException("Infinity is not a valid agreement value for ECDHC");
         } else {
            return var5.getAffineXCoord().toBigInteger();
         }
      }
   }
}
