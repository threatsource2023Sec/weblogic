package org.python.bouncycastle.pqc.crypto.gmss;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class GMSSKeyParameters extends AsymmetricKeyParameter {
   private GMSSParameters params;

   public GMSSKeyParameters(boolean var1, GMSSParameters var2) {
      super(var1);
      this.params = var2;
   }

   public GMSSParameters getParameters() {
      return this.params;
   }
}
