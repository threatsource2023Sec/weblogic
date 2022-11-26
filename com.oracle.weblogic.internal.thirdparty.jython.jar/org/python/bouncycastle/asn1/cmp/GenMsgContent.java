package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class GenMsgContent extends ASN1Object {
   private ASN1Sequence content;

   private GenMsgContent(ASN1Sequence var1) {
      this.content = var1;
   }

   public static GenMsgContent getInstance(Object var0) {
      if (var0 instanceof GenMsgContent) {
         return (GenMsgContent)var0;
      } else {
         return var0 != null ? new GenMsgContent(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public GenMsgContent(InfoTypeAndValue var1) {
      this.content = new DERSequence(var1);
   }

   public GenMsgContent(InfoTypeAndValue[] var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2.add(var1[var3]);
      }

      this.content = new DERSequence(var2);
   }

   public InfoTypeAndValue[] toInfoTypeAndValueArray() {
      InfoTypeAndValue[] var1 = new InfoTypeAndValue[this.content.size()];

      for(int var2 = 0; var2 != var1.length; ++var2) {
         var1[var2] = InfoTypeAndValue.getInstance(this.content.getObjectAt(var2));
      }

      return var1;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.content;
   }
}
