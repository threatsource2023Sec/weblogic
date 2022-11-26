package weblogic.rmi.extensions;

import java.rmi.RemoteException;

public interface AsyncResult {
   boolean hasResults();

   Object getObject() throws Throwable;

   void setResult(Object var1) throws RemoteException;

   void setTimeOut(long var1);
}
