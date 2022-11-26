package org.python.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.InvalidCipherTextException;

public class TBCPadding implements BlockCipherPadding {
   public void init(SecureRandom var1) throws IllegalArgumentException {
   }

   public String getPaddingName() {
      return "TBC";
   }

   public int addPadding(byte[] var1, int var2) {
      int var3 = var1.length - var2;
      byte var4;
      if (var2 > 0) {
         var4 = (byte)((var1[var2 - 1] & 1) == 0 ? 255 : 0);
      } else {
         var4 = (byte)((var1[var1.length - 1] & 1) == 0 ? 255 : 0);
      }

      while(var2 < var1.length) {
         var1[var2] = var4;
         ++var2;
      }

      return var3;
   }

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      byte var2 = var1[var1.length - 1];

      int var3;
      for(var3 = var1.length - 1; var3 > 0 && var1[var3 - 1] == var2; --var3) {
      }

      return var1.length - var3;
   }
}
