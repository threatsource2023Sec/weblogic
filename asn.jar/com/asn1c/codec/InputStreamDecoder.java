package com.asn1c.codec;

import com.asn1c.core.BitString;
import com.asn1c.core.SuperfluousDataException;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class InputStreamDecoder extends Decoder {
   protected InputStream in;
   private BitString buffer;
   private int pos;
   private long dropped;
   private byte[] byteBuffer = null;
   private static final int maxByteBufferSize = 8192;
   private static final int maxOffset = 65536;
   private static final int maxSkipBufferSize = 65536;

   private void ensureOpen() throws IOException {
      if (this.in == null) {
         throw new IOException("stream closed");
      }
   }

   private void ensureBuffered(int var1) throws IOException, EOFException {
      int var2 = this.buffer.bitLength() - this.pos;
      if (var2 < var1) {
         if (var2 == 0) {
            this.dropped += (long)this.pos;
            this.pos = 0;
            this.buffer.setBitLength(0);
         } else if (this.pos >= 65536) {
            this.buffer.setBits(this.buffer, this.pos & 7, this.pos, var2);
            this.dropped += (long)(this.pos & -8);
            this.pos &= 7;
            this.buffer.setBitLength(this.pos + var2);
         }

         int var3 = var1 - var2;
         if (this.byteBuffer == null) {
            this.byteBuffer = new byte[8192];
         }

         while(var3 > 0) {
            int var4 = this.in.read(this.byteBuffer, 0, Math.min((var3 + 7) / 8, 8192));
            if (var4 < 0) {
               throw new EOFException();
            }

            this.buffer.appendOctetsFromByteArray(this.byteBuffer, 0, var4);
            var3 -= var4 * 8;
         }

      }
   }

   public InputStreamDecoder(InputStream var1) {
      this.in = var1;
      this.buffer = new BitString();
      this.pos = 0;
      this.dropped = 0L;
   }

   public InputStream getInputStream() {
      return this.in;
   }

   public void setInputStream(InputStream var1) {
      this.in = var1;
      this.buffer.setBitLength(0);
      this.pos = 0;
      this.dropped = 0L;
   }

   public void read(BitString var1, int var2, int var3) throws IOException, EOFException {
      this.ensureOpen();
      this.ensureBuffered(var3);
      var1.setBits(this.buffer, this.pos, var2, var3);
      this.pos += var3;
   }

   public int getBitsToAlignment() {
      return -this.pos & 7;
   }

   public long getBitsRead() {
      return this.dropped + (long)this.pos;
   }

   public boolean checkEndOfData() {
      return false;
   }

   public void flushIn() throws IOException, EOFException, SuperfluousDataException {
   }

   public void skip(long var1) throws IOException, EOFException {
      long var3 = var1;
      this.ensureOpen();

      while(var3 > 0L) {
         int var5 = (int)Math.min(var3, 65536L);
         this.ensureBuffered(var5);
         this.pos += var5;
         var3 -= (long)var5;
      }

   }

   public void close() throws IOException {
      if (this.in != null) {
         this.in.close();
         this.in = null;
         this.buffer.setBitLength(0);
      }
   }

   public boolean readBit() throws IOException, EOFException {
      this.ensureOpen();
      this.ensureBuffered(1);
      return this.buffer.getBit(this.pos++);
   }

   public BitString readBits(int var1) throws IOException, EOFException {
      BitString var2 = new BitString(var1);
      this.read(var2, 0, var1);
      return var2;
   }

   public int readSInteger(int var1) throws IOException, EOFException, ValueTooLargeException {
      this.ensureBuffered(var1);
      int var2 = this.buffer.getSInteger(this.pos, var1);
      this.pos += var1;
      return var2;
   }

   public int readUInteger(int var1) throws IOException, EOFException, ValueTooLargeException {
      this.ensureBuffered(var1);
      int var2 = this.buffer.getUInteger(this.pos, var1);
      this.pos += var1;
      return var2;
   }

   public long readSLong(int var1) throws IOException, EOFException, ValueTooLargeException {
      this.ensureBuffered(var1);
      long var2 = this.buffer.getSLong(this.pos, var1);
      this.pos += var1;
      return var2;
   }

   public long readULong(int var1) throws IOException, EOFException, ValueTooLargeException {
      this.ensureBuffered(var1);
      long var2 = this.buffer.getULong(this.pos, var1);
      this.pos += var1;
      return var2;
   }

   public BigInteger readSBigInteger(int var1) throws IOException, EOFException {
      this.ensureBuffered(var1);
      BigInteger var2 = this.buffer.getSBigInteger(this.pos, var1);
      this.pos += var1;
      return var2;
   }

   public BigInteger readUBigInteger(int var1) throws IOException, EOFException {
      this.ensureBuffered(var1);
      BigInteger var2 = this.buffer.getUBigInteger(this.pos, var1);
      this.pos += var1;
      return var2;
   }
}
