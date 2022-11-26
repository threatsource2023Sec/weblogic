package org.python.bouncycastle.operator.bc;

import org.python.bouncycastle.crypto.engines.AESWrapEngine;
import org.python.bouncycastle.crypto.params.KeyParameter;

public class BcAESSymmetricKeyUnwrapper extends BcSymmetricKeyUnwrapper {
   public BcAESSymmetricKeyUnwrapper(KeyParameter var1) {
      super(AESUtil.determineKeyEncAlg(var1), new AESWrapEngine(), var1);
   }
}
