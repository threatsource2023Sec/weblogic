package weblogic.servlet.spi;

public class HttpServerException extends Exception {
   private static final long serialVersionUID = -2268704781666033114L;

   public HttpServerException() {
   }

   public HttpServerException(String message) {
      super(message);
   }

   public HttpServerException(Throwable cause) {
      super(cause);
   }

   public HttpServerException(String message, Throwable cause) {
      super(message, cause);
   }
}
