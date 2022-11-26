package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.BasicAgreement;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.util.Pack;

public class OldIESEngine extends IESEngine {
   public OldIESEngine(BasicAgreement var1, DerivationFunction var2, Mac var3) {
      super(var1, var2, var3);
   }

   public OldIESEngine(BasicAgreement var1, DerivationFunction var2, Mac var3, BufferedBlockCipher var4) {
      super(var1, var2, var3, var4);
   }

   protected byte[] getLengthTag(byte[] var1) {
      byte[] var2 = new byte[4];
      if (var1 != null) {
         Pack.intToBigEndian(var1.length * 8, var2, 0);
      }

      return var2;
   }
}
