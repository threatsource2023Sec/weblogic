package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface ServiceMigrationRuntimeMBean extends RuntimeMBean {
   boolean isClusterMaster();

   String getClusterMasterName() throws ManagementException;

   ServiceMigrationDataRuntimeMBean[] getMigrationData() throws ManagementException;
}
