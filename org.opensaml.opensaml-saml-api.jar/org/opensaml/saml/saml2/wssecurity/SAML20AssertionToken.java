package org.opensaml.saml.saml2.wssecurity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.soap.wssecurity.messaging.AbstractToken;

public class SAML20AssertionToken extends AbstractToken {
   private SubjectConfirmation subjectConfirmation;

   public SAML20AssertionToken(@Nonnull Assertion token) {
      super(token);
   }

   @Nullable
   public SubjectConfirmation getSubjectConfirmation() {
      return this.subjectConfirmation;
   }

   public void setSubjectConfirmation(@Nullable SubjectConfirmation newSubjectConfirmation) {
      this.subjectConfirmation = newSubjectConfirmation;
   }
}
