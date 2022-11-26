package org.python.bouncycastle.pqc.crypto.sphincs;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.KeyGenerationParameters;

public class SPHINCS256KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private SecureRandom random;
   private Digest treeDigest;

   public void init(KeyGenerationParameters var1) {
      this.random = var1.getRandom();
      this.treeDigest = ((SPHINCS256KeyGenerationParameters)var1).getTreeDigest();
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      Tree.leafaddr var1 = new Tree.leafaddr();
      byte[] var2 = new byte[1088];
      this.random.nextBytes(var2);
      byte[] var3 = new byte[1056];
      System.arraycopy(var2, 32, var3, 0, 1024);
      var1.level = 11;
      var1.subtree = 0L;
      var1.subleaf = 0L;
      HashFunctions var4 = new HashFunctions(this.treeDigest);
      Tree.treehash(var4, var3, 1024, 5, var2, var1, var3, 0);
      return new AsymmetricCipherKeyPair(new SPHINCSPublicKeyParameters(var3), new SPHINCSPrivateKeyParameters(var2));
   }
}
