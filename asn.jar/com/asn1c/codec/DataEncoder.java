package com.asn1c.codec;

import com.asn1c.core.BitString;
import com.asn1c.core.UnitString;
import java.math.BigInteger;

public class DataEncoder extends Encoder {
   protected BitString bs = new BitString();

   public BitString getEncodedData() {
      return this.bs;
   }

   public BitString getAndClearEncodedData() {
      BitString var1 = this.bs;
      this.bs = new BitString();
      return var1;
   }

   public void write(UnitString var1, int var2, int var3) {
      this.bs.appendBits(var1, var2, var3);
   }

   public int getBitsToAlignment() {
      int var1 = this.bs.bitLength();
      return (var1 & 7) != 0 ? 8 - (var1 & 7) : 0;
   }

   public long getBitsWritten() {
      return (long)this.bs.bitLength();
   }

   public void flushOut() {
   }

   public void close() {
   }

   public void writeBit(boolean var1) {
      this.bs.appendBit(var1);
   }

   public void writeBits(UnitString var1) {
      this.bs.append(var1);
   }

   public void writeByte(byte var1) {
      this.bs.appendByte(var1);
   }

   public void writeSInteger(int var1, int var2) {
      this.bs.appendSInteger(var1, var2);
   }

   public void writeUInteger(int var1, int var2) {
      this.bs.appendUInteger(var1, var2);
   }

   public void writeSLong(long var1, int var3) {
      this.bs.appendSLong(var1, var3);
   }

   public void writeULong(long var1, int var3) {
      this.bs.appendULong(var1, var3);
   }

   public void writeSBigInteger(BigInteger var1, int var2) {
      this.bs.appendSBigInteger(var1, var2);
   }

   public void writeUBigInteger(BigInteger var1, int var2) {
      this.bs.appendUBigInteger(var1, var2);
   }
}
