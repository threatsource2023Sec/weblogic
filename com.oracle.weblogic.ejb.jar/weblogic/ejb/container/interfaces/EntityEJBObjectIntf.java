package weblogic.ejb.container.interfaces;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EntityEJBObjectIntf extends EJBObject {
   void setPrimaryKey(Object var1) throws RemoteException;
}
