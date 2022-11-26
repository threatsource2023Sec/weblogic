package org.python.bouncycastle.crypto;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface KeyEncoder {
   byte[] getEncoded(AsymmetricKeyParameter var1);
}
