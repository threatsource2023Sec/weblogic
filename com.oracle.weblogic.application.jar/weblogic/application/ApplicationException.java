package weblogic.application;

import weblogic.utils.ErrorCollectionException;

public class ApplicationException extends ErrorCollectionException {
   public ApplicationException() {
   }

   public ApplicationException(String msg) {
      super(msg);
   }

   public ApplicationException(Throwable th) {
      super(th);
   }

   public ApplicationException(String msg, Throwable th) {
      super(msg, th);
   }
}
