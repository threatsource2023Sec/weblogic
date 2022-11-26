package org.glassfish.grizzly;

import java.io.IOException;

public class CloseReason {
   private static final IOException LOCALLY_CLOSED = new IOException("Locally closed");
   private static final IOException REMOTELY_CLOSED;
   public static final CloseReason LOCALLY_CLOSED_REASON;
   public static final CloseReason REMOTELY_CLOSED_REASON;
   private final CloseType type;
   private final IOException cause;

   public CloseReason(CloseType type, IOException cause) {
      this.type = type;
      this.cause = cause != null ? cause : (type == CloseType.LOCALLY ? LOCALLY_CLOSED : REMOTELY_CLOSED);
   }

   public CloseType getType() {
      return this.type;
   }

   public IOException getCause() {
      return this.cause;
   }

   static {
      LOCALLY_CLOSED.setStackTrace(new StackTraceElement[0]);
      REMOTELY_CLOSED = new IOException("Remotely closed");
      REMOTELY_CLOSED.setStackTrace(new StackTraceElement[0]);
      LOCALLY_CLOSED_REASON = new CloseReason(CloseType.LOCALLY, LOCALLY_CLOSED);
      REMOTELY_CLOSED_REASON = new CloseReason(CloseType.REMOTELY, REMOTELY_CLOSED);
   }
}
