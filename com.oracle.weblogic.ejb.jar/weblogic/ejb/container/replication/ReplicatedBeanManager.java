package weblogic.ejb.container.replication;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.cluster.replication.ResourceGroupKey;
import weblogic.rmi.extensions.server.RemoteReference;

public interface ReplicatedBeanManager {
   void becomePrimary(Object var1);

   Remote createSecondary(Object var1);

   Remote createSecondaryForBI(Object var1, Class var2);

   void removeSecondary(Object var1);

   void updateSecondary(Object var1, Serializable var2);

   void unregisterROIDs();

   RemoteReference getPrimaryRemoteRef(Object var1) throws RemoteException;

   RemoteReference getSecondaryRemoteRef(Object var1, boolean var2) throws RemoteException;

   void secondaryCreatedForFullState(Object var1, ResourceGroupKey var2);
}
