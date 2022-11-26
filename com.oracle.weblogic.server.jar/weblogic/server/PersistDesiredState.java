package weblogic.server;

import java.rmi.RemoteException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.ResourceGroupLifecycleException;

public abstract class PersistDesiredState implements Runnable {
   protected final String desiredState;
   protected final String serverName;
   protected final String[] serversAffected;
   protected RemoteLifeCycleOperations remote;

   PersistDesiredState(String desiredState, String serverName, String... serversAffected) {
      this.desiredState = desiredState;
      this.serverName = serverName;
      this.serversAffected = serversAffected;
   }

   protected abstract void saveDesiredState() throws PartitionLifeCycleException, RemoteException, ResourceGroupLifecycleException;

   public void run() {
      try {
         this.remote = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(this.serverName);
         if (this.remote != null) {
            this.saveDesiredState();
         }
      } catch (RemoteException | ResourceGroupLifecycleException | PartitionLifeCycleException var2) {
         throw new RuntimeException(var2);
      }
   }
}
