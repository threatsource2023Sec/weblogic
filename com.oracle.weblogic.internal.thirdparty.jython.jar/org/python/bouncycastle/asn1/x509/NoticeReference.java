package org.python.bouncycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Vector;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class NoticeReference extends ASN1Object {
   private DisplayText organization;
   private ASN1Sequence noticeNumbers;

   private static ASN1EncodableVector convertVector(Vector var0) {
      ASN1EncodableVector var1 = new ASN1EncodableVector();

      ASN1Integer var4;
      for(Enumeration var2 = var0.elements(); var2.hasMoreElements(); var1.add(var4)) {
         Object var3 = var2.nextElement();
         if (var3 instanceof BigInteger) {
            var4 = new ASN1Integer((BigInteger)var3);
         } else {
            if (!(var3 instanceof Integer)) {
               throw new IllegalArgumentException();
            }

            var4 = new ASN1Integer((long)(Integer)var3);
         }
      }

      return var1;
   }

   public NoticeReference(String var1, Vector var2) {
      this(var1, convertVector(var2));
   }

   public NoticeReference(String var1, ASN1EncodableVector var2) {
      this(new DisplayText(var1), var2);
   }

   public NoticeReference(DisplayText var1, ASN1EncodableVector var2) {
      this.organization = var1;
      this.noticeNumbers = new DERSequence(var2);
   }

   private NoticeReference(ASN1Sequence var1) {
      if (var1.size() != 2) {
         throw new IllegalArgumentException("Bad sequence size: " + var1.size());
      } else {
         this.organization = DisplayText.getInstance(var1.getObjectAt(0));
         this.noticeNumbers = ASN1Sequence.getInstance(var1.getObjectAt(1));
      }
   }

   public static NoticeReference getInstance(Object var0) {
      if (var0 instanceof NoticeReference) {
         return (NoticeReference)var0;
      } else {
         return var0 != null ? new NoticeReference(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public DisplayText getOrganization() {
      return this.organization;
   }

   public ASN1Integer[] getNoticeNumbers() {
      ASN1Integer[] var1 = new ASN1Integer[this.noticeNumbers.size()];

      for(int var2 = 0; var2 != this.noticeNumbers.size(); ++var2) {
         var1[var2] = ASN1Integer.getInstance(this.noticeNumbers.getObjectAt(var2));
      }

      return var1;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.organization);
      var1.add(this.noticeNumbers);
      return new DERSequence(var1);
   }
}
