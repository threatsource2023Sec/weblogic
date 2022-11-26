package org.python.bouncycastle.crypto.generators;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.PBEParametersGenerator;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.util.DigestFactory;

public class OpenSSLPBEParametersGenerator extends PBEParametersGenerator {
   private Digest digest = DigestFactory.createMD5();

   public void init(byte[] var1, byte[] var2) {
      super.init(var1, var2, 1);
   }

   private byte[] generateDerivedKey(int var1) {
      byte[] var2 = new byte[this.digest.getDigestSize()];
      byte[] var3 = new byte[var1];
      int var4 = 0;

      while(true) {
         this.digest.update(this.password, 0, this.password.length);
         this.digest.update(this.salt, 0, this.salt.length);
         this.digest.doFinal(var2, 0);
         int var5 = var1 > var2.length ? var2.length : var1;
         System.arraycopy(var2, 0, var3, var4, var5);
         var4 += var5;
         var1 -= var5;
         if (var1 == 0) {
            return var3;
         }

         this.digest.reset();
         this.digest.update(var2, 0, var2.length);
      }
   }

   public CipherParameters generateDerivedParameters(int var1) {
      var1 /= 8;
      byte[] var2 = this.generateDerivedKey(var1);
      return new KeyParameter(var2, 0, var1);
   }

   public CipherParameters generateDerivedParameters(int var1, int var2) {
      var1 /= 8;
      var2 /= 8;
      byte[] var3 = this.generateDerivedKey(var1 + var2);
      return new ParametersWithIV(new KeyParameter(var3, 0, var1), var3, var1, var2);
   }

   public CipherParameters generateDerivedMacParameters(int var1) {
      return this.generateDerivedParameters(var1);
   }
}
