package com.trilead.ssh2.crypto.cipher;

import java.io.IOException;
import java.io.OutputStream;

public class CipherOutputStream {
   BlockCipher currentCipher;
   OutputStream bo;
   byte[] buffer;
   byte[] enc;
   int blockSize;
   int pos;
   final int BUFF_SIZE = 2048;
   byte[] out_buffer = new byte[2048];
   int out_buffer_pos = 0;

   public CipherOutputStream(BlockCipher tc, OutputStream bo) {
      this.bo = bo;
      this.changeCipher(tc);
   }

   private void internal_write(byte[] src, int off, int len) throws IOException {
      while(len > 0) {
         int space = 2048 - this.out_buffer_pos;
         int copy = len > space ? space : len;
         System.arraycopy(src, off, this.out_buffer, this.out_buffer_pos, copy);
         off += copy;
         this.out_buffer_pos += copy;
         len -= copy;
         if (this.out_buffer_pos >= 2048) {
            this.bo.write(this.out_buffer, 0, 2048);
            this.out_buffer_pos = 0;
         }
      }

   }

   private void internal_write(int b) throws IOException {
      this.out_buffer[this.out_buffer_pos++] = (byte)b;
      if (this.out_buffer_pos >= 2048) {
         this.bo.write(this.out_buffer, 0, 2048);
         this.out_buffer_pos = 0;
      }

   }

   public void flush() throws IOException {
      if (this.pos != 0) {
         throw new IOException("FATAL: cannot flush since crypto buffer is not aligned.");
      } else {
         if (this.out_buffer_pos > 0) {
            this.bo.write(this.out_buffer, 0, this.out_buffer_pos);
            this.out_buffer_pos = 0;
         }

         this.bo.flush();
      }
   }

   public void changeCipher(BlockCipher bc) {
      this.currentCipher = bc;
      this.blockSize = bc.getBlockSize();
      this.buffer = new byte[this.blockSize];
      this.enc = new byte[this.blockSize];
      this.pos = 0;
   }

   private void writeBlock() throws IOException {
      try {
         this.currentCipher.transformBlock(this.buffer, 0, this.enc, 0);
      } catch (Exception var2) {
         throw (IOException)(new IOException("Error while decrypting block.")).initCause(var2);
      }

      this.internal_write(this.enc, 0, this.blockSize);
      this.pos = 0;
   }

   public void write(byte[] src, int off, int len) throws IOException {
      while(len > 0) {
         int avail = this.blockSize - this.pos;
         int copy = Math.min(avail, len);
         System.arraycopy(src, off, this.buffer, this.pos, copy);
         this.pos += copy;
         off += copy;
         len -= copy;
         if (this.pos >= this.blockSize) {
            this.writeBlock();
         }
      }

   }

   public void write(int b) throws IOException {
      this.buffer[this.pos++] = (byte)b;
      if (this.pos >= this.blockSize) {
         this.writeBlock();
      }

   }

   public void writePlain(int b) throws IOException {
      if (this.pos != 0) {
         throw new IOException("Cannot write plain since crypto buffer is not aligned.");
      } else {
         this.internal_write(b);
      }
   }

   public void writePlain(byte[] b, int off, int len) throws IOException {
      if (this.pos != 0) {
         throw new IOException("Cannot write plain since crypto buffer is not aligned.");
      } else {
         this.internal_write(b, off, len);
      }
   }
}
