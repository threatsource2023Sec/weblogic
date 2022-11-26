package weblogic.cluster.replication;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;

@Contract
public interface ReplicationServices {
   ROInfo register(Replicatable var1);

   ROInfo add(ROID var1, Replicatable var2);

   Replicatable registerLocally(HostID var1, ROID var2, Object var3) throws RemoteException;

   Replicatable lookup(ROID var1, Object var2) throws NotFoundException;

   Replicatable invalidationLookup(ROID var1, Object var2) throws NotFoundException;

   Object getSecondaryInfo(ROID var1) throws NotFoundException;

   RemoteReference lookupReplicaRemoteRef(ROID var1, Object var2, int var3, boolean var4) throws NotFoundException, RemoteException;

   void unregister(ROID var1, Object var2);

   void unregister(ROID[] var1, Object var2);

   void removeOrphanedSecondary(ROID var1, Object var2);

   Object updateSecondary(ROID var1, Serializable var2, Object var3) throws NotFoundException;

   Object updateSecondary(ROID var1, Serializable var2, Object var3, ResourceGroupKey var4) throws NotFoundException;

   Object copyUpdateSecondary(ROID var1, Serializable var2, Object var3, ResourceGroupKey var4) throws NotFoundException;

   Object copyUpdateSecondaryForZDT(ROID var1, Serializable var2, Object var3) throws NotFoundException;

   QuerySessionResponseMessage sendQuerySessionRequest(QuerySessionRequestMessage var1, int var2);

   long getPrimaryCount();

   long getSecondaryCount();

   String[] getSecondaryDistributionNames();

   void sync();

   void replicateOnShutdown();

   void ensureFullStateReplicated(List var1);

   void localCleanupOnPartitionShutdown(List var1, Object var2);

   boolean hasSecondaryServer(ROID var1) throws NotFoundException;

   HostID[] getPrimarySecondaryHosts(ROID var1);
}
