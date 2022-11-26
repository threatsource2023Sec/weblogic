package org.glassfish.grizzly.memory;

class Bits {
   private Bits() {
   }

   static char makeChar(byte b1, byte b0) {
      return (char)(b1 << 8 | b0 & 255);
   }

   static char getCharL(byte[] bb, int bi) {
      return makeChar(bb[bi + 1], bb[bi]);
   }

   static char getCharB(byte[] bb, int bi) {
      return makeChar(bb[bi], bb[bi + 1]);
   }

   static char getChar(byte[] bb, int bi, boolean bigEndian) {
      return bigEndian ? getCharB(bb, bi) : getCharL(bb, bi);
   }

   static byte char1(char x) {
      return (byte)(x >> 8);
   }

   static byte char0(char x) {
      return (byte)x;
   }

   static void putCharL(byte[] bb, int bi, char x) {
      bb[bi] = char0(x);
      bb[bi + 1] = char1(x);
   }

   static void putCharB(byte[] bb, int bi, char x) {
      bb[bi] = char1(x);
      bb[bi + 1] = char0(x);
   }

   static void putChar(byte[] bb, int bi, char x, boolean bigEndian) {
      if (bigEndian) {
         putCharB(bb, bi, x);
      } else {
         putCharL(bb, bi, x);
      }

   }

   static short makeShort(byte b1, byte b0) {
      return (short)(b1 << 8 | b0 & 255);
   }

   static short getShortL(byte[] bb, int bi) {
      return makeShort(bb[bi + 1], bb[bi]);
   }

   static short getShortB(byte[] bb, int bi) {
      return makeShort(bb[bi], bb[bi + 1]);
   }

   static short getShort(byte[] bb, int bi, boolean bigEndian) {
      return bigEndian ? getShortB(bb, bi) : getShortL(bb, bi);
   }

   static byte short1(short x) {
      return (byte)(x >> 8);
   }

   static byte short0(short x) {
      return (byte)x;
   }

   static void putShortL(byte[] bb, int bi, short x) {
      bb[bi] = short0(x);
      bb[bi + 1] = short1(x);
   }

   static void putShortB(byte[] bb, int bi, short x) {
      bb[bi] = short1(x);
      bb[bi + 1] = short0(x);
   }

   static void putShort(byte[] bb, int bi, short x, boolean bigEndian) {
      if (bigEndian) {
         putShortB(bb, bi, x);
      } else {
         putShortL(bb, bi, x);
      }

   }

   static int makeInt(byte b3, byte b2, byte b1, byte b0) {
      return (b3 & 255) << 24 | (b2 & 255) << 16 | (b1 & 255) << 8 | b0 & 255;
   }

   static int getIntL(byte[] bb, int bi) {
      return makeInt(bb[bi + 3], bb[bi + 2], bb[bi + 1], bb[bi]);
   }

   static int getIntB(byte[] bb, int bi) {
      return makeInt(bb[bi], bb[bi + 1], bb[bi + 2], bb[bi + 3]);
   }

   static int getInt(byte[] bb, int bi, boolean bigEndian) {
      return bigEndian ? getIntB(bb, bi) : getIntL(bb, bi);
   }

   static byte int3(int x) {
      return (byte)(x >> 24);
   }

   static byte int2(int x) {
      return (byte)(x >> 16);
   }

   static byte int1(int x) {
      return (byte)(x >> 8);
   }

   static byte int0(int x) {
      return (byte)x;
   }

   static void putIntL(byte[] bb, int bi, int x) {
      bb[bi + 3] = int3(x);
      bb[bi + 2] = int2(x);
      bb[bi + 1] = int1(x);
      bb[bi] = int0(x);
   }

   static void putIntB(byte[] bb, int bi, int x) {
      bb[bi] = int3(x);
      bb[bi + 1] = int2(x);
      bb[bi + 2] = int1(x);
      bb[bi + 3] = int0(x);
   }

   static void putInt(byte[] bb, int bi, int x, boolean bigEndian) {
      if (bigEndian) {
         putIntB(bb, bi, x);
      } else {
         putIntL(bb, bi, x);
      }

   }

   static long makeLong(byte b7, byte b6, byte b5, byte b4, byte b3, byte b2, byte b1, byte b0) {
      return ((long)b7 & 255L) << 56 | ((long)b6 & 255L) << 48 | ((long)b5 & 255L) << 40 | ((long)b4 & 255L) << 32 | ((long)b3 & 255L) << 24 | ((long)b2 & 255L) << 16 | ((long)b1 & 255L) << 8 | (long)b0 & 255L;
   }

