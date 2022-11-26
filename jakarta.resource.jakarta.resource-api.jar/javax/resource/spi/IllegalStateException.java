package javax.resource.spi;

import javax.resource.ResourceException;

public class IllegalStateException extends ResourceException {
   public IllegalStateException() {
   }

   public IllegalStateException(String message) {
      super(message);
   }

   public IllegalStateException(Throwable cause) {
      super(cause);
   }

   public IllegalStateException(String message, Throwable cause) {
      super(message, cause);
   }

   public IllegalStateException(String message, String errorCode) {
      super(message, errorCode);
   }
}
