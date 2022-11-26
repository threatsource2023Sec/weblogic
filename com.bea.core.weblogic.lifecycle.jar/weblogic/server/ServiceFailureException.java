package weblogic.server;

import weblogic.utils.ErrorCollectionException;

public final class ServiceFailureException extends ErrorCollectionException {
   private static final long serialVersionUID = -2297070152805309807L;

   public ServiceFailureException() {
   }

   public ServiceFailureException(String msg) {
      super(msg);
   }

   public ServiceFailureException(Throwable th) {
      super(th);
   }

   public ServiceFailureException(String msg, Throwable th) {
      super(msg, th);
   }
}
