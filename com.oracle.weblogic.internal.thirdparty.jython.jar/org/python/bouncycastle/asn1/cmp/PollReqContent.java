package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class PollReqContent extends ASN1Object {
   private ASN1Sequence content;

   private PollReqContent(ASN1Sequence var1) {
      this.content = var1;
   }

   public static PollReqContent getInstance(Object var0) {
      if (var0 instanceof PollReqContent) {
         return (PollReqContent)var0;
      } else {
         return var0 != null ? new PollReqContent(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public PollReqContent(ASN1Integer var1) {
      this((ASN1Sequence)(new DERSequence(new DERSequence(var1))));
   }

   public ASN1Integer[][] getCertReqIds() {
      ASN1Integer[][] var1 = new ASN1Integer[this.content.size()][];

      for(int var2 = 0; var2 != var1.length; ++var2) {
         var1[var2] = sequenceToASN1IntegerArray((ASN1Sequence)this.content.getObjectAt(var2));
      }

      return var1;
   }

   private static ASN1Integer[] sequenceToASN1IntegerArray(ASN1Sequence var0) {
      ASN1Integer[] var1 = new ASN1Integer[var0.size()];

      for(int var2 = 0; var2 != var1.length; ++var2) {
         var1[var2] = ASN1Integer.getInstance(var0.getObjectAt(var2));
      }

      return var1;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.content;
   }
}
