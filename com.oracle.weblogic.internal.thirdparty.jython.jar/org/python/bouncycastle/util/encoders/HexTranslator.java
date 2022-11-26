package org.python.bouncycastle.util.encoders;

public class HexTranslator implements Translator {
   private static final byte[] hexTable = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

   public int getEncodedBlockSize() {
      return 2;
   }

   public int encode(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      int var6 = 0;

      for(int var7 = 0; var6 < var3; var7 += 2) {
         var4[var5 + var7] = hexTable[var1[var2] >> 4 & 15];
         var4[var5 + var7 + 1] = hexTable[var1[var2] & 15];
         ++var2;
         ++var6;
      }

      return var3 * 2;
   }

   public int getDecodedBlockSize() {
      return 1;
   }

   public int decode(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      int var6 = var3 / 2;

      for(int var7 = 0; var7 < var6; ++var7) {
         byte var8 = var1[var2 + var7 * 2];
         byte var9 = var1[var2 + var7 * 2 + 1];
         if (var8 < 97) {
            var4[var5] = (byte)(var8 - 48 << 4);
         } else {
            var4[var5] = (byte)(var8 - 97 + 10 << 4);
         }

         if (var9 < 97) {
            var4[var5] += (byte)(var9 - 48);
         } else {
            var4[var5] += (byte)(var9 - 97 + 10);
         }

         ++var5;
      }

      return var6;
   }
}
