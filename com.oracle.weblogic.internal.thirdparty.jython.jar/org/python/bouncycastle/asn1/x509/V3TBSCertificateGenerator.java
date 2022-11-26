package org.python.bouncycastle.asn1.x509;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1UTCTime;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x500.X500Name;

public class V3TBSCertificateGenerator {
   DERTaggedObject version = new DERTaggedObject(true, 0, new ASN1Integer(2L));
   ASN1Integer serialNumber;
   AlgorithmIdentifier signature;
   X500Name issuer;
   Time startDate;
   Time endDate;
   X500Name subject;
   SubjectPublicKeyInfo subjectPublicKeyInfo;
   Extensions extensions;
   private boolean altNamePresentAndCritical;
   private DERBitString issuerUniqueID;
   private DERBitString subjectUniqueID;

   public void setSerialNumber(ASN1Integer var1) {
      this.serialNumber = var1;
   }

   public void setSignature(AlgorithmIdentifier var1) {
      this.signature = var1;
   }

   /** @deprecated */
   public void setIssuer(X509Name var1) {
      this.issuer = X500Name.getInstance(var1);
   }

   public void setIssuer(X500Name var1) {
      this.issuer = var1;
   }

   public void setStartDate(ASN1UTCTime var1) {
      this.startDate = new Time(var1);
   }

   public void setStartDate(Time var1) {
      this.startDate = var1;
   }

   public void setEndDate(ASN1UTCTime var1) {
      this.endDate = new Time(var1);
   }

   public void setEndDate(Time var1) {
      this.endDate = var1;
   }

   /** @deprecated */
   public void setSubject(X509Name var1) {
      this.subject = X500Name.getInstance(var1.toASN1Primitive());
   }

   public void setSubject(X500Name var1) {
      this.subject = var1;
   }

   public void setIssuerUniqueID(DERBitString var1) {
      this.issuerUniqueID = var1;
   }

   public void setSubjectUniqueID(DERBitString var1) {
      this.subjectUniqueID = var1;
   }

   public void setSubjectPublicKeyInfo(SubjectPublicKeyInfo var1) {
      this.subjectPublicKeyInfo = var1;
   }

   /** @deprecated */
   public void setExtensions(X509Extensions var1) {
      this.setExtensions(Extensions.getInstance(var1));
   }

   public void setExtensions(Extensions var1) {
      this.extensions = var1;
      if (var1 != null) {
         Extension var2 = var1.getExtension(Extension.subjectAlternativeName);
         if (var2 != null && var2.isCritical()) {
            this.altNamePresentAndCritical = true;
         }
      }

   }

   public TBSCertificate generateTBSCertificate() {
      if (this.serialNumber != null && this.signature != null && this.issuer != null && this.startDate != null && this.endDate != null && (this.subject != null || this.altNamePresentAndCritical) && this.subjectPublicKeyInfo != null) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         var1.add(this.version);
         var1.add(this.serialNumber);
         var1.add(this.signature);
         var1.add(this.issuer);
         ASN1EncodableVector var2 = new ASN1EncodableVector();
         var2.add(this.startDate);
         var2.add(this.endDate);
         var1.add(new DERSequence(var2));
         if (this.subject != null) {
            var1.add(this.subject);
         } else {
            var1.add(new DERSequence());
         }

         var1.add(this.subjectPublicKeyInfo);
         if (this.issuerUniqueID != null) {
            var1.add(new DERTaggedObject(false, 1, this.issuerUniqueID));
         }

         if (this.subjectUniqueID != null) {
            var1.add(new DERTaggedObject(false, 2, this.subjectUniqueID));
         }

         if (this.extensions != null) {
            var1.add(new DERTaggedObject(true, 3, this.extensions));
         }

         return TBSCertificate.getInstance(new DERSequence(var1));
      } else {
         throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
      }
   }
}
