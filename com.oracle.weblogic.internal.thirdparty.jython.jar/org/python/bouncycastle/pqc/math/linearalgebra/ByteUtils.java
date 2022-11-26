package org.python.bouncycastle.pqc.math.linearalgebra;

public final class ByteUtils {
   private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   private ByteUtils() {
   }

   public static boolean equals(byte[] var0, byte[] var1) {
      if (var0 == null) {
         return var1 == null;
      } else if (var1 == null) {
         return false;
      } else if (var0.length != var1.length) {
         return false;
      } else {
         boolean var2 = true;

         for(int var3 = var0.length - 1; var3 >= 0; --var3) {
            var2 &= var0[var3] == var1[var3];
         }

         return var2;
      }
   }

   public static boolean equals(byte[][] var0, byte[][] var1) {
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

   public static boolean equals(byte[][][] var0, byte[][][] var1) {
      if (var0.length != var1.length) {
         return false;
      } else {
         boolean var2 = true;

         for(int var3 = var0.length - 1; var3 >= 0; --var3) {
            if (var0[var3].length != var1[var3].length) {
               return false;
            }

            for(int var4 = var0[var3].length - 1; var4 >= 0; --var4) {
               var2 &= equals(var0[var3][var4], var1[var3][var4]);
            }
         }

         return var2;
      }
   }

   public static int deepHashCode(byte[] var0) {
      int var1 = 1;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1 = 31 * var1 + var0[var2];
      }

      return var1;
   }

   public static int deepHashCode(byte[][] var0) {
      int var1 = 1;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1 = 31 * var1 + deepHashCode(var0[var2]);
      }

      return var1;
   }

   public static int deepHashCode(byte[][][] var0) {
      int var1 = 1;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1 = 31 * var1 + deepHashCode(var0[var2]);
      }

      return var1;
   }

   public static byte[] clone(byte[] var0) {
      if (var0 == null) {
         return null;
      } else {
         byte[] var1 = new byte[var0.length];
         System.arraycopy(var0, 0, var1, 0, var0.length);
         return var1;
      }
   }

   public static byte[] fromHexString(String var0) {
      char[] var1 = var0.toUpperCase().toCharArray();
      int var2 = 0;

      for(int var3 = 0; var3 < var1.length; ++var3) {
         if (var1[var3] >= '0' && var1[var3] <= '9' || var1[var3] >= 'A' && var1[var3] <= 'F') {
            ++var2;
         }
      }

      byte[] var6 = new byte[var2 + 1 >> 1];
      int var4 = var2 & 1;

      for(int var5 = 0; var5 < var1.length; ++var5) {
         if (var1[var5] >= '0' && var1[var5] <= '9') {
            var6[var4 >> 1] = (byte)(var6[var4 >> 1] << 4);
            var6[var4 >> 1] = (byte)(var6[var4 >> 1] | var1[var5] - 48);
         } else {
            if (var1[var5] < 'A' || var1[var5] > 'F') {
               continue;
            }

            var6[var4 >> 1] = (byte)(var6[var4 >> 1] << 4);
            var6[var4 >> 1] = (byte)(var6[var4 >> 1] | var1[var5] - 65 + 10);
         }

         ++var4;
      }

      return var6;
   }

   public static String toHexString(byte[] var0) {
      String var1 = "";

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1 = var1 + HEX_CHARS[var0[var2] >>> 4 & 15];
         var1 = var1 + HEX_CHARS[var0[var2] & 15];
      }

      return var1;
   }

   public static String toHexString(byte[] var0, String var1, String var2) {
      String var3 = new String(var1);

      for(int var4 = 0; var4 < var0.length; ++var4) {
         var3 = var3 + HEX_CHARS[var0[var4] >>> 4 & 15];
         var3 = var3 + HEX_CHARS[var0[var4] & 15];
         if (var4 < var0.length - 1) {
            var3 = var3 + var2;
         }
      }

      return var3;
   }

   public static String toBinaryString(byte[] var0) {
      String var1 = "";

      for(int var2 = 0; var2 < var0.length; ++var2) {
         byte var3 = var0[var2];

         for(int var4 = 0; var4 < 8; ++var4) {
            int var5 = var3 >>> var4 & 1;
            var1 = var1 + var5;
         }

         if (var2 != var0.length - 1) {
            var1 = var1 + " ";
         }
      }

      return var1;
   }

   public static byte[] xor(byte[] var0, byte[] var1) {
      byte[] var2 = new byte[var0.length];

      for(int var3 = var0.length - 1; var3 >= 0; --var3) {
         var2[var3] = (byte)(var0[var3] ^ var1[var3]);
      }

      return var2;
   }

   public static byte[] concatenate(byte[] var0, byte[] var1) {
      byte[] var2 = new byte[var0.length + var1.length];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      System.arraycopy(var1, 0, var2, var0.length, var1.length);
      return var2;
   }

   public static byte[] concatenate(byte[][] var0) {
      int var1 = var0[0].length;
      byte[] var2 = new byte[var0.length * var1];
      int var3 = 0;

      for(int var4 = 0; var4 < var0.length; ++var4) {
         System.arraycopy(var0[var4], 0, var2, var3, var1);
         var3 += var1;
      }

      return var2;
   }

   public static byte[][] split(byte[] var0, int var1) throws ArrayIndexOutOfBoundsException {
      if (var1 > var0.length) {
         throw new ArrayIndexOutOfBoundsException();
      } else {
         byte[][] var2 = new byte[][]{new byte[var1], new byte[var0.length - var1]};
         System.arraycopy(var0, 0, var2[0], 0, var1);
         System.arraycopy(var0, var1, var2[1], 0, var0.length - var1);
         return var2;
      }
   }

   public static byte[] subArray(byte[] var0, int var1, int var2) {
      byte[] var3 = new byte[var2 - var1];
      System.arraycopy(var0, var1, var3, 0, var2 - var1);
      return var3;
   }

   public static byte[] subArray(byte[] var0, int var1) {
      return subArray(var0, var1, var0.length);
   }

   public static char[] toCharArray(byte[] var0) {
      char[] var1 = new char[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = (char)var0[var2];
      }

      return var1;
   }
}
