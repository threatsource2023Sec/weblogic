package org.apache.xml.security.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UnsyncBufferedOutputStream extends FilterOutputStream {
   protected byte[] buffer;
   protected int count;

   public UnsyncBufferedOutputStream(OutputStream out) {
      super(out);
      this.buffer = new byte[8192];
   }

   public UnsyncBufferedOutputStream(OutputStream out, int size) {
      super(out);
      if (size <= 0) {
         throw new IllegalArgumentException("size must be > 0");
      } else {
         this.buffer = new byte[size];
      }
   }

   public void flush() throws IOException {
      this.flushInternal();
      this.out.flush();
   }

   public void write(byte[] bytes, int offset, int length) throws IOException {
      if (length >= this.buffer.length) {
         this.flushInternal();
         this.out.write(bytes, offset, length);
      } else {
         if (length >= this.buffer.length - this.count) {
            this.flushInternal();
         }

         System.arraycopy(bytes, offset, this.buffer, this.count, length);
         this.count += length;
      }
   }

   public void write(int oneByte) throws IOException {
      if (this.count == this.buffer.length) {
         this.out.write(this.buffer, 0, this.count);
         this.count = 0;
      }

      this.buffer[this.count++] = (byte)oneByte;
   }

   private void flushInternal() throws IOException {
      if (this.count > 0) {
         this.out.write(this.buffer, 0, this.count);
         this.count = 0;
      }

   }
}
