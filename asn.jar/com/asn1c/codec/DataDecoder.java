package com.asn1c.codec;

import com.asn1c.core.BitString;
import com.asn1c.core.SuperfluousDataException;
import com.asn1c.core.UnitString;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;

public class DataDecoder extends Decoder {
   protected UnitString bs;
   protected int len;
   protected int pos;

   public DataDecoder() {
      this.bs = new BitString();
      this.len = 0;
      this.pos = 0;
   }

   public DataDecoder(UnitString var1) {
      this.bs = var1;
      this.len = var1.bitLength();
      this.pos = 0;
   }

   public void setEncodedData(UnitString var1) {
      this.bs = var1;
      this.len = var1.bitLength();
      this.pos = 0;
   }

   public long getBitsRead() {
      return (long)this.pos;
   }

   public void read(BitString var1, int var2, int var3) throws IOException, EOFException {
      if (this.pos + var3 > this.len) {
         throw new EOFException();
      } else {
         var1.setBits(this.bs, this.pos, var2, var3);
         this.pos += var3;
      }
   }

   public int getBitsToAlignment() {
      return -this.pos & 7;
   }

   public boolean checkEndOfData() {
      return this.pos >= this.len;
   }

   public void flushIn() throws IOException, EOFException, SuperfluousDataException {
      if (this.pos < this.len) {
         throw new SuperfluousDataException();
      }
   }

   public void skip(long var1) throws IOException, EOFException {
      if ((long)this.pos + var1 > (long)this.len) {
         throw new EOFException();
      } else {
         this.pos = (int)((long)this.pos + var1);
      }
   }

   public void close() throws IOException {
   }

   public boolean readBit() throws IOException, EOFException {
      if (this.pos >= this.len) {
         throw new EOFException();
      } else {
         return this.bs.getBit(this.pos++);
      }
   }

   public BitString readBits(int var1) throws IOException, EOFException {
      if (this.pos + var1 > this.len) {
         throw new EOFException();
      } else {
         BitString var2 = this.bs.getBits(this.pos, var1);
         this.pos += var1;
         return var2;
      }
   }

   public int readSInteger(int var1) throws IOException, EOFException, ValueTooLargeException {
      if (this.pos + var1 > this.len) {
         throw new EOFException();
      } else {
         int var2 = this.bs.getSInteger(this.pos, var1);
         this.pos += var1;
         return var2;
      }
   }

   public int readUInteger(int var1) throws IOException, EOFException, ValueTooLargeException {
      if (this.pos + var1 > this.len) {
         throw new EOFException();
      } else {
         int var2 = this.bs.getUInteger(this.pos, var1);
         this.pos += var1;
         return var2;
      }
   }

   public long readSLong(int var1) throws IOException, EOFException, ValueTooLargeException {
      if (this.pos + var1 > this.len) {
         throw new EOFException();
      } else {
         long var2 = this.bs.getSLong(this.pos, var1);
         this.pos += var1;
         return var2;
      }
   }

   public long readULong(int var1) throws IOException, EOFException, ValueTooLargeException {
      if (this.pos + var1 > this.len) {
         throw new EOFException();
      } else {
         long var2 = this.bs.getULong(this.pos, var1);
         this.pos += var1;
         return var2;
      }
   }

   public BigInteger readSBigInteger(int var1) throws IOException, EOFException {
      if (this.pos + var1 > this.len) {
         throw new EOFException();
      } else {
         BigInteger var2 = this.bs.getSBigInteger(this.pos, var1);
         this.pos += var1;
         return var2;
      }
   }

   public BigInteger readUBigInteger(int var1) throws IOException, EOFException {
      if (this.pos + var1 > this.len) {
         throw new EOFException();
      } else {
         BigInteger var2 = this.bs.getUBigInteger(this.pos, var1);
         this.pos += var1;
         return var2;
      }
   }
}
