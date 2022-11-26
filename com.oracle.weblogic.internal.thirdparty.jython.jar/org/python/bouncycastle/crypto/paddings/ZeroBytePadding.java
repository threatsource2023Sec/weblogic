package org.python.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.InvalidCipherTextException;

public class ZeroBytePadding implements BlockCipherPadding {
   public void init(SecureRandom var1) throws IllegalArgumentException {
   }

   public String getPaddingName() {
      return "ZeroByte";
   }

   public int addPadding(byte[] var1, int var2) {
      int var3;
      for(var3 = var1.length - var2; var2 < var1.length; ++var2) {
         var1[var2] = 0;
      }

      return var3;
   }

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2;
      for(var2 = var1.length; var2 > 0 && var1[var2 - 1] == 0; --var2) {
      }

      return var1.length - var2;
   }
}
