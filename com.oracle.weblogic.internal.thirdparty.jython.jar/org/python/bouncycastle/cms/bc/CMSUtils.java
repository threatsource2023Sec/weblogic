package org.python.bouncycastle.cms.bc;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.operator.GenericKey;

class CMSUtils {
   static CipherParameters getBcKey(GenericKey var0) {
      if (var0.getRepresentation() instanceof CipherParameters) {
         return (CipherParameters)var0.getRepresentation();
      } else if (var0.getRepresentation() instanceof byte[]) {
         return new KeyParameter((byte[])((byte[])var0.getRepresentation()));
      } else {
         throw new IllegalArgumentException("unknown generic key type");
      }
   }
}
