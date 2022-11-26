package org.python.bouncycastle.jcajce.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import org.python.bouncycastle.crypto.io.InvalidCipherTextIOException;

public class CipherInputStream extends FilterInputStream {
   private final Cipher cipher;
   private final byte[] inputBuffer = new byte[512];
   private boolean finalized = false;
   private byte[] buf;
   private int maxBuf;
   private int bufOff;

   public CipherInputStream(InputStream var1, Cipher var2) {
      super(var1);
      this.cipher = var2;
   }

   private int nextChunk() throws IOException {
      if (this.finalized) {
         return -1;
      } else {
         this.bufOff = 0;
         this.maxBuf = 0;

         while(this.maxBuf == 0) {
            int var1 = this.in.read(this.inputBuffer);
            if (var1 == -1) {
               this.buf = this.finaliseCipher();
               if (this.buf != null && this.buf.length != 0) {
                  this.maxBuf = this.buf.length;
                  return this.maxBuf;
               }

               return -1;
            }

            this.buf = this.cipher.update(this.inputBuffer, 0, var1);
            if (this.buf != null) {
               this.maxBuf = this.buf.length;
            }
         }

         return this.maxBuf;
      }
   }

   private byte[] finaliseCipher() throws InvalidCipherTextIOException {
      try {
         this.finalized = true;
         return this.cipher.doFinal();
      } catch (GeneralSecurityException var2) {
         throw new InvalidCipherTextIOException("Error finalising cipher", var2);
      }
   }

   public int read() throws IOException {
      return this.bufOff >= this.maxBuf && this.nextChunk() < 0 ? -1 : this.buf[this.bufOff++] & 255;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this.bufOff >= this.maxBuf && this.nextChunk() < 0) {
         return -1;
      } else {
         int var4 = Math.min(var3, this.available());
         System.arraycopy(this.buf, this.bufOff, var1, var2, var4);
         this.bufOff += var4;
         return var4;
      }
   }

   public long skip(long var1) throws IOException {
      if (var1 <= 0L) {
         return 0L;
      } else {
         int var3 = (int)Math.min(var1, (long)this.available());
         this.bufOff += var3;
         return (long)var3;
      }
   }

   public int available() throws IOException {
      return this.maxBuf - this.bufOff;
   }

   public void close() throws IOException {
      try {
         this.in.close();
      } finally {
         if (!this.finalized) {
            this.finaliseCipher();
         }

      }

      this.maxBuf = this.bufOff = 0;
   }

   public void mark(int var1) {
   }

   public void reset() throws IOException {
   }

   public boolean markSupported() {
      return false;
   }
}
