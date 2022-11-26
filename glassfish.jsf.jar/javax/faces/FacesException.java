package javax.faces;

public class FacesException extends RuntimeException {
   private static final long serialVersionUID = 3501800507902565991L;
   private Throwable cause = null;

   public FacesException() {
   }

   public FacesException(String message) {
      super(message);
   }

   public FacesException(Throwable cause) {
      super(cause == null ? null : cause.toString());
      this.cause = cause;
   }

   public FacesException(String message, Throwable cause) {
      super(message);
      this.cause = cause;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
