package com.rsa.certj.cert.extensions;

/** @deprecated */
public final class FreshestCRL extends CRLDistributionPoints {
   /** @deprecated */
   public FreshestCRL() {
      this.extensionTypeFlag = 46;
      this.criticality = false;
      this.setStandardOID(46);
      this.extensionTypeString = "FreshestCRL";
   }

   /** @deprecated */
   public Object clone() {
      FreshestCRL var1 = new FreshestCRL();
      this.copyValues(var1);
      return var1;
   }
}
