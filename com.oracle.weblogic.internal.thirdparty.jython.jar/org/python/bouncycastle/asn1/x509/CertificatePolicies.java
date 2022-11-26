package org.python.bouncycastle.asn1.x509;

import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;

public class CertificatePolicies extends ASN1Object {
   private final PolicyInformation[] policyInformation;

   public static CertificatePolicies getInstance(Object var0) {
      if (var0 instanceof CertificatePolicies) {
         return (CertificatePolicies)var0;
      } else {
         return var0 != null ? new CertificatePolicies(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public static CertificatePolicies getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static CertificatePolicies fromExtensions(Extensions var0) {
      return getInstance(var0.getExtensionParsedValue(Extension.certificatePolicies));
   }

   public CertificatePolicies(PolicyInformation var1) {
      this.policyInformation = new PolicyInformation[]{var1};
   }

   public CertificatePolicies(PolicyInformation[] var1) {
      this.policyInformation = var1;
   }

   private CertificatePolicies(ASN1Sequence var1) {
      this.policyInformation = new PolicyInformation[var1.size()];

      for(int var2 = 0; var2 != var1.size(); ++var2) {
         this.policyInformation[var2] = PolicyInformation.getInstance(var1.getObjectAt(var2));
      }

   }

   public PolicyInformation[] getPolicyInformation() {
      PolicyInformation[] var1 = new PolicyInformation[this.policyInformation.length];
      System.arraycopy(this.policyInformation, 0, var1, 0, this.policyInformation.length);
      return var1;
   }

   public PolicyInformation getPolicyInformation(ASN1ObjectIdentifier var1) {
      for(int var2 = 0; var2 != this.policyInformation.length; ++var2) {
         if (var1.equals(this.policyInformation[var2].getPolicyIdentifier())) {
            return this.policyInformation[var2];
         }
      }

      return null;
   }

   public ASN1Primitive toASN1Primitive() {
      return new DERSequence(this.policyInformation);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.policyInformation.length; ++var2) {
         if (var1.length() != 0) {
            var1.append(", ");
         }

         var1.append(this.policyInformation[var2]);
      }

      return "CertificatePolicies: [" + var1 + "]";
   }
}
