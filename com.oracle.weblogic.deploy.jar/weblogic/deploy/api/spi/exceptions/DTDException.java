package weblogic.deploy.api.spi.exceptions;

public class DTDException extends Exception {
   public DTDException() {
   }

   public DTDException(String dduri) {
      super(dduri);
   }

   public String getDD() {
      return this.getMessage();
   }
}
