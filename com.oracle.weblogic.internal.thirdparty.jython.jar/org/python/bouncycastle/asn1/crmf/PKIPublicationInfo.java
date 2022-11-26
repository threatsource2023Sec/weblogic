package org.python.bouncycastle.asn1.crmf;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class PKIPublicationInfo extends ASN1Object {
   private ASN1Integer action;
   private ASN1Sequence pubInfos;

   private PKIPublicationInfo(ASN1Sequence var1) {
      this.action = ASN1Integer.getInstance(var1.getObjectAt(0));
      this.pubInfos = ASN1Sequence.getInstance(var1.getObjectAt(1));
   }

   public static PKIPublicationInfo getInstance(Object var0) {
      if (var0 instanceof PKIPublicationInfo) {
         return (PKIPublicationInfo)var0;
      } else {
         return var0 != null ? new PKIPublicationInfo(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1Integer getAction() {
      return this.action;
   }

   public SinglePubInfo[] getPubInfos() {
      if (this.pubInfos == null) {
         return null;
      } else {
         SinglePubInfo[] var1 = new SinglePubInfo[this.pubInfos.size()];

         for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = SinglePubInfo.getInstance(this.pubInfos.getObjectAt(var2));
         }

         return var1;
      }
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.action);
      var1.add(this.pubInfos);
      return new DERSequence(var1);
   }
}
