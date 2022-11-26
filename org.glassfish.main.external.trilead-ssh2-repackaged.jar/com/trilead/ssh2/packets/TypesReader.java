package com.trilead.ssh2.packets;

import com.trilead.ssh2.util.Tokenizer;
import java.io.IOException;
import java.math.BigInteger;

public class TypesReader {
   byte[] arr;
   int pos = 0;
   int max = 0;

   public TypesReader(byte[] arr) {
      this.arr = arr;
      this.pos = 0;
      this.max = arr.length;
   }

   public TypesReader(byte[] arr, int off) {
      this.arr = arr;
      this.pos = off;
      this.max = arr.length;
      if (this.pos < 0 || this.pos > arr.length) {
         throw new IllegalArgumentException("Illegal offset.");
      }
   }

   public TypesReader(byte[] arr, int off, int len) {
      this.arr = arr;
      this.pos = off;
      this.max = off + len;
      if (this.pos >= 0 && this.pos <= arr.length) {
         if (this.max < 0 || this.max > arr.length) {
            throw new IllegalArgumentException("Illegal length.");
         }
      } else {
         throw new IllegalArgumentException("Illegal offset.");
      }
   }

   public int readByte() throws IOException {
      if (this.pos >= this.max) {
         throw new IOException("Packet too short.");
      } else {
         return this.arr[this.pos++] & 255;
      }
   }

   public byte[] readBytes(int len) throws IOException {
      if (this.pos + len > this.max) {
         throw new IOException("Packet too short.");
      } else {
         byte[] res = new byte[len];
         System.arraycopy(this.arr, this.pos, res, 0, len);
         this.pos += len;
         return res;
      }
   }

   public void readBytes(byte[] dst, int off, int len) throws IOException {
      if (this.pos + len > this.max) {
         throw new IOException("Packet too short.");
      } else {
         System.arraycopy(this.arr, this.pos, dst, off, len);
         this.pos += len;
      }
   }

   public boolean readBoolean() throws IOException {
      if (this.pos >= this.max) {
         throw new IOException("Packet too short.");
      } else {
         return this.arr[this.pos++] != 0;
      }
   }

   public int readUINT32() throws IOException {
      if (this.pos + 4 > this.max) {
         throw new IOException("Packet too short.");
      } else {
         return (this.arr[this.pos++] & 255) << 24 | (this.arr[this.pos++] & 255) << 16 | (this.arr[this.pos++] & 255) << 8 | this.arr[this.pos++] & 255;
      }
   }

   public long readUINT64() throws IOException {
      if (this.pos + 8 > this.max) {
         throw new IOException("Packet too short.");
      } else {
         long high = (long)((this.arr[this.pos++] & 255) << 24 | (this.arr[this.pos++] & 255) << 16 | (this.arr[this.pos++] & 255) << 8 | this.arr[this.pos++] & 255);
         long low = (long)((this.arr[this.pos++] & 255) << 24 | (this.arr[this.pos++] & 255) << 16 | (this.arr[this.pos++] & 255) << 8 | this.arr[this.pos++] & 255);
         return high << 32 | low & 4294967295L;
      }
   }

   public BigInteger readMPINT() throws IOException {
      byte[] raw = this.readByteString();
      BigInteger b;
      if (raw.length == 0) {
         b = BigInteger.ZERO;
      } else {
         b = new BigInteger(raw);
      }

      return b;
   }

   public byte[] readByteString() throws IOException {
      int len = this.readUINT32();
      if (len + this.pos > this.max) {
         throw new IOException("Malformed SSH byte string.");
      } else {
         byte[] res = new byte[len];
         System.arraycopy(this.arr, this.pos, res, 0, len);
         this.pos += len;
         return res;
      }
   }

   public String readString(String charsetName) throws IOException {
      int len = this.readUINT32();
      if (len + this.pos > this.max) {
         throw new IOException("Malformed SSH string.");
      } else {
         String res = charsetName == null ? new String(this.arr, this.pos, len) : new String(this.arr, this.pos, len, charsetName);
         this.pos += len;
         return res;
      }
   }

   public String readString() throws IOException {
      int len = this.readUINT32();
      if (len + this.pos > this.max) {
         throw new IOException("Malformed SSH string.");
      } else {
         String res = new String(this.arr, this.pos, len);
         this.pos += len;
         return res;
      }
   }

   public String[] readNameList() throws IOException {
      return Tokenizer.parseTokens(this.readString(), ',');
   }

   public int remain() {
      return this.max - this.pos;
   }
}
