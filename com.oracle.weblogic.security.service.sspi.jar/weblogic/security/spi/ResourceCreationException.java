package weblogic.security.spi;

public class ResourceCreationException extends SecuritySpiException {
   public ResourceCreationException() {
   }

   public ResourceCreationException(String msg) {
      super(msg);
   }

   public ResourceCreationException(Throwable nested) {
      super(nested);
   }

   public ResourceCreationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
