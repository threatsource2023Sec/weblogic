package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public abstract class AbstractTlsSigner implements TlsSigner {
   protected TlsContext context;

   public void init(TlsContext var1) {
      this.context = var1;
   }

   public byte[] generateRawSignature(AsymmetricKeyParameter var1, byte[] var2) throws CryptoException {
      return this.generateRawSignature((SignatureAndHashAlgorithm)null, var1, var2);
   }

   public boolean verifyRawSignature(byte[] var1, AsymmetricKeyParameter var2, byte[] var3) throws CryptoException {
      return this.verifyRawSignature((SignatureAndHashAlgorithm)null, var1, var2, var3);
   }

   public Signer createSigner(AsymmetricKeyParameter var1) {
      return this.createSigner((SignatureAndHashAlgorithm)null, var1);
   }

   public Signer createVerifyer(AsymmetricKeyParameter var1) {
      return this.createVerifyer((SignatureAndHashAlgorithm)null, var1);
   }
}
