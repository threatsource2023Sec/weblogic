package org.python.bouncycastle.asn1.cmc;

import java.io.IOException;
import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertificationRequest extends ASN1Object {
   private static final ASN1Integer ZERO = new ASN1Integer(0L);
   private final CertificationRequestInfo certificationRequestInfo;
   private final AlgorithmIdentifier signatureAlgorithm;
   private final DERBitString signature;

   public CertificationRequest(X500Name var1, AlgorithmIdentifier var2, DERBitString var3, ASN1Set var4, AlgorithmIdentifier var5, DERBitString var6) {
      this.certificationRequestInfo = new CertificationRequestInfo(var1, var2, var3, var4);
      this.signatureAlgorithm = var5;
      this.signature = var6;
   }

   private CertificationRequest(ASN1Sequence var1) {
      if (var1.size() != 3) {
         throw new IllegalArgumentException("incorrect sequence size");
      } else {
         this.certificationRequestInfo = new CertificationRequestInfo(ASN1Sequence.getInstance(var1.getObjectAt(0)));
         this.signatureAlgorithm = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
         this.signature = DERBitString.getInstance(var1.getObjectAt(2));
      }
   }

   public static CertificationRequest getInstance(Object var0) {
      if (var0 instanceof CertificationRequest) {
         return (CertificationRequest)var0;
      } else {
         return var0 != null ? new CertificationRequest(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public BigInteger getVersion() {
      return this.certificationRequestInfo.getVersion().getValue();
   }

   public X500Name getSubject() {
      return this.certificationRequestInfo.getSubject();
   }

   public ASN1Set getAttributes() {
      return this.certificationRequestInfo.getAttributes();
   }

   public AlgorithmIdentifier getSubjectPublicKeyAlgorithm() {
      return AlgorithmIdentifier.getInstance(this.certificationRequestInfo.getSubjectPublicKeyInfo().getObjectAt(0));
   }

   public DERBitString getSubjectPublicKey() {
      return DERBitString.getInstance(this.certificationRequestInfo.getSubjectPublicKeyInfo().getObjectAt(1));
   }

   public ASN1Primitive parsePublicKey() throws IOException {
      return ASN1Primitive.fromByteArray(this.getSubjectPublicKey().getOctets());
   }

   public AlgorithmIdentifier getSignatureAlgorithm() {
      return this.signatureAlgorithm;
   }

   public DERBitString getSignature() {
      return this.signature;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.certificationRequestInfo);
      var1.add(this.signatureAlgorithm);
      var1.add(this.signature);
      return new DERSequence(var1);
   }

   private class CertificationRequestInfo extends ASN1Object {
      private final ASN1Integer version;
      private final X500Name subject;
      private final ASN1Sequence subjectPublicKeyInfo;
      private final ASN1Set attributes;

      private CertificationRequestInfo(ASN1Sequence var2) {
         if (var2.size() != 4) {
            throw new IllegalArgumentException("incorrect sequence size for CertificationRequestInfo");
         } else {
            this.version = ASN1Integer.getInstance(var2.getObjectAt(0));
            this.subject = X500Name.getInstance(var2.getObjectAt(1));
            this.subjectPublicKeyInfo = ASN1Sequence.getInstance(var2.getObjectAt(2));
            if (this.subjectPublicKeyInfo.size() != 2) {
               throw new IllegalArgumentException("incorrect subjectPublicKeyInfo size for CertificationRequestInfo");
            } else {
               ASN1TaggedObject var3 = (ASN1TaggedObject)var2.getObjectAt(3);
               if (var3.getTagNo() != 0) {
                  throw new IllegalArgumentException("incorrect tag number on attributes for CertificationRequestInfo");
               } else {
                  this.attributes = ASN1Set.getInstance(var3, false);
               }
            }
         }
      }

      private CertificationRequestInfo(X500Name var2, AlgorithmIdentifier var3, DERBitString var4, ASN1Set var5) {
         this.version = CertificationRequest.ZERO;
         this.subject = var2;
         this.subjectPublicKeyInfo = new DERSequence(new ASN1Encodable[]{var3, var4});
         this.attributes = var5;
      }

      private ASN1Integer getVersion() {
         return this.version;
      }

      private X500Name getSubject() {
         return this.subject;
      }

      private ASN1Sequence getSubjectPublicKeyInfo() {
         return this.subjectPublicKeyInfo;
      }

      private ASN1Set getAttributes() {
         return this.attributes;
      }

      public ASN1Primitive toASN1Primitive() {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         var1.add(this.version);
         var1.add(this.subject);
         var1.add(this.subjectPublicKeyInfo);
         var1.add(new DERTaggedObject(false, 0, this.attributes));
         return new DERSequence(var1);
      }

      // $FF: synthetic method
      CertificationRequestInfo(X500Name var2, AlgorithmIdentifier var3, DERBitString var4, ASN1Set var5, Object var6) {
         this(var2, var3, var4, var5);
      }

      // $FF: synthetic method
      CertificationRequestInfo(ASN1Sequence var2, Object var3) {
         this(var2);
      }
   }
}
