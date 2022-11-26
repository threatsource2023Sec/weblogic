package org.apache.openjpa.lib.util;

public final class Bytes {
   private Bytes() {
   }

   public static byte[] append(byte[] a, byte[] b) {
      byte[] z = new byte[a.length + b.length];
      System.arraycopy(a, 0, z, 0, a.length);
      System.arraycopy(b, 0, z, a.length, b.length);
      return z;
   }

   public static byte[] toBytes(long n) {
      return toBytes(n, new byte[8]);
   }

   public static byte[] toBytes(long n, byte[] b) {
      b[7] = (byte)((int)n);
      n >>>= 8;
      b[6] = (byte)((int)n);
      n >>>= 8;
      b[5] = (byte)((int)n);
      n >>>= 8;
      b[4] = (byte)((int)n);
      n >>>= 8;
      b[3] = (byte)((int)n);
      n >>>= 8;
      b[2] = (byte)((int)n);
      n >>>= 8;
      b[1] = (byte)((int)n);
      n >>>= 8;
      b[0] = (byte)((int)n);
      return b;
   }

   public static long toLong(byte[] b) {
      return ((long)b[7] & 255L) + (((long)b[6] & 255L) << 8) + (((long)b[5] & 255L) << 16) + (((long)b[4] & 255L) << 24) + (((long)b[3] & 255L) << 32) + (((long)b[2] & 255L) << 40) + (((long)b[1] & 255L) << 48) + (((long)b[0] & 255L) << 56);
   }

   public static boolean areEqual(byte[] a, byte[] b) {
      int aLength = a.length;
      if (aLength != b.length) {
         return false;
      } else {
         for(int i = 0; i < aLength; ++i) {
            if (a[i] != b[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public static int compareTo(byte[] lhs, byte[] rhs) {
      if (lhs == rhs) {
         return 0;
      } else if (lhs == null) {
         return -1;
      } else if (rhs == null) {
         return 1;
      } else if (lhs.length != rhs.length) {
         return lhs.length < rhs.length ? -1 : 1;
      } else {
         for(int i = 0; i < lhs.length; ++i) {
            if (lhs[i] < rhs[i]) {
               return -1;
            }

            if (lhs[i] > rhs[i]) {
               return 1;
            }
         }

         return 0;
      }
   }

   public static short toShort(byte[] b) {
      return (short)((b[1] & 255) + ((b[0] & 255) << 8));
   }
}
