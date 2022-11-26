package weblogic.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.ResourceGroupLifecycleException;

@Contract
public interface RemoteLifeCycleOperations extends Remote {
   void shutdown() throws ServerLifecycleException, RemoteException;

   void shutdown(int var1, boolean var2) throws ServerLifecycleException, RemoteException;

   void shutdown(int var1, boolean var2, boolean var3) throws ServerLifecycleException, RemoteException;

   void forceShutdown() throws ServerLifecycleException, RemoteException;

   void suspend() throws ServerLifecycleException, RemoteException;

   void suspend(int var1, boolean var2) throws ServerLifecycleException, RemoteException;

   void forceSuspend() throws ServerLifecycleException, RemoteException;

   void resume() throws ServerLifecycleException, RemoteException;

   String getState() throws RemoteException;

   void setState(String var1, String var2) throws RemoteException;

   String getWeblogicHome() throws RemoteException;

   String getMiddlewareHome() throws RemoteException;

   void shutDownPartition(String var1, int var2, boolean var3, boolean var4, String... var5) throws PartitionLifeCycleException, RemoteException;

   void forceShutDownPartition(String var1, String... var2) throws PartitionLifeCycleException, RemoteException;

   void suspendPartition(String var1, int var2, boolean var3, String... var4) throws PartitionLifeCycleException, RemoteException;

   void forceSuspendPartition(String var1, String... var2) throws PartitionLifeCycleException, RemoteException;

   void startPartition(String var1, String var2, boolean var3, String... var4) throws PartitionLifeCycleException, RemoteException;

   void resumePartition(String var1, String... var2) throws PartitionLifeCycleException, RemoteException;

   void haltPartition(String var1, String var2, String... var3) throws PartitionLifeCycleException, RemoteException;

   void bootPartition(String var1, String var2, String... var3) throws PartitionLifeCycleException, RemoteException;

   void forceRestartPartition(String var1, String var2, String... var3) throws PartitionLifeCycleException, RemoteException;

   void shutDownResourceGroup(String var1, String var2, int var3, boolean var4, boolean var5, String... var6) throws ResourceGroupLifecycleException, RemoteException;

   void forceShutDownResourceGroup(String var1, String var2, String... var3) throws ResourceGroupLifecycleException, RemoteException;

   void suspendResourceGroup(String var1, String var2, int var3, boolean var4, String... var5) throws ResourceGroupLifecycleException, RemoteException;

   void forceSuspendResourceGroup(String var1, String var2, String... var3) throws ResourceGroupLifecycleException, RemoteException;

   void startResourceGroup(String var1, String var2, boolean var3, String... var4) throws ResourceGroupLifecycleException, RemoteException;

   void resumeResourceGroup(String var1, String var2, String... var3) throws ResourceGroupLifecycleException, RemoteException;

   void setDesiredPartitionState(String var1, String var2, String... var3) throws PartitionLifeCycleException, RemoteException;

   void setDesiredResourceGroupState(String var1, String var2, String var3, String... var4) throws ResourceGroupLifecycleException, RemoteException;

   Map getRuntimeStates();

   void setInvalid(String var1) throws RemoteException;
}
