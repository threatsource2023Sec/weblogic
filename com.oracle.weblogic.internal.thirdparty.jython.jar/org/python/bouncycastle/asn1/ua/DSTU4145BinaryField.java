package org.python.bouncycastle.asn1.ua;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class DSTU4145BinaryField extends ASN1Object {
   private int m;
   private int k;
   private int j;
   private int l;

   private DSTU4145BinaryField(ASN1Sequence var1) {
      this.m = ASN1Integer.getInstance(var1.getObjectAt(0)).getPositiveValue().intValue();
      if (var1.getObjectAt(1) instanceof ASN1Integer) {
         this.k = ((ASN1Integer)var1.getObjectAt(1)).getPositiveValue().intValue();
      } else {
         if (!(var1.getObjectAt(1) instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("object parse error");
         }

         ASN1Sequence var2 = ASN1Sequence.getInstance(var1.getObjectAt(1));
         this.k = ASN1Integer.getInstance(var2.getObjectAt(0)).getPositiveValue().intValue();
         this.j = ASN1Integer.getInstance(var2.getObjectAt(1)).getPositiveValue().intValue();
         this.l = ASN1Integer.getInstance(var2.getObjectAt(2)).getPositiveValue().intValue();
      }

   }

   public static DSTU4145BinaryField getInstance(Object var0) {
      if (var0 instanceof DSTU4145BinaryField) {
         return (DSTU4145BinaryField)var0;
      } else {
         return var0 != null ? new DSTU4145BinaryField(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public DSTU4145BinaryField(int var1, int var2, int var3, int var4) {
      this.m = var1;
      this.k = var2;
      this.j = var3;
      this.l = var4;
   }

   public int getM() {
      return this.m;
   }

   public int getK1() {
      return this.k;
   }

   public int getK2() {
      return this.j;
   }

   public int getK3() {
      return this.l;
   }

   public DSTU4145BinaryField(int var1, int var2) {
      this(var1, var2, 0, 0);
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(new ASN1Integer((long)this.m));
      if (this.j == 0) {
         var1.add(new ASN1Integer((long)this.k));
      } else {
         ASN1EncodableVector var2 = new ASN1EncodableVector();
         var2.add(new ASN1Integer((long)this.k));
         var2.add(new ASN1Integer((long)this.j));
         var2.add(new ASN1Integer((long)this.l));
         var1.add(new DERSequence(var2));
      }

      return new DERSequence(var1);
   }
}
