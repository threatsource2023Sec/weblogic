package org.python.bouncycastle.crypto.generators;

import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.params.DESedeParameters;

public class DESedeKeyGenerator extends DESKeyGenerator {
   private static final int MAX_IT = 20;

   public void init(KeyGenerationParameters var1) {
      this.random = var1.getRandom();
      this.strength = (var1.getStrength() + 7) / 8;
      if (this.strength != 0 && this.strength != 21) {
         if (this.strength == 14) {
            this.strength = 16;
         } else if (this.strength != 24 && this.strength != 16) {
            throw new IllegalArgumentException("DESede key must be 192 or 128 bits long.");
         }
      } else {
         this.strength = 24;
      }

   }

   public byte[] generateKey() {
      byte[] var1 = new byte[this.strength];
      int var2 = 0;

      do {
         this.random.nextBytes(var1);
         DESedeParameters.setOddParity(var1);
         ++var2;
      } while(var2 < 20 && (DESedeParameters.isWeakKey(var1, 0, var1.length) || !DESedeParameters.isRealEDEKey(var1, 0)));

      if (!DESedeParameters.isWeakKey(var1, 0, var1.length) && DESedeParameters.isRealEDEKey(var1, 0)) {
         return var1;
      } else {
         throw new IllegalStateException("Unable to generate DES-EDE key");
      }
   }
}
