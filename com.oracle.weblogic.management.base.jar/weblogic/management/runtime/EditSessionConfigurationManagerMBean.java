package weblogic.management.runtime;

import weblogic.management.ManagementException;
import weblogic.server.ServiceFailureException;

public interface EditSessionConfigurationManagerMBean extends RuntimeMBean {
   EditSessionConfigurationRuntimeMBean[] getEditSessionConfigurations();

   EditSessionConfigurationRuntimeMBean lookupEditSessionConfiguration(String var1);

   EditSessionConfigurationRuntimeMBean createEditSessionConfiguration(String var1, String var2) throws IllegalArgumentException, ManagementException, ServiceFailureException;

   void destroyEditSessionConfiguration(EditSessionConfigurationRuntimeMBean var1) throws ServiceFailureException, ManagementException;

   void forceDestroyEditSessionConfiguration(EditSessionConfigurationRuntimeMBean var1) throws ServiceFailureException, ManagementException;
}
