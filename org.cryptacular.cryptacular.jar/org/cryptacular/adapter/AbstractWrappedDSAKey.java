package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import org.bouncycastle.crypto.params.DSAKeyParameters;

public abstract class AbstractWrappedDSAKey extends AbstractWrappedKey {
   private static final String ALGORITHM = "DSA";

   public AbstractWrappedDSAKey(DSAKeyParameters wrappedKey) {
      super(wrappedKey);
   }

   public DSAParams getParams() {
      return new DSAParams() {
         public BigInteger getP() {
            return ((DSAKeyParameters)AbstractWrappedDSAKey.this.delegate).getParameters().getP();
         }

         public BigInteger getQ() {
            return ((DSAKeyParameters)AbstractWrappedDSAKey.this.delegate).getParameters().getQ();
         }

         public BigInteger getG() {
            return ((DSAKeyParameters)AbstractWrappedDSAKey.this.delegate).getParameters().getG();
         }
      };
   }

   public String getAlgorithm() {
      return "DSA";
   }
}
