package org.python.bouncycastle.crypto.macs;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;

public class CMacWithIV extends CMac {
   public CMacWithIV(BlockCipher var1) {
      super(var1);
   }

   public CMacWithIV(BlockCipher var1, int var2) {
      super(var1, var2);
   }

   void validate(CipherParameters var1) {
   }
}
