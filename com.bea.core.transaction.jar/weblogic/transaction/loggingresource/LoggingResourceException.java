package weblogic.transaction.loggingresource;

public class LoggingResourceException extends Exception {
   public LoggingResourceException() {
   }

   public LoggingResourceException(String s) {
      super(s);
   }

   public LoggingResourceException(Throwable t) {
      super(t);
   }
}
