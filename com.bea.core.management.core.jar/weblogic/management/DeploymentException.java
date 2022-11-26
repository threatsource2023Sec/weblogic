package weblogic.management;

public class DeploymentException extends ManagementException {
   static final long serialVersionUID = 4025521300339146724L;

   public DeploymentException(String message, Throwable t) {
      super(message, t);
   }

   public DeploymentException(Throwable t) {
      super(t);
   }

   public DeploymentException(String message) {
      super(message);
   }
}
