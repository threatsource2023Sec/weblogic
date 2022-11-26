package javax.resource.spi;

import javax.resource.ResourceException;

public class UnavailableException extends ResourceException {
   public UnavailableException() {
   }

   public UnavailableException(String message) {
      super(message);
   }

   public UnavailableException(Throwable cause) {
      super(cause);
   }

   public UnavailableException(String message, Throwable cause) {
      super(message, cause);
   }

   public UnavailableException(String message, String errorCode) {
      super(message, errorCode);
   }
}
