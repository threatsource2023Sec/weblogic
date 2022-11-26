package weblogic.osgi;

public class OSGiException extends Exception {
   private static final long serialVersionUID = -516223884057939912L;

   public OSGiException() {
   }

   public OSGiException(String message) {
      super(message);
   }

   public OSGiException(Throwable cause) {
      super(cause);
   }

   public OSGiException(String message, Throwable cause) {
      super(message, cause);
   }
}
