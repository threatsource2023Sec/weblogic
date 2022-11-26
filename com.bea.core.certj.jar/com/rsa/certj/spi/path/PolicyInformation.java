package com.rsa.certj.spi.path;

import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.extensions.PolicyQualifiers;
import java.util.Arrays;

/** @deprecated */
public final class PolicyInformation {
   private byte[] policyIdentifier;
   private PolicyQualifiers policyQualifiers;

   /** @deprecated */
   public PolicyInformation(byte[] var1, PolicyQualifiers var2) throws CertPathException {
      this.policyIdentifier = var1;

      try {
         if (var2 != null) {
            this.policyQualifiers = (PolicyQualifiers)var2.clone();
         }

      } catch (CloneNotSupportedException var4) {
         throw new CertPathException("Error in clone qualifiers.");
      }
   }

   /** @deprecated */
   public byte[] getCertPolicyID() {
      if (this.policyIdentifier == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.policyIdentifier.length];
         System.arraycopy(this.policyIdentifier, 0, var1, 0, this.policyIdentifier.length);
         return var1;
      }
   }

   /** @deprecated */
   public PolicyQualifiers getPolicyQualifiers() throws CertPathException {
      try {
         return this.policyQualifiers == null ? null : (PolicyQualifiers)this.policyQualifiers.clone();
      } catch (CloneNotSupportedException var2) {
         throw new CertPathException("Error in clone qualifiers.");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PolicyInformation) {
         PolicyInformation var2 = (PolicyInformation)var1;
         if (!CertJUtils.byteArraysEqual(var2.policyIdentifier, this.policyIdentifier)) {
            return false;
         } else if (this.policyQualifiers == null) {
            return var2.policyQualifiers == null;
         } else {
            return this.policyQualifiers.equals(var2.policyQualifiers);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + Arrays.hashCode(this.policyIdentifier);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.policyQualifiers);
      return var2;
   }
}
