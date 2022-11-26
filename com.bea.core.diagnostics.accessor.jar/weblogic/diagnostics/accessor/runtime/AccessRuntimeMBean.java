package weblogic.diagnostics.accessor.runtime;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public interface AccessRuntimeMBean extends RuntimeMBean {
   String SINGLETON_NAME = "Accessor";

   String[] getAvailableDiagnosticDataAccessorNames() throws ManagementException;

   DataAccessRuntimeMBean lookupDataAccessRuntime(String var1) throws ManagementException;

   DataAccessRuntimeMBean[] getDataAccessRuntimes() throws ManagementException;

   void removeAccessor(String var1) throws ManagementException;
}
