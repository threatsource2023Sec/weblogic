package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.crmf.EncryptedValue;
import org.python.bouncycastle.asn1.crmf.PKIPublicationInfo;

public class CertifiedKeyPair extends ASN1Object {
   private CertOrEncCert certOrEncCert;
   private EncryptedValue privateKey;
   private PKIPublicationInfo publicationInfo;

   private CertifiedKeyPair(ASN1Sequence var1) {
      this.certOrEncCert = CertOrEncCert.getInstance(var1.getObjectAt(0));
      if (var1.size() >= 2) {
         if (var1.size() == 2) {
            ASN1TaggedObject var2 = ASN1TaggedObject.getInstance(var1.getObjectAt(1));
            if (var2.getTagNo() == 0) {
               this.privateKey = EncryptedValue.getInstance(var2.getObject());
            } else {
               this.publicationInfo = PKIPublicationInfo.getInstance(var2.getObject());
            }
         } else {
            this.privateKey = EncryptedValue.getInstance(ASN1TaggedObject.getInstance(var1.getObjectAt(1)));
            this.publicationInfo = PKIPublicationInfo.getInstance(ASN1TaggedObject.getInstance(var1.getObjectAt(2)));
         }
      }

   }

   public static CertifiedKeyPair getInstance(Object var0) {
      if (var0 instanceof CertifiedKeyPair) {
         return (CertifiedKeyPair)var0;
      } else {
         return var0 != null ? new CertifiedKeyPair(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public CertifiedKeyPair(CertOrEncCert var1) {
      this(var1, (EncryptedValue)null, (PKIPublicationInfo)null);
   }

   public CertifiedKeyPair(CertOrEncCert var1, EncryptedValue var2, PKIPublicationInfo var3) {
      if (var1 == null) {
         throw new IllegalArgumentException("'certOrEncCert' cannot be null");
      } else {
         this.certOrEncCert = var1;
         this.privateKey = var2;
         this.publicationInfo = var3;
      }
   }

   public CertOrEncCert getCertOrEncCert() {
      return this.certOrEncCert;
   }

   public EncryptedValue getPrivateKey() {
      return this.privateKey;
   }

   public PKIPublicationInfo getPublicationInfo() {
      return this.publicationInfo;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.certOrEncCert);
      if (this.privateKey != null) {
         var1.add(new DERTaggedObject(true, 0, this.privateKey));
      }

      if (this.publicationInfo != null) {
         var1.add(new DERTaggedObject(true, 1, this.publicationInfo));
      }

      return new DERSequence(var1);
   }
}
