package weblogic.transaction.internal;

import java.rmi.RemoteException;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;

public interface SubCoordinator2 extends SubCoordinator {
   void nonXAResourceCommit(Xid var1, boolean var2, String var3) throws SystemException, RemoteException;
}
