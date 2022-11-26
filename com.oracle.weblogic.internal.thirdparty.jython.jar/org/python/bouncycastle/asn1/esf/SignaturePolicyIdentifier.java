package org.python.bouncycastle.asn1.esf;

import org.python.bouncycastle.asn1.ASN1Null;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERNull;

public class SignaturePolicyIdentifier extends ASN1Object {
   private SignaturePolicyId signaturePolicyId;
   private boolean isSignaturePolicyImplied;

   public static SignaturePolicyIdentifier getInstance(Object var0) {
      if (var0 instanceof SignaturePolicyIdentifier) {
         return (SignaturePolicyIdentifier)var0;
      } else if (!(var0 instanceof ASN1Null) && !hasEncodedTagValue(var0, 5)) {
         return var0 != null ? new SignaturePolicyIdentifier(SignaturePolicyId.getInstance(var0)) : null;
      } else {
         return new SignaturePolicyIdentifier();
      }
   }

   public SignaturePolicyIdentifier() {
      this.isSignaturePolicyImplied = true;
   }

   public SignaturePolicyIdentifier(SignaturePolicyId var1) {
      this.signaturePolicyId = var1;
      this.isSignaturePolicyImplied = false;
   }

   public SignaturePolicyId getSignaturePolicyId() {
      return this.signaturePolicyId;
   }

   public boolean isSignaturePolicyImplied() {
      return this.isSignaturePolicyImplied;
   }

   public ASN1Primitive toASN1Primitive() {
      return (ASN1Primitive)(this.isSignaturePolicyImplied ? DERNull.INSTANCE : this.signaturePolicyId.toASN1Primitive());
   }
}
