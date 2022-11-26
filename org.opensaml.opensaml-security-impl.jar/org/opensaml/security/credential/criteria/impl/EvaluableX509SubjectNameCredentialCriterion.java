package org.opensaml.security.credential.criteria.impl;

import java.security.cert.X509Certificate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509SubjectNameCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableX509SubjectNameCredentialCriterion extends AbstractTriStatePredicate implements EvaluableCredentialCriterion {
   private final Logger log = LoggerFactory.getLogger(EvaluableX509SubjectNameCredentialCriterion.class);
   private final X500Principal subjectName;

   public EvaluableX509SubjectNameCredentialCriterion(@Nonnull X509SubjectNameCriterion criteria) {
      this.subjectName = ((X509SubjectNameCriterion)Constraint.isNotNull(criteria, "Criterion instance cannot be null")).getSubjectName();
   }

   public EvaluableX509SubjectNameCredentialCriterion(@Nonnull X500Principal newSubjectName) {
      this.subjectName = (X500Principal)Constraint.isNotNull(newSubjectName, "Subject name cannot be null");
   }

   @Nullable
   public boolean apply(@Nullable Credential target) {
      if (target == null) {
         this.log.error("Credential target was null");
         return this.isNullInputSatisfies();
      } else if (!(target instanceof X509Credential)) {
         this.log.info("Credential is not an X509Credential, does not satisfy subject name criteria");
         return false;
      } else {
         X509Certificate entityCert = ((X509Credential)target).getEntityCertificate();
         if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, does not satisfy criteria");
            return false;
         } else {
            return this.subjectName.equals(entityCert.getSubjectX500Principal());
         }
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EvaluableX509SubjectNameCredentialCriterion [subjectName=");
      builder.append(this.subjectName.getName());
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.subjectName.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof EvaluableX509SubjectNameCredentialCriterion ? this.subjectName.equals(((EvaluableX509SubjectNameCredentialCriterion)obj).subjectName) : false;
      }
   }
}
