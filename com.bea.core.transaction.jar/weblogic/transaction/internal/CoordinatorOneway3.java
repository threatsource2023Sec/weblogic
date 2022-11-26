package weblogic.transaction.internal;

import java.rmi.RemoteException;
import javax.transaction.xa.Xid;

public interface CoordinatorOneway3 extends CoordinatorOneway2 {
   void checkStatus(String var1, Xid[] var2, String var3) throws RemoteException;
}
