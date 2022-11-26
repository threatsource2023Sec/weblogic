package org.python.bouncycastle.jcajce.provider.symmetric.util;

import org.python.bouncycastle.crypto.BlockCipher;

public interface BlockCipherProvider {
   BlockCipher get();
}
