package weblogic.ejb.spi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.rmi.RemoteObject;

public interface BusinessObject extends Remote, RemoteObject {
   BusinessHandle _WL_getBusinessObjectHandle() throws RemoteException;
}
