package weblogic.ejb;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.EJBHome;

public interface CachingHome extends EJBHome {
   void invalidate(Object var1) throws RemoteException;

   void invalidate(Collection var1) throws RemoteException;

   void invalidateAll() throws RemoteException;

   void invalidateLocalServer(Object var1) throws RemoteException;

   void invalidateLocalServer(Collection var1) throws RemoteException;

   void invalidateAllLocalServer() throws RemoteException;
}
