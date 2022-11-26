package org.python.bouncycastle.asn1.misc;

import org.python.bouncycastle.asn1.DERIA5String;

public class NetscapeRevocationURL extends DERIA5String {
   public NetscapeRevocationURL(DERIA5String var1) {
      super(var1.getString());
   }

   public String toString() {
      return "NetscapeRevocationURL: " + this.getString();
   }
}
