package weblogic.management.provider;

import weblogic.management.ManagementException;

public class EditSaveChangesFailedException extends ManagementException {
   public EditSaveChangesFailedException(String message, Throwable t, boolean unWrapExceptions) {
      super(message, t, unWrapExceptions);
   }

   public EditSaveChangesFailedException(String message, Throwable t) {
      super(message, t);
   }

   public EditSaveChangesFailedException(Throwable t) {
      super(t);
   }

   public EditSaveChangesFailedException(String message) {
      super(message);
   }
}
