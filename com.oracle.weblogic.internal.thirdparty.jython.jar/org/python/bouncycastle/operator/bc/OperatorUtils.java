package org.python.bouncycastle.operator.bc;

import java.security.Key;
import org.python.bouncycastle.operator.GenericKey;

class OperatorUtils {
   static byte[] getKeyBytes(GenericKey var0) {
      if (var0.getRepresentation() instanceof Key) {
         return ((Key)var0.getRepresentation()).getEncoded();
      } else if (var0.getRepresentation() instanceof byte[]) {
         return (byte[])((byte[])var0.getRepresentation());
      } else {
         throw new IllegalArgumentException("unknown generic key type");
      }
   }
}
