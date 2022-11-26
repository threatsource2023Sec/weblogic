package weblogic.servlet.internal;

public class IncompatibleSessionSerializationException extends RuntimeException {
   public IncompatibleSessionSerializationException() {
   }

   public IncompatibleSessionSerializationException(String message) {
      super(message);
   }

   public IncompatibleSessionSerializationException(String message, Throwable cause) {
      super(message, cause);
   }

   public IncompatibleSessionSerializationException(Throwable cause) {
      super(cause);
   }
}
