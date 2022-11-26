package org.python.bouncycastle.pqc.crypto.gmss.util;

public class GMSSUtil {
   public byte[] intToBytesLittleEndian(int var1) {
      byte[] var2 = new byte[]{(byte)(var1 & 255), (byte)(var1 >> 8 & 255), (byte)(var1 >> 16 & 255), (byte)(var1 >> 24 & 255)};
      return var2;
   }

   public int bytesToIntLittleEndian(byte[] var1) {
      return var1[0] & 255 | (var1[1] & 255) << 8 | (var1[2] & 255) << 16 | (var1[3] & 255) << 24;
   }

   public int bytesToIntLittleEndian(byte[] var1, int var2) {
      return var1[var2++] & 255 | (var1[var2++] & 255) << 8 | (var1[var2++] & 255) << 16 | (var1[var2] & 255) << 24;
   }

   public byte[] concatenateArray(byte[][] var1) {
      byte[] var2 = new byte[var1.length * var1[0].length];
      int var3 = 0;

      for(int var4 = 0; var4 < var1.length; ++var4) {
         System.arraycopy(var1[var4], 0, var2, var3, var1[var4].length);
         var3 += var1[var4].length;
      }

      return var2;
   }

   public void printArray(String var1, byte[][] var2) {
      System.out.println(var1);
      int var3 = 0;

      for(int var4 = 0; var4 < var2.length; ++var4) {
         for(int var5 = 0; var5 < var2[0].length; ++var5) {
            System.out.println(var3 + "; " + var2[var4][var5]);
            ++var3;
         }
      }

   }

   public void printArray(String var1, byte[] var2) {
      System.out.println(var1);
      int var3 = 0;

      for(int var4 = 0; var4 < var2.length; ++var4) {
         System.out.println(var3 + "; " + var2[var4]);
         ++var3;
      }

   }

   public boolean testPowerOfTwo(int var1) {
      int var2;
      for(var2 = 1; var2 < var1; var2 <<= 1) {
      }

      return var1 == var2;
   }

   public int getLog(int var1) {
      int var2 = 1;

      for(int var3 = 2; var3 < var1; ++var2) {
         var3 <<= 1;
      }

      return var2;
   }
}
