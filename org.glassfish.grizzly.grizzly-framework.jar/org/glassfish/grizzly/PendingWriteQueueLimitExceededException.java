package org.glassfish.grizzly;

import java.io.IOException;

public final class PendingWriteQueueLimitExceededException extends IOException {
   private static final long serialVersionUID = -7713985866708297095L;

   public PendingWriteQueueLimitExceededException() {
   }

   public PendingWriteQueueLimitExceededException(String message) {
      super(message);
   }
}
