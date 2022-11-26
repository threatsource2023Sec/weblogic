package weblogic.socket;

import java.io.IOException;

public final class MaxMessageSizeExceededException extends IOException {
   private static final long serialVersionUID = 3910659052490563444L;

   public MaxMessageSizeExceededException(int size, int maxSize) {
      super("Incoming message of size: '" + size + "' bytes exceeds the configured maximum of: '" + maxSize + "' bytes");
   }

   public MaxMessageSizeExceededException(int size, int maxSize, String protocol) {
      super("Incoming message of size: '" + size + "' bytes exceeds the configured maximum of: '" + maxSize + "' bytes for protocol: '" + protocol + "'");
   }
}
