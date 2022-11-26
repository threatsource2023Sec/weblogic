package weblogic.connector.exception;

import javax.resource.spi.ApplicationServerInternalException;
import javax.resource.spi.RetryableException;

public class RetryableApplicationServerInternalException extends ApplicationServerInternalException implements RetryableException {
   private static final long serialVersionUID = 1L;

   public RetryableApplicationServerInternalException() {
   }

   public RetryableApplicationServerInternalException(String message, String arg1) {
      super(message, arg1);
   }

   public RetryableApplicationServerInternalException(String message, Throwable arg1) {
      super(message, arg1);
   }

   public RetryableApplicationServerInternalException(String message) {
      super(message);
   }

   public RetryableApplicationServerInternalException(Throwable message) {
      super(message);
   }
}
