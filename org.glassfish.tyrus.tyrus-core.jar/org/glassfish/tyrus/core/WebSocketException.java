package org.glassfish.tyrus.core;

import javax.websocket.CloseReason;

public abstract class WebSocketException extends RuntimeException {
   public WebSocketException(String message) {
      super(message);
   }

   public abstract CloseReason getCloseReason();
}
