package com.asn1c.core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigInteger;

public class UnitString implements ASN1Object, Cloneable, Serializable, Comparable {
   protected static final int SHIFT = 3;
   protected static final int BITS = 8;
   protected static final int MASK = 7;
   protected static final byte[] leftmask = new byte[]{0, -128, -64, -32, -16, -8, -4, -2, -1};
   protected static final byte[] rightmask = new byte[]{0, 1, 3, 7, 15, 31, 63, 127, -1};
   protected static final byte[] bitmask = new byte[]{-128, 64, 32, 16, 8, 4, 2, 1};
   protected int bitsize;
   protected byte[] bits;
   protected int unitsize;
   static final long serialVersionUID = -6981081567680739289L;

   protected UnitString(int var1) {
      checkUnitSize(var1);
      this.unitsize = var1;
      this.bitsize = 0;
      this.bits = new byte[16];
   }

   protected UnitString(int var1, UnitString var2) {
      checkUnitSize(var1);
      this.unitsize = var1;
      this.checkBitLength(var2.bitsize);
      this.bitsize = var2.bitsize;
      this.bits = new byte[sublength(this.bitsize)];
      System.arraycopy(var2.bits, 0, this.bits, 0, this.bits.length);
   }

   protected UnitString(int var1, int var2) {
      checkUnitSize(var1);
      this.unitsize = var1;
      checkSize(var2);
      if ((long)var2 * (long)var1 > 2147483640L) {
         var2 = 2147483640 / var1;
      }

      this.bitsize = var2 * var1;
      this.bits = new byte[sublength(var2 * var1)];
   }

   protected UnitString(int var1, byte[] var2) {
      checkUnitSize(var1);
      this.unitsize = var1;
      this.bitsize = var2.length * 8;
      this.bits = (byte[])var2.clone();
   }

   protected UnitString(int var1, byte[] var2, int var3, int var4) {
      checkUnitSize(var1);
      this.unitsize = var1;
      checkSize(var4);
      if ((long)var4 * (long)var1 > 2147483640L) {
         var4 = 2147483640 / var1;
      }

      this.bitsize = var4 * var1;
      this.bits = new byte[sublength(var4 * var1)];
      bitcpy(this.bits, 0, var2, var3 * var1, var4 * var1);
   }

   public void setValue(UnitString var1) {
      this.checkBitLength(var1.bitsize);
      this.bitsize = var1.bitsize;
      this.bits = new byte[sublength(this.bitsize)];
      System.arraycopy(var1.bits, 0, this.bits, 0, this.bits.length);
   }

   public void setValue(byte[] var1) {
      this.bitsize = var1.length * 8;
      this.bits = (byte[])var1.clone();
   }

   public void setValue(byte[] var1, int var2, int var3) {
      checkSize(var3);
      if ((long)var3 * (long)this.unitsize > 2147483640L) {
         var3 = 2147483640 / this.unitsize;
      }

      this.bitsize = var3 * this.unitsize;
      this.bits = new byte[sublength(var3 * this.unitsize)];
      bitcpy(this.bits, 0, var1, var2 * this.unitsize, var3 * this.unitsize);
   }

   public void ensureBitCapacity(int var1) {
      checkSize(var1);
      int var2 = sublength(var1);
      if (var2 > this.bits.length) {
         int var3 = Math.max(2 * this.bits.length, var2);
         byte[] var4 = new byte[var3];
         System.arraycopy(this.bits, 0, var4, 0, sublength(this.bitsize));
         this.bits = var4;
      }

   }

   public void ensureOctetCapacity(int var1) {
      this.ensureBitCapacity(var1 * 8);
   }

   public void ensureCapacity(int var1) {
      this.ensureBitCapacity(var1 * this.unitsize);
   }

   public void ensureBitLength(int var1) {
      this.checkBitLength(var1);
      int var2 = sublength(var1);
      this.ensureBitCapacity(var1);
      if (var1 > this.bitsize) {
         this.bitsize = var1;
      }

   }

   public void ensureOctetLength(int var1) {
      this.ensureBitLength(var1 * 8);
   }

   public void ensureLength(int var1) {
      this.ensureBitLength(var1 * this.unitsize);
   }

   public int unit() {
      return this.unitsize;
   }

   public int bitLength() {
      return this.bitsize;
   }

   public int octetLength() {
      return (this.bitsize + 7) / 8;
   }

   public int length() {
      return this.bitsize / this.unitsize;
   }

   public int trimmedBitLength() {
      int var1;
      for(var1 = subscript(this.bitsize - 1); var1 >= 0 && this.bits[var1] == 0; --var1) {
      }

      if (var1 < 0) {
         return 0;
      } else {
         byte var2 = this.bits[var1];

         int var3;
         for(var3 = 7; var3 >= 0 && (var2 & bitmask[var3]) == 0; --var3) {
         }

         return var1 * 8 + var3 + 1;
      }
   }

   public int bitCapacity() {
      return this.bits.length << 3;
   }

   public int octetCapacity() {
      return this.bits.length;
   }

   public int capacity() {
      return (this.bits.length << 3) / this.unitsize;
   }

   public void setBitLength(int var1) {
      this.checkBitLength(var1);
      if (var1 < this.bitsize) {
         bitclr(this.bits, var1, this.bitsize - var1);
         this.bitsize = var1;
      } else {
         this.ensureBitLength(var1);
      }

   }

   public void setOctetLength(int var1) {
      this.setBitLength(var1 * 8);
   }

   public void setLength(int var1) {
      this.setBitLength(var1 * this.unitsize);
   }

   public void shrinkCapacity() {
      int var1 = sublength(this.bitsize);
      if (this.bits.length > var1) {
         byte[] var2 = new byte[var1];
         System.arraycopy(this.bits, 0, var2, 0, var1);
         this.bits = var2;
      }

   }

   public void trimLength() {
      int var1 = this.trimmedBitLength();
      this.bitsize = (var1 + this.unitsize - 1) / this.unitsize * this.unitsize;
   }

   public int get(int var1) {
      this.checkBitIndex(var1 * this.unitsize);
      int var2;
      switch (this.unitsize) {
         case 1:
            var2 = (this.bits[subscript(var1)] & bitmask[var1 & 7]) != 0 ? 1 : 0;
            break;
         case 8:
            var2 = this.bits[var1] & 255;
            break;
         default:
            throw new InternalError();
      }

      return var2;
   }

   public void set(int var1, int var2) {
      this.checkBitIndex(var1 * this.unitsize);
      switch (this.unitsize) {
         case 1:
            byte[] var10000;
            int var10001;
            if (var2 != 0) {
               var10000 = this.bits;
               var10001 = subscript(var1);
               var10000[var10001] |= bitmask[var1 & 7];
            } else {
               var10000 = this.bits;
               var10001 = subscript(var1);
               var10000[var10001] = (byte)(var10000[var10001] & ~bitmask[var1 & 7]);
            }
            break;
         case 8:
            this.bits[var1] = (byte)var2;
            break;
         default:
            throw new InternalError();
      }

   }

   public UnitString get(int var1, int var2) {
      this.checkBitRange(var1 * this.unitsize, var2 * this.unitsize);
      Object var3;
      switch (this.unitsize) {
         case 1:
            var3 = new BitString(var2);
            break;
         case 8:
            var3 = new OctetString(var2);
            break;
         default:
            throw new InternalError();
      }

      bitcpy(((UnitString)var3).bits, 0, this.bits, var1 * this.unitsize, var2 * this.unitsize);
      return (UnitString)var3;
   }

   public void set(UnitString var1, int var2, int var3, int var4) {
      this.checkBitRange(var3 * this.unitsize, var4 * this.unitsize);
      var1.checkBitRange(var2 * var1.unitsize, var4 * var1.unitsize);
      bitmove(this.bits, var3 * this.unitsize, var1.bits, var2 * var1.unitsize, var4 * this.unitsize);
   }

