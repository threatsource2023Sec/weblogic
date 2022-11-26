package org.python.bouncycastle.crypto.params;

import org.python.bouncycastle.crypto.CipherParameters;

public class KeyParameter implements CipherParameters {
   private byte[] key;

   public KeyParameter(byte[] var1) {
      this(var1, 0, var1.length);
   }

   public KeyParameter(byte[] var1, int var2, int var3) {
      this.key = new byte[var3];
      System.arraycopy(var1, var2, this.key, 0, var3);
   }

   public byte[] getKey() {
      return this.key;
   }
}
