package weblogic.management.runtime;

public interface ServiceMigrationDataRuntimeMBean extends RuntimeMBean {
   int SUCCEEDED = 0;
   int IN_PROGRESS = 1;
   int FAILED = 2;

   String[] getDestinationsAttempted();

   String getServerName();

   int getStatus();

   String getMigratedFrom();

   String getMigratedTo();

   long getMigrationStartTime();

   long getMigrationEndTime();

   String getClusterName();

   String getCoordinatorName();
}
