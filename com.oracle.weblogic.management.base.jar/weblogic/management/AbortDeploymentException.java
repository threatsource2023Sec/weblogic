package weblogic.management;

public class AbortDeploymentException extends RuntimeException {
   public AbortDeploymentException() {
   }

   public AbortDeploymentException(String msg) {
      super(msg);
   }
}
