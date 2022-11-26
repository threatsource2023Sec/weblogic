package org.glassfish.tyrus.core;

import javax.websocket.CloseReason;

class MessageTooBigException extends WebSocketException {
   private static final CloseReason CLOSE_REASON;
   private static final long serialVersionUID = -1636733948291376261L;

   MessageTooBigException(String message) {
      super(message);
   }

   public CloseReason getCloseReason() {
      return CLOSE_REASON;
   }

   static {
      CLOSE_REASON = CloseReasons.TOO_BIG.getCloseReason();
   }
}
