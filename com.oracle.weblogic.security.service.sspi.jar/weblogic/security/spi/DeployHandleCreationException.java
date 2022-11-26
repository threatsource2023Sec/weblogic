package weblogic.security.spi;

public class DeployHandleCreationException extends SecuritySpiException {
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
