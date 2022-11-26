package weblogic.rmi;

import weblogic.common.T3Exception;

/** @deprecated */
@Deprecated
public class RemoteException extends T3Exception {
   public Throwable detail;

   public RemoteException() {
   }

   public RemoteException(String s) {
      super(s);
   }

   public RemoteException(String s, Throwable t) {
      super(s, t);
      this.detail = t;
   }

   public String getMessage() {
      return super.getMessage();
   }
}
