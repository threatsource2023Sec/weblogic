package org.python.bouncycastle.est;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.python.bouncycastle.util.encoders.Base64;

class CTEBase64InputStream extends InputStream {
   protected final InputStream src;
   protected final byte[] rawBuf = new byte[1024];
   protected final byte[] data = new byte[768];
   protected final OutputStream dataOutputStream;
   protected final Long max;
   protected int rp;
   protected int wp;
   protected boolean end;
   protected long read;

   public CTEBase64InputStream(InputStream var1, Long var2) {
      this.src = var1;
      this.dataOutputStream = new OutputStream() {
         public void write(int var1) throws IOException {
            CTEBase64InputStream.this.data[CTEBase64InputStream.this.wp++] = (byte)var1;
         }
      };
      this.max = var2;
   }

   protected int pullFromSrc() throws IOException {
      if (this.read >= this.max) {
         return -1;
      } else {
         boolean var1 = false;
         int var2 = 0;

         int var5;
         do {
            var5 = this.src.read();
            if (var5 < 33 && var5 != 13 && var5 != 10) {
               if (var5 >= 0) {
                  ++this.read;
               }
            } else {
               if (var2 >= this.rawBuf.length) {
                  throw new IOException("Content Transfer Encoding, base64 line length > 1024");
               }

               this.rawBuf[var2++] = (byte)var5;
               ++this.read;
            }
         } while(var5 > -1 && var2 < this.rawBuf.length && var5 != 10 && this.read < this.max);

         if (var2 > 0) {
            try {
               Base64.decode(this.rawBuf, 0, var2, this.dataOutputStream);
            } catch (Exception var4) {
               throw new IOException("Decode Base64 Content-Transfer-Encoding: " + var4);
            }
         } else if (var5 == -1) {
            return -1;
         }

         return this.wp;
      }
   }

   public int read() throws IOException {
      if (this.rp == this.wp) {
         this.rp = 0;
         this.wp = 0;
         int var1 = this.pullFromSrc();
         if (var1 == -1) {
            return var1;
         }
      }

      return this.data[this.rp++] & 255;
   }

   public void close() throws IOException {
      this.src.close();
   }
}
