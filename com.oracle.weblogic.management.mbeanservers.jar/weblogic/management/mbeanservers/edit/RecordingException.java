package weblogic.management.mbeanservers.edit;

public class RecordingException extends EditException {
   public RecordingException(String message) {
      super(message);
   }

   public RecordingException(Throwable t) {
      super(t);
   }

   public RecordingException(String message, Throwable t) {
      super(message, t);
   }
}
