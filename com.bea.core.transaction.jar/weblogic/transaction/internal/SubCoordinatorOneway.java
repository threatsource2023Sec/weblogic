package weblogic.transaction.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.transaction.xa.Xid;

public interface SubCoordinatorOneway extends Remote {
   void startPrePrepareAndChain(PropagationContext var1, int var2) throws RemoteException;

   void startPrepare(Xid var1, String var2, String[] var3, int var4) throws RemoteException;

   void startCommit(Xid var1, String var2, String[] var3, boolean var4, boolean var5) throws RemoteException;

   void startRollback(Xid var1, String var2, String[] var3) throws RemoteException;

   void startRollback(Xid[] var1) throws RemoteException;
}
