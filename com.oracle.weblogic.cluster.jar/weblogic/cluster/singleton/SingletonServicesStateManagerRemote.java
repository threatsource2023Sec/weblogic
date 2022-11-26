package weblogic.cluster.singleton;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SingletonServicesStateManagerRemote extends Remote {
   String JNDI_NAME = "weblogic.cluster.singleton.SingletonServicesStateManager";
   int STORE_STATE = 1001;
   String SVCNAME_PARAM = "SvcName";
   String STATE_PARAM = "SvcState";
   String SENDER_PARAM = "Sender";

   Serializable invoke(int var1, Serializable var2) throws RemoteException;
}
