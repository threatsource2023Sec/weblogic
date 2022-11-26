package weblogic.management.utils;

public class ManagedServiceShutdownException extends Exception {
   public ManagedServiceShutdownException(String msg) {
      super(msg);
   }

   public ManagedServiceShutdownException(String msg, Throwable t) {
      super(msg, t);
   }

   public ManagedServiceShutdownException(Throwable t) {
      super(t);
   }
}
