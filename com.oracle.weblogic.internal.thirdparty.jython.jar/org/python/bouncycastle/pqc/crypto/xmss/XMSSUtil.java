package org.python.bouncycastle.pqc.crypto.xmss;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.util.encoders.Hex;

public class XMSSUtil {
   public static int log2(int var0) {
      int var1;
      for(var1 = 0; (var0 >>= 1) != 0; ++var1) {
      }

      return var1;
   }

   public static byte[] toBytesBigEndian(long var0, int var2) {
      byte[] var3 = new byte[var2];

      for(int var4 = var2 - 1; var4 >= 0; --var4) {
         var3[var4] = (byte)((int)var0);
         var0 >>>= 8;
      }

      return var3;
   }

   public static void intToBytesBigEndianOffset(byte[] var0, int var1, int var2) {
      if (var0 == null) {
         throw new NullPointerException("in == null");
      } else if (var0.length - var2 < 4) {
         throw new IllegalArgumentException("not enough space in array");
      } else {
         var0[var2] = (byte)(var1 >> 24 & 255);
         var0[var2 + 1] = (byte)(var1 >> 16 & 255);
         var0[var2 + 2] = (byte)(var1 >> 8 & 255);
         var0[var2 + 3] = (byte)(var1 & 255);
      }
   }

   public static void longToBytesBigEndianOffset(byte[] var0, long var1, int var3) {
      if (var0 == null) {
         throw new NullPointerException("in == null");
      } else if (var0.length - var3 < 8) {
         throw new IllegalArgumentException("not enough space in array");
      } else {
         var0[var3] = (byte)((int)(var1 >> 56 & 255L));
         var0[var3 + 1] = (byte)((int)(var1 >> 48 & 255L));
         var0[var3 + 2] = (byte)((int)(var1 >> 40 & 255L));
         var0[var3 + 3] = (byte)((int)(var1 >> 32 & 255L));
         var0[var3 + 4] = (byte)((int)(var1 >> 24 & 255L));
         var0[var3 + 5] = (byte)((int)(var1 >> 16 & 255L));
         var0[var3 + 6] = (byte)((int)(var1 >> 8 & 255L));
         var0[var3 + 7] = (byte)((int)(var1 & 255L));
      }
   }

   public static long bytesToXBigEndian(byte[] var0, int var1, int var2) {
      if (var0 == null) {
         throw new NullPointerException("in == null");
      } else {
         long var3 = 0L;

         for(int var5 = var1; var5 < var1 + var2; ++var5) {
            var3 = var3 << 8 | (long)(var0[var5] & 255);
         }

         return var3;
      }
   }

