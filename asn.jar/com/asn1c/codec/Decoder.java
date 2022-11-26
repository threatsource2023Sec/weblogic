package com.asn1c.codec;

import com.asn1c.core.BadDataException;
import com.asn1c.core.BitString;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;

public abstract class Decoder {
   private static final int maxSkipBufferSize = 65536;
   private BitString skipBuffer = null;

   protected Decoder() {
      License.checkLicense();
   }

   public abstract void read(BitString var1, int var2, int var3) throws IOException, EOFException;

   public abstract int getBitsToAlignment();

   public abstract long getBitsRead();

   public abstract boolean checkEndOfData();

   public abstract void flushIn() throws IOException, EOFException, BadDataException;

   public void skip(long var1) throws IOException, EOFException {
      if (this.skipBuffer == null) {
         this.skipBuffer = new BitString(65536);
      }

      for(long var3 = var1; var3 > 0L; var3 -= Math.min(65536L, var3)) {
         this.read(this.skipBuffer, 0, (int)Math.min(65536L, var3));
      }

   }

   public abstract void close() throws IOException;

   public boolean readBit() throws IOException, EOFException {
      BitString var1 = new BitString(1);
      this.read(var1, 0, 1);
      return var1.getBit(0);
   }

   public BitString readBits(int var1) throws IOException, EOFException {
      BitString var2 = new BitString(var1);
      this.read(var2, 0, var1);
      return var2;
   }

   public int readSInteger(int var1) throws IOException, EOFException, ValueTooLargeException {
      BitString var2 = new BitString(var1);
      this.read(var2, 0, var1);
      return var2.getSInteger(0, var1);
   }

   public int readUInteger(int var1) throws IOException, EOFException, ValueTooLargeException {
      BitString var2 = new BitString(var1);
      this.read(var2, 0, var1);
      return var2.getUInteger(0, var1);
   }

   public long readSLong(int var1) throws IOException, EOFException, ValueTooLargeException {
      BitString var2 = new BitString(var1);
      this.read(var2, 0, var1);
      return var2.getSLong(0, var1);
   }

   public long readULong(int var1) throws IOException, EOFException, ValueTooLargeException {
      BitString var2 = new BitString(var1);
      this.read(var2, 0, var1);
      return var2.getULong(0, var1);
   }

   public BigInteger readSBigInteger(int var1) throws IOException, EOFException {
      BitString var2 = new BitString(var1);
      this.read(var2, 0, var1);
      return var2.getSBigInteger(0, var1);
   }

   public BigInteger readUBigInteger(int var1) throws IOException, EOFException {
      BitString var2 = new BitString(var1);
      this.read(var2, 0, var1);
      return var2.getUBigInteger(0, var1);
   }
}
