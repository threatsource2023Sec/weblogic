package weblogic.management.provider;

import weblogic.management.ManagementException;

public class EditFailedException extends ManagementException {
   public EditFailedException(String message, Throwable t, boolean unWrapExceptions) {
      super(message, t, unWrapExceptions);
   }

   public EditFailedException(String message, Throwable t) {
      super(message, t);
   }

   public EditFailedException(Throwable t) {
      super(t);
   }

   public EditFailedException(String message) {
      super(message);
   }
}
