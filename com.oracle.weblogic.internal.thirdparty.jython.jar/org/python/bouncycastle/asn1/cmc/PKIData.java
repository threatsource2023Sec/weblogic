package org.python.bouncycastle.asn1.cmc;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class PKIData extends ASN1Object {
   private final TaggedAttribute[] controlSequence;
   private final TaggedRequest[] reqSequence;
   private final TaggedContentInfo[] cmsSequence;
   private final OtherMsg[] otherMsgSequence;

   public PKIData(TaggedAttribute[] var1, TaggedRequest[] var2, TaggedContentInfo[] var3, OtherMsg[] var4) {
      this.controlSequence = var1;
      this.reqSequence = var2;
      this.cmsSequence = var3;
      this.otherMsgSequence = var4;
   }

   private PKIData(ASN1Sequence var1) {
      if (var1.size() != 4) {
         throw new IllegalArgumentException("Sequence not 4 elements.");
      } else {
         ASN1Sequence var2 = (ASN1Sequence)var1.getObjectAt(0);
         this.controlSequence = new TaggedAttribute[var2.size()];

         int var3;
         for(var3 = 0; var3 < this.controlSequence.length; ++var3) {
            this.controlSequence[var3] = TaggedAttribute.getInstance(var2.getObjectAt(var3));
         }

         var2 = (ASN1Sequence)var1.getObjectAt(1);
         this.reqSequence = new TaggedRequest[var2.size()];

         for(var3 = 0; var3 < this.reqSequence.length; ++var3) {
            this.reqSequence[var3] = TaggedRequest.getInstance(var2.getObjectAt(var3));
         }

         var2 = (ASN1Sequence)var1.getObjectAt(2);
         this.cmsSequence = new TaggedContentInfo[var2.size()];

         for(var3 = 0; var3 < this.cmsSequence.length; ++var3) {
            this.cmsSequence[var3] = TaggedContentInfo.getInstance(var2.getObjectAt(var3));
         }

         var2 = (ASN1Sequence)var1.getObjectAt(3);
         this.otherMsgSequence = new OtherMsg[var2.size()];

         for(var3 = 0; var3 < this.otherMsgSequence.length; ++var3) {
            this.otherMsgSequence[var3] = OtherMsg.getInstance(var2.getObjectAt(var3));
         }

      }
   }

   public static PKIData getInstance(Object var0) {
      if (var0 instanceof PKIData) {
         return (PKIData)var0;
      } else {
         return var0 != null ? new PKIData(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1Primitive toASN1Primitive() {
      return new DERSequence(new ASN1Encodable[]{new DERSequence(this.controlSequence), new DERSequence(this.reqSequence), new DERSequence(this.cmsSequence), new DERSequence(this.otherMsgSequence)});
   }

   public TaggedAttribute[] getControlSequence() {
      return this.controlSequence;
   }

   public TaggedRequest[] getReqSequence() {
      return this.reqSequence;
   }

   public TaggedContentInfo[] getCmsSequence() {
      return this.cmsSequence;
   }

   public OtherMsg[] getOtherMsgSequence() {
      return this.otherMsgSequence;
   }
}
