package weblogic.ejb.spi;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface BusinessHandle extends Serializable {
   BusinessObject getBusinessObject() throws RemoteException;
}
