package weblogic.management;

/** @deprecated */
@Deprecated
public class DeferredDeploymentException extends DeploymentException {
   private static final long serialVersionUID = -8397075368277243930L;

   public DeferredDeploymentException(String msg) {
      super(msg);
   }

   public DeferredDeploymentException(Throwable nested) {
      super(nested);
   }

   public DeferredDeploymentException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
