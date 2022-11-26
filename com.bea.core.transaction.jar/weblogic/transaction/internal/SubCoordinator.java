package weblogic.transaction.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;

public interface SubCoordinator extends Remote {
   Xid[] recover(String var1, String var2) throws SystemException, RemoteException;

   void rollback(String var1, Xid[] var2) throws SystemException, RemoteException;

   void commit(String var1, Xid[] var2) throws SystemException, RemoteException;
}
