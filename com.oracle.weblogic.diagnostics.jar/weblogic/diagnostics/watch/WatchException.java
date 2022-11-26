package weblogic.diagnostics.watch;

import weblogic.diagnostics.type.DiagnosticException;

public class WatchException extends DiagnosticException {
   public WatchException() {
   }

   public WatchException(String msg) {
      super(msg);
   }

   public WatchException(Throwable t) {
      super(t);
   }

   public WatchException(String msg, Throwable t) {
      super(msg, t);
   }
}
