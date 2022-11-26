package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface TlsSigner {
   void init(TlsContext var1);

   byte[] generateRawSignature(AsymmetricKeyParameter var1, byte[] var2) throws CryptoException;

   byte[] generateRawSignature(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2, byte[] var3) throws CryptoException;

   boolean verifyRawSignature(byte[] var1, AsymmetricKeyParameter var2, byte[] var3) throws CryptoException;

   boolean verifyRawSignature(SignatureAndHashAlgorithm var1, byte[] var2, AsymmetricKeyParameter var3, byte[] var4) throws CryptoException;

   Signer createSigner(AsymmetricKeyParameter var1);

   Signer createSigner(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2);

   Signer createVerifyer(AsymmetricKeyParameter var1);

   Signer createVerifyer(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2);

   boolean isValidPublicKey(AsymmetricKeyParameter var1);
}
