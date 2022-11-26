package org.opensaml.security.credential.criteria.impl;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509IssuerSerialCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableX509IssuerSerialCredentialCriterion extends AbstractTriStatePredicate implements EvaluableCredentialCriterion {
   private final Logger log = LoggerFactory.getLogger(EvaluableX509IssuerSerialCredentialCriterion.class);
   private final X500Principal issuer;
   private final BigInteger serialNumber;

   public EvaluableX509IssuerSerialCredentialCriterion(@Nonnull X509IssuerSerialCriterion criteria) {
      this.issuer = ((X509IssuerSerialCriterion)Constraint.isNotNull(criteria, "Criterion instance cannot be null")).getIssuerName();
      this.serialNumber = criteria.getSerialNumber();
   }

   public EvaluableX509IssuerSerialCredentialCriterion(@Nonnull X500Principal newIssuer, @Nonnull BigInteger newSerialNumber) {
      this.issuer = (X500Principal)Constraint.isNotNull(newIssuer, "Issuer cannot be null");
      this.serialNumber = (BigInteger)Constraint.isNotNull(newSerialNumber, "Serial number cannot be null");
   }

   @Nullable
   public boolean apply(@Nullable Credential target) {
      if (target == null) {
         this.log.error("Credential target was null");
         return this.isNullInputSatisfies();
      } else if (!(target instanceof X509Credential)) {
         this.log.info("Credential is not an X509Credential, does not satisfy issuer name and serial number criteria");
         return false;
      } else {
         X509Certificate entityCert = ((X509Credential)target).getEntityCertificate();
         if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, does not satisfy criteria");
            return false;
         } else {
            return this.issuer.equals(entityCert.getIssuerX500Principal()) && this.serialNumber.equals(entityCert.getSerialNumber());
         }
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EvaluableX509IssuerSerialCredentialCriterion [issuer=");
      builder.append(this.issuer.getName());
      builder.append(", serialNumber=");
      builder.append(this.serialNumber);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      int result = 17;
      result = result * 37 + this.issuer.hashCode();
      result = result * 37 + this.serialNumber.hashCode();
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof EvaluableX509IssuerSerialCredentialCriterion)) {
         return false;
      } else {
         EvaluableX509IssuerSerialCredentialCriterion other = (EvaluableX509IssuerSerialCredentialCriterion)obj;
         return this.issuer.equals(other.issuer) && this.serialNumber.equals(other.serialNumber);
      }
   }
}
