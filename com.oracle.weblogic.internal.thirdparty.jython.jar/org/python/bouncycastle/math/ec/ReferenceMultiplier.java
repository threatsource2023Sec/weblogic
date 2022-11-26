package org.python.bouncycastle.math.ec;

import java.math.BigInteger;

public class ReferenceMultiplier extends AbstractECMultiplier {
   protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
      return ECAlgorithms.referenceMultiply(var1, var2);
   }
}
