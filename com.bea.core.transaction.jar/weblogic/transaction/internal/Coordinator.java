package weblogic.transaction.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

public interface Coordinator extends Remote {
   void commit(PropagationContext var1) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, SystemException, IllegalStateException, RemoteException;

   void rollback(PropagationContext var1) throws SystemException, IllegalStateException, RemoteException;
}
