package org.opensaml.security.x509;

import java.math.BigInteger;
import javax.annotation.Nonnull;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class X509IssuerSerialCriterion implements Criterion {
   private X500Principal issuerName;
   private BigInteger serialNumber;

   public X509IssuerSerialCriterion(@Nonnull X500Principal issuer, @Nonnull BigInteger serial) {
      this.setIssuerName(issuer);
      this.setSerialNumber(serial);
   }

   @Nonnull
   public X500Principal getIssuerName() {
      return this.issuerName;
   }

   public void setIssuerName(@Nonnull X500Principal issuer) {
      Constraint.isNotNull(issuer, "Issuer principal criteria value cannot be null");
      this.issuerName = issuer;
   }

   @Nonnull
   public BigInteger getSerialNumber() {
      return this.serialNumber;
   }

   public void setSerialNumber(@Nonnull BigInteger serial) {
      Constraint.isNotNull(serial, "Serial number criteria value cannot be null");
      this.serialNumber = serial;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("BindingCriterion [issuerName=");
      builder.append(this.issuerName.getName());
      builder.append(", serialNumber=");
      builder.append(this.serialNumber);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      int result = 17;
      result = result * 37 + this.issuerName.hashCode();
      result = result * 37 + this.serialNumber.hashCode();
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof X509IssuerSerialCriterion)) {
         return false;
      } else {
         X509IssuerSerialCriterion other = (X509IssuerSerialCriterion)obj;
         return this.issuerName.equals(other.issuerName) && this.serialNumber.equals(other.serialNumber);
      }
   }
}
