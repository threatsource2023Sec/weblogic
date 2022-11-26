package org.python.bouncycastle.asn1.esf;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class SigPolicyQualifiers extends ASN1Object {
   ASN1Sequence qualifiers;

   public static SigPolicyQualifiers getInstance(Object var0) {
      if (var0 instanceof SigPolicyQualifiers) {
         return (SigPolicyQualifiers)var0;
      } else {
         return var0 instanceof ASN1Sequence ? new SigPolicyQualifiers(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private SigPolicyQualifiers(ASN1Sequence var1) {
      this.qualifiers = var1;
   }

   public SigPolicyQualifiers(SigPolicyQualifierInfo[] var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2.add(var1[var3]);
      }

      this.qualifiers = new DERSequence(var2);
   }

   public int size() {
      return this.qualifiers.size();
   }

   public SigPolicyQualifierInfo getInfoAt(int var1) {
      return SigPolicyQualifierInfo.getInstance(this.qualifiers.getObjectAt(var1));
   }

   public ASN1Primitive toASN1Primitive() {
      return this.qualifiers;
   }
}
