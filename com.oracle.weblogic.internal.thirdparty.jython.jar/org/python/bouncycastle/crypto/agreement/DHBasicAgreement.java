package org.python.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.BasicAgreement;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;

public class DHBasicAgreement implements BasicAgreement {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private DHPrivateKeyParameters key;
   private DHParameters dhParams;

   public void init(CipherParameters var1) {
      AsymmetricKeyParameter var3;
      if (var1 instanceof ParametersWithRandom) {
         ParametersWithRandom var2 = (ParametersWithRandom)var1;
         var3 = (AsymmetricKeyParameter)var2.getParameters();
      } else {
         var3 = (AsymmetricKeyParameter)var1;
      }

      if (!(var3 instanceof DHPrivateKeyParameters)) {
         throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
      } else {
         this.key = (DHPrivateKeyParameters)var3;
         this.dhParams = this.key.getParameters();
      }
   }

   public int getFieldSize() {
      return (this.key.getParameters().getP().bitLength() + 7) / 8;
   }

   public BigInteger calculateAgreement(CipherParameters var1) {
      DHPublicKeyParameters var2 = (DHPublicKeyParameters)var1;
      if (!var2.getParameters().equals(this.dhParams)) {
         throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
      } else {
         BigInteger var3 = var2.getY().modPow(this.key.getX(), this.dhParams.getP());
         if (var3.compareTo(ONE) == 0) {
            throw new IllegalStateException("Shared key can't be 1");
         } else {
            return var3;
         }
      }
   }
}
