package weblogic.transaction.internal;

import java.rmi.RemoteException;
import java.util.Map;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public interface Coordinator2 extends Coordinator {
   Map getProperties();

   int xaPrepare(PropagationContext var1) throws RemoteException, XAException;

   void xaCommit(Xid var1) throws RemoteException, XAException;

   void xaRollback(Xid var1) throws RemoteException, XAException;

   void xaForget(Xid var1) throws RemoteException, XAException;

   /** @deprecated */
   @Deprecated
   Xid[] xaRecover() throws RemoteException, XAException;

   Xid[] xaRecover(int var1) throws RemoteException, XAException;
}
