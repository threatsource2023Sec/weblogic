package javax.websocket;

public class DeploymentException extends Exception {
   private static final long serialVersionUID = 7576860738144220015L;

   public DeploymentException(String message) {
      super(message);
   }

   public DeploymentException(String message, Throwable cause) {
      super(message, cause);
   }
}
