package weblogic.deploy.event;

public class DeploymentVetoException extends Exception {
   public DeploymentVetoException(String msg) {
      super(msg);
   }

   public DeploymentVetoException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public DeploymentVetoException(Throwable cause) {
      super(cause);
   }
}
