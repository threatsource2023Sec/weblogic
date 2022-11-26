package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import org.bouncycastle.crypto.params.RSAKeyParameters;

public class WrappedRSAPublicKey extends AbstractWrappedRSAKey implements RSAPublicKey {
   public WrappedRSAPublicKey(RSAKeyParameters wrappedKey) {
      super(wrappedKey);
   }

   public BigInteger getPublicExponent() {
      return ((RSAKeyParameters)this.delegate).getExponent();
   }
}
