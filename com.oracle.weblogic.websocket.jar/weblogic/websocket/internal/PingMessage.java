package weblogic.websocket.internal;

import java.io.IOException;
import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketListener;

class PingMessage extends AbstractWebSocketMessage {
   public PingMessage(byte[] data) {
      super(data, WebSocketMessage.Type.PING, true);
   }

   public void process(WebSocketConnection connection, WebSocketListener listener) throws IOException {
      listener.onPing(connection, this.getBinaryData());
      connection.sendPong(this.getBinaryData());
   }
}
