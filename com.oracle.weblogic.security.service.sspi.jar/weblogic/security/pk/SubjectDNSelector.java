package weblogic.security.pk;

import com.bea.common.security.SecurityLogger;

public final class SubjectDNSelector implements CertPathSelector {
   private String subjectDN;

   public SubjectDNSelector(String subjectDN) {
      if (subjectDN != null && subjectDN.length() >= 1) {
         this.subjectDN = subjectDN;
      } else {
         throw new IllegalArgumentException(SecurityLogger.getSubjectDNSelectorIllegalSubjectDN());
      }
   }

   public String getSubjectDN() {
      return this.subjectDN;
   }

   public String toString() {
      return "SubjectDNSelector, subjectDN=" + this.subjectDN;
   }
}
