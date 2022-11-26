package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;

public class ByteQueueOutputStream extends OutputStream {
   private ByteQueue buffer = new ByteQueue();

   public ByteQueue getBuffer() {
      return this.buffer;
   }

   public void write(int var1) throws IOException {
      this.buffer.addData(new byte[]{(byte)var1}, 0, 1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.buffer.addData(var1, var2, var3);
   }
}