   public static byte[] cloneArray(byte[] var0) {
      if (var0 == null) {
         throw new NullPointerException("in == null");
      } else {
         byte[] var1 = new byte[var0.length];

         for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = var0[var2];
         }

         return var1;
      }
   }

   public static byte[][] cloneArray(byte[][] var0) {
      if (hasNullPointer(var0)) {
         throw new NullPointerException("in has null pointers");
      } else {
         byte[][] var1 = new byte[var0.length][];

         for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = new byte[var0[var2].length];

            for(int var3 = 0; var3 < var0[var2].length; ++var3) {
               var1[var2][var3] = var0[var2][var3];
            }
         }

         return var1;
      }
   }

   public static byte[] concat(byte[]... var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1 += var0[var2].length;
      }

      byte[] var5 = new byte[var1];
      int var3 = 0;

      for(int var4 = 0; var4 < var0.length; ++var4) {
         System.arraycopy(var0[var4], 0, var5, var3, var0[var4].length);
         var3 += var0[var4].length;
      }

      return var5;
   }

   public static boolean compareByteArray(byte[] var0, byte[] var1) {
      if (var0 != null && var1 != null) {
         if (var0.length != var1.length) {
            throw new IllegalArgumentException("size of a and b must be equal");
         } else {
            for(int var2 = 0; var2 < var0.length; ++var2) {
               if (var0[var2] != var1[var2]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         throw new NullPointerException("a or b == null");
      }
   }

   public static boolean compareByteArray(byte[][] var0, byte[][] var1) {
      if (!hasNullPointer(var0) && !hasNullPointer(var1)) {
         for(int var2 = 0; var2 < var0.length; ++var2) {
            if (!compareByteArray(var0[var2], var1[var2])) {
               return false;
            }
         }

         return true;
      } else {
         throw new NullPointerException("a or b == null");
      }
   }

   public static void dumpByteArray(byte[][] var0) {
      if (hasNullPointer(var0)) {
         throw new NullPointerException("x has null pointers");
      } else {
         for(int var1 = 0; var1 < var0.length; ++var1) {
            System.out.println(Hex.toHexString(var0[var1]));
         }

      }
   }

   public static boolean hasNullPointer(byte[][] var0) {
      if (var0 == null) {
         return true;
      } else {
         for(int var1 = 0; var1 < var0.length; ++var1) {
            if (var0[var1] == null) {
               return true;
            }
         }

         return false;
      }
   }

   public static void copyBytesAtOffset(byte[] var0, byte[] var1, int var2) {
      if (var0 == null) {
         throw new NullPointerException("dst == null");
      } else if (var1 == null) {
         throw new NullPointerException("src == null");
      } else if (var2 < 0) {
         throw new IllegalArgumentException("offset hast to be >= 0");
      } else if (var1.length + var2 > var0.length) {
         throw new IllegalArgumentException("src length + offset must not be greater than size of destination");
      } else {
         for(int var3 = 0; var3 < var1.length; ++var3) {
            var0[var2 + var3] = var1[var3];
         }

      }
   }

   public static byte[] extractBytesAtOffset(byte[] var0, int var1, int var2) {
      if (var0 == null) {
         throw new NullPointerException("src == null");
      } else if (var1 < 0) {
         throw new IllegalArgumentException("offset hast to be >= 0");
      } else if (var2 < 0) {
         throw new IllegalArgumentException("length hast to be >= 0");
      } else if (var1 + var2 > var0.length) {
         throw new IllegalArgumentException("offset + length must not be greater then size of source array");
      } else {
         byte[] var3 = new byte[var2];

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4] = var0[var1 + var4];
         }

         return var3;
      }
   }

   public static boolean isIndexValid(int var0, long var1) {
      if (var1 < 0L) {
         throw new IllegalStateException("index must not be negative");
      } else {
         return var1 < 1L << var0;
      }
   }

   public static int getDigestSize(Digest var0) {
      if (var0 == null) {
         throw new NullPointerException("digest == null");
      } else {
         String var1 = var0.getAlgorithmName();
         if (var1.equals("SHAKE128")) {
            return 32;
         } else {
            return var1.equals("SHAKE256") ? 64 : var0.getDigestSize();
         }
      }
   }

   public static long getTreeIndex(long var0, int var2) {
      return var0 >> var2;
   }

   public static int getLeafIndex(long var0, int var2) {
      return (int)(var0 & (1L << var2) - 1L);
   }

   public static byte[] serialize(Object var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      ObjectOutputStream var2 = new ObjectOutputStream(var1);
      var2.writeObject(var0);
      var2.flush();
      return var1.toByteArray();
   }

   public static Object deserialize(byte[] var0) throws IOException, ClassNotFoundException {
      ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
      ObjectInputStream var2 = new ObjectInputStream(var1);
      return var2.readObject();
   }

   public static int calculateTau(int var0, int var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < var1; ++var3) {
         if ((var0 >> var3 & 1) == 0) {
            var2 = var3;
            break;
         }
      }

      return var2;
   }

   public static boolean isNewBDSInitNeeded(long var0, int var2, int var3) {
      if (var0 == 0L) {
         return false;
      } else {
         return var0 % (long)Math.pow((double)(1 << var2), (double)(var3 + 1)) == 0L;
      }
   }

   public static boolean isNewAuthenticationPathNeeded(long var0, int var2, int var3) {
      if (var0 == 0L) {
         return false;
      } else {
         return (var0 + 1L) % (long)Math.pow((double)(1 << var2), (double)var3) == 0L;
      }
   }
}
