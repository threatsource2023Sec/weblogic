package weblogic.transaction.internal;

import java.rmi.RemoteException;
import javax.transaction.xa.Xid;

public interface CoordinatorOneway2 extends CoordinatorOneway {
   void ackCommit(Xid var1, String var2, String[] var3) throws RemoteException;

   void nakCommit(Xid var1, String var2, short var3, String var4, String[] var5, String[] var6) throws RemoteException;

   void ackRollback(Xid var1, String var2, String[] var3) throws RemoteException;

   void nakRollback(Xid var1, String var2, short var3, String var4, String[] var5, String[] var6) throws RemoteException;
}
