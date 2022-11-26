package weblogic.websocket;

/** @deprecated */
@Deprecated
public class HandshakeException extends WebSocketException {
   public static final int PROTOCOL_ERROR = 1;
   public static final int UNSUPPORTED_VERSION = 2;
   private final int code;

   public HandshakeException(String message) {
      this(1, message);
   }

   public HandshakeException(int code, String message) {
      super(message);
      this.code = code;
   }

   public int getCode() {
      return this.code;
   }
}
