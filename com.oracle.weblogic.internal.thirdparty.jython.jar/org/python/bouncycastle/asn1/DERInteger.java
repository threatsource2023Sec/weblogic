package org.python.bouncycastle.asn1;

import java.math.BigInteger;

/** @deprecated */
public class DERInteger extends ASN1Integer {
   public DERInteger(byte[] var1) {
      super(var1, true);
   }

   public DERInteger(BigInteger var1) {
      super(var1);
   }

   public DERInteger(long var1) {
      super(var1);
   }
}
