package weblogic.scheduler;

public class TimerException extends Exception {
   public TimerException() {
   }

   public TimerException(String s) {
      super(s);
   }

   public TimerException(String s, Exception e) {
      super(s, e);
   }
}
