package weblogic.management.utils;

public class ManagedDeploymentNotPreparedException extends Exception {
   public ManagedDeploymentNotPreparedException(String msg) {
      super(msg);
   }

   public ManagedDeploymentNotPreparedException(String msg, Throwable t) {
      super(msg, t);
   }

   public ManagedDeploymentNotPreparedException(Throwable t) {
      super(t);
   }
}
