package org.glassfish.tyrus.core;

import java.nio.ByteBuffer;

class Masker {
   private volatile ByteBuffer buffer;
   private volatile byte[] mask;
   private volatile int index = 0;

   public Masker(ByteBuffer buffer) {
      this.buffer = buffer;
   }

   public Masker(int mask) {
      this.mask = new byte[4];
      this.mask[0] = (byte)(mask >> 24);
      this.mask[1] = (byte)(mask >> 16);
      this.mask[2] = (byte)(mask >> 8);
      this.mask[3] = (byte)mask;
   }

   byte get() {
      return this.buffer.get();
   }

   byte[] get(int size) {
      byte[] bytes = new byte[size];
      this.buffer.get(bytes);
      return bytes;
   }

   public byte[] unmask(int count) {
      byte[] bytes = this.get(count);
      if (this.mask != null) {
         for(int i = 0; i < bytes.length; ++i) {
            bytes[i] ^= this.mask[this.index++ % 4];
         }
      }

      return bytes;
   }

   public void mask(byte[] target, int location, byte[] bytes, int length) {
      if (bytes != null && target != null) {
         for(int i = 0; i < length; ++i) {
            target[location + i] = this.mask == null ? bytes[i] : (byte)(bytes[i] ^ this.mask[this.index++ % 4]);
         }
      }

   }

   public void setBuffer(ByteBuffer buffer) {
      this.buffer = buffer;
   }

   public byte[] getMask() {
      return this.mask;
   }

   public void readMask() {
      this.mask = this.get(4);
   }
}
