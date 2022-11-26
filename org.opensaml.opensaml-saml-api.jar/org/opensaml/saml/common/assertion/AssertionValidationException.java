package org.opensaml.saml.common.assertion;

import org.opensaml.saml.common.SAMLException;

public class AssertionValidationException extends SAMLException {
   private static final long serialVersionUID = -4394174383975186565L;

   public AssertionValidationException() {
   }

   public AssertionValidationException(String message) {
      super(message);
   }

   public AssertionValidationException(Exception cause) {
      super(cause);
   }

   public AssertionValidationException(String message, Exception cause) {
      super(message, cause);
   }
}
