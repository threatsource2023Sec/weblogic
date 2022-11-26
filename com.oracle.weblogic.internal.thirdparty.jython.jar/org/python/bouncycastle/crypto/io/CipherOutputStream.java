package org.python.bouncycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.StreamCipher;
import org.python.bouncycastle.crypto.modes.AEADBlockCipher;

public class CipherOutputStream extends FilterOutputStream {
   private BufferedBlockCipher bufferedBlockCipher;
   private StreamCipher streamCipher;
   private AEADBlockCipher aeadBlockCipher;
   private final byte[] oneByte = new byte[1];
   private byte[] buf;

   public CipherOutputStream(OutputStream var1, BufferedBlockCipher var2) {
      super(var1);
      this.bufferedBlockCipher = var2;
   }

   public CipherOutputStream(OutputStream var1, StreamCipher var2) {
      super(var1);
      this.streamCipher = var2;
   }

   public CipherOutputStream(OutputStream var1, AEADBlockCipher var2) {
      super(var1);
      this.aeadBlockCipher = var2;
   }

   public void write(int var1) throws IOException {
      this.oneByte[0] = (byte)var1;
      if (this.streamCipher != null) {
         this.out.write(this.streamCipher.returnByte((byte)var1));
      } else {
         this.write(this.oneByte, 0, 1);
      }

   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.ensureCapacity(var3, false);
      int var4;
      if (this.bufferedBlockCipher != null) {
         var4 = this.bufferedBlockCipher.processBytes(var1, var2, var3, this.buf, 0);
         if (var4 != 0) {
            this.out.write(this.buf, 0, var4);
         }
      } else if (this.aeadBlockCipher != null) {
         var4 = this.aeadBlockCipher.processBytes(var1, var2, var3, this.buf, 0);
         if (var4 != 0) {
            this.out.write(this.buf, 0, var4);
         }
      } else {
         this.streamCipher.processBytes(var1, var2, var3, this.buf, 0);
         this.out.write(this.buf, 0, var3);
      }

   }

   private void ensureCapacity(int var1, boolean var2) {
      int var3 = var1;
      if (var2) {
         if (this.bufferedBlockCipher != null) {
            var3 = this.bufferedBlockCipher.getOutputSize(var1);
         } else if (this.aeadBlockCipher != null) {
            var3 = this.aeadBlockCipher.getOutputSize(var1);
         }
      } else if (this.bufferedBlockCipher != null) {
         var3 = this.bufferedBlockCipher.getUpdateOutputSize(var1);
      } else if (this.aeadBlockCipher != null) {
         var3 = this.aeadBlockCipher.getUpdateOutputSize(var1);
      }

      if (this.buf == null || this.buf.length < var3) {
         this.buf = new byte[var3];
      }

   }

   public void flush() throws IOException {
      this.out.flush();
   }

   public void close() throws IOException {
      this.ensureCapacity(0, true);
      Object var1 = null;

      try {
         int var2;
         if (this.bufferedBlockCipher != null) {
            var2 = this.bufferedBlockCipher.doFinal(this.buf, 0);
            if (var2 != 0) {
               this.out.write(this.buf, 0, var2);
            }
         } else if (this.aeadBlockCipher != null) {
            var2 = this.aeadBlockCipher.doFinal(this.buf, 0);
            if (var2 != 0) {
               this.out.write(this.buf, 0, var2);
            }
         } else if (this.streamCipher != null) {
            this.streamCipher.reset();
         }
      } catch (InvalidCipherTextException var3) {
         var1 = new InvalidCipherTextIOException("Error finalising cipher data", var3);
      } catch (Exception var4) {
         var1 = new CipherIOException("Error closing stream: ", var4);
      }

      try {
         this.flush();
         this.out.close();
      } catch (IOException var5) {
         if (var1 == null) {
            var1 = var5;
         }
      }

      if (var1 != null) {
         throw var1;
      }
   }
}
