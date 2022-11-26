package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class X509SubjectNameCriterion implements Criterion {
   private X500Principal subjectName;

   public X509SubjectNameCriterion(@Nonnull X500Principal subject) {
      this.setSubjectName(subject);
   }

   @Nonnull
   public X500Principal getSubjectName() {
      return this.subjectName;
   }

   public void setSubjectName(@Nonnull X500Principal subject) {
      Constraint.isNotNull(subject, "Subject principal criteria value cannot be null");
      this.subjectName = subject;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("X509SubjectNameCriterion [subjectName=");
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
         return obj instanceof X509SubjectNameCriterion ? this.subjectName.equals(((X509SubjectNameCriterion)obj).subjectName) : false;
      }
   }
}
