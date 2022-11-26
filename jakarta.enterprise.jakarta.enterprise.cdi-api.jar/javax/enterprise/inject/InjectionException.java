package javax.enterprise.inject;

public class InjectionException extends RuntimeException {
   private static final long serialVersionUID = -2132733164534544788L;

   public InjectionException() {
   }

   public InjectionException(String message, Throwable throwable) {
      super(message, throwable);
   }

   public InjectionException(String message) {
      super(message);
   }

   public InjectionException(Throwable throwable) {
      super(throwable);
   }
}
