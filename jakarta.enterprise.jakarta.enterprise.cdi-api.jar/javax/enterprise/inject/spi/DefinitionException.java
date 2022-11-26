package javax.enterprise.inject.spi;

public class DefinitionException extends RuntimeException {
   private static final long serialVersionUID = -2699170549782567339L;

   public DefinitionException(String message, Throwable t) {
      super(message, t);
   }

   public DefinitionException(String message) {
      super(message);
   }

   public DefinitionException(Throwable t) {
      super(t);
   }
}