   public void append(int var1) {
      switch (this.unitsize) {
         case 1:
            this.appendBit(var1 != 0);
            break;
         case 8:
            this.appendByte((byte)var1);
            break;
         default:
            throw new InternalError();
      }

   }

   public boolean getBit(int var1) {
      this.checkBitIndex(var1);
      boolean var2 = (this.bits[subscript(var1)] & bitmask[var1 & 7]) != 0;
      return var2;
   }

   public void setBit(int var1) {
      this.checkBitIndex(var1);
      byte[] var10000 = this.bits;
      int var10001 = subscript(var1);
      var10000[var10001] |= bitmask[var1 & 7];
   }

   public void clearBit(int var1) {
      this.checkBitIndex(var1);
      byte[] var10000 = this.bits;
      int var10001 = subscript(var1);
      var10000[var10001] = (byte)(var10000[var10001] & ~bitmask[var1 & 7]);
   }

   public void setBit(int var1, boolean var2) {
      this.checkBitIndex(var1);
      byte[] var10000;
      int var10001;
      if (var2) {
         var10000 = this.bits;
         var10001 = subscript(var1);
         var10000[var10001] |= bitmask[var1 & 7];
      } else {
         var10000 = this.bits;
         var10001 = subscript(var1);
         var10000[var10001] = (byte)(var10000[var10001] & ~bitmask[var1 & 7]);
      }

   }

   public void appendBit(boolean var1) {
      this.ensureBitCapacity(this.bitsize + 1);
      if (var1) {
         byte[] var10000 = this.bits;
         int var10001 = subscript(this.bitsize);
         var10000[var10001] |= bitmask[this.bitsize & 7];
      }

      ++this.bitsize;
   }

   public BitString getBits(int var1, int var2) {
      BitString var3 = new BitString(var2);
      this.checkBitRange(var1, var2);
      bitcpy(var3.bits, 0, this.bits, var1, var2);
      return var3;
   }

   public void setBits(UnitString var1, int var2, int var3, int var4) {
      this.checkBitRange(var3, var4);
      var1.checkBitRange(var2, var4);
      bitmove(this.bits, var3, var1.bits, var2, var4);
   }

   public byte getOctet(int var1) {
      return this.getOctetAtBitPosition(var1 * 8);
   }

   public void setOctet(int var1, byte var2) {
      this.setOctetAtBitPosition(var1 * 8, var2);
   }

   public void appendOctet(byte var1) {
      this.appendByte(var1);
   }

   public OctetString getOctets(int var1, int var2) {
      return this.getOctetsAtBitPosition(var1 * 8, var2);
   }

   public void setOctets(UnitString var1, int var2, int var3, int var4) {
      this.setOctetsAtBitPosition(var1, var2 * 8, var3 * 8, var4);
   }

   public byte getOctetAtBitPosition(int var1) {
      this.checkBitRange(var1, 8);
      byte var2 = this.extractByte(var1);
      return var2;
   }

   public void setOctetAtBitPosition(int var1, byte var2) {
      this.checkBitRange(var1, 8);
      this.putByte(var1, var2);
   }

   public OctetString getOctetsAtBitPosition(int var1, int var2) {
      checkSize(var2);
      OctetString var3 = new OctetString(var2);
      bitcpy(var3.bits, 0, this.bits, var1, var2 * 8);
      return var3;
   }

   public void setOctetsAtBitPosition(UnitString var1, int var2, int var3, int var4) {
      this.checkBitRange(var3, var4 * 8);
      var1.checkBitRange(var2 * 8, var4 * 8);
      bitcpy(this.bits, var3, var1.bits, var2 * 8, var4 * 8);
   }

   public void append(UnitString var1) {
      this.appendBits(var1, 0, var1.bitsize);
   }

   public void append(UnitString var1, int var2, int var3) {
      this.appendBits(var1, var2 * var1.unitsize, var3 * this.unitsize);
   }

   public void appendBits(UnitString var1, int var2, int var3) {
      checkSize(var3);
      var1.checkBitRange(var2, var3);
      this.checkBitLength(this.bitsize + var3);
      this.ensureBitCapacity(this.bitsize + var3);
      bitcpy(this.bits, this.bitsize, var1.bits, var2, Math.min(var3, var1.bitsize));
      this.bitsize += var3;
   }

   public void appendOctets(UnitString var1, int var2, int var3) {
      this.appendBits(var1, var2 * 8, var3 * 8);
   }

   public void appendByteArray(byte[] var1) {
      this.appendOctetsFromByteArray(var1, 0, var1.length);
   }

   public void appendByteArray(byte[] var1, int var2, int var3) {
      checkSize(var3);
      this.checkBitRange(var2 * this.unitsize, var3 * this.unitsize, var1.length * 8);
      this.checkBitLength(this.bitsize + var3 * this.unitsize);
      this.ensureBitCapacity(this.bitsize + var3 * this.unitsize);
      bitcpy(this.bits, this.bitsize, var1, var2 * this.unitsize, var3 * this.unitsize);
      this.bitsize += var3 * this.unitsize;
   }

   public void appendBitsFromByteArray(byte[] var1, int var2, int var3) {
      checkSize(var3);
      this.checkBitRange(var2, var3, var1.length * 8);
      this.checkBitLength(this.bitsize + var3);
      this.ensureBitCapacity(this.bitsize + var3);
      bitcpy(this.bits, this.bitsize, var1, var2, var3);
      this.bitsize += var3;
   }

   public void appendOctetsFromByteArray(byte[] var1, int var2, int var3) {
      checkSize(var3);
      if (System.identityHashCode(this) > System.identityHashCode(var1)) {
         ;
      }

      this.checkBitRange(var2 * 8, var3 * 8, var1.length * 8);
      this.ensureBitCapacity(this.bitsize + var3 * 8);
      bitcpy(this.bits, this.bitsize, var1, var2 * 8, var3 * 8);
      this.bitsize += var3 * 8;
   }

   public void appendZeros(int var1) {
      checkSize(var1);
      this.ensureBitLength(this.bitsize + var1 * this.unitsize);
   }

   public void appendZeroBits(int var1) {
      checkSize(var1);
      this.checkBitLength(this.bitsize + var1);
      this.ensureBitLength(this.bitsize + var1);
   }

   public void appendZeroOctets(int var1) {
      checkSize(var1);
      this.ensureBitLength(this.bitsize + var1 * 8);
   }

   public int hashCode() {
      long var1 = 1234L;
      int var3 = sublength(this.bitsize);

      while(true) {
         --var3;
         if (var3 < 0) {
            return (int)(var1 >>> 32 ^ var1);
         }

         var1 ^= (long)(this.bits[var3] * (var3 + 1));
      }
   }

   public boolean equals(Object var1) {
      return this.compareTo(var1) == 0;
   }

   public int compareTo(Object var1) {
      if (this == var1) {
         return 0;
      } else {
         UnitString var2 = (UnitString)var1;
         int var3 = Math.min(this.bitLength(), var2.bitLength());
         int var4 = sublength(var3);

         for(int var5 = 0; var5 < var4; ++var5) {
            if (this.bits[var5] != var2.bits[var5]) {
               if (this.bits[var5] < 0 && var2.bits[var5] >= 0) {
                  return 1;
               }

               if (this.bits[var5] >= 0 && var2.bits[var5] < 0) {
                  return -1;
               }

               if (this.bits[var5] > var2.bits[var5]) {
                  return 1;
               }

               return -1;
            }
         }

         return 0;
      }
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      this.shrinkCapacity();
      var1.defaultWriteObject();
   }

