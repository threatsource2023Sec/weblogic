package org.python.bouncycastle.crypto.ec;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.math.ec.ECPoint;

public interface ECDecryptor {
   void init(CipherParameters var1);

   ECPoint decrypt(ECPair var1);
}
