package weblogic.transaction.internal;

import java.rmi.RemoteException;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;

public interface Coordinator3 extends Coordinator2 {
   void forceGlobalRollback(Xid var1) throws SystemException, RemoteException;

   void forceGlobalCommit(Xid var1) throws SystemException, RemoteException;
}
