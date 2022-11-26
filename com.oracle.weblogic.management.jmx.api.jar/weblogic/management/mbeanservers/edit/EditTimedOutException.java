package weblogic.management.mbeanservers.edit;

public class EditTimedOutException extends EditException {
   public EditTimedOutException(String message) {
      super(message);
   }

   public EditTimedOutException(Throwable t) {
      super(t);
   }

   public EditTimedOutException(String message, Throwable t) {
      super(message, t);
   }
}
