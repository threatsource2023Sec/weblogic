package org.python.bouncycastle.crypto;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class AsymmetricCipherKeyPair {
   private AsymmetricKeyParameter publicParam;
   private AsymmetricKeyParameter privateParam;

   public AsymmetricCipherKeyPair(AsymmetricKeyParameter var1, AsymmetricKeyParameter var2) {
      this.publicParam = var1;
      this.privateParam = var2;
   }

   /** @deprecated */
   public AsymmetricCipherKeyPair(CipherParameters var1, CipherParameters var2) {
      this.publicParam = (AsymmetricKeyParameter)var1;
      this.privateParam = (AsymmetricKeyParameter)var2;
   }

   public AsymmetricKeyParameter getPublic() {
      return this.publicParam;
   }

   public AsymmetricKeyParameter getPrivate() {
      return this.privateParam;
   }
}
