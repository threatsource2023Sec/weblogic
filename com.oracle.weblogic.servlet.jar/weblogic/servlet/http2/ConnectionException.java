package weblogic.servlet.http2;

public class ConnectionException extends HTTP2Exception {
   private static final long serialVersionUID = 1L;

   public ConnectionException(String msg, int errorCode) {
      super(msg, errorCode);
   }

   public ConnectionException(String msg, int errorCode, Throwable cause) {
      super(msg, errorCode, cause);
   }
}
