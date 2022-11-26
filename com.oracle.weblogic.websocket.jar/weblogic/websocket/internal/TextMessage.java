package weblogic.websocket.internal;

import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketListener;

class TextMessage extends AbstractWebSocketMessage {
   public TextMessage(byte[] data) {
      super(data, WebSocketMessage.Type.TEXT, true);
      if (this.binaryData != null) {
         this.textData = Utf8Utils.decodePayload(this.binaryData);
      }

   }

   public void process(WebSocketConnection connection, WebSocketListener listener) {
      listener.onMessage(connection, this.getTextData());
   }
}
