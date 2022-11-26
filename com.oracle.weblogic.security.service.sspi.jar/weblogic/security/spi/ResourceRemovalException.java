package weblogic.security.spi;

public class ResourceRemovalException extends SecuritySpiException {
   public ResourceRemovalException() {
   }

   public ResourceRemovalException(String msg) {
      super(msg);
   }

   public ResourceRemovalException(Throwable nested) {
      super(nested);
   }

   public ResourceRemovalException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
