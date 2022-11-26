package org.python.bouncycastle.asn1.crmf;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.GeneralName;

public class SinglePubInfo extends ASN1Object {
   private ASN1Integer pubMethod;
   private GeneralName pubLocation;

   private SinglePubInfo(ASN1Sequence var1) {
      this.pubMethod = ASN1Integer.getInstance(var1.getObjectAt(0));
      if (var1.size() == 2) {
         this.pubLocation = GeneralName.getInstance(var1.getObjectAt(1));
      }

   }

   public static SinglePubInfo getInstance(Object var0) {
      if (var0 instanceof SinglePubInfo) {
         return (SinglePubInfo)var0;
      } else {
         return var0 != null ? new SinglePubInfo(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public GeneralName getPubLocation() {
      return this.pubLocation;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.pubMethod);
      if (this.pubLocation != null) {
         var1.add(this.pubLocation);
      }

      return new DERSequence(var1);
   }
}
