package weblogic.ejb.container.ejbc;

import weblogic.utils.NestedException;

public class EJBCException extends NestedException {
   public EJBCException() {
   }

   public EJBCException(String msg) {
      super(msg);
   }

   public EJBCException(Throwable th) {
      super(th);
   }

   public EJBCException(String msg, Throwable th) {
      super(msg, th);
   }
}
