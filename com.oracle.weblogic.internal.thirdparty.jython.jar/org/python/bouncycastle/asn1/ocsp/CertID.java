package org.python.bouncycastle.asn1.ocsp;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertID extends ASN1Object {
   AlgorithmIdentifier hashAlgorithm;
   ASN1OctetString issuerNameHash;
   ASN1OctetString issuerKeyHash;
   ASN1Integer serialNumber;

   public CertID(AlgorithmIdentifier var1, ASN1OctetString var2, ASN1OctetString var3, ASN1Integer var4) {
      this.hashAlgorithm = var1;
      this.issuerNameHash = var2;
      this.issuerKeyHash = var3;
      this.serialNumber = var4;
   }

   private CertID(ASN1Sequence var1) {
      this.hashAlgorithm = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
      this.issuerNameHash = (ASN1OctetString)var1.getObjectAt(1);
      this.issuerKeyHash = (ASN1OctetString)var1.getObjectAt(2);
      this.serialNumber = (ASN1Integer)var1.getObjectAt(3);
   }

   public static CertID getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static CertID getInstance(Object var0) {
      if (var0 instanceof CertID) {
         return (CertID)var0;
      } else {
         return var0 != null ? new CertID(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public ASN1OctetString getIssuerNameHash() {
      return this.issuerNameHash;
   }

   public ASN1OctetString getIssuerKeyHash() {
      return this.issuerKeyHash;
   }

   public ASN1Integer getSerialNumber() {
      return this.serialNumber;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.hashAlgorithm);
      var1.add(this.issuerNameHash);
      var1.add(this.issuerKeyHash);
      var1.add(this.serialNumber);
      return new DERSequence(var1);
   }
}
