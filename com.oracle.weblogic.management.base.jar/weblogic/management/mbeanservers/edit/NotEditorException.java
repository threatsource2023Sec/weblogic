package weblogic.management.mbeanservers.edit;

public class NotEditorException extends EditException {
   public NotEditorException(String message) {
      super(message);
   }

   public NotEditorException(Throwable t) {
      super(t);
   }

   public NotEditorException(String message, Throwable t) {
      super(message, t);
   }
}
