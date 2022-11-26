package weblogic.websocket.internal;

import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketListener;

abstract class AbstractWebSocketMessage implements WebSocketMessage {
   protected byte[] binaryData;
   protected String textData;
   protected boolean finalFragment;
   protected WebSocketMessage.Type type;

   public AbstractWebSocketMessage(byte[] data, WebSocketMessage.Type type, boolean finalFragment) {
      this.type = type;
      this.finalFragment = finalFragment;
      this.binaryData = this.parsePayload(data);
   }

   protected byte[] parsePayload(byte[] data) {
      return data;
   }

   public String getTextData() {
      return this.textData;
   }

   public byte[] getBinaryData() {
      return this.binaryData;
   }

   public WebSocketMessage.Type getType() {
      return this.type;
   }

   public boolean isFinalFragment() {
      return this.finalFragment;
   }

   public abstract void process(WebSocketConnection var1, WebSocketListener var2) throws Exception;
}
