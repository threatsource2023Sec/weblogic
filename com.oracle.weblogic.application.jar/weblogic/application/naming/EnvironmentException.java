package weblogic.application.naming;

import weblogic.utils.NestedException;

public final class EnvironmentException extends NestedException {
   private static final long serialVersionUID = 5585967321340200145L;

   public EnvironmentException() {
   }

   public EnvironmentException(String msg) {
      super(msg);
   }

   public EnvironmentException(Throwable th) {
      super(th);
   }

   public EnvironmentException(String msg, Throwable th) {
      super(msg, th);
   }
}
