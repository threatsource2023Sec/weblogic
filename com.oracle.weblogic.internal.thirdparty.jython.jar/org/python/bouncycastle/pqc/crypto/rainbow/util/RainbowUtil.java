package org.python.bouncycastle.pqc.crypto.rainbow.util;

public class RainbowUtil {
   public static int[] convertArraytoInt(byte[] var0) {
      int[] var1 = new int[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = var0[var2] & 255;
      }

      return var1;
   }

   public static short[] convertArray(byte[] var0) {
      short[] var1 = new short[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = (short)(var0[var2] & 255);
      }

      return var1;
   }

   public static short[][] convertArray(byte[][] var0) {
      short[][] var1 = new short[var0.length][var0[0].length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         for(int var3 = 0; var3 < var0[0].length; ++var3) {
            var1[var2][var3] = (short)(var0[var2][var3] & 255);
         }
      }

      return var1;
   }

   public static short[][][] convertArray(byte[][][] var0) {
      short[][][] var1 = new short[var0.length][var0[0].length][var0[0][0].length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         for(int var3 = 0; var3 < var0[0].length; ++var3) {
            for(int var4 = 0; var4 < var0[0][0].length; ++var4) {
               var1[var2][var3][var4] = (short)(var0[var2][var3][var4] & 255);
            }
         }
      }

      return var1;
   }

   public static byte[] convertIntArray(int[] var0) {
      byte[] var1 = new byte[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = (byte)var0[var2];
      }

      return var1;
   }

   public static byte[] convertArray(short[] var0) {
      byte[] var1 = new byte[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = (byte)var0[var2];
      }

      return var1;
   }

   public static byte[][] convertArray(short[][] var0) {
      byte[][] var1 = new byte[var0.length][var0[0].length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         for(int var3 = 0; var3 < var0[0].length; ++var3) {
            var1[var2][var3] = (byte)var0[var2][var3];
         }
      }

      return var1;
   }

   public static byte[][][] convertArray(short[][][] var0) {
      byte[][][] var1 = new byte[var0.length][var0[0].length][var0[0][0].length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         for(int var3 = 0; var3 < var0[0].length; ++var3) {
            for(int var4 = 0; var4 < var0[0][0].length; ++var4) {
               var1[var2][var3][var4] = (byte)var0[var2][var3][var4];
            }
         }
      }

      return var1;
   }

   public static boolean equals(short[] var0, short[] var1) {
      if (var0.length != var1.length) {
         return false;
      } else {
         boolean var2 = true;

         for(int var3 = var0.length - 1; var3 >= 0; --var3) {
            var2 &= var0[var3] == var1[var3];
         }

         return var2;
      }
   }

   public static boolean equals(short[][] var0, short[][] var1) {
      if (var0.length != var1.length) {
         return false;
      } else {
         boolean var2 = true;

         for(int var3 = var0.length - 1; var3 >= 0; --var3) {
            var2 &= equals(var0[var3], var1[var3]);
         }

         return var2;
      }
   }

   public static boolean equals(short[][][] var0, short[][][] var1) {
      if (var0.length != var1.length) {
         return false;
      } else {
         boolean var2 = true;

         for(int var3 = var0.length - 1; var3 >= 0; --var3) {
            var2 &= equals(var0[var3], var1[var3]);
         }

         return var2;
      }
   }
}
