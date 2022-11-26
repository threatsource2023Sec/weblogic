package weblogic.servlet.cluster.wan;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface PersistenceServiceInternal extends Remote {
   String JNDI_NAME = "weblogic/servlet/wan/persistenceservice";

   void persistState(BatchedSessionState var1) throws ServiceUnavailableException, RemoteException;

   void invalidateSessions(Set var1) throws RemoteException;
}
