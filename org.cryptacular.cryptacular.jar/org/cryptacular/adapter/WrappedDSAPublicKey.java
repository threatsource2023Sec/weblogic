package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.DSAPublicKey;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;

public class WrappedDSAPublicKey extends AbstractWrappedDSAKey implements DSAPublicKey {
   public WrappedDSAPublicKey(DSAPublicKeyParameters wrappedKey) {
      super(wrappedKey);
   }

   public BigInteger getY() {
      return ((DSAPublicKeyParameters)this.delegate).getY();
   }
}
