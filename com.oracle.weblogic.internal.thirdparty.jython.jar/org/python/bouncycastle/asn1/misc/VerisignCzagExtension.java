package org.python.bouncycastle.asn1.misc;

import org.python.bouncycastle.asn1.DERIA5String;

public class VerisignCzagExtension extends DERIA5String {
   public VerisignCzagExtension(DERIA5String var1) {
      super(var1.getString());
   }

   public String toString() {
      return "VerisignCzagExtension: " + this.getString();
   }
}
