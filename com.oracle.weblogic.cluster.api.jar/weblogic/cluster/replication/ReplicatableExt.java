package weblogic.cluster.replication;

import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.RemoteReference;

public interface ReplicatableExt extends Replicatable {
   RemoteReference getPrimaryRemoteRef(ROID var1) throws RemoteException;

   RemoteReference getSecondaryRemoteRef(ROID var1, boolean var2) throws RemoteException;

   void secondaryCreatedForFullState(ROID var1, ResourceGroupKey var2);
}
