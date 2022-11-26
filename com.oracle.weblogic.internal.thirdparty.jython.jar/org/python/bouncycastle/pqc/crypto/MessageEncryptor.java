package org.python.bouncycastle.pqc.crypto;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.InvalidCipherTextException;

public interface MessageEncryptor {
   void init(boolean var1, CipherParameters var2);

   byte[] messageEncrypt(byte[] var1);

   byte[] messageDecrypt(byte[] var1) throws InvalidCipherTextException;
}
