package javax.resource.spi;

import javax.resource.ResourceException;

public class CommException extends ResourceException {
   public CommException() {
   }

   public CommException(String message) {
      super(message);
   }

   public CommException(Throwable cause) {
      super(cause);
   }

   public CommException(String message, Throwable cause) {
      super(message, cause);
   }

   public CommException(String message, String errorCode) {
      super(message, errorCode);
   }
}
