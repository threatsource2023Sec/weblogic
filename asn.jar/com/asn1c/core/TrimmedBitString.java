package com.asn1c.core;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class TrimmedBitString extends BitString {
   protected void checkBitIndex(int var1, int var2) {
      if (var1 < 0) {
         throw new IndexOutOfBoundsException(Integer.toString(var1));
      } else {
         this.ensureBitCapacity(var2);
      }
   }

   protected void checkBitRange(int var1, int var2, int var3) {
      if (var2 < 0) {
         throw new NegativeArraySizeException(Integer.toString(var2));
      } else if (var1 < 0) {
         throw new IndexOutOfBoundsException(Integer.toString(var1));
      } else {
         this.ensureBitCapacity(var1 + var2);
      }
   }

   public TrimmedBitString() {
   }

   public TrimmedBitString(int var1) {
      this.ensureBitCapacity(var1);
   }

   public TrimmedBitString(UnitString var1) {
      super(var1);
   }

   public TrimmedBitString(byte[] var1) {
      super(var1);
   }

   public TrimmedBitString(byte[] var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public void setBit(int var1) {
      UnitString.checkSize(var1);
      this.ensureBitLength(var1 + 1);
      super.setBit(var1);
   }

   public void clearBit(int var1) {
      UnitString.checkSize(var1);
      if (super.bitsize > var1) {
         super.clearBit(var1);
      }

   }

   public void setBit(int var1, boolean var2) {
      UnitString.checkSize(var1);
      if (var2) {
         this.ensureBitLength(var1 + 1);
         super.setBit(var1);
      } else if (super.bitsize > var1) {
         super.clearBit(var1);
      }

   }

   public boolean getBit(int var1) {
      UnitString.checkSize(var1);
      boolean var2;
      if (super.bitsize > var1) {
         var2 = super.getBit(var1);
      } else {
         var2 = false;
      }

      return var2;
   }

   public int bitLength() {
      this.trimLength();
      return super.bitsize;
   }

   public int octetLength() {
      this.trimLength();
      return (super.bitsize + 7) / 8;
   }

   public int length() {
      this.trimLength();
      return super.bitsize;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      this.trimLength();
      this.shrinkCapacity();
      var1.defaultWriteObject();
   }
}
