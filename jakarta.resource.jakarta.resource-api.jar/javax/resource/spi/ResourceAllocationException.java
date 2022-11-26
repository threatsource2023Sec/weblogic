package javax.resource.spi;

import javax.resource.ResourceException;

public class ResourceAllocationException extends ResourceException {
   public ResourceAllocationException() {
   }

   public ResourceAllocationException(String message) {
      super(message);
   }

   public ResourceAllocationException(Throwable cause) {
      super(cause);
   }

   public ResourceAllocationException(String message, Throwable cause) {
      super(message, cause);
   }

   public ResourceAllocationException(String message, String errorCode) {
      super(message, errorCode);
   }
}
