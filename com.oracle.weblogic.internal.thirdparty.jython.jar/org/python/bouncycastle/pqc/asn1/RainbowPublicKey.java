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
import org.python.bouncycastle.pqc.crypto.rainbow.util.RainbowUtil;

public class RainbowPublicKey extends ASN1Object {
   private ASN1Integer version;
   private ASN1ObjectIdentifier oid;
   private ASN1Integer docLength;
   private byte[][] coeffQuadratic;
   private byte[][] coeffSingular;
   private byte[] coeffScalar;

   private RainbowPublicKey(ASN1Sequence var1) {
      if (var1.getObjectAt(0) instanceof ASN1Integer) {
         this.version = ASN1Integer.getInstance(var1.getObjectAt(0));
      } else {
         this.oid = ASN1ObjectIdentifier.getInstance(var1.getObjectAt(0));
      }

      this.docLength = ASN1Integer.getInstance(var1.getObjectAt(1));
      ASN1Sequence var2 = ASN1Sequence.getInstance(var1.getObjectAt(2));
      this.coeffQuadratic = new byte[var2.size()][];

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         this.coeffQuadratic[var3] = ASN1OctetString.getInstance(var2.getObjectAt(var3)).getOctets();
      }

      ASN1Sequence var5 = (ASN1Sequence)var1.getObjectAt(3);
      this.coeffSingular = new byte[var5.size()][];

      for(int var4 = 0; var4 < var5.size(); ++var4) {
         this.coeffSingular[var4] = ASN1OctetString.getInstance(var5.getObjectAt(var4)).getOctets();
      }

      ASN1Sequence var6 = (ASN1Sequence)var1.getObjectAt(4);
      this.coeffScalar = ASN1OctetString.getInstance(var6.getObjectAt(0)).getOctets();
   }

   public RainbowPublicKey(int var1, short[][] var2, short[][] var3, short[] var4) {
      this.version = new ASN1Integer(0L);
      this.docLength = new ASN1Integer((long)var1);
      this.coeffQuadratic = RainbowUtil.convertArray(var2);
      this.coeffSingular = RainbowUtil.convertArray(var3);
      this.coeffScalar = RainbowUtil.convertArray(var4);
   }

   public static RainbowPublicKey getInstance(Object var0) {
      if (var0 instanceof RainbowPublicKey) {
         return (RainbowPublicKey)var0;
      } else {
         return var0 != null ? new RainbowPublicKey(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1Integer getVersion() {
      return this.version;
   }

   public int getDocLength() {
      return this.docLength.getValue().intValue();
   }

   public short[][] getCoeffQuadratic() {
      return RainbowUtil.convertArray(this.coeffQuadratic);
   }

   public short[][] getCoeffSingular() {
      return RainbowUtil.convertArray(this.coeffSingular);
   }

   public short[] getCoeffScalar() {
      return RainbowUtil.convertArray(this.coeffScalar);
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.version != null) {
         var1.add(this.version);
      } else {
         var1.add(this.oid);
      }

      var1.add(this.docLength);
      ASN1EncodableVector var2 = new ASN1EncodableVector();

      for(int var3 = 0; var3 < this.coeffQuadratic.length; ++var3) {
         var2.add(new DEROctetString(this.coeffQuadratic[var3]));
      }

      var1.add(new DERSequence(var2));
      ASN1EncodableVector var5 = new ASN1EncodableVector();

      for(int var4 = 0; var4 < this.coeffSingular.length; ++var4) {
         var5.add(new DEROctetString(this.coeffSingular[var4]));
      }

      var1.add(new DERSequence(var5));
      ASN1EncodableVector var6 = new ASN1EncodableVector();
      var6.add(new DEROctetString(this.coeffScalar));
      var1.add(new DERSequence(var6));
      return new DERSequence(var1);
   }
}
