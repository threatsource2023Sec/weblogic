package weblogic.management.runtime;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.management.configuration.MachineMBean;

public interface ClusterRuntimeMBean extends ReplicationRuntimeMBean, HealthFeedback {
   int getAliveServerCount();

   long getResendRequestsCount();

   long getFragmentsSentCount();

   long getFragmentsReceivedCount();

   /** @deprecated */
   @Deprecated
   String[] getSecondaryDistributionNames();

   long getMulticastMessagesLostCount();

   String[] getServerNames();

   long getForeignFragmentsDroppedCount();

   /** @deprecated */
   @Deprecated
   String getCurrentSecondaryServer();

   HashMap getUnreliableServers();

   HealthState getHealthState();

   MachineMBean getCurrentMachine();

   ServerMigrationRuntimeMBean getServerMigrationRuntime();

   JobSchedulerRuntimeMBean getJobSchedulerRuntime();

   UnicastMessagingRuntimeMBean getUnicastMessaging();

   String[] getActiveSingletonServices();

   void initiateResourceGroupMigration(String var1, String var2, String var3, int var4) throws TimeoutException, IllegalStateException;
}
