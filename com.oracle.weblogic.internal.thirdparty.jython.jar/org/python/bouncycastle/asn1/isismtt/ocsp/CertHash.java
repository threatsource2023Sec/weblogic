package org.python.bouncycastle.asn1.isismtt.ocsp;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertHash extends ASN1Object {
   private AlgorithmIdentifier hashAlgorithm;
   private byte[] certificateHash;

   public static CertHash getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof CertHash)) {
         if (var0 instanceof ASN1Sequence) {
            return new CertHash((ASN1Sequence)var0);
         } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
         }
      } else {
         return (CertHash)var0;
      }
   }

   private CertHash(ASN1Sequence var1) {
      if (var1.size() != 2) {
         throw new IllegalArgumentException("Bad sequence size: " + var1.size());
      } else {
         this.hashAlgorithm = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
         this.certificateHash = DEROctetString.getInstance(var1.getObjectAt(1)).getOctets();
      }
   }

   public CertHash(AlgorithmIdentifier var1, byte[] var2) {
      this.hashAlgorithm = var1;
      this.certificateHash = new byte[var2.length];
      System.arraycopy(var2, 0, this.certificateHash, 0, var2.length);
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public byte[] getCertificateHash() {
      return this.certificateHash;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.hashAlgorithm);
      var1.add(new DEROctetString(this.certificateHash));
      return new DERSequence(var1);
   }
}
