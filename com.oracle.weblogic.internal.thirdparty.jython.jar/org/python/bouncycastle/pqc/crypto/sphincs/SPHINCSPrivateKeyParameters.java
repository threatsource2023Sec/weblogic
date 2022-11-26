package org.python.bouncycastle.pqc.crypto.sphincs;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.util.Arrays;

public class SPHINCSPrivateKeyParameters extends AsymmetricKeyParameter {
   private final byte[] keyData;

   public SPHINCSPrivateKeyParameters(byte[] var1) {
      super(true);
      this.keyData = Arrays.clone(var1);
   }

   public byte[] getKeyData() {
      return Arrays.clone(this.keyData);
   }
}
