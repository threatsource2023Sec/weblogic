package com.asn1c.codec;

import com.asn1c.core.UnitString;
import java.io.IOException;
import java.math.BigInteger;

public abstract class FilterEncoder extends Encoder {
   protected Encoder out;

   protected FilterEncoder(Encoder var1) {
      this.out = var1;
   }

   public Encoder getOutputEncoder() {
      return this.out;
   }

   public void write(UnitString var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }

   public int getBitsToAlignment() {
      return this.out.getBitsToAlignment();
   }

   public long getBitsWritten() {
      return this.out.getBitsWritten();
   }

   public void flushOut() throws IOException {
      this.out.flushOut();
   }

   public void close() throws IOException {
      this.out.close();
   }

   public void writeBit(boolean var1) throws IOException {
      this.out.writeBit(var1);
   }

   public void writeBits(UnitString var1) throws IOException {
      this.out.writeBits(var1);
   }

   public void writeByte(byte var1) throws IOException {
      this.out.writeByte(var1);
   }

   public void writeSInteger(int var1, int var2) throws IOException {
      this.out.writeSInteger(var1, var2);
   }

   public void writeUInteger(int var1, int var2) throws IOException {
      this.out.writeUInteger(var1, var2);
   }

   public void writeSLong(long var1, int var3) throws IOException {
      this.out.writeSLong(var1, var3);
   }

   public void writeULong(long var1, int var3) throws IOException {
      this.out.writeULong(var1, var3);
   }

   public void writeSBigInteger(BigInteger var1, int var2) throws IOException {
      this.out.writeSBigInteger(var1, var2);
   }

   public void writeUBigInteger(BigInteger var1, int var2) throws IOException {
      this.out.writeUBigInteger(var1, var2);
   }
}
