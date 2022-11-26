package weblogic.socket;

import java.io.IOException;

public final class MaxConnectionsExceededException extends IOException {
   private static final long serialVersionUID = 3910659052490563444L;

   public MaxConnectionsExceededException(int maxSize) {
      super("Incoming connection exceeds the configured maximum of: '" + maxSize + "' connections");
   }

   public MaxConnectionsExceededException(int maxSize, String channel) {
      super("Incoming connection exceeds the configured maximum of: '" + maxSize + "' connections for channel: '" + channel + "'");
   }
}
