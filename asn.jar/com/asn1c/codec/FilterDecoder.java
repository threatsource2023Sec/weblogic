package com.asn1c.codec;

import com.asn1c.core.BadDataException;
import com.asn1c.core.BitString;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;

public abstract class FilterDecoder extends Decoder {
   protected Decoder in;

   public FilterDecoder(Decoder var1) {
      this.in = var1;
   }

   public Decoder getInputDecoder() {
      return this.in;
   }

   public void read(BitString var1, int var2, int var3) throws IOException, EOFException {
      this.in.read(var1, var2, var3);
   }

   public int getBitsToAlignment() {
      return this.in.getBitsToAlignment();
   }

   public long getBitsRead() {
      return this.in.getBitsRead();
   }

   public boolean checkEndOfData() {
      return this.in.checkEndOfData();
   }

   public void flushIn() throws IOException, EOFException, BadDataException {
      this.in.flushIn();
   }

   public void skip(long var1) throws IOException, EOFException {
      this.in.skip(var1);
   }

   public void close() throws IOException {
      this.in.close();
   }

   public boolean readBit() throws IOException, EOFException {
      return this.in.readBit();
   }

   public BitString readBits(int var1) throws IOException, EOFException {
      return this.in.readBits(var1);
   }

   public int readSInteger(int var1) throws IOException, EOFException, ValueTooLargeException {
      return this.in.readSInteger(var1);
   }

   public int readUInteger(int var1) throws IOException, EOFException, ValueTooLargeException {
      return this.in.readUInteger(var1);
   }

   public long readSLong(int var1) throws IOException, EOFException, ValueTooLargeException {
      return this.in.readSLong(var1);
   }

   public long readULong(int var1) throws IOException, EOFException, ValueTooLargeException {
      return this.in.readULong(var1);
   }

   public BigInteger readSBigInteger(int var1) throws IOException, EOFException {
      return this.in.readSBigInteger(var1);
   }

   public BigInteger readUBigInteger(int var1) throws IOException, EOFException {
      return this.in.readUBigInteger(var1);
   }
}
