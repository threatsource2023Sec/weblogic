package javax.resource.spi;

import javax.resource.ResourceException;

public class ApplicationServerInternalException extends ResourceException {
   public ApplicationServerInternalException() {
   }

   public ApplicationServerInternalException(String message) {
      super(message);
   }

   public ApplicationServerInternalException(Throwable cause) {
      super(cause);
   }

   public ApplicationServerInternalException(String message, Throwable cause) {
      super(message, cause);
   }

   public ApplicationServerInternalException(String message, String errorCode) {
      super(message, errorCode);
   }
}
