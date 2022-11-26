package weblogic.security.spi;

public class ApplicationRemovalException extends SecuritySpiException {
   public ApplicationRemovalException() {
   }

   public ApplicationRemovalException(String msg) {
      super(msg);
   }

   public ApplicationRemovalException(Throwable nested) {
      super(nested);
   }

   public ApplicationRemovalException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
