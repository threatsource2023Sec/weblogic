package weblogic.management.provider;

import weblogic.management.ManagementException;

public class EditWaitTimedOutException extends ManagementException {
   public EditWaitTimedOutException(String message, Throwable t, boolean unWrapExceptions) {
      super(message, t, unWrapExceptions);
   }

   public EditWaitTimedOutException(String message, Throwable t) {
      super(message, t);
   }

   public EditWaitTimedOutException(Throwable t) {
      super(t);
   }

   public EditWaitTimedOutException(String message) {
      super(message);
   }
}
