package org.python.bouncycastle.asn1.x509;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1UTCTime;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x500.X500Name;

public class V2TBSCertListGenerator {
   private ASN1Integer version = new ASN1Integer(1L);
   private AlgorithmIdentifier signature;
   private X500Name issuer;
   private Time thisUpdate;
   private Time nextUpdate = null;
   private Extensions extensions = null;
   private ASN1EncodableVector crlentries = new ASN1EncodableVector();
   private static final ASN1Sequence[] reasons = new ASN1Sequence[11];

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

   public void setThisUpdate(ASN1UTCTime var1) {
      this.thisUpdate = new Time(var1);
   }

   public void setNextUpdate(ASN1UTCTime var1) {
      this.nextUpdate = new Time(var1);
   }

   public void setThisUpdate(Time var1) {
      this.thisUpdate = var1;
   }

   public void setNextUpdate(Time var1) {
      this.nextUpdate = var1;
   }

   public void addCRLEntry(ASN1Sequence var1) {
      this.crlentries.add(var1);
   }

   public void addCRLEntry(ASN1Integer var1, ASN1UTCTime var2, int var3) {
      this.addCRLEntry(var1, new Time(var2), var3);
   }

   public void addCRLEntry(ASN1Integer var1, Time var2, int var3) {
      this.addCRLEntry(var1, var2, var3, (ASN1GeneralizedTime)null);
   }

   public void addCRLEntry(ASN1Integer var1, Time var2, int var3, ASN1GeneralizedTime var4) {
      ASN1EncodableVector var5;
      if (var3 != 0) {
         var5 = new ASN1EncodableVector();
         if (var3 < reasons.length) {
            if (var3 < 0) {
               throw new IllegalArgumentException("invalid reason value: " + var3);
            }

            var5.add(reasons[var3]);
         } else {
            var5.add(createReasonExtension(var3));
         }

         if (var4 != null) {
            var5.add(createInvalidityDateExtension(var4));
         }

         this.internalAddCRLEntry(var1, var2, new DERSequence(var5));
      } else if (var4 != null) {
         var5 = new ASN1EncodableVector();
         var5.add(createInvalidityDateExtension(var4));
         this.internalAddCRLEntry(var1, var2, new DERSequence(var5));
      } else {
         this.addCRLEntry(var1, var2, (Extensions)null);
      }

   }

   private void internalAddCRLEntry(ASN1Integer var1, Time var2, ASN1Sequence var3) {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      var4.add(var1);
      var4.add(var2);
      if (var3 != null) {
         var4.add(var3);
      }

      this.addCRLEntry(new DERSequence(var4));
   }

   public void addCRLEntry(ASN1Integer var1, Time var2, Extensions var3) {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      var4.add(var1);
      var4.add(var2);
      if (var3 != null) {
         var4.add(var3);
      }

      this.addCRLEntry(new DERSequence(var4));
   }

   public void setExtensions(X509Extensions var1) {
      this.setExtensions(Extensions.getInstance(var1));
   }

   public void setExtensions(Extensions var1) {
      this.extensions = var1;
   }

   public TBSCertList generateTBSCertList() {
      if (this.signature != null && this.issuer != null && this.thisUpdate != null) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         var1.add(this.version);
         var1.add(this.signature);
         var1.add(this.issuer);
         var1.add(this.thisUpdate);
         if (this.nextUpdate != null) {
            var1.add(this.nextUpdate);
         }

         if (this.crlentries.size() != 0) {
            var1.add(new DERSequence(this.crlentries));
         }

         if (this.extensions != null) {
            var1.add(new DERTaggedObject(0, this.extensions));
         }

         return new TBSCertList(new DERSequence(var1));
      } else {
         throw new IllegalStateException("Not all mandatory fields set in V2 TBSCertList generator.");
      }
   }

   private static ASN1Sequence createReasonExtension(int var0) {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      CRLReason var2 = CRLReason.lookup(var0);

      try {
         var1.add(Extension.reasonCode);
         var1.add(new DEROctetString(var2.getEncoded()));
      } catch (IOException var4) {
         throw new IllegalArgumentException("error encoding reason: " + var4);
      }

      return new DERSequence(var1);
   }

   private static ASN1Sequence createInvalidityDateExtension(ASN1GeneralizedTime var0) {
      ASN1EncodableVector var1 = new ASN1EncodableVector();

      try {
         var1.add(Extension.invalidityDate);
         var1.add(new DEROctetString(var0.getEncoded()));
      } catch (IOException var3) {
         throw new IllegalArgumentException("error encoding reason: " + var3);
      }

      return new DERSequence(var1);
   }

   static {
      reasons[0] = createReasonExtension(0);
      reasons[1] = createReasonExtension(1);
      reasons[2] = createReasonExtension(2);
      reasons[3] = createReasonExtension(3);
      reasons[4] = createReasonExtension(4);
      reasons[5] = createReasonExtension(5);
      reasons[6] = createReasonExtension(6);
      reasons[7] = createReasonExtension(7);
      reasons[8] = createReasonExtension(8);
      reasons[9] = createReasonExtension(9);
      reasons[10] = createReasonExtension(10);
   }
}
