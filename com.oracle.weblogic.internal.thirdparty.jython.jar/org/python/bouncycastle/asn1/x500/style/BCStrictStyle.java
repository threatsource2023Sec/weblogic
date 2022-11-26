package org.python.bouncycastle.asn1.x500.style;

import org.python.bouncycastle.asn1.x500.RDN;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x500.X500NameStyle;

public class BCStrictStyle extends BCStyle {
   public static final X500NameStyle INSTANCE = new BCStrictStyle();

   public boolean areEqual(X500Name var1, X500Name var2) {
      RDN[] var3 = var1.getRDNs();
      RDN[] var4 = var2.getRDNs();
      if (var3.length != var4.length) {
         return false;
      } else {
         for(int var5 = 0; var5 != var3.length; ++var5) {
            if (!this.rdnAreEqual(var3[var5], var4[var5])) {
               return false;
            }
         }

         return true;
      }
   }
}
