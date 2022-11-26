package weblogic.cache;

import java.rmi.RemoteException;

public class RemoteCacheException extends RemoteException {
   public RemoteCacheException(String s) {
      super(s);
   }

   public RemoteCacheException(String s, Throwable th) {
      super(s, th);
   }
}
