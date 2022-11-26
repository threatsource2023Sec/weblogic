package com.trilead.ssh2.crypto;

import java.io.IOException;
import java.math.BigInteger;

public class SimpleDERReader {
   byte[] buffer;
   int pos;
   int count;

   public SimpleDERReader(byte[] b) {
      this.resetInput(b);
   }

   public SimpleDERReader(byte[] b, int off, int len) {
      this.resetInput(b, off, len);
   }

   public void resetInput(byte[] b) {
      this.resetInput(b, 0, b.length);
   }

   public void resetInput(byte[] b, int off, int len) {
      this.buffer = b;
      this.pos = off;
      this.count = len;
   }

   private byte readByte() throws IOException {
      if (this.count <= 0) {
         throw new IOException("DER byte array: out of data");
      } else {
         --this.count;
         return this.buffer[this.pos++];
      }
   }

   private byte[] readBytes(int len) throws IOException {
      if (len > this.count) {
         throw new IOException("DER byte array: out of data");
      } else {
         byte[] b = new byte[len];
         System.arraycopy(this.buffer, this.pos, b, 0, len);
         this.pos += len;
         this.count -= len;
         return b;
      }
   }

   public int available() {
      return this.count;
   }

   private int readLength() throws IOException {
      int len = this.readByte() & 255;
      if ((len & 128) == 0) {
         return len;
      } else {
         int remain = len & 127;
         if (remain == 0) {
            return -1;
         } else {
            for(len = 0; remain > 0; --remain) {
               len <<= 8;
               len |= this.readByte() & 255;
            }

            return len;
         }
      }
   }

   public int ignoreNextObject() throws IOException {
      int type = this.readByte() & 255;
      int len = this.readLength();
      if (len >= 0 && len <= this.available()) {
         this.readBytes(len);
         return type;
      } else {
         throw new IOException("Illegal len in DER object (" + len + ")");
      }
   }

   public BigInteger readInt() throws IOException {
      int type = this.readByte() & 255;
      if (type != 2) {
         throw new IOException("Expected DER Integer, but found type " + type);
      } else {
         int len = this.readLength();
         if (len >= 0 && len <= this.available()) {
            byte[] b = this.readBytes(len);
            BigInteger bi = new BigInteger(b);
            return bi;
         } else {
            throw new IOException("Illegal len in DER object (" + len + ")");
         }
      }
   }

   public byte[] readSequenceAsByteArray() throws IOException {
      int type = this.readByte() & 255;
      if (type != 48) {
         throw new IOException("Expected DER Sequence, but found type " + type);
      } else {
         int len = this.readLength();
         if (len >= 0 && len <= this.available()) {
            byte[] b = this.readBytes(len);
            return b;
         } else {
            throw new IOException("Illegal len in DER object (" + len + ")");
         }
      }
   }

   public byte[] readOctetString() throws IOException {
      int type = this.readByte() & 255;
      if (type != 4) {
         throw new IOException("Expected DER Octetstring, but found type " + type);
      } else {
         int len = this.readLength();
         if (len >= 0 && len <= this.available()) {
            byte[] b = this.readBytes(len);
            return b;
         } else {
            throw new IOException("Illegal len in DER object (" + len + ")");
         }
      }
   }
}
