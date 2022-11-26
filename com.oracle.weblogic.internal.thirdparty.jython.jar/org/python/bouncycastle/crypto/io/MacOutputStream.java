package org.python.bouncycastle.crypto.io;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.crypto.Mac;

public class MacOutputStream extends OutputStream {
   protected Mac mac;

   public MacOutputStream(Mac var1) {
      this.mac = var1;
   }

   public void write(int var1) throws IOException {
      this.mac.update((byte)var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.mac.update(var1, var2, var3);
   }

   public byte[] getMac() {
      byte[] var1 = new byte[this.mac.getMacSize()];
      this.mac.doFinal(var1, 0);
      return var1;
   }
}
