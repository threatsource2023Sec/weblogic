package weblogic.servlet.http2.hpack;

public class HpackException extends Exception {
   public HpackException(String message) {
      super(message);
   }

   public HpackException(String message, Throwable cause) {
      super(message, cause);
   }
}
