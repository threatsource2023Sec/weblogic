package weblogic.websocket.internal;

import java.io.IOException;
import weblogic.websocket.ClosingMessage;
import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketListener;

class ClosingMessageImpl extends AbstractWebSocketMessage implements ClosingMessage {
   private int code;

   public ClosingMessageImpl(int code) {
      super((byte[])null, WebSocketMessage.Type.CLOSING, true);
      this.code = code;
   }

   public ClosingMessageImpl(byte[] data) {
      super(data, WebSocketMessage.Type.CLOSING, true);
      this.code = this.parseCode(data);
      if (this.binaryData != null) {
         this.textData = Utf8Utils.decodePayload(this.binaryData);
      }

   }

   protected byte[] parsePayload(byte[] data) {
      if (data != null) {
         if (data.length > 2) {
            byte[] reason = new byte[data.length - 2];
            System.arraycopy(data, 2, reason, 0, data.length - 2);
            return reason;
         }

         if (data.length == 1) {
            throw new WebSocketMessageParsingException(1002, "Invalid Payload");
         }
      }

      return null;
   }

   private int parseCode(byte[] data) {
      if (data != null && data.length >= 2) {
         int code = makeInt(data[0], data[1]);
         if (!this.isValidCode(code)) {
            throw new WebSocketMessageParsingException(1002, "Invalid Close Code");
         } else {
            return code;
         }
      } else {
         return -1;
      }
   }

   public int getStatusCode() {
      return this.code;
   }

   public String getReason() {
      return this.getTextData();
   }

   public void process(WebSocketConnection connection, WebSocketListener listener) throws IOException {
      int responseCode = this.code == -1 ? 1000 : this.code;
      listener.onClose(connection, this);
      connection.close(responseCode);
   }

   private boolean isValidCode(int code) {
      return (code < 0 || code > 999) && (code < 1004 || code > 1006) && (code < 1012 || code > 2999) && code <= 4999 && code >= -1;
   }

   private static int makeInt(byte b1, byte b0) {
      return (b1 & 255) << 8 | b0 & 255;
   }
}
