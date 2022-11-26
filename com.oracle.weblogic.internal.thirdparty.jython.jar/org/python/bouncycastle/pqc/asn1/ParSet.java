package org.python.bouncycastle.pqc.asn1;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.util.Arrays;

public class ParSet extends ASN1Object {
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private int t;
   private int[] h;
   private int[] w;
   private int[] k;

   private static int checkBigIntegerInIntRangeAndPositive(BigInteger var0) {
      if (var0.compareTo(BigInteger.valueOf(2147483647L)) <= 0 && var0.compareTo(ZERO) > 0) {
         return var0.intValue();
      } else {
         throw new IllegalArgumentException("BigInteger not in Range: " + var0.toString());
      }
   }

   private ParSet(ASN1Sequence var1) {
      if (var1.size() != 4) {
         throw new IllegalArgumentException("sie of seqOfParams = " + var1.size());
      } else {
         BigInteger var2 = ((ASN1Integer)var1.getObjectAt(0)).getValue();
         this.t = checkBigIntegerInIntRangeAndPositive(var2);
         ASN1Sequence var3 = (ASN1Sequence)var1.getObjectAt(1);
         ASN1Sequence var4 = (ASN1Sequence)var1.getObjectAt(2);
         ASN1Sequence var5 = (ASN1Sequence)var1.getObjectAt(3);
         if (var3.size() == this.t && var4.size() == this.t && var5.size() == this.t) {
            this.h = new int[var3.size()];
            this.w = new int[var4.size()];
            this.k = new int[var5.size()];

            for(int var6 = 0; var6 < this.t; ++var6) {
               this.h[var6] = checkBigIntegerInIntRangeAndPositive(((ASN1Integer)var3.getObjectAt(var6)).getValue());
               this.w[var6] = checkBigIntegerInIntRangeAndPositive(((ASN1Integer)var4.getObjectAt(var6)).getValue());
               this.k[var6] = checkBigIntegerInIntRangeAndPositive(((ASN1Integer)var5.getObjectAt(var6)).getValue());
            }

         } else {
            throw new IllegalArgumentException("invalid size of sequences");
         }
      }
   }

   public ParSet(int var1, int[] var2, int[] var3, int[] var4) {
      this.t = var1;
      this.h = var2;
      this.w = var3;
      this.k = var4;
   }

   public static ParSet getInstance(Object var0) {
      if (var0 instanceof ParSet) {
         return (ParSet)var0;
      } else {
         return var0 != null ? new ParSet(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public int getT() {
      return this.t;
   }

   public int[] getH() {
      return Arrays.clone(this.h);
   }

   public int[] getW() {
      return Arrays.clone(this.w);
   }

   public int[] getK() {
      return Arrays.clone(this.k);
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      ASN1EncodableVector var3 = new ASN1EncodableVector();

      for(int var4 = 0; var4 < this.h.length; ++var4) {
         var1.add(new ASN1Integer((long)this.h[var4]));
         var2.add(new ASN1Integer((long)this.w[var4]));
         var3.add(new ASN1Integer((long)this.k[var4]));
      }

      ASN1EncodableVector var5 = new ASN1EncodableVector();
      var5.add(new ASN1Integer((long)this.t));
      var5.add(new DERSequence(var1));
      var5.add(new DERSequence(var2));
      var5.add(new DERSequence(var3));
      return new DERSequence(var5);
   }
}
