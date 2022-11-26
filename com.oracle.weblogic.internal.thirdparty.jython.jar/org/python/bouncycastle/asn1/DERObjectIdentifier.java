package org.python.bouncycastle.asn1;

/** @deprecated */
public class DERObjectIdentifier extends ASN1ObjectIdentifier {
   public DERObjectIdentifier(String var1) {
      super(var1);
   }

   DERObjectIdentifier(byte[] var1) {
      super(var1);
   }

   DERObjectIdentifier(ASN1ObjectIdentifier var1, String var2) {
      super(var1, var2);
   }
}
