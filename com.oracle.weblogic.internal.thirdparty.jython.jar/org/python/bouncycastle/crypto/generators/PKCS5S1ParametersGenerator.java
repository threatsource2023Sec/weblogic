package org.python.bouncycastle.crypto.generators;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.PBEParametersGenerator;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

public class PKCS5S1ParametersGenerator extends PBEParametersGenerator {
   private Digest digest;

   public PKCS5S1ParametersGenerator(Digest var1) {
      this.digest = var1;
   }

   private byte[] generateDerivedKey() {
      byte[] var1 = new byte[this.digest.getDigestSize()];
      this.digest.update(this.password, 0, this.password.length);
      this.digest.update(this.salt, 0, this.salt.length);
      this.digest.doFinal(var1, 0);

      for(int var2 = 1; var2 < this.iterationCount; ++var2) {
         this.digest.update(var1, 0, var1.length);
         this.digest.doFinal(var1, 0);
      }

      return var1;
   }

   public CipherParameters generateDerivedParameters(int var1) {
      var1 /= 8;
      if (var1 > this.digest.getDigestSize()) {
         throw new IllegalArgumentException("Can't generate a derived key " + var1 + " bytes long.");
      } else {
         byte[] var2 = this.generateDerivedKey();
         return new KeyParameter(var2, 0, var1);
      }
   }

   public CipherParameters generateDerivedParameters(int var1, int var2) {
      var1 /= 8;
      var2 /= 8;
      if (var1 + var2 > this.digest.getDigestSize()) {
         throw new IllegalArgumentException("Can't generate a derived key " + (var1 + var2) + " bytes long.");
      } else {
         byte[] var3 = this.generateDerivedKey();
         return new ParametersWithIV(new KeyParameter(var3, 0, var1), var3, var1, var2);
      }
   }

   public CipherParameters generateDerivedMacParameters(int var1) {
      return this.generateDerivedParameters(var1);
   }
}
