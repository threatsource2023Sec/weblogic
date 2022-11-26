package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface PathServiceRuntimeMBean extends RuntimeMBean {
   PSAssemblyRuntimeMBean[] getAssemblies() throws ManagementException;
}
