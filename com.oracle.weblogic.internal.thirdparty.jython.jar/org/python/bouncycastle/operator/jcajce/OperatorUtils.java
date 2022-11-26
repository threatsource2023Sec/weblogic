package org.python.bouncycastle.operator.jcajce;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import org.python.bouncycastle.operator.GenericKey;

class OperatorUtils {
   static Key getJceKey(GenericKey var0) {
      if (var0.getRepresentation() instanceof Key) {
         return (Key)var0.getRepresentation();
      } else if (var0.getRepresentation() instanceof byte[]) {
         return new SecretKeySpec((byte[])((byte[])var0.getRepresentation()), "ENC");
      } else {
         throw new IllegalArgumentException("unknown generic key type");
      }
   }
}
