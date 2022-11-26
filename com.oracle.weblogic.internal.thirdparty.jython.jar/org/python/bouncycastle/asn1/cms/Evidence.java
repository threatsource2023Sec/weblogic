package org.python.bouncycastle.asn1.cms;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERTaggedObject;

public class Evidence extends ASN1Object implements ASN1Choice {
   private TimeStampTokenEvidence tstEvidence;

   public Evidence(TimeStampTokenEvidence var1) {
      this.tstEvidence = var1;
   }

   private Evidence(ASN1TaggedObject var1) {
      if (var1.getTagNo() == 0) {
         this.tstEvidence = TimeStampTokenEvidence.getInstance(var1, false);
      }

   }

   public static Evidence getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof Evidence)) {
         if (var0 instanceof ASN1TaggedObject) {
            return new Evidence(ASN1TaggedObject.getInstance(var0));
         } else {
            throw new IllegalArgumentException("unknown object in getInstance");
         }
      } else {
         return (Evidence)var0;
      }
   }

   public TimeStampTokenEvidence getTstEvidence() {
      return this.tstEvidence;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.tstEvidence != null ? new DERTaggedObject(false, 0, this.tstEvidence) : null;
   }
}
