package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface PSAssemblyRuntimeMBean extends PSEntryCursorRuntimeMBean {
   String getMapEntries() throws ManagementException;

   String getMapEntries(int var1) throws ManagementException;
}
