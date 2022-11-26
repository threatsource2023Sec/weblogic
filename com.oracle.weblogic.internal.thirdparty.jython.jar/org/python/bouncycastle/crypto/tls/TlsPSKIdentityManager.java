package org.python.bouncycastle.crypto.tls;

public interface TlsPSKIdentityManager {
   byte[] getHint();

   byte[] getPSK(byte[] var1);
}
