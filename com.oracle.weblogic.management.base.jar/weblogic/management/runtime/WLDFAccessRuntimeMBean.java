package weblogic.management.runtime;

import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.management.ManagementException;

public interface WLDFAccessRuntimeMBean extends AccessRuntimeMBean {
   WLDFDataAccessRuntimeMBean lookupWLDFDataAccessRuntime(String var1) throws ManagementException;

   WLDFDataAccessRuntimeMBean[] getWLDFDataAccessRuntimes() throws ManagementException;
}
