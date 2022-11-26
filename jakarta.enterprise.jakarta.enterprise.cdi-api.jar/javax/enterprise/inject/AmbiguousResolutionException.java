package javax.enterprise.inject;

public class AmbiguousResolutionException extends ResolutionException {
   private static final long serialVersionUID = -2132733164534544788L;

   public AmbiguousResolutionException() {
   }

   public AmbiguousResolutionException(String message, Throwable throwable) {
      super(message, throwable);
   }

   public AmbiguousResolutionException(String message) {
      super(message);
   }

   public AmbiguousResolutionException(Throwable throwable) {
      super(throwable);
   }
}
