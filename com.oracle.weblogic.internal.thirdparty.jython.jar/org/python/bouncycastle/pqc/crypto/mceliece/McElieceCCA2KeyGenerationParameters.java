package org.python.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.KeyGenerationParameters;

public class McElieceCCA2KeyGenerationParameters extends KeyGenerationParameters {
   private McElieceCCA2Parameters params;

   public McElieceCCA2KeyGenerationParameters(SecureRandom var1, McElieceCCA2Parameters var2) {
      super(var1, 128);
      this.params = var2;
   }

   public McElieceCCA2Parameters getParameters() {
      return this.params;
   }
}
