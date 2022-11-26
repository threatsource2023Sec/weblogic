package org.python.bouncycastle.asn1.isismtt.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERPrintableString;
import org.python.bouncycastle.asn1.DERSequence;

public class MonetaryLimit extends ASN1Object {
   DERPrintableString currency;
   ASN1Integer amount;
   ASN1Integer exponent;

   public static MonetaryLimit getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof MonetaryLimit)) {
         if (var0 instanceof ASN1Sequence) {
            return new MonetaryLimit(ASN1Sequence.getInstance(var0));
         } else {
            throw new IllegalArgumentException("unknown object in getInstance");
         }
      } else {
         return (MonetaryLimit)var0;
      }
   }

   private MonetaryLimit(ASN1Sequence var1) {
      if (var1.size() != 3) {
         throw new IllegalArgumentException("Bad sequence size: " + var1.size());
      } else {
         Enumeration var2 = var1.getObjects();
         this.currency = DERPrintableString.getInstance(var2.nextElement());
         this.amount = ASN1Integer.getInstance(var2.nextElement());
         this.exponent = ASN1Integer.getInstance(var2.nextElement());
      }
   }

   public MonetaryLimit(String var1, int var2, int var3) {
      this.currency = new DERPrintableString(var1, true);
      this.amount = new ASN1Integer((long)var2);
      this.exponent = new ASN1Integer((long)var3);
   }

   public String getCurrency() {
      return this.currency.getString();
   }

   public BigInteger getAmount() {
      return this.amount.getValue();
   }

   public BigInteger getExponent() {
      return this.exponent.getValue();
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.currency);
      var1.add(this.amount);
      var1.add(this.exponent);
      return new DERSequence(var1);
   }
}
