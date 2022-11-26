package weblogic.websocket.internal;

import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketListener;

class ContinuationMessage extends AbstractWebSocketMessage {
   private boolean text;

   public ContinuationMessage(byte[] data, boolean finalFragment, boolean text, boolean shouldDecode, Utf8Utils.DecodingContext decodingContext) {
      super(data, WebSocketMessage.Type.CONTINUATION, finalFragment);
      this.text = text || shouldDecode;
      if (this.text && this.binaryData != null) {
         this.textData = Utf8Utils.decodePayload(this.binaryData, decodingContext, finalFragment);
      }

   }

   public void process(WebSocketConnection connection, WebSocketListener listener) {
      if (this.text) {
         listener.onFragment(connection, this.isFinalFragment(), this.getTextData());
      } else {
         listener.onFragment(connection, this.isFinalFragment(), this.getBinaryData());
      }

   }
}
