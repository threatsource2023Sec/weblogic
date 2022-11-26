package weblogic.transaction.internal;

import java.rmi.RemoteException;
import javax.transaction.xa.Xid;

public interface SubCoordinatorOneway3 extends SubCoordinatorOneway2 {
   void forceLocalRollback(Xid var1) throws RemoteException;

   void forceLocalCommit(Xid var1) throws RemoteException;
}
