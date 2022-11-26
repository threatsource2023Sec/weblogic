package org.python.bouncycastle.asn1.x509;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1UTCTime;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x500.X500Name;

public class V1TBSCertificateGenerator {
   DERTaggedObject version = new DERTaggedObject(true, 0, new ASN1Integer(0L));
   ASN1Integer serialNumber;
   AlgorithmIdentifier signature;
   X500Name issuer;
   Time startDate;
   Time endDate;
   X500Name subject;
   SubjectPublicKeyInfo subjectPublicKeyInfo;

   public void setSerialNumber(ASN1Integer var1) {
      this.serialNumber = var1;
   }

   public void setSignature(AlgorithmIdentifier var1) {
      this.signature = var1;
   }

   /** @deprecated */
   public void setIssuer(X509Name var1) {
      this.issuer = X500Name.getInstance(var1.toASN1Primitive());
   }

   public void setIssuer(X500Name var1) {
      this.issuer = var1;
   }

   public void setStartDate(Time var1) {
      this.startDate = var1;
   }

   public void setStartDate(ASN1UTCTime var1) {
      this.startDate = new Time(var1);
   }

   public void setEndDate(Time var1) {
      this.endDate = var1;
   }

   public void setEndDate(ASN1UTCTime var1) {
      this.endDate = new Time(var1);
   }

   /** @deprecated */
   public void setSubject(X509Name var1) {
      this.subject = X500Name.getInstance(var1.toASN1Primitive());
   }

   public void setSubject(X500Name var1) {
      this.subject = var1;
   }

   public void setSubjectPublicKeyInfo(SubjectPublicKeyInfo var1) {
      this.subjectPublicKeyInfo = var1;
   }

   public TBSCertificate generateTBSCertificate() {
      if (this.serialNumber != null && this.signature != null && this.issuer != null && this.startDate != null && this.endDate != null && this.subject != null && this.subjectPublicKeyInfo != null) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         var1.add(this.serialNumber);
         var1.add(this.signature);
         var1.add(this.issuer);
         ASN1EncodableVector var2 = new ASN1EncodableVector();
         var2.add(this.startDate);
         var2.add(this.endDate);
         var1.add(new DERSequence(var2));
         var1.add(this.subject);
         var1.add(this.subjectPublicKeyInfo);
         return TBSCertificate.getInstance(new DERSequence(var1));
      } else {
         throw new IllegalStateException("not all mandatory fields set in V1 TBScertificate generator");
      }
   }
}
