package javax.resource.spi;

import javax.resource.ResourceException;

public class ResourceAdapterInternalException extends ResourceException {
   public ResourceAdapterInternalException() {
   }

   public ResourceAdapterInternalException(String message) {
      super(message);
   }

   public ResourceAdapterInternalException(Throwable cause) {
      super(cause);
   }

   public ResourceAdapterInternalException(String message, Throwable cause) {
      super(message, cause);
   }

   public ResourceAdapterInternalException(String message, String errorCode) {
      super(message, errorCode);
   }
}
