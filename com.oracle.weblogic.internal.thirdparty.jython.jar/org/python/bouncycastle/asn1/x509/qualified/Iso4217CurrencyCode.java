package org.python.bouncycastle.asn1.x509.qualified;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERPrintableString;

public class Iso4217CurrencyCode extends ASN1Object implements ASN1Choice {
   final int ALPHABETIC_MAXSIZE = 3;
   final int NUMERIC_MINSIZE = 1;
   final int NUMERIC_MAXSIZE = 999;
   ASN1Encodable obj;
   int numeric;

   public static Iso4217CurrencyCode getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof Iso4217CurrencyCode)) {
         if (var0 instanceof ASN1Integer) {
            ASN1Integer var3 = ASN1Integer.getInstance(var0);
            int var2 = var3.getValue().intValue();
            return new Iso4217CurrencyCode(var2);
         } else if (var0 instanceof DERPrintableString) {
            DERPrintableString var1 = DERPrintableString.getInstance(var0);
            return new Iso4217CurrencyCode(var1.getString());
         } else {
            throw new IllegalArgumentException("unknown object in getInstance");
         }
      } else {
         return (Iso4217CurrencyCode)var0;
      }
   }

   public Iso4217CurrencyCode(int var1) {
      if (var1 <= 999 && var1 >= 1) {
         this.obj = new ASN1Integer((long)var1);
      } else {
         throw new IllegalArgumentException("wrong size in numeric code : not in (1..999)");
      }
   }

   public Iso4217CurrencyCode(String var1) {
      if (var1.length() > 3) {
         throw new IllegalArgumentException("wrong size in alphabetic code : max size is 3");
      } else {
         this.obj = new DERPrintableString(var1);
      }
   }

   public boolean isAlphabetic() {
      return this.obj instanceof DERPrintableString;
   }

   public String getAlphabetic() {
      return ((DERPrintableString)this.obj).getString();
   }

   public int getNumeric() {
      return ((ASN1Integer)this.obj).getValue().intValue();
   }

   public ASN1Primitive toASN1Primitive() {
      return this.obj.toASN1Primitive();
   }
}
