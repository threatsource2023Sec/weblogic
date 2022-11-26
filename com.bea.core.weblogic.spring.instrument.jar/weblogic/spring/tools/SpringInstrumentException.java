package weblogic.spring.tools;

public class SpringInstrumentException extends RuntimeException {
   private static final long serialVersionUID = -4513483321309576256L;

   public SpringInstrumentException() {
   }

   public SpringInstrumentException(String message) {
      super(message);
   }

   public SpringInstrumentException(String message, Throwable cause) {
      super(message, cause);
   }

   public SpringInstrumentException(Throwable cause) {
      super(cause);
   }
}
