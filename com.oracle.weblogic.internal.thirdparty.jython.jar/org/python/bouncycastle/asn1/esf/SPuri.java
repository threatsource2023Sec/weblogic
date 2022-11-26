package org.python.bouncycastle.asn1.esf;

import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERIA5String;

public class SPuri {
   private DERIA5String uri;

   public static SPuri getInstance(Object var0) {
      if (var0 instanceof SPuri) {
         return (SPuri)var0;
      } else {
         return var0 instanceof DERIA5String ? new SPuri(DERIA5String.getInstance(var0)) : null;
      }
   }

   public SPuri(DERIA5String var1) {
      this.uri = var1;
   }

   public DERIA5String getUri() {
      return this.uri;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.uri.toASN1Primitive();
   }
}
