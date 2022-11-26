package weblogic.websocket.internal;

import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketListener;

class PongMessage extends AbstractWebSocketMessage {
   public PongMessage(byte[] data) {
      super(data, WebSocketMessage.Type.PONG, true);
   }

   public void process(WebSocketConnection connection, WebSocketListener listener) {
      listener.onPong(connection, this.getBinaryData());
   }
}
