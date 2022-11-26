package org.python.bouncycastle.crypto.params;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.util.Arrays;

public class TweakableBlockCipherParameters implements CipherParameters {
   private final byte[] tweak;
   private final KeyParameter key;

   public TweakableBlockCipherParameters(KeyParameter var1, byte[] var2) {
      this.key = var1;
      this.tweak = Arrays.clone(var2);
   }

   public KeyParameter getKey() {
      return this.key;
   }

   public byte[] getTweak() {
      return this.tweak;
   }
}
