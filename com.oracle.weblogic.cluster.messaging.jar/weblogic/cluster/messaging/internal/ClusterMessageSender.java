package weblogic.cluster.messaging.internal;

import java.rmi.RemoteException;

public interface ClusterMessageSender {
   ClusterResponse[] send(ClusterMessage var1, ServerInformation[] var2) throws ClusterMessageProcessingException;

   ClusterResponse send(ClusterMessage var1, ServerInformation var2) throws RemoteException;

   ClusterResponse send(ClusterMessage var1, String var2) throws RemoteException;
}
