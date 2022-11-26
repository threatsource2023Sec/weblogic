package org.python.bouncycastle.asn1.x509;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.util.Strings;

public class CRLDistPoint extends ASN1Object {
   ASN1Sequence seq = null;

   public static CRLDistPoint getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static CRLDistPoint getInstance(Object var0) {
      if (var0 instanceof CRLDistPoint) {
         return (CRLDistPoint)var0;
      } else {
         return var0 != null ? new CRLDistPoint(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private CRLDistPoint(ASN1Sequence var1) {
      this.seq = var1;
   }

   public CRLDistPoint(DistributionPoint[] var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();

      for(int var3 = 0; var3 != var1.length; ++var3) {
         var2.add(var1[var3]);
      }

      this.seq = new DERSequence(var2);
   }

   public DistributionPoint[] getDistributionPoints() {
      DistributionPoint[] var1 = new DistributionPoint[this.seq.size()];

      for(int var2 = 0; var2 != this.seq.size(); ++var2) {
         var1[var2] = DistributionPoint.getInstance(this.seq.getObjectAt(var2));
      }

      return var1;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.seq;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = Strings.lineSeparator();
      var1.append("CRLDistPoint:");
      var1.append(var2);
      DistributionPoint[] var3 = this.getDistributionPoints();

      for(int var4 = 0; var4 != var3.length; ++var4) {
         var1.append("    ");
         var1.append(var3[var4]);
         var1.append(var2);
      }

      return var1.toString();
   }
}
