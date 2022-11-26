package weblogic.servlet.internal;

import java.io.IOException;

public class MaxPostSizeExceededException extends IOException {
   public MaxPostSizeExceededException() {
   }

   public MaxPostSizeExceededException(String message) {
      super(message);
   }

   public MaxPostSizeExceededException(String message, Throwable cause) {
      super(message, cause);
   }

   public MaxPostSizeExceededException(Throwable cause) {
      super(cause);
   }

   public MaxPostSizeExceededException(int size, int maxSize) {
      super("Incoming message of size: '" + size + "' bytes exceeds the configured maximum of: '" + maxSize + "' bytes");
   }

   public MaxPostSizeExceededException(int size, int maxSize, String protocol) {
      super("Incoming message of size: '" + size + "' bytes exceeds the configured maximum of: '" + maxSize + "' bytes for protocol: '" + protocol + "'");
   }
}
