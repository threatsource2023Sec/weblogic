package org.python.bouncycastle.crypto.ec;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.math.ec.ECPoint;

public interface ECEncryptor {
   void init(CipherParameters var1);

   ECPair encrypt(ECPoint var1);
}
