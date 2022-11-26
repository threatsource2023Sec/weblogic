package weblogic.management.provider;

public interface MigrationData extends ServerMachineMigrationData {
   int SUCCEEDED = 0;
   int IN_PROGRESS = 1;
   int FAILED = 2;
   String SERVER_MIGRATION = "server";
   String SERVICE_MIGRATION = "service";

   int getStatus();

   String getMachineMigratedFrom();

   long getMigrationStartTime();

   long getMigrationEndTime();

   String getClusterName();

   String getClusterMasterName();

   String getMigrationType();
}
