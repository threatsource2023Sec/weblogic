package weblogic.servlet.http2;

public class ProtocalException extends HTTP2Exception {
   private static final long serialVersionUID = 1L;

   public ProtocalException(String msg, int errorCode) {
      super(msg, errorCode);
   }
}
