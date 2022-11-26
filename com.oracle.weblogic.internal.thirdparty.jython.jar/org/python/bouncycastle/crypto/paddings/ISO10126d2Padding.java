package org.python.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.InvalidCipherTextException;

public class ISO10126d2Padding implements BlockCipherPadding {
   SecureRandom random;

   public void init(SecureRandom var1) throws IllegalArgumentException {
      if (var1 != null) {
         this.random = var1;
      } else {
         this.random = new SecureRandom();
      }

   }

   public String getPaddingName() {
      return "ISO10126-2";
   }

   public int addPadding(byte[] var1, int var2) {
      byte var3;
      for(var3 = (byte)(var1.length - var2); var2 < var1.length - 1; ++var2) {
         var1[var2] = (byte)this.random.nextInt();
      }

      var1[var2] = var3;
      return var3;
   }

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2 = var1[var1.length - 1] & 255;
      if (var2 > var1.length) {
         throw new InvalidCipherTextException("pad block corrupted");
      } else {
         return var2;
      }
   }
}
