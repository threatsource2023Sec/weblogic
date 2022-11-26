package org.python.bouncycastle.crypto.ec;

import org.python.bouncycastle.crypto.CipherParameters;

public interface ECPairTransform {
   void init(CipherParameters var1);

   ECPair transform(ECPair var1);
}
