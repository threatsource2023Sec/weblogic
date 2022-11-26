package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface PartitionUserFileSystemManagerMBean extends RuntimeMBean {
   boolean exists(String var1) throws ManagementException;

   boolean mkdir(String var1) throws ManagementException;

   String[] list(String var1) throws ManagementException;

   boolean deleteFile(String var1) throws ManagementException;

   boolean rmdir(String var1) throws ManagementException;
}