   static long getLongL(byte[] bb, int bi) {
      return makeLong(bb[bi + 7], bb[bi + 6], bb[bi + 5], bb[bi + 4], bb[bi + 3], bb[bi + 2], bb[bi + 1], bb[bi]);
   }

   static long getLongB(byte[] bb, int bi) {
      return makeLong(bb[bi], bb[bi + 1], bb[bi + 2], bb[bi + 3], bb[bi + 4], bb[bi + 5], bb[bi + 6], bb[bi + 7]);
   }

   static long getLong(byte[] bb, int bi, boolean bigEndian) {
      return bigEndian ? getLongB(bb, bi) : getLongL(bb, bi);
   }

   static byte long7(long x) {
      return (byte)((int)(x >> 56));
   }

   static byte long6(long x) {
      return (byte)((int)(x >> 48));
   }

   static byte long5(long x) {
      return (byte)((int)(x >> 40));
   }

   static byte long4(long x) {
      return (byte)((int)(x >> 32));
   }

   static byte long3(long x) {
      return (byte)((int)(x >> 24));
   }

   static byte long2(long x) {
      return (byte)((int)(x >> 16));
   }

   static byte long1(long x) {
      return (byte)((int)(x >> 8));
   }

   static byte long0(long x) {
      return (byte)((int)x);
   }

   static void putLongL(byte[] bb, int bi, long x) {
      bb[bi + 7] = long7(x);
      bb[bi + 6] = long6(x);
      bb[bi + 5] = long5(x);
      bb[bi + 4] = long4(x);
      bb[bi + 3] = long3(x);
      bb[bi + 2] = long2(x);
      bb[bi + 1] = long1(x);
      bb[bi] = long0(x);
   }

   static void putLongB(byte[] bb, int bi, long x) {
      bb[bi] = long7(x);
      bb[bi + 1] = long6(x);
      bb[bi + 2] = long5(x);
      bb[bi + 3] = long4(x);
      bb[bi + 4] = long3(x);
      bb[bi + 5] = long2(x);
      bb[bi + 6] = long1(x);
      bb[bi + 7] = long0(x);
   }

   static void putLong(byte[] bb, int bi, long x, boolean bigEndian) {
      if (bigEndian) {
         putLongB(bb, bi, x);
      } else {
         putLongL(bb, bi, x);
      }

   }

   static float getFloatL(byte[] bb, int bi) {
      return Float.intBitsToFloat(getIntL(bb, bi));
   }

   static float getFloatB(byte[] bb, int bi) {
      return Float.intBitsToFloat(getIntB(bb, bi));
   }

   static float getFloat(byte[] bb, int bi, boolean bigEndian) {
      return bigEndian ? getFloatB(bb, bi) : getFloatL(bb, bi);
   }

   static void putFloatL(byte[] bb, int bi, float x) {
      putIntL(bb, bi, Float.floatToRawIntBits(x));
   }

   static void putFloatB(byte[] bb, int bi, float x) {
      putIntB(bb, bi, Float.floatToRawIntBits(x));
   }

   static void putFloat(byte[] bb, int bi, float x, boolean bigEndian) {
      if (bigEndian) {
         putFloatB(bb, bi, x);
      } else {
         putFloatL(bb, bi, x);
      }

   }

   static double getDoubleL(byte[] bb, int bi) {
      return Double.longBitsToDouble(getLongL(bb, bi));
   }

   static double getDoubleB(byte[] bb, int bi) {
      return Double.longBitsToDouble(getLongB(bb, bi));
   }

   static double getDouble(byte[] bb, int bi, boolean bigEndian) {
      return bigEndian ? getDoubleB(bb, bi) : getDoubleL(bb, bi);
   }

   static void putDoubleL(byte[] bb, int bi, double x) {
      putLongL(bb, bi, Double.doubleToRawLongBits(x));
   }

   static void putDoubleB(byte[] bb, int bi, double x) {
      putLongB(bb, bi, Double.doubleToRawLongBits(x));
   }

   static void putDouble(byte[] bb, int bi, double x, boolean bigEndian) {
      if (bigEndian) {
         putDoubleB(bb, bi, x);
      } else {
         putDoubleL(bb, bi, x);
      }

   }
}
