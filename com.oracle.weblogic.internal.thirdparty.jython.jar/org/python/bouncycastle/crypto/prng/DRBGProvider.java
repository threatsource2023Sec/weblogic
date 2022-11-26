package org.python.bouncycastle.crypto.prng;

import org.python.bouncycastle.crypto.prng.drbg.SP80090DRBG;

interface DRBGProvider {
   SP80090DRBG get(EntropySource var1);
}
