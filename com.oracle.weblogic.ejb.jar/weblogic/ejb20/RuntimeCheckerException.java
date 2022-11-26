package weblogic.ejb20;

import java.rmi.RemoteException;

public final class RuntimeCheckerException extends RemoteException {
   private static final long serialVersionUID = 2689241955047378499L;

   public RuntimeCheckerException() {
   }

   public RuntimeCheckerException(String s) {
      super(s);
   }

   public RuntimeCheckerException(String s, Throwable th) {
      super(s, th);
   }
}
