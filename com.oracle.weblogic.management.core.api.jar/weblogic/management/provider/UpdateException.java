package weblogic.management.provider;

import weblogic.management.ManagementException;

public class UpdateException extends ManagementException {
   public UpdateException(String message, Throwable t, boolean unWrapExceptions) {
      super(message, t, unWrapExceptions);
   }

   public UpdateException(String message, Throwable t) {
      super(message, t);
   }

   public UpdateException(Throwable t) {
      super(t);
   }

   public UpdateException(String message) {
      super(message);
   }
}
