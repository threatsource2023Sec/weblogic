package org.python.bouncycastle.jcajce.provider.asymmetric.util;

import java.io.IOException;
import java.math.BigInteger;

public interface DSAEncoder {
   byte[] encode(BigInteger var1, BigInteger var2) throws IOException;

   BigInteger[] decode(byte[] var1) throws IOException;
}
