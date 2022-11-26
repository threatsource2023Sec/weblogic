package org.opensaml.security.credential.criteria.impl;

import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableX509CertSelectorCredentialCriterion extends AbstractTriStatePredicate implements EvaluableCredentialCriterion {
   private final Logger log = LoggerFactory.getLogger(EvaluableX509CertSelectorCredentialCriterion.class);
   private final X509CertSelector certSelector;

   public EvaluableX509CertSelectorCredentialCriterion(@Nonnull X509CertSelector selector) {
      this.certSelector = (X509CertSelector)Constraint.isNotNull(selector, "X.509 cert selector cannot be null");
   }

   @Nullable
   public boolean apply(@Nullable Credential target) {
      if (target == null) {
         this.log.error("Credential target was null");
         return this.isNullInputSatisfies();
      } else if (!(target instanceof X509Credential)) {
         this.log.info("Credential is not an X509Credential, cannot evaluate X509CertSelector criteria");
         return false;
      } else {
         X509Certificate entityCert = ((X509Credential)target).getEntityCertificate();
         if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, cannot evaluate X509CertSelector criteria");
            return false;
         } else {
            return this.certSelector.match(entityCert);
         }
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EvaluableX509CertSelectorCredentialCriterion [certSelector=");
      builder.append("<contents not displayable>");
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.certSelector.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof EvaluableX509CertSelectorCredentialCriterion ? this.certSelector.equals(((EvaluableX509CertSelectorCredentialCriterion)obj).certSelector) : false;
      }
   }
}
