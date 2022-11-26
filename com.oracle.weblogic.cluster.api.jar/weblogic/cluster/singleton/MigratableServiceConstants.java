package weblogic.cluster.singleton;

public interface MigratableServiceConstants {
   int RESTART = 1;
   int NO_RESTART = -1;
   String TIMER_MANAGER = "MigratableServerTimerManager";
   String SINGLETON_TIMER_MANAGER = "SingletonServiceTimerManager";
   int STATUS_DONE = 0;
   int SOURCE_DOWN = 1;
   int DESTINATION_DOWN = 2;
   int STATUS_FAILED = -1;
   int MIGRATION_IN_PROGRESS = -2;
   String SINGLETON_LEASING_SERVICE_NAME = "service";
   String SERVER_LEASING_SERVICE_NAME = "wlsserver";
}
