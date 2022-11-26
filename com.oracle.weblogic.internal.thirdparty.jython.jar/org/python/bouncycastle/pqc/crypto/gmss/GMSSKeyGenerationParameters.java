package org.python.bouncycastle.pqc.crypto.gmss;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.KeyGenerationParameters;

public class GMSSKeyGenerationParameters extends KeyGenerationParameters {
   private GMSSParameters params;

   public GMSSKeyGenerationParameters(SecureRandom var1, GMSSParameters var2) {
      super(var1, 1);
      this.params = var2;
   }

   public GMSSParameters getParameters() {
      return this.params;
   }
}
