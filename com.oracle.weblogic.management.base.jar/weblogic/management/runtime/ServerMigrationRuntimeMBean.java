package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface ServerMigrationRuntimeMBean extends RuntimeMBean {
   boolean isClusterMaster();

   String getClusterMasterName() throws ManagementException;

   MigrationDataRuntimeMBean[] getMigrationData() throws ManagementException;
}
