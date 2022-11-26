package org.python.bouncycastle.asn1.icao;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERPrintableString;
import org.python.bouncycastle.asn1.DERSequence;

public class LDSVersionInfo extends ASN1Object {
   private DERPrintableString ldsVersion;
   private DERPrintableString unicodeVersion;

   public LDSVersionInfo(String var1, String var2) {
      this.ldsVersion = new DERPrintableString(var1);
      this.unicodeVersion = new DERPrintableString(var2);
   }

   private LDSVersionInfo(ASN1Sequence var1) {
      if (var1.size() != 2) {
         throw new IllegalArgumentException("sequence wrong size for LDSVersionInfo");
      } else {
         this.ldsVersion = DERPrintableString.getInstance(var1.getObjectAt(0));
         this.unicodeVersion = DERPrintableString.getInstance(var1.getObjectAt(1));
      }
   }

   public static LDSVersionInfo getInstance(Object var0) {
      if (var0 instanceof LDSVersionInfo) {
         return (LDSVersionInfo)var0;
      } else {
         return var0 != null ? new LDSVersionInfo(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public String getLdsVersion() {
      return this.ldsVersion.getString();
   }

   public String getUnicodeVersion() {
      return this.unicodeVersion.getString();
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.ldsVersion);
      var1.add(this.unicodeVersion);
      return new DERSequence(var1);
   }
}
