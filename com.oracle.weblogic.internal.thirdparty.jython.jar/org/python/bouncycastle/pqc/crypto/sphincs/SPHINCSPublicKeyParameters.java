package org.python.bouncycastle.pqc.crypto.sphincs;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.util.Arrays;

public class SPHINCSPublicKeyParameters extends AsymmetricKeyParameter {
   private final byte[] keyData;

   public SPHINCSPublicKeyParameters(byte[] var1) {
      super(false);
      this.keyData = Arrays.clone(var1);
   }

   public byte[] getKeyData() {
      return Arrays.clone(this.keyData);
   }
}
