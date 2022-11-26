package org.python.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.InvalidCipherTextException;

public class PKCS7Padding implements BlockCipherPadding {
   public void init(SecureRandom var1) throws IllegalArgumentException {
   }

   public String getPaddingName() {
      return "PKCS7";
   }

   public int addPadding(byte[] var1, int var2) {
      byte var3;
      for(var3 = (byte)(var1.length - var2); var2 < var1.length; ++var2) {
         var1[var2] = var3;
      }

      return var3;
   }

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2 = var1[var1.length - 1] & 255;
      byte var3 = (byte)var2;
      boolean var4 = var2 > var1.length | var2 == 0;

      for(int var5 = 0; var5 < var1.length; ++var5) {
         var4 |= var1.length - var5 <= var2 & var1[var5] != var3;
      }

      if (var4) {
         throw new InvalidCipherTextException("pad block corrupted");
      } else {
         return var2;
      }
   }
}
