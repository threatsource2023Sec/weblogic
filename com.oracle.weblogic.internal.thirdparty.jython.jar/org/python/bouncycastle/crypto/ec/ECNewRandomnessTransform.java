package org.python.bouncycastle.crypto.ec;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.FixedPointCombMultiplier;

public class ECNewRandomnessTransform implements ECPairFactorTransform {
   private ECPublicKeyParameters key;
   private SecureRandom random;
   private BigInteger lastK;

   public void init(CipherParameters var1) {
      if (var1 instanceof ParametersWithRandom) {
         ParametersWithRandom var2 = (ParametersWithRandom)var1;
         if (!(var2.getParameters() instanceof ECPublicKeyParameters)) {
            throw new IllegalArgumentException("ECPublicKeyParameters are required for new randomness transform.");
         }

         this.key = (ECPublicKeyParameters)var2.getParameters();
         this.random = var2.getRandom();
      } else {
         if (!(var1 instanceof ECPublicKeyParameters)) {
            throw new IllegalArgumentException("ECPublicKeyParameters are required for new randomness transform.");
         }

         this.key = (ECPublicKeyParameters)var1;
         this.random = new SecureRandom();
      }

   }

   public ECPair transform(ECPair var1) {
      if (this.key == null) {
         throw new IllegalStateException("ECNewRandomnessTransform not initialised");
      } else {
         ECDomainParameters var2 = this.key.getParameters();
         BigInteger var3 = var2.getN();
         ECMultiplier var4 = this.createBasePointMultiplier();
         BigInteger var5 = ECUtil.generateK(var3, this.random);
         ECPoint[] var6 = new ECPoint[]{var4.multiply(var2.getG(), var5).add(var1.getX()), this.key.getQ().multiply(var5).add(var1.getY())};
         var2.getCurve().normalizeAll(var6);
         this.lastK = var5;
         return new ECPair(var6[0], var6[1]);
      }
   }

   public BigInteger getTransformValue() {
      return this.lastK;
   }

   protected ECMultiplier createBasePointMultiplier() {
      return new FixedPointCombMultiplier();
   }
}
