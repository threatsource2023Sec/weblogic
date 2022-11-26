package org.python.bouncycastle.pqc.crypto.newhope;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.util.Arrays;

public class NHPrivateKeyParameters extends AsymmetricKeyParameter {
   final short[] secData;

   public NHPrivateKeyParameters(short[] var1) {
      super(true);
      this.secData = Arrays.clone(var1);
   }

   public short[] getSecData() {
      return Arrays.clone(this.secData);
   }
}
