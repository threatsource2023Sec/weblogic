package weblogic.security.spi;

import weblogic.utils.NestedException;

public class SecuritySpiException extends NestedException {
   public SecuritySpiException() {
   }

   public SecuritySpiException(String msg) {
      super(msg);
   }

   public SecuritySpiException(Throwable nested) {
      super(nested);
   }

   public SecuritySpiException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
