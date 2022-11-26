package weblogic.wtc.jatmi;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface TuxedoServiceHome extends EJBHome {
   TuxedoService create() throws CreateException, RemoteException;
}
