package weblogic.ejb20.cache;

import java.rmi.RemoteException;

public final class CacheFullException extends RemoteException {
   private static final long serialVersionUID = 4801611082075828086L;

   public CacheFullException() {
   }

   public CacheFullException(String msg) {
      super(msg);
   }

   public CacheFullException(String msg, Throwable th) {
      super(msg, th);
   }
}
