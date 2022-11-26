package weblogic.server.embed;

public class EmbeddedServerException extends Exception {
   public EmbeddedServerException(String message) {
      super(message);
   }

   public EmbeddedServerException(String message, Throwable cause) {
      super(message, cause);
   }
}
