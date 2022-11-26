package weblogic.websocket;

/** @deprecated */
@Deprecated
public class WebSocketException extends RuntimeException {
   public WebSocketException(String message) {
      super(message);
   }

   public WebSocketException(String message, Throwable cause) {
      super(message, cause);
   }

   public WebSocketException(Throwable throwable) {
      super(throwable);
   }
}
