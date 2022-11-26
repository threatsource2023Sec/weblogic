package org.python.bouncycastle.asn1.crmf;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x509.Time;

public class OptionalValidity extends ASN1Object {
   private Time notBefore;
   private Time notAfter;

   private OptionalValidity(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var2.nextElement();
         if (var3.getTagNo() == 0) {
            this.notBefore = Time.getInstance(var3, true);
         } else {
            this.notAfter = Time.getInstance(var3, true);
         }
      }

   }

   public static OptionalValidity getInstance(Object var0) {
      if (var0 instanceof OptionalValidity) {
         return (OptionalValidity)var0;
      } else {
         return var0 != null ? new OptionalValidity(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public OptionalValidity(Time var1, Time var2) {
      if (var1 == null && var2 == null) {
         throw new IllegalArgumentException("at least one of notBefore/notAfter must not be null.");
      } else {
         this.notBefore = var1;
         this.notAfter = var2;
      }
   }

   public Time getNotBefore() {
      return this.notBefore;
   }

   public Time getNotAfter() {
      return this.notAfter;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.notBefore != null) {
         var1.add(new DERTaggedObject(true, 0, this.notBefore));
      }

      if (this.notAfter != null) {
         var1.add(new DERTaggedObject(true, 1, this.notAfter));
      }

      return new DERSequence(var1);
   }
}
