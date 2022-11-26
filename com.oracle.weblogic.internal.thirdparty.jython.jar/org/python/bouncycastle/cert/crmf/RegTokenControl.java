package org.python.bouncycastle.cert.crmf;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.crmf.CRMFObjectIdentifiers;

public class RegTokenControl implements Control {
   private static final ASN1ObjectIdentifier type;
   private final DERUTF8String token;

   public RegTokenControl(DERUTF8String var1) {
      this.token = var1;
   }

   public RegTokenControl(String var1) {
      this.token = new DERUTF8String(var1);
   }

   public ASN1ObjectIdentifier getType() {
      return type;
   }

   public ASN1Encodable getValue() {
      return this.token;
   }

   static {
      type = CRMFObjectIdentifiers.id_regCtrl_regToken;
   }
}
