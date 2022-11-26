package weblogic.transaction.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import javax.transaction.xa.Xid;
import weblogic.security.acl.internal.AuthenticatedUser;

public interface SubCoordinatorOneway5 extends Remote {
   void startPrepare(Xid var1, String var2, String[] var3, int var4, Map var5) throws RemoteException;

   void startCommit(Xid var1, String var2, String[] var3, boolean var4, boolean var5, AuthenticatedUser var6, Map var7) throws RemoteException;

   void startRollback(Xid var1, String var2, String[] var3, AuthenticatedUser var4, String[] var5, Map var6) throws RemoteException;
}
