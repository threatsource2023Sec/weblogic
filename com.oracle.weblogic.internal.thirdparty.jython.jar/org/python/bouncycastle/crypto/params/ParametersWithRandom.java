package org.python.bouncycastle.crypto.params;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;

public class ParametersWithRandom implements CipherParameters {
   private SecureRandom random;
   private CipherParameters parameters;

   public ParametersWithRandom(CipherParameters var1, SecureRandom var2) {
      this.random = var2;
      this.parameters = var1;
   }

   public ParametersWithRandom(CipherParameters var1) {
      this(var1, new SecureRandom());
   }

   public SecureRandom getRandom() {
      return this.random;
   }

   public CipherParameters getParameters() {
      return this.parameters;
   }
}
