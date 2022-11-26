package org.python.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.InvalidCipherTextException;

public class ISO7816d4Padding implements BlockCipherPadding {
   public void init(SecureRandom var1) throws IllegalArgumentException {
   }

   public String getPaddingName() {
      return "ISO7816-4";
   }

   public int addPadding(byte[] var1, int var2) {
      int var3 = var1.length - var2;
      var1[var2] = -128;
      ++var2;

      while(var2 < var1.length) {
         var1[var2] = 0;
         ++var2;
      }

      return var3;
   }

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2;
      for(var2 = var1.length - 1; var2 > 0 && var1[var2] == 0; --var2) {
      }

      if (var1[var2] != -128) {
         throw new InvalidCipherTextException("pad block corrupted");
      } else {
         return var1.length - var2;
      }
   }
}
