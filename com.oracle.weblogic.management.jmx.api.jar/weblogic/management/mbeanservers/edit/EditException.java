package weblogic.management.mbeanservers.edit;

import weblogic.management.ManagementException;

public class EditException extends ManagementException {
   public EditException(String message) {
      super(message);
   }

   public EditException(Throwable t) {
      super(t);
   }

   public EditException(String message, Throwable t) {
      super(message, t);
   }
}
