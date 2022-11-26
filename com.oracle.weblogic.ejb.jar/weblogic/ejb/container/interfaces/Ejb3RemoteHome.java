package weblogic.ejb.container.interfaces;

import java.rmi.RemoteException;
import weblogic.ejb20.interfaces.RemoteHome;

public interface Ejb3RemoteHome extends RemoteHome {
   Object getBusinessImpl(Object var1, String var2) throws RemoteException;

   Object getBusinessImpl(Object var1, Class var2) throws RemoteException;
}
