package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.crmf.EncryptedValue;

public class CertOrEncCert extends ASN1Object implements ASN1Choice {
   private CMPCertificate certificate;
   private EncryptedValue encryptedCert;

   private CertOrEncCert(ASN1TaggedObject var1) {
      if (var1.getTagNo() == 0) {
         this.certificate = CMPCertificate.getInstance(var1.getObject());
      } else {
         if (var1.getTagNo() != 1) {
            throw new IllegalArgumentException("unknown tag: " + var1.getTagNo());
         }

         this.encryptedCert = EncryptedValue.getInstance(var1.getObject());
      }

   }

   public static CertOrEncCert getInstance(Object var0) {
      if (var0 instanceof CertOrEncCert) {
         return (CertOrEncCert)var0;
      } else {
         return var0 instanceof ASN1TaggedObject ? new CertOrEncCert((ASN1TaggedObject)var0) : null;
      }
   }

   public CertOrEncCert(CMPCertificate var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("'certificate' cannot be null");
      } else {
         this.certificate = var1;
      }
   }

   public CertOrEncCert(EncryptedValue var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("'encryptedCert' cannot be null");
      } else {
         this.encryptedCert = var1;
      }
   }

   public CMPCertificate getCertificate() {
      return this.certificate;
   }

   public EncryptedValue getEncryptedCert() {
      return this.encryptedCert;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.certificate != null ? new DERTaggedObject(true, 0, this.certificate) : new DERTaggedObject(true, 1, this.encryptedCert);
   }
}
