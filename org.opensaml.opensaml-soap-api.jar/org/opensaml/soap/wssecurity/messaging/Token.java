package org.opensaml.soap.wssecurity.messaging;

public interface Token {
   Object getWrappedToken();

   ValidationStatus getValidationStatus();

   public static enum ValidationStatus {
      VALID,
      INVALID,
      INDETERMINATE,
      VALIDATION_NOT_ATTEMPTED;
   }
}
