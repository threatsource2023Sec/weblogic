package com.oracle.tyrus.fallback;

class Base16WriteUtil {
   private static final byte[] HEX = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

   static byte[] convert(byte[] data, int offset, int length) {
      byte[] base16 = new byte[2 * length];

      for(int i = 0; i < length; ++i) {
         base16[2 * i] = HEX[data[offset + i] >>> 4 & 15];
         base16[2 * i + 1] = HEX[data[offset + i] & 15];
      }

      return base16;
   }
}
