package weblogic.wtc.jatmi;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface TuxedoService extends EJBObject {
   Reply service(TPServiceInformation var1) throws TPException, TPReplyException, RemoteException;
}
