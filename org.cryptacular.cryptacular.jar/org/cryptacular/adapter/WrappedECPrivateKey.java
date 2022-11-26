package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;

public class WrappedECPrivateKey extends AbstractWrappedECKey implements ECPrivateKey {
   public WrappedECPrivateKey(ECPrivateKeyParameters wrappedKey) {
      super(wrappedKey);
   }

   public BigInteger getS() {
      return ((ECPrivateKeyParameters)this.delegate).getD();
   }
}
