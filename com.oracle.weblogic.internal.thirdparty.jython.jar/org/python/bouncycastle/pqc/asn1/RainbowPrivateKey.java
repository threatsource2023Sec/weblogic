package org.python.bouncycastle.pqc.asn1;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.pqc.crypto.rainbow.Layer;
import org.python.bouncycastle.pqc.crypto.rainbow.util.RainbowUtil;

public class RainbowPrivateKey extends ASN1Object {
   private ASN1Integer version;
   private ASN1ObjectIdentifier oid;
   private byte[][] invA1;
   private byte[] b1;
   private byte[][] invA2;
   private byte[] b2;
   private byte[] vi;
   private Layer[] layers;

   private RainbowPrivateKey(ASN1Sequence var1) {
      if (var1.getObjectAt(0) instanceof ASN1Integer) {
         this.version = ASN1Integer.getInstance(var1.getObjectAt(0));
      } else {
         this.oid = ASN1ObjectIdentifier.getInstance(var1.getObjectAt(0));
      }

      ASN1Sequence var2 = (ASN1Sequence)var1.getObjectAt(1);
      this.invA1 = new byte[var2.size()][];

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         this.invA1[var3] = ((ASN1OctetString)var2.getObjectAt(var3)).getOctets();
      }

      ASN1Sequence var19 = (ASN1Sequence)var1.getObjectAt(2);
      this.b1 = ((ASN1OctetString)var19.getObjectAt(0)).getOctets();
      ASN1Sequence var4 = (ASN1Sequence)var1.getObjectAt(3);
      this.invA2 = new byte[var4.size()][];

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         this.invA2[var5] = ((ASN1OctetString)var4.getObjectAt(var5)).getOctets();
      }

      ASN1Sequence var20 = (ASN1Sequence)var1.getObjectAt(4);
      this.b2 = ((ASN1OctetString)var20.getObjectAt(0)).getOctets();
      ASN1Sequence var6 = (ASN1Sequence)var1.getObjectAt(5);
      this.vi = ((ASN1OctetString)var6.getObjectAt(0)).getOctets();
      ASN1Sequence var7 = (ASN1Sequence)var1.getObjectAt(6);
      byte[][][][] var8 = new byte[var7.size()][][][];
      byte[][][][] var9 = new byte[var7.size()][][][];
      byte[][][] var10 = new byte[var7.size()][][];
      byte[][] var11 = new byte[var7.size()][];

      int var12;
      for(var12 = 0; var12 < var7.size(); ++var12) {
         ASN1Sequence var13 = (ASN1Sequence)var7.getObjectAt(var12);
         ASN1Sequence var14 = (ASN1Sequence)var13.getObjectAt(0);
         var8[var12] = new byte[var14.size()][][];

         ASN1Sequence var16;
         int var17;
         for(int var15 = 0; var15 < var14.size(); ++var15) {
            var16 = (ASN1Sequence)var14.getObjectAt(var15);
            var8[var12][var15] = new byte[var16.size()][];

            for(var17 = 0; var17 < var16.size(); ++var17) {
               var8[var12][var15][var17] = ((ASN1OctetString)var16.getObjectAt(var17)).getOctets();
            }
         }

         ASN1Sequence var23 = (ASN1Sequence)var13.getObjectAt(1);
         var9[var12] = new byte[var23.size()][][];

         for(int var24 = 0; var24 < var23.size(); ++var24) {
            ASN1Sequence var25 = (ASN1Sequence)var23.getObjectAt(var24);
            var9[var12][var24] = new byte[var25.size()][];

            for(int var18 = 0; var18 < var25.size(); ++var18) {
               var9[var12][var24][var18] = ((ASN1OctetString)var25.getObjectAt(var18)).getOctets();
            }
         }

         var16 = (ASN1Sequence)var13.getObjectAt(2);
         var10[var12] = new byte[var16.size()][];

         for(var17 = 0; var17 < var16.size(); ++var17) {
            var10[var12][var17] = ((ASN1OctetString)var16.getObjectAt(var17)).getOctets();
         }

         var11[var12] = ((ASN1OctetString)var13.getObjectAt(3)).getOctets();
      }

      var12 = this.vi.length - 1;
      this.layers = new Layer[var12];

      for(int var21 = 0; var21 < var12; ++var21) {
         Layer var22 = new Layer(this.vi[var21], this.vi[var21 + 1], RainbowUtil.convertArray(var8[var21]), RainbowUtil.convertArray(var9[var21]), RainbowUtil.convertArray(var10[var21]), RainbowUtil.convertArray(var11[var21]));
         this.layers[var21] = var22;
      }

   }

   public RainbowPrivateKey(short[][] var1, short[] var2, short[][] var3, short[] var4, int[] var5, Layer[] var6) {
      this.version = new ASN1Integer(1L);
      this.invA1 = RainbowUtil.convertArray(var1);
      this.b1 = RainbowUtil.convertArray(var2);
      this.invA2 = RainbowUtil.convertArray(var3);
      this.b2 = RainbowUtil.convertArray(var4);
      this.vi = RainbowUtil.convertIntArray(var5);
      this.layers = var6;
   }

   public static RainbowPrivateKey getInstance(Object var0) {
      if (var0 instanceof RainbowPrivateKey) {
         return (RainbowPrivateKey)var0;
      } else {
         return var0 != null ? new RainbowPrivateKey(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1Integer getVersion() {
      return this.version;
   }

   public short[][] getInvA1() {
      return RainbowUtil.convertArray(this.invA1);
   }

   public short[] getB1() {
      return RainbowUtil.convertArray(this.b1);
   }

   public short[] getB2() {
      return RainbowUtil.convertArray(this.b2);
   }

   public short[][] getInvA2() {
      return RainbowUtil.convertArray(this.invA2);
   }

   public Layer[] getLayers() {
      return this.layers;
   }

   public int[] getVi() {
      return RainbowUtil.convertArraytoInt(this.vi);
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.version != null) {
         var1.add(this.version);
      } else {
         var1.add(this.oid);
      }

      ASN1EncodableVector var2 = new ASN1EncodableVector();

      for(int var3 = 0; var3 < this.invA1.length; ++var3) {
         var2.add(new DEROctetString(this.invA1[var3]));
      }

      var1.add(new DERSequence(var2));
      ASN1EncodableVector var17 = new ASN1EncodableVector();
      var17.add(new DEROctetString(this.b1));
      var1.add(new DERSequence(var17));
      ASN1EncodableVector var4 = new ASN1EncodableVector();

      for(int var5 = 0; var5 < this.invA2.length; ++var5) {
         var4.add(new DEROctetString(this.invA2[var5]));
      }

      var1.add(new DERSequence(var4));
      ASN1EncodableVector var18 = new ASN1EncodableVector();
      var18.add(new DEROctetString(this.b2));
      var1.add(new DERSequence(var18));
      ASN1EncodableVector var6 = new ASN1EncodableVector();
      var6.add(new DEROctetString(this.vi));
      var1.add(new DERSequence(var6));
      ASN1EncodableVector var7 = new ASN1EncodableVector();

      for(int var8 = 0; var8 < this.layers.length; ++var8) {
         ASN1EncodableVector var9 = new ASN1EncodableVector();
         byte[][][] var10 = RainbowUtil.convertArray(this.layers[var8].getCoeffAlpha());
         ASN1EncodableVector var11 = new ASN1EncodableVector();

         ASN1EncodableVector var13;
         int var14;
         for(int var12 = 0; var12 < var10.length; ++var12) {
            var13 = new ASN1EncodableVector();

            for(var14 = 0; var14 < var10[var12].length; ++var14) {
               var13.add(new DEROctetString(var10[var12][var14]));
            }

            var11.add(new DERSequence(var13));
         }

         var9.add(new DERSequence(var11));
         byte[][][] var19 = RainbowUtil.convertArray(this.layers[var8].getCoeffBeta());
         var13 = new ASN1EncodableVector();

         ASN1EncodableVector var15;
         int var16;
         for(var14 = 0; var14 < var19.length; ++var14) {
            var15 = new ASN1EncodableVector();

            for(var16 = 0; var16 < var19[var14].length; ++var16) {
               var15.add(new DEROctetString(var19[var14][var16]));
            }

            var13.add(new DERSequence(var15));
         }

         var9.add(new DERSequence(var13));
         byte[][] var20 = RainbowUtil.convertArray(this.layers[var8].getCoeffGamma());
         var15 = new ASN1EncodableVector();

         for(var16 = 0; var16 < var20.length; ++var16) {
            var15.add(new DEROctetString(var20[var16]));
         }

         var9.add(new DERSequence(var15));
         var9.add(new DEROctetString(RainbowUtil.convertArray(this.layers[var8].getCoeffEta())));
         var7.add(new DERSequence(var9));
      }

      var1.add(new DERSequence(var7));
      return new DERSequence(var1);
   }
}
