package weblogic.management.provider;

import weblogic.management.ManagementException;

public class EditChangesValidationException extends ManagementException {
   public EditChangesValidationException(String message, Throwable t, boolean unWrapExceptions) {
      super(message, t, unWrapExceptions);
   }

   public EditChangesValidationException(String message, Throwable t) {
      super(message, t);
   }

   public EditChangesValidationException(Throwable t) {
      super(t);
   }

   public EditChangesValidationException(String message) {
      super(message);
   }
}
