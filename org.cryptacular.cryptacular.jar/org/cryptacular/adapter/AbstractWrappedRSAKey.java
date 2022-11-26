package org.cryptacular.adapter;

import java.math.BigInteger;
import org.bouncycastle.crypto.params.RSAKeyParameters;

public abstract class AbstractWrappedRSAKey extends AbstractWrappedKey {
   private static final String ALGORITHM = "RSA";

   public AbstractWrappedRSAKey(RSAKeyParameters wrappedKey) {
      super(wrappedKey);
   }

   public BigInteger getModulus() {
      return ((RSAKeyParameters)this.delegate).getModulus();
   }

   public String getAlgorithm() {
      return "RSA";
   }
}
