package weblogic.deploy.api.tools.deployer;

public class InvalidOptionException extends IllegalArgumentException {
   public InvalidOptionException() {
   }

   public InvalidOptionException(String s) {
      super(s);
   }

   public InvalidOptionException(String message, Throwable cause) {
      super(message, cause);
   }

   public InvalidOptionException(Throwable cause) {
      super(cause);
   }
}
