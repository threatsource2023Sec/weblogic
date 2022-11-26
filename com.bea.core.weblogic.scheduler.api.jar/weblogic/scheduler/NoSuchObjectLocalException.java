package weblogic.scheduler;

public class NoSuchObjectLocalException extends Exception {
   public NoSuchObjectLocalException() {
   }

   public NoSuchObjectLocalException(String s) {
      super(s);
   }

   public NoSuchObjectLocalException(String s, Exception e) {
      super(s, e);
   }
}
