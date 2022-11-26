package javax.enterprise.inject;

public class CreationException extends InjectionException {
   private static final long serialVersionUID = 1002854668862145298L;

   public CreationException() {
   }

   public CreationException(String message) {
      super(message);
   }

   public CreationException(Throwable cause) {
      super(cause);
   }

   public CreationException(String message, Throwable cause) {
      super(message, cause);
   }
}
