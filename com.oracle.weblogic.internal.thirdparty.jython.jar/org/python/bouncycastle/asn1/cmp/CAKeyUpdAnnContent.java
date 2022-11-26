package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class CAKeyUpdAnnContent extends ASN1Object {
   private CMPCertificate oldWithNew;
   private CMPCertificate newWithOld;
   private CMPCertificate newWithNew;

   private CAKeyUpdAnnContent(ASN1Sequence var1) {
      this.oldWithNew = CMPCertificate.getInstance(var1.getObjectAt(0));
      this.newWithOld = CMPCertificate.getInstance(var1.getObjectAt(1));
      this.newWithNew = CMPCertificate.getInstance(var1.getObjectAt(2));
   }

   public static CAKeyUpdAnnContent getInstance(Object var0) {
      if (var0 instanceof CAKeyUpdAnnContent) {
         return (CAKeyUpdAnnContent)var0;
      } else {
         return var0 != null ? new CAKeyUpdAnnContent(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public CAKeyUpdAnnContent(CMPCertificate var1, CMPCertificate var2, CMPCertificate var3) {
      this.oldWithNew = var1;
      this.newWithOld = var2;
      this.newWithNew = var3;
   }

   public CMPCertificate getOldWithNew() {
      return this.oldWithNew;
   }

   public CMPCertificate getNewWithOld() {
      return this.newWithOld;
   }

   public CMPCertificate getNewWithNew() {
      return this.newWithNew;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.oldWithNew);
      var1.add(this.newWithOld);
      var1.add(this.newWithNew);
      return new DERSequence(var1);
   }
}
