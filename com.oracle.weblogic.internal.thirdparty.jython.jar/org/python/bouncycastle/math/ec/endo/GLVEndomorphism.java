package org.python.bouncycastle.math.ec.endo;

import java.math.BigInteger;

public interface GLVEndomorphism extends ECEndomorphism {
   BigInteger[] decomposeScalar(BigInteger var1);
}
