package org.glassfish.grizzly.compression.lzma.impl;

public class CRC {
   static final int[] TABLE = new int[256];
   int _value = -1;

   public void init() {
      this._value = -1;
   }

   public void update(byte[] data, int offset, int size) {
      for(int i = 0; i < size; ++i) {
         this._value = TABLE[(this._value ^ data[offset + i]) & 255] ^ this._value >>> 8;
      }

   }

   public void update(byte[] data) {
      int size = data.length;

      for(int i = 0; i < size; ++i) {
         this._value = TABLE[(this._value ^ data[i]) & 255] ^ this._value >>> 8;
      }

   }

   public void updateByte(int b) {
      this._value = TABLE[(this._value ^ b) & 255] ^ this._value >>> 8;
   }

   public int getDigest() {
      return ~this._value;
   }

   static {
      for(int i = 0; i < 256; ++i) {
         int r = i;

         for(int j = 0; j < 8; ++j) {
            if ((r & 1) != 0) {
               r = r >>> 1 ^ -306674912;
            } else {
               r >>>= 1;
            }
         }

         TABLE[i] = r;
      }

   }
}
