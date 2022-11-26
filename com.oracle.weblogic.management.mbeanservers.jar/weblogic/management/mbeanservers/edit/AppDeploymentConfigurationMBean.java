package weblogic.management.mbeanservers.edit;

import weblogic.management.mbeanservers.Service;

public interface AppDeploymentConfigurationMBean extends Service {
   DescriptorMBean[] getDescriptors();

   AppDeploymentConfigurationModuleMBean[] getModules();
}
