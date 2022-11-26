package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class WrappedRSAPrivateCrtKey extends AbstractWrappedRSAKey implements RSAPrivateCrtKey {
   public WrappedRSAPrivateCrtKey(RSAPrivateCrtKeyParameters parameters) {
      super(parameters);
   }

   public BigInteger getPublicExponent() {
      return ((RSAPrivateCrtKeyParameters)this.delegate).getPublicExponent();
   }

   public BigInteger getPrimeP() {
      return ((RSAPrivateCrtKeyParameters)this.delegate).getP();
   }

   public BigInteger getPrimeQ() {
      return ((RSAPrivateCrtKeyParameters)this.delegate).getQ();
   }

   public BigInteger getPrimeExponentP() {
      return ((RSAPrivateCrtKeyParameters)this.delegate).getDP();
   }

   public BigInteger getPrimeExponentQ() {
      return ((RSAPrivateCrtKeyParameters)this.delegate).getDQ();
   }

   public BigInteger getCrtCoefficient() {
      return ((RSAPrivateCrtKeyParameters)this.delegate).getQInv();
   }

   public BigInteger getPrivateExponent() {
      return ((RSAPrivateCrtKeyParameters)this.delegate).getExponent();
   }
}
