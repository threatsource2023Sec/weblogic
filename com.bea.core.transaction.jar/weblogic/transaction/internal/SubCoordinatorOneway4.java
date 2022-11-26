package weblogic.transaction.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.transaction.xa.Xid;
import weblogic.security.acl.internal.AuthenticatedUser;

public interface SubCoordinatorOneway4 extends Remote {
   void startRollback(Xid var1, String var2, String[] var3, AuthenticatedUser var4, String[] var5) throws RemoteException;
}
