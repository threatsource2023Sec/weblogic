package weblogic.management.provider;

import weblogic.management.ManagementException;

public class EditNotEditorException extends ManagementException {
   public EditNotEditorException(String message, Throwable t, boolean unWrapExceptions) {
      super(message, t, unWrapExceptions);
   }

   public EditNotEditorException(String message, Throwable t) {
      super(message, t);
   }

   public EditNotEditorException(Throwable t) {
      super(t);
   }

   public EditNotEditorException(String message) {
      super(message);
   }
}
