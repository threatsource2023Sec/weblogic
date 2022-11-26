package weblogic.application.utils;

public class StateChangeException extends Exception {
   public StateChangeException() {
   }

   public StateChangeException(String msg) {
      super(msg);
   }

   public StateChangeException(Throwable th) {
      super(th);
   }

   public StateChangeException(String msg, Throwable th) {
      super(msg, th);
   }
}
