package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.DSAPrivateKey;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;

public class WrappedDSAPrivateKey extends AbstractWrappedDSAKey implements DSAPrivateKey {
   public WrappedDSAPrivateKey(DSAPrivateKeyParameters parameters) {
      super(parameters);
   }

   public BigInteger getX() {
      return ((DSAPrivateKeyParameters)this.delegate).getX();
   }
}
