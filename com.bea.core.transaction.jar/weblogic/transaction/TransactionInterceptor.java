package weblogic.transaction;

import java.rmi.RemoteException;

public interface TransactionInterceptor {
   Object sendRequest(Object var1) throws RemoteException;

   Object sendResponse(Object var1) throws RemoteException;

   void receiveRequest(Object var1) throws RemoteException;

   void receiveResponse(Object var1) throws RemoteException;

   void dispatchRequest(Object var1) throws RemoteException;

   void receiveAsyncResponse(Object var1) throws RemoteException;

   boolean isParticipant(String var1);

   boolean needsInterception();
}
