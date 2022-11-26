package weblogic.transaction.internal;

import java.rmi.RemoteException;
import javax.transaction.xa.Xid;
import weblogic.security.acl.internal.AuthenticatedUser;

public interface SubCoordinatorOneway2 extends SubCoordinatorOneway {
   void startCommit(Xid var1, String var2, String[] var3, boolean var4, boolean var5, AuthenticatedUser var6) throws RemoteException;

   void startRollback(Xid var1, String var2, String[] var3, AuthenticatedUser var4) throws RemoteException;
}
