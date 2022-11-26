package weblogic.servlet.http2;

public abstract class HTTP2Exception extends Exception {
   private static final long serialVersionUID = 1L;
   private final int errorCode;

   public HTTP2Exception(String msg, int errorCode) {
      super(msg);
      this.errorCode = errorCode;
   }

   public HTTP2Exception(String msg, int errorCode, Throwable cause) {
      super(msg, cause);
      this.errorCode = errorCode;
   }

   public int getError() {
      return this.errorCode;
   }
}
