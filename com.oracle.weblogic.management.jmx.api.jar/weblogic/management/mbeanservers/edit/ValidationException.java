package weblogic.management.mbeanservers.edit;

public class ValidationException extends EditException {
   public ValidationException(String message) {
      super(message);
   }

   public ValidationException(Throwable t) {
      super(t);
   }

   public ValidationException(String message, Throwable t) {
      super(message, t);
   }
}
