package weblogic.ejb20.interfaces;

import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;

public interface RemoteHome extends EJBHome, weblogic.ejb.spi.RemoteHome {
   EJBObject allocateEJBObject() throws RemoteException;

   EJBObject allocateEJBObject(Object var1) throws RemoteException;

   boolean usesBeanManagedTx() throws RemoteException;

   String getIsIdenticalKey() throws RemoteException;

   void undeploy() throws RemoteException;
}
