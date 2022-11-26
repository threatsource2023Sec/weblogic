package org.python.bouncycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.crypto.Digest;

public class DigestInputStream extends FilterInputStream {
   protected Digest digest;

   public DigestInputStream(InputStream var1, Digest var2) {
      super(var1);
      this.digest = var2;
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if (var1 >= 0) {
         this.digest.update((byte)var1);
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.in.read(var1, var2, var3);
      if (var4 > 0) {
         this.digest.update(var1, var2, var4);
      }

      return var4;
   }

   public Digest getDigest() {
      return this.digest;
   }
}
