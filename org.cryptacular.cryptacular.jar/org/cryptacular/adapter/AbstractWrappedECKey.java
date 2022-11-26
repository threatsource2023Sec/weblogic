package org.cryptacular.adapter;

import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;

public abstract class AbstractWrappedECKey extends AbstractWrappedKey {
   private static final String ALGORITHM = "EC";

   public AbstractWrappedECKey(ECKeyParameters wrappedKey) {
      super(wrappedKey);
   }

   public ECParameterSpec getParams() {
      ECDomainParameters params = ((ECKeyParameters)this.delegate).getParameters();
      return new ECParameterSpec(EC5Util.convertCurve(params.getCurve(), params.getSeed()), new ECPoint(params.getG().normalize().getXCoord().toBigInteger(), params.getG().normalize().getYCoord().toBigInteger()), params.getN(), params.getH().intValue());
   }

   public String getAlgorithm() {
      return "EC";
   }
}
