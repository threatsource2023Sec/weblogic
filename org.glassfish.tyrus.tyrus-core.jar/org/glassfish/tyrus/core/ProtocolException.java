package org.glassfish.tyrus.core;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;

public class ProtocolException extends WebSocketException {
   private final String reasonPhrase;

   public ProtocolException(String reasonPhrase) {
      super(reasonPhrase);
      this.reasonPhrase = reasonPhrase;
   }

   public CloseReason getCloseReason() {
      return new CloseReason(CloseCodes.PROTOCOL_ERROR, this.reasonPhrase);
   }
}
