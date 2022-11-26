package weblogic.management.logging;

public class DomainLogHandlerException extends Exception {
   public DomainLogHandlerException(String msg) {
      super(msg);
   }

   public DomainLogHandlerException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
