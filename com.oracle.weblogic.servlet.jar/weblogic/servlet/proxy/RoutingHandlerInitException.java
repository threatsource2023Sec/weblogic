package weblogic.servlet.proxy;

public class RoutingHandlerInitException extends RuntimeException {
   public RoutingHandlerInitException(String message, Throwable error) {
      super(message, error);
   }

   public RoutingHandlerInitException(String message) {
      super(message);
   }
}
