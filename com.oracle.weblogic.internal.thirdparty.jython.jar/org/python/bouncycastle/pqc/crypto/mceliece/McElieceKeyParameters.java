package org.python.bouncycastle.pqc.crypto.mceliece;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class McElieceKeyParameters extends AsymmetricKeyParameter {
   private McElieceParameters params;

   public McElieceKeyParameters(boolean var1, McElieceParameters var2) {
      super(var1);
      this.params = var2;
   }

   public McElieceParameters getParameters() {
      return this.params;
   }
}
