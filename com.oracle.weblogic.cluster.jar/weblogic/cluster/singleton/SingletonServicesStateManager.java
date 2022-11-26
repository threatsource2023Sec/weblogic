package weblogic.cluster.singleton;

import java.util.Map;

public interface SingletonServicesStateManager {
   String DEFAULT_STATE_MANAGER = "default-singleton-statemanager";
   int FAILED_STATE = 0;
   int MIGRATABLE_STATE = 1;
   int NON_MIGRATABLE_STATE = 2;
   int SHUTDOWN_STATE = 3;
   int MANUAL_STATE = 4;
   int INACTIVE_MANAGED = 5;
   String[] STRINGIFIED_STATE = new String[]{"Failed", "Migratable", "Non-Migratable", "Shutdown", "Manual", "Inactive-Managed"};

   boolean checkServiceState(String var1, int var2);

   SingletonServicesState getServiceState(String var1);

   boolean storeServiceState(String var1, SingletonServicesState var2);

   boolean removeServiceState(String var1);

   Map getAllServicesState();

   void syncState();

   void leaseAcquired();

   void lostLease();
}
