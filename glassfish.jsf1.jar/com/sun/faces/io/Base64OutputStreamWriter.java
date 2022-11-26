package com.sun.faces.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class Base64OutputStreamWriter extends OutputStream {
   private byte[] buf;
   private char[] chars;
   private int count;
   private int encCount;
   private int totalCharsWritten;
   private Writer writer;
   private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

   public Base64OutputStreamWriter(int size, Writer writer) {
      if (size < 0) {
         throw new IllegalArgumentException("Negative initial size: " + size);
      } else {
         this.buf = new byte[size];
         this.chars = new char[size];
         this.totalCharsWritten = 0;
         this.writer = writer;
      }
   }

   public void write(int b) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
         if (len != 0) {
            if (this.count + len > this.buf.length) {
               this.encodePendingBytes(false);
            }

            System.arraycopy(b, off, this.buf, this.count, len);
            this.count += len;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void close() throws IOException {
   }

   public void finish() throws IOException {
      this.encodePendingBytes(true);
   }

   public int getTotalCharsWritten() {
      return this.totalCharsWritten;
   }

   private void encodePendingBytes(boolean pad) throws IOException {
      int eLen = this.count / 3 * 3;

      int left;
      int i;
      for(left = 0; left < eLen; this.chars[this.encCount++] = CA[i & 63]) {
         i = (this.buf[left++] & 255) << 16 | (this.buf[left++] & 255) << 8 | this.buf[left++] & 255;
         if (this.encCount + 4 > this.chars.length) {
            this.drainCharBuffer();
         }

         this.chars[this.encCount++] = CA[i >>> 18 & 63];
         this.chars[this.encCount++] = CA[i >>> 12 & 63];
         this.chars[this.encCount++] = CA[i >>> 6 & 63];
      }

      left = this.count - eLen;
      if (!pad) {
         System.arraycopy(this.buf, eLen, this.buf, 0, left);
         this.count = left;
      } else {
         this.drainCharBuffer();
         if (left > 0) {
            i = (this.buf[eLen] & 255) << 10 | (left == 2 ? (this.buf[this.count - 1] & 255) << 2 : 0);
            this.writer.write(CA[i >> 12]);
            this.writer.write(CA[i >>> 6 & 63]);
            this.writer.write(left == 2 ? CA[i & 63] : 61);
            this.writer.write(61);
         }
      }

   }

   private void drainCharBuffer() throws IOException {
      this.writer.write(this.chars, 0, this.encCount);
      this.totalCharsWritten += this.encCount;
      this.encCount = 0;
   }
}
