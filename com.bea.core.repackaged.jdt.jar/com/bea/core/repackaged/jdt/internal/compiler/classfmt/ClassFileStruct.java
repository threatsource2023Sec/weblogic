package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

public abstract class ClassFileStruct {
   byte[] reference;
   int[] constantPoolOffsets;
   int structOffset;

   public ClassFileStruct(byte[] classFileBytes, int[] offsets, int offset) {
      this.reference = classFileBytes;
      this.constantPoolOffsets = offsets;
      this.structOffset = offset;
   }

   public double doubleAt(int relativeOffset) {
      return Double.longBitsToDouble(this.i8At(relativeOffset));
   }

   public float floatAt(int relativeOffset) {
      return Float.intBitsToFloat(this.i4At(relativeOffset));
   }

   public int i4At(int relativeOffset) {
      int position = relativeOffset + this.structOffset;
      return (this.reference[position++] & 255) << 24 | (this.reference[position++] & 255) << 16 | ((this.reference[position++] & 255) << 8) + (this.reference[position] & 255);
   }

   public long i8At(int relativeOffset) {
      int position = relativeOffset + this.structOffset;
      return (long)(this.reference[position++] & 255) << 56 | (long)(this.reference[position++] & 255) << 48 | (long)(this.reference[position++] & 255) << 40 | (long)(this.reference[position++] & 255) << 32 | (long)(this.reference[position++] & 255) << 24 | (long)(this.reference[position++] & 255) << 16 | (long)(this.reference[position++] & 255) << 8 | (long)(this.reference[position++] & 255);
   }

   protected void reset() {
      this.reference = null;
      this.constantPoolOffsets = null;
   }

   public int u1At(int relativeOffset) {
      return this.reference[relativeOffset + this.structOffset] & 255;
   }

   public int u2At(int relativeOffset) {
      int position = relativeOffset + this.structOffset;
      return (this.reference[position++] & 255) << 8 | this.reference[position] & 255;
   }

   public long u4At(int relativeOffset) {
      int position = relativeOffset + this.structOffset;
      return ((long)this.reference[position++] & 255L) << 24 | (long)((this.reference[position++] & 255) << 16) | (long)((this.reference[position++] & 255) << 8) | (long)(this.reference[position] & 255);
   }

   public char[] utf8At(int relativeOffset, int bytesAvailable) {
      int length = bytesAvailable;
      char[] outputBuf = new char[bytesAvailable];
      int outputPos = 0;

      int x;
      for(int readOffset = this.structOffset + relativeOffset; length != 0; outputBuf[outputPos++] = (char)x) {
         x = this.reference[readOffset++] & 255;
         --length;
         if ((128 & x) != 0) {
            if ((x & 32) != 0) {
               length -= 2;
               x = (x & 15) << 12 | (this.reference[readOffset++] & 63) << 6 | this.reference[readOffset++] & 63;
            } else {
               --length;
               x = (x & 31) << 6 | this.reference[readOffset++] & 63;
            }
         }
      }

      if (outputPos != bytesAvailable) {
         System.arraycopy(outputBuf, 0, outputBuf = new char[outputPos], 0, outputPos);
      }

      return outputBuf;
   }
}
