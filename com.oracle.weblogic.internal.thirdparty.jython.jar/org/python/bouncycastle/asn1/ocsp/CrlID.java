package org.python.bouncycastle.asn1.ocsp;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;

public class CrlID extends ASN1Object {
   private DERIA5String crlUrl;
   private ASN1Integer crlNum;
   private ASN1GeneralizedTime crlTime;

   private CrlID(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var2.nextElement();
         switch (var3.getTagNo()) {
            case 0:
               this.crlUrl = DERIA5String.getInstance(var3, true);
               break;
            case 1:
               this.crlNum = ASN1Integer.getInstance(var3, true);
               break;
            case 2:
               this.crlTime = ASN1GeneralizedTime.getInstance(var3, true);
               break;
            default:
               throw new IllegalArgumentException("unknown tag number: " + var3.getTagNo());
         }
      }

   }

   public static CrlID getInstance(Object var0) {
      if (var0 instanceof CrlID) {
         return (CrlID)var0;
      } else {
         return var0 != null ? new CrlID(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public DERIA5String getCrlUrl() {
      return this.crlUrl;
   }

   public ASN1Integer getCrlNum() {
      return this.crlNum;
   }

   public ASN1GeneralizedTime getCrlTime() {
      return this.crlTime;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.crlUrl != null) {
         var1.add(new DERTaggedObject(true, 0, this.crlUrl));
      }

      if (this.crlNum != null) {
         var1.add(new DERTaggedObject(true, 1, this.crlNum));
      }

      if (this.crlTime != null) {
         var1.add(new DERTaggedObject(true, 2, this.crlTime));
      }

      return new DERSequence(var1);
   }
}
