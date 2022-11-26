package weblogic.websocket.internal;

import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketListener;

class BinaryMessage extends AbstractWebSocketMessage {
   public BinaryMessage(byte[] data) {
      super(data, WebSocketMessage.Type.BINARY, true);
   }

   public void process(WebSocketConnection connection, WebSocketListener listener) {
      listener.onMessage(connection, this.getBinaryData());
   }
}
