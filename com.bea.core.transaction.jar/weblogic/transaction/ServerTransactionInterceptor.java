package weblogic.transaction;

import java.rmi.RemoteException;

public interface ServerTransactionInterceptor extends TransactionInterceptor {
   Object sendRequest(Object var1) throws RemoteException;
}
