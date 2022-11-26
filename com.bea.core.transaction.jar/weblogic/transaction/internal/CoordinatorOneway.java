package weblogic.transaction.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.transaction.xa.Xid;

public interface CoordinatorOneway extends Remote {
   void checkStatus(Xid[] var1, String var2) throws RemoteException;

   void startRollback(PropagationContext var1) throws RemoteException;

   void ackPrePrepare(PropagationContext var1) throws RemoteException;

   void ackPrepare(Xid var1, String var2, int var3) throws RemoteException;

   void ackCommit(Xid var1, String var2) throws RemoteException;

   void nakCommit(Xid var1, String var2, short var3, String var4) throws RemoteException;

   void ackRollback(Xid var1, String var2) throws RemoteException;

   void nakRollback(Xid var1, String var2, short var3, String var4) throws RemoteException;
}
