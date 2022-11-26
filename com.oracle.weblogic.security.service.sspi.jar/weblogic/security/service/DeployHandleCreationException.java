package weblogic.security.service;

public class DeployHandleCreationException extends SecurityServiceException {
   public DeployHandleCreationException() {
   }

   public DeployHandleCreationException(String msg) {
      super(msg);
   }

   public DeployHandleCreationException(Throwable nested) {
      super(nested);
   }

   public DeployHandleCreationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
