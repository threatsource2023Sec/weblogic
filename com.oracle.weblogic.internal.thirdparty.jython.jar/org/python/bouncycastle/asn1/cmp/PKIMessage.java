package org.python.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;

public class PKIMessage extends ASN1Object {
   private PKIHeader header;
   private PKIBody body;
   private DERBitString protection;
   private ASN1Sequence extraCerts;

   private PKIMessage(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      this.header = PKIHeader.getInstance(var2.nextElement());
      this.body = PKIBody.getInstance(var2.nextElement());

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var2.nextElement();
         if (var3.getTagNo() == 0) {
            this.protection = DERBitString.getInstance(var3, true);
         } else {
            this.extraCerts = ASN1Sequence.getInstance(var3, true);
         }
      }

   }

   public static PKIMessage getInstance(Object var0) {
      if (var0 instanceof PKIMessage) {
         return (PKIMessage)var0;
      } else {
         return var0 != null ? new PKIMessage(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public PKIMessage(PKIHeader var1, PKIBody var2, DERBitString var3, CMPCertificate[] var4) {
      this.header = var1;
      this.body = var2;
      this.protection = var3;
      if (var4 != null) {
         ASN1EncodableVector var5 = new ASN1EncodableVector();

         for(int var6 = 0; var6 < var4.length; ++var6) {
            var5.add(var4[var6]);
         }

         this.extraCerts = new DERSequence(var5);
      }

   }

   public PKIMessage(PKIHeader var1, PKIBody var2, DERBitString var3) {
      this(var1, var2, var3, (CMPCertificate[])null);
   }

   public PKIMessage(PKIHeader var1, PKIBody var2) {
      this(var1, var2, (DERBitString)null, (CMPCertificate[])null);
   }

   public PKIHeader getHeader() {
      return this.header;
   }

   public PKIBody getBody() {
      return this.body;
   }

   public DERBitString getProtection() {
      return this.protection;
   }

   public CMPCertificate[] getExtraCerts() {
      if (this.extraCerts == null) {
         return null;
      } else {
         CMPCertificate[] var1 = new CMPCertificate[this.extraCerts.size()];

         for(int var2 = 0; var2 < var1.length; ++var2) {
            var1[var2] = CMPCertificate.getInstance(this.extraCerts.getObjectAt(var2));
         }

         return var1;
      }
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.header);
      var1.add(this.body);
      this.addOptional(var1, 0, this.protection);
      this.addOptional(var1, 1, this.extraCerts);
      return new DERSequence(var1);
   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if (var3 != null) {
         var1.add(new DERTaggedObject(true, var2, var3));
      }

   }
}
