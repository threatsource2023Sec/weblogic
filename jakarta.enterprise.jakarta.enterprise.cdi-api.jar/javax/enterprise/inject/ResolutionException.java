package javax.enterprise.inject;

public class ResolutionException extends InjectionException {
   private static final long serialVersionUID = -6280627846071966243L;

   public ResolutionException() {
   }

   public ResolutionException(String message, Throwable cause) {
      super(message, cause);
   }

   public ResolutionException(String message) {
      super(message);
   }

   public ResolutionException(Throwable cause) {
      super(cause);
   }
}
