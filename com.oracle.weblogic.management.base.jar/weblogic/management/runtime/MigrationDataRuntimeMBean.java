package weblogic.management.runtime;

public interface MigrationDataRuntimeMBean extends RuntimeMBean {
   int SUCCEEDED = 0;
   int IN_PROGRESS = 1;
   int FAILED = 2;

   String[] getMachinesAttempted();

   String getServerName();

   int getStatus();

   String getMachineMigratedFrom();

   String getMachineMigratedTo();

   long getMigrationStartTime();

   long getMigrationEndTime();

   String getClusterName();

   String getClusterMasterName();
}
