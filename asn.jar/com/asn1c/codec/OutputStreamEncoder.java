package com.asn1c.codec;

import com.asn1c.core.BitString;
import com.asn1c.core.UnitString;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

public class OutputStreamEncoder extends Encoder {
   protected OutputStream out;
   private static final int maxBufferSize = 65536;
   private BitString buffer;
   private long dropped;
   private byte[] byteBuffer = null;

   private void ensureOpen() throws IOException {
      if (this.out == null) {
         throw new IOException("stream closed");
      }
   }

   private void ensureToBuffer(int var1) throws IOException {
      if (this.buffer.bitLength() + var1 > 65536 && this.buffer.bitLength() > 0) {
         this.flushOut();
      }

   }

   public OutputStreamEncoder(OutputStream var1) {
      this.out = var1;
      this.buffer = new BitString();
      this.dropped = 0L;
   }

   public OutputStream getOutputStream() {
      return this.out;
   }

   public void setOutputStream(OutputStream var1) {
      this.out = var1;
      this.buffer.setBitLength(0);
      this.dropped = 0L;
   }

   public void write(UnitString var1, int var2, int var3) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(var3);
      this.buffer.appendBits(var1, var2, var3);
   }

   public int getBitsToAlignment() {
      return -this.buffer.bitLength() & 7;
   }

   public long getBitsWritten() {
      return this.dropped + (long)this.buffer.bitLength();
   }

   public void flushOut() throws IOException {
      this.ensureOpen();
      int var1 = this.buffer.bitLength() & -8;
      int var2 = this.buffer.bitLength() & 7;
      this.out.write(this.buffer.getInternalByteArray(), 0, var1 / 8);
      this.buffer.setBits(this.buffer, 0, var1, var2);
      this.buffer.setBitLength(var2);
      this.dropped += (long)var1;
   }

   public void close() throws IOException {
      if (this.out != null) {
         this.out.close();
         this.out = null;
         this.buffer.setBitLength(0);
      }
   }

   public void writeBit(boolean var1) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(1);
      this.buffer.appendBit(var1);
   }

   public void writeByte(byte var1) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(8);
      this.buffer.appendByte(var1);
   }

   public void writeSInteger(int var1, int var2) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(var2);
      this.buffer.appendSInteger(var1, var2);
   }

   public void writeUInteger(int var1, int var2) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(var2);
      this.buffer.appendUInteger(var1, var2);
   }

   public void writeSLong(long var1, int var3) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(var3);
      this.buffer.appendSLong(var1, var3);
   }

   public void writeULong(long var1, int var3) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(var3);
      this.buffer.appendULong(var1, var3);
   }

   public void writeSBigInteger(BigInteger var1, int var2) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(var2);
      this.buffer.appendSBigInteger(var1, var2);
   }

   public void writeUBigInteger(BigInteger var1, int var2) throws IOException {
      this.ensureOpen();
      this.ensureToBuffer(var2);
      this.buffer.appendUBigInteger(var1, var2);
   }
}
