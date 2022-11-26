package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;

class TlsOutputStream extends OutputStream {
   private byte[] buf = new byte[1];
   private TlsProtocol handler;

   TlsOutputStream(TlsProtocol var1) {
      this.handler = var1;
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.handler.writeData(var1, var2, var3);
   }

   public void write(int var1) throws IOException {
      this.buf[0] = (byte)var1;
      this.write(this.buf, 0, 1);
   }

   public void close() throws IOException {
      this.handler.close();
   }

   public void flush() throws IOException {
      this.handler.flush();
   }
}
