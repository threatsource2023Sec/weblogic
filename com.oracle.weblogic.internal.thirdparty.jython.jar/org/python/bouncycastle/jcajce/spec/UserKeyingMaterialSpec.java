package org.python.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.util.Arrays;

public class UserKeyingMaterialSpec implements AlgorithmParameterSpec {
   private final byte[] userKeyingMaterial;

   public UserKeyingMaterialSpec(byte[] var1) {
      this.userKeyingMaterial = Arrays.clone(var1);
   }

   public byte[] getUserKeyingMaterial() {
      return Arrays.clone(this.userKeyingMaterial);
   }
}
