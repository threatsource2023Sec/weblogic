package weblogic.cluster.messaging.internal;

import java.io.IOException;

public class RejectConnectionException extends IOException {
   public RejectConnectionException(String message) {
      super(message);
   }
}