   public Object clone() {
      UnitString var1;
      try {
         var1 = (UnitString)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      var1.bits = new byte[this.bits.length];
      System.arraycopy(this.bits, 0, var1.bits, 0, this.bits.length);
      return var1;
   }

   public String toString() {
      StringBuffer var1;
      switch (this.unitsize) {
         case 1:
            int var2 = this.bitLength();
            var1 = new StringBuffer(3 + var2);
            var1.append("'");

            for(int var3 = 0; var3 < var2; ++var3) {
               var1.append((char)(this.getBit(var3) ? '1' : '0'));
            }

            var1.append("'B");
            break;
         case 8:
            var1 = new StringBuffer(3 + 2 * this.octetLength());
            var1.append("'");

            for(int var4 = 0; var4 < this.octetLength(); ++var4) {
               if ((this.bits[var4] & 240) >= 160) {
                  var1.append((char)(((this.bits[var4] & 240) >> 4) + 65 - 10));
               } else {
                  var1.append((char)(((this.bits[var4] & 240) >> 4) + 48));
               }

               if ((this.bits[var4] & 15) >= 10) {
                  var1.append((char)((this.bits[var4] & 15) + 65 - 10));
               } else {
                  var1.append((char)((this.bits[var4] & 15) + 48));
               }
            }

            var1.append("'H");
            break;
         default:
            throw new InternalError();
      }

      return var1.toString();
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      switch (this.unitsize) {
         case 1:
            var1.print(var2 + var3 + "'");
            int var6 = this.bitLength();

            for(int var7 = 0; var7 < var6; ++var7) {
               var1.print((char)(this.getBit(var7) ? '1' : '0'));
            }

            var1.println("'B" + var4);
            break;
         case 8:
            var1.print(var2 + var3 + "'");

            for(int var8 = 0; var8 < this.octetLength(); ++var8) {
               if ((this.bits[var8] & 240) >= 160) {
                  var1.print((char)(((this.bits[var8] & 240) >> 4) + 65 - 10));
               } else {
                  var1.print((char)(((this.bits[var8] & 240) >> 4) + 48));
               }

               if ((this.bits[var8] & 15) >= 10) {
                  var1.print((char)((this.bits[var8] & 15) + 65 - 10));
               } else {
                  var1.print((char)((this.bits[var8] & 15) + 48));
               }
            }

            var1.println("'H" + var4);
            break;
         default:
            throw new InternalError();
      }

   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   public static UnitString toUnitString(String16 var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else if (var0.startsWith("'") && var0.endsWith("'B")) {
         return toBitString(var0);
      } else if (var0.startsWith("'") && var0.endsWith("'H")) {
         return toOctetString(var0);
      } else {
         throw new NumberFormatException(var0.toString());
      }
   }

   public static BitString toBitString(String16 var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else if (var0.startsWith("'") && var0.endsWith("'B")) {
         BitString var1 = new BitString(var0.length() - 3);
         int var2 = var0.length() - 3;

         while(var2-- > 0) {
            char var3 = var0.charAt(1 + var2);
            if (var3 == '1') {
               var1.setBit(var2);
            } else if (var3 != '0') {
               throw new NumberFormatException(var0.toString());
            }
         }

         return var1;
      } else {
         throw new NumberFormatException(var0.toString());
      }
   }

   public static OctetString toOctetString(String16 var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else if (var0.startsWith("'") && var0.endsWith("'H")) {
         OctetString var1 = new OctetString((var0.length() - 3) / 2);

         byte var5;
         for(int var2 = var0.length() - 3 + 1 & -2; var2 > 0; var1.setOctet(var2, var5)) {
            var2 -= 2;
            char var3 = var0.charAt(1 + var2);
            char var4 = var0.length() - 2 >= 1 + var2 + 1 ? var0.charAt(1 + var2 + 1) : 48;
            if (var3 >= '0' && var3 <= '9') {
               var5 = (byte)(var3 - 48 << 4);
            } else if (var3 >= 'a' && var3 <= 'f') {
               var5 = (byte)(var3 - 97 + 10 << 4);
            } else {
               if (var3 < 'A' || var3 > 'F') {
                  throw new NumberFormatException(var0.toString());
               }

               var5 = (byte)(var3 - 65 + 10 << 4);
            }

            if (var4 >= '0' && var4 <= '9') {
               var5 |= (byte)(var4 - 48);
            } else if (var4 >= 'a' && var4 <= 'f') {
               var5 |= (byte)(var4 - 97 + 10);
            } else {
               if (var4 < 'A' || var4 > 'F') {
                  throw new NumberFormatException(var0.toString());
               }

               var5 |= (byte)(var4 - 65 + 10);
            }
         }

         return var1;
      } else {
         throw new NumberFormatException(var0.toString());
      }
   }

   public static UnitString toUnitString(String32 var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else if (var0.startsWith("'") && var0.endsWith("'B")) {
         return toBitString(var0);
      } else if (var0.startsWith("'") && var0.endsWith("'H")) {
         return toOctetString(var0);
      } else {
         throw new NumberFormatException(var0.toString());
      }
   }

   public static BitString toBitString(String32 var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else if (var0.startsWith("'") && var0.endsWith("'B")) {
         BitString var1 = new BitString(var0.length() - 3);
         int var2 = var0.length() - 3;

         while(var2-- > 0) {
            int var3 = var0.charAt(1 + var2);
            if (var3 == 49) {
               var1.setBit(var2);
            } else if (var3 != 48) {
               throw new NumberFormatException(var0.toString());
            }
         }

         return var1;
      } else {
         throw new NumberFormatException(var0.toString());
      }
   }

   public static OctetString toOctetString(String32 var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else if (var0.startsWith("'") && var0.endsWith("'H")) {
         OctetString var1 = new OctetString((var0.length() - 3) / 2);

         byte var5;
         for(int var2 = var0.length() - 3 + 1 & -2; var2 > 0; var1.setOctet(var2, var5)) {
            var2 -= 2;
            int var3 = var0.charAt(1 + var2);
            int var4 = var0.length() - 2 >= 1 + var2 + 1 ? var0.charAt(1 + var2 + 1) : 48;
            if (var3 >= 48 && var3 <= 57) {
               var5 = (byte)(var3 - 48 << 4);
            } else if (var3 >= 97 && var3 <= 102) {
               var5 = (byte)(var3 - 97 + 10 << 4);
            } else {
               if (var3 < 65 || var3 > 70) {
                  throw new NumberFormatException(var0.toString());
               }

               var5 = (byte)(var3 - 65 + 10 << 4);
            }

            if (var4 >= 48 && var4 <= 57) {
               var5 |= (byte)(var4 - 48);
            } else if (var4 >= 97 && var4 <= 102) {
               var5 |= (byte)(var4 - 97 + 10);
            } else {
               if (var4 < 65 || var4 > 70) {
                  throw new NumberFormatException(var0.toString());
               }

               var5 |= (byte)(var4 - 65 + 10);
            }
         }

         return var1;
      } else {
         throw new NumberFormatException(var0.toString());
      }
   }

   public static UnitString toUnitString(String var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else if (var0.startsWith("'") && var0.endsWith("'B")) {
         return toBitString(var0);
      } else if (var0.startsWith("'") && var0.endsWith("'H")) {
         return toOctetString(var0);
      } else {
         throw new NumberFormatException(var0.toString());
      }
   }

   public static BitString toBitString(String var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else {
         BitString var1;
         int var2;
         char var3;
         if (var0.startsWith("'") && var0.endsWith("'B")) {
            var1 = new BitString(var0.length() - 3);
            var2 = var0.length() - 3;

            while(var2-- > 0) {
               var3 = var0.charAt(1 + var2);
               if (var3 == '1') {
                  var1.setBit(var2);
               } else if (var3 != '0') {
                  throw new NumberFormatException(var0.toString());
               }
            }

            return var1;
         } else if (var0.startsWith("'") && var0.endsWith("'H")) {
            var1 = new BitString((var0.length() - 3) * 4);

            byte var5;
            for(var2 = var0.length() - 3 + 1 & -2; var2 > 0; var1.setOctet(var2, var5)) {
               var2 -= 2;
               var3 = var0.charAt(1 + var2);
               char var4 = var0.length() - 2 >= 1 + var2 + 1 ? var0.charAt(1 + var2 + 1) : 48;
               if (var3 >= '0' && var3 <= '9') {
                  var5 = (byte)(var3 - 48 << 4);
               } else if (var3 >= 'a' && var3 <= 'f') {
                  var5 = (byte)(var3 - 97 + 10 << 4);
               } else {
                  if (var3 < 'A' || var3 > 'F') {
                     throw new NumberFormatException(var0.toString());
                  }

                  var5 = (byte)(var3 - 65 + 10 << 4);
               }

               if (var4 >= '0' && var4 <= '9') {
                  var5 |= (byte)(var4 - 48);
               } else if (var4 >= 'a' && var4 <= 'f') {
                  var5 |= (byte)(var4 - 97 + 10);
               } else {
                  if (var4 < 'A' || var4 > 'F') {
                     throw new NumberFormatException(var0.toString());
                  }

                  var5 |= (byte)(var4 - 65 + 10);
               }
            }

            return var1;
         } else {
            throw new NumberFormatException(var0.toString());
         }
      }
   }

   public static OctetString toOctetString(String var0) {
      if (var0 == null) {
         throw new NumberFormatException();
      } else {
         OctetString var1;
         int var2;
         char var3;
         if (var0.startsWith("'") && var0.endsWith("'B'")) {
            var1 = new OctetString((var0.length() - 3 + 7) / 8);
            var2 = var0.length() - 3;

            while(var2-- > 0) {
               var3 = var0.charAt(1 + var2);
               if (var3 == '1') {
                  var1.setBit(var2);
               } else if (var3 != '0') {
                  throw new NumberFormatException(var0.toString());
               }
            }

            return var1;
         } else if (var0.startsWith("'") && var0.endsWith("'H")) {
            var1 = new OctetString((var0.length() - 3) / 2);

            byte var5;
            for(var2 = var0.length() - 3 + 1 & -2; var2 > 0; var1.setOctet(var2, var5)) {
               var2 -= 2;
               var3 = var0.charAt(1 + var2);
               char var4 = var0.length() - 2 >= 1 + var2 + 1 ? var0.charAt(1 + var2 + 1) : 48;
               if (var3 >= '0' && var3 <= '9') {
                  var5 = (byte)(var3 - 48 << 4);
               } else if (var3 >= 'a' && var3 <= 'f') {
                  var5 = (byte)(var3 - 97 + 10 << 4);
               } else {
                  if (var3 < 'A' || var3 > 'F') {
                     throw new NumberFormatException(var0.toString());
                  }

                  var5 = (byte)(var3 - 65 + 10 << 4);
               }

               if (var4 >= '0' && var4 <= '9') {
                  var5 |= (byte)(var4 - 48);
               } else if (var4 >= 'a' && var4 <= 'f') {
                  var5 |= (byte)(var4 - 97 + 10);
               } else {
                  if (var4 < 'A' || var4 > 'F') {
                     throw new NumberFormatException(var0.toString());
                  }

                  var5 |= (byte)(var4 - 65 + 10);
               }
            }

            return var1;
         } else {
            throw new NumberFormatException(var0.toString());
         }
      }
   }

   public byte[] toByteArray() {
      byte[] var1 = new byte[(this.bitsize + 7) / 8];
      System.arraycopy(this.bits, 0, var1, 0, (this.bitsize + 7) / 8);
      return var1;
   }

   private byte extractByte(int var1) {
      int var2 = subscript(var1);
      int var3 = var1 & 7;
      if (var3 == 0) {
         return this.bits[var2];
      } else {
         int var4 = 8 - var3;
         return (byte)(this.bits[var2] << 8 - var4 | this.bits[var2 + 1] >>> 0 + var4);
      }
   }

   public void appendByte(byte var1) {
      int var2 = subscript(this.bitsize);
      int var3 = this.bitsize & 7;
      this.ensureBitCapacity(this.bitsize + 8);
      if (var3 == 0) {
         this.bits[var2] = var1;
      } else {
         int var4 = 8 - var3;
         byte[] var10000 = this.bits;
         var10000[var2] = (byte)(var10000[var2] | var1 >>> var3);
         this.bits[var2 + 1] = (byte)(var1 << var4);
      }

      this.bitsize += 8;
   }

   private void putByte(int var1, byte var2) {
      int var3 = subscript(var1);
      int var4 = var1 & 7;
      if (var4 == 0) {
         this.bits[var3] = var2;
      } else {
         int var5 = 8 - var4;
         this.bits[var3] = (byte)(this.bits[var3] & leftmask[var4] | var2 >>> var4);
         this.bits[var3 + 1] = (byte)(this.bits[var3] & rightmask[var5] | var2 << var5);
      }

   }

   private byte extractSByte(int var1, int var2) {
      int var3 = subscript(var1);
      int var4 = var1 & 7;
      int var5 = 8 - var4;
      return var5 >= var2 ? (byte)(this.bits[var3] << var4 >> 8 - var2) : (byte)(this.bits[var3] << var4 >> 8 - var2 | this.bits[var3 + 1] >>> 8 - var2 + var5);
   }

   private byte extractUByte(int var1, int var2) {
      int var3 = subscript(var1);
      int var4 = var1 & 7;
      int var5 = 8 - var4;
      return var5 >= var2 ? (byte)(this.bits[var3] << var4 >>> 8 - var2) : (byte)(this.bits[var3] << var4 >>> 8 - var2 | this.bits[var3 + 1] >>> 8 - var2 + var5);
   }

   public void appendSInteger(int var1, int var2) {
      this.ensureBitCapacity(this.bitsize + var2);
      if (var2 > 32) {
         bitfill(this.bits, this.bitsize, var2 - 32, var1 < 0);
         this.bitsize += var2 - 32;
         var2 = 32;
      }

      int var3 = subscript(this.bitsize);
      int var4 = this.bitsize & 7;
      int var5 = 8 - var4;
      byte[] var10000;
      if (var5 >= var2) {
         var10000 = this.bits;
         var10000[var3] |= (byte)((var1 & rightmask[var2]) << var5 - var2);
      } else if (var5 + 8 >= var2) {
         var10000 = this.bits;
         var10000[var3] |= (byte)(var1 >>> var2 - var5 & rightmask[var5]);
         this.bits[var3 + 1] = (byte)(var1 << 8 + var5 - var2);
      } else if (var5 + 16 >= var2) {
         var10000 = this.bits;
         var10000[var3] |= (byte)(var1 >>> var2 - var5 & rightmask[var5]);
         this.bits[var3 + 1] = (byte)(var1 >>> var2 - var5 - 8);
         this.bits[var3 + 2] = (byte)(var1 << 16 + var5 - var2);
      } else if (var5 + 24 >= var2) {
         var10000 = this.bits;
         var10000[var3] |= (byte)(var1 >>> var2 - var5 & rightmask[var5]);
         this.bits[var3 + 1] = (byte)(var1 >>> var2 - var5 - 8);
         this.bits[var3 + 2] = (byte)(var1 >>> var2 - var5 - 16);
         this.bits[var3 + 3] = (byte)(var1 << 24 + var5 - var2);
      } else {
         var10000 = this.bits;
         var10000[var3] |= (byte)(var1 >>> var2 - var5 & rightmask[var5]);
         this.bits[var3 + 1] = (byte)(var1 >>> var2 - var5 - 8);
         this.bits[var3 + 2] = (byte)(var1 >>> var2 - var5 - 16);
         this.bits[var3 + 3] = (byte)(var1 >>> var2 - var5 - 24);
         this.bits[var3 + 4] = (byte)(var1 << 32 + var5 - var2);
      }

      this.bitsize += var2;
   }

   public void appendUInteger(int var1, int var2) {
      this.ensureBitCapacity(this.bitsize + var2);
      if (var2 > 32) {
         this.bitsize += var2 - 32;
         var2 = 32;
      }

      int var3 = subscript(this.bitsize);
      int var4 = this.bitsize & 7;
      int var5 = 8 - var4;
      byte[] var10000;
      if (var5 >= var2) {
         var10000 = this.bits;
         var10000[var3] |= (byte)((var1 & rightmask[var2]) << var5 - var2);
      } else if (var5 + 8 >= var2) {
         var10000 = this.bits;
         var10000[var3] |= (byte)(var1 >>> var2 - var5 & rightmask[var5]);
         this.bits[var3 + 1] = (byte)(var1 << 8 + var5 - var2);
      } else if (var5 + 16 >= var2) {
         var10000 = this.bits;
         var10000[var3] |= (byte)(var1 >>> var2 - var5 & rightmask[var5]);
         this.bits[var3 + 1] = (byte)(var1 >>> var2 - var5 - 8);
         this.bits[var3 + 2] = (byte)(var1 << 16 + var5 - var2);
      } else if (var5 + 24 >= var2) {
         var10000 = this.bits;
         var10000[var3] |= (byte)(var1 >>> var2 - var5 & rightmask[var5]);
         this.bits[var3 + 1] = (byte)(var1 >>> var2 - var5 - 8);
         this.bits[var3 + 2] = (byte)(var1 >>> var2 - var5 - 16);
         this.bits[var3 + 3] = (byte)(var1 << 24 + var5 - var2);
      } else {
         var10000 = this.bits;
         var10000[var3] |= (byte)(var1 >>> var2 - var5 & rightmask[var5]);
         this.bits[var3 + 1] = (byte)(var1 >>> var2 - var5 - 8);
         this.bits[var3 + 2] = (byte)(var1 >>> var2 - var5 - 16);
         this.bits[var3 + 3] = (byte)(var1 >>> var2 - var5 - 24);
         this.bits[var3 + 4] = (byte)(var1 << 32 + var5 - var2);
      }

      this.bitsize += var2;
   }

   public void appendSLong(long var1, int var3) {
      this.ensureBitCapacity(this.bitsize + var3);
      if (var3 > 64) {
         bitfill(this.bits, this.bitsize, var3 - 64, var1 < 0L);
         this.bitsize += var3 - 64;
         var3 = 64;
      }

      int var4 = subscript(this.bitsize);
      int var5 = this.bitsize & 7;
      int var6 = 8 - var5;
      byte[] var10000;
      if (var6 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)((var1 & (long)rightmask[var3]) << var6 - var3));
      } else if (var6 + 8 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 << 8 + var6 - var3));
      } else if (var6 + 16 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 << 16 + var6 - var3));
      } else if (var6 + 24 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 << 24 + var6 - var3));
      } else if (var6 + 32 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 << 32 + var6 - var3));
      } else if (var6 + 40 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 >>> var3 - var6 - 32));
         this.bits[var4 + 5] = (byte)((int)(var1 << 40 + var6 - var3));
      } else if (var6 + 48 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 >>> var3 - var6 - 32));
         this.bits[var4 + 5] = (byte)((int)(var1 >>> var3 - var6 - 40));
         this.bits[var4 + 6] = (byte)((int)(var1 << 48 + var6 - var3));
      } else if (var6 + 56 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 >>> var3 - var6 - 32));
         this.bits[var4 + 5] = (byte)((int)(var1 >>> var3 - var6 - 40));
         this.bits[var4 + 6] = (byte)((int)(var1 >>> var3 - var6 - 48));
         this.bits[var4 + 7] = (byte)((int)(var1 << 56 + var6 - var3));
      } else {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 >>> var3 - var6 - 32));
         this.bits[var4 + 5] = (byte)((int)(var1 >>> var3 - var6 - 40));
         this.bits[var4 + 6] = (byte)((int)(var1 >>> var3 - var6 - 48));
         this.bits[var4 + 7] = (byte)((int)(var1 >>> var3 - var6 - 56));
         this.bits[var4 + 8] = (byte)((int)(var1 << 64 + var6 - var3));
      }

      this.bitsize += var3;
   }

   public void appendULong(long var1, int var3) {
      this.ensureBitCapacity(this.bitsize + var3);
      if (var3 > 64) {
         this.bitsize += var3 - 64;
         var3 = 64;
      }

      int var4 = subscript(this.bitsize);
      int var5 = this.bitsize & 7;
      int var6 = 8 - var5;
      byte[] var10000;
      if (var6 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)((var1 & (long)rightmask[var3]) << var6 - var3));
      } else if (var6 + 8 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 << 8 + var6 - var3));
      } else if (var6 + 16 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 << 16 + var6 - var3));
      } else if (var6 + 24 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 << 24 + var6 - var3));
      } else if (var6 + 32 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 << 32 + var6 - var3));
      } else if (var6 + 40 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 >>> var3 - var6 - 32));
         this.bits[var4 + 5] = (byte)((int)(var1 << 40 + var6 - var3));
      } else if (var6 + 48 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 >>> var3 - var6 - 32));
         this.bits[var4 + 5] = (byte)((int)(var1 >>> var3 - var6 - 40));
         this.bits[var4 + 6] = (byte)((int)(var1 << 48 + var6 - var3));
      } else if (var6 + 56 >= var3) {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 >>> var3 - var6 - 32));
         this.bits[var4 + 5] = (byte)((int)(var1 >>> var3 - var6 - 40));
         this.bits[var4 + 6] = (byte)((int)(var1 >>> var3 - var6 - 48));
         this.bits[var4 + 7] = (byte)((int)(var1 << 56 + var6 - var3));
      } else {
         var10000 = this.bits;
         var10000[var4] |= (byte)((int)(var1 >>> var3 - var6 & (long)rightmask[var6]));
         this.bits[var4 + 1] = (byte)((int)(var1 >>> var3 - var6 - 8));
         this.bits[var4 + 2] = (byte)((int)(var1 >>> var3 - var6 - 16));
         this.bits[var4 + 3] = (byte)((int)(var1 >>> var3 - var6 - 24));
         this.bits[var4 + 4] = (byte)((int)(var1 >>> var3 - var6 - 32));
         this.bits[var4 + 5] = (byte)((int)(var1 >>> var3 - var6 - 40));
         this.bits[var4 + 6] = (byte)((int)(var1 >>> var3 - var6 - 48));
         this.bits[var4 + 7] = (byte)((int)(var1 >>> var3 - var6 - 56));
         this.bits[var4 + 8] = (byte)((int)(var1 << 64 + var6 - var3));
      }

      this.bitsize += var3;
   }

   public void appendSBigInteger(BigInteger var1, int var2) {
      byte[] var3 = var1.toByteArray();
      int var4 = var3.length << 3;
      this.ensureBitCapacity(this.bitsize + var2);
      if (var2 > var4) {
         bitfill(this.bits, this.bitsize, var2 - var4, var3[0] < 0);
         this.bitsize += var2 - var4;
         var2 = var4;
      }

      bitcpy(this.bits, this.bitsize, var3, var4 - var2, var2);
      this.bitsize += var2;
   }

   public void appendUBigInteger(BigInteger var1, int var2) {
      byte[] var3 = var1.toByteArray();
      int var4 = var3.length << 3;
      this.ensureBitCapacity(this.bitsize + var2);
      if (var2 > var4) {
         this.bitsize += var2 - var4;
         var2 = var4;
      }

      bitcpy(this.bits, this.bitsize, var3, var4 - var2, var2);
      this.bitsize += var2;
   }

   public void appendString16(String16 var1, int var2, int var3) {
      this.ensureBitCapacity(this.bitsize + var2 * var3);
      int var4;
      if (var3 > 16) {
         var4 = var3 - 16;
         var3 = 16;
      } else {
         var4 = 0;
      }

      for(int var5 = 0; var5 < var2; ++var5) {
         this.bitsize += var4;
         int var6 = subscript(this.bitsize);
         int var7 = this.bitsize & 7;
         int var8 = 8 - var7;
         short var9 = (short)var1.charAt(var5);
         byte[] var10000;
         if (var8 >= var3) {
            var10000 = this.bits;
            var10000[var6] |= (byte)((var9 & rightmask[var3]) << var8 - var3);
         } else if (var8 + 8 >= var3) {
            var10000 = this.bits;
            var10000[var6] |= (byte)(var9 >>> var3 - var8 & rightmask[var8]);
            this.bits[var6 + 1] = (byte)(var9 << 8 + var8 - var3);
         } else {
            var10000 = this.bits;
            var10000[var6] |= (byte)(var9 >>> var3 - var8 & rightmask[var8]);
            this.bits[var6 + 1] = (byte)(var9 >>> var3 - var8 - 8);
            this.bits[var6 + 2] = (byte)(var9 << 16 + var8 - var3);
         }

         this.bitsize += var3;
      }

   }

   public void appendString32(String32 var1, int var2, int var3) {
      this.ensureBitCapacity(this.bitsize + var2 * var3);
      int var4;
      if (var3 > 32) {
         var4 = var3 - 32;
         var3 = 32;
      } else {
         var4 = 0;
      }

      for(int var5 = 0; var5 < var2; ++var5) {
         this.bitsize += var4;
         int var6 = subscript(this.bitsize);
         int var7 = this.bitsize & 7;
         int var8 = 8 - var7;
         int var9 = var1.charAt(var5);
         byte[] var10000;
         if (var8 >= var3) {
            var10000 = this.bits;
            var10000[var6] |= (byte)((var9 & rightmask[var3]) << var8 - var3);
         } else if (var8 + 8 >= var3) {
            var10000 = this.bits;
            var10000[var6] |= (byte)(var9 >>> var3 - var8 & rightmask[var8]);
            this.bits[var6 + 1] = (byte)(var9 << 8 + var8 - var3);
         } else if (var8 + 16 >= var3) {
            var10000 = this.bits;
            var10000[var6] |= (byte)(var9 >>> var3 - var8 & rightmask[var8]);
            this.bits[var6 + 1] = (byte)(var9 >>> var3 - var8 - 8);
            this.bits[var6 + 2] = (byte)(var9 << 16 + var8 - var3);
         } else if (var8 + 24 >= var3) {
            var10000 = this.bits;
            var10000[var6] |= (byte)(var9 >>> var3 - var8 & rightmask[var8]);
            this.bits[var6 + 1] = (byte)(var9 >>> var3 - var8 - 8);
            this.bits[var6 + 2] = (byte)(var9 >>> var3 - var8 - 16);
            this.bits[var6 + 3] = (byte)(var9 << 24 + var8 - var3);
         } else {
            var10000 = this.bits;
            var10000[var6] |= (byte)(var9 >>> var3 - var8 & rightmask[var8]);
            this.bits[var6 + 1] = (byte)(var9 >>> var3 - var8 - 8);
            this.bits[var6 + 2] = (byte)(var9 >>> var3 - var8 - 16);
            this.bits[var6 + 3] = (byte)(var9 >>> var3 - var8 - 24);
            this.bits[var6 + 4] = (byte)(var9 << 32 + var8 - var3);
         }

         this.bitsize += var3;
      }

   }

   public int getSInteger(int var1, int var2) throws ValueTooLargeException {
      this.checkBitRange(var1, var2);
      if (var2 > 32) {
         try {
            if (this.getBit(var1)) {
               this.getOnes(var1 + 1, var2 - 32);
            } else {
               this.getZeros(var1 + 1, var2 - 32);
            }
         } catch (BadDataException var7) {
            throw new ValueTooLargeException();
         }

         var1 += var2 - 32;
         var2 = 32;
      }

      int var4 = subscript(var1);
      int var5 = var1 & 7;
      int var6 = 8 - var5;
      int var3;
      if (var6 >= var2) {
         var3 = this.bits[var4] << var5 + 24 >> 32 - var2;
      } else if (var6 + 8 >= var2) {
         var3 = (this.bits[var4] << var5 + 24 | (this.bits[var4 + 1] & 255) << var5 + 16) >> 32 - var2;
      } else if (var6 + 16 >= var2) {
         var3 = (this.bits[var4] << var5 + 24 | (this.bits[var4 + 1] & 255) << var5 + 16 | (this.bits[var4 + 2] & 255) << var5 + 8) >> 32 - var2;
      } else if (var6 + 24 >= var2) {
         var3 = (this.bits[var4] << var5 + 24 | (this.bits[var4 + 1] & 255) << var5 + 16 | (this.bits[var4 + 2] & 255) << var5 + 8 | (this.bits[var4 + 3] & 255) << var5) >> 32 - var2;
      } else {
         var3 = (this.bits[var4] << var5 + 24 | (this.bits[var4 + 1] & 255) << var5 + 16 | (this.bits[var4 + 2] & 255) << var5 + 8 | (this.bits[var4 + 3] & 255) << var5 | (this.bits[var4 + 4] & 255) >> var6) >> 32 - var2;
      }

      return var3;
   }

   public int getUInteger(int var1, int var2) throws ValueTooLargeException {
      this.checkBitRange(var1, var2);
      if (var2 > 32) {
         try {
            if (this.getBit(var1)) {
               this.getOnes(var1 + 1, var2 - 32);
            } else {
               this.getZeros(var1 + 1, var2 - 32);
            }
         } catch (BadDataException var7) {
            throw new ValueTooLargeException();
         }

         var1 += var2 - 32;
         var2 = 32;
      }

      int var4 = subscript(var1);
      int var5 = var1 & 7;
      int var6 = 8 - var5;
      int var3;
      if (var6 >= var2) {
         var3 = this.bits[var4] << var5 + 24 >>> 32 - var2;
      } else if (var6 + 8 >= var2) {
         var3 = (this.bits[var4] << var5 + 24 | (this.bits[var4 + 1] & 255) << var5 + 16) >>> 32 - var2;
      } else if (var6 + 16 >= var2) {
         var3 = (this.bits[var4] << var5 + 24 | (this.bits[var4 + 1] & 255) << var5 + 16 | (this.bits[var4 + 2] & 255) << var5 + 8) >>> 32 - var2;
      } else if (var6 + 24 >= var2) {
         var3 = (this.bits[var4] << var5 + 24 | (this.bits[var4 + 1] & 255) << var5 + 16 | (this.bits[var4 + 2] & 255) << var5 + 8 | (this.bits[var4 + 3] & 255) << var5) >>> 32 - var2;
      } else {
         var3 = (this.bits[var4] << var5 + 24 | (this.bits[var4 + 1] & 255) << var5 + 16 | (this.bits[var4 + 2] & 255) << var5 + 8 | (this.bits[var4 + 3] & 255) << var5 | (this.bits[var4 + 4] & 255) >> var6) >>> 32 - var2;
      }

      return var3;
   }

   public long getSLong(int var1, int var2) throws ValueTooLargeException {
      this.checkBitRange(var1, var2);
      if (var2 > 64) {
         try {
            if (this.getBit(var1)) {
               this.getOnes(var1 + 1, var2 - 64);
            } else {
               this.getZeros(var1 + 1, var2 - 64);
            }
         } catch (BadDataException var8) {
            throw new ValueTooLargeException();
         }

         var1 += var2 - 64;
         var2 = 64;
      }

      int var5 = subscript(var1);
      int var6 = var1 & 7;
      int var7 = 8 - var6;
      long var3;
      if (var7 >= var2) {
         var3 = (long)(this.bits[var5] << var6 + 24 >> 32 - var2);
      } else if (var7 + 8 >= var2) {
         var3 = (long)((this.bits[var5] << var6 + 24 | (this.bits[var5 + 1] & 255) << var6 + 16) >> 32 - var2);
      } else if (var7 + 16 >= var2) {
         var3 = (long)((this.bits[var5] << var6 + 24 | (this.bits[var5 + 1] & 255) << var6 + 16 | (this.bits[var5 + 2] & 255) << var6 + 8) >> 32 - var2);
      } else if (var7 + 24 >= var2) {
         var3 = (long)((this.bits[var5] << var6 + 24 | (this.bits[var5 + 1] & 255) << var6 + 16 | (this.bits[var5 + 2] & 255) << var6 + 8 | (this.bits[var5 + 3] & 255) << var6) >> 32 - var2);
      } else if (var7 + 32 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24) >> 64 - var2;
      } else if (var7 + 40 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24 | ((long)this.bits[var5 + 5] & 255L) << var6 + 16) >> 64 - var2;
      } else if (var7 + 48 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24 | ((long)this.bits[var5 + 5] & 255L) << var6 + 16 | ((long)this.bits[var5 + 6] & 255L) << var6 + 8) >> 64 - var2;
      } else if (var7 + 56 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24 | ((long)this.bits[var5 + 5] & 255L) << var6 + 16 | ((long)this.bits[var5 + 6] & 255L) << var6 + 8 | ((long)this.bits[var5 + 7] & 255L) << var6) >> 64 - var2;
      } else {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24 | ((long)this.bits[var5 + 5] & 255L) << var6 + 16 | ((long)this.bits[var5 + 6] & 255L) << var6 + 8 | ((long)this.bits[var5 + 7] & 255L) << var6 | ((long)this.bits[var5 + 8] & 255L) >> var7) >> 64 - var2;
      }

      return var3;
   }

   public long getULong(int var1, int var2) throws ValueTooLargeException {
      this.checkBitRange(var1, var2);
      if (var2 > 64) {
         try {
            if (this.getBit(var1)) {
               this.getOnes(var1 + 1, var2 - 64);
            } else {
               this.getZeros(var1 + 1, var2 - 64);
            }
         } catch (BadDataException var8) {
            throw new ValueTooLargeException();
         }

         var1 += var2 - 64;
         var2 = 64;
      }

      int var5 = subscript(var1);
      int var6 = var1 & 7;
      int var7 = 8 - var6;
      long var3;
      if (var7 >= var2) {
         var3 = (long)this.bits[var5] << var6 + 56 >>> 64 - var2;
      } else if (var7 + 8 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48) >>> 64 - var2;
      } else if (var7 + 16 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40) >>> 64 - var2;
      } else if (var7 + 24 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32) >>> 64 - var2;
      } else if (var7 + 32 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24) >>> 64 - var2;
      } else if (var7 + 40 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24 | ((long)this.bits[var5 + 5] & 255L) << var6 + 16) >>> 64 - var2;
      } else if (var7 + 48 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24 | ((long)this.bits[var5 + 5] & 255L) << var6 + 16 | ((long)this.bits[var5 + 6] & 255L) << var6 + 8) >>> 64 - var2;
      } else if (var7 + 56 >= var2) {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24 | ((long)this.bits[var5 + 5] & 255L) << var6 + 16 | ((long)this.bits[var5 + 6] & 255L) << var6 + 8 | ((long)this.bits[var5 + 7] & 255L) << var6) >>> 64 - var2;
      } else {
         var3 = ((long)this.bits[var5] << var6 + 56 | ((long)this.bits[var5 + 1] & 255L) << var6 + 48 | ((long)this.bits[var5 + 2] & 255L) << var6 + 40 | ((long)this.bits[var5 + 3] & 255L) << var6 + 32 | ((long)this.bits[var5 + 4] & 255L) << var6 + 24 | ((long)this.bits[var5 + 5] & 255L) << var6 + 16 | ((long)this.bits[var5 + 6] & 255L) << var6 + 8 | ((long)this.bits[var5 + 7] & 255L) << var6 | ((long)this.bits[var5 + 8] & 255L) >> var7) >>> 64 - var2;
      }

      return var3;
   }

   public BigInteger getSBigInteger(int var1, int var2) {
      this.checkBitRange(var1, var2);
      int var4 = var2 + 7 >> 3;
      byte[] var3 = new byte[var4];
      int var5 = 0;
      if ((var2 & 7) != 0) {
         var3[var5++] = this.extractSByte(var1, var2 & 7);
         var1 += var2 & 7;
      }

      while(var5 < var4) {
         var3[var5++] = this.extractByte(var1);
         var1 += 8;
      }

      return new BigInteger(var3);
   }

   public BigInteger getUBigInteger(int var1, int var2) {
      this.checkBitRange(var1, var2);
      int var4 = var2 + 15 >> 3;
      byte[] var3 = new byte[var4];
      int var5 = 0;
      var3[var5++] = 0;
      if ((var2 & 7) != 0) {
         var3[var5++] = this.extractUByte(var1, var2 & 7);
         var1 += var2 & 7;
      }

      while(var5 < var4) {
         var3[var5++] = this.extractByte(var1);
         var1 += 8;
      }

      return new BigInteger(var3);
   }

   public String getString(int var1, int var2, int var3) {
      StringBuffer var4 = new StringBuffer(var2);
      var4.setLength(var2);
      checkSize(var3);
      if (var3 > 16) {
         throw new IndexOutOfBoundsException(Integer.toString(var3));
      } else {
         this.checkBitRange(var1, var2 * var3);

         try {
            for(int var5 = 0; var5 < var2; ++var5) {
               var4.setCharAt(var5, (char)this.getUInteger(var1, var3));
               var1 += var3;
            }
         } catch (ValueTooLargeException var6) {
            throw new InternalError();
         }

         return var4.toString();
      }
   }

   public String32 getString32(int var1, int var2, int var3) {
      String32Buffer var4 = new String32Buffer(var2);
      var4.setLength(var2);
      checkSize(var3);
      if (var3 > 32) {
         throw new IndexOutOfBoundsException(Integer.toString(var3));
      } else {
         this.checkBitRange(var1, var2 * var3);

         try {
            for(int var5 = 0; var5 < var2; ++var5) {
               var4.setCharAt(var5, this.getUInteger(var1, var3));
               var1 += var3;
            }
         } catch (ValueTooLargeException var6) {
            throw new InternalError();
         }

         return var4.toString32();
      }
   }

   public void getByteArray(byte[] var1, int var2, int var3, int var4) {
      this.checkBitRange(var3, var4);
      this.checkBitRange(var2 * 8, var4, var1.length * 8);
      if ((var4 & 7) != 0) {
         throw new IllegalArgumentException(Integer.toString(var4));
      } else {
         while(var4 >= 8) {
            var1[var2++] = this.extractByte(var3);
            var3 += 8;
            var4 -= 8;
         }

      }
   }

   public byte[] getInternalByteArray() {
      return this.bits;
   }

   public void getZeros(int var1, int var2) throws BadDataException {
      this.checkBitRange(var1, var2);
      if (bitscan(this.bits, var1, var2) < var1 + var2) {
         throw new BadDataException();
      }
   }

   public void getOnes(int var1, int var2) throws BadDataException {
      this.checkBitRange(var1, var2);
      if (bitcscan(this.bits, var1, var2) < var1 + var2) {
         throw new BadDataException();
      }
   }

   protected static final int subscript(int var0) {
      return var0 >> 3;
   }

   protected static final int sublength(int var0) {
      return var0 + 7 >> 3;
   }

   protected final void checkBitIndex(int var1) {
      this.checkBitIndex(var1, this.bitsize);
   }

   protected void checkBitIndex(int var1, int var2) {
      if (var1 < 0 || var1 >= var2) {
         throw new IndexOutOfBoundsException(Integer.toString(var1));
      }
   }

   protected final void checkBitRange(int var1, int var2) {
      this.checkBitRange(var1, var2, this.bitsize);
   }

   protected void checkBitRange(int var1, int var2, int var3) {
      if (var2 < 0) {
         throw new NegativeArraySizeException(Integer.toString(var2));
      } else if (var1 < 0) {
         throw new IndexOutOfBoundsException(Integer.toString(var1));
      } else if (var1 > var3 - var2) {
         throw new IndexOutOfBoundsException(Integer.toString(var1 + var2 - 1));
      }
   }

   protected static final void checkSize(int var0) {
      if (var0 < 0) {
         throw new NegativeArraySizeException(Integer.toString(var0));
      }
   }

   protected static final void checkUnitSize(int var0) {
      if (var0 != 1 && var0 != 8) {
         throw new IllegalArgumentException(Integer.toString(var0));
      }
   }

   protected void checkBitLength(int var1) {
      if (var1 < 0) {
         throw new NegativeArraySizeException(Integer.toString(var1));
      } else if (var1 % this.unitsize != 0) {
         throw new IllegalArgumentException(Integer.toString(var1));
      }
   }

   protected static final void bitclr(byte[] var0, int var1, int var2) {
      int var3 = subscript(var1);
      int var4 = var1 & 7;
      int var5 = 8 - var4;
      if (var2 >= var5) {
         int var10001 = var3++;
         var0[var10001] &= leftmask[var4];

         for(var2 -= var5; var2 >= 8; var2 -= 8) {
            var0[var3++] = 0;
         }

         if (var2 > 0) {
            var0[var3] &= rightmask[8 - var2];
         }
      } else {
         var0[var3] = (byte)(var0[var3] & ~(leftmask[var2] >>> var4));
      }

   }

   protected static final void bitset(byte[] var0, int var1, int var2) {
      int var3 = subscript(var1);
      int var4 = var1 & 7;
      int var5 = 8 - var4;
      if (var2 >= var5) {
         int var10001 = var3++;
         var0[var10001] |= rightmask[var5];

         for(var2 -= var5; var2 >= 8; var2 -= 8) {
            var0[var3++] = -1;
         }

         if (var2 > 0) {
            var0[var3] |= leftmask[var2];
         }
      } else {
         var0[var3] = (byte)(var0[var3] | leftmask[var2] >>> var4);
      }

   }

   protected static final void bitfill(byte[] var0, int var1, int var2, boolean var3) {
      if (var3) {
         bitset(var0, var1, var2);
      } else {
         bitclr(var0, var1, var2);
      }

   }

   protected static final void bitcpy(byte[] var0, int var1, byte[] var2, int var3, int var4) {
      if (var4 != 0) {
         int var5 = subscript(var1);
         int var6 = subscript(var3);
         int var7 = var1 & 7;
         int var8 = var3 & 7;
         int var9 = 8 - var7;
         int var10 = 8 - var8;
         byte var11;
         if (var4 >= var9) {
            if (var10 >= var9) {
               var11 = (byte)(var2[var6] << var8);
            } else {
               var11 = (byte)(var2[var6] << var8 | (var2[var6 + 1] & 255) >>> var10);
            }

            var0[var5] = (byte)(var0[var5] & leftmask[var7] | (var11 & 255) >>> var7);
            var4 -= var9;
            var8 += var9;
            if (var8 >= 8) {
               var8 -= 8;
               ++var6;
            }

            var10 = 8 - var8;
            ++var5;
            if (var4 >= 8) {
               if (var8 == 0) {
                  int var12 = subscript(var4);
                  System.arraycopy(var2, var6, var0, var5, var12);
                  var6 += var12;
                  var5 += var12;
                  var4 &= 7;
               } else {
                  while(var4 >= 8) {
                     var11 = (byte)(var2[var6] << var8 | (var2[var6 + 1] & 255) >>> var10);
                     var0[var5++] = var11;
                     ++var6;
                     var4 -= 8;
                  }
               }
            }

            if (var4 > 0) {
               if (var10 >= var4) {
                  var11 = (byte)(var2[var6] << var8);
               } else {
                  var11 = (byte)(var2[var6] << var8 | (var2[var6 + 1] & 255) >>> var10);
               }

               var0[var5] = (byte)(var11 & leftmask[var4] | var0[var5] & rightmask[8 - var4]);
            }
         } else {
            if (var10 >= var4) {
               var11 = (byte)(var2[var6] << var8);
            } else {
               var11 = (byte)(var2[var6] << var8 | (var2[var6 + 1] & 255) >>> var10);
            }

            var0[var5] = (byte)(var0[var5] & leftmask[var7] | (var11 & 255 & leftmask[var4]) >>> var7 | var0[var5] & rightmask[var9 - var4]);
         }

      }
   }

   protected static final void bitmove(byte[] var0, int var1, byte[] var2, int var3, int var4) {
      if (var0 == var2 && var1 > var3 && var1 < var3 + var4) {
         bitcpy(var0, var1, (byte[])var2.clone(), var3, var4);
      } else {
         bitcpy(var0, var1, var2, var3, var4);
      }

   }

   protected static final int bitscan(byte[] var0, int var1, int var2) {
      int var3 = subscript(var1);
      int var4 = var1 & 7;
      if (var4 != 0) {
         while(true) {
            if (var4 >= 8 || var2 <= 0) {
               ++var3;
               break;
            }

            if ((var0[var3] & bitmask[var4]) != 0) {
               return var1;
            }

            ++var1;
            ++var4;
            --var2;
         }
      }

      while(var2 >= 8) {
         if (var0[var3] != 0) {
            for(var4 = 0; var4 < 8; ++var4) {
               if ((var0[var3] & bitmask[var4]) != 0) {
                  return var1;
               }

               ++var1;
            }
         }

         var1 += 8;
         ++var3;
         var2 -= 8;
      }

      if (var2 > 0) {
         for(var4 = 0; var4 < var2; ++var4) {
            if ((var0[var3] & bitmask[var4]) != 0) {
               return var1;
            }

            ++var1;
         }
      }

      return var1;
   }

   protected static final int bitcscan(byte[] var0, int var1, int var2) {
      int var3 = subscript(var1);
      int var4 = var1 & 7;
      if (var4 != 0) {
         while(true) {
            if (var4 >= 8 || var2 <= 0) {
               ++var3;
               break;
            }

            if ((var0[var3] & bitmask[var4]) == 0) {
               return var1;
            }

            ++var1;
            ++var4;
            --var2;
         }
      }

      while(var2 >= 8) {
         if (var0[var3] != -1) {
            for(var4 = 0; var4 < 8; ++var4) {
               if ((var0[var3] & bitmask[var4]) == 0) {
                  return var1;
               }

               ++var1;
            }
         }

         var1 += 8;
         ++var3;
         var2 -= 8;
      }

      if (var2 > 0) {
         for(var4 = 0; var4 < var2; ++var4) {
            if ((var0[var3] & bitmask[var4]) == 0) {
               return var1;
            }

            ++var1;
         }
      }

      return var1;
   }
}
