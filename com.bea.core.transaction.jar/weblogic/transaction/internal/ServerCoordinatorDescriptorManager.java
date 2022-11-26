package weblogic.transaction.internal;

import java.util.List;
import weblogic.transaction.TransactionLogger;

public interface ServerCoordinatorDescriptorManager extends CoordinatorDescriptorManager {
   void setLocalCoordinatorDescriptor(CoordinatorDescriptor var1);

   ServerCoordinatorDescriptor getLocalCoordinatorDescriptor();

   String getLocalCoordinatorURL();

   ServerCoordinatorDescriptor getOrCreate(String var1);

   CoordinatorDescriptor getOrCreateForMigration(String var1);

   List getAllCheckpointServers();

   void setLatestServerCheckpoint(TransactionLogger var1, ServerCheckpoint var2);

   void setLatestServerCheckpoint(ServerCheckpoint var1);

   void checkpointIfNecessary();

   ServerCoordinatorDescriptor[] getServers(String var1);

   void checkpointServers();

   void setOnlySSLCoordinatorURL();

   void setServerCheckpointNeeded(boolean var1);

   void setPurgeFromCheckpointIntervalSeconds(int var1);

   int getPurgeFromCheckpointIntervalSeconds();

   void handleNotification(PropertyChangeNotification var1, String var2);

   ServerCoordinatorDescriptor[] getActiveServers();
}
