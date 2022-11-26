package weblogic.security.pk;

import com.bea.common.security.SecurityLogger;
import com.bea.common.security.utils.encoders.BASE64Encoder;

public final class SubjectKeyIdentifierSelector implements CertPathSelector {
   private byte[] subjectKeyIdentifier;

   public SubjectKeyIdentifierSelector(byte[] subjectKeyIdentifier) {
      if (subjectKeyIdentifier != null && subjectKeyIdentifier.length >= 1) {
         this.subjectKeyIdentifier = subjectKeyIdentifier;
      } else {
         throw new IllegalArgumentException(SecurityLogger.getSubjectKeyIdentifierSelectorIllegalSubjectKeyIdentifier());
      }
   }

   public byte[] getSubjectKeyIdentifier() {
      return this.subjectKeyIdentifier;
   }

   public String toString() {
      String base64SubjectKeyIdentifier = (new BASE64Encoder()).encodeBuffer(this.subjectKeyIdentifier);
      return "SubjectKeyIdentifierSelector, subjectKeyIdentifier=" + base64SubjectKeyIdentifier;
   }
}
