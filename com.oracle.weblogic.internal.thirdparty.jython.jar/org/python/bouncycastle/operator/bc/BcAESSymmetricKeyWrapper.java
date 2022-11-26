package org.python.bouncycastle.operator.bc;

import org.python.bouncycastle.crypto.engines.AESWrapEngine;
import org.python.bouncycastle.crypto.params.KeyParameter;

public class BcAESSymmetricKeyWrapper extends BcSymmetricKeyWrapper {
   public BcAESSymmetricKeyWrapper(KeyParameter var1) {
      super(AESUtil.determineKeyEncAlg(var1), new AESWrapEngine(), var1);
   }
}
