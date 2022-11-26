package weblogic.t3.srvr;

import weblogic.utils.NestedException;

final class FatalStartupException extends NestedException {
   private static final long serialVersionUID = -87776756165832496L;

   public FatalStartupException() {
   }

   public FatalStartupException(String msg) {
      super(msg);
   }

   public FatalStartupException(Throwable th) {
      super(th);
   }

   public FatalStartupException(String msg, Throwable th) {
      super(msg, th);
   }
}
