package weblogic.websocket.internal;

import weblogic.websocket.WebSocketException;

public class WebSocketMessageParsingException extends WebSocketException {
   private int statusCode;

   public WebSocketMessageParsingException(int statusCode, String reason) {
      super(reason);
      this.statusCode = statusCode;
   }

   public int getStatusCode() {
      return this.statusCode;
   }
}
