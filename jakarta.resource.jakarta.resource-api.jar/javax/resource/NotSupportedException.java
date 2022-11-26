package javax.resource;

public class NotSupportedException extends ResourceException {
   public NotSupportedException() {
   }

   public NotSupportedException(String message) {
      super(message);
   }

   public NotSupportedException(Throwable cause) {
      super(cause);
   }

   public NotSupportedException(String message, Throwable cause) {
      super(message, cause);
   }

   public NotSupportedException(String message, String errorCode) {
      super(message, errorCode);
   }
}
