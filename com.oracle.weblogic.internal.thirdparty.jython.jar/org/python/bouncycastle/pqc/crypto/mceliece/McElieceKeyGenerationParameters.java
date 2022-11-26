package org.python.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.KeyGenerationParameters;

public class McElieceKeyGenerationParameters extends KeyGenerationParameters {
   private McElieceParameters params;

   public McElieceKeyGenerationParameters(SecureRandom var1, McElieceParameters var2) {
      super(var1, 256);
      this.params = var2;
   }

   public McElieceParameters getParameters() {
      return this.params;
   }
}
