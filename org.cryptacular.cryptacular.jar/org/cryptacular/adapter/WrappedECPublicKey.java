package org.cryptacular.adapter;

import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

public class WrappedECPublicKey extends AbstractWrappedECKey implements ECPublicKey {
   public WrappedECPublicKey(ECPublicKeyParameters wrappedKey) {
      super(wrappedKey);
   }

   public ECPoint getW() {
      return new ECPoint(((ECPublicKeyParameters)this.delegate).getQ().normalize().getXCoord().toBigInteger(), ((ECPublicKeyParameters)this.delegate).getQ().normalize().getYCoord().toBigInteger());
   }
}
