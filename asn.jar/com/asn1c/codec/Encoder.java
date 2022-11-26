package com.asn1c.codec;

import com.asn1c.core.BitString;
import com.asn1c.core.UnitString;
import java.io.IOException;
import java.math.BigInteger;

public abstract class Encoder {
   protected Encoder() {
      License.checkLicense();
   }

   public abstract void write(UnitString var1, int var2, int var3) throws IOException;

   public abstract int getBitsToAlignment();

   public abstract long getBitsWritten();

   public abstract void flushOut() throws IOException;

   public abstract void close() throws IOException;

   public void writeBit(boolean var1) throws IOException {
      BitString var2 = new BitString(1);
      var2.setBit(0, var1);
      this.write(var2, 0, 1);
   }

   public void writeBits(UnitString var1) throws IOException {
      this.write(var1, 0, var1.bitLength());
   }

   public void writeByte(byte var1) throws IOException {
      BitString var2 = new BitString();
      var2.appendByte(var1);
      this.write(var2, 0, 8);
   }

   public void writeSInteger(int var1, int var2) throws IOException {
      BitString var3 = new BitString();
      var3.appendSInteger(var1, var2);
      this.write(var3, 0, var2);
   }

   public void writeUInteger(int var1, int var2) throws IOException {
      BitString var3 = new BitString();
      var3.appendUInteger(var1, var2);
      this.write(var3, 0, var2);
   }

   public void writeSLong(long var1, int var3) throws IOException {
      BitString var4 = new BitString();
      var4.appendSLong(var1, var3);
      this.write(var4, 0, var3);
   }

   public void writeULong(long var1, int var3) throws IOException {
      BitString var4 = new BitString();
      var4.appendULong(var1, var3);
      this.write(var4, 0, var3);
   }

   public void writeSBigInteger(BigInteger var1, int var2) throws IOException {
      BitString var3 = new BitString();
      var3.appendSBigInteger(var1, var2);
      this.write(var3, 0, var2);
   }

   public void writeUBigInteger(BigInteger var1, int var2) throws IOException {
      BitString var3 = new BitString();
      var3.appendUBigInteger(var1, var2);
      this.write(var3, 0, var2);
   }
}
